package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public interface UserFactory {
	public TwitterUserInfo323 searchUserByScreenName(String screenName);

	public TwitterUserInfo323 searchUserByNameId(long nameId);
}
