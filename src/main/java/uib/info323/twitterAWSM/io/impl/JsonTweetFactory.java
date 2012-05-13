package uib.info323.twitterAWSM.io.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.io.TweetSearchFactory;
import uib.info323.twitterAWSM.model.impl.TweetInfo323Impl;
import uib.info323.twitterAWSM.model.impl.TweetSearchResultsImpl;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;
import uib.info323.twitterAWSM.utils.TweetParser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonTweetFactory implements TweetSearchFactory {

	private final String searchApiUrl;
	private final String apiUrl;
	private final RestTemplate restTemplate;
	private final SimpleDateFormat dateFormatter;

	public JsonTweetFactory(String searchApiUrl, String apiUrl,
			RestTemplate restTemplate) {
		this.apiUrl = apiUrl;
		this.searchApiUrl = searchApiUrl;
		this.restTemplate = restTemplate;
		dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");

	}

	@Override
	public TweetSearchResults searchTweets(String searchTerm, int resultsPerPage) {

		// Construct the REST request
		String requestUrl = searchApiUrl
				+ "q={searchTerm}&rpp={resultsPerPage}";
		// Send the request to the Twitter search API and store JSON result in
		// String
		try {
			String searchResults = restTemplate.getForObject(requestUrl,
					String.class, searchTerm, resultsPerPage);
			// Create an object for results and return this object
			return jsonToSearchResults(searchResults);
		} catch (HttpClientErrorException e) {
			throw new BadRequestException();
		}

	}

	@Override
	public TweetSearchResults searchTweetsByDate(String searchTerm, Date date) {
		// TODO Auto-generated method stub
		return null;
	};

	public static void main(String[] args) {
		TweetSearchFactory tweetFactory = new JsonTweetFactory(
				"https://search.twitter.com/search.json?",
				"https://api.twitter.com/", new RestTemplate());
		tweetFactory.searchTweets("bergen", 19);

	}

	private LinkedList<TweetInfo323> jsonToTweets(JsonElement jsonTweets,
			JsonParser parser) {

		LinkedList<TweetInfo323> tweets = new LinkedList<TweetInfo323>();

		for (JsonElement tweetElement : jsonTweets.getAsJsonArray()) {

			JsonObject tweetObject = tweetElement.getAsJsonObject();

			long id = tweetObject.get("id").getAsLong();
			String text = tweetObject.get("text").getAsString();
			Date createdAt = parseDate(tweetObject.get("created_at")
					.getAsString());
			String fromUser = tweetObject.get("from_user").getAsString();
			String profileImageUrl = tweetObject.get("profile_image_url")
					.getAsString();

			long fromUserId = tweetObject.get("from_user_id").getAsLong();
			String languageCode = tweetObject.get("iso_language_code")
					.getAsString();
			String source = tweetObject.get("source").getAsString();
			double tweetRank = 0;
			long inReplyToStatusId = 0;
			int retweetCount = 0;
			long toUserId = 0;

			// If tweet has a to user id element....
			JsonElement toUserIdElement = tweetObject.get("to_user_id");

			if (!toUserIdElement.isJsonNull()) {
				toUserId = toUserIdElement.getAsLong();
			}

			// Make sure retweet count actually exist
			JsonElement retweetElement = tweetObject.get("metadata")
					.getAsJsonObject().get("recent_retweets");
			if (retweetElement != null) {
				retweetCount = retweetElement.getAsInt();
			}

			ArrayList<String> tags = (ArrayList<String>) TweetParser.getTerms(
					text, "#".charAt(0));
			ArrayList<String> mentions = (ArrayList<String>) TweetParser
					.getTerms(text, "@".charAt(0));

			// Get related tweets
			LinkedList<Long> related = (LinkedList<Long>) getRelatedTweets(id,
					parser);

			TwitterUserInfo323Impl userInfo = null;

			tweets.add(new TweetInfo323Impl(related, id, text, createdAt,
					fromUser, profileImageUrl, toUserId, fromUserId,
					languageCode, source, tweetRank, inReplyToStatusId,
					retweetCount, mentions, tags, userInfo));
		}

		return tweets;
	}

	@Override
	public TweetSearchResults getNextPage(String query, int rpp, int page,
			long maxId) {
		// Construct the REST request
		String nextPageUrl = "?page=" + page + "&max_id=" + maxId + "&q="
				+ query + "&rpp=" + rpp;
		String requestUrl = searchApiUrl + nextPageUrl;

		try {
			String searchResults = restTemplate.getForObject(requestUrl,
					String.class);
			// Create an object for results and return this object
			return jsonToSearchResults(searchResults);
		} catch (HttpClientErrorException e) {
			throw new BadRequestException();
		}

	}

	private TweetSearchResults jsonToSearchResults(String searchResults) {

		// Parse the JSON result
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(searchResults);
		JsonObject object = element.getAsJsonObject();

		// Get search-info from JSON object
		String searchTerms = object.get("query").getAsString();
		String nextPageUrl = null;
		if (object.get("next_page") != null) {
			nextPageUrl = object.get("next_page").getAsString();
		}
		String refreshUrl = object.get("refresh_url").getAsString();
		int pageNumber = object.get("page").getAsInt();

		// Parse and get tweets from result
		LinkedList<TweetInfo323> tweets = jsonToTweets(object.get("results"),
				parser);

		return new TweetSearchResultsImpl(searchTerms, nextPageUrl, refreshUrl,
				tweets, pageNumber);

	}

	private List<Long> getRelatedTweets(long id, JsonParser parser) {

		return new LinkedList<Long>();
	}

	private Date parseDate(String dateString) {

		Date date;
		try {
			date = dateFormatter.parse(dateString.substring(0, 25));
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}
}
