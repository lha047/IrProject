package uib.info323.twitterAWSM.model.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import uib.info323.twitterAWSM.model.impl.TweetInfo323;

public class TweetInfo323Test {

	@Test
	public void testEquals() {
		TweetInfo323 tweet1 = new TweetInfo323(0, "This is a twitter message.", new Date(), "Snorremd", "https://twimg0-a.akamaihd.net/profile_images/299544698/snorreavatarspeilvendt_normal.jpg", 1, 2, "norsk", "string", 0);
		TweetInfo323 tweet2 = new TweetInfo323(0, "This is a twitter message.", new Date(), "Snorremd", "https://twimg0-a.akamaihd.net/profile_images/299544698/snorreavatarspeilvendt_normal.jpg", 1, 2, "norsk", "string", 0);
		
		assertTrue("Tweet1 equals 2 ", tweet1.equals(tweet2));
	}

}
