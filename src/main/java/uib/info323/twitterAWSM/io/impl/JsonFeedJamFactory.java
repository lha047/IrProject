package uib.info323.twitterAWSM.io.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.io.AbstractFeedJamFactory;
import uib.info323.twitterAWSM.io.TrendFactory;
import uib.info323.twitterAWSM.io.TweetDAO;
import uib.info323.twitterAWSM.io.TweetSearchFactory;
import uib.info323.twitterAWSM.io.UserDAO;
import uib.info323.twitterAWSM.io.UserFactory;
import uib.info323.twitterAWSM.io.UserSearchFactory;

@Component
public class JsonFeedJamFactory extends AbstractFeedJamFactory {

	private final String apiUrl = "https://api.twitter.com/";
	private final String searchApiUrl = "https://search.twitter.com/search.json?";
	@Autowired
	private RestTemplate restTemplate;

	public JsonFeedJamFactory() {
		super();
	}

	@Override
	public TweetSearchFactory getTweetFactory() {
		return new JsonTweetFactory(searchApiUrl, apiUrl, restTemplate);
	}

	@Override
	public UserSearchFactory getUserSearchFactory() {
		return new JsonUserFactory();
	}

	@Override
	public TrendFactory getTrendFactory() {
		return new JsonTrendFactory(apiUrl, restTemplate);
	}

	@Override
	public UserFactory getUserFactory() {

		return new UserFactoryImpl();
	}

	@Override
	public UserDAO getUserDAO() {

		return new MySQLUserFactory();
	}

	@Override
	public TweetDAO getTweetDAO() {
		// TODO Auto-generated method stub
		return new MySQLTweetFactory();
	}

}
