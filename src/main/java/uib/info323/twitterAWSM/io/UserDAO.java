package uib.info323.twitterAWSM.io;

import java.util.List;

import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public interface UserDAO {

	/**
	 * Adds user
	 * 
	 * @param user
	 * @return true if the user is added
	 */
	public boolean addUser(TwitterUserInfo323 user);

	/**
	 * Adds batch of users
	 * 
	 * @param users
	 * @param sql
	 *            insert query
	 * @return number of inserted users
	 */
	public int addBatchUsers(List<TwitterUserInfo323> users, String sql);

	/**
	 * Updates user information
	 * 
	 * @param user
	 * @return true if the update was successful
	 */
	public boolean updateUser(TwitterUserInfo323 user);

	/**
	 * @param userId
	 * @return
	 */
	public TwitterUserInfo323 selectUserById(long userId);

	public TwitterUserInfo323 selectUserByScreenName(String screenName);

	public List<Long> selectFollowersByUserId(long userId);

	public List<Long> selectFollowingByUserId(long userId);

	public int insertBatchFollowersFollowing(FollowersFollowingResultPage f,
			String sql);

}
