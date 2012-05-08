package uib.info323.twitterAWSM.io;

import java.util.Date;

import uib.info323.twitterAWSM.model.interfaces.Trends;

public interface TrendFactory {

	public Trends getDailyTrendsForDate(Date date);
}
