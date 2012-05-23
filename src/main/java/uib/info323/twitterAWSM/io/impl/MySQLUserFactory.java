package uib.info323.twitterAWSM.io.impl;

import java.io.File;
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

@Component
public class MySQLUserFactory implements UserDAO {

	private static final String SQL_INSERT_USER = "insert into users(id, screen_name, name, url, profile_image_url, description, location, created_date, favorites_count, followers_count, friends_count, language, profile_url, statuses_count, fitness_score, last_updated) "
			+ "values(:id, :screen_name, :name, :url, :profile_image_url, :description, :location, :created_date, :favorites_count, :followers_count, :friends_count, :language, :profile_url, :statuses_count, :fitness_score, :last_updated)";

	private static final String SQL_SELECT_USER_BY_SCREEN_NAME = "SELECT * FROM users WHERE screen_name = :screen_name";

	private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = :id";

	private static final String SQL_UPDATE_USER = "UPDATE users	SET screen_name=:screen_name, name=:name, url=:url, profile_image_url=:profile_image_url, description=:description, location=:location, created_date=:created_date, favorites_count=:favorites_count,"
			+ " followers_count=:favorites_count, friends_count=:friends_count, language=:language, profile_url=:profile_url, statuses_count=:statuses_count, fitness_score=:fitness_score, last_updated=:last_updated "
			+ "WHERE ID=:ID";

	private static final String SQL_UPDATE_FOLLOWERS_WITH_USER_ID = "UPDATE followers SET user_id=:user_id WHERE screen_name=:screen_name";

	private static final String SQL_UPDATE_FOLLOWING_WITH_USER_ID = "UPDATE following SET user_id=:user_id WHERE screen_name=:screen_name";

	private static final String SQL_SELECT_SCREEN_NAME = "SELECT screen_name FROM users";

	private static final String SQL_INSERT_FOLLOWING = "INSERT IGNORE INTO following (screen_name, user_id, following_screen_name) values (:screen_name, :user_id, :following_screen_name)";

	private static final String SQL_SELECT_ALL_FOLLOWERS = "SELECT follower_screen_name FROM followers";

	private static final String SQL_INSERT_FOLLOWERS = "INSERT IGNORE INTO followers (screen_name, user_id, follower_screen_name) values (:screen_name, :user_id, :follower_screen_name)";

	private static final String SELECT_FOLLOWERS_BY_SCREEN_NAME = "SELECT follower_screen_name FROM followers WHERE screen_name = :screen_name";

	private static final String SELECT_FOLLOWING_BY_SCREEN_NAME = "SELECT following_screen_name FROM following WHERE SCREEN_NAME = :screen_name";

	private static final String SELECT_FOLLOWERS_BY_ID = "SELECT followerId FROM followers WHERE userId = :userId";

	// Correct logger...
	private static Logger logger = LoggerFactory
			.getLogger(MySQLUserFactory.class);
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private DateFormat dateFormat;
	private Date date;

	private UserRowMapper userRowMapper;

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
				"screen_name", screenName);
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

		// List<String> users = userFactory
		// .selectAllScreenNamesFromDB(SQL_SELECT_SCREEN_NAME);
		// UserSearchFactory uf = new HttpUserFactory();
		//
		// List<String> followers = userFactory
		// .selectAllScreenNamesFromDB(SQL_SELECT_SCREEN_NAME);
		//
		userFactory.insertUserIdsToFollowersFollowing(
				"SELECT DISTINCT  screen_name FROM following",
				SQL_UPDATE_FOLLOWING_WITH_USER_ID);

		// int START = 393;
		// int STOP = followers.size();
		// for (int i = START; i < STOP; i++) {
		//
		// TwitterUserInfo323 tu = uf.searchUserByScreenName(followers.get(i));
		// System.out.println(i + " user " + tu.getId());
		// userFactory.addUser(tu);
		//
		// }

		// UserRank userRank = new UserRank("https://api.twitter.com/",
		// new RestTemplate());

		// int STOPP = 1;
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
		//
		// FollowersFollowingResultPage f = (FollowersFollowingResultPage) uf
		// .findUsersFollowers(users.get(i));
		//
		// FollowersFollowingResultPage f2 = (FollowersFollowingResultPage) uf
		// .findUsersFriends(users.get(i));
		//
		// System.out.println("*******DB " + f.getScreenName() + " "
		// + f2.getScreenName() + "*********");
		// System.out.println("Insert followers and followings into database");
		// userFactory.addFollowers(f);
		// userFactory.addFollowing(f2);
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

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

	private List<String> selectAllScreenNamesFromDB(String SQL) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> list = namedParameterJdbcTemplate.queryForList(SQL, map,
				String.class);

		return list;
	}

	public List<String> selectFollowersByScreenName(String screenName) {

		SqlParameterSource parameter = new MapSqlParameterSource("screen_name",
				screenName);
		List<String> list = namedParameterJdbcTemplate.queryForList(
				SELECT_FOLLOWERS_BY_SCREEN_NAME, parameter, String.class);

		return list;
	}

	public List<Long> selectFollowingByScreenName(String screenName) {

		SqlParameterSource parameter = new MapSqlParameterSource("screen_name",
				screenName);
		List<Long> list = namedParameterJdbcTemplate.queryForList(
				SELECT_FOLLOWING_BY_SCREEN_NAME, parameter, Long.class);
		return list;
	}

	public void insertUserIdsToFollowersFollowing(String sql,
			String sqlFollowing) {
		List<String> followers = selectAllScreenNamesFromDB(sql);
		for (String f : followers) {
			TwitterUserInfo323 user = selectUserByScreenName(f);
			System.out.println("user " + user.getScreenName());
			Map<String, Object> paramMap = followersWithIdToMap(
					user.getScreenName(), user.getId());
			int n = namedParameterJdbcTemplate.update(sqlFollowing, paramMap);
			System.out
					.println("user " + user.getScreenName() + " updated " + n);
		}

	}

	private Map<String, Object> followersWithIdToMap(String screenName, long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("screen_name", screenName);
		params.put("user_id", id);
		return params;
	}

	public void addFollowers(FollowersFollowingResultPage f) {

		String[] followers = f.getFollowersScreenNames();
		for (String follower : followers) {
			Map<String, Object> paramMap = followersToMap(f.getScreenName(),
					follower);
			namedParameterJdbcTemplate.update(SQL_INSERT_FOLLOWERS, paramMap);
		}

	}

	public void addFollowing(FollowersFollowingResultPage f) {
		String[] following = f.getFollowersScreenNames();
		for (String follow : following) {
			Map<String, Object> paramMap = followingToMap(f.getScreenName(),
					follow);
			namedParameterJdbcTemplate.update(SQL_INSERT_FOLLOWING, paramMap);
		}
	}

	private Map<String, Object> followersToMap(String screenName,
			String followerScreenName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("screen_name", screenName);
		params.put("follower_screen_name", followerScreenName);
		return params;
	}

	private Map<String, Object> followingToMap(String screenName,
			String followingScreenName) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("screen_name", screenName);
		params.put("following_screen_name", followingScreenName);

		return params;
	}

	@Override
	public TwitterUserInfo323 selectUserById(long id)
			throws UserNotFoundException {

		SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
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

	@Override
	public boolean addListUsers(List<TwitterUserInfo323> users) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Long> selectFollowersByUserId(long userId) {
		SqlParameterSource parameter = new MapSqlParameterSource("userId",
				userId);
		List<Long> list = namedParameterJdbcTemplate.queryForList(
				SELECT_FOLLOWERS_BY_ID, parameter, Long.class);
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
}
