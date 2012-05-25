package uib.info323.twitterAWSM.io;

import java.util.Date;
import java.util.List;

import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public interface TweetFactory {

	/**
	 * Creates a tweet
	 * 
	 * @param related
	 * @param id
	 * @param text
	 * @param createdAt
	 * @param fromUser
	 * @param profileImageUrl
	 * @param toUserId
	 * @param fromUserId
	 * @param languageCode
	 * @param source
	 * @param tweetRank
	 * @param inReplyToStatusId
	 * @param retweetCount
	 * @param mentions
	 * @param tags
	 * @param userInfo
	 * @return
	 */
	public TweetInfo323 createTweet(List<Long> related, long id, String text,
			Date createdAt, String fromUser, String profileImageUrl,
			long toUserId, long fromUserId, String languageCode, String source,
			double tweetRank, Long inReplyToStatusId, Integer retweetCount,
			List<Long> reTweeters, List<String> mentions, List<String> tags,
			TwitterUserInfo323 userInfo, Date lastUpdated);

}
