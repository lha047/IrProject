package uib.info323.twitterAWSM;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;

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
	
	
	@RequestMapping(value = "/tag")
	public ModelAndView search(@RequestParam String q) {
		
		ModelAndView mav = new ModelAndView("tagSearchResults");
		
		List<TweetInfo323> tweetList = new ArrayList<TweetInfo323>();
		logger.info("Number of tweets matching " + "#"+q + " is " + tweetList.size());
		logger.info("Searching for: " + "#"+q);
		mav.addObject("query", q);
		mav.addObject("results", tweetList);
		
		return mav;
		
	}
	
}
