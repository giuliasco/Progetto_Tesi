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
		String vertix = scanner.next();
		String s = "";
		for (int i=0; i<vertix.length(); i++){
			char c = vertix.charAt(i);
			if(Character.isDigit(c))
				s+=c;
		}
		int v = Integer.parseInt(s);*/
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

		System.out.print("inserisci il numero di colori che deve avere il grafo: ");
		String color = scanner.next();
		int c = Integer.parseInt(color);


		System.out.print("inserisci la dimensione desiderata del treelet k  : ");
		String dim = scanner.next();
		int k = Integer.parseInt(dim);
		if (k<c) {
			System.out.println("inserisci una dimensione del treelet maggiore o uguale al numero di colori");
			System.out.print("inserisci la nuova dimensione : ");
			String dim1 = scanner.next();
			int k1 = Integer.parseInt(dim1);
			k=k1;
		}


		//Graph graph = new Graph(v);
		//graph.graphByFile(csvFile);
		Table b=new Table();
		b.optGraph(g2,c,k);
		for (int i=0 ; i<b.table.size(); i++) {
			for(int j=0; j<b.table.get(i).size(); j++){
				if (b.table.get(i).get(j)!= null){
					for(Treelet t : b.table.get(i).get(j).keySet()){
						System.out.println( "l'albero del nodo " + i + "   son con forma   " + t.num + "   " + b.table.get(i).get(j).get(t));
					}
				}

			}


		}


	}
	}




