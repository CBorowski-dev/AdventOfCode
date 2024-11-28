package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class Day20_PulsePropagation {

	public static boolean HIGH = true;
	public static boolean LOW = false;

	public static List<Pulse> pulseQueue = new PulseList();
	public static Map<String, Module> modules = new HashMap<>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day20_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day20.txt"));
			String line = reader.readLine();
			while (line != null) {
				// &pz -> kt, pg, mb, vr, hp, jp, tx
				String[] parts = line.split(" -> ");
				String[] moduleArray = parts[1].split(",");
				List<String> destModules = new ArrayList<>();
				for (String m : moduleArray) {
					String destName = m.trim();
					destModules.add(destName);
					Module module = modules.get(destName);
					if (module!=null && module instanceof Conjunction)	((Conjunction) module).addInputModule(parts[0].trim());
				}
				if (parts[0].startsWith("&")) {
					// Conjunction
					String name = parts[0].trim().substring(1);
					modules.put(name, new Conjunction(name, searchKnownInputModules(name), destModules));
				} else if (parts[0].startsWith("%")) {
					// Flip-flop
					String name = parts[0].trim().substring(1);
					modules.put(name, new FlipFlop(name, destModules));
				} else {
					// Broadcast
					String name = parts[0].trim();
					modules.put(name, new Broadcaster(name, destModules));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//for (int i=0; i<10000000; i++) {
		int i=0;
		boolean pressButton = true;
		while (pressButton) {
			((PulseList) pulseQueue).add(new Pulse(LOW, null, "broadcaster"));
			i++;
			while (!pulseQueue.isEmpty()) {
				Pulse p = pulseQueue.remove(0);
				Module destModule = modules.get(p.toModule);
				if (destModule != null) destModule.input(p);
				else if (p.toModule.equals("rx") && p.level==LOW) {
					System.out.println("rx reached : " + i);
					pressButton = false;
				}
			}
		}
		long highPulseCount = ((PulseList) pulseQueue).highPulseCountert;
		long lowPulseCount = ((PulseList) pulseQueue).lowPulseCountert;
		System.out.println("--------------------------------");
		System.out.println("highPulseCount : " + highPulseCount);
		System.out.println("lowPulseCount : " + lowPulseCount);
		System.out.println(highPulseCount * lowPulseCount);
	}

	private static List<String> searchKnownInputModules(String toName) {
		List<String> inputModules = new ArrayList<>();

		for (Module fromModule: modules.values()) {
			if (fromModule.destModuleNames.contains(toName))	inputModules.add(fromModule.name);
		}

		return inputModules;
	}

	public static class PulseList extends LinkedList {
		public int highPulseCountert = 0;
		public int lowPulseCountert = 0;

		public boolean add(Pulse p) {
			if (p.level)	highPulseCountert++;
			else lowPulseCountert++;
			//System.out.println(p.fromModule + (p.level?" -high-> ":" -low-> ") + p.toModule);
			return super.add(p);
		}
	}

	public static class Pulse {
		public boolean level = false;
		public String fromModule;
		public String toModule;
		public Pulse(boolean level, String fromModule, String toModule) {
			this.level = level;
			this.fromModule = fromModule;
			this.toModule = toModule;
		}
	}

	public static abstract class Module {
		public String name;
		public List<String> destModuleNames = new ArrayList<>();
		public Module(String name, List<String> modules) {
			this.name = name;
			this.destModuleNames.addAll(modules);
		}
		public void sendPulse(boolean pulseLevel, String fromModule) {
			for (String toModule : destModuleNames) {
				((PulseList)pulseQueue).add(new Pulse(pulseLevel, fromModule, toModule));
			}
		}
		public abstract void input(Pulse p);
	}

	public static class FlipFlop extends Module {
		public boolean state = false;
		public FlipFlop(String name, List modules) {
			super(name, modules);
		}

		@Override
		public void input(Pulse p) {
			if (!p.level) {
				state = !state;	// toggle state
				sendPulse(state, this.name);
			}
		}
	}

	public static class Conjunction extends Module {
		public Map<String, Boolean> inputModules = new HashMap<>();
		public Conjunction(String name, List<String> fromModules, List<String> modules) {
			super(name, modules);
			for (String s : fromModules) {
				inputModules.put(s, false);
			}
		}

		@Override
		public void input(Pulse p) {
			if (!inputModules.containsKey(p.fromModule))	inputModules.put(p.fromModule, p.level);
			else inputModules.replace(p.fromModule, p.level);	// update Status for current fromModule
			int count = 0;
			for (Boolean status : inputModules.values())	if (status)	count++;
			sendPulse((count == inputModules.size()) ? LOW : HIGH, this.name);
		}

		public void addInputModule(String inputName) {
			if (inputName.startsWith("%") || inputName.startsWith("&")) {
				inputName = inputName.substring(1);
			}
			if (inputModules.get(inputName) == null)	inputModules.put(inputName, false);
		}
	}

	public static class Broadcaster extends Module {
		public Broadcaster(String name, List modules) {
			super(name, modules);
		}
		public void input(Pulse p) {
			sendPulse(p.level, this.name);
		}
	}
}
