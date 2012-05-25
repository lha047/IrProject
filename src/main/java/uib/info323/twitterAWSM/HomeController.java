package uib.info323.twitterAWSM;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.io.AbstractFeedJamFactory;
import uib.info323.twitterAWSM.io.TrendFactory;

import uib.info323.twitterAWSM.model.interfaces.Trends;

import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;
import uib.info323.twitterAWSM.utils.JsonTweetParser;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) throws IOException {
		logger.info("Printing homepage");

		ModelAndView mav = new ModelAndView("home");

		logger.info("Get todays trends with factory");

		TrendFactory factory = AbstractFeedJamFactory.getFactory(
				AbstractFeedJamFactory.JSON).getTrendFactory();
		Trends trends = null;
		try {
			trends = factory.getDailyTrendsForDate(new Date()); // Get todays
																// trends
		} catch (BadRequestException e) {
			mav.addObject("error", "No more requests");
		}
		logger.info("Got todays trends...");

		mav.addObject("trends", trends);

		return mav;
	}
}
