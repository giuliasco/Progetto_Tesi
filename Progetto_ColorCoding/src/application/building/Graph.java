package application.building;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class Graph {
	public ArrayList<HashSet<Integer>> adj;
	public int V;
	private int added=0;



	public Graph(String s )
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println( "["+timestamp+"] " + "CREAZIONE DEL GRAFO");
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
			adj = new ArrayList<HashSet<Integer>>();
			for (int i = 0; i < V; i++)
				adj.add(new HashSet<Integer>());
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
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
		System.out.println( "["+timestamp1+"] " + "GRAFO CREATO CORRETTAMENTE");
	}



	/*
	metodo per la colorazione del grafo, sfruttando i seed, se il seed è uguale a 0 si avrà una colorazione round robin,
	mentre per seed diversi da 0 si avrà ua colorazione random basata sul seed.
	 */
	public int[] colorGraph (int c , long seed)
	{
		int color[] = new int[V];

        if(seed == 0)
		{
			for (int j = 0; j < V; j++)
				color[j] = j % c;
		}
		else
		{
		    Random generator = new Random(seed);
			for (int j = 0; j < V; j++)
				color[j] = generator.nextInt(c);
		}

        return color;
	}



}




