package uib.info323.twitterAWSM.model.interfaces;

import java.util.List;

public interface TweetSearchResults {
	
	public String getSearchTerm();
	
	public String nextPageUrl();
	
	public String refreshUrl();
	
	public List<TweetInfo323> getTweets();
	
	public int getPageNumber();
	
}
