package uib.info323.twitterAWSM.io;

import java.util.Date;
import uib.info323.twitterAWSM.model.interfaces.ITweetSearchResults;

public interface TweetFactoryIntf {
	
	public ITweetSearchResults searchTweets(String searchTerm);
	public ITweetSearchResults searchTweetsByDate(String searchTerm, Date date);
	
}
