package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day21_KeypadConundrum {

    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day21_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day21.txt";

    int steps = 3;
    //String[] tmpCodes = new String[] {"<A^A>^^AvvvA", "^^^A<AvvvA>A", "^<<A^^A>>AvvvA", "^^<<A>A>AvvA", "^A<<^^A>>AvvvA"}; // codes for example
    String[] tmpCodes = new String[] {"^^^AvA<<A>>vvA", "<^A<^A>>AvvA", "^^^A<<Avv>>AvA", "^^A<^AvvAv>A", "<<^A>^^AvvvA>A"}; // codes for input

    private final List<char[]> codes = new ArrayList<>(5);
    private final List<Integer> numericalValue = new ArrayList<>(5);

    public Day21_KeypadConundrum() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println("Result: " + computeResult());
    }

    private int computeResult() {
        int result = 0;

        char[] keySequence;
        int i=0;
        for (String code : tmpCodes) {
            // keySequence = getDoorKeypadSequence(code);
            System.out.print(code + " => ");
            keySequence = code.toCharArray();
            for (int x=0; x<steps; x++) {
                keySequence = getRobotKeypadSequence(keySequence);
                //System.out.print(String.valueOf(keySequence) + " => ");
            }
            System.out.println(keySequence.length);
            result += (keySequence.length * numericalValue.get(i++));
        }

        return result;
    }

    private char[] getDoorKeypadSequence(char[] code) {
        StringBuffer sb = new StringBuffer();

        return sb.toString().toCharArray();
    }

    private char[] getRobotKeypadSequence(char[] input) {
        StringBuffer sb = new StringBuffer();
        char previousChar = 'A';
        for (char c : input) {
            switch (c) {
                case 'A':
                    if (previousChar=='A') sb.append("A");
                    else if (previousChar=='^') sb.append(">A");
                    else if (previousChar=='v') sb.append(">^A");
                    else if (previousChar=='<') sb.append(">>^A");
                    else if (previousChar=='>') sb.append("^A");
                    break;
                case '^':
                    if (previousChar=='A') sb.append("<A");
                    else if (previousChar=='^') sb.append("A");
                    else if (previousChar=='v') sb.append("^A");
                    else if (previousChar=='<') sb.append(">^A");
                    else if (previousChar=='>') sb.append("<^A");
                    break;
                case 'v':
                    if (previousChar=='A') sb.append("<vA");
                    else if (previousChar=='^') sb.append("vA");
                    else if (previousChar=='v') sb.append("A");
                    else if (previousChar=='<') sb.append(">A");
                    else if (previousChar=='>') sb.append("<A");
                    break;
                case '<':
                    if (previousChar=='A') sb.append("v<<A");
                    else if (previousChar=='^') sb.append("v<A");
                    else if (previousChar=='v') sb.append("<A");
                    else if (previousChar=='<') sb.append("A");
                    else if (previousChar=='>') sb.append("<<A");
                    break;
                case '>':
                    if (previousChar=='A') sb.append("vA");
                    else if (previousChar=='^') sb.append("v>A");
                    else if (previousChar=='v') sb.append(">A");
                    else if (previousChar=='<') sb.append(">>A");
                    else if (previousChar=='>') sb.append("A");
            }
            previousChar = c;
        }
        return sb.toString().toCharArray();
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                codes.add(line.toCharArray());
                line = line.substring(0, line.length()-1);
                numericalValue.add(Integer.parseInt(line));
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { new Day21_KeypadConundrum(); }
}
