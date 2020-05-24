package application;
import java.util.*;

//import java.util.ArrayList;
import application.building.*;

public class Main {

	public static void main(String[] args) {
		int k = 2;
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



		/*ColorNode c1= new ColorNode(1, 1);
		ColorNode c2= new ColorNode(2, 2);
		ColorNode c3= new ColorNode(3, 3);
		ColorNode c4= new ColorNode(4, 4);
		ColorNode c5= new ColorNode(5, 5);
		Treelet t1=new Treelet(c5);
		Treelet t2= new Treelet(c3);
		Treelet t3= new Treelet(c4);
		ArrayList<Treelet> subtree1 = new ArrayList<Treelet>();
		ArrayList<Treelet> subtree2 = new ArrayList<Treelet>();
		subtree1.add(t2);
		subtree1.add(t3);
		Treelet t4 = new Treelet(subtree1, c2);


		subtree2.add(t1);
		subtree2.add(t4);
		Treelet t5 = new Treelet(subtree2, c1);

		Treelet t6 = new Treelet();
		t6=t6.mergeTreelets(t5,t2);
		/*if(!t6.isEmpty()){
			System.out.println("l'unione ha avuto effetto");
		}else System.out.println("l'albero è vuoto");*/
		//for(Integer x : t4.subtree)
		//System.out.println(x);

		//System.out.println(t6.root);
		//System.out.println(t6.beta);

		BuildClass build = new BuildClass();

		build.optGraph(g2, k);

		for (int v=0; v< g2.adj.size(); v++){

			System.out.println(build.occVector.get(v));
		}


	}
}

