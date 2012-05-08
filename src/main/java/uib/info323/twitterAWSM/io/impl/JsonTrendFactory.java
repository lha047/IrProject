package uib.info323.twitterAWSM.io.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import uib.info323.twitterAWSM.io.TrendFactory;
import uib.info323.twitterAWSM.model.impl.TrendImpl;
import uib.info323.twitterAWSM.model.impl.TrendsImpl;
import uib.info323.twitterAWSM.model.interfaces.Trend;
import uib.info323.twitterAWSM.model.interfaces.Trends;

public class JsonTrendFactory implements TrendFactory {

	private final String apiUrl;
	private final RestTemplate restTemplate;
	private final SimpleDateFormat requestDateFormatter;
	private final SimpleDateFormat responseDateFormatter;

	public JsonTrendFactory(String apiUrl, RestTemplate restTemplate) {
		this.apiUrl = apiUrl;
		this.restTemplate = restTemplate;
		requestDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		responseDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}

	public static void main(String[] args) {
		JsonTrendFactory factory = new JsonTrendFactory("https://api.twitter.com/", new RestTemplate());
		Trends trends = factory.getDailyTrendsForDate(new Date());
		System.out.println(trends.getTrends().keySet().size());
	}
	
	@Override
	public Trends getDailyTrendsForDate(Date date) {

		String formattedDate = requestDateFormatter.format(date);

		// Construct the REST request
		String requestUrl = apiUrl
				+ "1/trends/daily.json?date={formattedDate}";
		// Send the request to the Twitter search API and store JSON result in
		// String
		String jsonResponse = restTemplate.getForObject(requestUrl,
				String.class, formattedDate);

		// Create an object for trends and return this object
		return jsonToTrends(jsonResponse);
	}

	private Trends jsonToTrends(String jsonResponse) {

		// Parse the JSON result
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonResponse);
		JsonObject object = element.getAsJsonObject();

		// Get trend-info from JSON object
		JsonObject jsonTrends = object.get("trends").getAsJsonObject();
		
		HashMap<Date, List<Trend>> trendsByTime = new HashMap<Date, List<Trend>>();
				
		for (Entry<String, JsonElement> trendsEntry : jsonTrends.entrySet()) {
			Date date;
			try {
				date = responseDateFormatter.parse(trendsEntry.getKey());
			} catch (ParseException e) {
				date = new Date();
			}
			List<Trend> trends = jsonToTrendsList(trendsEntry.getValue());
			trendsByTime.put(date, trends);
		}
		return new TrendsImpl(trendsByTime);

	}

	private List<Trend> jsonToTrendsList(JsonElement trends) {
		List<Trend> trendsList = new LinkedList<Trend>();
		
		for(JsonElement trendElement : trends.getAsJsonArray()) {
			JsonObject trendObject = trendElement.getAsJsonObject();
			String name = trendObject.get("name").getAsString();
			String query = trendObject.get("query").getAsString();
			trendsList.add(new TrendImpl(name, query));
		}
		return trendsList;
		
	}



}
