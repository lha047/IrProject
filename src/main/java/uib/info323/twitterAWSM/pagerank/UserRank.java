package uib.info323.twitterAWSM.pagerank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.io.UserSearchFactory;
import uib.info323.twitterAWSM.io.impl.JsonUserFactory;
import Jama.Matrix;

/**
 * @author Lisa
 * 
 *         Based on PageRank implementation from:
 *         http://www.javadev.org/files/Ranking.pdf
 * 
 */
public class UserRank {

	private static final double DAMPING_FACTOR = 0.85;
	private List<Long> params;
	private UserSearchFactory userFactory;

	public UserRank(String apiUrl, RestTemplate restTemplate) {
		params = new ArrayList<Long>();
		userFactory = new JsonUserFactory(apiUrl, restTemplate);
	}

	public static void main(String[] args) {
		UserRank userRank = new UserRank("https://api.twitter.com/",
				new RestTemplate());

		double rank = userRank.userRank(333);
		System.out.println(rank);

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
				System.out.println(followers[i]);
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
					double factor = -1
							* (DAMPING_FACTOR / getFollwing(linkId).length);
					System.out.println("Factor " + factor);
					return factor;
				}
			}
		}
		return 0;
	}

	private double[][] generateMatrix() {
		double[][] matrix = new double[params.size()][params.size()];
		for (int i = 0; i < params.size(); i++) {
			for (int j = 0; j < params.size(); j++) {
				double multiFactor = getMultiFactor(params.get(i),
						params.get(j));
				System.out.println("multifactor " + multiFactor);
				matrix[i][j] = multiFactor;
			}
		}
		return matrix;
	}

	private long[] getFollowers(long userId) {
		// FollowersFollowingResultPage resPage = userFactory
		// .findUsersFollowers(userId);
		// System.out.println("******" + resPage.getUserId() + " *** "
		// + resPage.getFollowersIds().length);
		// long[] followers = resPage.getFollowersIds();
		//
		// return followers;
		Map<Long, long[]> map = new HashMap<Long, long[]>();
		map.put((long) 123, new long[] { 333, 213 });
		map.put((long) 213, new long[] { 333, 123 });
		map.put((long) 333, new long[] { 123 });
		// map.put((long) 4, new long[] { 3, 1, 2 });
		// map.put((long) 5, new long[] { 1, 7, 8, 9 });
		// map.put((long) 6, new long[] { 3, 4 });
		// map.put((long) 7, new long[] { 1, 2, 4, 5, 8, 9 });
		// map.put((long) 8, new long[] { 3, 2 });
		// map.put((long) 9, new long[] { 1 });
		return map.get(userId);

	}

	private long[] getFollwing(long userId) {

		// return userFactory.findUsersFriends(userId).getFollowersIds();

		Map<Long, long[]> map = new HashMap<Long, long[]>();
		map.put((long) 123, new long[] { 2, 3, 6, 7, 8, 9, 10 });
		map.put((long) 213, new long[] { 3, 4, 7 });
		map.put((long) 333, new long[] { 1, 2, 7, 4 });
		// map.put((long) 4, new long[] { 1, 2, 5, 8, 54, 73, 23, 213 });
		// map.put((long) 5, new long[] { 1, 3, 6, 9, 3 });
		// map.put((long) 6, new long[] { 1, 2 });
		// map.put((long) 7, new long[] { 1, 5, 8, 78 });
		// map.put((long) 8, new long[] { 1 });
		// map.put((long) 9, new long[] { 1, 5, 8, 78 });

		return map.get(userId);
	}

}
