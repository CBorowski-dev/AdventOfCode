package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

public class Day09_RopeBridge {
	
	private static HashSet<Position> uniquePositions = new HashSet<Position>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		Position posHead = new Position(0, 0);
		Position posTail = new Position(0, 0);
		
		uniquePositions.add(posTail.copy());
		
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day09.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				// System.out.println(line);
				moveHead(posHead, posTail, line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(uniquePositions.size());
	}

	private static void moveHead(Position posHead, Position posTail, String line) {
		String[] moveComponents = line.split(" ");
		Direction d = Direction.LEFT;
		switch(moveComponents[0]) {
		case "L":
			d = Direction.LEFT;
			break;
		case "R":
			d = Direction.RIGHT;
			break;
		case "D":
			d = Direction.DOWN;
			break;
		case "U":
			d = Direction.UP;
		}
		int max = Integer.parseInt(moveComponents[1]);
		for (int i=0; i<max; i++) {
			posHead.move(d);
			moveTail(posHead, posTail);
			uniquePositions.add(posTail.copy());
		}
	}

	private static void moveTail(Position posHead, Position posTail) {
		if (distance(posHead.x, posTail.x) <= 1 && distance(posHead.y, posTail.y) <= 1) {
			return;
		}
		if (posHead.x == posTail.x) {
			// adjust y in tail
			posTail.y = (posHead.y - posTail.y)/2 + posTail.y;
			return;
		}
		if (posHead.y == posTail.y) {
			// adjust x in tail
			posTail.x = (posHead.x - posTail.x)/2 + posTail.x;
			return;
		}
		if (distance(posHead.x, posTail.x) < distance(posHead.y, posTail.y)) {
			 posTail.x = posHead.x;
			 posTail.y = (posHead.y - posTail.y)/2 + posTail.y;
		} else {
			 posTail.y = posHead.y;
			 posTail.x = (posHead.x - posTail.x)/2 + posTail.x;
		}
		
	}
	
	private static int distance(int v1, int v2) {
		return Math.abs(v1-v2);
	}

	public static class Position {
		int x, y = 0; // start position
		
		public Position(int x2, int y2) {
			x = x2;
			y = y2;
		}

		public Position move(Direction d) {
			switch(d) {
			case LEFT:
				x -= 1;
				break;
			case RIGHT:
				x += 1;
				break;
			case DOWN:
				y -= 1;
				break;
			case UP:
				y += 1;
			}
			return this;
		}
		
		public Position copy() {
			return new Position(x, y);
		}
		
		@Override
		public String toString() {
			return x + " " + y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			return x == other.x && y == other.y;
		}
		
	}
	
	public enum Direction {
		LEFT, RIGHT, UP, DOWN;
	}
}

