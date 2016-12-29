package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import job.Job;
import user.LargeUser;

public class TestLargeUser {

	@Test
	public void testLargeUser() {
		try {
			new LargeUser(Job.MPRICE);
			fail();
		} catch (Exception e) {
			assertEquals("Initial budget too low", e.getMessage());
		}
		try {
			LargeUser l1 = new LargeUser(Job.HPRICE*1000);
			assertTrue(l1.getBudget() <= Job.HPRICE*1000 && l1.getBudget() >= Job.HPRICE * Job.HMAXNODE);
			assertEquals(4, l1.getMaxJob());
			assertTrue(10.0 == l1.getJobsPerDay());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			fail();
		}
	}

}
