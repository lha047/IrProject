package uib.info323.twitterAWSM.model.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.model.interfaces.ITwitterUserInfo323;

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

	public int getFitnessScore() {

		return fitnessScore;
	}

	public Date getCreatedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getFavoritesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFollowersCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFriendsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileImageUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getScreenName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getStatusesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

}
