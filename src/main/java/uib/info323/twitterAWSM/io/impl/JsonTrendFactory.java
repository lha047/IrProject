package uib.info323.twitterAWSM.io.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.io.TrendFactory;
import uib.info323.twitterAWSM.model.impl.TrendImpl;
import uib.info323.twitterAWSM.model.impl.TrendsImpl;
import uib.info323.twitterAWSM.model.interfaces.Trend;
import uib.info323.twitterAWSM.model.interfaces.Trends;
import uib.info323.twitterAWSM.utils.DateParser;
import uib.info323.twitterAWSM.utils.JsonTrendParser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonTrendFactory implements TrendFactory {

	private final String apiUrl = "https://api.twitter.com/";
	@Autowired
	private RestTemplate restTemplate;

	public JsonTrendFactory() {
		restTemplate = new RestTemplate();

	}

	public static void main(String[] args) {
		JsonTrendFactory factory = new JsonTrendFactory();
		Trends trends = factory.getDailyTrendsForDate(new Date());
		System.out.println(trends.getTrends().keySet().size());
	}

	@Override
	public Trends getDailyTrendsForDate(Date date) throws BadRequestException {

		String formattedDate;
		try {
			formattedDate = DateParser.parseRequestDate(date);
		} catch (ParseException e1) {
			formattedDate = "2012-01-01";
		}

		// Construct the REST request
		String requestUrl = apiUrl + "1/trends/daily.json?date={formattedDate}";
		String jsonResponse = "";
		// Send the request to the Twitter search API and store JSON result in
		// String
		try {
			jsonResponse = restTemplate.getForObject(requestUrl, String.class,
					formattedDate);
		} catch (HttpClientErrorException e) {
			throw new BadRequestException();
		}
		// Create an object for trends and return this object
		return JsonTrendParser.jsonToTrends(jsonResponse);
	}

//	private Trends jsonToTrends(String jsonResponse) {
//
//		// Parse the JSON result
//		JsonParser parser = new JsonParser();
//		JsonElement element = parser.parse(jsonResponse);
//		JsonObject object = element.getAsJsonObject();
//
//		// Get trend-info from JSON object
//		JsonObject jsonTrends = object.get("trends").getAsJsonObject();
//
//		HashMap<Date, List<Trend>> trendsByTime = new HashMap<Date, List<Trend>>();
//
//		for (Entry<String, JsonElement> trendsEntry : jsonTrends.entrySet()) {
//			Date date;
//			try {
//				date = DateParser.parseResponseDate(trendsEntry.getKey());
//			} catch (ParseException e) {
//				date = new Date();
//			}
//			List<Trend> trends = jsonToTrendsList(trendsEntry.getValue());
//			trendsByTime.put(date, trends);
//		}
//		return new TrendsImpl(trendsByTime);
//
//	}

//	private List<Trend> jsonToTrendsList(JsonElement trends) {
//		List<Trend> trendsList = new LinkedList<Trend>();
//
//		for (JsonElement trendElement : trends.getAsJsonArray()) {
//			JsonObject trendObject = trendElement.getAsJsonObject();
//			String name = trendObject.get("name").getAsString();
//			String query = trendObject.get("query").getAsString();
//			trendsList.add(new TrendImpl(name, query));
//		}
//		return trendsList;
//
//	}

}
