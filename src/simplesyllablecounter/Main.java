package simplesyllablecounter;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import stopwatch.Stopwatch;

/**
 * read all the words from file or URL and call countSyllables.
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class Main {
	final static String DICT_URL = "http://se.cpe.ku.ac.th/dictionary.txt";
	final static String DICT_FILENAME = "dictionary.txt";
	final static String SCRABBLE_FILENAME = "scrabble.txt";
	/**
	 * read all words from input file or URL
	 * 
	 * @return list of all words in String
	 */
	public static List<String> readLine() {
		InputStream in = null;
		try {
			// use url
			URL url = new URL(DICT_URL);
			in = url.openStream();
			// read file from file in folder
			ClassLoader loader = Main.class.getClassLoader();
			in = loader.getResourceAsStream(DICT_FILENAME);
			/* choose one input way - using url or filename */

			// read all words
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			List<String> words = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				words.add(line);
			}
			return words;

		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);

		}
	}

	/**
	 * call countSyllableCounter
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Stopwatch stopwatch = new Stopwatch();
		stopwatch.start();
		List<String> words = readLine();
		SimpleSyllableCounter counter = new SimpleSyllableCounter();
		int totalSyllables = 0;
		for (String word : words) {
			totalSyllables  += counter.countSyllables(word);
		}
		stopwatch.stop();
		System.out.println("Reading words from "+ DICT_URL);
		System.out.println("Counted "+totalSyllables+" syllables in "+words.size()+" words");
		System.out.printf("Elapsed times: %.3f sec",stopwatch.getElapsed());

	}
}
