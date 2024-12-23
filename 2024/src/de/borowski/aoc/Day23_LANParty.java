package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day23_LANParty {

    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day23_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day23.txt";

    private final Map<String, List<String>> connections = new HashMap<>();
    private final Set<Triple> tripleSet = new HashSet<>();

    public Day23_LANParty() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println(computeResult());
    }

    private int computeResult() {
        Set<String> keys = connections.keySet();
        for (String key : keys) {
            checkGroupOfThreeComputers(key);
        }
        return tripleSet.size();
    }

    private void checkGroupOfThreeComputers(String key) {
        List<String> n = connections.get(key);
        for (int x=0; x<n.size()-1; x++) {
            for (int y=x+1; y<n.size(); y++) {
                String s1 = n.get(x);
                String s2 = n.get(y);
                if (connections.get(s1).contains(s2)) {
                    if (key.startsWith("t") || s1.startsWith("t") || s2.startsWith("t")) {
                        List<String> triple = new ArrayList<>(3);
                        triple.add(key);
                        triple.add(s1);
                        triple.add(s2);
                        triple = triple.stream().sorted().toList();
                        tripleSet.add(new Triple(triple.get(0), triple.get(1), triple.get(2)));
                    }
                }
            }
        }
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                // System.out.println(line);
                String[] ids = line.split("-");
                fillConnectionsMap(ids[0], ids[1]);
                fillConnectionsMap(ids[1], ids[0]);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillConnectionsMap(String id1, String id2) {
        List<String> n = connections.computeIfAbsent(id1, k -> new ArrayList<>());
        if (!n.contains(id2)) n.add(id2);
    }

    private static class Triple {
        String s1, s2, s3;

        public Triple(String s1, String s2, String s3) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Triple triple = (Triple) o;
            return Objects.equals(s1, triple.s1) && Objects.equals(s2, triple.s2) && Objects.equals(s3, triple.s3);
        }

        @Override
        public int hashCode() {
            return Objects.hash(s1, s2, s3);
        }
    }

    public static void main(String[] args) { new Day23_LANParty(); }
}