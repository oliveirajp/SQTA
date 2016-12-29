package user;

import java.util.concurrent.ThreadLocalRandom;

import job.Job;

public class SmallUser extends User{

	public SmallUser(int maxBudget) throws Exception {
		super(2, 2.0);
		int min = Job.MMAXNODE * Job.MPRICE;
		if(min > maxBudget)
			throw new Exception("Initial budget too low");
		
		setBudget(ThreadLocalRandom.current().nextInt(min, maxBudget + 1));		
	}


}
