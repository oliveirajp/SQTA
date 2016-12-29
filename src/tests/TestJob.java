package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import job.Job;

public class TestJob {

	@Test
	public void testJob() {
		try {
			Job j1 = new Job(1, 50);
			assertEquals(j1.jobType, 1);
			assertEquals(j1.maxNodes, Job.SMAXNODE);
			assertEquals(j1.endTime, Job.SMAXT);
			assertEquals(j1.nodes, 1);
			assertEquals(j1.duration, 50);
			assertEquals(j1.price, Job.SPRICE * j1.nodes);
			assertEquals(j1.startTime, 1);
			assertEquals(j1.fixedDuration, 50);

			Job j2 = new Job(8, 70);
			assertEquals(j2.jobType, 2);
			assertEquals(j2.maxNodes, Job.MMAXNODE);
			assertEquals(j2.endTime, Job.MMAXT);
			assertEquals(j2.nodes, 8);
			assertEquals(j2.duration, 70);
			assertEquals(j2.price, Job.MPRICE * j2.nodes);
			assertEquals(j2.startTime, 1);
			assertEquals(j2.fixedDuration, 70);

			Job j3 = new Job(16, 15 * 60);
			assertEquals(j3.jobType, 3);
			assertEquals(j3.maxNodes, Job.LMAXNODE);
			assertEquals(j3.endTime, Job.LMAXT);
			assertEquals(j3.nodes, 16);
			assertEquals(j3.duration, 15 * 60);
			assertEquals(j3.price, Job.LPRICE * j3.nodes);
			assertEquals(j3.startTime, 1);
			assertEquals(j3.fixedDuration, 15 * 60);

			Job j4 = new Job(128, 24 * 60);
			assertEquals(j4.jobType, 4);
			assertEquals(j4.maxNodes, Job.HMAXNODE);
			assertEquals(j4.endTime, Job.HMAXT);
			assertEquals(j4.nodes, 128);
			assertEquals(j4.duration, 24 * 60);
			assertEquals(j4.price, Job.HPRICE * j4.nodes);
			assertEquals(j4.startTime, 1);
			assertEquals(j4.fixedDuration, 24 * 60);
			
			Job j6 = new Job(1, 70);
			assertEquals(2, j6.jobType);
			
			Job j7 = new Job(3, 50);
			assertEquals(2, j7.jobType);
			
			Job j8 = new Job(13, 70);
			assertEquals(3, j8.jobType);
			
			Job j9 = new Job(10, 9*60);
			assertEquals(3, j9.jobType);
			
			Job j10 = new Job(128, 10*60);
			assertEquals(4, j10.jobType);
			
			Job j11 = new Job(40, 24*60);
			assertEquals(4, j11.jobType);
			
			try {
				new Job(Job.HMAXNODE + 1, 60);
				fail();
			} catch (Exception e) {
				assertEquals(e.getMessage(), "Job parameters wrong");
			}
			
			try {
				new Job(Job.HMAXNODE, Job.HMAXT + 1);
				fail();
			} catch (Exception e) {
				assertEquals(e.getMessage(), "Job parameters wrong");
			}
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testUpdate() {
		try {
			Job j1 = new Job(10,10);
			int duration = j1.duration;
			int endTime = j1.endTime;
			j1.update();
			
			assertEquals(duration - 1, j1.duration);
			assertEquals(endTime - 1, j1.endTime);
		} catch (Exception e) {
			fail();
		}
	}

}
