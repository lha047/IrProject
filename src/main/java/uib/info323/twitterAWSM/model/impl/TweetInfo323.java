package uib.info323.twitterAWSM.model.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.model.Parser;
import uib.info323.twitterAWSM.model.interfaces.IReply;
import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;

@Component
public class TweetInfo323 implements ITweetInfo323 {

	private List<IReply> replies;
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
	
	public TweetInfo323(
			long id, 
			String text, 
			Date createdAt, 
			String fromUser, 
			String profileImageUrl, 
			long toUserId, 
			long fromUserId,
			String languageCode,
			String source,
			double tweetRank) {
		
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

	public List<IReply> getReplies() {
		return replies;
	}

	public List<String> getTags() {

		return Parser.parseTweets(getText(), '#');
	}

	public List<String> getMentions() {
		return Parser.parseTweets(getText(), '@');
	}

	public Date getCreatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFromUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getFromUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Long getInReplyToStatusId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLanguageCode() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileImageUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getRetweetCount() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getToUserId() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public int compareTo(ITweetInfo323 o) {
		if(tweetRank > o.getTweetRank()) {
			return 1;
		} else if(tweetRank < o.getTweetRank()) {
			return -1;
		} else {
			return 0;
		}
	}

	

}
