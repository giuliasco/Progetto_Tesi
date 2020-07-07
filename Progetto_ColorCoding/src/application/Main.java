package application;
import java.util.*;

//import java.util.ArrayList;
import application.building.*;

public class Main {

	public static void main(String[] args) {

		/*Scanner scanner = new Scanner(System.in);


		System.out.print("inserisci il tuo file csv, con il proprio percorso : ");
		String csvFile = scanner.next();

		System.out.print("inserisci il numero di nodi del grafo: ");
		int v = scanner.nextInt();

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

		Graph graph = new Graph(v);
		graph.graphByFile(csvFile);
		Table b=new Table();
		b.optGraph(graph,c,k);*/

		Vector<Vector<HashMap<Integer,Integer>>> prova = new Vector<Vector<HashMap<Integer, Integer>>>();
		int h=2;
		for (int i=0; i<10;i++) {
			HashMap<Integer, Integer> culo = new HashMap<Integer, Integer>();
			culo.put(i,i);
			Vector<HashMap<Integer,Integer>> cazzi = new Vector<HashMap<Integer, Integer>>(4);
			cazzi.add(1,culo);
			prova.add(cazzi);
		}
		/*HashMap<Integer,Integer> cazzini = new HashMap<Integer, Integer>();
		cazzini.put(2,2);

		prova.get(h).add(cazzini);*/

		System.out.println(prova.get(3).get(1));

	}

	}




