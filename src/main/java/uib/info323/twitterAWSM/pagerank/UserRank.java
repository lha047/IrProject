package uib.info323.twitterAWSM.pagerank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uib.info323.twitterAWSM.io.UserSearchFactory;
import Jama.Matrix;

public class UserRank {

	private static final double DAMPING_FACTOR = 0.85;
	private List<Long> params;
	private UserSearchFactory userFactory;

	public UserRank() {
		params = new ArrayList<Long>();
	}

	public static void main(String[] args) {
		UserRank userRank = new UserRank();
		double en = userRank.userRank(1);
		double to = userRank.userRank(2);
		double tre = userRank.userRank(3);
		System.out.println(en);
		System.out.println(to);
		System.out.println(tre);
		System.out.println(en + to + tre);

	}

	public double userRank(long userId) {
		generateParamsList(userId);

		Matrix matrix = new Matrix(generateMatrix());

		double[][] arrB = new double[params.size()][1];

		for (int i = 0; i < params.size(); i++) {
			arrB[i][0] = 1 - DAMPING_FACTOR;
		}

		Matrix matrixB = new Matrix(arrB);

		Matrix x = matrix.solve(matrixB);

		int ind = 0;

		int cnt = 0;

		for (Iterator it = params.iterator(); it.hasNext();) {

			long currentUser = (Long) it.next();

			if (currentUser == userId)
				ind = cnt;

			cnt++;

		}

		return x.getArray()[ind][0];
	}

	private void generateParamsList(long userId) {

		if (!params.contains(userId)) {
			params.add(userId);
		}
		long[] followers = getFollowers(userId);

		for (int i = 0; i < followers.length; i++) {
			if (!params.contains(followers[i])) {
				generateParamsList(followers[i]);
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

	private long[] getFollowers(long userId) {

		// return userFactory.findUsersFollowers(userId).getFollowersIds();
		Map<Long, long[]> map = new HashMap<Long, long[]>();
		map.put((long) 1, new long[] { 3, 2 });
		map.put((long) 2, new long[] { 3, 1 });
		map.put((long) 3, new long[] { 1 });

		return map.get(userId);

	}

	private long[] getFollwing(long userId) {

		// return userFactory.findUsersFriends(linkId).getFollowersIds();

		Map<Long, long[]> map = new HashMap<Long, long[]>();
		map.put((long) 1, new long[] { 2, 3 });
		map.put((long) 2, new long[] { 3 });
		map.put((long) 3, new long[] { 1, 4, 5, 6 });

		return map.get(userId);
	}

}
