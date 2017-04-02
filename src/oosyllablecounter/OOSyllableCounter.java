package oosyllablecounter;

/**
 * receive a word and count its syllables using 6 states as inner classes below (the OO approach)
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class OOSyllableCounter {
	private final State START = new StartState();
	private final State CONSONANT = new ConsonantState();
	private final State SINGLE_VOWEL = new SingleVowelState();
	private final State MULTIVOWEL = new MultiVowelState();
	private final State HYPHEN = new HyphenState();
	private final State NONWORD = new NonwordState();
	private int index = 0;
	private int wordLength = 0;

	private State state;
	private int syllableCount = 0;

	/**
	 * change to a new state
	 * 
	 * @param newstate
	 *            is new state
	 */
	public void setState(State newstate) {
		state = newstate;
	}

	/**
	 * count syllables of the input word by using state machine
	 * 
	 * @param word
	 *            is an input word
	 * @return a number of syllables of the word
	 */
	public int countSyllables(String word) {
		syllableCount = 0;
		char c;
		word = word.toLowerCase();
		wordLength = word.length();
		setState(START);
		for (int i = 0; i < wordLength; i++) {
			c = word.charAt(i);
			index = i;
			if (c == '\'' || Character.isWhitespace(c))
				continue; // ignore apostrophe
			// process character c using state machine
			state.handleChar(c);
			if (state == NONWORD)
				break;
		}
		return syllableCount;
	}

	/**
	 * Start state<br>
	 * start of the string. Checking first character to set state.
	 * 
	 * @author Vittunyuta Maeprasart
	 *
	 */
	class StartState extends State {

		/*
		 * @see oosyllablecounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c)) {
				setState(SINGLE_VOWEL);
				enterState();
			} else if (Character.isLetter(c))
				setState(CONSONANT);
			else if (c == '-' || !Character.isLetter(c))
				setState(NONWORD);
			else
				setState(HYPHEN);
		}

		/*
		 * @see oosyllablecounter.State#enterState()
		 */
		public void enterState() {
			syllableCount++;
		}
	}

	/**
	 * Consonant state<br>
	 * is for the most recent character is a letter but not a vowel.
	 * 
	 * @author Vittunyuta Maeprasart
	 *
	 */
	class ConsonantState extends State {

		/*
		 * @see oosyllablecounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c)) {
				if (c != 'e' || (index != wordLength - 1) || (syllableCount == 0)) {
					setState(SINGLE_VOWEL);
					enterState();
				}
			} else if (Character.isLetter(c))
				/* stay in consonant state */;
			else if (c == '-')
				setState(HYPHEN);
			else
				setState(NONWORD);
		}

		/*
		 * @see oosyllablecounter.State#enterState()
		 */
		public void enterState() {
			syllableCount++;
		}
	}

	/**
	 * Single_vowel state<br>
	 * is for the most recent character is a vowel, including 'y', that is the first
	 * vowel in a vowel group.
	 * 
	 * @author Vittunyuta Maeprasart
	 *
	 */
	class SingleVowelState extends State {

		/*
		 * @see oosyllablecounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isVowel(c)) {
				setState(MULTIVOWEL);
			} else if (Character.isLetter(c))
				setState(CONSONANT);
			else if (c == '-')
				setState(HYPHEN);
			else
				setState(NONWORD);
		}

	}

	/**
	 * Multi_vowel state<br>
	 * most recent char is a vowel that follows another vowel (2 or more vowels
	 * together).
	 * 
	 * @author Vittunyuta Maeprasart
	 *
	 */
	class MultiVowelState extends State {
		
		/*
		 * @see oosyllablecounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isVowel(c)) {
				;
			} else if (Character.isLetter(c))
				setState(CONSONANT);
			else if (c == '-')
				setState(HYPHEN);
			else
				setState(NONWORD);
		}

	}

	/**
	 * Hyphen state<br>
	 * the most recent character is hyphen and the word cannot have 2 hyphen
	 * together.
	 * 
	 * @author Vittunyuta Maeprasart
	 *
	 */
	class HyphenState extends State {

		/*
		 * @see oosyllablecounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c)) {
				if (c != 'e' || (index != wordLength - 1) || (syllableCount == 0)) {
					setState(SINGLE_VOWEL);
					enterState();
				}
			} else if (Character.isLetter(c))
				setState(CONSONANT);
			else
				setState(NONWORD);
		}

		/*
		 * @see oosyllablecounter.State#enterState()
		 */
		public void enterState() {
			syllableCount++;
		}

	}

	/**
	 * Non_word state<br>
	 * the character sequence is not a word. The word contains non-letters or
	 * doesn't contain any vowels except '-' and apostrophe (').
	 * 
	 * @author Vittunyuta Maeprasart
	 *
	 */
	class NonwordState extends State {

		/*
		 * @see oosyllablecounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			enterState(); // end checking this word
		}

		/*
		 * @see oosyllablecounter.State#enterState()
		 */
		public void enterState() {
			syllableCount = 0;
		}
	}

}
