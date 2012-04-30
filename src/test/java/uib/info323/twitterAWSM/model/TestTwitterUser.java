/**
 * 
 */
package uib.info323.twitterAWSM.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uib.info323.twitterAWSM.model.impl.TwitterUserInfo323;

/**
 * @author Lisa
 * 
 */
public class TestTwitterUser {

	TwitterUserInfo323 user;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		user = new TwitterUserInfo323(0, 1, "blabla", "blabla sen",
				"example.com", "example.com/profileImage", "hmm", "location",
				new Date(), 10, 10, 12, "No", "example.com/profile", 23);

	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#TwitterUserInfo323(int, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Date, int, int, int, java.lang.String, java.lang.String, int)}
	 * .
	 */
	@Test
	public void testTwitterUserInfo323ConstructorAndGet() {
		assertEquals(0, user.getFitnessScore());
		assertEquals(1, user.getId());
		assertEquals("blabla", user.getScreenName());
		assertEquals("blabla sen", user.getName());
		assertEquals("example.com", user.getUrl());
		assertEquals("example.com/profileImage", user.getProfileImageUrl());
		assertEquals("hmm", user.getDescription());
		assertEquals("location", user.getLocation());
		assertEquals(10, user.getFavoritesCount());
		assertEquals(10, user.getFollowersCount());
		assertEquals(12, user.getFriendsCount());
		assertEquals("No", user.getLanguage());
		assertEquals("example.com/profile", user.getProfileUrl());
		assertEquals(23, user.getStatusesCount());
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setFitnessScore(int)}
	 * .
	 */
	@Test
	public void testSetFitnessScore() {
		user.setFitnessScore(100);
		assertEquals(100, user.getFitnessScore());
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setId(long)}
	 * .
	 */
	@Test
	public void testSetId() {
		user.setId(2345);
		assertEquals(2345, user.getId());
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setScreenName(java.lang.String)}
	 * .
	 */
	@Test
	public void testSetScreenName() {
		String name = "NyttScreenName";
		user.setScreenName(name);
		assertEquals(name, user.getScreenName());
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setName(java.lang.String)}
	 * .
	 */
	@Test
	public void testSetName() {
		String name = "NyyName";
		user.setName(name);
		assertEquals(name, user.getName());
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setUrl(java.lang.String)}
	 * .
	 */
	@Test
	public void testSetUrl() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getProfileImageUrl()}
	 * .
	 */
	@Test
	public void testGetProfileImageUrl() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setProfileImageUrl(java.lang.String)}
	 * .
	 */
	@Test
	public void testSetProfileImageUrl() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getDescription()}
	 * .
	 */
	@Test
	public void testGetDescription() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setDescription(java.lang.String)}
	 * .
	 */
	@Test
	public void testSetDescription() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getLocation()}
	 * .
	 */
	@Test
	public void testGetLocation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setLocation(java.lang.String)}
	 * .
	 */
	@Test
	public void testSetLocation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getCreatedDate()}
	 * .
	 */
	@Test
	public void testGetCreatedDate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setCreatedDate(java.util.Date)}
	 * .
	 */
	@Test
	public void testSetCreatedDate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getFavoritesCount()}
	 * .
	 */
	@Test
	public void testGetFavoritesCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setFavoritesCount(int)}
	 * .
	 */
	@Test
	public void testSetFavoritesCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getFollowersCount()}
	 * .
	 */
	@Test
	public void testGetFollowersCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setFollowersCount(int)}
	 * .
	 */
	@Test
	public void testSetFollowersCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getFriendsCount()}
	 * .
	 */
	@Test
	public void testGetFriendsCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setFriendsCount(int)}
	 * .
	 */
	@Test
	public void testSetFriendsCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getLanguage()}
	 * .
	 */
	@Test
	public void testGetLanguage() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setLanguage(java.lang.String)}
	 * .
	 */
	@Test
	public void testSetLanguage() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getProfileUrl()}
	 * .
	 */
	@Test
	public void testGetProfileUrl() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setProfileUrl(java.lang.String)}
	 * .
	 */
	@Test
	public void testSetProfileUrl() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#getStatusesCount()}
	 * .
	 */
	@Test
	public void testGetStatusesCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#setStatusesCount(int)}
	 * .
	 */
	@Test
	public void testSetStatusesCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link uib.info323.twitterAWSM.model.impl.TwitterUserInfo323#compareTo(uib.info323.twitterAWSM.model.interfaces.ITwitterUserInfo323)}
	 * .
	 */
	@Test
	public void testCompareTo() {
		fail("Not yet implemented");
	}

}
