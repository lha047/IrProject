package uib.info323.twitterAWSM.io.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import uib.info323.twitterAWSM.io.UserFactory;
import uib.info323.twitterAWSM.io.impl.UserFactoryImpl;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public class UserRowMapper implements RowMapper<TwitterUserInfo323> {

	private SimpleDateFormat dateFormat;
	private UserFactory userFactory;

	public UserRowMapper() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		userFactory = new UserFactoryImpl();
	}

	@Override
	public TwitterUserInfo323 mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		Date createdDate = null;
		java.sql.Date d = rs.getDate("CREATED_DATE");
		try {
			createdDate = dateFormat.parse(d.toString());
		} catch (ParseException e) {
			System.out.println("Error in parson of date, UserRowMapper");
			e.printStackTrace();
		}

		return userFactory.createTwitterUser(rs.getFloat("FITNESS_SCORE"),
				rs.getLong("ID"), rs.getString("SCREEN_NAME"),
				rs.getString("NAME"), rs.getString("URL"),
				rs.getString("PROFILE_IMAGE_URL"), rs.getString("DESCRIPTION"),
				rs.getString("LOCATION"), createdDate,
				rs.getInt("FAVORITES_COUNT"), rs.getInt("FOLLOWERS_COUNT"),
				rs.getInt("FRIENDS_COUNT"), rs.getString("LANGUAGE"),
				rs.getString("PROFILE_URL"), rs.getInt("STATUSES_COUNT"),
				rs.getDate("LAST_UPDATED"));
	}
}
