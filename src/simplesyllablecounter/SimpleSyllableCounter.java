package simplesyllablecounter;

/**
 * receive a word and count its syllables using enum to define 6 state (the
 * simple non-OO approach)
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class SimpleSyllableCounter {
	private int totalCount = 0;
	private State state = null;
	private int wordLength;

	/**
	 * Define 6 type of the states.
	 * 
	 * @author Vittunyuta Maeprasart
	 *
	 */
	public enum State {
		START, // starting state for checking state type of the first character
		CONSONANT, // the most recent character is a letter but not a vowel.
		SINGLE_VOWEL, // the most recent character is a vowel, including 'y',
						// that is the first vowel in a vowel group.
		MULTIVOWEL, // most recent char is a vowel that follows another vowel
		HYPHEN, // the most recent character is hyphen and the word cannot have
				// 2 hyphen together.
		NONWORD; // the recent word contains non-letters or doesn't contain any
					// vowels except '-' and apostrophe (').
	}

	/**
	 * count syllables of the input word
	 * 
	 * @param word
	 *            is an input word
	 * @return a number of syllables of the word
	 */
	public int countSyllables(String word) {
		int syllables = 0;
		char c;
		word = word.toLowerCase();
		wordLength = word.length();
		if (state == null)
			state = State.START; // State is enum of the states

		if (wordLength > 0) {
			c = word.charAt(0);
			if (!(c == '\'' || Character.isWhitespace(c))) { // ignore
																// apostrophe
				// process character c using state machine
				switch (state) {
				case START:
					if (isVowelOrY(c)) {
						state = State.SINGLE_VOWEL;
						syllables++;
					} else if (Character.isLetter(c))
						state = State.CONSONANT;
					else if (c == '-' || !Character.isLetter(c))
						state = State.NONWORD;
					else
						state = State.HYPHEN;
					break;

				case CONSONANT:
					if (isVowelOrY(c)) {
						if (c != 'e' || (word.length() != 1) || (totalCount == 0)) {
							state = State.SINGLE_VOWEL;
							syllables++;
						}
					} else if (Character.isLetter(c))
						/* stay in consonant state */;
					else if (c == '-')
						state = State.HYPHEN;
					else
						state = State.NONWORD;
					break;

				case SINGLE_VOWEL:
					if (isVowel(c)) {
						state = State.MULTIVOWEL;
					} else if (Character.isLetter(c))
						state = State.CONSONANT;
					else if (c == '-')
						state = State.HYPHEN;
					else
						state = State.NONWORD;
					break;

				case MULTIVOWEL:
					if (isVowel(c)) {
						;
					} else if (Character.isLetter(c))
						state = State.CONSONANT;
					else if (c == '-')
						state = State.HYPHEN;
					else
						state = State.NONWORD;
					break;

				case HYPHEN:
					if (isVowelOrY(c)) {
						if (c != 'e' || (word.length() != 1) || (totalCount == 0)) {
							state = State.SINGLE_VOWEL;
							syllables++;
						}
					} else if (Character.isLetter(c))
						state = State.CONSONANT;
					else
						state = State.NONWORD;
					break;

				case NONWORD:
					syllables = (-1)*totalCount;// end checking this word
					break;

				default:
					break;
				}
			}
			totalCount += syllables;
			return syllables + countSyllables(word.substring(1));
		}
		state = State.START;
		totalCount = 0;
		return syllables;
	}

	/**
	 * checking an input character. Is it a vowel (a,e,i,o,u) or y?
	 * 
	 * @param c
	 *            is a character to check
	 * @return true if c is one of five vowel or y
	 */
	public boolean isVowelOrY(char c) {
		if (isVowel(c) || c == 'y')
			return true;
		return false;
	}

	/**
	 * checking an input character. Is it a vowel (a,e,i,o,u)?
	 * 
	 * @param c
	 *            is a character to check
	 * @return true if c is one of five vowel
	 */
	public boolean isVowel(char c) {
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
			return true;
		return false;
	}

}
