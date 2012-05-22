package uib.info323.twitterAWSM.io;

import java.util.List;

import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public interface UserSearchFactory {

	public TwitterUserInfo323 searchUserByScreenName(String screenName)
			throws UserNotFoundException;

	public TwitterUserInfo323 searchUserByNameId(long nameId)
			throws UserNotFoundException;

	public Object findUsersFollowers(String string);

	public Object findUsersFriends(String string);

	public List<TwitterUserInfo323> getRetweetedBy(long tweetId);

}
