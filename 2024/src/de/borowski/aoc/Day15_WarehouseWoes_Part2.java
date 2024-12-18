package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day15_WarehouseWoes_Part2 {
    int size =50;
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day15_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day15.txt";

    private final char[][] map = new char[size][size];
    private char[] movements;
    private int rX = 0;
    private int rY = 0;

    public Day15_WarehouseWoes_Part2() {
        readInput();
        moveRobot();
        System.out.println("--------------------------------");
        System.out.println(computeSumOfGPS());
    }

    private void moveRobot() {
        for (char m : movements) {
            if (m=='^') {
                int freePosition = -1;
                for (int y=rY; y>0 && freePosition==-1; y--) {
                    if (map[y][rX]=='.') freePosition = y;
                    if (map[y][rX]=='#') break;
                }
                if (freePosition != -1) {
                    for (int y=freePosition; y<rY; y++) map[y][rX] = map[y+1][rX];
                    map[rY--][rX] = '.';
                }
            } else if (m=='>') {
                int freePosition = -1;
                for (int x=rX; x < size-1 && freePosition==-1; x++) {
                    if (map[rY][x]=='.') freePosition = x;
                    if (map[rY][x]=='#') break;
                }
                if (freePosition != -1) {
                    for (int x=freePosition; x>rX; x--) map[rY][x] = map[rY][x-1];
                    map[rY][rX++] = '.';
                }
            } else if (m=='v') {
                int freePosition = -1;
                for (int y=rY; y< size-1 && freePosition==-1; y++) {
                    if (map[y][rX]=='.') freePosition = y;
                    if (map[y][rX]=='#') break;
                }
                if (freePosition != -1) {
                    for (int y=freePosition; y>rY; y--) map[y][rX] = map[y-1][rX];
                    map[rY++][rX] = '.';
                }
            } else if (m=='<') {
                int freePosition = -1;
                for (int x=rX; x > 0 && freePosition==-1; x--) {
                    if (map[rY][x]=='.') freePosition = x;
                    if (map[rY][x]=='#') break;
                }
                if (freePosition != -1) {
                    for (int x=freePosition; x<rX; x++) map[rY][x] = map[rY][x+1];
                    map[rY][rX--] = '.';
                }
            }
        }
    }

    private int computeSumOfGPS() {
        int sumOfGPS = 0;
        for (int y = 1; y< map.length-1; y++) {
            for (int x = 1; x< map.length-1; x++) {
                if (map[y][x]=='O') {
                    sumOfGPS += y*100 + x;
                }
            }
        }
        return sumOfGPS;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                if (row < size) {
                    map[row] = line.toCharArray();
                    if (line.indexOf('@')>0) {
                        rX = line.indexOf('@');
                        rY = row;
                    }
                    row++;
                } else if (!line.isBlank()) {
                    movements = line.toCharArray();
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { new Day15_WarehouseWoes_Part2(); }
}
