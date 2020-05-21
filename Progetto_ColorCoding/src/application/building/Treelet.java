package application.building;

import java.util.*;

public class Treelet implements Comparable<Treelet>{
	
	public ColorNode root;
	public int size;
	public int num;
	ArrayList<Integer> subtree = new ArrayList<Integer>();

	public Treelet() {
		root=new ColorNode();
		size=0;
		num=0;

	}

	public Treelet(ColorNode root) {
		this.root= root;
		size=1;
		num=0;

	}

	public Treelet (ArrayList<Treelet> treelet , ColorNode root ){
		this.root = root;
		this.size=1;
		Collections.sort(treelet);
		for (Treelet t : treelet){
			subtree.add(t.num);
			this.size += t.size;
		}
		if (!this.root.hasChild()) num=0;
		else{
			int binaryNum[] = new int[16];
			Visit(this.root, binaryNum);
		}
	}

	public int[] Visit(ColorNode x, int array[]){

		return array;
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



	@Override
	public int compareTo(Treelet treelet) {
		String num1 = String.valueOf(this.num);
		String num2 = String.valueOf(treelet.num);
		return num1.compareTo(num2);
	}
}

/*  per convertire da numero binario a numero decimale
public void given_binaryNumber_then_ConvertToDecimalNumber() {
    assertEquals(8, Integer.parseInt("1000", 2));
    assertEquals(20, Integer.parseInt("10100", 2));
}
 */