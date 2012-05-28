package uib.info323.twitterAWSM.io;

import uib.info323.twitterAWSM.model.interfaces.Trends;

/**
 * @author Lisa Halvorsen, Snorre Davøen Info323 2012
 * 
 */
public interface TrendDAO {

	/**
	 * Inserts Json string of trends
	 * 
	 * @param json
	 *            trends
	 * @return true if the trend is inserted
	 */
	public boolean insertTrends(String json);

	/**
	 * Selects trends by date
	 * 
	 * @param date
	 * @return trends of the selected date
	 */
	public Trends selectTrendsByDate(String date);
}
