package uib.info323.twitterAWSM.io.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import uib.info323.twitterAWSM.io.TweetFactory;
import uib.info323.twitterAWSM.io.impl.TweetFactoryImpl;
import uib.info323.twitterAWSM.model.impl.TweetInfo323Impl;
import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public class TweetRowMapper implements RowMapper<TweetInfo323> {

	@Autowired
	private TweetFactory tweetFactory;
	private SimpleDateFormat dateFormat;

	public TweetRowMapper() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		tweetFactory = new TweetFactoryImpl();
	}

	public void setTweetFactory(TweetFactory tweetFactory) {
		this.tweetFactory = tweetFactory;
	}

	@Override
	public TweetInfo323 mapRow(ResultSet rs, int rowNum) throws SQLException {

		Date createdAt = null;
		Date lastUpdated = null;
		java.sql.Date tempCreatedAt = rs.getDate("CREATED_AT");
		java.sql.Date tempLastUpdate = rs.getDate("last_updated");

		try {
			createdAt = dateFormat.parse(tempCreatedAt.toString());
			lastUpdated = dateFormat.parse(tempLastUpdate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR in parsing date in TweetRowMapper");
			e.printStackTrace();
		}

		List<Long> related = new ArrayList<Long>();
		List<String> mentions = new ArrayList<String>();
		List<String> tags = new ArrayList<String>();
		List<Long> reTweeters = new ArrayList<Long>();

		TwitterUserInfo323 userInfo = new TwitterUserInfo323Impl();

		TweetInfo323Impl tweet = (TweetInfo323Impl) tweetFactory.createTweet(
				related, rs.getLong("ID"), rs.getString("TEXT"), createdAt,
				rs.getString("FROM_USER"), rs.getString("PROFILE_IMAGE_URL"),
				rs.getLong("TO_USER_ID"), rs.getLong("from_user_id"),
				rs.getString("LANGUAGE_CODE"), rs.getString("SOURCE"),
				rs.getDouble("TWEET_RANK"),
				rs.getLong("IN_REPLY_TO_STATUS_ID"),
				rs.getInt("RETWEET_COUNT"), reTweeters, mentions, tags,
				userInfo, lastUpdated);

		return tweet;
	}

}
