package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.io.impl.JsonFeedJamFactory;

public abstract class AbstractFeedJamFactory {
	
	private static final int JSON = 1;
	private static final int MYSQL = 2;
	
	public AbstractFeedJamFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract TweetFactoryIntf getTweetFactory();
	public abstract UserFactoryIntf getUserFactory();
	
	
	public static AbstractFeedJamFactory getFactory(int type) {
		if(type == 1) return new JsonFeedJamFactory();
		return null;
	}
	

}
