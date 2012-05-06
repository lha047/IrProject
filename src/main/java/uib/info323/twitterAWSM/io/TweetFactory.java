package uib.info323.twitterAWSM.io;

import java.util.Date;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;

public interface TweetFactory {
	
	public TweetSearchResults searchTweets(String searchTerm);
	public TweetSearchResults searchTweetsByDate(String searchTerm, Date date);
	
}
