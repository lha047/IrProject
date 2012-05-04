package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.io.impl.JsonTweetFactory;

public class JsonFeedJamFactory extends AbstractFeedJamFactory {
	
	private final String apiUrl = "https://api.twitter.com/";
	
	public JsonFeedJamFactory() {
		super();
	}
	

	@Override
	public TweetFactoryIntf getTweetFactory() {
		// TODO Auto-generated method stub
		return new JsonTweetFactory(apiUrl);
	}

	@Override
	public UserFactoryIntf getUserFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
