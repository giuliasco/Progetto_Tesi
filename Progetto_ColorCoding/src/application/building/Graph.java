package application.building;

import java.util.*;

public class Graph {
	protected ArrayList<ArrayList<Integer>> adj;
	protected int V;

	public Graph() {
		adj = null;
		V = 0;
	}

	public Graph(ArrayList<ArrayList<Integer>> adjacency) {
		this.adj = adjacency;
		this.V = adj.size();
	}

	public Graph(int v) {
		V = v;
		adj = new ArrayList<ArrayList<Integer>>(V);
		for (int i = 0; i < V; i++)
			adj.add(new ArrayList<Integer>());
	}

	public void setV(int V) {
		this.V = V;
	}

	public int getV() {
		return V;
	}

	public void setAdj(ArrayList<ArrayList<Integer>> adj) {
		this.adj = adj;
	}

	public ArrayList<ArrayList<Integer>> getAdj() {
		return adj;
	}

	public void addEdge(int u, int v) {
		adj.get(u).add(v);
		adj.get(v).add(u);
	}

	public int[] colorGraph(int k) {

		int c[];
		c = new int[V];
		for (int i = 0; i < V; i++)
			c[i] = (int) (Math.random() * k + 1);

		return c;

	}


	public void printAdjacencyList() {
		for (int i = 0; i < adj.size(); i++) {
			System.out.println("Adjacency list of " + i);
			for (int j = 0; j < adj.get(i).size(); j++) {
				System.out.print(adj.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

}