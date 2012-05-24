package uib.info323.twitterAWSM.io.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.io.UserSearchFactory;
import uib.info323.twitterAWSM.model.impl.FollowersFollowingResultPageImpl;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUserFactory implements UserSearchFactory {

	private final String apiUri;

	@Autowired
	private RestTemplate restTemplate;

	private TwitterUserInfo323 user;
	private SimpleDateFormat dateFormatter;

	@Autowired
	private JsonParser parser;

	public JsonUserFactory() {
		apiUri = "https://api.twitter.com/";
		dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		restTemplate = new RestTemplate();
		parser = new JsonParser();
	}

	// public JsonUserFactory(String apiUrl, RestTemplate restTemplate) {
	// this.apiUri = apiUrl;
	// this.restTemplate = restTemplate;
	// dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
	// parser = new JsonParser();
	// }

	@Override
	public TwitterUserInfo323 searchUserByNameId(long nameId)
			throws BadRequestException {
		String request = apiUri
				+ "1/users/show.json?user_id={nameId}&include_entities=true";
		// https://api.twitter.com/
		Long l = new Long(nameId);
		jsonToUser(request, l.toString());

		return user;
	}

	@Override
	public TwitterUserInfo323 searchUserByScreenName(String screenName) {
		String request = apiUri
				+ "1/users/show.json?screen_name={screenName}&include_entities=true";
		// https://api.twitter.com/
		jsonToUser(request, screenName);

		return user;
	}

	public List<TwitterUserInfo323> getRetweetedBy(long tweetId) {
		String searchURL = "https://api.twitter.com/1/statuses/{tweetId}/retweeted_by.json";
		String res = restTemplate
				.getForObject(searchURL, String.class, tweetId);
		System.out.println(res);
		return jsonReTweetedBy(res);
	}

	private TwitterUserInfo323 jsonToUser(String request, String twitterUser)
			throws BadRequestException {
		String searchResult = "";
		try {

			searchResult = restTemplate.getForObject(request, String.class,
					twitterUser);
		} catch (HttpClientErrorException hcee) {
			throw new BadRequestException();
		}
		JsonElement element = parser.parse(searchResult);

		return jsonElementToUser(element);

	}

	public List<TwitterUserInfo323> parseJsonToUsers(String searchResult) {

		JsonElement element = parser.parse(searchResult);
		System.out.println("Parsed element: " + element.toString());
		List<TwitterUserInfo323> users = new ArrayList<TwitterUserInfo323>();
		JsonObject jsonUsers = element.getAsJsonObject().get("users").getAsJsonObject();
		for (JsonElement userElement : jsonUsers.getAsJsonArray()) {
			users.add(jsonElementToUser(userElement));

		}

		return users;
	}

	private TwitterUserInfo323 jsonElementToUser(JsonElement userElement) {
		JsonObject obj = userElement.getAsJsonObject();

		long id = obj.get("id").getAsLong();
		String screenName = obj.get("screen_name").getAsString();
		String name = obj.get("name").getAsString();

		String url = "";
		if (!obj.get("url").isJsonNull()) {
			url = obj.get("url").getAsString();
		}

		String profileImageUrl = obj.get("profile_image_url").getAsString();

		String description = obj.get("description").getAsString();
		String location = obj.get("location").getAsString();
		;
		Date createdDate = parseDate(obj.get("created_at").getAsString());
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
				profileUrl, statusesCount, new Date());
		return user;
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

	public static void main(String[] args) {
		// UserSearchFactory uf = new
		// JsonUserFactory("https://api.twitter.com/",
		// new RestTemplate());
		UserSearchFactory uf = new JsonUserFactory();

		TwitterUserInfo323Impl t = (TwitterUserInfo323Impl) uf
				.searchUserByScreenName("HalvorsenMari");
		System.out.println(t.getId());
		FollowersFollowingResultPage f = (FollowersFollowingResultPage) uf
				.findUsersFollowers(t.getScreenName());

		FollowersFollowingResultPage f2 = (FollowersFollowingResultPage) uf
				.findUsersFriends(t.getScreenName());
		long tweetId = 0;
		uf.getRetweetedBy(tweetId);
		// TwitterUserInfo323Impl t = (TwitterUserInfo323Impl) uf
		// .searchUserByScreenName("HalvorsenMari");
		// System.out.println(t.getId());
		// FollowersFollowingResultPage f = uf.findUsersFollowers(t.getId());
		//
		// FollowersFollowingResultPage f2 = uf.findUsersFriends(t.getId());

	}

	public FollowersFollowingResultPage findUsersFriends(String screenName) {
		String request = "https://api.twitter.com/1/friends/ids.json?cursor=-1&user_id={userId}";
		FollowersFollowingResultPage resPage = findFollowersFriends(screenName,
				request);
		return resPage;
	}

	@Override
	public FollowersFollowingResultPage findUsersFollowers(String screenName) {
		String request = "https://api.twitter.com/1/followers/ids.json?cursor=-1&user_id={userId}";
		FollowersFollowingResultPage resPage = findFollowersFriends(screenName,
				request);
		return resPage;
	}

	private List<TwitterUserInfo323> jsonReTweetedBy(String res) {
		JsonElement element = parser.parse(res);
		JsonObject object = element.getAsJsonObject();
		JsonArray array = object.getAsJsonArray();

		return null;
	}

	private FollowersFollowingResultPage findFollowersFriends(
			String screenName, String request) {
		try {
			Thread.sleep(24 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = restTemplate.getForObject(request, String.class, screenName);
		JsonElement element = parser.parse(s);
		JsonObject object = element.getAsJsonObject();
		JsonArray array = object.get("").getAsJsonArray();
		String[] screenNames = new String[array.size()];
		System.out.println("number of followers" + array.size());
		for (int i = 0; i < array.size(); i++) {
			screenNames[i] = array.get(i).getAsJsonPrimitive().getAsString();
			System.out.println("ScreenName :" + i + " " + screenNames[i]);
		}

		int previous = object.get("previous_cursor").getAsInt();
		int next = -1;
		if (!object.get("next_cursor").isJsonObject()) {
			next = object.get("next_cursor").getAsInt();
		}
		FollowersFollowingResultPage resPage = new FollowersFollowingResultPageImpl();
		resPage.setFollowersScreenNames(screenNames);
		resPage.setNextCursor(next);
		resPage.setPreviousCursor(previous);
		resPage.setScreenName(screenName);
		return resPage;
	}

}
