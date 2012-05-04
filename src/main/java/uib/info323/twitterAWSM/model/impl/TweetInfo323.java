package uib.info323.twitterAWSM.model.impl;

import java.util.Date;
import java.util.List;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.model.Parser;
import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;

@Component
public class TweetInfo323 extends Tweet implements ITweetInfo323 {

	private List<Tweet> replies;
	private double tweetRank;
	private Long inReplyToStatusId;
	private Integer retweetCount;
	private List<String> mentions;
	private List<String> tags;
	private List<TwitterProfile> retweeters;

	public TweetInfo323() {
		super(0, "", new Date(), "", "", (long) 0, (long) 0, "", "");

	}

	public TweetInfo323(long id, String text, Date createdAt, String fromUser,
			String profileImageUrl, Long toUserId, long fromUserId,
			String languageCode, String source) {
		super(id, text, createdAt, fromUser, profileImageUrl, toUserId,
				fromUserId, languageCode, source);
	}

	public TweetInfo323(long id, String text, Date createdAt, String fromUser,
			String profileImageUrl, Long toUserId, long fromUserId,
			String languageCode, String source, Long inReplyToStatus,
			Integer retweetCount, double tweetRank) {
		super(id, text, createdAt, fromUser, profileImageUrl, toUserId,
				fromUserId, languageCode, source);
		this.inReplyToStatusId = inReplyToStatus;
		this.retweetCount = retweetCount;
		this.tweetRank = tweetRank;
		mentions = getMentions();
		tags = getTags();
	}

	public TweetInfo323(Tweet t, List<Tweet> replies2,
			List<TwitterProfile> retweeters) {
		super(t.getId(), t.getText(), t.getCreatedAt(), t.getFromUser(), t
				.getProfileImageUrl(), t.getToUserId(), t.getFromUserId(), t
				.getLanguageCode(), t.getSource());
		this.replies = replies2;
		this.retweeters = retweeters;
	}

	private long checkNull(Long toUserId2) {
		if (toUserId2 == null) {
			return 0;
		}
		return toUserId2;
	}

	public List<Tweet> getReplies() {
		return replies;
	}

	public List<String> getTags() {

		return Parser.parseTweets(getText(), '#');
	}

	public List<String> getMentions() {
		return Parser.parseTweets(getText(), '@');
	}

	public Long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public Integer getRetweetCount() {
		return retweetCount;
	}

	public double getTweetRank() {
		return tweetRank;
	}

	public void setTweetRank(double tweetRank) {
		this.tweetRank = tweetRank;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (super.getId() ^ (super.getId() >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ITweetInfo323))
			return false;
		TweetInfo323 other = (TweetInfo323) obj;
		if (super.getId() != other.getId())
			return false;
		return true;
	}

	public int compareTo(ITweetInfo323 o) {
		if (tweetRank > o.getTweetRank()) {
			return 1;
		} else if (tweetRank < o.getTweetRank()) {
			return -1;
		} else {
			return 0;
		}
	}

	public void setReplies(List<Tweet> replies) {
		this.replies = replies;

	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setMentions(List<String> mentions) {
		this.mentions = mentions;

	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;

	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;

	}

	public List<TwitterProfile> getRetweeters() {
		return retweeters;
	}

	public void setRetweeters(List<TwitterProfile> retweeters) {
		this.retweeters = retweeters;

	}
}
