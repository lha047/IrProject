package uib.info323.twitterAWSM.model.search;

import java.util.List;

import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.ITwitterRequest;
import uib.info323.twitterAWSM.model.interfaces.ITwitterUserInfo323;

public interface ISearch {

	public List<Tweet> getTweets();

	public ITwitterUserInfo323 getUser(ITwitterRequest user);

	public List<TwitterProfile> getFollowers();

	public List<Place> findPlace();

	public List<ITweetInfo323> search(String string);

}
