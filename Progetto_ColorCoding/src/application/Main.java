package application;
import java.util.*;
import application.building.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args) {

		/*Scanner scanner = new Scanner(System.in);

		System.out.print("inserisci il tuo file, con il proprio percorso : ");
		String file = scanner.next();

		System.out.print("inserisci il numero di colori che deve avere il grafo: ");
		String color = scanner.next();
		int c = Integer.parseInt(color);

		System.out.print("inserisci la dimensione desiderata del treelet k  : ");
		String dim = scanner.next();
		int k = Integer.parseInt(dim);

		Graph graph = new Graph(file);

		Table b = new Table(graph,c,k,3);
		try {
			b.build();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		b.writeToCsvFile();*/

		int prova[] = {1,2,3,4,0,5,6};

		for (int i=0 ;i<prova.length;i++){
			if (prova[i] == 0) break;
			System.out.println(prova[i]);
		}

	}
	}




