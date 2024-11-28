package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class Day23_LongWalk {
	// https://www.geeksforgeeks.org/find-longest-path-directed-acyclic-graph/
	public static int fieldSize = 141; // 23 | 141
	public static List<Node> nodes = new ArrayList<>();
	public static List<Edge> edges = new ArrayList<>();
	public static int nodeCount = 0;
	public static char[][] field = new char[fieldSize][fieldSize];
	public static byte[][] visited = new byte[fieldSize][fieldSize];

	public static void main(String[] args) {
		BufferedReader reader;
		initilize();
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day23_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day23.txt"));
			String line = reader.readLine();
			int y=0;
			while (line != null) {
				field[y++] = line.toCharArray();
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// create nodes
		nodes.add(new Node(1, 0, nodeCount++));
		createNodes();
		nodes.add(new Node(fieldSize-2, fieldSize-1, nodeCount++));
		// create edges
		computeEdges();
		// set up graph
		Graph g = new Graph(edges);
		System.out.println("--------------------------------");
		g.longestPath(0);
	}

	private static void initilize() {
		for (int y=0; y<fieldSize; y++) {
			for (int x=0; x<fieldSize; x++) {
				visited[y][x] = 0;
			}
		}
	}

	private static void computeEdges() {
		for (int i=0; i<nodes.size()-1; i++) {
			Node n = nodes.get(i);
			computeEdge(n, n.x, n.y, 0, false);
		}

	}

	private static void computeEdge(Node n, int x, int y, int steps, boolean checkCoordinate) {
		//System.out.println(x + " " + y + " " + steps);
		if (checkCoordinate && isNode(x, y)) {
			int i = nodes.indexOf(new Node(x, y, -1));
			Edge edge = new Edge(n, nodes.get(i), steps);
			edges.add(edge);
			System.out.println(" --> " + edge);
		} else {
			visited[y][x] = 1;
			if (x - 1 >= 0 && visited[y][x-1]==0 && field[y][x-1] != '#' && field[y][x-1] != '>')
				computeEdge(n, x - 1, y, steps + 1, true);
			if (x + 1 < fieldSize && visited[y][x+1]==0 && field[y][x + 1] != '#')
				computeEdge(n, x + 1, y, steps + 1, true);
			if (y - 1 >= 0 && visited[y-1][x]==0 && field[y-1][x] != '#' && field[y-1][x] != 'v')
				computeEdge(n, x, y - 1, steps + 1, true);
			if (y + 1 < fieldSize && visited[y+1][x]==0 && field[y + 1][x] != '#')
				computeEdge(n, x, y + 1, steps + 1, true);
			visited[y][x] = 0;
		}
	}

	private static boolean isNode(int x, int y) {
		Node c_tmp = new Node(x, y, -1);
		return nodes.contains(c_tmp);
	}

	private static void createNodes() {
		for (int y=1; y<fieldSize-1; y++) {
			for (int x=1; x<fieldSize-1; x++) {
				int count = 0;
				if (field[y][x] == '.') {
					if (field[y - 1][x] == 'v') count++;
					if (field[y][x - 1] == '>') count++;
					if (field[y + 1][x] == 'v') count++;
					if (field[y][x + 1] == '>') count++;
					if (count >=2) {
						Node c = new Node(x, y, nodeCount++);
						if (!nodes.contains(c)) nodes.add(c);
					}
				}
			}
		}
	}

	public static class Node {
		public int x, y, nr;

		@Override
		public String toString() {
			return "[ Nr=" + nr + " : y=" + y + ", x=" + x + " ]";
		}
		public Node(int x, int y, int nr) {
			this.x = x;
			this.y = y;
			this.nr = nr;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Node that = (Node) o;
			return x == that.x && y == that.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}

	public static class Edge {
		public Node startNode, endNode;
		public int weight;

		public Edge(Node n1, Node n2, int weight) {
			this.startNode = n1;
			this.endNode = n2;
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Edge: " +
					"Start : " + startNode +
					", End : " + endNode +
					", weight=" + weight;
		}
	}

	static class AdjListNode {
		int v, weight;
		AdjListNode(int _v, int _w) {
			v = _v;
			weight = _w;
		}
		int getV() { return v; }
		int getWeight() { return weight; }
	}

	static class Graph {
		int V; // No. of vertices'

		// Pointer to an array containing adjacency lists
		ArrayList<ArrayList<AdjListNode>> adj;

		Graph(List<Edge> edges) {
			this.V = edges.size();
			adj = new ArrayList<ArrayList<AdjListNode>>(V);

			for(int i = 0; i < V; i++){
				adj.add(new ArrayList<AdjListNode>());
			}
			for (Edge e : edges) {
				addEdge(e.startNode.nr, e.endNode.nr, e.weight);
			}
		}

		void addEdge(int u, int v, int weight) {
			AdjListNode node = new AdjListNode(v, weight);
			adj.get(u).add(node); // Add v to u's list
		}

		// A recursive function used by longestPath. See below
		// link for details
		// https:// www.geeksforgeeks.org/topological-sorting/
		void topologicalSortUtil(int v, boolean visited[], Stack<Integer> stack) {
			// Mark the current node as visited
			visited[v] = true;

			// Recur for all the vertices adjacent to this vertex
			for (int i = 0; i<adj.get(v).size(); i++) {
				AdjListNode node = adj.get(v).get(i);
				if (!visited[node.getV()])
					topologicalSortUtil(node.getV(), visited, stack);
			}

			// Push current vertex to stack which stores topological sort
			stack.push(v);
		}

		// The function to find longest distances from a given vertex.
		// It uses recursive topologicalSortUtil() to get topological
		// sorting.
		void longestPath(int s) {
			Stack<Integer> stack = new Stack<Integer>();
			int dist[] = new int[V];

			// Mark all the vertices as not visited
			boolean visited[] = new boolean[V];
			for (int i = 0; i < V; i++)
				visited[i] = false;

			// Call the recursive helper function to store Topological
			// Sort starting from all vertices one by one
			for (int i = 0; i < V; i++)
				if (visited[i] == false)
					topologicalSortUtil(i, visited, stack);

			// Initialize distances to all vertices as infinite and
			// distance to source as 0
			for (int i = 0; i < V; i++)
				dist[i] = Integer.MIN_VALUE;

			dist[s] = 0;

			// Process vertices in topological order
			while (stack.isEmpty() == false) {
				// Get the next vertex from topological order
				int u = stack.peek();
				stack.pop();

				// Update distances of all adjacent vertices ;
				if (dist[u] != Integer.MIN_VALUE)
				{
					for (int i = 0; i<adj.get(u).size(); i++)
					{
						AdjListNode node = adj.get(u).get(i);
						if (dist[node.getV()] < dist[u] + node.getWeight())
							dist[node.getV()] = dist[u] + node.getWeight();
					}
				}
			}

			// Print the calculated longest distances
			for (int i = 0; i < V; i++)
				if(dist[i] == Integer.MIN_VALUE)
					System.out.print("INF ");
				else
					System.out.print(dist[i] + " ");
		}
	}
}
