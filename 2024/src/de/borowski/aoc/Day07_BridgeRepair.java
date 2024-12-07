package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day07_BridgeRepair {
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day07_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day07.txt";

    private final List<List<Long>> calibrationEquations = new ArrayList<>();

    public Day07_BridgeRepair() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println(getCalibrationResult().toString());
    }

    private BigInteger getCalibrationResult() {
        BigInteger result = BigInteger.valueOf(0);
        for (List<Long> values : calibrationEquations) {
            if (isEquationValid(values)) result = result.add(BigInteger.valueOf(values.getFirst()));
        }
        return result;
    }

    private boolean isEquationValid(List<Long> values) {
        int operatorCount = values.size() - 2;
        int numberOfPossibilities = (int) Math.pow(2, operatorCount);
        for (int i=0; i<numberOfPossibilities; i++) {
            char[] operatorArray = decToBinary(i);
            long result = values.get(1);
            for (int j=0; j<values.size()-2; j++) {
                if (operatorArray[j]=='*') {
                    result *= values.get(j+2);
                } else {
                    result += values.get(j+2);
                }
            }
            if (result == values.get(0)) return true;
        }
        return false;
    }

    private char[] decToBinary(int n) {
        StringBuilder sb = new StringBuilder(32);
        for (int i = 31; i >= 0; i--) {
            int k = n >> i;
            sb.insert(0, (k & 1) > 0 ? '*' : '+');
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
                List<Long> values = new ArrayList<>();
                String[] xyz = line.split(":");
                values.add(Long.valueOf(xyz[0]));
                String[] operands = xyz[1].trim().split(" ");
                for (String op : operands) {
                    values.add(Long.parseLong(op));
                }
                calibrationEquations.add(values);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Day07_BridgeRepair();
    }
}
