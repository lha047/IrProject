package uib.info323.twitterAWSM.io;

import java.util.Date;
import java.util.List;

import uib.info323.twitterAWSM.model.impl.TweetInfo323;

public interface TweetFactoryIntf {
	
	public List<TweetInfo323> searchTweets(String searchTerm);
	public List<TweetInfo323> searchTweetsByDate(String searchTerm, Date date);
	
}
