package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.model.interfaces.FollowersResultPage;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public interface UserSearchFactory {

	public TwitterUserInfo323 searchUserByScreenName(String screenName)
			throws UserNotFoundException;

	public TwitterUserInfo323 searchUserByNameId(long nameId)
			throws UserNotFoundException;

	public FollowersResultPage findUsersFollowers(long userId);

	public FollowersResultPage findUsersFriends(long userId);

}
