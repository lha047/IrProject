package uib.info323.twitterAWSM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;
import uib.info323.twitterAWSM.model.search.ISearch;

/**
 * 
 * 
 * @author
 *
 */
@Controller
@RequestMapping(value = "/search/*")
public class SearchController {

	private static final Logger logger = LoggerFactory
			.getLogger(SearchController.class);
	
	@Autowired
	private ISearch search;
	
	@RequestMapping(value = "/tag")
	public ModelAndView search(@RequestParam String q) {
		
		ModelAndView mav = new ModelAndView("tagSearchResults");
		
		List<ITweetInfo323> tweetList = search.search("#"+q);
		logger.info("Number of tweets matching " + "#"+q + " is " + tweetList.size());
		logger.info("Searching for: " + "#"+q);
		mav.addObject("query", q);
		mav.addObject("results", tweetList);
		
		return mav;
		
	}
	
}