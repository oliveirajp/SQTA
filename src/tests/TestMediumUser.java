package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import job.Job;
import user.MediumUser;

public class TestMediumUser {

	@Test
	public void testMediumUser() {
		try {
			new MediumUser(Job.MPRICE);
			fail();
		} catch (Exception e) {
			assertEquals("Initial budget too low", e.getMessage());
		}
		try {
			MediumUser m1 = new MediumUser(Job.LPRICE*1000);
			assertTrue(m1.getBudget() <= Job.LPRICE*1000 && m1.getBudget() >= Job.LPRICE * Job.LMAXNODE);
			assertEquals(3, m1.getMaxJob());
			assertTrue(5.0 == m1.getJobsPerDay());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			fail();
		}
	}

}
