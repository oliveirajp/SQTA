package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import simulator.Simulator;

public class Main {

	public static void main(String[] args) {
		Simulator s = new Simulator(5);
		int x = 0;
		int days = 60*24*7;

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
			//System.out.println(x);
		}
		for (int i = 0; i < Simulator.waitTimes.size();i++) {
			try {
				bw.write(Simulator.waitTimes.get(i) + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		s.destroy();
		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
