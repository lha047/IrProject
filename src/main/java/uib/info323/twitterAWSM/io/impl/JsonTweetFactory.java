package uib.info323.twitterAWSM.io.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
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
	private final SimpleDateFormat dateFormatter;
	public JsonTweetFactory(String searchApiUrl, String apiUrl, RestTemplate restTemplate) {
		this.apiUrl = apiUrl;
		this.searchApiUrl = searchApiUrl;
		this.restTemplate = restTemplate;
		dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");

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

		for(JsonElement tweetElement : jsonTweets.getAsJsonArray()) {
			
			JsonObject tweetObject = tweetElement.getAsJsonObject();
			
			long id = tweetObject.get("id").getAsLong();
			String text = tweetObject.get("text").getAsString();
			Date createdAt = parseDate(tweetObject.get("created_at").getAsString());
			String fromUser = tweetObject.get("from_user").getAsString();
			String profileImageUrl = tweetObject.get("profile_image_url").getAsString();
			long toUserId = tweetObject.get("to_user_id").getAsLong();
			long fromUserId = tweetObject.get("from_user_id").getAsLong();
			String languageCode = tweetObject.get("iso_language_code").getAsString();
			String source = tweetObject.get("source").getAsString();
			double tweetRank = 0;
			long inReplyToStatusId = 0;
			
			Twitter twitter = new TwitterTemplate();
			SearchResults tweeties = twitter.searchOperations().search("bergen");
			LinkedList<Tweet> tweetList = (LinkedList<Tweet>) tweeties.getTweets();
			Tweet tweet = tweetList.getFirst();
			
			

			// Get related tweets
			LinkedList<Long> related = (LinkedList<Long>) getRelatedTweets(id, parser);
			
			
			
			
			tweets.add(new TweetInfo323Impl(related, id, text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode, source, tweetRank, inReplyToStatusId, retweetCount, mentions, tags))


		}
		
		
//		List<Long> related, long id, String text,
//		Date createdAt, String fromUser, String profileImageUrl,
//		long toUserId, long fromUserId, String languageCode, String source,
//		double tweetRank, Long inReplyToStatusId, Integer retweetCount,
//		List<String> mentions, List<String> tags
		
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
	
	private Date parseDate(String dateString) {
		
		Date date;
		try {
			date = dateFormatter.parse(dateString.substring(0,25));
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}


}
