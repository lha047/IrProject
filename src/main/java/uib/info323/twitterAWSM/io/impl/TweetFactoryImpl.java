package uib.info323.twitterAWSM.io.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.io.TweetFactory;
import uib.info323.twitterAWSM.model.impl.TweetInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

@Component
public class TweetFactoryImpl implements TweetFactory {

	@Override
	public TweetInfo323 createTweet(List<Long> related, long id, String text,
			Date createdAt, String fromUser, String profileImageUrl,
			long toUserId, long fromUserId, String languageCode, String source,
			double tweetRank, Long inReplyToStatusId, Integer retweetCount,
			List<Long> reTweeters, List<String> mentions, List<String> tags,
			TwitterUserInfo323 userInfo, Date lastUpdated) {

		return new TweetInfo323Impl(related, id, text, createdAt, fromUser,
				profileImageUrl, toUserId, fromUserId, languageCode, source,
				tweetRank, inReplyToStatusId, retweetCount, reTweeters,
				mentions, tags, userInfo, lastUpdated);
	}

}
