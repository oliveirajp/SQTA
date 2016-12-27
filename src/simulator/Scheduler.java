package simulator;

import java.util.ArrayList;

import job.Job;
public class Scheduler {

	static ArrayList<Job> queue;
	//static int freedCores;

	public Scheduler(){
		queue = new ArrayList<Job>();
	}

	public static void submitJob(Job j, int time){
		j.waitTime = time;
		queue.add(j);
	}

	public void update(int time){
		//System.out.println("Previous " + queue.size());
		for(int i = 0; i < queue.size(); i++){
			switch (queue.get(i).queue) {
			case 1:
				if(queue.get(i).nodes <= SuperComputer.smallNodes){
					SuperComputer.startJob(queue.get(i), time);
					queue.remove(i);
					i--;
					continue;
				}
				break;
			case 2:
				if(queue.get(i).nodes <= SuperComputer.mediumNodes){
					SuperComputer.startJob(queue.get(i), time);
					queue.remove(i);
					i--;
					continue;
				}	
				break;
			case 3:
				break;
			case 4:
				break;
			}
			if(queue.get(i).nodes <= SuperComputer.availableNodes){
				if(i != 0){
					if(SuperComputer.activeJobs.size() > 0){
						if(queue.get(i).endTime <= SuperComputer.activeJobs.get(0).endTime){
							SuperComputer.startJob(queue.get(i), time);
							queue.remove(i);
							i--;
							continue;
						}
					}
					else{
						SuperComputer.startJob(queue.get(i), time);
						queue.remove(i);
						i--;
						continue;
					}
				}
				else{
					SuperComputer.startJob(queue.get(i), time);
					queue.remove(i);
					i--;
					continue;
				}
			}
		}
		//System.out.println("Last " + queue.size());
	}
}
