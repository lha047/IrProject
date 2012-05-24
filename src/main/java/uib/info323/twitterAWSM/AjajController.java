package uib.info323.twitterAWSM;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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
	String processSearch(@RequestParam String searchResultJson) {
		TweetSearchResults searchResult = tweetSearchFactory
				.jsonToSearchResults(searchResultJson);
		List<Long> usersWhoDontExisitInDB = mySqlUserFactory
				.checkIfUsersExistsInDB(searchResult);
		
		StringBuilder jsonString = new StringBuilder("{ ");
		for(Long user : usersWhoDontExisitInDB) {
			jsonString.append(user);
			jsonString.append(",");
		}
		jsonString.append(" }");
		return jsonString.toString();
	}
	
	@RequestMapping(value = "/processUsers", method = RequestMethod.POST)
	public @ResponseBody
	String processUsers(@RequestParam String users) {
		
		
		
		return users;
		
		
		
	}
};
