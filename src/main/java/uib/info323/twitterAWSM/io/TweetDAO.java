package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.model.impl.TweetInfo323Impl;

public interface TweetDAO {

	public boolean insertTweet(TweetInfo323Impl tweet);

	public boolean updateTweet(TweetInfo323Impl tweet);
}
