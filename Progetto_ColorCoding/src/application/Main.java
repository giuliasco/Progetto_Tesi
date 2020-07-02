package application;
import java.util.*;

//import java.util.ArrayList;
import application.building.*;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);


		/*System.out.print("inserisci il tuo file csv, con il proprio percorso : ");
		String csvFile = scanner.next();

		System.out.print("inserisci il numero di nodi del grafo: ");
		int v = scanner.nextInt();*/

		System.out.print("inserisci il numero di colori che deve avere il grafo: ");
		int c = scanner.nextInt();

		System.out.print("inserisci la dimensione desiderata del treelet k : ");
		int k = scanner.nextInt();
		if (k<c) {
			System.out.println("inserisci una dimensione del treelet maggiore o uguale al numero di colori");
			System.out.print("inserisci la nuova dimensione : ");
			int k1 = scanner.nextInt();
			k=k1;
		}
		System.out.println(k);
		/*Graph graph = new Graph(v);
		graph.graphByFile(csvFile);
		Table b=new Table();
		b.optGraph(graph,c,k);*/

		/*int k = 8;

		String csvFIle = "/home/giulia/Scrivania/Progetto_Tesi/lastfm_asia_edges.csv";
		int v = 7624;
		Graph graph = new Graph(v);
		graph.graphByFile(csvFIle);
		Table b=new Table();
		b.optGraph(graph,k);*/




	}

	}




