package uib.info323.twitterAWSM.model;

import java.util.Date;

import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public class TwitterUserMock implements TwitterUserInfo323 {

	int fitnessScoew;
	int followers;
	private int followersCount;
	private int friendsCount;
	private long id;
	private String location;
	private String name;
	private String profileImageUrl;
	private String screenName;
	private int statusesCount;
	private Date lastUpdated;

	public TwitterUserMock() {
		this.fitnessScoew = 10;
		this.followers = 20;
		this.followersCount = 23;
		this.friendsCount = 40;
		this.id = 2345234;
		this.location = "Bergen";
		this.name = "Lisa";
		this.profileImageUrl = "";
		this.screenName = "@lisaHalvors";
		this.statusesCount = 213;
		this.lastUpdated = new Date();
	}

	public int compareTo(TwitterUserInfo323 o) {
		if (this.fitnessScoew == o.getFitnessScore())
			return 0;
		else if (this.fitnessScoew < o.getFitnessScore())
			return -1;
		else
			return 1;
	}

	public float getFitnessScore() {

		return fitnessScoew;
	}

	public void setFitnessScore(int fitnessScore) {
		fitnessScoew = fitnessScore;

	}

	public int getFollowersCount() {

		return followers;
	}

	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;

	}

	public int getFriendsCount() {

		return friendsCount;
	}

	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;

	}

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;

	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;

	}

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getProfileImageUrl() {

		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;

	}

	public String getScreenName() {

		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;

	}

	public int getStatusesCount() {
		// TODO Auto-generated method stub
		return statusesCount;
	}

	public void setStatusesCount(int statusesCount) {
		// TODO Auto-generated method stub
		this.statusesCount = statusesCount;
	}

	@Override
	public void setFitnessScore(float fitnessScore) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getCreatedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFavoritesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFavoritesCount(int favoritesCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLanguage(String language) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getProfileUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProfileUrl(String profileUrl) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUrl(String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getLastUpdated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastUpdated(Date lastUpdated) {
		// TODO Auto-generated method stub
		
	}

}
