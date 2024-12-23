package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day23_LANParty_Part2 {

    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day23_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day23.txt";

    private final Map<String, List<String>> connections = new HashMap<>();
    private List<String> maxGroup = new ArrayList<>();

    public Day23_LANParty_Part2() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println("Password: " + computePassword());
    }

    private String computePassword() {
        Set<String> keys = connections.keySet();
        int i=0;
        for (String key : keys) {
            searchMaxGroupOfComputers(key);
        }
        maxGroup = maxGroup.stream().sorted().toList();
        StringBuffer password = new StringBuffer();
        for (String s: maxGroup) {
            password.append(s);
            password.append(',');
        }
        String pw = password.toString();
        return pw.substring(0, pw.length()-1);
    }

    private void searchMaxGroupOfComputers(String key) {
        int count = 0;
        List<String> group = new ArrayList<>();
        group.add(key);
        List<String> n = connections.get(key);
        String s1, s2 = null;
        for (int x = 0; x < n.size() - 1; x++) {
            s1 = n.get(x);
            count = 0;
            for (int y = x + 1; y < n.size(); y++) {
                s2 = n.get(y);
                if (!connections.get(s1).contains(s2)) count++;
            }
            if (count==0) group.add(s1);
        }
        if (count==0) group.add(s2);
        if (group.size()>maxGroup.size()) {
            maxGroup.clear();
            maxGroup.addAll(group);
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

    public static void main(String[] args) { new Day23_LANParty_Part2(); }
}