package user;

import java.util.concurrent.ThreadLocalRandom;

import job.Job;

public class SmallUser extends User{

	public SmallUser(int maxBudget) {
		super(2, 2.0);
		int min = Job.MMAXNODE * Job.MMAXT;
		budget = ThreadLocalRandom.current().nextInt(min, maxBudget + 1);		
	}


}
