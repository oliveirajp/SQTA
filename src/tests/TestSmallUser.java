package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import job.Job;
import user.SmallUser;

public class TestSmallUser {

	@Test
	public void testSmallUser() {
		try {
			new SmallUser(Job.MPRICE);
			fail();
		} catch (Exception e) {
			assertEquals("Initial budget too low", e.getMessage());
		}
		try {
			SmallUser s1 = new SmallUser(Job.MPRICE*100);
			assertTrue(s1.getBudget() <= Job.MPRICE*100 && s1.getBudget() >= Job.MMAXNODE * Job.MMAXNODE);
			assertEquals(2, s1.getMaxJob());
			assertTrue(2.0 == s1.getJobsPerDay());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			fail();
		}
	}

}
