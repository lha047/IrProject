package uib.info323.twitterAWSM.model.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.model.Parser;
import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.ITwitterRequest;
import uib.info323.twitterAWSM.model.interfaces.ITwitterUserInfo323;

@Component
public class Search implements ISearch {

	@Autowired
	TwitterTemplate twitterTemplat;

	// @Autowired
	// RestTemplate restTemplate;

	public List<Tweet> getTweets() {

		return twitterTemplat.timelineOperations().getPublicTimeline();
	}

	public ITwitterUserInfo323 getUser(ITwitterRequest user) {

		return null;// twitterTemplat.userOperations().getUserProfile("@lisaHalvors");
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

	/*
	 * Prøver å få tak i tweets inkl replies.
	 * 
	 * @see
	 * uib.info323.twitterAWSM.model.search.ISearch#search(java.lang.String)
	 */
	public List<ITweetInfo323> search(String string) {
		twitterTemplat = new TwitterTemplate();
		SearchResults sr = twitterTemplat.searchOperations().search(string);
		List<Tweet> tweets = sr.getTweets();
		List<ITweetInfo323> list = new ArrayList<ITweetInfo323>();
		for (Tweet t : tweets) {

			if (t.getInReplyToStatusId() != null) {
				// System.out.println("Tweet id: " + t.getId() + " from : "
				// + t.getFromUser() + " " + t.getFromUserId()
				// + " in reply status id :" + t.getInReplyToStatusId()
				// + " retweet count : " + t.getRetweetCount()
				// + " to user id: " + t.getToUserId() + " text: "
				// + t.getText());
				List<String> liste = Parser.parseTweets(t.getText(), '@');
				// System.out.println("@@@: " + liste.get(0));
				// SearchResults s =
				// twitterTemplat.searchOperations().search(liste.get(0));
				SearchResults s = twitterTemplat.searchOperations().search(
						t.getFromUser());

				boolean found = false;
				for (int i = 0; i < s.getTweets().size() && !found; i++) {
					System.out.println("id: " + s.getTweets().get(i).getId()
							+ "  " + s.getTweets().get(i).getText());
					if (s.getTweets().get(i).getId() == t
							.getInReplyToStatusId()) {
						// System.out.println("id: "
						// + s.getTweets().get(i).getId() + " @@@@:"
						// + s.getTweets().get(i).getFromUser()
						// + "  TWEET: " + s.getTweets().get(i).getText());
						found = true;
					}
				}
			}

			ITweetInfo323 tweet;

			// list.add();
		}

		return list;
	}

	public static void main(String[] args) {
		Search s = new Search();
		List<ITweetInfo323> liste = s.search("#Bergen");

	}
}
