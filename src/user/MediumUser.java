package user;

import java.util.concurrent.ThreadLocalRandom;

import job.Job;

public class MediumUser extends User{

	public MediumUser(int maxBudget) throws Exception {
		super(3, 5.0);
		int min = Job.LMAXNODE * Job.LPRICE;
		if(min > maxBudget)
			throw new Exception("Initial budget too low");
		
		setBudget(ThreadLocalRandom.current().nextInt(min, maxBudget + 1));		
	}


}
