package uib.info323.twitterAWSM.model.interfaces;

import java.util.Date;

public interface ITwitterUserInfo323 {

	public int getFitnessScore();

	// methods from TwitterProfile
	public Date getCreatedDate();

	public String getDescription();

	public int getFavoritesCount();

	public int getFollowersCount();

	public int getFriendsCount();

	public long getId();

	public String getLanguage();

	public String getLocation();

	public String getName();

	public String getProfileImageUrl();

	public String getProfileUrl();

	public String getScreenName();

	public int getStatusesCount();

	public String getUrl();

}
