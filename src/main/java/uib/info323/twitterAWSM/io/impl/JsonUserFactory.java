package uib.info323.twitterAWSM.io.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.client.RestTemplate;

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
	private final RestTemplate restTemplate;

	private TwitterUserInfo323 user;
	private SimpleDateFormat dateFormatter;
	private JsonParser parser;

	public JsonUserFactory(String apiUrl, RestTemplate restTemplate) {
		this.apiUri = apiUrl;
		this.restTemplate = restTemplate;
		dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		parser = new JsonParser();
	}

	@Override
	public TwitterUserInfo323 searchUserByScreenName(String screenName) {
		String request = apiUri
				+ "1/users/show.json?screen_name={screenName}&include_entities=true";
		// https://api.twitter.com/
		jsonToUser(request, screenName);

		return user;
	}

	private TwitterUserInfo323 jsonToUser(String request, String twitterUser) {
		String searchResult = restTemplate.getForObject(request, String.class,
				twitterUser);

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
		UserSearchFactory uf = new JsonUserFactory("https://api.twitter.com/",
				new RestTemplate());
		TwitterUserInfo323Impl t = (TwitterUserInfo323Impl) uf
				.searchUserByScreenName("HalvorsenMari");
		System.out.println(t.getId());
		FollowersFollowingResultPage f = uf.findUsersFollowers(t.getId());

		FollowersFollowingResultPage f2 = uf.findUsersFriends(t.getId());

	}

	@Override
	public TwitterUserInfo323 searchUserByNameId(long nameId) {
		String request = apiUri
				+ "1/users/show.json?user_id={nameId}&include_entities=true";
		// https://api.twitter.com/
		Long l = new Long(nameId);
		jsonToUser(request, l.toString());

		return user;
	}

	public FollowersFollowingResultPage findUsersFriends(long userId) {
		String request = "https://api.twitter.com/1/friends/ids.json?cursor=-1&user_id={userId}";
		FollowersFollowingResultPage resPage = findFollowersFriends(userId,
				request);
		return resPage;
	}

	@Override
	public FollowersFollowingResultPage findUsersFollowers(long userId) {
		String request = "https://api.twitter.com/1/followers/ids.json?cursor=-1&user_id={userId}";
		FollowersFollowingResultPage resPage = findFollowersFriends(userId,
				request);
		return resPage;
	}

	private FollowersFollowingResultPage findFollowersFriends(long userId,
			String request) {
		try {
			Thread.sleep(24 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = restTemplate.getForObject(request, String.class, userId);
		JsonElement element = parser.parse(s);
		JsonObject object = element.getAsJsonObject();
		JsonArray array = object.get("ids").getAsJsonArray();
		long[] userIds = new long[array.size()];
		System.out.println("number of followers" + array.size());
		for (int i = 0; i < array.size(); i++) {
			userIds[i] = array.get(i).getAsJsonPrimitive().getAsLong();
			System.out.println("followersId :" + i + " " + userIds[i]);
		}

		int previous = object.get("previous_cursor").getAsInt();
		int next = -1;
		if (!object.get("next_cursor").isJsonObject()) {
			next = object.get("next_cursor").getAsInt();
		}
		FollowersFollowingResultPage resPage = new FollowersFollowingResultPageImpl();
		resPage.setFollowersIds(userIds);
		resPage.setNextCursor(next);
		resPage.setPreviousCursor(previous);
		return resPage;
	}

}
