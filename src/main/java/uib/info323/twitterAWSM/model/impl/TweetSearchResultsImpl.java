package uib.info323.twitterAWSM.model.impl;

import java.util.List;

import uib.info323.twitterAWSM.model.interfaces.TweetInfo323;
import uib.info323.twitterAWSM.model.interfaces.TweetSearchResults;

public class TweetSearchResultsImpl implements TweetSearchResults {

	private String searchTerms;
	private String nextPageUrl;
	private String refreshUrl;
	private List<TweetInfo323> tweets;
	private int pageNumber;
	
	
	public TweetSearchResultsImpl(String searchTerms, String nextPageUrl,
			String refreshUrl, List<TweetInfo323> tweets, int pageNumber) {
		super();
		this.searchTerms = searchTerms;
		this.nextPageUrl = nextPageUrl;
		this.refreshUrl = refreshUrl;
		this.tweets = tweets;
		this.pageNumber = pageNumber;
	}

	@Override
	public String getSearchTerm() {
		// TODO Auto-generated method stub
		return searchTerms;
	}

	@Override
	public String nextPageUrl() {
		// TODO Auto-generated method stub
		return nextPageUrl;
	}

	@Override
	public String refreshUrl() {
		// TODO Auto-generated method stub
		return refreshUrl;
	}

	@Override
	public List<TweetInfo323> getTweets() {
		// TODO Auto-generated method stub
		return tweets;
	}

	@Override
	public int getPageNumber() {
		// TODO Auto-generated method stub
		return pageNumber;
	}
	
	

}
