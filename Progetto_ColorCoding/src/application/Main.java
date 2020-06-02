package application;
import java.util.*;

//import java.util.ArrayList;
import application.building.*;
import sun.reflect.generics.tree.Tree;

public class Main {

	public static void main(String[] args) {
		/*int k = 7;
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(6);
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		ArrayList<Integer> a7 = new ArrayList<Integer>();
		ArrayList<Integer> a8 = new ArrayList<Integer>();
		a0.add(1);
		a0.add(2);
		a0.add(3);
		a0.add(4);
		a0.add(5);
		a0.add(6);
		a0.add(7);
		a0.add(8);
		a1.add(0);
		a1.add(2);
		a1.add(3);
		a1.add(4);
		a1.add(5);
		a1.add(6);
		a1.add(7);
		a1.add(8);
		a2.add(1);
		a2.add(0);
		a2.add(3);
		a2.add(4);
		a2.add(5);
		a2.add(6);
		a2.add(7);
		a2.add(8);
		a3.add(1);
		a3.add(2);
		a3.add(0);
		a3.add(4);
		a3.add(5);
		a3.add(6);
		a3.add(7);
		a3.add(8);
		a4.add(1);
		a4.add(2);
		a4.add(3);
		a4.add(0);
		a4.add(5);
		a4.add(6);
		a4.add(7);
		a4.add(8);
		a5.add(1);
		a5.add(2);
		a5.add(3);
		a5.add(4);
		a5.add(0);
		a5.add(6);
		a5.add(7);
		a5.add(8);
		a6.add(1);
		a6.add(2);
		a6.add(3);
		a6.add(4);
		a6.add(5);
		a6.add(0);
		a6.add(7);
		a6.add(8);
		a7.add(1);
		a7.add(2);
		a7.add(3);
		a7.add(4);
		a7.add(5);
		a7.add(6);
		a7.add(0);
		a7.add(8);
		a8.add(1);
		a8.add(2);
		a8.add(3);
		a8.add(4);
		a8.add(5);
		a8.add(6);
		a8.add(7);
		a8.add(0);
		adj.add(a0);
		adj.add(a1);
		adj.add(a2);
		adj.add(a3);
		adj.add(a4);
		adj.add(a5);
		adj.add(a6);
		adj.add(a7);
		adj.add(a8);
		Graph g2 = new Graph(adj);*/



		ColorNode c1= new ColorNode(1, 1);
		ColorNode c2= new ColorNode(2, 2);
		ColorNode c3= new ColorNode(3, 3);
		ColorNode c4= new ColorNode(3, 4);
		ColorNode c5= new ColorNode(3, 5);
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
		System.out.println(t5.num);
		/*System.out.println("L'hash code di t1 è  "+t1.hashCode());
		System.out.println("L'hash code di t2 è  "+t2.hashCode());
		System.out.println("L'hash code di t3 è  "+t3.hashCode());
		System.out.println(t5.hashCode());*/
		/*System.out.println("L'hash code di t5 è  "+t6.hashCode());
		System.out.println("Colori code di t5 è  "+t5.color.hashCode());
		System.out.println("L'hash code di t6 è  "+t6.hashCode());
		System.out.println("Colori di t6 è  "+t6.color.hashCode());*/
		/*if(!t6.isEmpty()){
			System.out.println("l'unione ha avuto effetto");
			System.out.println(t1.num);
			System.out.println(t6.num);
		}else System.out.println("l'albero è vuoto");*/
		//for(Integer x : t4.subtree)
		//System.out.println(x);

		//System.out.println(t6.root);
		//System.out.println(t6.beta);*/


		//g2.printAdjacencyList();
		//LinkedList<HashMap<HashMap<Integer,HashSet<Integer>>, Integer>> prova = g2.optGraph(k);
		/*Building b=new Building();
		b.optGraph(g2,k);

		for(int i=0 ; i<g2.V ; i++) {
			System.out.println("La tabella per il nodo   " + i + "  è   " + b.table.get(i));

		}*/



	}

	}


