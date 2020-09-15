package application;
import java.util.*;
import application.building.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args ) {

		/*Scanner scanner = new Scanner(System.in);

		System.out.print("inserisci il tuo file, con il proprio percorso : ");
		String file = scanner.next();

		System.out.print("inserisci il numero di colori che deve avere il grafo: ");
		String color = scanner.next();
		int c = Integer.parseInt(color);

		System.out.print("inserisci la dimensione desiderata del treelet k  : ");
		String dim = scanner.next();
		int k = Integer.parseInt(dim);

		String file = new String();


		 */
		String file = args[0];
		Graph graph = new Graph(file);

		int c = Integer.valueOf(args[1]);

		int k = Integer.valueOf(args[2]);

		int thread = Integer.valueOf(args[3]);


		int coloration = Integer.valueOf(args[4]); // 1 per colorazione random , mentre 2 per round robin

		String path = args[5];


		Table b = new Table(graph,c,k,thread,coloration);
		try {
			b.build();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		b.writeToCsvFile(path);
	}
	}




