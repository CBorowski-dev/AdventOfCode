package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day02_PlayRockPaperScissors_Part2 {
	
	/**
	 * shape you selected:
	 * 1 for Rock (A)
	 * 2 for Paper (B)
	 * 3 for Scissors (C)
	 * 
	 * outcome of the round:
	 * 0 if you lost (X)
	 * 3 if the round was a draw (Y)
	 * 6 if you won (Z)
	 */
	
	public static Map<String, Integer> map = new HashMap<String, Integer>();
	
	public static void main(String[] args) {
		BufferedReader reader;
		int totalScore = 0;
		
		map.put("A", 1);
		map.put("B", 2);
		map.put("C", 3);

		map.put("X", 0);
		map.put("Y", 3);
		map.put("Z", 6);

		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day02.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				System.out.println(line);
				totalScore += computeRoundScore(line);
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("--------------------------------");
		System.out.println("Score : " + totalScore);
	}
	
	public static int computeRoundScore(String line) {
		
		String[] x = line.split(" ");
		int opponent = map.get(x[0]);
		int neededOutcome = map.get(x[1]);
		int roundScore = 0;
		
		// outcome of the round
		roundScore = neededOutcome;
//		System.out.println("--> " + roundScore);
		// shape you selected
		if (neededOutcome == 3) {
			// round is a draw --> select the same item
			roundScore += opponent;
//			System.out.println("--> " + roundScore);
		} else if (neededOutcome == 0) {
			// need to loose --> select weaker item
			if (opponent == 1) {
				roundScore += 3;
//				System.out.println("--> " + roundScore);
			} else {
				roundScore += (opponent - 1);
//				System.out.println("--> " + roundScore);
			}
		} else {
			// need to win --> select stronger item
			if (opponent == 3) {
				roundScore += 1;
//				System.out.println("--> " + roundScore);
			} else {
				roundScore += (opponent + 1);
//				System.out.println("--> " + roundScore);
			}
		}
		return roundScore;
	}

}
