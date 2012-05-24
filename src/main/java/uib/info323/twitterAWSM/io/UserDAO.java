package uib.info323.twitterAWSM.io;

import java.util.List;

import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public interface UserDAO {

	public boolean addUser(TwitterUserInfo323 user);

	public boolean updateUser(TwitterUserInfo323 user);

	public TwitterUserInfo323 selectUserById(long userId);

	public TwitterUserInfo323 selectUserByScreenName(String screenName);

	public boolean addListUsers(List<TwitterUserInfo323> users);

	public List<Long> selectFollowersByUserId(long userId);

	public List<Long> selectFollowingByUserId(long userId);

}
