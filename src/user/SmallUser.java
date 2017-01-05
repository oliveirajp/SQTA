package user;

import simulationSystem.Job;

public class SmallUser extends User{

	public SmallUser(int budget) throws Exception {
		super(2, 2.0);
		int min = Job.MMAXNODE * Job.MPRICE;
		if(min > budget)
			throw new Exception("Initial budget too low");
		
		setBudget(budget);		
	}


}
