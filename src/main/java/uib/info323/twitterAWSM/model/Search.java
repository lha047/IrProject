package uib.info323.twitterAWSM.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

@Component
public class Search implements ISearch {

	@Autowired
	TwitterTemplate twitterTemplat;

	public List<Tweet> getTweets() {

		return twitterTemplat.timelineOperations().getPublicTimeline();
	}

	public TwitterProfile getUser(ITwitterRequest user) {

		return twitterTemplat.userOperations().getUserProfile("@lisaHalvors");
	}

	public List<Place> findPlace() {
		double latitude = 60.3880718;
		double longitude = 5.3321794;
		// Place place = twitterTemplat.geoOperations().getPlace("Bergen");

		return twitterTemplat.geoOperations().reverseGeoCode(latitude,
				longitude);
	}

	public List<TwitterProfile> getFollowers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTwitterTemplate(Object arg0) {
		// TODO Auto-generated method stub

	}

	public List<Tweet> search(String string) {
		SearchResults sr = twitterTemplat.searchOperations().search(string, 1,
				10);
		List<Tweet> tweets = sr.getTweets();
		return tweets;
	}
}
