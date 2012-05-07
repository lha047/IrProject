package uib.info323.twitterAWSM.model.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public interface TweetInfo323 extends Comparable<TweetInfo323> {

	// public List<ITweetInfo323> getReplies();
	//
	// public void setReplies(List<ITweetInfo323> replies);

	public List<Long> getRelated();

	public void setRelated(List<Long> related);

	public List<TwitterProfile> getRetweeters();

	public void setRetweeters(List<TwitterProfile> retweeters);

	public List<String> getTags();

	public void setTags(List<String> tags);

	public List<String> getMentions();

	public void setMentions(List<String> mentions);

	// Methods from Tweet-class
	public Date getCreatedAt();

	public void setCreatedAt(Date date);

	public String getFromUser();

	public void setFromUser(String formUser);

	public long getFromUserId();

	public void setFromUserId(long formUserId);

	public long getId();

	public void setId(long id);

	public Long getInReplyToStatusId();

	public void setInReplyToStatusId(Long inReplyToStatusId);

	public String getLanguageCode();

	public void setLanguageCode(String languageCode);

	public String getProfileImageUrl();

	public void setProfileImageUrl(String profileImageUrl);

	public int getRetweetCount();

	public void setRetweetCount(int retweetCount);

	public String getSource();

	public void setSource(String source);

	public String getText();

	public void setText(String text);

	public long getToUserId();

	public void setToUserId(Long toUserId);

	public double getTweetRank();

	public void setTweetRank(double tweetRank);

}