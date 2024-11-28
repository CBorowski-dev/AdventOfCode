package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Day25_Snoverload {

	public static Map<String, Component> hm = new HashMap<>();
	public static List<Component> toVisit = new ArrayList<>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day25_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day25.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				String name = line.substring(0, 3);
				Component c = hm.get(name);
				if (c==null) {
					c = new Component(name);
					hm.put(name, c);
				}
				String[] n = line.substring(5).split(" ");
				for (int i=0; i< n.length; i++) {
					name = n[i];
					Component cc = hm.get(name);
					if (cc==null) {
						cc = new Component(name);
						hm.put(name, cc);
					}
					if (isAllowed(c.name, cc.name)) {
						c.addComponent(cc);
						cc.addComponent(c);
					}
				}
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		// call getDotContent(hm)
		//System.out.println(getDotContent(hm));
		toVisit.add(hm.get("ghs"));
		visit();
	}

	private static void visit() {
		int visitedCount = 0;
		int idx = 0;
		Component c;
		do {
			c = toVisit.get(idx);
			if (!c.isVisited) {
				c.isVisited = true;
				visitedCount++;
				for (Component cc : c.connectedComponents) {
					if (!cc.isVisited) {
						toVisit.add(cc);
					}
				}
			}
			idx++;
		} while (idx < toVisit.size());
		System.out.println(visitedCount);
		System.out.println(hm.size()-visitedCount);
		System.out.println(visitedCount * (hm.size()-visitedCount));
	}

	private static String getDotContent(Map<String, Component> hm) {
		StringBuilder sb = new StringBuilder("graph {\nratio=\"fill\";\n" +
				"size=\"24,12!\";\n" +
				"margin=0;\n" +
				"node [shape=circle, width=0.04, height=0.04];\n");
		for (String name: hm.keySet()) {
			Component c = hm.get(name);
			for (Component cc: c.connectedComponents) {
				//if (isAllowed(name, cc.name)) {
					sb.append(name + "--" + cc.name + ";\n");
				//}
				cc.connectedComponents.remove(c);
			}
		}
		sb.append('}');
		return sb.toString();
	}

	private static boolean isAllowed(String name, String name1) {
		if (name.equals("pzv") && name1.equals("xft") || name1.equals("pzv") && name.equals("xft")) return false;
		if (name.equals("hbr") && name1.equals("sds") || name1.equals("hbr") && name.equals("sds")) return false;
		if (name.equals("dqf") && name1.equals("cbx") || name1.equals("dqf") && name.equals("cbx")) return false;
		return true;
	}

	public static class Component {

		boolean isVisited = false;
		String name;
		List<Component> connectedComponents = new ArrayList<>();

		public Component(String name) {
			this.name = name;
		}

		public void addComponent(Component c) {
			if (!connectedComponents.contains(c)) {
				connectedComponents.add(c);
			}
		}
	}

}
