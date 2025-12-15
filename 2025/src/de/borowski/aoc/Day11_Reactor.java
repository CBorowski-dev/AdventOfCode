package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day11_Reactor {

    public static final Map<String, Node> CON = new HashMap<>();

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day11_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day11.txt"));
            String line = reader.readLine();

            while (line != null) {
                // System.out.println(line);
                String[] nodes = line.split(": ");
                String[] toNodes = nodes[1].split(" ");
                Node key = CON.get(nodes[0]);
                if (key == null) {
                    key = new Node(nodes[0], generateChildNodeList(toNodes));
                    CON.put(nodes[0], key);
                } else if (key.childNodes == null) {
                    key.childNodes = generateChildNodeList(toNodes);
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Part 1
        Node node = CON.get("you");
        searchAllPaths(node, "out");

        // Part 2: Run part 1 for the following combination and multiply the tree numbers for the result
        // srv -> fft : 8049
        // fft -> dac : 3377314
        // dac -> out : 12215
        System.out.println("--------------------------------");
        System.out.println(node.counter);
    }

    private static List<Node> generateChildNodeList(String[] toNodes) {
        List<Node> childNodes = new ArrayList<>();
        for (String s : toNodes) {
            Node key = CON.get(s);
            if (key == null) {
                key = new Node(s);
                CON.put(s, key);
            }
            childNodes.add(key);
        }
        return childNodes;
    }

    private static void searchAllPaths(Node node, String target) {
        for (Node childNode : node.childNodes) {
            if (childNode.label.equals(target)) {
                node.counter = 1;
            } else if (!childNode.visited) {
                if (childNode.childNodes != null) searchAllPaths(childNode, target);
                node.counter += childNode.counter;
            } else {
                node.counter += childNode.counter;
            }
        }
        node.visited = true;
    }

    public static class Node {
        String label;
        long counter = 0;
        List<Node> childNodes;
        boolean visited = false;

        public Node(String label) {
            this.label = label;
        }

        public Node(String label, List<Node> childNodes) {
            this.label = label;
            this.childNodes = childNodes;
        }
    }

}
