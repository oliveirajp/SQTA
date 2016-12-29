package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import simulator.Simulator;
import simulator.SuperComputer;

public class Main {

	public static void main(String[] args) {
		Simulator s = new Simulator(5);
		int x = 0;
		int days = 60*24*30;

		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("waitTimes.csv");
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		s.destroy();
		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
