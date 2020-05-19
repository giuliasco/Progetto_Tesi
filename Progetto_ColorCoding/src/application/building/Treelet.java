package application.building;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Treelet {
	
	public ColorNode root;
	public int size;
	public ArrayList<Treelet> subtree ;

	public Treelet() {
		root=new ColorNode();
		size=0;
		this.subtree= new ArrayList<Treelet>();
	}
	
	public Treelet(ColorNode root) {
		this.root= root;
		size=1;
		this.subtree= new ArrayList<Treelet>();
	}
	  
	public Treelet(ArrayList<Treelet> subtrees, ColorNode root) {
		this.root= root;
		this.size =1;
		this.subtree= new ArrayList<Treelet>();
		if (!subtrees.isEmpty()) {
			for (Treelet x : subtrees) {
				this.root.addChild(x.root);
				subtree.add(x);
				size=size+x.size;
			}
		}
		
	}
	
	public int size() {
		return size;
	}
	
	public int getRoot() {
		return root.getData();
	}
	
	public boolean isEmpty() {
		return this.root==null;
	}
	
	public Iterator<ColorNode> iteratorLevelOrder(){
		ArrayList<ColorNode> templist= new ArrayList<ColorNode>();
		levelorder(this.root, templist);
		return templist.iterator();
	}
	
	public void levelorder(ColorNode node, ArrayList<ColorNode> templist) {
		Queue<ColorNode> queueOfnodes = new LinkedList<ColorNode>();
		ColorNode current;
		queueOfnodes.add(node);
		while(! queueOfnodes.isEmpty()) {
			current= queueOfnodes.remove();
			templist.add(current);
			if(current.hasChild()) {
				for (int i=0; i<current.child.size();i++) {
					queueOfnodes.add(current.child.get(i));
				}
			}
		}
		
	}


}
