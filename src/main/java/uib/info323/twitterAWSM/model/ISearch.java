package uib.info323.twitterAWSM.model;

import java.util.List;

import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

public interface ISearch {

	public List<Tweet> getTweets();

	public TwitterProfile getUser(ITwitterRequest user);

	public List<TwitterProfile> getFollowers();

	public List<Place> findPlace();

	public List<Tweet> search(String string);

}
