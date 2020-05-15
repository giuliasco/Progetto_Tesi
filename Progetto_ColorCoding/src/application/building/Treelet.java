package application.building;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

 
public class Treelet {
	
	
	  
	// Represents a node of an n-ary tree 
	static class Node 
	{ 
	    int key; 
	    Vector<Node >child = new Vector<>(); 
	}; 
	  
	// Utility function to create a new tree node 
	static Node newNode(int key) 
	{ 
	    Node temp = new Node(); 
	    temp.key = key; 
	    return temp; 
	} 
	  
	// Prints the n-ary tree level wise 
	static void LevelOrderTraversal(Node root) 
	{ 
	    if (root == null) 
	        return; 
	  
	    // Standard level order traversal code 
	    // using queue 
	    Queue<Node > q = new LinkedList<>(); // Create a queue 
	    q.add(root); // Enqueue root  
	    while (!q.isEmpty()) 
	    { 
	        int n = q.size(); 
	  
	        // If this node has children 
	        while (n > 0) 
	        { 
	            // Dequeue an item from queue 
	            // and print it 
	            Node p = q.peek(); 
	            q.remove(); 
	            System.out.print(p.key + " "); 
	  
	            // Enqueue all children of  
	            // the dequeued item 
	            for (int i = 0; i < p.child.size(); i++) 
	                q.add(p.child.get(i)); 
	            n--; 
	        } 
	          
	        // Print new line between two levels 
	        System.out.println();  
	    } 
	} 
}
