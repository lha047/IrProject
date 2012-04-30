import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;

import uib.info323.twitterAWSM.model.TwitterUserMock;
import uib.info323.twitterAWSM.model.interfaces.ITwitterUserInfo323;
import uib.info323.twitterAWSM.model.search.ISearch;
import uib.info323.twitterAWSM.model.search.Search;

public class SearchTest {

	@Autowired
	ISearch search;
	private String userNameLisa;
	private String userNameLene;
	private String tagJava;
	private String tagSemWeb;
	private ITwitterUserInfo323 userProfile;
	private List<Tweet> liste;

	@Before
	public void setUp() throws Exception {
		search = new Search();
		userNameLisa = "@lisaHalvors";
		userNameLene = "@lennev";
		tagJava = "#java";
		tagSemWeb = "#SemWeb";

		userProfile = new TwitterUserMock();
		liste = new ArrayList<Tweet>();

	}

	@Test
	public final void testGetTweets() {
		liste = search.getTweets();
		Integer l = 0;
		assertFalse("liste størrelse != 0", l.equals(liste.size()));

	}

	@Test
	public final void testGetUserString() {
		ITwitterUserInfo323 user = search.getUser(userNameLisa);
		assertEquals("ScreenName", "lisaHalvors", user.getScreenName());
		assertFalse("ScreenNname != null", user.getScreenName().equals(null));
		assertEquals("followersCount", 18, user.getFollowersCount());
		Integer minus = -1;
		assertFalse("followerscount != -1",
				minus.equals(user.getFollowersCount()));
	}

	@Test
	public final void testFindPlace() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetFollowers() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetTwitterTemplate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSearch() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testMain() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetUserITwitterRequest() {
		fail("Not yet implemented"); // TODO
	}

}
