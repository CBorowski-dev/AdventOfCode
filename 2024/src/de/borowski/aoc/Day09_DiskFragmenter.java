package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day09_DiskFragmenter {
    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day09_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day09.txt";

    private final List<Integer> input = new ArrayList<>();

    public Day09_DiskFragmenter() {
        readInput();
        // moveBlocks_Part1();
        moveBlocks_Part2();
        System.out.println("--------------------------------");
        System.out.println(calculateChecksum());
    }

    private long calculateChecksum() {
        long checksum = 0;
        long index = 0;
        for (int x: input) {
            if (x != -1) checksum += (index * x);
            index++;
        }
        return checksum;
    }

    private void moveBlocks_Part1() {
        int endPointer = input.size()-1;
        if (input.get(endPointer) == -1) endPointer = moveEndPointerBackwards(endPointer);
        for (int i=0; i<endPointer; i++) {
            if (input.get(i) == -1) {
                input.add(i, input.get(endPointer));
                input.remove(i+1);
                input.remove(endPointer);
                endPointer--;
                if (input.get(endPointer) == -1) endPointer = moveEndPointerBackwards(endPointer);
            }
        }
    }

    private void moveBlocks_Part2() {
        int startPointer, currentID, fileLength, startIndex;
        int endPointer = input.size()-1;
        do {
            if (input.get(endPointer) == -1) endPointer = moveEndPointerBackwards(endPointer);
            currentID = input.get(endPointer);
            // search first occurence of this ID
            startPointer = input.indexOf(currentID);
            fileLength = endPointer - startPointer + 1;

            // search for free space from the beginning
            startIndex = searchFreeSpace(fileLength, startPointer - 1);
            if (startIndex >= 0) {
                if (startIndex >= startPointer) return;
                // copy file
                for (int i = 0; i < fileLength; i++) {
                    input.remove(startIndex + i);
                    input.add(startIndex + i, currentID);
                    input.remove(startPointer + i);
                    input.add(startPointer + i, -1);
                }
            }
            endPointer = startPointer - 1;
        } while (true);
    }

    private int searchFreeSpace(int fileLength, int maxIndex) {
        int startPointer = input.indexOf(-1);
        do {
            int length = 1;
            while (input.get(startPointer + length).equals(-1)) length++;
            if (length >= fileLength) return startPointer;
            else {
                startPointer += length;
                while (startPointer <= maxIndex && !input.get(startPointer).equals(-1)) startPointer++;
                if (startPointer >= maxIndex) return -1;
            }
        } while (true);
    }

    private int moveEndPointerBackwards(int endPointer) {
        for (int i = endPointer; i>=0; i--) {
            if (input.get(i) != -1) return i;
        }
        return 0;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                // System.out.println(line);
                int id=0;
                for (int x=0; x<line.length(); x++) {
                    int c = Integer.parseInt(String.valueOf(line.charAt(x)));
                    for (int i=0; i<c; i++) input.add((x % 2 == 0) ? id : -1);
                    if (x % 2 == 0) id++;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Coordinate {
        public int y, x;

        public Coordinate(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return y == that.y && x == that.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y, x);
        }
    }

    public static void main(String[] args) { new Day09_DiskFragmenter(); }
}
