package uib.info323.twitterAWSM;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.io.TweetSearchFactory;
import uib.info323.twitterAWSM.io.impl.JsonTweetFactory;
import uib.info323.twitterAWSM.io.impl.JsonUserFactory;
import uib.info323.twitterAWSM.io.impl.MySQLUserFactory;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

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

		StringBuilder jsonString = new StringBuilder("{ ");
		for (Long user : usersWhoDontExisitInDB) {
			jsonString.append(user);
			jsonString.append(",");
		}
		jsonString.append(" }");
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

		// Creates search results page
		TweetSearchResults searchResult = tweetSearchFactory
				.jsonToSearchResults(searchRequest);

		// adds new users to db
		List<TwitterUserInfo323> twitterUsers = jsonUserFactory
				.parseJsonToUsers(users);
		for (TwitterUserInfo323 user : twitterUsers) {
			mySqlUserFactory.addUser(user);
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

		mav.addObject("query", searchQuery);
		mav.addObject("results", searchResult);
		return mav;

	}

	@RequestMapping(value = "/processFollowers", method = RequestMethod.POST)
	public @ResponseBody
	String processFollowers(@RequestParam String followers) {

		// Error if something went wrong
		return followers;

	}

	@RequestMapping(value = "/processFollowing", method = RequestMethod.POST)
	public @ResponseBody
	String processFollowing(@RequestParam String following) {

		// Error if something went wrong
		return following;

	}
}
