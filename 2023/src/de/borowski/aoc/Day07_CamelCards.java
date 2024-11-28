package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Day07_CamelCards {

	public static List<Hand> hands = new ArrayList<Hand>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day07.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				// System.out.println(line);
				hands.add(splitLine(line));
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sortHands();
		System.out.println("--------------------------------");
		System.out.println(determinateTotalWinnings());
	}

	private static int determinateTotalWinnings() {
		int index = 1;
		int totalWinnings = 0;
		for (Hand h : hands) {
			System.out.println(h);
			totalWinnings = totalWinnings + (h.bid * index);
			index++;
		}
		return totalWinnings;
	}

	private static void sortHands() {
		Collections.sort(hands);
	}

	private static Hand splitLine(String line) {
		int[] hand = new int[5];
		String[] s = line.split(" ");
		char[] chars = s[0].toCharArray();
		for (int i=0; i<5; i++) {
			if (chars[i] == 'A') {
				hand[i] = 13;
			} else if (chars[i] == 'K') {
				hand[i] = 12;
			} else if (chars[i] == 'Q') {
				hand[i] = 11;
			} else if (chars[i] == 'J') {
				hand[i] = 1; // vorher 11
			} else if (chars[i] == 'T') {
				hand[i] = 10;
			} else {
				hand[i] = Integer.parseInt(Character.toString(chars[i]));
			}
		}
		return new Hand(hand, Integer.parseInt(s[1]), determinatePair(hand));
	}

	/**
	 * For part 2
	 * @param hand
	 * @return
	 */
	private static int determinatePair(int[] hand) {
		boolean two = false;
		boolean three = false;
		int[] counter = new int[14];
		for (int i : hand) {
			counter[i]++;
		}
		int finalResult = 0;
		for (int i=2;i<=13;i++) {
			if (two && counter[i]==2) {
				return counter[1]>0 ? 5 : 3;
			} else if (counter[i]==2) {
				two = true;
			} else if (counter[i]==3) {
				three = true;
			} else if (counter[i]==4) {
				return counter[1]>0 ? 7 : 6;
			} else if (counter[i]==5) {
				return 7;
			}
		}
		if (!two && !three) {
			if (counter[1]==5) return 7;
			if (counter[1]==4) return 7;
			if (counter[1]==3) return 6;
			if (counter[1]==2) return 4;
			if (counter[1]==1) return 2;
			return 1;
		} else if (two && !three) {
			if (counter[1]==1) return 4;
			if (counter[1]==2) return 6;
			if (counter[1]==3) return 7;
			return 2;
		} else if (!two && three) {
			if (counter[1]==1) return 6;
			if (counter[1]==2) return 7;
			return 4;
		} else if (two && three) {
			return 5;
		}
		return finalResult;
	}

	/*
	private static int determinatePair(int[] hand) {
		boolean two = false;
		boolean three = false;
		int[] counter = new int[15];
		for (int i : hand) {
			counter[i]++;
		}
		for (int i=1;i<=14;i++) {
			if (two && counter[i]==2) {
				return 3;
			} else if (counter[i]==2) {
				two = true;
			} else if (counter[i]==3) {
				three = true;
			} else if (counter[i]==4) {
				return 6;
			} else if (counter[i]==5) {
				return 7;
			}
		}
		if (!two && !three) {
			return 1;
		} else if (two && !three) {
			return 2;
		} else if (!two && three) {
			return 4;
		} else if (two && three) {
			return 5;
		}

		return 3;
	}*/

	public static class Hand implements Comparable {
		int[] hand;
		int bid;
		int pair = 0;

		public Hand(int[] hand, int bid, int pair) {
			this.hand = hand;
			this.bid = bid;
			this.pair = pair;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Hand hand1 = (Hand) o;
			return bid == hand1.bid && Arrays.equals(hand, hand1.hand);
		}

		@Override
		public int compareTo(Object o) {
			Hand other = (Hand) o;
			if (pair>other.pair) {
				return 1;
			} else if (pair<other.pair) {
				return -1;
			} else {
				for (int i = 0; i < 5; i++) {
					if (hand[i] > other.hand[i]) {
						return 1;
					} else if (hand[i] < other.hand[i]) {
						return -1;
					}
				}
			}
			return 0;
		}

		@Override
		public String toString() {
			return "Hand{" +
					"hand=" + Arrays.toString(hand) +
					", bid=" + bid +
					", pair=" + pair +
					'}';
		}

		@Override
		public int hashCode() {
			int result = Objects.hash(bid);
			result = 31 * result + Arrays.hashCode(hand);
			return result;
		}

	}

}
