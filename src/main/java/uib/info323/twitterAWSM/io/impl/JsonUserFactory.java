package uib.info323.twitterAWSM.io.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.io.UserSearchFactory;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUserFactory implements UserSearchFactory {

	private final String apiUri;
	private final RestTemplate restTemplate;

	private TwitterUserInfo323 user;
	private SimpleDateFormat dateFormatter;

	public JsonUserFactory(String apiUrl, RestTemplate restTemplate) {
		this.apiUri = apiUrl;
		this.restTemplate = restTemplate;
		dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
	}

	@Override
	public TwitterUserInfo323 searchUserByScreenName(String screenName) {
		String request = apiUri
				+ "1/users/show.json?screen_name={screenName}&include_entities=true";
		// https://api.twitter.com/
		jsonToUser(request, screenName);

		return user;
	}

	private void jsonToUser(String request, String twitterUser) {
		String searchResult = restTemplate.getForObject(request, String.class,
				twitterUser);

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(searchResult);

		JsonObject obj = element.getAsJsonObject();

		long id = obj.get("id").getAsLong();
		String screenName = obj.get("screen_name").getAsString();
		String name = obj.get("name").getAsString();

		String url = "";
		if (!obj.get("url").isJsonNull()) {
			url = obj.get("url").getAsString();
		}

		String profileImageUrl = element.getAsJsonObject()
				.get("profile_image_url").getAsString();

		String description = obj.get("description").getAsString();
		String location = obj.get("location").getAsString();
		;
		Date createdDate = parseDate(element.getAsJsonObject()
				.get("created_at").getAsString());
		int favoritesCount = obj.get("favourites_count").getAsInt();
		int followersCount = obj.get("followers_count").getAsInt();
		int friendsCount = obj.get("friends_count").getAsInt();
		String language = obj.get("lang").getAsString();

		String profileUrl = "https://twitter.com/#!/" + screenName;

		int statusesCount = obj.get("statuses_count").getAsInt();

		// TODO endre fittnessScore 0
		user = new TwitterUserInfo323Impl(0, id, screenName, name, url,
				profileImageUrl, description, location, createdDate,
				favoritesCount, followersCount, friendsCount, language,
				profileUrl, statusesCount);

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

	// public static void main(String[] args) {
	// UserFactory uf = new JsonUserFactory("https://api.twitter.com/",
	// new RestTemplate());
	// uf.searchUserByScreenName("lisaHalvors");
	// uf.searchUserByNameId(96887286);
	// }

	@Override
	public TwitterUserInfo323 searchUserByNameId(long nameId) {
		String request = apiUri
				+ "1/users/show.json?user_id={nameId}&include_entities=true";
		// https://api.twitter.com/
		Long l = new Long(nameId);
		jsonToUser(request, l.toString());

		return user;
	}

}
