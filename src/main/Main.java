package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import simulationSystem.Simulator;
import simulationSystem.SuperComputer;

public class Main {

	private static final String FILENAME = "filename.txt";
	private static void outputInfo(){
		System.out.println("XXX represents the number. Only numbers are needed");
		System.out.println("==============================================================");
		System.out.println("XXX - time in minutes for simulation to be executed");
		System.out.println("XXX - number of users to be created");
		System.out.println("XXX XXX ... - User classes in order (only 1 - Small, 2 - Medium and 3 - Large are allowed");
		System.out.println("XXX XXX ... - User budgets in order");
	}
	public static void main(String[] args) {
		BufferedReader br = null;
		FileReader fr = null;

		int x = 0;
		int days = 60*24*30;
		int users = 0;
		int[] userTypes = null;
		int[] budgets = null;
		try {
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				switch (i) {
				case 0:
					try{
						days = Integer.parseInt(sCurrentLine);
					} catch (Exception e) {
						System.err.println("Error when parsing file");
						outputInfo();
						System.exit(0);
					}
					break;
				case 1:
					try{
						users = Integer.parseInt(sCurrentLine);
					} catch (Exception e) {
						System.err.println("Error when parsing file");
						outputInfo();
						System.exit(0);
					}
					break;
				case 2:
					try {					
						userTypes = Arrays.asList(sCurrentLine.split(" "))
								.stream()
								.map(String::trim)
								.mapToInt(Integer::parseInt).toArray();
					} catch (Exception e) {
						System.err.println("Error when parsing file");
						outputInfo();
						System.exit(0);
					}
					break;
				case 3:
					try{
						budgets = Arrays.asList(sCurrentLine.split(" "))
								.stream()
								.map(String::trim)
								.mapToInt(Integer::parseInt).toArray();
					} catch (Exception e) {
						System.err.println("Error when parsing file");
						outputInfo();
						System.exit(0);
					}
					break;
				default:
					System.err.println("Input file badly formatted, file should follow the following format");
					outputInfo();
					System.exit(0);
					break;
				}
				i++;
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			outputInfo();
			e.printStackTrace();
		}
		if(userTypes.length != users){
			System.err.println("Not enough user types");
			outputInfo();
			System.exit(0);
		}
		if(budgets.length != users){
			System.err.println("Not enough budgets");
			outputInfo();
			System.exit(0);
		}
		if(users <= 0){
			System.err.println("Not enough users");
			outputInfo();
			System.exit(0);
		}
		if(days <= 0){
			System.err.println("Not enough time to run simulation");
			outputInfo();
			System.exit(0);
		}
		Simulator s = new Simulator(users, userTypes, budgets);

		while(x < days){
			s.update();
			x++;
		}
		System.out.println("Short queue:");
		System.out.println("\tAverage throughput per week: " + ((double)Simulator.sNumbers / (double)s.getWeek()));
		System.out.println("\tAverage waitTime: " + ((double)Simulator.sWait / (double)Simulator.sNumbers));
		System.out.println("Medium queue:");
		System.out.println("\tAverage throughput per week: " + ((double)Simulator.mNumbers / (double)s.getWeek()));
		System.out.println("\tAverage waitTime: " + ((double)Simulator.mWait / (double)Simulator.mNumbers));
		System.out.println("Long queue:");
		System.out.println("\tAverage throughput per week: " + ((double)Simulator.lNumbers / (double)s.getWeek()));
		System.out.println("\tAverage waitTime: " + ((double)Simulator.lWait / (double)Simulator.lNumbers));
		System.out.println("Huge queue:");
		System.out.println("\tAverage throughput per week: " + ((double)Simulator.hNumbers / (double)s.getWeek()));
		System.out.println("\tAverage waitTime: " + ((double)Simulator.hWait / (double)Simulator.hNumbers));

		System.out.println("Machine-hours consumed: ");
		System.out.println("\tTotal hours: "+ Simulator.hoursConsumed);
		System.out.println("\tTotal nodes: "+ Simulator.nodesConsumed);
		System.out.println("Price paid by users: " + Simulator.pricePaidByUsers);
		System.out.println("Economic balance: " + (Simulator.pricePaidByUsers - (SuperComputer.operatingCosts * ((double)x / 60.0))));
		double sum = 0;
		for (int turnAround : Simulator.getTurnAroundTimes()) {
			sum += turnAround;
		}
		sum = sum / Simulator.getTurnAroundTimes().size();
		System.out.println("Average turnaround time: " + sum);
	}

}
