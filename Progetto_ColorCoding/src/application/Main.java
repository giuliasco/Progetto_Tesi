package application;
import java.util.ArrayList;
import application.building.*;

public class Main {


	//import java.util.ArrayList;


		  public static void main(String[] args) 
		    { 
		        // Creating a graph with 5 vertices 
		        int V = 5; 
		       
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
			  adj.add(a6);
			  
			 
			  
		    }  
			}

