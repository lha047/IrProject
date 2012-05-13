package uib.info323.twitterAWSM.model.impl;

import uib.info323.twitterAWSM.model.interfaces.FollowersResultPage;

public class FollowersResultPageImpl implements FollowersResultPage {

	private long[] followersIds;
	private int previousCursor;
	private int nextCursor;

	@Override
	public long[] getFollowersIds() {

		return followersIds;
	}

	@Override
	public void setFollowersIds(long[] followersIds) {

		this.followersIds = followersIds;
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
