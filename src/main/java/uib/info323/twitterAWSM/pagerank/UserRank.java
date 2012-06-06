package uib.info323.twitterAWSM.pagerank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import uib.info323.twitterAWSM.exceptions.UserNotFoundException;
import uib.info323.twitterAWSM.io.UserDAO;
import uib.info323.twitterAWSM.io.UserSearchFactory;
import uib.info323.twitterAWSM.io.impl.JsonUserFactory;
import uib.info323.twitterAWSM.model.interfaces.TwitterUserInfo323;
import uib.info323.twitterAWSM.utils.LongConverter;
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
	@Autowired
	private UserDAO mySqlUserFactory;
	private static final Logger logger = LoggerFactory
			.getLogger(UserRank.class);

	private static final double ZERO_FOLLOWING = 1;
	private static final double DEVIDED_BY_ZERO = 0;

	public UserRank(UserDAO u) {
		params = new ArrayList<Long>();
		userFactory = new JsonUserFactory();
		mySqlUserFactory = u;
	}

	public static void main(String[] args) {

	}

	public double userRank(long userId) {
		long startInsert = System.currentTimeMillis();
		generateParamsList(userId);
		long timeToInsert = System.currentTimeMillis() - startInsert;
		logger.debug("Time generateParamsList: " + timeToInsert / 1000
				+ " seconds");
		System.out.println("Params size " + params.size());

		ArrayList<Long[]> allFollowers = new ArrayList<Long[]>();
		for (int i = 0; i < params.size(); i++) {
			allFollowers.add(LongConverter.convertListToArray(getFollowers(params.get(i))));
		}
//		long[] followers = getFollowers(sourceId);

		long startInsert2 = System.currentTimeMillis();
		Matrix matrix = new Matrix(generateMatrix());
		long timeToInsert2 = System.currentTimeMillis() - startInsert2;
		logger.debug("Time generateMatrix: " + timeToInsert2 / 1000
				+ " seconds");

		double[][] arrB = new double[params.size()][1];

		for (int i = 0; i < params.size(); i++) {
			arrB[i][0] = 1 - DAMPING_FACTOR;
		}

		Matrix matrixB = new Matrix(arrB);
		long startInsert3 = System.currentTimeMillis();
		Matrix x = matrix.solve(matrixB);
		long timeToInsert3 = System.currentTimeMillis() - startInsert3;
		logger.debug("Time solve: " + timeToInsert3 / 1000 + " seconds");

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
		long[] followers = getFollowers(userId); // often there is no followers
													// of the followers so 0 is
													// returned
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

			for (long follower : followers) {
				if (follower == linkId) {
					// double factor = -1
					// * (DAMPING_FACTOR / getFollwing(linkId).length);
					double factor = 0;
					try {
						long start = System.currentTimeMillis();
						System.out.println("Get following...");
						factor = -1
								* (DAMPING_FACTOR / getNumberOfFollwing(linkId));
						long timeToGetFollowing = System.currentTimeMillis()
								- start;
						// logger.debug("Time to get following "
						// + timeToGetFollowing);
					} catch (ArithmeticException ae) {
						return DEVIDED_BY_ZERO;
					}
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
				matrix[i][j] = multiFactor;
			}
			System.out.println("params" + params.size() + " i " + i);
		}
		return matrix;
	}

	private long[] getFollowers(long userId) {
		List<Long> followers = mySqlUserFactory.selectFollowersByUserId(userId);
		if (followers.size() == 0 || followers == null) {
			return new long[0];
		}

		// long[] followers = userFactory.findUsersFollowers(userId)
		// .getFollowersUserIds();

		return LongConverter.convertObjLongToPrimLong(followers);

		// Map<Long, long[]> map = new HashMap<Long, long[]>();
		// map.put((long) 123, new long[] { 333, 213 });
		// map.put((long) 213, new long[] { 333, 123 });
		// map.put((long) 333, new long[] { 123 });
		// map.put((long) 4, new long[] { 3, 1, 2 });
		// map.put((long) 5, new long[] { 1, 7, 8, 9 });
		// map.put((long) 6, new long[] { 3, 4 });
		// map.put((long) 7, new long[] { 1, 2, 4, 5, 8, 9 });
		// map.put((long) 8, new long[] { 3, 2 });
		// map.put((long) 9, new long[] { 1 });
		// return map.get(userId);

	}

	/**
	 * if the user is following 0 1 is returned to avoid aritmeticexception
	 * divied by 0.
	 * 
	 * @param linkId
	 * @return
	 */
	private double getNumberOfFollwing(long linkId) {
		TwitterUserInfo323 user = null;

		try {
			user = mySqlUserFactory.selectUserById(linkId);
			if (user.getFriendsCount() > 0) {
				System.out.println("Fant bruker i users "
						+ user.getFriendsCount());
				return user.getFriendsCount();
			} else
				return ZERO_FOLLOWING;

		} catch (UserNotFoundException unfe) {
			// System.out.println("UserNotFound ex");
		}
		int numberOfFollowing = mySqlUserFactory
				.selectFollowingByUserId(linkId).size();
		if (numberOfFollowing > 0) {
			System.out.println("Fant bruker i following " + numberOfFollowing);
			return numberOfFollowing;
		} else {
			return ZERO_FOLLOWING;
		}

	}

	private long[] getFollwing(long userId) {

		// return userFactory.findUsersFriends(userId).getFollowersUserIds();

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

	/**
	 * Retrieves the users followers, for each of the followers retrieve their
	 * followers
	 * 
	 * @param userId
	 * @return
	 */
	public double simplifiedUserRank(long userId) {
		double userRank = 0;
		List<Long> followers = mySqlUserFactory.selectFollowersByUserId(userId);
		int totalNumberOfFollowers = 0;
		for (int i = 0; i < followers.size(); i++) {
			TwitterUserInfo323 follower = mySqlUserFactory
					.selectUserById(followers.get(i));
			if (follower == null) {
				// TODO do something if the user is not in the database.
				// Retrieve from Twitter API.
				// Find the followers count
				int numberOfFollowers = follower.getFollowersCount();
				totalNumberOfFollowers += numberOfFollowers;
			} else {
				// find the list of the followers followers
				List<Long> followersFollowers = mySqlUserFactory
						.selectFollowersByUserId(follower.getId());
				if (followersFollowers.size() == 0) {
					int numberOfFollowers = follower.getFollowersCount();
					totalNumberOfFollowers += numberOfFollowers;
				} else {

				}

			}

		}
		userRank = totalNumberOfFollowers / followers.size();
		return 0;
	}

	public double verySimplifiedUserRank(long userId) {
		double userRank = 0;
		List<Long> followers = mySqlUserFactory.selectFollowersByUserId(userId);
		int totalNumberOfFollowers = 0;
		for (int i = 0; i < followers.size(); i++) {
			TwitterUserInfo323 follower = mySqlUserFactory
					.selectUserById(followers.get(i));
			if (follower == null) {
				TwitterUserInfo323 tempUser = userFactory
						.searchUserByScreenName(follower.getScreenName());
				int numberOfFollowers = tempUser.getFollowersCount();
				totalNumberOfFollowers += numberOfFollowers;
			} else {
				int numberOfFollowers = follower.getFollowersCount();
				totalNumberOfFollowers += numberOfFollowers;
			}

		}
		userRank = (1 - DAMPING_FACTOR) * totalNumberOfFollowers
				/ followers.size();
		return 0;
	}
}
