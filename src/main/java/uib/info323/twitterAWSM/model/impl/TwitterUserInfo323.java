package uib.info323.twitterAWSM.model.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.model.interfaces.ITwitterUserInfo323;

/**
 * @author Lisa
 * 
 */
@Component
public class TwitterUserInfo323 implements ITwitterUserInfo323 {

	private int fitnessScore;
	private long id;
	private String screenName;
	private String name;
	private String url;
	private String profileImageUrl;
	private String description;
	private String location;
	private Date createdDate;
	private int favoritesCount;
	private int followersCount;
	private int friendsCount;
	private String language;
	private String profileUrl;
	private int statusesCount;

	/**
	 * Constructor
	 * 
	 * @param fitnessScore
	 * @param id
	 * @param screenName
	 * @param name
	 * @param url
	 * @param profileImageUrl
	 * @param description
	 * @param location
	 * @param createdDate
	 * @param favoritesCount
	 * @param followersCount
	 * @param friendsCount
	 * @param language
	 * @param profileUrl
	 * @param statusesCount
	 */
	public TwitterUserInfo323(int fitnessScore, long id, String screenName,
			String name, String url, String profileImageUrl,
			String description, String location, Date createdDate,
			int favoritesCount, int followersCount, int friendsCount,
			String language, String profileUrl, int statusesCount) {
		this.fitnessScore = fitnessScore;
		this.id = id;
		this.screenName = screenName;
		this.name = name;
		this.url = url;
		this.profileImageUrl = profileImageUrl;
		this.description = description;
		this.location = location;
		this.createdDate = createdDate;
		this.favoritesCount = favoritesCount;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.language = language;
		this.profileUrl = profileUrl;
		this.statusesCount = statusesCount;
	}

	public int getFitnessScore() {
		return fitnessScore;
	}

	public void setFitnessScore(int fitnessScore) {
		this.fitnessScore = fitnessScore;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getFavoritesCount() {
		return favoritesCount;
	}

	public void setFavoritesCount(int favoritesCount) {
		this.favoritesCount = favoritesCount;
	}

	public int getFollowersCount() {
		return followersCount;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public int getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(int statusesCount) {
		this.statusesCount = statusesCount;
	}

	public int compareTo(ITwitterUserInfo323 o) {
		if (fitnessScore == o.getFitnessScore()) {
			return 0;
		} else if (fitnessScore > o.getFitnessScore()) {
			return 1;
		} else
			return -1;
	}

}
