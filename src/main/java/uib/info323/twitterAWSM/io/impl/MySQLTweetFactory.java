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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import uib.info323.twitterAWSM.exceptions.TweetException;
import uib.info323.twitterAWSM.exceptions.TweetNotFoundException;
import uib.info323.twitterAWSM.io.TweetDAO;
import uib.info323.twitterAWSM.io.rowmapper.TweetRowMapper;
import uib.info323.twitterAWSM.model.impl.TweetInfo323Impl;
import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;

public class MySQLTweetFactory implements TweetDAO {

	private static final int DAYS_TOO_OLD = 6;

	private static final String SQL_INSERT_TWEET = "insert into tweets(ID, FROM_USER, FROM_USER_ID, TO_USER_ID, CREATED_AT, IN_REPLY_TO_STATUS_ID, "
			+ "LANGUAGE_CODE, PROFILE_IMAGE_URL, RETWEET_COUNT, SOURCE, TEXT, TWEET_RANK, USER_INFO, LAST_UPDATED)"
			+ "values(:ID, :FROM_USER, :FROM_USER_ID, :TO_USER_ID, :CREATED_AT, :IN_REPLY_TO_STATUS_ID, "
			+ ":LANGUAGE_CODE, :PROFILE_IMAGE_URL, :RETWEET_COUNT, :SOURCE, :TEXT, :TWEET_RANK, :USER_INFO, :LAST_UPDATED)";

	private static final String SQL_UPDATE_TWEET = "UPDATE tweets SET ID=:ID, FROM_USER=:FROM_USER, FROM_USER_ID:FROM_USER_ID, TO_USER_ID=:TO_USER_ID, CREATED_AT=:CREATED_AT, IN_REPLY_TO_STATUS_ID=:IN_REPLY_TO_STATUS_ID, "
			+ "LANGUAGE_CODE=:LANGUAGE_CODE, PROFILE_IMAGE_URL=:PROFILE_IMAGE_URL, RETWEET_COUNT=:RETWEET_COUNT, SOURCE=:SOURCE, TEXT=:TEXT, TWEET_RANK=:TWEET_RANK, USER_INFO=:USER_INFO, LAST_UPDATE=:LAST_UPDATED "
			+ "WHERE ID=:ID";

	private static final String SQL_SELECT_TWEET_BY_ID = "SELECT * FROM tweets WHERE ID = :ID";

	private static final long MILLSECS_PER_DAY = (24 * 60 * 60 * 1000);

	private static final String SQL_SECLECT_TWEETS_WITH_RETWEETS = "SELECT ID FROM tweets WHERE RETWEET_COUNT > 0";

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private Date date;
	private DateFormat dateFormat;

	public MySQLTweetFactory() {
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
	public boolean insertTweet(TweetInfo323 tweet) throws TweetException {
		Map<String, Object> params = tweetToMap(tweet);

		int inserted = -1;
		try {
			inserted = namedParameterJdbcTemplate.update(SQL_INSERT_TWEET,
					params);

		} catch (DataAccessException dae) {
			throw new TweetException();
		}
		if (inserted != 1) {
			TweetInfo323Impl t = (TweetInfo323Impl) selectTweetById(tweet
					.getId());
			if (tooOldTweet(t.getLastUpdated())) {
				updateTweet(tweet);
			}
		}

		return inserted == 1;
	}

	public boolean tooOldTweet(Date lastUpdated) {
		date = new Date();
		System.out.println(date.toString());
		System.out.println(lastUpdated.toString());
		long deltaDays = (date.getTime() - lastUpdated.getTime())
				/ MILLSECS_PER_DAY;
		System.out.println(deltaDays);
		if (deltaDays > DAYS_TOO_OLD)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateTweet(TweetInfo323 tweet) throws TweetException {
		Map<String, Object> params = tweetToMap(tweet);
		int updated = -1;
		try {

			updated = namedParameterJdbcTemplate.update(SQL_UPDATE_TWEET,
					params);
		} catch (DataAccessException dae) {
			throw new TweetException();
		}
		return updated == 1;
	}

	@Override
	public TweetInfo323 selectTweetById(long id) {
		SqlParameterSource parameterSource = new MapSqlParameterSource("ID", id);
		TweetInfo323Impl tweet = null;
		try {
			tweet = (TweetInfo323Impl) namedParameterJdbcTemplate
					.queryForObject(SQL_SELECT_TWEET_BY_ID, parameterSource,
							new TweetRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new TweetNotFoundException();
		}
		return tweet;
	}

	private Map<String, Object> tweetToMap(TweetInfo323 tweet) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ID", tweet.getId());
		params.put("FROM_USER", tweet.getFromUser());
		params.put("FROM_USER_ID", tweet.getFromUserId());
		params.put("TO_USER_ID", tweet.getToUserId());
		params.put("CREATED_AT", tweet.getCreatedAt());
		params.put("IN_REPLY_TO_STATUS_ID", tweet.getInReplyToStatusId());
		params.put("LANGUAGE_CODE", tweet.getLanguageCode());
		params.put("PROFILE_IMAGE_URL", tweet.getProfileImageUrl());
		params.put("RETWEET_COUNT", tweet.getRetweetCount());
		params.put("SOURCE", tweet.getSource());
		params.put("TEXT", tweet.getText());
		params.put("TWEET_RANK", tweet.getTweetRank());
		params.put("USER_INFO", tweet.getUserInfo().getId());
		date = new Date();
		params.put("LAST_UPDATED", dateFormat.format(date));
		return params;
	}

	public static void main(String[] args) {

		ApplicationContext context = new FileSystemXmlApplicationContext("src"
				+ File.separator + "main" + File.separator + "webapp"
				+ File.separator + "WEB-INF" + File.separator + "spring"
				+ File.separator + "appServlet" + File.separator
				+ "db-context.xml");

		MySQLTweetFactory tweetFactory = (MySQLTweetFactory) context
				.getBean("mySqlTweetFactory");
		List<Long> tweetsWithReTweets = tweetFactory.findTweetsWithReTweets();

		// List<Long> related = new ArrayList<Long>();
		// List<String> mentions = new ArrayList<String>();
		// List<String> tags = new ArrayList<String>();
		// TwitterUserInfo323 user = new TwitterUserInfo323Impl();
		// user.setId(36341207);
		//
		// SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		// try {
		// Date d = s.parse("2012-05-01");
		// System.out.println(tweetFactory.tooOldTweet(d));
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// TweetInfo323Impl t2 = new TweetInfo323Impl(related, 12345, "TEST",
		// new Date(), "from user", "http://profile.image.com", 1234,
		// 4321, "No", "source", 5.0, (long) 123, 4, mentions, tags, user);
		//
		// new ArrayList<Long>, 1111111, "AWSM Tweet for FEEDJAM", new Date(),
		// "Torstein", "http://bilde.tull.com", 12234, 3212, "en", "SORUCE",
		// 100, 12321321, 23, new ArrayList<String>(), new ArrrayList<String>(),
		// new TweetInfo323Impl());
		System.out.println("****INSERT******");
		// try {
		// System.out.println(tweetFactory.insertTweet(t2));
		// } catch (TweetException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// TweetInfo323Impl t = null;
		// try {
		// t = (TweetInfo323Impl) tweetFactory.selectTweetById(12345);
		// } catch (UserNotFoundException e) {
		//
		// }
	}

	private List<Long> findTweetsWithReTweets() {

		SqlParameterSource parameter = new MapSqlParameterSource(
				"RETWEET_COUNT", 0);
		List<Long> list = namedParameterJdbcTemplate.queryForList(
				SQL_SECLECT_TWEETS_WITH_RETWEETS, parameter, Long.class);
		return list;
	}

}
