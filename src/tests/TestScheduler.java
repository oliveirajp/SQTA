package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import simulationSystem.Job;
import simulationSystem.Scheduler;
import simulationSystem.Simulator;
import simulationSystem.SuperComputer;

public class TestScheduler {

	@Test
	public void testScheduler() {
		new Scheduler();
		assertTrue(Scheduler.getQueue() != null);
	}

	@Test
	public void testSubmitJob() {
		try {
			new Scheduler();
			int queueSize = Scheduler.getQueue().size();
			Job j1 = new Job(10,20);
			Scheduler.submitJob(j1, 500);

			assertEquals(j1.startTime, 500);
			assertEquals(queueSize + 1,Scheduler.getQueue().size());
			assertTrue(j1 == Scheduler.getQueue().get(queueSize));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testUpdate() {
		Scheduler s = new Scheduler();
		new SuperComputer();
		try{
			Job j1 = new Job(2,50);
			Job j2 = new Job(5,70);
			Job j3 = new Job(40,600);
			Job j4 = new Job(128,600);
			
			Scheduler.submitJob(j1, 500);
			Scheduler.submitJob(j2, 500);
			Scheduler.submitJob(j3, 500);
			Scheduler.submitJob(j4, 500);
			
			s.update(Simulator.weekEndStart - 40, Simulator.weekEndStart - 40);
			
			assertEquals(4, Scheduler.getQueue().size());
			
			s.update(Simulator.weekEndStart + 2, Simulator.weekEndStart + 2);
			
			assertEquals(4, Scheduler.getQueue().size());
			
			s.update(Simulator.weekEndStart, Simulator.weekEndStart);
			
			assertEquals(3, Scheduler.getQueue().size());
			
			new SuperComputer();
			
			s.update(501, 501);
			
			assertEquals(0, Scheduler.getQueue().size());
			
			//Short test
			new SuperComputer();
			Job s1 = new Job(2,50);
			Job s2 = new Job(2,50);
			Job s3 = new Job(2,50);
			Job s4 = new Job(2,50);
			Job s5 = new Job(2,50);
			Job s6 = new Job(2,50);
			Job s7 = new Job(2,50);
			
			Scheduler.submitJob(s1, 1);
			Scheduler.submitJob(s2, 2);
			Scheduler.submitJob(s3, 3);
			Scheduler.submitJob(s4, 4);
			Scheduler.submitJob(s5, 5);
			Scheduler.submitJob(s6, 6);
			Scheduler.submitJob(s7, 7);
			
			s.update(10, 10);
			
			assertEquals(0, Scheduler.getQueue().size());
			
			//Medium test
			new SuperComputer();
			Job m1 = new Job(12,70);
			Job m2 = new Job(12,70);
			Job m3 = new Job(12,70);
			Job m4 = new Job(12,70);
			
			Scheduler.submitJob(m1, 1);
			Scheduler.submitJob(m2, 2);
			Scheduler.submitJob(m3, 3);
			Scheduler.submitJob(m4, 4);
			
			s.update(10, 10);
			assertEquals(0, Scheduler.getQueue().size());
			
			//Large test
			new SuperComputer();
			Job l1 = new Job(64,600);
			Job l2 = new Job(64,600);
			Job l3 = new Job(6,550);
			Job l4 = new Job(1,700);
			
			Scheduler.submitJob(l1, 1);
			Scheduler.submitJob(l2, 2);
			Scheduler.submitJob(l3, 3);
			
			s.update(4,4);
			assertEquals(1, Scheduler.getQueue().size());
			
			Scheduler.submitJob(l4, 10);
			SuperComputer.update(10);
			s.update(10,10);
			assertEquals(2, Scheduler.getQueue().size());
			
			//Huge test
			s = new Scheduler();
			new SuperComputer();
			Job h1 = new Job(128,50);
			Job h2 = new Job(2,24*60);
			
			Scheduler.submitJob(h1, 1);
			Scheduler.submitJob(l1, 1);
			Scheduler.submitJob(h2, 2);
			
			s.update(10, 10);
			
			assertEquals(2, Scheduler.getQueue().size());
			new SuperComputer();
			s.update(Simulator.weekEndStart, Simulator.weekEndStart);
			
			assertEquals(1, Scheduler.getQueue().size());
			
//			Scheduler.submitJob(s2, 2);
//			Scheduler.submitJob(s3, 3);
//			s.update(1, 1);
			
		} catch(Exception e){
			System.out.println(e.getMessage());
			fail();
		}
	}

}
