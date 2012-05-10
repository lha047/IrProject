package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;

public interface UserSearchFactory {

	public TwitterUserInfo323 searchUserByScreenName(String screenName);

	public TwitterUserInfo323 searchUserByNameId(long nameId);

}
