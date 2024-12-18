package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day15_WarehouseWoes_Part2 {
    int size = 50; // 10 50
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day15_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day15.txt";

    private final char[][] map = new char[size][size*2];
    private char[] movements;
    private int rX = 0;
    private int rY = 0;

    public Day15_WarehouseWoes_Part2() {
        readInput();
        moveRobot();
        System.out.println("--------------------------------");
        printMap();
        System.out.println(computeSumOfGPS());
    }

    private void moveRobot() {
        for (char m : movements) {
            if (m=='^' || m=='v') {
                Set<Integer> s = new HashSet<>();
                s.add(rX);
                if (moveRobot(s, rY, (m=='^') ? -1 : 1)) rY += (m=='^') ? -1 : 1;
            } else if (m=='>') {
                int freePosition = -1;
                for (int x=rX; x<(size*2)-1 && freePosition==-1; x++) {
                    if (map[rY][x]=='.') freePosition = x;
                    if (map[rY][x]=='#') break;
                }
                if (freePosition != -1) {
                    for (int x=freePosition; x>rX; x--) map[rY][x] = map[rY][x-1];
                    map[rY][rX++] = '.';
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

    private boolean moveRobot(Set<Integer> xAll, int y, int yDiff) {
        Set<Integer> xAllNew = new HashSet();
        for (int x: xAll) {
            // check, whats above (NORTH) or below (SOUTH)
            if (map[y+yDiff][x]=='#') return false;
            if (map[y+yDiff][x]!='.') {
                xAllNew.add(x);
                xAllNew.add((map[y+yDiff][x]=='[') ? x+1 : x-1);
            }
        }
        boolean spaceAvailable = true;
        if (!xAllNew.isEmpty()) spaceAvailable = moveRobot(xAllNew, y+yDiff, yDiff);
        if (spaceAvailable) {
            // move row
            for (int x: xAll) {
                map[y+yDiff][x]=map[y][x];
                map[y][x]='.';
            }
            return true;
        }
        return false;
    }

    private int computeSumOfGPS() {
        int sumOfGPS = 0;
        for (int y = 1; y< size-1; y++) {
            for (int x = 1; x< size*2-1; x++) {
                if (map[y][x]=='[') {
                    sumOfGPS += y*100 + x;
                }
            }
        }
        return sumOfGPS;
    }

    private void printMap() {
        for (int y = 0; y<size; y++) {
            for (int x = 0; x<size*2; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                if (row < size) {
                    map[row] = converLine(line);
                    if (line.indexOf('@')>0) {
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

    private char[] converLine(String line) {
        char[] row = new char[size*2];
        int idx = 0;
        for (int i=0; i<line.length(); i++) {
            char c = line.charAt(i);
            if (c=='#') {
                row[idx++] = '#';
                row[idx++] = '#';
            } else if (c=='O') {
                row[idx++] = '[';
                row[idx++] = ']';
            } else if (c=='.') {
                row[idx++] = '.';
                row[idx++] = '.';
            } else if (c=='@') {
                rX = idx;
                row[idx++] = '@';
                row[idx++] = '.';
            }
        }
        return row;
    }

    public static void main(String[] args) { new Day15_WarehouseWoes_Part2(); }
}
