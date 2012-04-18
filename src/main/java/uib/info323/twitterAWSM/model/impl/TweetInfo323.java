package uib.info323.twitterAWSM.model.impl;

import java.util.Date;
import java.util.List;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.model.Parser;
import uib.info323.twitterAWSM.model.interfaces.IReply;
import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;

@Component
public class TweetInfo323 extends Tweet implements ITweetInfo323 {

	private List<IReply> replies;

	/**
	 * @param id
	 * @param text
	 * @param createdAt
	 * @param fromUser
	 * @param profileImageUrl
	 * @param toUserId
	 * @param fromUserId
	 * @param languageCode
	 * @param source
	 */
	public TweetInfo323(long id, String text, Date createdAt, String fromUser,
			String profileImageUrl, Long toUserId, long fromUserId,
			String languageCode, String source, List<IReply> replies) {
		super(id, text, createdAt, fromUser, profileImageUrl, toUserId,
				fromUserId, languageCode, source);
		this.replies = replies;
	}

	public List<IReply> getReplies() {
		return replies;
	}

	public List<String> getTags() {

		return Parser.parseTweets(getText(), '#');
	}

	public List<String> getMentions() {
		return Parser.parseTweets(getText(), '@');
	}

}
