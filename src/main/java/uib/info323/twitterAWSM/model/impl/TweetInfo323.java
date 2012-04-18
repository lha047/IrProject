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
	private Long toUserId;
	private long fromUserId;
	private String languageCode;
	private String source;

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

}
