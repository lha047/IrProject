package uib.info323.twitterAWSM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.exceptions.TweetException;
import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.io.AbstractFeedJamFactory;
import uib.info323.twitterAWSM.io.TweetSearchFactory;
import uib.info323.twitterAWSM.io.impl.JsonFeedJamFactory;
import uib.info323.twitterAWSM.io.impl.MySQLTweetFactory;
import uib.info323.twitterAWSM.io.impl.MySQLUserFactory;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

/**
 * 
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "/search", method = RequestMethod.GET)
public class SearchController {

	private static final Logger logger = LoggerFactory
			.getLogger(SearchController.class);
	@Autowired
	private MySQLUserFactory mySQLUserFactory;

	@Autowired
	private MySQLTweetFactory mySqlTweetFactory;

	// doesn't work as it's own controller, should fix, this is hack, this is
	// Dog
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search(@RequestParam String q, int resultsPerPage) {

		ModelAndView mav = new ModelAndView("tagSearchResults");
		
		if(q.isEmpty()) {
			mav.addObject("error", "Please specify a search term.");
			return mav;
		}
		
		JsonFeedJamFactory factory = (JsonFeedJamFactory) AbstractFeedJamFactory
				.getFactory(AbstractFeedJamFactory.JSON);

		TweetSearchFactory tweetFactory = factory.getTweetFactory();

		TweetSearchResults tweetResults;
		try {
			tweetResults = tweetFactory.searchTweets(q, resultsPerPage);

			System.out.println(tweetResults.getTweets().size());
			// For each tweet get user info
			for (TweetInfo323 tweet : tweetResults.getTweets()) {
				long userId = tweet.getFromUserId();
				TwitterUserInfo323 user;
				System.out.println("User id: " + userId);

				try {

					user = mySQLUserFactory.selectUserById(userId);
					System.out.println("Find in database");

				} catch (UserNotFoundException e) {
					e.printStackTrace();
					user = factory.getUserSearchFactory().searchUserByNameId(
							userId);
					logger.info("Insert user into DB!");
					mySQLUserFactory.addUser(user);
				}
				tweet.setTwitterUserInfo323(user);

				try {
					insertTweetToDB(tweet);
				} catch (TweetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// logger.info("Number of tweets matching " + "#"+q + " is " +
			// tweetResults.size());
			logger.info("Searching for: " + "#" + q);

			mav.addObject("query", q);
			mav.addObject("results", tweetResults);
			mav.addObject("nextPageUrl", tweetResults.nextPageUrl());

		} catch (BadRequestException e) {
			mav.addObject("error", "Ran out of requests.");
		}

		return mav;

	}

	private void insertTweetToDB(TweetInfo323 tweet) throws TweetException {

		mySqlTweetFactory.insertTweet(tweet);

	}

	// ajax requests mockup (virker ikke slik den skal =D)
	@RequestMapping(value = "/ajax", method = RequestMethod.GET)
	public ModelAndView ajax(@RequestParam String q, int rpp, int page,
			long max_id) {

		ModelAndView mav = new ModelAndView("tweetList");

		JsonFeedJamFactory factory = (JsonFeedJamFactory) AbstractFeedJamFactory
				.getFactory(AbstractFeedJamFactory.JSON);
		TweetSearchFactory tweetFactory = factory.getTweetFactory();

		try {
			TweetSearchResults tweetResults = tweetFactory.getNextPage(q, rpp,
					page, max_id);

			// For each tweet get user info
			for (TweetInfo323 tweet : tweetResults.getTweets()) {
				long userId = tweet.getFromUserId();
				TwitterUserInfo323 user;
				System.out.println("User id: " + userId);

				try {

					user = mySQLUserFactory.selectUserById(userId);
					System.out.println("Find in database");

				} catch (UserNotFoundException e) {
					e.printStackTrace();
					user = factory.getUserSearchFactory().searchUserByNameId(
							userId);
					logger.info("Insert user into DB!");
					mySQLUserFactory.addUser(user);
				}
				tweet.setTwitterUserInfo323(user);

				try {
					insertTweetToDB(tweet);
				} catch (TweetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// logger.info("Number of tweets matching " + "#"+q + " is " +
			// tweetResults.size());
			logger.info("Searching for: " + "#" + q);
			mav.addObject("query", q);
			mav.addObject("results", tweetResults);
		} catch (BadRequestException e) {
			mav.addObject("error", "Ran out of requests.");
		}

		return mav;

	}

}
