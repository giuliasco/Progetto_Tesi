package application.building;

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
	//vedere se può essere utile un metodo che mi aggiunge un vertice

	public void treeletFromGraph(Graph g,int k){
		int occ=0;
		Vector<HashMap<Treelet, Integer>> vector = new Vector<HashMap<Treelet, Integer>>();
		int color[] =g.colorGraph(k);
		//per ogni nodo creiamo l'albero composto dal solo nodo con occorrenza 1;
		for ( int v=0 ; v<g.V ; v++ ){
			ColorNode c = new ColorNode (v,color[v]);
			Treelet t = new Treelet(c);
			HashMap<Treelet,Integer> opt = new HashMap<Treelet, Integer>();
			opt.put(t,occ+1);
			vector.add(opt);
		}
		for(int h=2; h==k ; h++){
			for ( int u=0; u<g.adj.size() ;u++){
				for(int v = 0 ; v < g.adj.get(u).size() ; v++){

				}
			}
		}

	}
}

/*
devo capire se c'è un modo che mi permette facilmente dalla numero del treelet di risalire al treelet stesso???? questo mi serv per i merge
però salvarmi il treelet nell'haash map ad ora non è funzionale perchè dovrei incasinare una frega le cose per occ.
DA CAPIRE
 */