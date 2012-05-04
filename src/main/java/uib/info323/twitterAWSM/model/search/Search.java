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

import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323;
import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.ITwitterUserInfo323;

@Component
public class Search implements ISearch {

	@Autowired
	TwitterTemplate twitterTemplat;

	ITwitterUserInfo323 user;

	/*
	 * (non-Javadoc)
	 * 
	 * @see uib.info323.twitterAWSM.model.search.ISearch#getTweets()
	 */
	public List<Tweet> getTweets() {

		// return twitterTemplat.timelineOperations().getPublicTimeline();
		return null;
	}

	public ITwitterUserInfo323 getUser(String user) {

		TwitterProfile profile = twitterTemplat.userOperations()
				.getUserProfile(user);
		this.user = new TwitterUserInfo323();
		this.user.setId(profile.getId());
		this.user.setName(profile.getName());
		this.user.setScreenName(profile.getScreenName());
		this.user.setProfileImageUrl(profile.getProfileImageUrl());
		this.user.setFollowersCount(profile.getFollowersCount());
		this.user.setFriendsCount(profile.getFriendsCount());

		// return this.user;
		return null;
	}

	public List<Place> findPlace() {
		double latitude = 60.3880718;
		double longitude = 5.3321794;
		// Place place = twitterTemplat.geoOperations().getPlace("Bergen");

		// return twitterTemplat.geoOperations().reverseGeoCode(latitude,
		// longitude);
		return null;
	}

	public List<TwitterProfile> getFollowers() {

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
		SearchResults sr = twitterTemplat.searchOperations().search(string);
		List<Tweet> searchResultTweets = sr.getTweets();

		List<ITweetInfo323> list = new ArrayList<ITweetInfo323>();
		// int i = 0;
		// for (Tweet t : searchResultTweets) {
		// if (t.getFromUser().equals("felixge")) {
		// System.out.println("ID: " + t.getId() + " **from user "
		// + t.getFromUser() + " fromUserId " + t.getFromUserId()
		// + " toUserId " + t.getToUserId() + " inReplyStatusId "
		// + t.getInReplyToStatusId() + " retweetCount "
		// + t.getRetweetCount() + " tweet " + t.getText());
		// }
		// finner replies

		// Long ideen = new Long(t.getId());
		// SearchResults searchres = twitterTemplat.searchOperations().search(
		// "@" + searchResultTweets.get(i).getFromUser());
		// List<Tweet> tempReplies = searchres.getTweets();
		// List<Tweet> replies = new ArrayList<Tweet>();
		// for (Tweet twee : tempReplies) {
		// if (twee.getInReplyToStatusId() != null) {
		// if (ideen.compareTo(twee.getInReplyToStatusId()) == 0) {
		// // System.out.println("TO**from user " +
		// // twee.getFromUser()
		// // + " fromUserId " + twee.getFromUserId()
		// // + " toUserId " + twee.getToUserId()
		// // + " inReplyStatusId " + twee.getInReplyToStatusId()
		// // + " retweetCount " + twee.getRetweetCount()
		// // + " tweet " + twee.getText());
		// replies.add(twee);
		// }
		// }
		// }
		// // Finner Twitter profilene som har retweetet
		// // System.out.println("Users of reweets");
		// List<TwitterProfile> retweeters = twitterTemplat
		// .timelineOperations().getRetweetedBy(t.getId());
		// System.out.println("IRTSI " + t.getInReplyToStatusId());
		// int index = 0;
		// for (TwitterProfile p : retweeters) {
		// System.out.println("user " + index + " " + p.getScreenName());
		// index++;
		// }
		// list.add(new TweetInfo323(t, replies, retweeters));
		// System.out.println("****");
		// i++;

		// }

		return list;
	}
}
