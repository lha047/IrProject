package uib.info323.twitterAWSM.model.impl;

import java.util.Date;

import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.model.interfaces.ITwitterUserInfo323;

@Component
public class TwitterUserInfo323 extends TwitterProfile implements
		ITwitterUserInfo323 {

	private int fitnessScore;

	public TwitterUserInfo323(long id, String screenName, String name,
			String url, String profileImageUrl, String description,
			String location, Date createdDate, int fitnessScore) {
		super(id, screenName, name, url, profileImageUrl, description,
				location, createdDate);
		this.fitnessScore = fitnessScore;
	}

	public int getFitnessScore() {

		return fitnessScore;
	}

}
