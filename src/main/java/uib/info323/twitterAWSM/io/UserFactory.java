package uib.info323.twitterAWSM.io;

import java.util.Date;

import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public interface UserFactory {

	public TwitterUserInfo323 createTwitterUser(float fitnessScore, long id,
			String screenName, String name, String url, String profileImageUrl,
			String description, String location, Date createdDate,
			int favoritesCount, int followersCount, int friendsCount,
			String language, String profileUrl, int statusesCount, Date lastUpdated, Date lastRanked);
}
