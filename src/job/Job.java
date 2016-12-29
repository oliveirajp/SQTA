package job;
import simulator.SuperComputer;

public class Job {
	public static int SMAXNODE = 2;
	public final static int SMAXT = 60;
	public final static int SPRICE = 1;
	public static int MMAXNODE = (int) (0.1 * SuperComputer.nodes);
	public final static int MMAXT = 8 * 60;
	public final static int MPRICE = 8;
	public static int LMAXNODE = (int) (0.5 * SuperComputer.nodes);
	public final static int LMAXT = 16 * 60;
	public final static int LPRICE = 16;
	public static int HMAXNODE = SuperComputer.nodes;
	public final static int HMAXT = 64 * 60;
	public final static int HPRICE = 64;

	public int nodes;
	public int maxNodes;
	public int endTime;
	public int duration;
	public int price;
	public int jobType, assignedQueue;
	public int startTime, fixedDuration;

	public Job(int nodes, int duration) throws Exception {
		if(nodes > HMAXNODE || duration > HMAXT)
			throw new Exception("Job parameters wrong");
		this.nodes = nodes;
		this.duration = duration;
		if(nodes <= SMAXNODE && duration <= SMAXT){
			jobType = 1;
			maxNodes = SMAXNODE;
			endTime = SMAXT;
			price = SPRICE;
		} else if(nodes <= MMAXNODE && duration <= MMAXT){
			jobType = 2;
			maxNodes = MMAXNODE;
			endTime = MMAXT;
			price = MPRICE;
		}
		else if(nodes <= LMAXNODE && duration <= LMAXT){
			jobType = 3;
			maxNodes = LMAXNODE;
			endTime = LMAXT;
			price = LPRICE;
		}
		else{
			jobType = 4;
			maxNodes = HMAXNODE;
			endTime = HMAXT;
			price = HPRICE;
		}
		assignedQueue = jobType;
		price *= nodes;
		fixedDuration = duration;
		startTime = 1;
	}

	public void update(){
		endTime--;
		duration--;
	}

}
