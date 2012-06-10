package uib.info323.twitterAWSM.model.interfaces;

public interface Trend {
	
	/**
	 * Get the name of the twitter trend.
	 * @return the name of the twitter trend.
	 */
	public String getName();
	
	/**
	 * Set the name of the twitter trend.
	 * @param name
	 */
	public void setName(String name);

	/**
	 * Get the query form of the twitter trend.
	 * @return the query of the twitter trend.
	 */
	public String getQuery();
	
	/**
	 * Set the query form of the twitter trend.
	 * @param query
	 */
	public void setQuery(String query);

}
