package uib.info323.twitterAWSM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;
import uib.info323.twitterAWSM.model.search.ISearch;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private ISearch search;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) throws IOException {
		logger.info("Printing homepage");

		
		ArrayList<ITweetInfo323> results = (ArrayList<ITweetInfo323>) search.search("#UIB");
		logger.info("Number of results for UIB: " + results.size());
		
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("publicTweets", search.getTweets());
		mav.addObject("user", search.getUser(null));
		mav.addObject("results", search.search("#UiB"));
		logger.info("Number of results for UIB: " + results.size());

		return mav;
	}
}
