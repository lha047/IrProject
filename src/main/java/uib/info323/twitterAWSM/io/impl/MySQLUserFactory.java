package uib.info323.twitterAWSM.io.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.io.UserDAO;
import uib.info323.twitterAWSM.io.rowmapper.UserRowMapper;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public class MySQLUserFactory implements UserDAO {

	private static final String SQL_INSERT_USER = "insert into users(ID, SCREEN_NAME, NAME, URL, PROFILE_IMAGE_URL, DESCRIPTION, LOCATION, CREATED_DATE, FAVORITES_COUNT, FOLLOWERS_COUNT, FRIENDS_COUNT, LANGUAGE, PROFILE_URL, STATUSES_COUNT, FITNESS_SCORE, LAST_UPDATED) "
			+ "values(:ID, :SCREEN_NAME, :NAME, :URL, :PROFILE_IMAGE_URL, :DESCRIPTION, :LOCATION, :CREATED_DATE, :FAVORITES_COUNT, :FOLLOWERS_COUNT, :FRIENDS_COUNT, :LANGUAGE, :PROFILE_URL, :STATUSES_COUNT, :FITNESS_SCORE, :LAST_UPDATED)";

	private static final String SQL_SELECT_USER_BY_SCREEN_NAME = "SELECT * FROM users WHERE SCREEN_NAME = :SCREEN_NAME";

	private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE ID = :ID";

	private static final String SQL_UPDATE_USER = "UPDATE users	SET SCREEN_NAME=:SCREEN_NAME, NAME=:NAME, URL=:URL, PROFILE_IMAGE_URL=:PROFILE_IMAGE_URL, DESCRIPTION=:DESCRIPTION, LOCATION=:LOCATION, CREATED_DATE=:CREATED_DATE, FAVORITES_COUNT=:FAVORITES_COUNT,"
			+ " FOLLOWERS_COUNT=:FAVORITES_COUNT, FRIENDS_COUNT=:FRIENDS_COUNT, LANGUAGE=:LANGUAGE, PROFILE_URL=:PROFILE_URL, STATUSES_COUNT=:STATUSES_COUNT, FITNESS_SCORE=:FITNESS_SCORE, LAST_UPDATED=:LAST_UPDATED "
			+ "WHERE ID=:ID";

	private static final String SQL_SELECT_USERS_ID = "SELECT ID FROM users";

	private static final String SQL_INSERT_FOLLOWING = "INSERT INTO following (userId, following) values (:userId, :following)";

	private static final String SQL_INSERT_FOLLOWERS = "INSERT INTO followers (userId, follower) values (:userId, :follower)";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private DateFormat dateFormat;
	private Date date;

	private UserRowMapper userRowMapper;

	public MySQLUserFactory() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	@Autowired
	public MySQLUserFactory(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	}

	/**
	 * @param jdbcTemplate
	 */
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public TwitterUserInfo323 selectUserByScreenName(String screenName)
			throws UserNotFoundException {
		SqlParameterSource namedParameter = new MapSqlParameterSource(
				"SCREEN_NAME", screenName);
		TwitterUserInfo323Impl user = null;
		try {
			user = (TwitterUserInfo323Impl) namedParameterJdbcTemplate
					.queryForObject(SQL_SELECT_USER_BY_SCREEN_NAME,
							namedParameter, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException();
		}
		return user;
	}

	public static void main(String[] args) {

		ApplicationContext context = new FileSystemXmlApplicationContext("src"
				+ File.separator + "main" + File.separator + "webapp"
				+ File.separator + "WEB-INF" + File.separator + "spring"
				+ File.separator + "appServlet" + File.separator
				+ "db-context.xml");

		MySQLUserFactory userFactory = (MySQLUserFactory) context
				.getBean("mySqlUserFactory");

		TwitterUserInfo323Impl s = (TwitterUserInfo323Impl) userFactory
				.selectUserByScreenName("lisaHalvors");
		System.out.println(s.getId() + " " + s.getScreenName());

		List<Long> users = userFactory.findUsersFromDB();
		System.out.println(users.size());

		// TwitterUserInfo323Impl user = new TwitterUserInfo323Impl((float) 23,
		// (long) 2222, "screenName", "name", "http://url.com",
		// "profileImageUrl", "description", "location", new Date(), 12,
		// 23, 23, "No", "http://profile.url", 12, new Date());
		//
		// System.out.println(userFactory.addUser(user));
		// TwitterUserInfo323Impl t = null;
		// try {
		// t = (TwitterUserInfo323Impl) userFactory.searchUserByNameId(1111);
		// } catch (UserNotFoundException e) {
		//
		// }

		// System.out.println(userFactory.searchUserByNameId(2222).getId()
		//
		// + userFactory.searchUserByScreenName("trolloso").getScreenName());
		// TwitterUserInfo323Impl user2 = new TwitterUserInfo323Impl((float) 23,
		// (long) 2222, "øløløløløl", "TrlololololloMannen",
		// "http://url.com", "profileImageUrl", "description", "location",
		// new Date(), 12, 23, 23, "No", "http://profile.url", 12,
		// new Date());
		// userFactory.updateUser(user2);
		// System.out.println(userFactory.searchUserByNameId(2222).getId() + " "
		// + userFactory.searchUserByNameId(2222).getScreenName());

	}

	private List<Long> findUsersFromDB() {
		Map<String, Long> map = new HashMap<String, Long>();
		List<Long> list = namedParameterJdbcTemplate.queryForList(
				SQL_SELECT_USERS_ID, map, Long.class);
		return list;
	}

	public void addFollowers(FollowersFollowingResultPage f) {

		long[] followers = f.getFollowersIds();
		for (long follower : followers) {
			Map<String, Object> paramMap = followersToMap(f.getUserId(),
					follower);
			namedParameterJdbcTemplate.update(SQL_INSERT_FOLLOWERS, paramMap);
		}

	}

	private Map<String, Object> followersToMap(long userId, long follower) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("follower", follower);
		return params;
	}

	public void addFollowing(FollowersFollowingResultPage f) {
		long[] following = f.getFollowersIds();
		for (long follow : following) {
			Map<String, Object> paramMap = followingToMap(f.getUserId(), follow);
			namedParameterJdbcTemplate.update(SQL_INSERT_FOLLOWING, paramMap);
		}
	}

	private Map<String, Object> followingToMap(long userId, long following) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("userId", userId);
		params.put("following", following);

		return params;
	}

	@Override
	public TwitterUserInfo323 selectUserById(long id)
			throws UserNotFoundException {

		SqlParameterSource namedParameter = new MapSqlParameterSource("ID", id);
		TwitterUserInfo323Impl user = null;
		try {
			user = (TwitterUserInfo323Impl) namedParameterJdbcTemplate
					.queryForObject(SQL_SELECT_USER_BY_ID, namedParameter,
							new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException();
		}

		return user;
	}

	@Override
	public boolean updateUser(TwitterUserInfo323 user) {
		Map<String, Object> params = userToMap(user);
		namedParameterJdbcTemplate.update(SQL_UPDATE_USER, params);
		return false;
	}

	@Override
	public boolean addUser(TwitterUserInfo323 user) {
		Map<String, Object> params = userToMap(user);
		int inserted = -1;
		try {
			inserted = namedParameterJdbcTemplate.update(SQL_INSERT_USER,
					params);
		} catch (Exception e) {
			// TODO existing entety
		}
		return inserted == 1;
	}

	private Map<String, Object> userToMap(TwitterUserInfo323 user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ID", user.getId());
		params.put("SCREEN_NAME", user.getScreenName());
		params.put("NAME", user.getName());
		params.put("URL", user.getUrl());
		params.put("PROFILE_IMAGE_URL", user.getProfileImageUrl());
		params.put("DESCRIPTION", user.getDescription());
		params.put("LOCATION", user.getLocation());
		params.put("CREATED_DATE", user.getCreatedDate());
		params.put("FAVORITES_COUNT", user.getFavoritesCount());
		params.put("FOLLOWERS_COUNT", user.getFollowersCount());
		params.put("FRIENDS_COUNT", user.getFriendsCount());
		params.put("LANGUAGE", user.getLanguage());
		params.put("PROFILE_URL", user.getProfileUrl());
		params.put("STATUSES_COUNT", user.getStatusesCount());
		params.put("FITNESS_SCORE", user.getFitnessScore());
		date = new Date();
		params.put("LAST_UPDATED", dateFormat.format(date));
		return params;
	}

	@Override
	public boolean addListUsers(List<TwitterUserInfo323> users) {
		// TODO Auto-generated method stub
		return false;
	}

}
