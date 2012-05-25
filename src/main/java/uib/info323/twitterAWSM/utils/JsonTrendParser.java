package uib.info323.twitterAWSM.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.model.impl.FollowersFollowingResultPageImpl;
import uib.info323.twitterAWSM.model.impl.TrendImpl;
import uib.info323.twitterAWSM.model.impl.TrendsImpl;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.Trend;
import uib.info323.twitterAWSM.model.interfaces.Trends;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonTrendParser {


	public static Trends jsonToTrends(String jsonTrends) {

		// Parse the JSON result
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonTrends);
		JsonObject object = element.getAsJsonObject();

		// Get trend-info from JSON object
		JsonObject jsonTrendsObject = object.get("trends").getAsJsonObject();

		HashMap<Date, List<Trend>> trendsByTime = new HashMap<Date, List<Trend>>();

		for (Entry<String, JsonElement> trendsEntry : jsonTrendsObject.entrySet()) {
			Date date;
			try {
				date = DateParser.parseResponseDate(trendsEntry.getKey());
			} catch (ParseException e) {
				date = new Date();
			}
			List<Trend> trends = JsonTrendParser.jsonToTrendsList(trendsEntry.getValue());
			trendsByTime.put(date, trends);
		}
		return new TrendsImpl(trendsByTime);
	}
	
	private static List<Trend> jsonToTrendsList(JsonElement trends) {
		List<Trend> trendsList = new LinkedList<Trend>();

		for (JsonElement trendElement : trends.getAsJsonArray()) {
			JsonObject trendObject = trendElement.getAsJsonObject();
			String name = trendObject.get("name").getAsString();
			String query = trendObject.get("query").getAsString();
			trendsList.add(new TrendImpl(name, query));
		}
		return trendsList;

	}

}