package simulator; 
import job.*;

import java.util.ArrayList;

public class SuperComputer {
	public final static int nodes = 128;
	static int smallNodes;
	static int mediumNodes;
	static int availableNodes;
	static ArrayList<Job> activeJobs;

	public SuperComputer() {
		activeJobs = new ArrayList<Job>();
		smallNodes = (int) (0.1 * nodes);
		mediumNodes = (int) (0.3 * nodes);
		availableNodes = nodes - (smallNodes + mediumNodes);
	}

	public static void startJob(Job job, int time){
		int i;
		for(i = 0; i < activeJobs.size(); i++){
			if(activeJobs.get(i).endTime <= job.endTime)
				break;
		}
		switch (job.queue) {
		case 1:
			smallNodes -= job.nodes;
			break;
		case 2:
			mediumNodes -= job.nodes;
			break;
		case 3:
			availableNodes -= job.nodes;
			break;
		case 4:
			//todo
			availableNodes -= job.nodes;
			break;
		}
		job.waitTime = time - job.waitTime;
		Simulator.waitTimes.add(job.waitTime);
		activeJobs.add(i, job);			
	}

	public void update(){
	//	System.out.println("Small " + smallNodes + "Medium " + mediumNodes + "Rest " + availableNodes);
		for (int i = 0; i < activeJobs.size(); i++) {
			activeJobs.get(i).update();

			if(activeJobs.get(i).duration == 0){
				Job j = activeJobs.get(i);
				switch (j.queue) {
				case 1:
					smallNodes += j.nodes;
					break;
				case 2:
					mediumNodes += j.nodes;
					break;
				case 3:
					availableNodes += j.nodes;
					break;
				case 4:
					//todo
					break;
				}
				activeJobs.remove(i);
				i--;
			}
		}
	}
}
