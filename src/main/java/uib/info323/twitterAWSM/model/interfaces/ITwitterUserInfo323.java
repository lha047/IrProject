package uib.info323.twitterAWSM.model.interfaces;


public interface ITwitterUserInfo323 extends Comparable<ITwitterUserInfo323> {

	public int getFitnessScore();

	public void setFitnessScore(int fitnessScore);

	// methods from TwitterProfile
	// public Date getCreatedDate();
	//
	// public void setCreatedDate(Date createdDate);
	//
	// public String getDescription();
	//
	// public void setDescription(String description);
	//
	// public int getFavoritesCount();
	//
	// public void setFavoritesCount(int favoritesCount);

	public int getFollowersCount();

	public void setFollowersCount(int followersCount);

	public int getFriendsCount();

	public void setFriendsCount(int friendsCount);

	public long getId();

	public void setId(long id);

	// public String getLanguage();
	//
	// public void setLanguage(String language);

	public String getLocation();

	public void setLocation(String location);

	public String getName();

	public void setName(String name);

	public String getProfileImageUrl();

	public void setProfileImageUrl(String profileImageUrl);

	//
	// public String getProfileUrl();
	//
	// public void setProfileUrl(String profileUrl);

	public String getScreenName();

	public void setScreenName(String screenName);

	public int getStatusesCount();

	public void setStatusesCount(int statusesCount);

	// public String getUrl();
	//
	// public void setUrl(String url);

}
