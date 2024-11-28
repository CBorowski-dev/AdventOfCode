package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day07_NoSpaceLeftOnDevice_Part2 {
	
	static final Entity root = new Entity("/", true, 0);
	static final int spaceAvaiable = 70000000;
	static final int minSpaceNeededForUpdate = 30000000;
	static Entity additionalDirectoryNeededToDeleteForUpdate = root;

	public static void main(String[] args) {
		BufferedReader reader;
		Entity currentDirectory = null;
		
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
		System.out.println("--------------------------------");
		int directorySize = computeDirectorySize(root);
		System.out.println("Used space : " + directorySize);
		int unusedSpace = spaceAvaiable - directorySize;
		System.out.println("Unused space : " + unusedSpace);
		int additionalSpaceNeededForUpdate = minSpaceNeededForUpdate - unusedSpace;
		System.out.println("Additional space for update : " + additionalSpaceNeededForUpdate);
		findSmallestDirectoryNeededToDeleteForUpdate(root, additionalSpaceNeededForUpdate);
		System.out.println("Smallest directory for update : " + additionalDirectoryNeededToDeleteForUpdate.name + " - " + additionalDirectoryNeededToDeleteForUpdate.size);
	}
	
	private static void findSmallestDirectoryNeededToDeleteForUpdate(Entity currentDirectory, int sizeNeededForUpdate) {
		if (currentDirectory.size >= sizeNeededForUpdate && currentDirectory.size < additionalDirectoryNeededToDeleteForUpdate.size) {
			additionalDirectoryNeededToDeleteForUpdate = currentDirectory;
		}
		for (int i=0; i<currentDirectory.chidList.size(); i++) {
			Entity child = (Entity) currentDirectory.chidList.get(i);
			if (child.isDirectory) {
				findSmallestDirectoryNeededToDeleteForUpdate(child, sizeNeededForUpdate);
			}
		}
	}

	private static Entity findEntity(Entity dir, String name) {
		for (Entity e : dir.chidList) {
			if (e.name.equals(name) && e.isDirectory) {
				return e;
			}
		}
		return null;
	}

	private static int computeDirectorySize(Entity currentDirectory) {
		if (currentDirectory.visited) {
			return currentDirectory.size;
		}
		currentDirectory.visited = true;
		int size = 0;
		for (int i=0; i<currentDirectory.chidList.size(); i++) {
			Entity child = (Entity) currentDirectory.chidList.get(i);
			if (child.isDirectory) {
				// directory
				size += computeDirectorySize(child);
			} else {
				// file
				size += child.size;
			}
		}
		currentDirectory.size = size;
		
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
