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

import uib.info323.twitterAWSM.io.TweetFactory;
import uib.info323.twitterAWSM.model.impl.TweetInfo323Impl;
import uib.info323.twitterAWSM.model.impl.TweetSearchResultsImpl;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;

public class JsonTweetFactory implements TweetFactory{

	private final String searchApiUrl;
	private final String apiUrl;
	private final RestTemplate restTemplate;

	public JsonTweetFactory(String searchApiUrl, String apiUrl, RestTemplate restTemplate) {
		this.apiUrl = apiUrl;
		this.searchApiUrl = searchApiUrl;
		this.restTemplate = restTemplate;
	}

	@Override
	public TweetSearchResults searchTweets(String searchTerm) {

		// Construct the REST request
		String requestUrl = searchApiUrl + "q={searchTerm}";
		// Send the request to the Twitter search API and store JSON result in String
		String searchResults = restTemplate.getForObject(requestUrl, String.class, searchTerm);

		// Parse the JSON result
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(searchResults);
		JsonObject object = element.getAsJsonObject();

		// Get search-info from JSON object
		String searchTerms = object.get("query").getAsString();
		String nextPageUrl = object.get("next_page").getAsString();
		String refreshUrl = object.get("refresh_url").getAsString();
		int pageNumber = object.get("page").getAsInt();
		
		// Parse and get tweets from result
		LinkedList<TweetInfo323> tweets = jsonToTweets(object.get("results"), parser);

		// Create an object for results and return this object
		return new TweetSearchResultsImpl(searchTerms, nextPageUrl, refreshUrl, tweets, pageNumber);

	}

	@Override
	public TweetSearchResults searchTweetsByDate(String searchTerm, Date date) {
		// TODO Auto-generated method stub
		return null;
	};

	public static void main(String[] args) {
		TweetFactory tweetFactory = new JsonTweetFactory("https://search.twitter.com/search.json?", "https://api.twitter.com/", new RestTemplate());
		tweetFactory.searchTweets("bergen");


	}


	private LinkedList<TweetInfo323> jsonToTweets(JsonElement jsonTweets, JsonParser parser) {
		
		LinkedList<TweetInfo323> tweets = new LinkedList<TweetInfo323>();

		// Create a format to convert date string to date object
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");

		for(JsonElement tweetElement : jsonTweets.getAsJsonArray()) {
			
			JsonObject tweetObject = tweetElement.getAsJsonObject();
			long id = tweetObject.get("id").getAsLong();
			String dateString = tweetObject.get("created_at").getAsString().substring(0,25);
			Date date;
			try {
				date = formatter.parse(dateString);
			} catch (ParseException e) {
				date = new Date();
			}

			// Get related tweets
			LinkedList<Long> related = (LinkedList<Long>) getRelatedTweets(id, parser);
			

		}
		
		return tweets;

	}
	
	private List<Long> getRelatedTweets(long id, JsonParser parser) {
		
		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("Related to id: " + id);
		// Create request and store JSON result
		String relatedRequest = apiUrl + "1/related_results/show/{id}.json?include_entities=1";
		String relatedJson = restTemplate.getForObject(relatedRequest, String.class, id);
		
		
		
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement elementRelateds = parser.parse(relatedJson);
		String jsonOutput = gson.toJson(elementRelateds);
		System.out.println(jsonOutput);
		
		return null;
	}


}
