package uib.info323.twitterAWSM.io.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.exceptions.BadRequestException;
import uib.info323.twitterAWSM.io.UserSearchFactory;
import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;
import uib.info323.twitterAWSM.utils.JsonUserParser;

public class JsonUserFactory implements UserSearchFactory {

	private final String apiUri;

	@Autowired
	private RestTemplate restTemplate;

	private TwitterUserInfo323 user;

	public JsonUserFactory() {
		apiUri = "https://api.twitter.com/";
		restTemplate = new RestTemplate();
	}

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

	private TwitterUserInfo323 jsonToUser(String request, String twitterUser)
			throws BadRequestException {
		String searchResult = "";
		try {

			searchResult = restTemplate.getForObject(request, String.class,
					twitterUser);
		} catch (HttpClientErrorException hcee) {
			throw new BadRequestException();
		}
		//JsonElement element = parser.parse(searchResult);

		return JsonUserParser.jsonToUser(searchResult);
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

//	private List<TwitterUserInfo323> jsonReTweetedBy(String res) {
//		JsonElement element = parser.parse(res);
//		JsonObject object = element.getAsJsonObject();
//		JsonArray array = object.getAsJsonArray();
//
//		return null;
//	}

	private FollowersFollowingResultPage findFollowersFriends(
			String screenName, String request) {
		try {
			Thread.sleep(24 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonFollowersFollowing = restTemplate.getForObject(request, String.class, screenName);
		return JsonUserParser.jsonToFollowersFollowing(screenName, jsonFollowersFollowing);
	
	}

}
