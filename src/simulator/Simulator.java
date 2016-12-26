package simulator;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import user.*;

public class Simulator {
	int time;
	SuperComputer sc;
	Scheduler sched;
	ArrayList<User> users;
	public static ArrayList<Integer> waitTimes;
	
	public Simulator(int userSize) {
		time = 0;
		waitTimes = new ArrayList<Integer>();
		sc = new SuperComputer();
		sched = new Scheduler();
		int randomType;
		users = new ArrayList<User>();
		for(int i = 0; i < userSize; i++)
		{
			randomType = ThreadLocalRandom.current().nextInt(1, 4);
			switch (randomType) {
			case 1: users.add(new SmallUser(10000));
				break;
			case 2:
				users.add(new MediumUser(100000));
				break;
			case 3:
				users.add(new MediumUser(1000000));
				//users.add(new LargeUser(1000000));
				break;
			}
		}
	}
	
	public void update(){
		time++;
		sc.update();
		for (User user : users) {
			user.update(time);
		}
		sched.update(time);
	}
	public void destroy(){
		for (User user : users) {
			user.destroy();
		}
	}
}
