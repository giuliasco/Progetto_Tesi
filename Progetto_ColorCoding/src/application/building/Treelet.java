package application.building;

import java.util.*;




public class Treelet implements Comparable<Treelet>{
	
	public ColorNode root;
	public int size ,num, beta;
	public ArrayList<Integer> subtree = new ArrayList<Integer>();

	//Cosruttore vuoto
	public Treelet() {
		root=new ColorNode();
		size=0;
		num=0;

	}

	//Costruttore per un albero composto da un unico nodo
	public Treelet(ColorNode root) {
		this.root= root;
		this.size=1;
		this.num=0;

	}

	//costruttore di un albero composto da più nodi
	public Treelet (ArrayList<Treelet> treelet , ColorNode root ){
		this.root = root;
		this.size=1;

		Collections.sort(treelet);
		for (Treelet t : treelet){
			this.subtree.add(t.num);
			this.root.addChild(t.root);
			this.size += t.size;
		}
		ArrayList<Integer> binaryNum= new ArrayList<Integer>(16);
		if(this.root.hasChild()) {
			binaryNum = Visit(this.root, binaryNum);
		}

		while ( binaryNum.size() <16){
			binaryNum.add(0);
		}

		String binary = new String() ;
		for (int i = 0 ; i<16 ; i++) {
			String s = String.valueOf(binaryNum.get(i));
				binary += s;
			}

			this.num = Integer.parseInt(binary, 2);
	}

	//metodo ricorsivo utilizzato per la visita dell'albero che mi permette di generare il numero che caratterizza ogni albero
	public ArrayList<Integer> Visit(ColorNode x, ArrayList<Integer> array) {
		if (x.hasChild()) {
			for (ColorNode c : x.child) {
				array.add(1);
				Visit(c, array);
			}
		}
		array.add(0);
		return array;
	}


	
	public boolean isEmpty() {
		return this.root==null;
	}
	



	@Override
	public int compareTo(Treelet treelet) {
		String num1 = String.valueOf(this.num);
		String num2 = String.valueOf(treelet.num);
		return num1.compareTo(num2);
	}
}

		/*
		Iteratore che posso usare per scorrere la lista dei treelet arrivare all'ultimo e fare nuna qualche operazione

		Iterator<Treelet> itr = treelet.iterator() ;


		while(itr.hasNext()){


			Treelet current = itr.next();
			this.subtree.add(current.num);
			this.root.addChild(current.root);
			this.size += current.size;

			if(!itr.hasNext()) this.beta=current.beta;
		}*/