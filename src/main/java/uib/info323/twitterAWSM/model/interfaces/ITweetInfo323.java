package uib.info323.twitterAWSM.model.interfaces;

import java.util.Date;
import java.util.List;


/**
 * Based on the org.springframwork.social.twitter.api.Tweet Implementing classes
 * must extend org.springframwork.social.twitter.api.Tweet
 * 
 * @author Lisa
 * 
 */
public interface ITweetInfo323 {

	public List<IReply> getReplies();

	public List<String> getTags();

	public List<String> getMentions();

	// Methods from Tweet-class
	public Date getCreatedAt();

	public String getFromUser();

	public long getFromUserId();

	public long getId();

	public Long getInReplyToStatusId();

	public String getLanguageCode();

	public String getProfileImageUrl();

	public Integer getRetweetCount();

	public String getSource();

	public String getText();

	public Long getToUserId();

}
