package application.building;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
	public ArrayList<ArrayList<Integer>> adj;
	public int V;
	private int added=0;



	public Graph(String s )
	{
		String line = "";
		HashSet<Integer> vertex = new HashSet<Integer>();
		//inserisco i vertici e creo la lista di adiacenza

		try (BufferedReader br = new BufferedReader(new FileReader(s)))
		{
			while((line=br.readLine()) != null){
				String[] edge = line.split(",|\t|\\ ");

				if (edge[0] != null && !edge[0].isEmpty() && edge[1] != null && !edge[1].isEmpty() )
				{
					int u = Integer.parseInt(edge[0]);
					int w = Integer.parseInt(edge[1]);
					vertex.add(u);
					vertex.add(w);
				}
			}
			V = vertex.size();
			if(!vertex.contains(0)) added=-1;
			adj = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i < V; i++)
				adj.add(new ArrayList<Integer>());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(s)))
		{
			while((line=br.readLine()) != null){
				String[] edge = line.split(",|\t|\\ ");
				if (edge[0] != null && !edge[0].isEmpty() && edge[1] != null && !edge[1].isEmpty() )
				{
					int u = Integer.parseInt(edge[0]) + added;
					int w = Integer.parseInt(edge[1]) + added;
					adj.get(u).add(w);
					adj.get(w).add(u);
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}





	//metodo per aggiungere un arco
	public void addEdge(int u, int v)
	{
			adj.get(u).add(v);
			adj.get(v).add(u);
	}


	public int[] colorGraph (int c) {
		int color[] = new int[V];

		Scanner scanner = new Scanner(System.in);
		System.out.println("inserisci il metodo desiderato per la colorazione del grafo :");
		System.out.println("1) Random");
		System.out.println("2) Round Robin");
		System.out.print("Digita il numero corrispondente al metodo desiderato :");
		String answer = scanner.next();
		if (answer.equals("1"))
		{
			for (int i = 0; i < V; i++)
				color[i] = (int) (Math.random() * c + 1);
		}
		else if (answer.equals("2"))
		{
			for (int i = 0; i < V; i++)
				color[i] = i % c;
		}
		return color;
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
	//costruttore vuoto
	public Graph() {
		adj = new ArrayList<ArrayList<Integer>>();
		V = 0;
	}

	//costruttore che costruisce il grafo a partire della lista di adiacenza
	public Graph(ArrayList<ArrayList<Integer>> adjacency) {
		this.adj = adjacency;
		this.V = adj.size();
	}

	//costruttore che costruisce un grafo con tutti nodi isolati, al quale si possono aggiungere gli archi in seguito
	public Graph(int v)
	{
		V = v;
		adj = new ArrayList<ArrayList<Integer>>(V);
		for (int i = 0; i < V; i++)
			adj.add(new ArrayList<Integer>());
	}
 */