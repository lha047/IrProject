package uib.info323.twitterAWSM.model.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.model.Parser;
import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;

@Component
public class TweetInfo323 implements ITweetInfo323 {

	private List<ITweetInfo323> replies;
	private long id;
	private String text;
	private Date createdAt;
	private String fromUser;
	private String profileImageUrl;
	private long toUserId;
	private long fromUserId;
	private String languageCode;
	private String source;
	private double tweetRank;
	private Long inReplyToStatusId;
	private Integer retweetCount;
	private List<String> mentions;
	private List<String> tags;

	public TweetInfo323(long id, String text, Date createdAt, String fromUser,
			String profileImageUrl, long toUserId, long fromUserId,
			String languageCode, String source, double tweetRank) {

		this.id = id;
		this.text = text;
		this.createdAt = createdAt;
		this.fromUser = fromUser;
		this.profileImageUrl = profileImageUrl;
		this.toUserId = toUserId;
		this.fromUserId = fromUserId;
		this.languageCode = languageCode;
		this.source = source;
		this.tweetRank = tweetRank;

	}

	public TweetInfo323(List<ITweetInfo323> replies, long id, String text,
			Date createdAt, String fromUser, String profileImageUrl,
			long toUserId, long fromUserId, String languageCode, String source,
			double tweetRank, Long inReplyToStatusId, Integer retweetCount,
			List<String> mentions, List<String> tags) {
		this.replies = replies;
		this.id = id;
		this.text = text;
		this.createdAt = createdAt;
		this.fromUser = fromUser;
		this.profileImageUrl = profileImageUrl;
		this.toUserId = toUserId;
		this.fromUserId = fromUserId;
		this.languageCode = languageCode;
		this.source = source;
		this.tweetRank = tweetRank;
		this.inReplyToStatusId = inReplyToStatusId;
		this.retweetCount = retweetCount;
		this.mentions = mentions;
		this.tags = tags;
	}

	public List<ITweetInfo323> getReplies() {
		return replies;
	}

	public List<String> getTags() {

		return Parser.parseTweets(getText(), '#');
	}

	public List<String> getMentions() {
		return Parser.parseTweets(getText(), '@');
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getFromUser() {
		return fromUser;
	}

	public long getFromUserId() {
		return fromUserId;
	}

	public long getId() {
		return id;
	}

	public Long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public Integer getRetweetCount() {
		return retweetCount;
	}

	public String getSource() {
		return source;
	}

	public String getText() {
		return text;
	}

	public Long getToUserId() {
		return toUserId;
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
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (id != other.id)
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

	public void setReplies(List<ITweetInfo323> replies) {
		this.replies = replies;

	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setMentions(List<String> mentions) {
		this.mentions = mentions;

	}

	public void setCreatedAt(Date date) {
		this.createdAt = date;

	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;

	}

	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;

	}

	public void setId(long id) {
		this.id = id;

	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;

	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;

	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;

	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;

	}

	public void setSource(String source) {
		this.source = source;

	}

	public void setText(String text) {
		this.text = text;

	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;

	}

}
