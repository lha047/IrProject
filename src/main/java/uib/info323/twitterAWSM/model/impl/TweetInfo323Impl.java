package uib.info323.twitterAWSM.model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public class TweetInfo323Impl implements TweetInfo323 {

	private static final double ZERO_TAGS = 0.2;
	private static final double ONE_OR_TWO_TAGS = 1;
	private static final double TREE_TAGS = 0.4;
	private static final double FOUR_OR_MORE_TAGS = 0.1;
	private static final double MENTIONS_ZERO = 0.5;
	private static final double MENTIONS_ONE = 0.7;
	private static final double MENTIONS_TWO_AND_TREE = 0.9;
	private static final double MENTIONS__MORE_THAN_TREE = 0.1;
	private static final double ZERO_RETWEETS = 0.1;
	private static final double ONE_RETWEET = 0.5;
	private static final double TWO_TO_TEN_RETWEETS = 0.6;
	private static final double ELEVEN_TO_HUNDRED_RETWEETS = 0.7;
	private static final double HUNDRED_AND_ONE_TO_THOUSAND_RETWEETS = 0.8;
	private static final double THOUSAND_PLUSS_RETWEETS = 0.9;
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
	private Date lastUpdated;
	private List<Long> reTweeters;

	public TweetInfo323Impl() {
	}

	public TweetInfo323Impl(List<Long> related, long id, String text,
			Date createdAt, String fromUser, String profileImageUrl,
			long toUserId, long fromUserId, String languageCode, String source,
			double tweetRank, Long inReplyToStatusId, Integer retweetCount,
			List<Long> reTweeters, List<String> mentions, List<String> tags,
			TwitterUserInfo323 userInfo, Date lastUpdated) {
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
		this.reTweeters = reTweeters;
		this.mentions = mentions;
		this.tags = tags;
		this.userInfo = userInfo;
		this.lastUpdated = lastUpdated;
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
		System.out.println(tweetRank);
		tweetRank += tagPoints();
		System.out.println("tag " + tweetRank);
		tweetRank += mentionPoints();
		System.out.println("metions " + tweetRank);
		tweetRank += reTweetPoints();
		System.out.println("retweet " + tweetRank);

		this.tweetRank = tweetRank;
	}

	public static void main(String[] args) {
		TweetInfo323Impl tweet = new TweetInfo323Impl();
		tweet.setRetweetCount(6000);
		List<String> m = new ArrayList<String>();
		m.add("HER");
		m.add("HER");
		m.add("HER");
		m.add("HER");
		tweet.setMentions(m);
		List<String> t = new ArrayList<String>();
		// t.add("2");
		// t.add("2");
		// t.add("2");
		// t.add("2");
		tweet.setTags(t);
		tweet.setTweetRank(0);
		System.out.println("TweetRank " + tweet.getTweetRank());
	}

	private double reTweetPoints() {
		int reTweetPoints = getRetweetCount();
		double retweetPoints = 0;
		if (reTweetPoints <= 0)
			retweetPoints = ZERO_RETWEETS;
		else if (reTweetPoints == 1)
			retweetPoints = ONE_RETWEET;
		else if (reTweetPoints >= 2 && reTweetPoints <= 10)
			retweetPoints = TWO_TO_TEN_RETWEETS;
		else if (reTweetPoints >= 11 && reTweetPoints <= 100)
			retweetPoints = ELEVEN_TO_HUNDRED_RETWEETS;
		else if (reTweetPoints >= 101 && reTweetPoints <= 1000)
			retweetPoints = HUNDRED_AND_ONE_TO_THOUSAND_RETWEETS;
		else
			retweetPoints = THOUSAND_PLUSS_RETWEETS;
		System.out.println("retweetpoints " + retweetPoints);
		return retweetPoints;
	}

	private double mentionPoints() {
		double mentionsPoints = 0;
		if (getMentions().size() == 0) {
			mentionsPoints = MENTIONS_ZERO;
		} else if (getMentions().size() == 1) {
			mentionsPoints = MENTIONS_ONE;
		} else if (getMentions().size() == 2 || getMentions().size() == 3) {
			mentionsPoints = MENTIONS_TWO_AND_TREE;
		} else
			mentionsPoints = MENTIONS__MORE_THAN_TREE;
		System.out.println("mentionspoint" + mentionsPoints);
		return mentionsPoints;
	}

	private double tagPoints() {
		double tagPoints = 0;
		int tags = getTags().size();
		if (tags <= 0) {
			tagPoints = ZERO_TAGS;
		} else if (tags >= 1 && tags <= 2) {
			tagPoints = ONE_OR_TWO_TAGS;
		} else if (tags == 3)
			tagPoints = TREE_TAGS;
		else
			tagPoints = FOUR_OR_MORE_TAGS;
		System.out.println("tag points " + tagPoints);
		return tagPoints;
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
	public List<Long> getRetweeters() {

		return reTweeters;
	}

	@Override
	public void setRetweeters(List<Long> retweeters) {
		this.reTweeters = retweeters;

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

	public Date getLastUpdated() {

		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
