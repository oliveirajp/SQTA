package simulationSystem;
import java.util.ArrayList;

import user.LargeUser;
import user.MediumUser;
import user.SmallUser;
import user.User;

public class Simulator {
	private Scheduler sched;
	public final static int weekEndStart = 6240;
	public final static int weekEndStop = 6240 + 3840;
	private ArrayList<User> users;
	private static ArrayList<Integer> turnAroundTimes;
	static public int sNumbers,mNumbers, lNumbers, hNumbers;
	static public int sWait,mWait, lWait, hWait;
	static public int nodesConsumed;
	static public double hoursConsumed;
	static public int pricePaidByUsers;
	private int time;
	private int week;
	private int realTime;

	public Simulator(int userSize, int[] userTypes, int[] budgets) {
		turnAroundTimes = new ArrayList<Integer>();
		new SuperComputer();
		sched = new Scheduler();
		//int randomType;
		users = new ArrayList<User>();
		time = 0;
		week = 0;
		realTime = 0;
		sNumbers = mNumbers = lNumbers = hNumbers = 0;
		sWait = mWait = lWait = hWait = 0;
		nodesConsumed = 0;
		hoursConsumed = 0.0;
		pricePaidByUsers = 0;
		try{
			/*for(int i = 0; i < userSize; i++)
			{
				randomType = ThreadLocalRandom.current().nextInt(1, 4);
				switch (randomType) {
				case 1: users.add(new SmallUser(10000));
				break;
				case 2:
					users.add(new MediumUser(100000));
					break;
				case 3:
					users.add(new LargeUser(1000000));
					break;
				}
			}*/
			int budget;
			for (int i = 0; i < userSize; i++) {
				budget = budgets[i];
				switch (userTypes[i]) {
				case 1: 
					users.add(new SmallUser(budget));
					break;
				case 2:
					users.add(new MediumUser(budget));
					break;
				case 3:
					users.add(new LargeUser(budget));
					break;
				default:
					System.err.println("Incorrect user type");
					System.exit(0);
					break;
				}
			}
		} catch (Exception e){
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}

	public void update(){
		time++;
		if(time == weekEndStop){
			week++;
			time = 0;
		}
		realTime = time + week * weekEndStop;
		SuperComputer.update(realTime);
		for (User user : users) {
			try{
				user.update(realTime);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.exit(0);
			}
		}
		try {
			sched.update(time, realTime);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}

	public static ArrayList<Integer> getTurnAroundTimes() {
		return turnAroundTimes;
	}

	public Scheduler getScheduler() {
		return sched;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getWeek() {
		return week;
	}

	public int getRealTime() {
		return realTime;
	}
}
