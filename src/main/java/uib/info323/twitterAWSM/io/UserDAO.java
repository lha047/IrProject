package uib.info323.twitterAWSM.io;

import java.util.List;

import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public interface UserDAO {

	public boolean addUser(TwitterUserInfo323 user);

	public boolean saveUser(TwitterUserInfo323 user);

	public boolean addListUsers(List<TwitterUserInfo323> users);

}
