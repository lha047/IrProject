package uib.info323.twitterAWSM.io.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.io.UserDAO;
import uib.info323.twitterAWSM.io.rowmapper.UserRowMapper;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;
import uib.info323.twitterAWSM.pagerank.UserRank;

@Component
public class MySQLUserFactory implements UserDAO {

	public static final String SQL_INSERT_USER = "insert ignore into users(id, screen_name, name, url, profile_image_url, description, location, created_date, favorites_count, followers_count, friends_count, language, profile_url, statuses_count, fitness_score, last_updated) "

			+ "values(:id, :screen_name, :name, :url, :profile_image_url, :description, :location, :created_date, :favorites_count, :followers_count, :friends_count, :language, :profile_url, :statuses_count, :fitness_score, :last_updated)";

	private static final String SQL_SELECT_USER_BY_SCREEN_NAME = "SELECT * FROM users WHERE screen_name = :screen_name";

	private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = :id";

	private static final String SQL_UPDATE_USER = "UPDATE users	SET screen_name=:screen_name, name=:name, url=:url, profile_image_url=:profile_image_url, description=:description, location=:location, created_date=:created_date, favorites_count=:favorites_count,"
			+ " followers_count=:favorites_count, friends_count=:friends_count, language=:language, profile_url=:profile_url, statuses_count=:statuses_count, fitness_score=:fitness_score, last_updated=:last_updated "
			+ "WHERE ID=:ID";

	private static final String SQL_UPDATE_FOLLOWERS_WITH_USER_ID = "UPDATE followers SET user_id=:user_id WHERE screen_name=:screen_name";

	private static final String SQL_UPDATE_FOLLOWING_WITH_USER_ID = "UPDATE following SET user_id=:user_id WHERE screen_name=:screen_name";

	// private static final String SQL_SELECT_SCREEN_NAME =
	// "SELECT screen_name FROM users";

	private static final String SQL_SELECT_ALL_FOLLOWERS = "SELECT follower_id FROM followers";

	public static final String SQL_INSERT_FOLLOWING = "INSERT IGNORE INTO following (user_id, following_id) values (:user_id, :following_id)";

	public static final String SQL_INSERT_FOLLOWERS = "INSERT IGNORE INTO followers (user_id, follower_id) values (:user_id, :follower_id)";

	private static final String SELECT_FOLLOWERS_BY_ID = "SELECT followerId FROM followers WHERE userId = :userId";

	private static final String SELECT_FOLLOWING_BY_ID = "SELECT followingId FROM following WHERE userId = :userId";

	// Correct logger...
	private static Logger logger = LoggerFactory
			.getLogger(MySQLUserFactory.class);

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	private Connection con;
	private DateFormat dateFormat;
	private Date date;

	// private UserRowMapper userRowMapper;

	public MySQLUserFactory() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	}

	//
	// public MySQLUserFactory(
	// NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
	// this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	// dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//
	// }

	/**
	 * @param namedParameterJdbcTemplate
	 */
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public TwitterUserInfo323 selectUserById(long id)
			throws UserNotFoundException {

		// PreparedStatement ps = c
		SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
		TwitterUserInfo323Impl user = null;
		try {
			user = (TwitterUserInfo323Impl) jdbcTemplate.queryForObject(
					SQL_SELECT_USER_BY_ID, namedParameter, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException();
		}

		return user;
	}

	@Override
	public TwitterUserInfo323 selectUserByScreenName(String screenName)
			throws UserNotFoundException {
		SqlParameterSource namedParameter = new MapSqlParameterSource(
				"screen_name", screenName);
		TwitterUserInfo323Impl user = null;
		try {
			user = (TwitterUserInfo323Impl) jdbcTemplate.queryForObject(
					SQL_SELECT_USER_BY_SCREEN_NAME, namedParameter,
					new UserRowMapper());
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

		userFactory.createUserRankForUsers(userFactory);
		// userFactory.insertUserIdsToFollowersFollowing(
		// "SELECT DISTINCT  screen_name FROM following",
		// SQL_UPDATE_FOLLOWING_WITH_USER_ID);

	}

	private List<String> selectAllScreenNamesFromDB(String SQL) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> list = jdbcTemplate.queryForList(SQL, map, String.class);

		return list;
	}

	private Map<String, Object> followersWithIdToMap(String screenName, long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("screen_name", screenName);
		params.put("user_id", id);
		return params;
	}

	// public int newInsertBatchFollowing(FollowersFollowingResultPage f) {
	// StringBuilder sb = new StringBuilder();
	// sb.append("INSERT IGNORE INTO following (user_id, following_id) values ");
	//
	// return exec(f, sb);
	// }

	public int newInsertBatchFollowers(FollowersFollowingResultPage f) {

		// try {
		// PreparedStatement stmt = (PreparedStatement) con
		// .prepareStatement(SQL_INSERT_FOLLOWERS);
		// return exec(f, stmt);
		// } catch (SQLException e) {
		// return -1;
		// }

		StringBuilder sb = new StringBuilder();

		sb.append("INSERT IGNORE INTO followers (user_id, follower_id) values ");

		return exec(f, sb);
	}

	public int exec(FollowersFollowingResultPage f, StringBuilder sb) {
		long[] ids = f.getFollowersUserIds();

		for (int i = 0; i < ids.length; i++) {

			sb.append("(" + f.getUserId() + ", " + ids[i] + "),");
		}
		sb.deleteCharAt(sb.toString().length() - 1);
		sb.append(";");
		System.out.println(sb.toString());
		String url = "jdbc:mysql://feedjam.thunemedia.no/feedjam";
		String user = "bobkaare";
		String password = "info323";

		Connection con = null;
		int inserted = -1;
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = con.createStatement();
			inserted = stmt.executeUpdate(sb.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inserted;
	}

	public int insertBatchFollowers(FollowersFollowingResultPage f) {

		long[] followers = f.getFollowersUserIds();
		System.out.println("Number of followers: " + followers.length);

		// Build INSERT SQL statement
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT IGNORE INTO followers (user_id, follower_id) values ");
		for (int i = 0; i < followers.length; i++) {
			sb.append("(:user_id" + i + ", :follower_id" + i + "),");
		}
		sb.deleteCharAt(sb.length() - 1);

		long start = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < followers.length; i++) {
			map.put("user_id" + i, f.getUserId());
			map.put("follower_id" + i, followers[i]);
		}

		long timeToMap = System.currentTimeMillis() - start;
		System.out.println("Time for followers to map? " + timeToMap);

		start = System.currentTimeMillis();
		int updated = jdbcTemplate.update(sb.toString(), map);
		long timeToInsert = System.currentTimeMillis() - start;
		System.out.println("Time to insert? " + timeToInsert);
		return updated;
	}

	public int insertBatchFollowing(FollowersFollowingResultPage f) {

		long[] following = f.getFollowersUserIds();

		// Build INSERT SQL statement
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT IGNORE INTO following (user_id, following_id) values ");
		for (int i = 0; i < following.length; i++) {
			sb.append("(:user_id" + i + ", :following_id" + i + "),");
		}
		sb.deleteCharAt(sb.length() - 1);

		long start = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < following.length; i++) {
			map.put("user_id" + i, f.getUserId());
			map.put("following_id" + i, following[i]);
		}

		long timeToMap = System.currentTimeMillis() - start;
		System.out.println("Time for following to map? " + timeToMap);

		start = System.currentTimeMillis();
		int updated = jdbcTemplate.update(sb.toString(), map);
		long timeToInsert = System.currentTimeMillis() - start;
		System.out.println("Time to insert? " + timeToInsert);
		return updated;
	}

	// public void addFollowing(FollowersFollowingResultPage f) {
	// long[] following = f.getFollowersUserIds();
	// for (long follow ? following) {
	// Map<String, Object> paramMap = followingToMap(f.getUserId(), follow);
	// jdbcTemplate.update(SQL_INSERT_FOLLOWING, paramMap);
	// }
	// }

	private Map<String, Object> followerToMap(long userId, long followerId,
			int i) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id" + i, userId);
		params.put("follower_id" + i, followerId);
		return params;
	}

	private Map<String, Object> followingToMap(long userId, long followingId) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("user_id", userId);
		params.put("following_id", followingId);

		return params;
	}

	@Override
	public boolean updateUser(TwitterUserInfo323 user) {
		Map<String, Object> params = userToMap(user);
		jdbcTemplate.update(SQL_UPDATE_USER, params);
		return false;
	}

	@Override
	public boolean addUser(TwitterUserInfo323 user) {
		Map<String, Object> params = userToMap(user);
		int inserted = -1;
		try {
			inserted = jdbcTemplate.update(SQL_INSERT_USER, params);
		} catch (Exception e) {
			// TODO existing entety
		}
		return inserted == 1;
	}

	private Map<String, Object> userToMap(TwitterUserInfo323 user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("screen_name", user.getScreenName());
		params.put("name", user.getName());
		params.put("url", user.getUrl());
		params.put("profile_image_url", user.getProfileImageUrl());
		params.put("description", user.getDescription());
		params.put("location", user.getLocation());
		params.put("created_date", user.getCreatedDate());
		params.put("favorites_count", user.getFavoritesCount());
		params.put("followers_count", user.getFollowersCount());
		params.put("friends_count", user.getFriendsCount());
		params.put("language", user.getLanguage());
		params.put("profile_url", user.getProfileUrl());
		params.put("statuses_count", user.getStatusesCount());
		params.put("fitness_score", user.getFitnessScore());
		date = new Date();
		params.put("last_updated", dateFormat.format(date));
		return params;
	}

	public int addBatchUsers(List<TwitterUserInfo323> users, String sql) {
		List<SqlParameterSource> parameters = new ArrayList<SqlParameterSource>();
		for (TwitterUserInfo323 tu : users) {
			Map<String, Object> map = userToMap(tu);
			parameters.add(new MapSqlParameterSource(map));
		}
		int[] updated = jdbcTemplate.batchUpdate(sql,
				parameters.toArray(new SqlParameterSource[0]));
		return updated.length;
	}

	@Override
	public List<Long> selectFollowersByUserId(long userId) {
		SqlParameterSource parameter = new MapSqlParameterSource("userId",
				userId);
		System.out.println();
		List<Long> list = jdbcTemplate.queryForList(SELECT_FOLLOWERS_BY_ID,
				parameter, Long.class);
		return list;
	}

	@Override
	public List<Long> selectFollowingByUserId(long userId) {
		SqlParameterSource parameter = new MapSqlParameterSource("userId",
				userId);
		List<Long> list = jdbcTemplate.queryForList(SELECT_FOLLOWING_BY_ID,
				parameter, Long.class);
		return list;
	}

	/**
	 * Checks if the tweeters of the tweets in the searchResult exists in DB.
	 * 
	 * @param searchResult
	 * @return list of users not in DB.
	 */
	public List<Long> checkIfUsersExistsInDB(TweetSearchResults searchResult) {
		List<TweetInfo323> tweets = searchResult.getTweets();
		List<Long> usersNotInDB = new ArrayList<Long>();
		for (TweetInfo323 t : tweets) {
			try {
				TwitterUserInfo323 user = selectUserById(t.getFromUserId());

			} catch (UserNotFoundException unfe) {

				usersNotInDB.add(t.getFromUserId());
			}
		}
		return usersNotInDB;
	}

	public void createUserRankForUsers(MySQLUserFactory f) {
		UserRank ur = new UserRank(f);
		// int[] followers = selectFollowersByUserId(userId);
		// int[] following = selectFollowingByUserId(userId);
		List<Long> distinctFollowersUserIds = selectDistinctUserIdsFrom("followers");
		System.out.println("distinct users with followers in db "
				+ distinctFollowersUserIds.size());
		String sql = "UPDATE `users` SET `fitness_score`= 1 WHERE id = ?";
		// long u = distinctFollowersUserIds.get(0);
		long u = 594326498;
		long u2 = 12720;
		// for (Long user ? distinctFollowersUserIds) {
		double userRank = ur.userRank(u);
		double userRank2 = ur.userRank(u2);
		System.out.println("user " + u + " rank " + userRank);
		System.out.println("user " + u2 + " rank " + userRank2);
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("id", u);
		// map.put("fitness_score", userRank);
		// int updated = jdbcTemplate.update(sql, map);
		// System.out.println("user " + u + " rank " + userRank + " update "
		// + updated);
		// }
	}

	private List<Long> selectDistinctUserIdsFrom(String table) {

		String sql = "SELECT DISTINCT user_id FROM " + table;
		Map<String, Long> map = new HashMap<String, Long>();
		List<Long> users = jdbcTemplate.queryForList(sql, map, Long.class);
		return users;
	}

}
