package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day22_MonkeyMarket_Part2 {

    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day22_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day22.txt";

    private final List<Long> secretNumbers = new ArrayList<>();
    private final Map<KeySequenz, Long> keySequenzes = new HashMap<>(2000);

    public Day22_MonkeyMarket_Part2() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println("Result: " + computeResult(2000));
    }

    private long computeResult(int rounds) {
        long result = 0;
        byte[] diffs = new byte[] {0,0,0,0};
        byte previousLastDigit = 0;
        Set<KeySequenz> usedKeySequenzes = new HashSet<>();

        for (long sn : secretNumbers) {
            usedKeySequenzes.clear();
            previousLastDigit = (byte)(sn % 10);
            diffs[0] = (byte) (0 - previousLastDigit);
            for (int i=0; i<rounds; i++) {
                sn = (sn ^ (sn * 64)) % 16777216; // first step
                sn = (sn ^ (long) Math.abs((double) sn / 32)) % 16777216; // second step
                sn = (sn ^ (sn * 2048)) % 16777216; // third step
                // last digit
                byte lastDigit = (byte)(sn % 10);
                if (i>0) {
                    diffs[3] = diffs[2];
                    diffs[2] = diffs[1];
                    diffs[1] = diffs[0];
                    diffs[0] = (byte) (lastDigit - previousLastDigit);
                }
                if (i>=3) {
                    KeySequenz ks = new KeySequenz(diffs[0],diffs[1],diffs[2],diffs[3]);
                    if (!usedKeySequenzes.contains(ks)) {
                        usedKeySequenzes.add(ks);
                        Long value = keySequenzes.get(ks);
                        if (value == null) value = 0l;
                        value += lastDigit;
                        keySequenzes.put(ks, value);
                    }
                }
                // System.out.println(sn + ": " + lastDigit + " (" + diffs[0] + ")");
                previousLastDigit = lastDigit;
            }
        }

        // Search key sequenz with the greatest value
        for (KeySequenz ks : keySequenzes.keySet()) {
            long tmpValue = keySequenzes.get(ks);
            if (tmpValue > result) result = tmpValue;
        }

        return result;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                secretNumbers.add(Long.parseLong(line));
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class KeySequenz {
        byte b0, b1, b2, b3;

        public KeySequenz(byte b0, byte b1, byte b2, byte b3) {
            this.b0 = b0;
            this.b1 = b1;
            this.b2 = b2;
            this.b3 = b3;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            KeySequenz that = (KeySequenz) o;
            return b0 == that.b0 && b1 == that.b1 && b2 == that.b2 && b3 == that.b3;
        }

        @Override
        public int hashCode() {
            return Objects.hash(b0, b1, b2, b3);
        }
    }

    public static void main(String[] args) { new Day22_MonkeyMarket_Part2(); }
}
