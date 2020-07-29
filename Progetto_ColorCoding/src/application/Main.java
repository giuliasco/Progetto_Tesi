package application;
import java.util.*;

//import java.util.ArrayList;
import application.building.*;

public class Main {

	public static void main(String[] args) {
		/*
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(6);
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a1.add(1);
		a1.add(5);
		a2.add(0);
		a2.add(2);
		a2.add(5);
		a3.add(1);
		a3.add(3);
		a3.add(4);
		a4.add(2);
		a4.add(4);
		a5.add(2);
		a5.add(3);
		a5.add(5);
		a6.add(0);
		a6.add(1);
		a6.add(5);
		adj.add(a1);
		adj.add(a2);
		adj.add(a3);
		adj.add(a4);
		adj.add(a5);
		adj.add(a6);
		Graph g2 = new Graph(adj);
		 */
		Scanner scanner = new Scanner(System.in);


		System.out.print("inserisci il tuo file csv, con il proprio percorso : ");
		String csvFile = scanner.next();

		System.out.print("inserisci il numero di colori che deve avere il grafo: ");
		String color = scanner.next();
		int c = Integer.parseInt(color);

		System.out.print("inserisci la dimensione desiderata del treelet k  : ");
		String dim = scanner.next();
		int k = Integer.parseInt(dim);

		Graph graph = new Graph(csvFile);
		Table b = new Table();
		b.optGraph(graph,c,k);
		b.writeToCsvFile(b.table);

	}
	}




