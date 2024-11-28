package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day15_LensLibraryPart2
{
	public static List<List<Lens>> boxes = new ArrayList<>();

	public static void main(String[] args) {
		BufferedReader reader;

		initilzeListOfBoxes();
		try {
			// reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day15_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day15.txt"));
			String line = reader.readLine();
			String[] steps = line.split(",");
			for (String s : steps) {
				int i = s.indexOf('=');
				if (i>0) {
					// add
					String lensLabel = s.substring(0, i);
					int boxNr = getBoxNr(lensLabel);
					int fl = Integer.parseInt(s.substring(i+1));
					addToBox(boxNr, new Lens(lensLabel, fl));
					printBox(boxNr);
				} else {
					// remove
					i = s.indexOf('-');
					String lensLabel = s.substring(0, i);
					int boxNr = getBoxNr(lensLabel);
					removeFromBox(boxNr, lensLabel);
					printBox(boxNr);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(getFocusPowerSum());
	}

	private static long getFocusPowerSum() {
		long sum = 0;
		for (int b=0; b<boxes.size(); b++) {
			List<Lens> box = boxes.get(b);
			for (int i=0; i<box.size(); i++) {
				sum += (b+1) * (i+1) * box.get(i).focalLength;
			}
		}
		return sum;
	}

	public static void printBox(int boxNr) {
		List<Lens> boxContent = boxes.get(boxNr);
		System.out.print("Box " + boxNr + ": ");
		for (Lens l : boxContent) {
			System.out.print(l);
		}
		System.out.println();
	}

	private static void addToBox(int boxNr, Lens lens) {
		List<Lens> boxContent = boxes.get(boxNr);
		int idx = boxContent.indexOf(lens);
		if (idx==-1) boxContent.add(lens);
		else {
			boxContent.remove(idx);
			boxContent.add(idx, lens);
		}
	}

	private static void removeFromBox(int boxNr, String lensLabel) {
		List<Lens> boxContent = boxes.get(boxNr);
		for (Lens l : boxContent) {
			if (l.lensLabel.equals(lensLabel)) {
				boxContent.remove(l);
				return;
			}
		}
	}

	private static void initilzeListOfBoxes() {
		for (int i=0; i<256; i++) {
			boxes.add(new ArrayList<Lens>());
		}
	}

	private static int getBoxNr(String s) {
		//System.out.println(s);
		int boxNr=0;
		char[] sa = s.toCharArray();
		for (int i=0; i<sa.length; i++) {
			//System.out.println(sa[i] + " -> " + String.valueOf((int)sa[i]));
			boxNr += (int)sa[i];
			boxNr *= 17;
			boxNr = boxNr%256;
		}
		//System.out.println("-> " + boxNr);

		return boxNr;
	}

	public static class Lens {
		String lensLabel;
		int focalLength;

		@Override
		public String toString() {
			return "[" + lensLabel + ' ' + focalLength + ']';
		}

		public Lens(String lensLabel, int focalLength) {
			this.lensLabel = lensLabel;
			this.focalLength = focalLength;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Lens lens = (Lens) o;
			return Objects.equals(lensLabel, lens.lensLabel);
		}

		@Override
		public int hashCode() {
			return Objects.hash(lensLabel);
		}
	}

}
