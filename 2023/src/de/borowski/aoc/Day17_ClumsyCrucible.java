package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Day17_ClumsyCrucible {

    public static class Cell {
        public Cell parent;
        public int x, y;
        public int totalDistance = Integer.MAX_VALUE;
        public boolean isVisited = false;
        public int steps = 0;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public Cell(int y, int x, Cell parent, int steps) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.steps = steps;
        }

        /*@Override
        public int compareTo(Cell o) {
            if (this.equals(o)) return 0;
            int vistualDistance1 = this.totalDistance + (dim-1 - x) + (dim-1 - y);
            int vistualDistance2 = o.totalDistance + (dim-1 - o.x) + (dim-1 - o.y);
            return vistualDistance1 - vistualDistance2;
        }*/
    }

    void dijkstraSearch(int[][] field, int rows, int cols, int srcX, int srcY, int destX, int destY) {
        Cell[][] cellField = new Cell[dim][dim];
        Cell startCell = new Cell(srcY, srcX, null, 0);
        startCell.totalDistance = 0;
        cellField[srcY][srcX] = startCell;

        PriorityQueue<Cell> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(cell -> cell.totalDistance));
        priorityQueue.add(startCell);

        int[] dx = { 1, 0, -1, 0 };
        int[] dy = { 0, 1, 0, -1 };
        int addX, addY;
        while (!priorityQueue.isEmpty()) {
            Cell cell = priorityQueue.poll(); // Remove from the open list
            if (cell.isVisited) continue;
            cell.isVisited = true;
            printField(cellField, cell.y, cell.x);
            if (cell.x == destX && cell.y == destY) {
                printPath(cell);
                System.out.println("--> " + cell.totalDistance);
                return;
            }
            for (int i = 0; i < dx.length; i++) { // Generating all the 4 neighbors of the cell
                addX = dx[i]; addY = dy[i];
                if (isDirectionAllowed(cellField, cell.y, cell.x, addX, addY)) { // check if this direction is allowed
                    int nY = cell.y + addY;
                    int nX = cell.x + addX;
                    if (nY >= 0 && nY < rows && nX >= 0 && nX < cols) {
                        Cell nCell = cellField[nY][nX];
                        if (nCell == null) {
                            nCell = new Cell(nY, nX, cell, cell.steps + 1);
                            cellField[nY][nX] = nCell;
                        }
                        int newDistance = cell.totalDistance + field[nY][nX] /*+ (dim-1 - nY) + (dim-1 - nX)*/;
                        if (newDistance <= nCell.totalDistance) {
                            nCell.totalDistance = newDistance;
                            nCell.parent = cell;
                            nCell.steps = cell.steps + 1;
                            priorityQueue.remove(nCell);
                            priorityQueue.add(nCell);
                        }
                    }
                }
            }
        }
        System.out.println("Failed to find the destination cell.");
    }

    private void printField(Cell[][] cellField, int _y, int _x) {
        for (int y=0; y<dim; y++) {
            for (int x=0; x<dim; x++) {
                if (y==_y && x==_x) System.out.print('*');
                else if (cellField[y][x] != null) {
                    System.out.print(cellField[y][x].isVisited? isInPath(cellField, y, x, _y, _x)?getDirectionChar(cellField[y][x]):'-' : 'o');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
        System.out.println("=========================================");
    }

    private boolean isInPath(Cell[][] cellField, int y, int x, int _y, int _x) {
        Cell c = cellField[_y][_x];
        while (c.parent != null) {
            if (c.x == x && c.y == y) return true;
            c = c.parent;
        }
        return false;
    }

    private char getDirectionChar(Cell cell) {
        if (cell.parent != null) {
            int x_d = cell.x - cell.parent.x;
            int y_d = cell.y - cell.parent.y;

            if (x_d > 0) return '>';
            if (x_d < 0) return '<';
            if (y_d > 0) return 'v';
            return '^';
        }
        return '.';
    }

    private void printPath(Cell nCell) {
        do {
            System.out.print("(" + nCell.y + ", " + nCell.x + ")<");
            nCell = nCell.parent;
        } while (nCell !=null);
        System.out.println();
    }

    private boolean isDirectionAllowed(Cell[][] cellField, int y, int x, int x_d, int y_d) {
        int countSameDirection = 0;
        for (int i=0; i<3; i++) {
            if (cellField[y][x].parent == null) break;
            int y_t = cellField[y][x].parent.y;
            int x_t = cellField[y][x].parent.x;
            int y_diff = y - y_t;
            int x_diff = x - x_t;
            if (y_diff == y_d && x_diff == x_d) countSameDirection++;
            y = y_t;
            x = x_t;
        }
        return countSameDirection<3;
    }

    public static int dim = 13;
    public static int[][] field = new int[dim][dim];

    public static void main(String[] args) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day17_testset.txt"));
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day17.txt"));
            String line = reader.readLine();

            int y = 0;
            while (line != null) {
                for (int x = 0; x < dim; x++) {
                    field[y][x] = Character.getNumericValue(line.charAt(x));
                }
                y++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Day17_ClumsyCrucible app = new Day17_ClumsyCrucible();
        app.dijkstraSearch(field, field.length, field[0].length, 0, 0, dim-1, dim-1);
    }
}