package uib.info323.twitterAWSM.model.interfaces;

public interface FollowersFollowingResultPage {

	/**
	 * Retrieves the user id of a twitter user.
	 * @return the user id
	 */
	public long getUserId();

	/**
	 * Sets a new user id.
	 * @param userId
	 */
	public void setUserId(long userId);

	/**
	 * Retrieves the user ids of the users that
	 * follow or is followed by the user stored in 
	 * the result page.
	 * @return the followers or following of the user
	 */
	public long[] getFollowersUserIds();

	/**
	 * Sets the user ids of the users that
	 * follow or is followed by the user stored in
	 * the result page.
	 * @param followersIds
	 */
	public void setFollowersUserIds(long[] followersIds);

	public int getPreviousCursor();

	public void setPreviousCursor(int previousCursor);

	public int getNextCursor();

	public void setNextCursor(int nextCursor);
}
