package simulationSystem; 
import java.util.ArrayList;

public class SuperComputer {
	public final static int nodes = 128;
	private static int smallNodes;
	private static int mediumNodes;
	private static int availableNodes;
	private static int weekendNodes;
	static public final int operatingCosts = 2;
	private static final int initSmallNodes = (int) (0.1 * nodes);
	private static final int initMediumNodes = (int) (0.3 * nodes);
	private static final int initAvailableNodes = nodes - (initSmallNodes + initMediumNodes);
	private static ArrayList<Job> activeJobs;

	public SuperComputer() {
		activeJobs = new ArrayList<Job>();
		smallNodes = initSmallNodes;
		mediumNodes = initMediumNodes;
		availableNodes = initAvailableNodes;
		weekendNodes = nodes;
	}

	public static void startJob(Job job, int time) throws Exception{
		int i;
		for(i = 0; i < activeJobs.size(); i++){
			if(activeJobs.get(i).endTime <= job.endTime)
				break;
		}
		switch (job.assignedQueue) {
		case 1:
			smallNodes -= job.nodes;
			Simulator.sNumbers++;
			Simulator.sWait += time - job.startTime;
			break;
		case 2:
			mediumNodes -= job.nodes;
			Simulator.mNumbers++;
			Simulator.mWait += time - job.startTime;
			break;
		case 3:
			availableNodes -= job.nodes;
			Simulator.lNumbers++;
			Simulator.lWait += time - job.startTime;
			break;
		case 4:
			weekendNodes -= job.nodes;
			Simulator.hNumbers++;
			Simulator.hWait += time - job.startTime;
			break;
		}
		activeJobs.add(i, job);
		if(smallNodes < 0 || mediumNodes < 0 || availableNodes < 0 || weekendNodes < 0)
			throw new Exception("Queue overused");
		
		if(weekendNodes != nodes)
			if(smallNodes != initSmallNodes || mediumNodes != initMediumNodes || availableNodes != initAvailableNodes)
			throw new Exception("WeekendQueue overused");
	}

	public static void update(int time){
		for (int i = 0; i < activeJobs.size(); i++) {
			activeJobs.get(i).update();

			if(activeJobs.get(i).duration == 0){
				Job j = activeJobs.get(i);
				switch (j.assignedQueue) {
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
					weekendNodes += j.nodes;
					break;
				}
				Simulator.getTurnAroundTimes().add((time - j.startTime) / j.fixedDuration);
				Simulator.hoursConsumed += (double)j.fixedDuration / 60.0;
				Simulator.nodesConsumed += j.nodes;
				activeJobs.remove(i);
				i--;				
			}
		}
	}

	public static ArrayList<Job> getActiveJobs() {
		return activeJobs;
	}

	public static int getSmallNodes() {
		return smallNodes;
	}

	public static void setSmallNodes(int smallNodes) {
		SuperComputer.smallNodes = smallNodes;
	}

	public static int getMediumNodes() {
		return mediumNodes;
	}

	public static void setMediumNodes(int mediumNodes) {
		SuperComputer.mediumNodes = mediumNodes;
	}

	public static int getAvailableNodes() {
		return availableNodes;
	}

	public static void setAvailableNodes(int availableNodes) {
		SuperComputer.availableNodes = availableNodes;
	}

	public static int getWeekendNodes() {
		return weekendNodes;
	}

	public static void setWeekendNodes(int weekendNodes) {
		SuperComputer.weekendNodes = weekendNodes;
	}
}
