package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day21_KeypadConundrum_Part2 {

    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day21_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day21.txt";

    long length = 0;
    int steps = 25;
    //String[] tmpCodes = new String[] {"<A^A^^>AvvvA", "^^^A<AvvvA>A", "^<<A^^A>>AvvvA", "^^<<A>A>AvvA", "^A<<^^A>>AvvvA"}; // codes for example
    String[] tmpCodes = new String[] {"^^^AvA<<A>>vvA", "<^A<^A>>AvvA", "^^^A<<Avv>>AvA", "^^A<^AvvAv>A", "^<<A^^>AvvvA>A"}; // codes for input

    private final List<char[]> codes = new ArrayList<>(5);
    private final List<Integer> numericalValue = new ArrayList<>(5);
    private final Map<Touple, Long> seqMap = new HashMap<>();

    public Day21_KeypadConundrum_Part2() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println("Result: " + computeResult());
    }

    private long computeResult() {
        long result = 0;
        int i=0;
        for (String code : tmpCodes) {
            length = 0;
            computeMove(code, 0, steps);
            System.out.println(length + " " + numericalValue.get(i));
            result += (length * numericalValue.get(i++));
        }
        return result;
    }

    private long computeMove(String code, int idx, int maxIdx) {
        String[] keySequence = splitCode(code);
        long tmpLength = 0;
        long tmpLengthAll = 0;
        for (String c: keySequence) {
            tmpLength = 0;
            Touple t = new Touple(c, idx);
            if (seqMap.get(t)!=null) {
                length += seqMap.get(t);
                tmpLength += seqMap.get(t);
            } else {
                String seq = getRobotKeypadSequence(c);
                if (idx + 1 == maxIdx) {
                    length += seq.length();
                    tmpLength += seq.length();
                    //System.out.print(String.valueOf(seq));
                } else {
                    tmpLength = computeMove(seq, idx + 1, maxIdx);
                }
                seqMap.putIfAbsent(t, tmpLength);
            }
            tmpLengthAll += tmpLength;
        }
        return tmpLengthAll;
    }

    private String[] splitCode(String code) {
        List<String> keySequence = new ArrayList<>();
        while (true) {
            int idx = code.indexOf('A');
            if (idx==-1) break;
            keySequence.add(code.substring(0, idx+1));
            code = code.substring(idx+1);
        }
        return keySequence.toArray(new String[0]);
    }

    private char[] getDoorKeypadSequence(char[] code) {
        StringBuffer sb = new StringBuffer();

        return sb.toString().toCharArray();
    }

    private String getRobotKeypadSequence(String input) {
        StringBuffer sb = new StringBuffer();
        char previousChar = 'A';
        for (char c : input.toCharArray()) {
            switch (c) {
                case 'A':
                    if (previousChar=='A') sb.append("A");
                    else if (previousChar=='^') sb.append(">A");
                    else if (previousChar=='v') sb.append("^>A");
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
        return sb.toString();
    }

    private static class Touple {
        String c;
        int idx;

        public Touple(String c, int idx) {
            this.c = c;
            this.idx = idx;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Touple touple = (Touple) o;
            return idx == touple.idx && Objects.equals(c, touple.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(c, idx);
        }
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

    public static void main(String[] args) { new Day21_KeypadConundrum_Part2(); }
}
