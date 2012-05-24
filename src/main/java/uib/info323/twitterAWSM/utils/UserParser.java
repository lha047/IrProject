package uib.info323.twitterAWSM.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserParser {

	
	public static List<TwitterUserInfo323> jsonToUsers(String jsonUsers) {
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonUsers);
		
		List<TwitterUserInfo323> users = new ArrayList<TwitterUserInfo323>();
		for(JsonElement userElement : element.getAsJsonArray()) {
			
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

		String description = obj.get("description").getAsString();
		String location = obj.get("location").getAsString();
		;
		Date createdDate;
		try {
			createdDate = DateParser.parseDate(element.getAsJsonObject()
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

}
