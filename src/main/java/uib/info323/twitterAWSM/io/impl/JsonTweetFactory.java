package uib.info323.twitterAWSM.io.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uib.info323.twitterAWSM.io.TweetFactoryIntf;
import uib.info323.twitterAWSM.model.impl.TweetInfo323;

public class JsonTweetFactory implements TweetFactoryIntf{
	
	private final String apiUrl;
	private final RestTemplate restTemplate;
	
	public JsonTweetFactory(String apiUrl, RestTemplate restTemplate) {
		this.apiUrl = apiUrl;
		this.restTemplate = restTemplate;
	}

	@Override
	public List<TweetInfo323> searchTweets(String searchTerm) {
		// TODO Auto-generated method stub
		String requestUrl = apiUrl + "q={searchTerm}";
		String jsonTweets = restTemplate.getForObject(requestUrl, String.class, searchTerm);
		
		Gson gson = new Gson();
		
		
		LinkedList<TweetInfo323> tweets = new LinkedList<TweetInfo323>();
		
		return tweets;
	}

	@Override
	public List<TweetInfo323> searchTweetsByDate(String searchTerm, Date date) {
		// TODO Auto-generated method stub
		return null;
	};
	
	public static void main(String[] args) {
		TweetFactoryIntf tweetFactory = new JsonTweetFactory("https://search.twitter.com/search.json?", new RestTemplate());
		tweetFactory.searchTweets("uib");
	}
	

}
