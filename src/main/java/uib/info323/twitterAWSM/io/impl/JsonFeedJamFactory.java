package uib.info323.twitterAWSM.io.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.io.AbstractFeedJamFactory;
import uib.info323.twitterAWSM.io.TrendFactory;
import uib.info323.twitterAWSM.io.TweetFactory;
import uib.info323.twitterAWSM.io.UserFactory;

@Component
public class JsonFeedJamFactory extends AbstractFeedJamFactory {
	
	private final String apiUrl = "https://api.twitter.com/";
	private final String searchApiUrl = "https://search.twitter.com/search.json?";
	private final RestTemplate restTemplate = new RestTemplate();
	
	public JsonFeedJamFactory() {
		super();
	}
	

	@Override
	public TweetFactory getTweetFactory() {
		return new JsonTweetFactory(searchApiUrl, apiUrl, restTemplate);
	}

	@Override
	public UserFactory getUserFactory() {
		return new JsonUserFactory(apiUrl, restTemplate);
	}


	@Override
	public TrendFactory getTrendFactory() {
		return new JsonTrendFactory(apiUrl, restTemplate);
	}

}
