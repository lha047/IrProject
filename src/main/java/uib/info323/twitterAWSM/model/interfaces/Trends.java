package uib.info323.twitterAWSM.model.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Trends {

	/**
	 * Get the time and date of when the trend was cached.
	 * @return the time and date.
	 */
	public String getInsertedDateAndTime();

	/**
	 * Set the time and date of when the trend was cached.
	 * @param dateAndTime
	 */
	public void setInsertedDateAndTime(String dateAndTime);

	/**
	 * Get the trending topics for the 
	 * @return a map of trending topics.
	 */
	public Map<Date, List<Trend>> getTrends();

	/**
	 * Set
	 * @param trends
	 */
	public void setTrends(Map<Date, List<Trend>> trends);

}
