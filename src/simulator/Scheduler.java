package simulator;

import java.util.ArrayList;

import job.Job;
public class Scheduler {

	private static ArrayList<Job> queue;

	public Scheduler(){
		queue = new ArrayList<Job>();
	}

	public static void submitJob(Job j, int time){
		j.startTime = time;
		queue.add(j);
	}

	public void update(int fakeTime, int time) throws Exception{
		for(int i = 0; i < queue.size(); i++){
			if(fakeTime >= Simulator.weekEndStart && queue.get(i).jobType != 4)
				continue;
			else{
				switch (queue.get(i).jobType) {
				case 1:
					if(fakeTime + queue.get(i).endTime < Simulator.weekEndStart){
						if(queue.get(i).nodes <= SuperComputer.getSmallNodes()){
							SuperComputer.startJob(queue.get(i), time);
							queue.remove(i);
							i--;
							continue;
						}
					}
					else
						continue;
					break;
				case 2:
					if(fakeTime + queue.get(i).endTime < Simulator.weekEndStart){
						if(queue.get(i).nodes <= SuperComputer.getMediumNodes()){
							SuperComputer.startJob(queue.get(i), time);
							queue.remove(i);
							i--;
							continue;
						}
					}else
						continue;

					break;
				case 3:
					if(fakeTime + queue.get(i).endTime >= Simulator.weekEndStart){
						continue;
					}
					break;
				case 4:
					if(fakeTime == Simulator.weekEndStart){
						if(queue.get(i).nodes <= SuperComputer.getWeekendNodes()){
							SuperComputer.startJob(queue.get(i), time);
							queue.remove(i);
							i--;
						}
						continue;
					} else
						continue;
				}
				if(queue.get(i).nodes <= SuperComputer.getAvailableNodes()){
					if(i != 0){
						if(SuperComputer.getActiveJobs().size() > 0){
							if(queue.get(i).endTime <= SuperComputer.getActiveJobs().get(0).endTime){
								queue.get(i).assignedQueue = 3;
								SuperComputer.startJob(queue.get(i), time);
								queue.remove(i);
								i--;
								continue;
							}
						}
						else{
							queue.get(i).assignedQueue = 3;
							SuperComputer.startJob(queue.get(i), time);
							queue.remove(i);
							i--;
							continue;
						}
					}
					else{
						queue.get(i).assignedQueue = 3;
						SuperComputer.startJob(queue.get(i), time);
						queue.remove(i);
						i--;
						continue;
					}
				}
			}
		}
	}

	public static ArrayList<Job> getQueue() {
		return queue;
	}
}
