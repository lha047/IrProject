package uib.info323.twitterAWSM.io.impl;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.io.TrendFactory;
import uib.info323.twitterAWSM.model.interfaces.Trends;
import uib.info323.twitterAWSM.utils.DateParser;
import uib.info323.twitterAWSM.utils.JsonTrendParser;

public class JsonTrendFactory implements TrendFactory {

	private final String apiUrl = "https://api.twitter.com/";
	@Autowired
	private RestTemplate restTemplate;

	public JsonTrendFactory() {
		restTemplate = new RestTemplate();

	}

	public static void main(String[] args) {
		JsonTrendFactory factory = new JsonTrendFactory();
		Trends trends = JsonTrendParser.jsonToTrends(factory
				.getDailyTrendsForDate(new Date()));
		System.out.println(trends.getTrends().keySet().size());
	}

	@Override
	public String getDailyTrendsForDate(Date date) throws BadRequestException {

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
		return jsonResponse;
	}

}
