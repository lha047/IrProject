package uib.info323.twitterAWSM.io;

import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.io.impl.JsonTweetFactory;
import uib.info323.twitterAWSM.io.impl.JsonUserFactory;

public class JsonFeedJamFactory extends AbstractFeedJamFactory {
	
	private final String apiUrl = "https://api.twitter.com/";
	private final String searchApiUrl = "https://search.twitter.com/search.json?";
	private final RestTemplate restTemplate = new RestTemplate();
	
	public JsonFeedJamFactory() {
		super();
	}
	

	@Override
	public TweetFactoryIntf getTweetFactory() {
		// TODO Auto-generated method stub
		return new JsonTweetFactory(searchApiUrl, restTemplate);
	}

	@Override
	public UserFactoryIntf getUserFactory() {
		// TODO Auto-generated method stub
		return new JsonUserFactory(apiUrl, restTemplate);
	}

}
