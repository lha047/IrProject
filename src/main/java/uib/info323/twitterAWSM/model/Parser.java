package uib.info323.twitterAWSM.model;

import java.util.ArrayList;
import java.util.List;

public class Parser {

	/**
	 * @param tweet
	 * @param symbol
	 * @return List of strings where first character is symbol
	 */
	public static List<String> parseTweets(String tweet, Character symbol) {
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < tweet.length(); i++) {
			Character c = tweet.charAt(i);
			// System.out.println(c);
			if (c.compareTo(symbol) == 0) {
				// System.out.println("index of #: " + i);
				String subString = tweet.substring(i);

				// System.out.println("SubString: " + subString);
				int indexOfTagEnd = -1;

				indexOfTagEnd = subString.indexOf(" ");
				// System.out.println("index of tag end: " + indexOfTagEnd);

				if (indexOfTagEnd != -1
						&& indexOfTagEnd < subString.length() - 1) {
					// System.out.println("i: " + i + " last: " +
					// indexOfTagEnd);
					list.add(tweet.substring(i, i + indexOfTagEnd));
				}
				if (!subString.contains(" ")) {
					list.add(tweet.substring(i));
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		String tweet = "#Breivik shows no emotions while hearing the screams of his victims... http://gu.com/p/37v27/tw via @guardian #oslo #norway";
		List<String> liste = Parser.parseTweets(tweet, '@');
		System.out.println("Tweet tags:");
		for (String s : liste) {
			System.out.println("Tag: " + s);
		}
	}
}
