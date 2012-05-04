package uib.info323.twitterAWSM.model.interfaces;

import java.util.List;

public interface ITweetSearchResults {
	
	public String getSearchTerm();
	
	public String nextPageUrl();
	
	public String refreshUrl();
	
	public List<ITweetInfo323> getTweets();
	
	public int getPageNumber();
	
}
