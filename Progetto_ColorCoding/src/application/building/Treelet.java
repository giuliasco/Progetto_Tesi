package application.building;

import java.util.*;




public class Treelet implements Comparable<Treelet>{
	
	public ColorNode root;
	public int size ,num, beta;
	public LinkedList<Integer> subtree = new LinkedList<Integer>();
	public HashSet<Integer> color= new HashSet<Integer>();
	//Cosruttore vuoto
	public Treelet() { }

	//Costruttore per un albero composto da un unico nodo
	public Treelet(ColorNode root) {
		this.root= root;
		this.size=1;
		this.num=0;
		this.beta=1;
		this.color=colorSet(root, color);
	}

	//costruttore di un albero composto da più nodi
	public Treelet (ArrayList<Treelet> treelet , ColorNode root ){
		this.root = root;
		this.size=1;
		this.beta=1;
		Collections.sort(treelet);
		for (Treelet t : treelet){
			this.subtree.addLast(t.num);
			this.root.addChild(t.root);
			this.size += t.size;
		}
			String binary = binaryVisit(this);
			this.num = Integer.parseInt(binary, 2);
			this.color=colorSet(root, color);
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

	//metodo per ottenere la stringa binaria chepoi caratterizza l'albero nella forma
	public String binaryVisit (Treelet t){
		ArrayList<Integer> binaryNum= new ArrayList<Integer>(16);
		if(t.root.hasChild()) {
			binaryNum = Visit(t.root, binaryNum);
		}

		while ( binaryNum.size() <16){
			binaryNum.add(0);
		}

		String binary = new String() ;
		for (int i = 0 ; i<16 ; i++) {
			String s = String.valueOf(binaryNum.get(i));
			binary += s;
		}
		return binary;
	}

	//metodo per determinare se l'albero ha elementi
	public boolean isEmpty() {
		return this.root==null;
	}

	//metodo che mi restituisce l'insieme dei colori che caratterizza l'albero
	public HashSet<Integer> colorSet(ColorNode x, HashSet<Integer> color){
		color.add(x.color);
		if(x.hasChild())
		{
			for (ColorNode y : x.child)
				colorSet(y,color);
		}
		return color;
	}

	//metodo che mi permette di unire due alberi secondo le giuste proprietà
	public Treelet mergeTreelets(Treelet t1, Treelet t2){

		Treelet merge = new Treelet();
		HashSet<Integer> interColor = new HashSet<Integer>(t1.color);
		interColor.retainAll(t2.color);
		if( interColor.isEmpty() ) {
			if (!t1.subtree.isEmpty()) {
				if (t2.num >= t1.subtree.getLast()) {
					ColorNode mergeRoot = new ColorNode(t1.root.data,t1.root.color);
					merge.root = mergeRoot;
					merge.root.addChild(t2.root);
					merge.subtree = new LinkedList<Integer>();
					for(int i = 0 ; i<t1.subtree.size() ; i++) merge.subtree.addLast(t1.subtree.get(i));
					merge.subtree.addLast(t2.num);
					merge.size = t1.size + t2.size;
					merge.color=colorSet(merge.root, merge.color);
					String binary = binaryVisit(merge);
					merge.num = Integer.parseInt(binary, 2);
					if (t1.subtree.getLast() == t2.num) merge.beta = t1.beta + 1;
					else merge.beta=t1.beta;
				}
			} else {
					ColorNode mergeRoot = new ColorNode(t1.root.data,t1.root.color);
					merge.root = mergeRoot;
					merge.root.addChild(t2.root);
					merge.subtree = new LinkedList<Integer>();
					merge.subtree.addLast(t2.num);
					merge.size = t1.size + t2.size;
					merge.color=colorSet(merge.root, merge.color);
					String binary = binaryVisit(merge);
					merge.num = Integer.parseInt(binary, 2);
					merge.beta = t1.beta;
				}

			}

		return merge;
	}

	//metodo per confrontare due alberi in base ai numeri
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