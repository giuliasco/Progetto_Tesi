package application.building;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Treelet {
	
	public ColorNode root;
	public int size;
	
	public Treelet() {
		root=null;
		size=0;
	}
	
	public Treelet(ColorNode root) {
		this.root= root;
		size=1;
	}
	  
	public Treelet(ArrayList<Treelet> subtree, ColorNode root) {
		this.root= root;
		size =1;
		
		if (!subtree.isEmpty()) {
			for (Treelet x : subtree) {
				this.root.addChild(x.root);
				size=size+x.size;
			}
		}
		
	}
	
	public int size() {
		return size;
	}
	
	public ColorNode getRoot() {
		return root;
	}
	
	public boolean isEmpty() {
		return this.root==null;
	}
	
	public Iterator<Integer> iteratorLevelOrder(){
		ArrayList<Integer> templist= new ArrayList<Integer>();
		levelorder(this.root, templist);
		return templist.iterator();
	}
	
	public void levelorder(ColorNode node, List<Integer> templist) {
		Queue<ColorNode> queueOfnodes = new LinkedList<ColorNode>();
		ColorNode current;
		queueOfnodes.add(node);
		while(! queueOfnodes.isEmpty()) {
			current= queueOfnodes.remove();
			templist.add(current.data);
			if(current.hasChild()) {
				for (int i=0; i<current.child.size();i++) {
					queueOfnodes.add(current.child.get(i));
				}
			}
		}
		
	}
	/*// Represents a node of an n-ary tree 
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
	} */
}
