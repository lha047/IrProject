package uib.info323.twitterAWSM;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.io.AbstractFeedJamFactory;
import uib.info323.twitterAWSM.io.TweetFactory;
import uib.info323.twitterAWSM.io.impl.JsonFeedJamFactory;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;

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
		public ModelAndView ajax(@RequestParam String q, String rpp, String page,
				String max_id) {

			ModelAndView mav = new ModelAndView("tweetList");

			JsonFeedJamFactory factory = (JsonFeedJamFactory) AbstractFeedJamFactory
					.getFactory(AbstractFeedJamFactory.JSON);
			TweetFactory tweetFactory = factory.getTweetFactory();
			String nextPageUrl = "?page=" + page + "&max_id=" + max_id + "&q=" + q
					+ "&rpp=" + rpp;
			TweetSearchResults tweetResults = tweetFactory.getNextPage(nextPageUrl);

			// logger.info("Number of tweets matching " + "#"+q + " is " +
			// tweetResults.size());
			logger.info("Searching for: " + "#" + q);
			mav.addObject("query", q);
			mav.addObject("results", tweetResults);

			return mav;

		}
	
}
