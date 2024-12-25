package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day25_CodeChronicle {

    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day25_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day25.txt";

    List<Code> keyList = new ArrayList<>();
    List<Code> lockList = new ArrayList<>();

    public Day25_CodeChronicle() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println("Result: " + computeResult());
    }

    private long computeResult() {
        int countFit = 0;
        for (Code key: keyList)
            for (Code lock: lockList)
                if (key.fit(lock)) countFit++;
        return countFit;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            int[] codePins = new int[] {-1,-1,-1,-1,-1};
            Code code = new Code(codePins);
            boolean isKey = true;
            boolean isFirstRow = true;
            while (line != null) {
                if (!line.isEmpty()) {
                    char[] cp = line.toCharArray();
                    if (isFirstRow) {
                        if (cp[0]=='#') isKey=false;
                        isFirstRow = false;
                    }
                    int i=0;
                    for (char c : cp) {
                        codePins[i++] = (c=='#') ? 1 : 0;
                    }
                    code.add(codePins);
                } else {
                    if (isKey) keyList.add(code);
                    else lockList.add(code);
                    codePins = new int[] {-1,-1,-1,-1,-1};
                    code = new Code(codePins);
                    isKey = true;
                    isFirstRow = true;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Code {
        int[] code;

        public Code(int[] code) {
            this.code = Arrays.copyOf(code, code.length);
        }

        public void add(int[] codePins) {
            for (int i=0; i<5; i++) code[i] += codePins[i];
        }

        public boolean fit(Code extCode) {
            for (int i=0; i<5; i++) if (code[i]+extCode.code[i] > 5) return false;
            return true;
        }
    }

    public static void main(String[] args) { new Day25_CodeChronicle(); }
}
