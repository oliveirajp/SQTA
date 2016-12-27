package user;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import job.*;
import simulator.Scheduler;

public class User {
	int budget;
	int maxJob;
	int maxNodes;
	int maxDuration;
	double submitRate;
	int time;
	double jobsPerDay;
	double corrector;
	FileWriter fw;
	BufferedWriter bw;
	public User(int maxJob, double jobsPerDay) {
		this.maxJob = maxJob;
		this.jobsPerDay = jobsPerDay;
		switch (maxJob) {
		case 2:
			maxNodes = Job.MMAXNODE;
			maxDuration = Job.MMAXT;
			break;
		case 3:
			maxNodes = Job.LMAXNODE;
			maxDuration = Job.LMAXT;
			break;
		case 4:
			maxNodes = Job.HMAXNODE;
			maxDuration = Job.HMAXT;
			break;
		}
		corrector = 1/(55.0/64.0);//1/(1-Math.exp(-((0.5*maxNodes)/(60*24)) * 80));
		this.submitRate = jobsPerDay/(60*24);
		try {
			fw = new FileWriter("test4.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
	}

	public void update(int x){
		time++;

		double p = 1 - Math.exp(-submitRate * time);
		double test = ThreadLocalRandom.current().nextDouble();
		if(test <= p){
			Scheduler.submitJob(randomizeJob(), x);
			time = 1;
		}

	}

	public void destroy(){
		try {
			bw.close();
			fw.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Job randomizeJob(){
		int jobNodes = (int) (1 + ((1 - Math.exp(-(0.15 * ((double)maxNodes / 128.0)) * (double)time)) * (double)maxNodes));
		int jobTime = (int) (1 + ((1 - Math.exp(-(0.15 * ((double)maxNodes / 128.0)) * (double)time))  * (double)maxDuration ));
		Job job = new Job(jobNodes, jobTime);
		budget -= job.price;
		//calculateMaxNodes();
		return job;
	}

	/*void calculateMaxNodes(){
		switch (maxJob) {
		case 2:
			
			break;
		case 3:

			break;
		case 4:
			if(budget / Job.HPRICE < 1)
				
			break;
		}
		if(budget / Job.HPRICE < 1)
			maxNodes = Job.LMAXNODE;
		if(budget < Job.LMAXNODE)
			maxNodes = Job.MMAXNODE;
		if(budget < Job.MMAXNODE)
			maxNodes = Job.SMAXNODE;

	}*/
}
