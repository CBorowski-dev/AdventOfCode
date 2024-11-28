package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class Day22_SandSlabs {

	public static List<Cube> cubeRepo = new ArrayList<Cube>();
	public static Queue<Cube> priorityQueue = new PriorityQueue<>((o1, o2) -> {
		if (o1.endBricks[0].z == o2.endBricks[0].z) return 0;
		return o1.endBricks[0].z < o2.endBricks[0].z ? -1 : 1;
	});

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day22_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day22.txt"));
			String line = reader.readLine();

			int z = 0;
			while (line != null) {
				Cube c = new Cube(line);
				priorityQueue.add(c);
				line = reader.readLine();
			}
			while (!priorityQueue.isEmpty()) {
				Cube c = priorityQueue.poll();
				cubeRepo.add(c);
				c.settleDown(cubeRepo);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// printRepo();
		System.out.println("--------------------------------");
		System.out.println(countRemovableCubes(cubeRepo));
	}

	private static void printRepo() {
		int size = cubeRepo.size();
		for (int i=0; i<size; i++) {
			System.out.println(cubeRepo.get(i));
		}
	}

	private static int countRemovableCubes(List<Cube> cubeRepo) {
		int count = 1;
		int size = cubeRepo.size();
		for (int i=0; i<size-1; i++) {
			Cube c = cubeRepo.get(i);
			if (c.aboveCubes.size() == 0) count++;
			else {
				boolean dependantCubeExists = false;
				for (int x=0; x<c.aboveCubes.size() && !dependantCubeExists; x++) {
					Cube ac = c.aboveCubes.get(x);
					if (ac.downCubes.size()==1)	dependantCubeExists = true;
				}
				if (!dependantCubeExists)	count++;
			}
		}
		return count;
	}

	public static class Cube {
		Coordinate[] endBricks = new Coordinate[2];
		List<Cube> aboveCubes = new ArrayList<>();
		List<Cube> downCubes = new ArrayList<>();
		Coordinate direction;
		int length;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Cube cube = (Cube) o;
			return length == cube.length && Arrays.equals(endBricks, cube.endBricks) && Objects.equals(direction, cube.direction) && Objects.equals(aboveCubes, cube.aboveCubes);
		}

		@Override
		public int hashCode() {
			int result = Objects.hash(direction, length, aboveCubes);
			result = 31 * result + Arrays.hashCode(endBricks);
			return result;
		}

		@Override
		public String toString() {
			return "[" + endBricks[0].x + "," + endBricks[0].y + "," + endBricks[0].z + "] ~ ["
					+ endBricks[1].x + "," + endBricks[1].y + "," + endBricks[1].z + "]";
		}

		public Cube(String line) {
			String[] coordinates = line.split("~");
			String[] c = coordinates[0].split(",");
			endBricks[0] = new Coordinate(Integer.parseInt(c[0]), Integer.parseInt(c[1]), Integer.parseInt(c[2]));
			c = coordinates[1].split(",");
			endBricks[1] = new Coordinate(Integer.parseInt(c[0]), Integer.parseInt(c[1]), Integer.parseInt(c[2]));
			if (endBricks[0].z > endBricks[1].z)	System.out.println("Umgekehrt");
			analyzeDirection();
			analyzeLength();
		}

		private void analyzeLength() {
			this.length = minus(endBricks[1], endBricks[0]).length();
		}

		private Coordinate minus(Coordinate eb1, Coordinate eb0) {
			return new Coordinate(eb1.x-eb0.x, eb1.y-eb0.y, eb1.z-eb0.z);
		}

		private void analyzeDirection() {
			direction = minus(endBricks[1], endBricks[0]).normalize();
		}

		public void settleDown(List<Cube> cubeRepo) {
			List<Cube> dcubes = new ArrayList<>();
			int z_max = 0;
			for (int i = cubeRepo.size() - 2; i >= 0; i--) {
				int z = interactWith(cubeRepo.get(i));
				if (z==z_max) {
					dcubes.add(cubeRepo.get(i));
				} else if (z>z_max) {
					dcubes.clear();
					dcubes.add(cubeRepo.get(i));
					z_max = z;
				}
			}
			int diff = endBricks[1].z - endBricks[0].z;
			endBricks[0].z = z_max+1;
			endBricks[1].z = diff + z_max+1;
			for (Cube c : dcubes) {
				downCubes.add(c);
				c.aboveCubes.add(this);
			}
		}

		private int interactWith(Cube cube) {
			Coordinate c1 = new Coordinate(endBricks[0].x, endBricks[0].y, endBricks[0].z);
			for (int i1=0; i1<length; i1++) {
				Coordinate c2 = new Coordinate(cube.endBricks[0].x, cube.endBricks[0].y, cube.endBricks[0].z);
				for (int i2=0; i2<cube.length; i2++) {
					if (c1.x == c2.x && c1.y == c2.y) {
						return cube.endBricks[1].z;
					}
					c2.add(cube.direction);
				}
				c1.add(direction);
			}
			return -1;
		}
	}

	public static class Coordinate {
		int x, y, z;

		public Coordinate(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Coordinate that = (Coordinate) o;
			return x == that.x && y == that.y && z == that.z;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y, z);
		}

		public Coordinate add(Coordinate endBrick) {
			x += endBrick.x;
			y += endBrick.y;
			z += endBrick.z;
			return this;
		}

		public Coordinate normalize() {
			if (x != 0)	x /= Math.abs(x);
			if (y != 0)	y /= Math.abs(y);
			if (z != 0)	z /= Math.abs(z);
			return this;
		}

		public int length() {
			return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) + 1;
		}
	}

}
