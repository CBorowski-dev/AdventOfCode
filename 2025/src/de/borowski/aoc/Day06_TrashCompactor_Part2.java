package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day06_TrashCompactor_Part2 {
    public static final int ROWS = 5; // 4 or 5
    public static final int COLS = 3769; // 15 or 3769
    public static final char[][] chars = new char[ROWS][COLS];

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day06_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day06.txt"));
            String line = reader.readLine();
            int row = 0;
            while (line != null) {
                char[] lineChars = line.toCharArray();
                System.arraycopy(lineChars, 0, chars[row], 0, lineChars.length);
                row++;
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Part 2
        long sum = 0;
        int idx_prev = 0, idx;
        for (int col = 1; col < COLS; col++) {
            if (chars[ROWS - 1][col] == '+' || chars[ROWS - 1][col] == '*') {
                idx = col;
                sum += calculateResult(idx_prev, idx-1, chars[ROWS - 1][idx_prev]);
                idx_prev = idx;
            }
        }
        sum += calculateResult(idx_prev, COLS, chars[ROWS - 1][idx_prev]);

        System.out.println("--------------------------------");
        System.out.println(sum);
    }

    private static long calculateResult(int idxPrev, int idx, char c) {
        long result = c == '+' ? 0 : 1;
        for (int col = idxPrev; col < idx; col++) {
            StringBuilder sb = new StringBuilder();
            for (int row = 0; row < ROWS - 1; row++) {
                sb.append(chars[row][col]);
            }
            long num = Long.parseLong(sb.toString().trim());
            if (c == '+') {
                result += num;
            } else {
                result *= num;
            }
        }
        return result;
    }

}
