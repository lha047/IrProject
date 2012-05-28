package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.model.interfaces.Trends;

public interface TrendDAO {

	public boolean insertTrends(String json);

	public Trends selectTrendsByDate(String date);
}
