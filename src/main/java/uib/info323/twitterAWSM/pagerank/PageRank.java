package uib.info323.twitterAWSM.pagerank;

import java.util.List;

import uib.info323.twitterAWSM.io.UserSearchFactory;

public class PageRank {

	private static final double DAMPING_FACTOR = 0.85;
	private List<Long> params;
	private UserSearchFactory userFactory;

	public double userRank(long userId) {
		generateParamsList(userId);

		// Matrix matrix = new Matrix(generateMatrix());

		double[][] arrB = new double[params.size()][1];
		for (int i = 0; i < params.size(); i++) {
			arrB[i][0] = 1 - DAMPING_FACTOR;
		}

		// Matrix matrixB = new Matrix(matrix);
		return 0;
	}

	private void generateParamsList(long userId) {

		if (!params.contains(userId)) {
			params.add(userId);
		}
		long[] followers = getFollowers(userId);

		for (long l : followers) {
			if (!params.contains(l)) {
				generateParamsList(l);
			}
		}
	}

	private double getMultiFactor(long sourceId, long linkId) {
		if (sourceId == linkId) {
			return 1;
		} else {
			long[] followers = getFollowers(sourceId);
			for (long l : followers) {
				if (l == linkId) {
					return -1 * (DAMPING_FACTOR / getFollwing(linkId).length);
				}
			}
		}
		return 0;
	}

	private double[][] generateMatrix() {
		double[][] matrix = new double[params.size()][params.size()];
		for (int i = 0; i < params.size(); i++) {
			for (int j = 0; j < params.size(); j++) {
				matrix[i][j] = getMultiFactor(params.get(i), params.get(j));
			}
		}
		return matrix;
	}

	private long[] getFollwing(long linkId) {

		return userFactory.findUsersFriends(linkId).getFollowersIds();
	}

	private long[] getFollowers(long userId) {

		return userFactory.findUsersFollowers(userId).getFollowersIds();
	}

}
