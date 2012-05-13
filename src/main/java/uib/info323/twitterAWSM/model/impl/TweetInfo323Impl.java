package uib.info323.twitterAWSM.model.impl;

import java.util.Date;
import java.util.List;

import org.springframework.social.twitter.api.TwitterProfile;

import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public class TweetInfo323Impl implements TweetInfo323 {

	private List<Long> related;
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
	private long inReplyToStatusId;
	private int retweetCount;
	private List<String> mentions;
	private List<String> tags;
	private TwitterUserInfo323 userInfo;

	public TweetInfo323Impl() {
	}

	public TweetInfo323Impl(List<Long> related, long id, String text,
			Date createdAt, String fromUser, String profileImageUrl,
			long toUserId, long fromUserId, String languageCode, String source,
			double tweetRank, Long inReplyToStatusId, Integer retweetCount,
			List<String> mentions, List<String> tags,
			TwitterUserInfo323 userInfo) {
		this.related = related;
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
		this.userInfo = userInfo;
	}

	// private List<Tweet> replies;
	// private double tweetRank;
	// private Long inReplyToStatusId;
	// private Integer retweetCount;
	// private List<String> mentions;
	// private List<String> tags;
	// private List<TwitterProfile> retweeters;
	//
	// public TweetInfo323() {
	// super(0, "", new Date(), "", "", (long) 0, (long) 0, "", "");
	//
	// }
	//
	// public TweetInfo323(long id, String text, Date createdAt, String
	// fromUser,
	// String profileImageUrl, Long toUserId, long fromUserId,
	// String languageCode, String source) {
	// super(id, text, createdAt, fromUser, profileImageUrl, toUserId,
	// fromUserId, languageCode, source);
	// }
	//
	// public TweetInfo323(long id, String text, Date createdAt, String
	// fromUser,
	// String profileImageUrl, Long toUserId, long fromUserId,
	// String languageCode, String source, Long inReplyToStatus,
	// Integer retweetCount, double tweetRank) {
	// super(id, text, createdAt, fromUser, profileImageUrl, toUserId,
	// fromUserId, languageCode, source);
	// this.inReplyToStatusId = inReplyToStatus;
	// this.retweetCount = retweetCount;
	// this.tweetRank = tweetRank;
	// mentions = getMentions();
	// tags = getTags();
	// }
	//
	// public TweetInfo323(Tweet t, List<Tweet> replies2,
	// List<TwitterProfile> retweeters) {
	// super(t.getId(), t.getText(), t.getCreatedAt(), t.getFromUser(), t
	// .getProfileImageUrl(), t.getToUserId(), t.getFromUserId(), t
	// .getLanguageCode(), t.getSource());
	// this.replies = replies2;
	// this.retweeters = retweeters;
	// }
	//
	// private long checkNull(Long toUserId2) {
	// if (toUserId2 == null) {
	// return 0;
	// }
	// return toUserId2;
	// }
	//
	// public List<Tweet> getReplies() {
	// return replies;
	// }
	//
	// public List<String> getTags() {
	//
	// return Parser.parseTweets(getText(), '#');
	// }
	//
	// public List<String> getMentions() {
	// return Parser.parseTweets(getText(), '@');
	// }
	//
	// public Long getInReplyToStatusId() {
	// return inReplyToStatusId;
	// }
	//
	// public Integer getRetweetCount() {
	// return retweetCount;
	// }
	//
	// public double getTweetRank() {
	// return tweetRank;
	// }
	//
	// public void setTweetRank(double tweetRank) {
	// this.tweetRank = tweetRank;
	// }
	//
	// public void setReplies(List<Tweet> replies) {
	// this.replies = replies;
	//
	// }
	//
	// public void setTags(List<String> tags) {
	// this.tags = tags;
	// }
	//
	// public void setMentions(List<String> mentions) {
	// this.mentions = mentions;
	//
	// }
	//
	// public void setInReplyToStatusId(Long inReplyToStatusId) {
	// this.inReplyToStatusId = inReplyToStatusId;
	//
	// }
	//
	// public void setRetweetCount(Integer retweetCount) {
	// this.retweetCount = retweetCount;
	//
	// }
	//
	// public List<TwitterProfile> getRetweeters() {
	// return retweeters;
	// }
	//
	// public void setRetweeters(List<TwitterProfile> retweeters) {
	// this.retweeters = retweeters;
	//
	// }

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
		if (!(obj instanceof TweetInfo323))
			return false;
		TweetInfo323Impl other = (TweetInfo323Impl) obj;
		if (id != other.getId())
			return false;
		return true;
	}

	public int compareTo(TweetInfo323 o) {
		if (tweetRank > o.getTweetRank()) {
			return 1;
		} else if (tweetRank < o.getTweetRank()) {
			return -1;
		} else {
			return 0;
		}
	}

	public List<Long> getRelated() {
		return related;
	}

	public void setRelated(List<Long> related) {
		this.related = related;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public long getToUserId() {
		return toUserId;
	}

	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}

	public long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double getTweetRank() {
		return tweetRank;
	}

	public void setTweetRank(double tweetRank) {
		this.tweetRank = tweetRank;
	}

	public Long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public int getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}

	public List<String> getMentions() {
		return mentions;
	}

	public void setMentions(List<String> mentions) {
		this.mentions = mentions;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public List<TwitterProfile> getRetweeters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRetweeters(List<TwitterProfile> retweeters) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setToUserId(Long toUserId) {
		// TODO Auto-generated method stub

	}

	@Override
	public TwitterUserInfo323 getUserInfo() {
		return userInfo;
	}

	@Override
	public void setTwitterUserInfo323(TwitterUserInfo323 userInfo) {
		this.userInfo = userInfo;
	}

}
