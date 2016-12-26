package user;

import java.util.concurrent.ThreadLocalRandom;

import job.Job;

public class LargeUser extends User{

	public LargeUser(int maxBudget) {
		super(4, 10.0);
		int min = Job.HMAXNODE * Job.HMAXT;
		budget = ThreadLocalRandom.current().nextInt(min, maxBudget + 1);		
	}


}
