package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.io.impl.JsonFeedJamFactory;

public abstract class AbstractFeedJamFactory {
	
	public static final int JSON = 1;
	public static final int MYSQL = 2;
	
	public AbstractFeedJamFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract TweetFactory getTweetFactory();
	public abstract UserFactory getUserFactory();
	public abstract TrendFactory getTrendFactory();
	
	
	public static AbstractFeedJamFactory getFactory(int type) {
		if(type == JSON) return new JsonFeedJamFactory();
		return null;
	}
	

}
