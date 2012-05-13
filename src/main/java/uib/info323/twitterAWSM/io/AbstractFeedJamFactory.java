package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.io.impl.JsonFeedJamFactory;

public abstract class AbstractFeedJamFactory {

	public static final int JSON = 1;
	public static final int MYSQL = 2;

	public AbstractFeedJamFactory() {
		// TODO Auto-generated constructor stub
	}

	public abstract TweetSearchFactory getTweetFactory();

	public abstract UserFactory getUserFactory();

	public abstract TrendFactory getTrendFactory();

	public abstract UserDAO getUserDAO();

	public abstract UserSearchFactory getUserSearchFactory();

	public abstract TweetDAO getTweetDAO();

	public static AbstractFeedJamFactory getFactory(int type) {
		if (type == JSON)
			return new JsonFeedJamFactory();
		return null;
	}

}
