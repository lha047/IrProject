package uib.info323.twitterAWSM.io.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.io.UserDAO;
import uib.info323.twitterAWSM.io.UserSearchFactory;
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

	private static final String SQL_SELECT_ALL_FOLLOWERS = "SELECT followerId FROM followers";

	private static final String SQL_INSERT_FOLLOWING = "INSERT IGNORE INTO following (userId, following) values (:userId, :following)";

	private static final String SQL_INSERT_FOLLOWERS = "INSERT IGNORE INTO followers (userId, followerId) values (:userId, :followerId)";

	private static final String SELECT_FOLLOWERS_BY_ID = "SELECT followerId FROM followers WHERE userId = :userId";

	private static final String SELECT_FOLLOWING_BY_ID = "SELECT following FROM following WHERE userId = :userId";

	// Correct logger...
	private static Logger logger = LoggerFactory
			.getLogger(MySQLUserFactory.class);
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

		// TwitterUserInfo323Impl s = (TwitterUserInfo323Impl) userFactory
		// .selectUserByScreenName("lisaHalvors");
		// System.out.println(s.getId() + " " + s.getScreenName());

		// List<Long> users =
		// userFactory.selectAllIdsFromDB(SQL_SELECT_USERS_ID);
		// System.out.println(users.size());

		UserSearchFactory uf = new JsonUserFactory("https://api.twitter.com/",
				new RestTemplate());
		List<Long> followers = userFactory
				.selectAllIdsFromDB(SQL_SELECT_ALL_FOLLOWERS);
		System.out.println(followers.size());
		int START = 393;
		int STOP = followers.size();
		long millis = 30 * 1000;
		for (int i = START; i < STOP; i++) {

			TwitterUserInfo323 tu = uf.searchUserByNameId(followers.get(i));
			System.out.println(i + " user " + tu.getId());
			userFactory.addUser(tu);

			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// UserRank userRank = new UserRank("https://api.twitter.com/",
		// new RestTemplate());

		int STOPP = 1;
		// for (int i = 0; i < STOPP; i++) {
		// System.out.println("Rank Users " + users.get(i));
		// double d = userRank.userRank(userFactory.selectUserByScreenName(
		// "lisaHalvors").getId());
		// System.out.println("Rank " + d);
		// TwitterUserInfo323 user = userFactory.selectUserById(users.get(i));
		// user.setFitnessScore((float) d);
		// userFactory.updateUser(user);
		// }

		// To test selectFollowersByUserId(id);
		// long id = 1;
		// List<Long> followers = userFactory.selectFollowersByUserId(id );
		// for (Long l : followers) {
		// System.out.println(l);
		// }

		// To run insert follower following
		// int TO_NUMBER = users.size();
		// FollowersFollowingResultPage[] l = new
		// FollowersFollowingResultPage[TO_NUMBER];
		// FollowersFollowingResultPage[] l2 = new
		// FollowersFollowingResultPage[TO_NUMBER];
		//
		// for (int i = 0; i < TO_NUMBER; i++) {
		// System.out.println("Teller " + i);
		// System.out.println("*******Twitter " + users.get(i) + "*********");
		// FollowersFollowingResultPage f = uf
		// .findUsersFollowers(users.get(i));
		// FollowersFollowingResultPage f2 = uf.findUsersFriends(users.get(i));
		//
		// System.out.println("*******DB " + f.getUserId() + " "
		// + f2.getUserId() + "*********");
		// userFactory.addFollowers(f);
		// userFactory.addFollowing(f2);
		// try {
		// Thread.sleep(1000 * 20);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

	}

	private List<Long> selectAllIdsFromDB(String SQL) {
		Map<String, Long> map = new HashMap<String, Long>();
		List<Long> list = namedParameterJdbcTemplate.queryForList(SQL, map,
				Long.class);
		return list;
	}

	public List<Long> selectFollowersByUserId(long userId) {

		SqlParameterSource parameter = new MapSqlParameterSource("userId",
				userId);
		List<Long> list = namedParameterJdbcTemplate.queryForList(
				SELECT_FOLLOWERS_BY_ID, parameter, Long.class);
		return list;
	}

	public List<Long> selectFollowingByUserId(long userId) {

		SqlParameterSource parameter = new MapSqlParameterSource("userId",
				userId);
		List<Long> list = namedParameterJdbcTemplate.queryForList(
				SELECT_FOLLOWING_BY_ID, parameter, Long.class);
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
		params.put("followerId", follower);
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
