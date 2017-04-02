package oosyllablecounter;

/**
 * Provide basic behavior of all state
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public abstract class State {
	/**
	 * checking an input character and changing state following each state
	 * conditions.
	 * 
	 * @param c
	 *            is a character to check
	 */
	public abstract void handleChar(char c);

	/**
	 * use to increment the syllable count when entering the initial vowel.
	 */
	public void enterState() {
		/* default is to do something */ };

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
