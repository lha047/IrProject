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

	private static final String SQL_INSERT_TWEET = "insert into tweets(id, from_user, from_user_id, to_user_id, created_at, in_reply_to_status_id, "
			+ "language_codeE, profile_image_url, retweet_count, source, text, tweet_rank, user_info, last_updated)"
			+ "values(:id, :from_user, :from_user_id, :to_user_id, :created_at, :in_reply_to_status_id, "
			+ ":language_code, :profile_image_url, :retweet_count, :source, :text, :tweet_rank, :user_info, :last_updated)";

	private static final String SQL_UPDATE_TWEET = "UPDATE tweets SET id=:id, from_user=:from_user, from_user_id:from_user_id, to_user_id=:to_user_id, created_atT=:created_at, in_reply_to_status_id=:in_reply_to_status_id, "
			+ "language_code=:language_code, profile_image_url=:profile_image_url, retweet_count=:retweet_count, source=:source, text=:text, tweet_rank=:tweet_rank, user_info=:user_info, last_updated=:last_updated "
			+ "WHERE ID=:ID";

	private static final String SQL_SELECT_TWEET_BY_ID = "SELECT * FROM tweets WHERE id = :id";

	private static final long MILLSECS_PER_DAY = (24 * 60 * 60 * 1000);

	private static final String SQL_SECLECT_TWEETS_WITH_RETWEETS = "SELECT id FROM tweets WHERE retweet_count > 0";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	private Date date;
	private DateFormat dateFormat;

	public MySQLTweetFactory() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	/**
	 * @param jdbcTemplate
	 */
	@Autowired
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertTweet(TweetInfo323 tweet) throws TweetException {
		Map<String, Object> params = tweetToMap(tweet);

		int inserted = -1;
		try {
			inserted = jdbcTemplate.update(SQL_INSERT_TWEET, params);
			if (inserted == 0) {
				TweetInfo323Impl t = (TweetInfo323Impl) selectTweetById(tweet
						.getId());

				if (tooOldTweet(t.getLastUpdated())
						|| t.getLastUpdated() == null) {
					System.out.println("update " + updateTweet(tweet));
				}
			}
			return true;

		} catch (DataAccessException dae) {
			return false;
		}

	}

	public boolean tooOldTweet(Date lastUpdated) {
		date = new Date();
		// System.out.println(date.toString());
		// System.out.println(lastUpdated.toString());
		long deltaDays = (date.getTime() - lastUpdated.getTime())
				/ MILLSECS_PER_DAY;
		// System.out.println(deltaDays);
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
			updated = jdbcTemplate.update(SQL_UPDATE_TWEET, params);
		} catch (DataAccessException dae) {
			throw new TweetException();
		}
		return updated == 1;
	}

	@Override
	public TweetInfo323 selectTweetById(long id) {
		SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
		TweetInfo323Impl tweet = null;
		try {
			tweet = (TweetInfo323Impl) jdbcTemplate.queryForObject(
					SQL_SELECT_TWEET_BY_ID, parameterSource,
					new TweetRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new TweetNotFoundException();
		}
		return tweet;
	}

	private Map<String, Object> tweetToMap(TweetInfo323 tweet) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", tweet.getId());
		params.put("from_user", tweet.getFromUser());
		params.put("from_user_id", tweet.getFromUserId());
		params.put("to_user_id", tweet.getToUserId());
		params.put("created_at", tweet.getCreatedAt());
		params.put("in_reply_to_status_id", tweet.getInReplyToStatusId());
		params.put("language_code", tweet.getLanguageCode());
		params.put("profile_image_url", tweet.getProfileImageUrl());
		params.put("retweet_count", tweet.getRetweetCount());
		params.put("source", tweet.getSource());
		params.put("text", tweet.getText());
		params.put("tweet_rank", tweet.getTweetRank());
		params.put("user_info", tweet.getUserInfo().getId());
		date = new Date();
		params.put("last_updated", dateFormat.format(date));
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
				"retweet_count", 0);
		List<Long> list = jdbcTemplate.queryForList(
				SQL_SECLECT_TWEETS_WITH_RETWEETS, parameter, Long.class);
		return list;
	}

}
