package job;
import simulator.SuperComputer;

public class Job {
	public static int SMAXNODE = 2;
	public final static int SMAXT = 60;
	public static int MMAXNODE = (int) (0.1 * SuperComputer.nodes);
	public final static int MMAXT = 8 * 60;
	public static int LMAXNODE = (int) (0.5 * SuperComputer.nodes);
	public final static int LMAXT = 16 * 60;
	public static int HMAXNODE = SuperComputer.nodes;
	public final static int HMAXT = 64 * 60;

	public int nodes;
	public int maxNodes;
	public int endTime;
	public int duration;
	public int price;
	public int queue;
	public int waitTime;

	public Job(int nodes, int duration) {
		this.nodes = nodes;
		this.duration = duration;
		if(nodes <= SMAXNODE && duration <= SMAXT){
			queue = 1;
			maxNodes = SMAXNODE;
			endTime = SMAXT;
		} else if(nodes <= MMAXNODE && duration <= MMAXT){
			queue = 2;
			maxNodes = MMAXNODE;
			endTime = MMAXT;
		}
		else if(nodes <= LMAXNODE && duration <= LMAXT){
			queue = 3;
			maxNodes = LMAXNODE;
			endTime = LMAXT;
		}
		else{
			queue = 4;
			maxNodes = HMAXNODE;
			endTime = HMAXT;
		}	
		price = nodes * (endTime / 60);
	}

	public void update(){
		endTime--;
		duration--;
	}

}