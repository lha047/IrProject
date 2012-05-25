package uib.info323.twitterAWSM.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.model.impl.FollowersFollowingResultPageImpl;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUserParser {


	public static List<TwitterUserInfo323> jsonToUsers(String jsonUsers) {

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonUsers);
		JsonElement jsonUsersElement = element.getAsJsonObject().get("users");

		List<TwitterUserInfo323> users = new ArrayList<TwitterUserInfo323>();
		for(JsonElement userElement : jsonUsersElement.getAsJsonArray()) {

			users.add(jsonToUser(userElement.toString()));

		}
		return users;
	}

	public static TwitterUserInfo323 jsonToUser(String jsonUser) {

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonUser);

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
		String description = "";
		if(!obj.get("description").isJsonNull()) {
			description = obj.get("description").getAsString();
		}
		String location = obj.get("location").getAsString();
		Date createdDate;
		try {
			createdDate = DateParser.parseResponseDate(element.getAsJsonObject()
					.get("created_at").getAsString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			createdDate = new Date();
		}
		int favoritesCount = obj.get("favourites_count").getAsInt();
		int followersCount = obj.get("followers_count").getAsInt();
		int friendsCount = obj.get("friends_count").getAsInt();
		String language = obj.get("lang").getAsString();

		String profileUrl = "https://twitter.com/#!/" + screenName;

		int statusesCount = obj.get("statuses_count").getAsInt();

		// TODO endre fittnessScore 0
		TwitterUserInfo323 user = new TwitterUserInfo323Impl(0, id, screenName, name, url,
				profileImageUrl, description, location, createdDate,
				favoritesCount, followersCount, friendsCount, language,
				profileUrl, statusesCount, new Date());
		return user;

	}

	public static FollowersFollowingResultPage jsonToFollowersFollowing(String screenName, String jsonFollowersFollowing) {

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonFollowersFollowing);
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
