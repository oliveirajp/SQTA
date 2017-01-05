package user;

import simulationSystem.Job;

public class MediumUser extends User{

	public MediumUser(int budget) throws Exception {
		super(3, 5.0);
		int min = Job.LMAXNODE * Job.LPRICE;
		if(min > budget)
			throw new Exception("Initial budget too low");
		
		setBudget(budget);		
	}


}
