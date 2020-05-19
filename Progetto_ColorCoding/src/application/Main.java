package application;
import java.util.*;

//import java.util.ArrayList;
import application.building.*;
import sun.reflect.generics.tree.Tree;

public class Main {


	//import java.util.ArrayList;


		  public static void main(String[] args) 
		    { 
			int k=2;
			 ArrayList<ArrayList<Integer>> adj=new ArrayList<ArrayList<Integer>>(6);
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
			  Graph g2= new Graph(adj);
			 g2.printAdjacencyList();


				HashMap<Integer, HashSet<Treelet>> prova = g2.CC2(k);

				for (int i=0; i<g2.getV(); i++){

						System.out.println(prova.get(i).size());

					}


				}
				/*ColorNode c0 = new ColorNode(0, 1);
				ColorNode c1 = new ColorNode(1, 2);
				ColorNode c2 = new ColorNode(2, 3);
				ColorNode c3 = new ColorNode(3, 4);
				ColorNode c4 = new ColorNode(4, 5);
				//ColorNode c5 = new ColorNode(5, 6);
				ArrayList<Treelet> al1 = new ArrayList<Treelet>();
				ArrayList<Treelet> al2 = new ArrayList<Treelet>();
				ArrayList<Treelet> al3 = new ArrayList<Treelet>();
				Treelet t1 = new Treelet(c3);
				Treelet t2= new Treelet(c4);
				al1.add(t1);
				al1.add(t2);
				Treelet t3 = new Treelet(al1, c2);
				al2.add(t3);
				Treelet t4 = new Treelet(al2,c1);
				al3.add(t4);

				Treelet t5 = new Treelet(al3,c0);



				for ( Treelet tree : t5.subtree ) {
					Iterator<ColorNode> it = tree.iteratorLevelOrder();
					ArrayList<ColorNode> prova = new ArrayList<ColorNode>();
					while (it.hasNext()) {
						prova.add(it.next());
					}
					for (ColorNode x : prova) {
						System.out.println(x.data);
					}
				}
				/*for (ColorNode c : t6.root.child){
					System.out.println(c.data);
				}*/

}

