package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.model.impl.TweetInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;

public interface TweetDAO {

	public boolean insertTweet(TweetInfo323Impl tweet);

	public boolean updateTweet(TweetInfo323Impl tweet);

	public TweetInfo323 selectTweetById(long id);
}
