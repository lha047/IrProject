package uib.info323.twitterAWSM.model.impl;

import uib.info323.twitterAWSM.model.interfaces.FollowersFollowingResultPage;

public class FollowersFollowingResultPageImpl implements FollowersFollowingResultPage {

	private String[] followersScreenNames;
	private int previousCursor;
	private int nextCursor;
	private String screenName;

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String userId) {
		this.screenName = userId;
	}

	@Override
	public String[] getFollowersScreenNames() {

		return followersScreenNames;
	}

	@Override
	public void setFollowersScreenNames(String[] followersScreenNames) {

		this.followersScreenNames = followersScreenNames;
	}

	@Override
	public int getPreviousCursor() {

		return previousCursor;
	}

	@Override
	public void setPreviousCursor(int previousCursor) {

		this.previousCursor = previousCursor;
	}

	@Override
	public int getNextCursor() {

		return nextCursor;
	}

	@Override
	public void setNextCursor(int nextCursor) {

		this.nextCursor = nextCursor;
	}

}
