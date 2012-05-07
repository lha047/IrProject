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
	
	
	public ModelAndView search(@RequestParam String q, int resultsPerPage) {
		
		ModelAndView mav = new ModelAndView("tagSearchResults");
		
		JsonFeedJamFactory factory = (JsonFeedJamFactory) AbstractFeedJamFactory.getFactory(1);
		TweetFactory tweetFactory = factory.getTweetFactory();
		TweetSearchResults tweetList = tweetFactory.searchTweets(q, resultsPerPage);
		//logger.info("Number of tweets matching " + "#"+q + " is " + tweetList.size());
		logger.info("Searching for: " + "#"+q);
		mav.addObject("query", q);
		mav.addObject(tweetList);
		mav.addObject("results", tweetList);
		
		return mav;
		
	}
	
}
