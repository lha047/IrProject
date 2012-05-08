package uib.info323.twitterAWSM.model.impl;

import uib.info323.twitterAWSM.model.interfaces.Trend;

public class TrendImpl implements Trend {
	
	String name;
	String query;
	
	public TrendImpl(String name, String query) {
		this.name = name;
		this.query = query;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	

}
