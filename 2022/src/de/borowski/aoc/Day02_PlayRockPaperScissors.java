package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day02_PlayRockPaperScissors {
	
	/**
	 * shape you selected:
	 * 1 for Rock (A, X)
	 * 2 for Paper (B, Y)
	 * 3 for Scissors (C, Z)
	 * 
	 * outcome of the round:
	 * 0 if you lost
	 * 3 if the round was a draw
	 * 6 if you won
	 */
	
	public static Map<String, Integer> map = new HashMap<String, Integer>();
	
	public static void main(String[] args) {
		BufferedReader reader;
		int totalScore = 0;
		
		map.put("A", 1);
		map.put("X", 1);
		map.put("B", 2);
		map.put("Y", 2);
		map.put("C", 3);
		map.put("Z", 3);

		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day02.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				// System.out.println(line);
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
		int player = map.get(x[1]);
		int roundScore = 0;
		
		// shape you selected
		roundScore = player;
		// outcome of the round
		if (player == opponent) {
			// round is a draw
			roundScore += 3;
		} else if ((player == 3 && opponent == 2) ||
				(player == 2 && opponent == 1) ||
				(player == 1 && opponent == 3)) {
			// player wins
			roundScore += 6;
		}
		return roundScore;
	}

}
