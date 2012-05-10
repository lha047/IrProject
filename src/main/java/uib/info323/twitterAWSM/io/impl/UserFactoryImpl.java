package uib.info323.twitterAWSM.io.impl;

import java.util.Date;

import uib.info323.twitterAWSM.io.UserFactory;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public class UserFactoryImpl implements UserFactory {

	public UserFactoryImpl() {
	}

	@Override
	public TwitterUserInfo323 createTwitterUser(float fitnessScore, long id,
			String screenName, String name, String url, String profileImageUrl,
			String description, String location, Date createdDate,
			int favoritesCount, int followersCount, int friendsCount,
			String language, String profileUrl, int statusesCount) {

		return new TwitterUserInfo323Impl(fitnessScore, id, screenName, name,
				url, profileImageUrl, description, location, createdDate,
				favoritesCount, followersCount, friendsCount, language,
				profileUrl, statusesCount);
	}

}
