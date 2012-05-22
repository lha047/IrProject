package uib.info323.twitterAWSM.io.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.io.UserDAO;
import uib.info323.twitterAWSM.io.UserSearchFactory;
import uib.info323.twitterAWSM.model.impl.FollowersFollowingResultPageImpl;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

@Component
public class HttpUserFactory implements UserSearchFactory, UserDAO {

	private RestTemplate restTemplate;
	private final String twitterUrl = "http://mobile.twitter.com/";

	public HttpUserFactory() {
		restTemplate = new RestTemplate();
	}

	
	@Override
	public TwitterUserInfo323 searchUserByScreenName(String screenNameInput) {

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String query = twitterUrl + "{screenNameInput}";
		String html = restTemplate.getForObject(query, String.class, screenNameInput);

		Document doc = Jsoup.parse(html);

		float fitnessScore = -1;
		long id = -1;
		String screenName = doc.select(".username").eq(0).text().substring(1);
		String url = doc.select(".url").eq(0).text();
		String name = doc.select(".fullname").eq(0).text();
		String profileImageUrl = doc.select("img[alt="+name+"]").attr("src");
		String description = doc.select(".bio").eq(0).text();
		String location = doc.select(".location").eq(0).text();
		Date createdDate = new Date();
		int favoritesCount = 0;
		int followersCount = Integer.parseInt(doc.select(".statnum").eq(2).text().replaceAll("[^0-9-]", ""));
		int friendsCount = Integer.parseInt(doc.select(".statnum").eq(1).text().replaceAll("[^0-9-]", ""));
		String language = "en";
		String profileUrl = "http://www.twitter.com/#!/" + screenName;
		int statusesCount = Integer.parseInt(doc.select(".statnum").eq(0).text().replaceAll("[^0-9-]", ""));
		Date lastUpdated = createdDate;

		TwitterUserInfo323 user = new TwitterUserInfo323Impl(
				fitnessScore, 
				id, 
				screenName, 
				name, 
				url, 
				profileImageUrl, 
				description, 
				location, 
				createdDate, 
				favoritesCount, 
				followersCount, 
				friendsCount, 
				language, 
				profileUrl, 
				statusesCount, 
				lastUpdated);
		return user;
	}

	public FollowersFollowingResultPage findUsersFollowers(String screenNameInput) {

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String query = twitterUrl + "{screenNameInput}/followers";
		String html = restTemplate.getForObject(query, String.class, screenNameInput);

		Document doc = Jsoup.parse(html);
		FollowersFollowingResultPage followers = new FollowersFollowingResultPageImpl();
		followers.setScreenName(screenNameInput);
		//System.out.println(doc);
		Elements usernameElements = doc.select(".username");
		System.out.println(usernameElements.first().text());
		System.out.println("Size of elements: " + usernameElements.size());
		String[] followersArray = new String[usernameElements.size()];
		int i = 0;
		for(Element element : usernameElements) {
			System.out.println(element.text());
			followersArray[i] = element.text().substring(1);
			i++;
		}
		
		followers.setFollowersScreenNames(followersArray);
		return followers;
	}

	public FollowersFollowingResultPage findUsersFriends(String screenNameInput) {

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String query = twitterUrl + "{screenNameInput}/following";
		String html = restTemplate.getForObject(query, String.class, screenNameInput);

		Document doc = Jsoup.parse(html);

		FollowersFollowingResultPage followings = new FollowersFollowingResultPageImpl();
		followings.setScreenName(screenNameInput);
		
		Elements usernameElements = doc.select(".username:gt(0)");
		String[] followingsArray = new String[usernameElements.size()];
		int i = 0;
		for(Element element : usernameElements) {
			followingsArray[i++] = element.text().substring(1);
		}
		
		followings.setFollowersScreenNames(followingsArray);
		return followings;
	}

	@Override
	public TwitterUserInfo323 searchUserByNameId(long nameId)
			throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUser(TwitterUserInfo323 user) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean updateUser(TwitterUserInfo323 user) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public TwitterUserInfo323 selectUserById(long userId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TwitterUserInfo323 selectUserByScreenName(String screenName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean addListUsers(List<TwitterUserInfo323> users) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<TwitterUserInfo323> getRetweetedBy(long tweetId) {
		// TODO Auto-generated method stub
		return null;
	}
}
