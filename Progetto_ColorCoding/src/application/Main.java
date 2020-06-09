package application;
import java.util.*;

//import java.util.ArrayList;
import application.building.*;

public class Main {

	public static void main(String[] args) {
		int k = 9;

		String csvFIle = "/home/giulia/Scrivania/Progetto_Tesi/lastfm_asia_edges.csv";
		int v = 7624;
		Graph graph = new Graph(v);
		graph.graphByFile(csvFIle);
		Table b=new Table();
		b.optGraph(graph,k);

			for (int i=0; i< graph.V ; i++) {
				System.out.println("La tabella per il nodo   " + i + "  è   " + b.table.get(i));
				System.out.println("La tabella normalizzata per il nodo   " + i + "  è   " + b.betaTable.get(i));
			}








	}

	}




