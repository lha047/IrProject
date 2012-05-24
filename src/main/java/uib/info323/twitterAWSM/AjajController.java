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

import uib.info323.twitterAWSM.io.TweetSearchFactory;
import uib.info323.twitterAWSM.io.impl.JsonTweetFactory;
import uib.info323.twitterAWSM.io.impl.MySQLUserFactory;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;

@Controller
@RequestMapping(value = "/ajaj")
public class AjajController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	private TweetSearchFactory tweetSearchFactory;

	private String apiUrl = "https://api.twitter.com/";

	private String searchApiUrl = "https://search.twitter.com/search.json?";

	@Autowired
	private MySQLUserFactory mySqlUserFactory;

	public AjajController() {
		tweetSearchFactory = new JsonTweetFactory(searchApiUrl, apiUrl,
				new RestTemplate());
	}

	@RequestMapping(value = "/processSeach", method = RequestMethod.POST)
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

		return mav;

	}

	@RequestMapping(value = "/processFollowers", method = RequestMethod.POST)
	public @ResponseBody
	String processFollowers(@RequestParam Long userId, String followers) {
		System.out.println(userId);
		// Error if something went wrong
		return followers;

	}

	@RequestMapping(value = "/processFollowing", method = RequestMethod.POST)
	public @ResponseBody
	String processFollowing(@RequestParam Long userId, String following) {

		// Error if something went wrong
		return following;

	}
}
