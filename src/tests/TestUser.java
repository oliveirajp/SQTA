package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import job.Job;
import simulator.Scheduler;
import user.LargeUser;
import user.MediumUser;
import user.SmallUser;
import user.User;

public class TestUser {

	@Test
	public void testUser() {
		User u1;
		try {
			u1 = new SmallUser(10000);
			assertEquals(2,u1.getMaxJob());
			assertEquals(0,u1.getTime());
			assertTrue(2.0 == u1.getJobsPerDay());
			assertTrue(2.0 / (60.0*24.0) == u1.getSubmitRate());
			assertEquals(Job.MMAXNODE, u1.getMaxNodes());
			assertEquals(Job.MMAXT, u1.getMaxDuration());

			User u2 = new MediumUser(100000);
			assertEquals(Job.LMAXNODE, u2.getMaxNodes());
			assertEquals(Job.LMAXT, u2.getMaxDuration());

			User u3 = new LargeUser(1000000);
			assertEquals(Job.HMAXNODE, u3.getMaxNodes());
			assertEquals(Job.HMAXT, u3.getMaxDuration());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testUpdate() {
		new Scheduler();
		try{
			User u1 = new MediumUser(100000);
			int time = u1.getTime();
			u1.update(1);
			assertEquals(time + 1, u1.getTime());

			//change cycle limit to test more cases
			for(int i = 2; i < 1000; i++){
				u1.update(i);
				time = u1.getTime();
				assertTrue(u1.getProbability()>= 0 && u1.getProbability() <= 1);
				assertTrue(u1.getChance()>= 0 && u1.getChance() <= 1);
				if(u1.getChance() <= u1.getProbability())
					assertEquals(0, u1.getTime());
				else
					assertEquals(time, u1.getTime());
			}
			u1.setBudget(0);
			time = u1.getTime();
			u1.update(10);
			assertEquals(time, u1.getTime());
		} catch(Exception e){
			System.err.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testRandomizeJob() {
		new Scheduler();
		try{
			User u1 = new MediumUser(100000);
			u1.setTime(20);
			int budget = u1.getBudget();
			Job j;
			try{
				j = u1.randomizeJob();
				assertTrue(j != null);
				assertEquals(budget - j.price, u1.getBudget());
				//change cycle limit to test more cases
				for(int i = 1; i < 1000; i++){
					u1.setTime(i);
					j = u1.randomizeJob();
					assertTrue(j != null);
					assertTrue(u1.jobNodes >= 1 && u1.jobNodes <= u1.getMaxNodes());
					assertTrue(u1.jobTime >= 1 && u1.jobTime <= u1.getMaxDuration());
				}
				fail();
			} catch(Exception e){
				assertEquals("Null budget", e.getMessage());
			}
		} catch(Exception e){
			System.err.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testCalculateMaxNodes() {
		//Large user - Huge queue
		try{
			User u1 = new LargeUser(1000000);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.HMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.HMAXT);
			u1.setBudget(Job.HPRICE * 2);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.HMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.HMAXT);

			//Large user - Large queue
			u1.setBudget(Job.LPRICE + 10);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.LMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.LMAXT);

			//Large user - Medium queue
			u1.setBudget(Job.MPRICE + 4);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.MMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.MMAXT);

			//Large user - Small queue
			u1.setBudget(Job.SPRICE + 1);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.SMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.SMAXT);

			//Medium user - Large queue
			u1 = new MediumUser(1000000);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.LMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.LMAXT);

			//Medium user - Medium queue
			u1.setBudget(Job.MPRICE + 4);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.MMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.MMAXT);

			//Medium user - Small queue
			u1.setBudget(Job.SPRICE + 1);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.SMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.SMAXT);

			//Small User - Medium queue
			u1 = new SmallUser(100000);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.MMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.MMAXT);

			//Small User - Small queue
			u1.setBudget(Job.SPRICE + 1);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.SMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.SMAXT);

			//Boundary tests
			//0 budget
			u1.setBudget(0);
			u1.calculateMaxNodes();
			assertEquals(u1.getMaxNodes(),0);
			assertEquals(u1.getMaxDuration(),0);

			//Max job restriction
			u1.setMaxJob(4);
			u1.setBudget(Job.HPRICE * 1000);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.HMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.HMAXT);

			u1.setMaxJob(3);
			u1.setBudget(Job.HPRICE * 1000);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.LMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.LMAXT);

			u1.setMaxJob(2);
			u1.setBudget(Job.HPRICE * 1000);
			u1.calculateMaxNodes();
			assertTrue(u1.getMaxNodes() <= Job.MMAXNODE);
			assertTrue(u1.getMaxDuration() <= Job.MMAXT);

		} catch(Exception e){
			System.err.println(e.getMessage());
			fail();
		}

	}

}
