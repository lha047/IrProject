package uib.info323.twitterAWSM.model.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

@Component
public interface TweetInfo323 extends Comparable<TweetInfo323> {

	/**
	 * @return
	 */
	public TwitterUserInfo323 getUserInfo();

	/**
	 * @param userInfo
	 */
	public void setTwitterUserInfo323(TwitterUserInfo323 userInfo);

	/**
	 * @return
	 */
	public List<TwitterProfile> getRetweeters();

	/**
	 * @param retweeters
	 */
	public void setRetweeters(List<TwitterProfile> retweeters);

	/**
	 * @return
	 */
	public List<String> getTags();

	/**
	 * @param tags
	 */
	public void setTags(List<String> tags);

	/**
	 * @return
	 */
	public List<String> getMentions();

	/**
	 * @param mentions
	 */
	public void setMentions(List<String> mentions);

	// Methods from Tweet-class
	/**
	 * @return created at date
	 */
	public Date getCreatedAt();

	/**
	 * @param date
	 */
	public void setCreatedAt(Date date);

	/**
	 * @return from user screen name
	 */
	public String getFromUser();

	/**
	 * @param formUser
	 */
	public void setFromUser(String formUser);

	/**
	 * @return from user id
	 */
	public long getFromUserId();

	/**
	 * @param formUserId
	 */
	public void setFromUserId(long formUserId);

	/**
	 * @return tweet id
	 */
	public long getId();

	/**
	 * @param id
	 */
	public void setId(long id);

	/**
	 * @return
	 */
	public Long getInReplyToStatusId();

	/**
	 * @param inReplyToStatusId
	 */
	public void setInReplyToStatusId(Long inReplyToStatusId);

	/**
	 * @return
	 */
	public String getLanguageCode();

	/**
	 * @param languageCode
	 */
	public void setLanguageCode(String languageCode);

	/**
	 * @return
	 */
	public String getProfileImageUrl();

	/**
	 * @param profileImageUrl
	 */
	public void setProfileImageUrl(String profileImageUrl);

	/**
	 * @return
	 */
	public int getRetweetCount();

	/**
	 * @param retweetCount
	 */
	public void setRetweetCount(int retweetCount);

	/**
	 * @return
	 */
	public String getSource();

	/**
	 * @param source
	 */
	public void setSource(String source);

	/**
	 * @return
	 */
	public String getText();

	/**
	 * @param text
	 */
	public void setText(String text);

	/**
	 * @return
	 */
	public long getToUserId();

	/**
	 * @param toUserId
	 */
	public void setToUserId(Long toUserId);

	/**
	 * @return
	 */
	public double getTweetRank();

	/**
	 * @param tweetRank
	 */
	public void setTweetRank(double tweetRank);

	/**
	 * @return
	 */
	public Date getLastUpdated();

	/**
	 * @param lastUpdated
	 */
	public void setLastUpdated(Date lastUpdated);

}
