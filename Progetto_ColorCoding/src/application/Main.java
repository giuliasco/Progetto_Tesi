package application;
import java.util.ArrayList;

//import java.util.ArrayList;
import application.building.*;

public class Main {


	//import java.util.ArrayList;


		  public static void main(String[] args) 
		    { 
		        // Creating a graph with 5 vertices 
		        /*int V = 5; 
		       
		        Graph g1 = new Graph(V); 
		  
		        // Adding edges one by one. 
		        g1.addEdge(0, 1); 
		        g1.addEdge(0, 4); 
		        g1.addEdge(1, 2); 
		        g1.addEdge(1, 3); 
		        g1.addEdge(1, 4); 
		        g1.addEdge(2, 3); 
		        g1.addEdge(3, 4); 
		        //g1.printAdjacencyList(); 
			  
			 ArrayList<ArrayList<Integer>> adj=new ArrayList<ArrayList<Integer>>(6);
			  ArrayList<Integer> a1 = new ArrayList<Integer>();
			  ArrayList<Integer> a2 = new ArrayList<Integer>();
			  ArrayList<Integer> a3 = new ArrayList<Integer>();
			  ArrayList<Integer> a4 = new ArrayList<Integer>();
			  ArrayList<Integer> a5 = new ArrayList<Integer>();
			  ArrayList<Integer> a6 = new ArrayList<Integer>();
			  a1.add(2);
			  a1.add(6);
			  a2.add(1);
			  a2.add(3);
			  a2.add(6);
			  a3.add(2);
			  a3.add(4);
			  a3.add(5);
			  a4.add(3);
			  a4.add(5);
			  a5.add(3);
			  a5.add(4);
			  a5.add(6);
			  a6.add(1);
			  a6.add(2);
			  a6.add(5);
			  adj.add(a1);
			  adj.add(a2);
			  adj.add(a3);
			  adj.add(a4);
			  adj.add(a5);
			  adj.add(a6);*/
			  
			 ColorNode c1= new ColorNode(1, 1);
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

			 System.out.println(t5.getRoot().getData());
			 System.out.print(t5.size);
			 		
			/*System.out.println(t1.size);
			for (Treelet x : subtree1) {
				ColorNode root1 = x.getRoot();
				System.out.println(root1.getData());
			 
		    } */ 
		    }
}

