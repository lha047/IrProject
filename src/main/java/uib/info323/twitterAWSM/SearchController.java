package uib.info323.twitterAWSM;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.io.AbstractFeedJamFactory;
import uib.info323.twitterAWSM.io.TweetFactory;
import uib.info323.twitterAWSM.io.impl.JsonFeedJamFactory;
import uib.info323.twitterAWSM.io.impl.MySQLUserFactory;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
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
	
	
	
	// doesn't work as it's own controller, should fix, this is hack, this is
		// Dog
		@RequestMapping(method = RequestMethod.GET)
		public ModelAndView search(@RequestParam String q, int resultsPerPage) {

			ModelAndView mav = new ModelAndView("tagSearchResults");
			JsonFeedJamFactory factory = (JsonFeedJamFactory) AbstractFeedJamFactory
					.getFactory(AbstractFeedJamFactory.JSON);
			TweetFactory tweetFactory = factory.getTweetFactory();
			TweetSearchResults tweetResults = tweetFactory.searchTweets(q,
					resultsPerPage);
			
			System.out.println(tweetResults.getTweets().size());
			// For each tweet get user info
			for(TweetInfo323 tweet : tweetResults.getTweets()) {
				long userId = tweet.getFromUserId();
				TwitterUserInfo323 user;
				System.out.println("User id: " + userId);
				
				try {
					
					user = mySQLUserFactory.searchUserByNameId(userId);
					System.out.println("Find in database");

				} catch (UserNotFoundException e) {
					e.printStackTrace();
					user = factory.getUserSearchFactory().searchUserByNameId(userId);
					logger.info("Insert user into DB!");
					mySQLUserFactory.addUser(user);
				}
				tweet.setTwitterUserInfo323(user);
				
			}

			// logger.info("Number of tweets matching " + "#"+q + " is " +
			// tweetResults.size());
			logger.info("Searching for: " + "#" + q);

			mav.addObject("query", q);
			mav.addObject("results", tweetResults);
			mav.addObject("nextPageUrl", tweetResults.nextPageUrl());

			return mav;

		}

		// ajax requests mockup (virker ikke slik den skal =D)
		@RequestMapping(value = "/ajax", method = RequestMethod.GET)
		public ModelAndView ajax(@RequestParam String q, int rpp, int page,
				long max_id) {

			ModelAndView mav = new ModelAndView("tweetList");

			JsonFeedJamFactory factory = (JsonFeedJamFactory) AbstractFeedJamFactory
					.getFactory(AbstractFeedJamFactory.JSON);
			TweetFactory tweetFactory = factory.getTweetFactory();
			
			TweetSearchResults tweetResults = tweetFactory.getNextPage(q, rpp, page, max_id);

			// logger.info("Number of tweets matching " + "#"+q + " is " +
			// tweetResults.size());
			logger.info("Searching for: " + "#" + q);
			mav.addObject("query", q);
			mav.addObject("results", tweetResults);

			return mav;

		}
	
}
