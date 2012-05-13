package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.exceptions.TweetException;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;

public interface TweetDAO {

	public boolean insertTweet(TweetInfo323 tweet) throws TweetException;

	public boolean updateTweet(TweetInfo323 tweet) throws TweetException;

	public TweetInfo323 selectTweetById(long id);
}
