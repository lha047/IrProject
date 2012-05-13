package uib.info323.twitterAWSM.io;

import java.util.Date;

import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;

public interface TweetSearchFactory {

	public TweetSearchResults searchTweets(String searchTerm, int resultsPerPage);

	public TweetSearchResults searchTweetsByDate(String searchTerm, Date date);

	public TweetSearchResults getNextPage(String query, int rpp, int page,
			long maxId);
}