package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day07_NoSpaceLeftOnDevice {
	
	static final Entity root = new Entity("/", true, 0);
	static int sumAtMostSize = 0;

	public static void main(String[] args) {
		BufferedReader reader;
		Entity currentDirectory = null;
		int sum = 0;
		
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day07.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				if (line.startsWith("$")) {
					if (line.startsWith("$ cd")) {
						String name = line.substring(5);
						if (name.equals("/")) {
							currentDirectory = root;
						} else if (name.equals("..")) {
							currentDirectory = currentDirectory.parentEntity;
						} else {
							// ToDo
							currentDirectory = findEntity(currentDirectory, name);
							if (currentDirectory == null) {
								System.out.println("	==> Fehler: " + line);
							}
						}
					}
					// "$ ls" wird nicht betrachtet
				} else if (line.startsWith("dir")) {
					// directory
					Entity dir = new Entity(line.substring(4), true, 0);
					dir.parentEntity = currentDirectory;
					currentDirectory.addEntity(dir);
				} else {
					// file
					String[] lineParts = line.split(" ");
					Entity file = new Entity(lineParts[1], false, Integer.parseInt(lineParts[0]));
					file.parentEntity = currentDirectory;
					currentDirectory.addEntity(file);
				}
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		countAllDirectoriesWithSizeAtMost(root, 100000);
		System.out.println("--------------------------------");
		System.out.println(sumAtMostSize);
	}
	
	private static Entity findEntity(Entity dir, String name) {
		for (Entity e : dir.chidList) {
			if (e.name.equals(name) && e.isDirectory) {
				return e;
			}
		}
		return null;
	}

	private static int countAllDirectoriesWithSizeAtMost(Entity currentDirectory, int atMostSize) {
		if (currentDirectory.visited) {
			System.out.println("--" + currentDirectory.name);
		}
		currentDirectory.visited = true;
		int size = 0;
		for (int i=0; i<currentDirectory.chidList.size(); i++) {
			Entity child = (Entity) currentDirectory.chidList.get(i);
			if (child.isDirectory) {
				// directory
				size += countAllDirectoriesWithSizeAtMost(child, atMostSize);
			} else {
				// file
				size += child.size;
			}
		}
		if (size <= atMostSize) {
			// System.out.println(currentDirectory.name + " " + size);
			sumAtMostSize += size;
		}
		
		return size;
	}

	static class Entity {
		String name;
		boolean isDirectory;
		int size;
		Entity parentEntity;
		List<Entity> chidList = new ArrayList<Entity>();
		boolean visited = false;
		
		public Entity(String name, boolean isDirectory, int size) {
			this.name = name;
			this.isDirectory = isDirectory;
			this.size = size;
		}

		public void addEntity(Entity entity) {
			chidList.add(entity);			
		}
	}

}
