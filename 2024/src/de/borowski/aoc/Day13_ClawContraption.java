package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13_ClawContraption {

    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day13_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day13.txt";

    public Day13_ClawContraption(boolean part2) {
        readInput(part2);
    }

    private void readInput(boolean part2) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int x_A=0, y_A=0, x_B=0, y_B=0;
            long x_G=0, y_G=0;
            boolean gameDataComplete = false;
            String line = reader.readLine();
            long tokens = 0;
            while (line != null) {
                if (line.startsWith("Button A:")) {
                    line = line.substring(line.indexOf('X'));
                    x_A = Integer.valueOf(line.substring(2, line.indexOf(',')));
                    y_A = Integer.valueOf(line.substring(line.indexOf(',')+4));
                } else if (line.startsWith("Button B:")) {
                    line = line.substring(line.indexOf('X'));
                    x_B = Integer.valueOf(line.substring(2, line.indexOf(',')));
                    y_B = Integer.valueOf(line.substring(line.indexOf(',')+4));
                } else if (line.startsWith("Prize:")) {
                    line = line.substring(line.indexOf('X'));
                    x_G = Integer.valueOf(line.substring(2, line.indexOf(','))) + (part2 ? 10000000000000L : 0);
                    y_G = Integer.valueOf(line.substring(line.indexOf(',')+4)) + (part2 ? 10000000000000L : 0);
                    gameDataComplete = true;
                }
                if (gameDataComplete) {
                    Game g = new Game(x_A, y_A, x_B, y_B, x_G, y_G);
                    if (g.isSolvable()) {
                        tokens += (g.pressCountA*3 + g.pressCountB);
                    }
                    gameDataComplete = false;
                }
                // read next line
                line = reader.readLine();
            }
            System.out.println(tokens);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Button A: X+94, Y+34
    // Button B: X+22, Y+67
    // Prize: X=8400, Y=5400
    private class Game {
        double x_A, y_A, x_B, y_B, x_G, y_G;
        long pressCountA, pressCountB;

        public Game(int x_A, int y_A, int x_B, int y_B, long x_G, long y_G) {
            this.x_A = x_A;
            this.y_A = y_A;
            this.x_B = x_B;
            this.y_B = y_B;
            this.x_G = x_G;
            this.y_G = y_G;
        }

        public boolean isSolvable() {
            // lineares Gleichungssystem mit 2 unbekannten lösen
            double pCB = ((y_G - (y_A*x_G)/x_A)/(y_B - (y_A*x_B)/x_A));
            double pCA = ((x_G/x_A) - (pCB*x_B)/x_A);
            pressCountB = (long)round(pCB, 0);
            pressCountA = (long)round(pCA, 0);
            if (pressCountB<0 || pressCountA<0) return false;
            // Gegenrechnung
            return pressCountA*x_A + pressCountB*x_B == x_G && pressCountA*y_A + pressCountB*y_B == y_G;
        }

        private double round(double value, int decimalPoints) {
            double d = Math.pow(10, decimalPoints);
            return Math.round(value * d) / d;
        }
    }

    public static void main(String[] args) {
        boolean part2 = true;
        new Day13_ClawContraption(part2);
    }
}
