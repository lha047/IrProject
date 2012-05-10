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
		try {
			createdDate = dateFormat.parse(rs.getDate("date").toString());
		} catch (ParseException e) {
			System.out.println("Error in parson of date, UserRowMapper");
			e.printStackTrace();
		}

		return userFactory.createTwitterUser(rs.getFloat("fitness_score"),
				rs.getLong("id"), rs.getString("screen_name"),
				rs.getString("name"), rs.getString("url"),
				rs.getString("profile_image_url"), rs.getString("description"),
				rs.getString("location"), createdDate,
				rs.getInt("favorites_count"), rs.getInt("followers_count"),
				rs.getInt("friends_count"), rs.getString("language"),
				rs.getString("profile_url"), rs.getInt("statuses_count"));
	}
}
