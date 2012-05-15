package uib.info323.twitterAWSM.model.interfaces;

public interface FollowersResultPage {

	public long getUserId();

	public void setUserId(long userId);

	public long[] getFollowersIds();

	public void setFollowersIds(long[] followersIds);

	public int getPreviousCursor();

	public void setPreviousCursor(int previousCursor);

	public int getNextCursor();

	public void setNextCursor(int nextCursor);
}
