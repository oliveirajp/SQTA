package user;

import java.util.concurrent.ThreadLocalRandom;

import job.Job;

public class MediumUser extends User{

	public MediumUser(int maxBudget) {
		super(3, 5.0);
		int min = Job.LMAXNODE * Job.LMAXT;
		budget = ThreadLocalRandom.current().nextInt(min, maxBudget + 1);		
	}


}
