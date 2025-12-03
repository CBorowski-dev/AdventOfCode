package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day03_Lobby {

    public static final List<String> banks = new ArrayList<>();

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day03_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day03.txt"));
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                banks.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long sum = 0;
        for (String bank : banks) {
            long maxVoltage = getMaxVoltage(bank);
            sum += maxVoltage;
        }
        System.out.println("--------------------------------");
        System.out.println(sum);
    }

    private static long getMaxVoltage(String bank) {
        char[] digits = bank.toCharArray();
        long maxVoltage = 0;
        int index = -1;
        int digitCount = 12; // for part 1 set value to 2; for part 2 set value to 12
        for (int n = digitCount - 1; n >= 0; n--) {
            index = getIndex(digits, index + 1, n);
            maxVoltage += (long) (Integer.parseInt(String.valueOf(digits[index])) * Math.pow((double) 10, (double) n));
        }
        return maxVoltage;
    }

    private static int getIndex(char[] digits, int start_index, int digitsToSkip) {
        int d = 0;
        int index = 0;
        for (int i = start_index; i < digits.length - digitsToSkip; i++) {
            int d_tmp = Integer.parseInt(String.valueOf(digits[i]));
            if (d_tmp > d) {
                d = d_tmp;
                index = i;
            }
        }
        return index;
    }

}
