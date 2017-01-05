package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import simulationSystem.Job;
import simulationSystem.Simulator;
import simulationSystem.SuperComputer;

public class TestSuperComputer {

	@Test
	public void testSuperComputer() {
		new SuperComputer();
		assertTrue(SuperComputer.getActiveJobs() != null);
		assertEquals((int)(0.1 * SuperComputer.nodes), SuperComputer.getSmallNodes());
		assertEquals((int)(0.3 * SuperComputer.nodes), SuperComputer.getMediumNodes());
		int rest = SuperComputer.nodes - SuperComputer.getSmallNodes() - SuperComputer.getMediumNodes();
		assertEquals(rest, SuperComputer.getAvailableNodes());
		assertEquals(SuperComputer.nodes, SuperComputer.getWeekendNodes());

	}

	@Test
	public void testStartJob() {
		new SuperComputer();
		new Simulator(1, new int[]{1}, new int[]{10000});
		try {
			Job j1 = new Job(2,50);
			Job j2 = new Job(5,70);
			Job j3 = new Job(40,600);

			int sNodes = SuperComputer.getSmallNodes();
			int mNodes = SuperComputer.getMediumNodes();
			int gNodes = SuperComputer.getAvailableNodes();

			SuperComputer.startJob(j2, 1);
			assertEquals(Job.MMAXT, SuperComputer.getActiveJobs().get(0).endTime);
			SuperComputer.startJob(j1, 1);
			assertEquals(Job.MMAXT, SuperComputer.getActiveJobs().get(0).endTime);
			SuperComputer.startJob(j3, 1);
			assertEquals(Job.LMAXT, SuperComputer.getActiveJobs().get(0).endTime);
			
			assertEquals(sNodes - 2, SuperComputer.getSmallNodes());
			assertEquals(mNodes - 5, SuperComputer.getMediumNodes());
			assertEquals(gNodes - 40, SuperComputer.getAvailableNodes());
			
			Job j5 = new Job(2,50);
			Job j6 = new Job(5,70);
			Job j7 = new Job(20,600);
			
			SuperComputer.startJob(j5, 2);
			SuperComputer.startJob(j6, 2);
			SuperComputer.startJob(j7, 2);
			
			assertEquals(2, Simulator.sNumbers);
			assertEquals(2, Simulator.mNumbers);
			assertEquals(2, Simulator.lNumbers);
			
			assertEquals(6, SuperComputer.getActiveJobs().size());
			
			int wait = 1 - j1.startTime;
			wait += 2 - j5.startTime;
			assertEquals(wait, Simulator.sWait);
			
			wait = 1 - j2.startTime;
			wait += 2 - j6.startTime;
			assertEquals(wait, Simulator.mWait);
			
			wait = 1 - j3.startTime;
			wait += 2 - j7.startTime;
			assertEquals(wait, Simulator.lWait);
			//assertEquals(1-j4.startTime, Simulator.hWait);

			SuperComputer.setSmallNodes(0);
			SuperComputer.setMediumNodes(0);
			SuperComputer.setAvailableNodes(0);
			SuperComputer.setWeekendNodes(0);
			
			try {
				SuperComputer.startJob(j1, 1);
				fail();
			} catch (Exception e) {
				assertEquals("Queue overused", e.getMessage());
			}
			
			try {
				SuperComputer.startJob(j2, 1);
				fail();
			} catch (Exception e) {
				assertEquals("Queue overused", e.getMessage());
			}
			
			try {
				SuperComputer.startJob(j3, 1);
				fail();
			} catch (Exception e) {
				assertEquals("Queue overused", e.getMessage());
			}
			
			try {
				Job j8 = new Job(128,600);
				SuperComputer.startJob(j8, 1);
				fail();
			} catch (Exception e) {
				assertEquals("Queue overused", e.getMessage());
			}

		} catch (Exception e) {
			fail();
		}
		
		try {
			new SuperComputer();
			new Simulator(1, new int[]{1}, new int[]{10000});
			Job j1 = new Job(128,600);
			int hNodes = SuperComputer.getWeekendNodes();

			SuperComputer.startJob(j1, 1);
			assertEquals(Job.HMAXT, SuperComputer.getActiveJobs().get(0).endTime);

			assertEquals(hNodes - 128, SuperComputer.getWeekendNodes());

			assertEquals(1, Simulator.hNumbers);
			
			Job j2 = new Job(2,50);
			SuperComputer.startJob(j2, 2);
			fail();
			
		} catch (Exception e) {
			assertEquals("WeekendQueue overused", e.getMessage());
		}
	}

	@Test
	public void testUpdate() {
		new SuperComputer();
		new Simulator(1, new int[]{1}, new int[]{10000});

		try {
			Job j1 = new Job(2,1);
			Job j2 = new Job(5,1);
			Job j3 = new Job(40,1);
			Job j5 = new Job(2,10);
			
			SuperComputer.startJob(j2, 1);
			SuperComputer.startJob(j1, 1);
			SuperComputer.startJob(j3, 1);
			
			
			SuperComputer.update(2);
			assertEquals(0, SuperComputer.getActiveJobs().size());
			double hours = 3.0/60.0;
			assertTrue(hours == Simulator.hoursConsumed);
			assertEquals(40+5+2, Simulator.nodesConsumed);

			SuperComputer.startJob(j5, 3);
			SuperComputer.update(4);
			assertEquals(1, SuperComputer.getActiveJobs().size());
			
			new SuperComputer();
			new Simulator(1, new int[]{1}, new int[]{10000});
			Job j4 = new Job(128,1);
			SuperComputer.startJob(j4, 1);
			
			SuperComputer.update(2);
			assertEquals(0, SuperComputer.getActiveJobs().size());
			hours = 1.0/60.0;
			assertTrue(hours == Simulator.hoursConsumed);
			assertEquals(128, Simulator.nodesConsumed);
			
		} catch (Exception e) {
			fail();
		}
		
	}

}
