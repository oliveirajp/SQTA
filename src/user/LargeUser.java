package user;

import simulationSystem.Job;

public class LargeUser extends User{

	public LargeUser(int budget) throws Exception {
		super(4, 10.0);
		int min = Job.HMAXNODE * Job.HPRICE;
		if(min > budget)
			throw new Exception("Initial budget too low");
		setBudget(budget);		
	}


}
