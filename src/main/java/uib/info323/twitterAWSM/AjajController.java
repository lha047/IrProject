package uib.info323.twitterAWSM;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.exceptions.TweetException;
import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.io.TweetSearchFactory;
import uib.info323.twitterAWSM.io.impl.JsonTweetFactory;
import uib.info323.twitterAWSM.io.impl.JsonUserFactory;
import uib.info323.twitterAWSM.io.impl.MySQLTweetFactory;
import uib.info323.twitterAWSM.io.impl.MySQLUserFactory;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;
import uib.info323.twitterAWSM.utils.JsonUserParser;

@Controller
@RequestMapping(value = "/ajaj")
public class AjajController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	private TweetSearchFactory tweetSearchFactory;

	private String apiUrl = "https://api.twitter.com/";

	private String searchApiUrl = "https://search.twitter.com/search.json?";

	private JsonUserFactory jsonUserFactory;

	@Autowired
	private MySQLUserFactory mySqlUserFactory;
	@Autowired
	private MySQLTweetFactory mySqlTweetFactory;

	public AjajController() {
		tweetSearchFactory = new JsonTweetFactory(searchApiUrl, apiUrl,
				new RestTemplate());
		jsonUserFactory = new JsonUserFactory();
	}

	@RequestMapping(value = "/processSearch", method = RequestMethod.POST)
	public @ResponseBody
	String processSearch(@RequestParam String searchResponse) {
		TweetSearchResults searchResult = tweetSearchFactory
				.jsonToSearchResults(searchResponse);

		List<Long> usersWhoDontExisitInDB = mySqlUserFactory
				.checkIfUsersExistsInDB(searchResult);

		StringBuilder jsonString = new StringBuilder("");
		for (Long user : usersWhoDontExisitInDB) {
			jsonString.append(user);
			jsonString.append(",");
		}
		jsonString.append("");
		System.out.println("usersWhoDontExistInDB " + jsonString.toString());
		return jsonString.toString();
	}

	/**
	 * @param users
	 *            . user info from twitter which doesnt exist in db
	 * @return full view
	 */
	@RequestMapping(value = "/processUsers", method = RequestMethod.POST)
	public ModelAndView processUsers(@RequestParam String users,
			String searchQuery, String searchRequest, int rpp) {
		ModelAndView mav = new ModelAndView("tweetList");
		System.out.println("SearchRequest " + searchRequest);
		// Creates search results page
		TweetSearchResults searchResult = tweetSearchFactory
				.jsonToSearchResults(searchRequest);

		// adds new users to db
		List<TwitterUserInfo323> twitterUsers = null;

		if (users.length() > 0) {

			twitterUsers = JsonUserParser.jsonToUsers(users);
		}
		if (twitterUsers != null) {

			int inserted = mySqlUserFactory.addBatchUsers(twitterUsers,
					MySQLUserFactory.SQL_INSERT_USER);

		}

		// retrieves user info for tweets from db
		for (TweetInfo323 tweet : searchResult.getTweets()) {
			long id = tweet.getFromUserId();
			TwitterUserInfo323 user = new TwitterUserInfo323Impl();
			try {
				user = mySqlUserFactory.selectUserById(id);
			} catch (UserNotFoundException u) {
				tweet.setTwitterUserInfo323(new TwitterUserInfo323Impl());
			}
			tweet.setTwitterUserInfo323(user);
		}

		List<TweetInfo323> tweets = searchResult.getTweets();
		for (TweetInfo323 t : tweets) {
			try {
				double userRank = t.getUserInfo().getFitnessScore(); // find
																		// userRank
				t.setTweetRank(userRank); // sets and calculates tweetRank
				mySqlTweetFactory.insertTweet(t);
			} catch (TweetException e) {

				System.out.println("Error inserting tweet " + t.getId());
			}
		}
		System.out
				.println("SearchResult "
						+ searchResult.getTweets().size()
						+ " user in index 0:"
						+ searchResult.getTweets().get(0).getUserInfo()
								.getScreenName());

		mav.addObject("query", searchQuery);
		mav.addObject("results", searchResult);
		return mav;

	}

	@RequestMapping(value = "/processFollowers", method = RequestMethod.POST)
	public ResponseEntity<String> processFollowers(@RequestParam String userId,
			String followers) {

		long userIdLong = Long.parseLong(userId);
		long startParse = System.currentTimeMillis();
		FollowersFollowingResultPage followersResultPage = JsonUserParser
				.jsonToFollowersFollowing(userIdLong, followers);
		long timeToParse = System.currentTimeMillis() - startParse;
		logger.debug("Time to parse followers: " + timeToParse / 1000
				+ " seconds");

		long startInsert = System.currentTimeMillis();
		int updated = mySqlUserFactory
				.newInsertBatchFollowers(followersResultPage);
		// int updated = mySqlUserFactory.insertBatchFollowers(
		// followersResultPage, MySQLUserFactory.SQL_INSERT_FOLLOWERS);
		long timeToInsert = System.currentTimeMillis() - startInsert;
		logger.debug("Time to insert followers: " + timeToInsert / 1000
				+ " seconds");
		System.out.println("followers batchinserted " + updated);

		return new ResponseEntity<String>(HttpStatus.OK);

	}

	@RequestMapping(value = "/processFollowing", method = RequestMethod.POST)
	public ResponseEntity<String> processFollowing(@RequestParam String userId,
			String following) {
		System.out.println("processFollowing request.....");
		long userIdLong = Long.parseLong(userId);
		FollowersFollowingResultPage followingResultPage = JsonUserParser
				.jsonToFollowersFollowing(userIdLong, following);

		int updated = mySqlUserFactory
				.newInsertBatchFollowing(followingResultPage);

		// int updated = mySqlUserFactory.insertBatchFollowing(
		// followingResultPage, MySQLUserFactory.SQL_INSERT_FOLLOWING);

		System.out.println("following batchinsert " + updated);
		return new ResponseEntity<String>(HttpStatus.OK);

	}
}
