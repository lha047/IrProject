package uib.info323.twitterAWSM.io.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.lf5.util.DateFormatManager;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import uib.info323.twitterAWSM.io.TweetFactoryIntf;
import uib.info323.twitterAWSM.model.impl.TweetInfo323;
import uib.info323.twitterAWSM.model.impl.TweetSearchResults;
import uib.info323.twitterAWSM.model.interfaces.ITweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.ITweetSearchResults;

public class JsonTweetFactory implements TweetFactoryIntf{
	
	private final String searchApiUrl;
	private final String apiUrl;
	private final RestTemplate restTemplate;
	
	public JsonTweetFactory(String searchApiUrl, String apiUrl, RestTemplate restTemplate) {
		this.apiUrl = apiUrl;
		this.searchApiUrl = searchApiUrl;
		this.restTemplate = restTemplate;
	}

	@Override
	public List<TweetInfo323> searchTweets(String searchTerm) {
		// TODO Auto-generated method stub
		String requestUrl = searchApiUrl + "q={searchTerm}";
		String searchResults = restTemplate.getForObject(requestUrl, String.class, searchTerm);
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(searchResults);
		
		JsonObject object = element.getAsJsonObject();
		
		String searchTerms = object.get("query").getAsString();
		String nextPageUrl = object.get("next_page").getAsString();
		String refreshUrl = object.get("refresh_url").getAsString();
		int pageNumber = object.get("page").getAsInt();
		
		LinkedList<ITweetInfo323> tweets = new LinkedList<ITweetInfo323>();
		JsonElement jsonTweets = object.get("results");
		
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		
		
		for(JsonElement tweetElement : jsonTweets.getAsJsonArray()) {
			System.out.println(tweetElement);
			JsonObject tweetObject = tweetElement.getAsJsonObject();
			long id = tweetObject.get("id").getAsLong();
			String dateString = tweetObject.get("created_at").getAsString().substring(0,25);
			Date date;
			try {
				date = formatter.parse(dateString);
			} catch (ParseException e) {
				date = new Date();
			}
			
			
			String relatedRequest = apiUrl + "1/related_results/show/{id}.json?include_entities=1";
			String relatedJson = restTemplate.getForObject(relatedRequest, String.class, id);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonElement elementRelateds = parser.parse(relatedJson);
			String jsonOutput = gson.toJson(elementRelateds);
			System.out.println(jsonOutput);
			
		}
		
		
		
		ITweetSearchResults results = new TweetSearchResults(searchTerms, nextPageUrl, refreshUrl, tweets, pageNumber);
		
		
		
		//LinkedList<TweetInfo323> tweets = new LinkedList<TweetInfo323>();
		
		return null;
	}

	@Override
	public List<TweetInfo323> searchTweetsByDate(String searchTerm, Date date) {
		// TODO Auto-generated method stub
		return null;
	};
	
	public static void main(String[] args) {
		TweetFactoryIntf tweetFactory = new JsonTweetFactory("https://search.twitter.com/search.json?", "https://api.twitter.com/", new RestTemplate());
		tweetFactory.searchTweets("uib");
		
		
	}
	

}
