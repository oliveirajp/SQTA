package user;

import java.util.concurrent.ThreadLocalRandom;

import job.Job;

public class LargeUser extends User{

	public LargeUser(int maxBudget) throws Exception {
		super(4, 10.0);
		int min = Job.HMAXNODE * Job.HPRICE;
		if(min > maxBudget)
			throw new Exception("Initial budget too low");
		setBudget(ThreadLocalRandom.current().nextInt(min, maxBudget + 1));		
	}


}
