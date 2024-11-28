package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Day17_ClumsyCrucibleAStar {

    public static class Node {
        public Node parent;
        public int x, y;
        public int g_val = Integer.MAX_VALUE;
        public boolean isVisited = false;
        public int steps = 0;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node cell = (Node) o;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public Node(int y, int x, Node parent, int steps) {
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

    void aStarSearch(int[][] field, int rows, int cols, int srcX, int srcY, int destX, int destY) {
        Node[][] cellField = new Node[dim][dim];
        Node startCell = new Node(srcY, srcX, null, 0);
        startCell.g_val = 0;
        cellField[srcY][srcX] = startCell;

        PriorityQueue<Node> openQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.g_val /*+ (dim-1 - node.x) + (dim-1 - node.y)*/));
        openQueue.add(startCell);

        while (!openQueue.isEmpty()) {
            Node node = openQueue.poll(); // Remove from the open list
            if (node.x == destX && node.y == destY) {
                printPath(node);
                System.out.println("--> " + node.g_val);
                return;
            }
            node.isVisited = true;
            printField(cellField, node.y, node.x);
            expandNode(field, rows, cols, cellField, node, openQueue);
        }
        System.out.println("Failed to find the destination cell.");
    }

    private void expandNode(int[][] field, int rows, int cols, Node[][] nodeField, Node node, PriorityQueue<Node> openQueue) {
        int[] dx = { 1, 0, -1, 0 };
        int[] dy = { 0, -1, 0, 1 };
        int addX, addY;
        for (int i = 0; i < 4; i++) { // Generating all the 4 neighbors of the node
            addX = dx[i]; addY = dy[i];
            if (isDirectionAllowed(nodeField, node.y, node.x, addX, addY)) { // check if this direction is allowed
                int nY = node.y + addY;
                int nX = node.x + addX;
                if (nY >= 0 && nY < rows && nX >= 0 && nX < cols) {
                    Node nNode = nodeField[nY][nX];
                    if (nNode == null) {
                        nNode = new Node(nY, nX, node, node.steps + 1);
                        nodeField[nY][nX] = nNode;
                    } else if (nNode.isVisited) continue;
                    int ng_val = node.g_val + field[nY][nX];
                    if (openQueue.contains(nNode) && nNode.g_val <= ng_val) continue;
                    nNode.g_val = ng_val;
                    nNode.parent = node;
                    nNode.steps = node.steps + 1;
                    openQueue.remove(nNode);
                    openQueue.add(nNode);
                }
            }
        }
    }

    private void printField(Node[][] cellField, int _y, int _x) {
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

    private boolean isInPath(Node[][] cellField, int y, int x, int _y, int _x) {
        Node c = cellField[_y][_x];
        while (c.parent != null) {
            if (c.x == x && c.y == y) return true;
            c = c.parent;
        }
        return false;
    }

    private char getDirectionChar(Node cell) {
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

    private void printPath(Node nCell) {
        do {
            System.out.print("(" + nCell.y + ", " + nCell.x + ")<");
            nCell = nCell.parent;
        } while (nCell !=null);
        System.out.println();
    }

    private boolean isDirectionAllowed(Node[][] cellField, int y, int x, int x_d, int y_d) {
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
        Day17_ClumsyCrucibleAStar app = new Day17_ClumsyCrucibleAStar();
        app.aStarSearch(field, field.length, field[0].length, 0, 0, dim-1, dim-1);
    }
}