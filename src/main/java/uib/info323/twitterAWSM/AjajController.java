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
	private MySQLUserFactory userFactory;

	public AjajController() {
		tweetSearchFactory = new JsonTweetFactory(searchApiUrl, apiUrl,
				new RestTemplate());
	}

	@RequestMapping(value = "/processSeach", method = RequestMethod.POST)
	public @ResponseBody
	String processSearch(@RequestParam String users) {
		TweetSearchResults searchResult = tweetSearchFactory
				.jsonToSearchResults(users);
		List<Long> usersWhoDontExisitInDB = userFactory
				.checkIfUsersExistsInDB(searchResult);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, List<Long>> userMap = new HashMap<String, List<Long>>();

		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(userMap);
		} catch (JsonGenerationException e) {
			return "{ error: { \"JsonGenerationException\" }}";
		} catch (JsonMappingException e) {
			return "{ error: { \"JsonMappintException\" }}";
		} catch (IOException e) {
			return "{ error: { \"IOException\" }}";
		}
		return jsonString;
	}
};
