package uib.info323.twitterAWSM.model.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import uib.info323.twitterAWSM.model.interfaces.Trend;
import uib.info323.twitterAWSM.model.interfaces.Trends;

public class TrendsImpl implements Trends {

	private Map<Date, List<Trend>> trends;
	private String dateAndTime;

	public TrendsImpl(Map<Date, List<Trend>> trends, String dateAndTime) {
		this.dateAndTime = dateAndTime;
		this.trends = trends;
	}

	public Map<Date, List<Trend>> getTrends() {
		return trends;
	}

	public void setTrends(Map<Date, List<Trend>> trends) {
		this.trends = trends;
	}

	@Override
	public String getInsertedDateAndTime() {

		return dateAndTime;
	}

	@Override
	public void setInsertedDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;

	}
}
