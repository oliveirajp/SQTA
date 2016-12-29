package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import simulator.Simulator;

public class TestSimulator {

	@Test
	public void testSimulator() {
		//argument is number of users
		Simulator s = new Simulator(10);
		assertTrue(Simulator.getTurnAroundTimes() != null);
		assertTrue(s.getScheduler() != null);
		assertTrue(s.getUsers() != null);
		assertEquals(0, s.getWeek());
		assertEquals(0, s.getTime());
		assertEquals(10, s.getUsers().size());
	}

	@Test
	public void testUpdate() {
		Simulator s = new Simulator(5);
		int time = s.getTime();
		
		s.update();
		assertEquals(time + 1, s.getTime());
		assertTrue(s.getTime() <= s.getRealTime());
		
		s.setTime(Simulator.weekEndStop - 1);
		int week = s.getWeek();
		s.update();
		assertEquals(week + 1, s.getWeek());
		assertEquals(0, s.getTime());
		assertTrue(s.getTime() <= s.getRealTime());
	}
}
