package uib.info323.twitterAWSM.io.impl;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uib.info323.twitterAWSM.io.TweetFactoryIntf;
import uib.info323.twitterAWSM.model.impl.TweetInfo323;

public class JsonTweetFactory implements TweetFactoryIntf{
	
	private final String apiUrl;
	
	public JsonTweetFactory(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	@Override
	public List<TweetInfo323> searchTweets(String searchTerm) {
		// TODO Auto-generated method stub
		Gson response = new Gson();
		GsonBuilder builder = new  GsonBuilder();
		
		return null;
	}

	@Override
	public List<TweetInfo323> searchTweetsByDate(String searchTerm, Date date) {
		// TODO Auto-generated method stub
		return null;
	};
	

}
