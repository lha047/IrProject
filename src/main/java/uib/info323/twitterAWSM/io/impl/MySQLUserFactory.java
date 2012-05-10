package uib.info323.twitterAWSM.io.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import uib.info323.twitterAWSM.io.UserDAO;
import uib.info323.twitterAWSM.io.UserSearchFactory;
import uib.info323.twitterAWSM.io.rowmapper.UserRowMapper;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public class MySQLUserFactory implements UserSearchFactory, UserDAO {

	private static final String SQL_INSERT_USER = "insert into users(id, screen_name, name, url, profile_image_url, description, location, date, favorites_count, followers_count, friends_count, language, profile_url, statuses_count, fitness_score, last_updated) "
			+ "values(:id, :screen_name, :name, :url, :profile_image_url, :description, :location, :date, :favorites_count, :followers_count, :friends_count, :language, :profile_url, :statuses_count, :fitness_score, :last_updated)";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private DateFormat dateFormat;
	private Date date;
	private UserRowMapper userRowMapper;

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
	public TwitterUserInfo323 searchUserByScreenName(String screenName) {
		// TODO Auto-generated method stub
		return null;
	}

	// public static void main(String[] args) {
	// ApplicationContext context = new ClassPathXmlApplicationContext(
	// "servlet-context.xml");
	//
	// UserFactory userFactory = (UserFactory) context.getBean("userFacory");
	// userFactory.searchUserByNameId(1234);
	// }

	@Override
	public TwitterUserInfo323 searchUserByNameId(long id) {
		// TODO
		return null;
	}

	@Override
	public boolean addUser(TwitterUserInfo323 user) {
		Map<String, Object> params = userToMap(user);
		int inserted = namedParameterJdbcTemplate.update(SQL_INSERT_USER,
				params);

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
		params.put("date", user.getCreatedDate());
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
	public boolean saveUser(TwitterUserInfo323 user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addListUsers(List<TwitterUserInfo323> users) {
		// TODO Auto-generated method stub
		return false;
	}

}
