package user;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import simulationSystem.Job;
import simulationSystem.Scheduler;
import simulationSystem.Simulator;

public abstract class User {
	static int id = 0;
	private int budget;
	private int maxJob;
	private int maxNodes;
	private int maxDuration;
	private double submitRate;
	private int time;
	private double jobsPerDay;
	private double probability;
	private double chance;
	public int jobNodes, jobTime;
	FileWriter fw;
	BufferedWriter bw;
	public User(int maxJob, double jobsPerDay) {
		id++;
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
		this.submitRate = jobsPerDay/(60.0*24.0);
		time = 0;
	}

	public void update(int x) throws Exception{
		if(budget > 0){
			time++;
			probability = 1 - Math.exp(-submitRate * time);
			chance = ThreadLocalRandom.current().nextDouble();
			if(chance <= probability){
				Scheduler.submitJob(randomizeJob(), x);
				time = 0;
			}
		}

	}

	public Job randomizeJob() throws Exception{
		if(budget > 0){
			calculateMaxNodes();
			jobNodes = (int) (1 + ((1 - Math.exp(-(0.15 * ((double)maxNodes / 128.0)) * (double)time)) * (double)maxNodes));
			jobTime = (int) (1 + ((1 - Math.exp(-(0.15 * ((double)maxNodes / 128.0)) * (double)time))  * (double)maxDuration ));
			Job job = new Job(jobNodes, jobTime);
			budget -= job.price;

			Simulator.pricePaidByUsers += job.price;
			return job;
		}
		else
			throw new Exception("Null budget");
	}

	public void calculateMaxNodes(){
		if(budget >= Job.HPRICE && maxJob == 4){
			maxNodes = budget / Job.HPRICE;
			maxNodes = maxNodes < Job.HMAXNODE ? maxNodes : Job.HMAXNODE;
			maxDuration = Job.HMAXT;			
		} else if(budget >= Job.LPRICE && maxJob >= 3){
			maxNodes = budget / Job.LPRICE;
			maxNodes = maxNodes < Job.LMAXNODE ? maxNodes : Job.LMAXNODE;
			maxDuration = Job.LMAXT;			
		}else if(budget >= Job.MPRICE){
			maxNodes = budget / Job.MPRICE;
			maxNodes = maxNodes < Job.MMAXNODE ? maxNodes : Job.MMAXNODE;
			maxDuration = Job.MMAXT;			
		} else if(budget >= Job.SPRICE){
			maxNodes = budget / Job.SPRICE;
			maxNodes = maxNodes < Job.SMAXNODE ? maxNodes : Job.SMAXNODE;
			maxDuration = Job.SMAXT;
		}
		else{
			maxNodes = 0;
			maxDuration = 0;
		}
	}

	public int getMaxJob() {
		return maxJob;
	}

	public void setMaxJob(int maxJob) {
		this.maxJob = maxJob;
	}

	public double getJobsPerDay() {
		return jobsPerDay;
	}

	public int getMaxNodes() {
		return maxNodes;
	}


	public int getMaxDuration() {
		return maxDuration;
	}
	public double getSubmitRate() {
		return submitRate;
	}
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getProbability() {
		return probability;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public double getChance() {
		return chance;
	}
}
