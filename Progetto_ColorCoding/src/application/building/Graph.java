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




	public LinkedList<HashMap<Integer, Integer>> optGraph( int k) {
		int h=2;
		LinkedList<HashMap<Integer, Integer>> occVector = new LinkedList<HashMap<Integer, Integer>>();
		LinkedList<LinkedList<Treelet>> vectorTree = new LinkedList<LinkedList<Treelet>>();
		LinkedList<LinkedList<Double>> betaOcc = new LinkedList<LinkedList<Double>>();
		int[] color = this.colorGraph(k);
		//per ogni vertice creiamo l'albero composto dal solo nodo con occorrenza 1;
		for (int v = 0; v < this.V; v++) {
			ColorNode node = new ColorNode(v, color[v]);
			Treelet tree = new Treelet(node);
			HashMap<Integer, Integer> opt = new HashMap<Integer, Integer>();
			LinkedList<Treelet> treeSet= new LinkedList<Treelet>();
			treeSet.add(tree);
			vectorTree.add(treeSet);
			int occ=1;
			opt.put(tree.num, occ);
			occVector.add(opt);
		}

		//Parte dinamica
		while(h <= k ) {
			for (int v = 0; v < adj.size(); v++) {
				LinkedList<Treelet> tmp = new LinkedList<Treelet>();
				for (Treelet x : vectorTree.get(v)) {
					for (int u = 0; u < adj.get(v).size(); u++) {
					int w = adj.get(v).get(u);
						for (Treelet y : vectorTree.get(w)) {
							Treelet z = new Treelet();
							if (x.size < h && y.size == (h - x.size))
							z = z.mergeTreelets(x, y);
							if (!z.isEmpty()) {
								int occ = (occVector.get(v).get(x.num)) * (occVector.get(w).get(y.num));
								if(occVector.get(v).containsKey(z.num)) {
									int occ1=occ + occVector.get(v).get(z.num);
									occVector.get(v).put(z.num,occ1);
								}else occVector.get(v).put(z.num,occ);

								tmp.add(z);
							}
						}
						}
				}
				if(!tmp.isEmpty()){
					for (Treelet let : tmp)
						vectorTree.get(v).add(let);
				}
			}
			h++;
		}

		/*for(int v = 0; v<this.V ; v++) {
			Double opt;
			for (Treelet tree : vectorTree.get(v)){
				if(tree.size == k){
					String s1= String.valueOf(tree.beta);
					Double beta= Double.valueOf(s1) ;
					int occ1 =occVector.get(v).get(tree.num);
					opt= occ1/beta;
					System.out.println("Le occorrenze sul nodo " + v +" del treelet " + tree + "sono :   "  + opt  );
				}
			}
		}*/



		return occVector;
	}


}



/*
devo capire se c'è un modo che mi permette facilmente dalla numero del treelet di risalire al treelet stesso???? questo mi serv per i merge
però salvarmi il treelet nell'haash map ad ora non è funzionale perchè dovrei incasinare una frega le cose per occ.
DA CAPIRE
 */