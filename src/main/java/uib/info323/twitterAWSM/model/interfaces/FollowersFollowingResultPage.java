package uib.info323.twitterAWSM.model.interfaces;

public interface FollowersFollowingResultPage {

	public String getScreenName();

	public void setScreenName(String userId);

	public String[] getFollowersScreenNames();

	public void setFollowersScreenNames(String[] followersIds);

	public int getPreviousCursor();

	public void setPreviousCursor(int previousCursor);

	public int getNextCursor();

	public void setNextCursor(int nextCursor);
}
