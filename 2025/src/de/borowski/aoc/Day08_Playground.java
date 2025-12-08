package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day08_Playground {

    public static final List<Box> boxes = new ArrayList<>();
    public static final List<Integer> inCircuit = new ArrayList<>();
    public static final Map<Double, Distance> distances = new HashMap<>();

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day08_testset.txt"));
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day08.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] v = line.split(",");
                boxes.add(new Box(Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2])));
                inCircuit.add(-1);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Part 1
        // Compute distances
        for (int x1 = 0; x1 < boxes.size() - 1; x1++) {
            for (int x2 = x1 + 1; x2 < boxes.size(); x2++) {
                Box b1 = boxes.get(x1);
                Box b2 = boxes.get(x2);
                double d = b1.distance(b2);
                if (distances.containsKey(d)) System.out.println("--- PROBLEM ---");
                distances.put(d, new Distance(b1, b2));
            }
        }
        // sort distances
        List<Double> sortedDistances = distances.keySet().stream().sorted().toList();

        // create circuit
        int connectionCount = 0;
        int maxCircuitNumber = -1;
        for (int i = 0; i < sortedDistances.size() && connectionCount < 10; i++) {
            double d = sortedDistances.get(i);
            Distance dist = distances.get(d);
            int b1Index = boxes.indexOf(dist.b1);
            int b2Index = boxes.indexOf(dist.b2);
            Integer b1InCircuitNumber = inCircuit.get(b1Index);
            Integer b2InCircuitNumber = inCircuit.get(b2Index);
            if (b1InCircuitNumber == -1 && b2InCircuitNumber == -1) {
                maxCircuitNumber++;
                inCircuit.remove(b1Index);
                inCircuit.add(b1Index, maxCircuitNumber);
                inCircuit.remove(b2Index);
                inCircuit.add(b2Index, maxCircuitNumber);
                connectionCount++;
            } else if (b1InCircuitNumber == -1 && b2InCircuitNumber >= 0) {
                inCircuit.remove(b1Index);
                inCircuit.add(b1Index, b2InCircuitNumber);
                connectionCount++;
            } else if (b1InCircuitNumber >= 0 && b2InCircuitNumber == -1) {
                inCircuit.remove(b2Index);
                inCircuit.add(b2Index, b1InCircuitNumber);
                connectionCount++;
            } else {
                // connectionCount++;
                System.out.println("x");
            }
        }
        // the three largest circuits
        int[] circuitsSize = new int[maxCircuitNumber + 1];
        for (int i = 0; i < inCircuit.size(); i++) {
            if (inCircuit.get(i) >= 0) circuitsSize[inCircuit.get(i)]++;
        }
        int[] sortedCircuitsSize = Arrays.stream(circuitsSize).sorted().toArray();
        int result = 1;
        for (int i = sortedCircuitsSize.length - 1; i >= sortedCircuitsSize.length - 3; i--) {
            result *= sortedCircuitsSize[i];
        }

        System.out.println("--------------------------------");
        System.out.println(result);
    }

    public record Box(int x, int y, int z) {
        public double distance(Box otherBox) {
            return Math.sqrt(Math.pow(x - otherBox.x, 2.0) + Math.pow(y - otherBox.y, 2.0) + Math.pow(z - otherBox.z, 2.0));
        }
    }

    public record Distance(Box b1, Box b2) {
    }

}
