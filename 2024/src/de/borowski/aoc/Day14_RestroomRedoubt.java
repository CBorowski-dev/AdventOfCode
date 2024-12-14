package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day14_RestroomRedoubt {
    // int xSize =11;
    // int ySize =7;
    int xSize =101;
    int ySize =103;

    int sec = 100;
    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day14_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day14.txt";

    List<Robot> robots = new ArrayList<>();

    public Day14_RestroomRedoubt() {
        readInput();
        System.out.println("--------------------------------");
        // System.out.println(calculateSafetyFactor()); // Part 1
        for (int sec=11; sec<10000; sec += 101) { // Part 2
            displayRoom(sec);
        }
    }

    private void displayRoom(int sec) {
        char[][] d = new char[ySize][xSize];
        for (int y=0; y<ySize; y++) {
            for (int x=0; x<xSize; x++) {
                d[y][x] = ' ';
            }
        }

        for (Robot r: robots) {
            r.calculateFuturePosition(sec);
            d[r.yFuture][r.xFuture] = '*';
        }
        System.out.print(sec);
        for (int y=0; y<ySize; y++) {
            for (int x=0; x<xSize; x++) {
                System.out.print(d[y][x]);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    private int calculateSafetyFactor() {
        int[] quadrants = new int[] {0,0,0,0};
        for (Robot r: robots) {
            r.calculateFuturePosition(sec);
            int xMiddle = (xSize - 1) / 2;
            int yMiddle = (ySize - 1) / 2;
            if (r.xFuture != xMiddle && r.yFuture != yMiddle) {
                if (r.xFuture < xMiddle) {
                    if (r.yFuture < yMiddle) quadrants[0]++; else quadrants[1]++;
                } else {
                    if (r.yFuture < yMiddle) quadrants[2]++; else quadrants[3]++;
                }
            }
        }
        return quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3];
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(" ");
                // position
                values[0] = values[0].substring(2);
                String[] pos = values[0].split(",");
                // velocity
                values[1] = values[1].substring(2);
                String[] vel = values[1].split(",");

                Robot r = new Robot(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), Integer.parseInt(vel[0]), Integer.parseInt(vel[1]));
                robots.add(r);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Robot {
        int x, y, vX, vY;

        int xFuture, yFuture;

        public Robot(int x, int y, int vX, int vY) {
            this.x = x;
            this.y = y;
            this.vX = vX;
            this.vY = vY;
        }

        public void calculateFuturePosition(int seconds) {
            xFuture = (x + (vX * seconds)) % xSize;
            xFuture = (xFuture < 0) ? xSize + xFuture : xFuture;
            yFuture = (y + (vY * seconds)) % ySize;
            yFuture = (yFuture < 0) ? ySize + yFuture : yFuture;
        }
    }

    public static void main(String[] args) { new Day14_RestroomRedoubt(); }
}
