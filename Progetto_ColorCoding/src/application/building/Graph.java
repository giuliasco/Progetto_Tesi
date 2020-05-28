package application.building;

import sun.reflect.generics.tree.Tree;

import java.util.*;

public class Graph {
	public ArrayList<ArrayList<Integer>> adj;
	public int V;

	//costruttore vuoto
	public Graph() {
		adj = null;
		V = 0;
	}

	//costruttore che costruisce il grafo a partire della lista di adiacenza
	public Graph(ArrayList<ArrayList<Integer>> adjacency) {
		this.adj = adjacency;
		this.V = adj.size();
	}

	//costruttore che costruisce un grafo con tutti nodi isolati, al quale si possono aggiungere gli archi in seguito
	public Graph(int v) {
		V = v;
		adj = new ArrayList<ArrayList<Integer>>(V);
		for (int i = 0; i < V; i++)
			adj.add(new ArrayList<Integer>());
	}


	//metodo per aggiungere un arco
	public void addEdge(int u, int v) {
		adj.get(u).add(v);
		adj.get(v).add(u);
	}

	//metodo per colorare il grafo
	public int[] colorGraph(int k) {
		int c[];
		c = new int[V];
		for (int i = 0; i < V; i++)
			c[i] = (int) (Math.random() * k + 1);
		return c;
	}

	//stampare la lista di adiacenza
	public void printAdjacencyList()
	{
		for (int i = 0; i < adj.size(); i++) {
			System.out.println("Adjacency list of " + i);
			for (int j = 0; j < adj.get(i).size(); j++) {
				System.out.print(adj.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}



}



/*
devo capire se c'è un modo che mi permette facilmente dalla numero del treelet di risalire al treelet stesso???? questo mi serv per i merge
però salvarmi il treelet nell'haash map ad ora non è funzionale perchè dovrei incasinare una frega le cose per occ.
DA CAPIRE
 */