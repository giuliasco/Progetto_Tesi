package application.building;

import java.util.*;




public class Treelet{

	public ColorNode root;
	public int size ,num, beta;
	public LinkedList<Integer> subtree = new LinkedList<Integer>();
	public ArrayList<Integer> color= new ArrayList<Integer>();
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



	//metodo ricorsivo utilizzato per la visita dell'albero che mi permette di generare il numero che caratterizza ogni albero
	public LinkedList<Integer> Visit(ColorNode x, LinkedList<Integer> list) {
		if (x.hasChild()) {
			for (ColorNode c : x.child) {
				list.addLast(1);
				Visit(c, list);
			}

		}
		list.addLast(0);
		return list;
	}

	//metodo per ottenere la stringa binaria chepoi caratterizza l'albero nella forma
	public String binaryVisit (Treelet t){
		LinkedList<Integer> binaryNum= new LinkedList<Integer>();
		if(t.root.hasChild()) {
			binaryNum = Visit(t.root, binaryNum);
		}



		String binary = new String() ;
		for (int i = 0 ; i< binaryNum.size()-1 ; i++) {
			String s = String.valueOf(binaryNum.get(i));
			binary += s;
		}
		return binary;
	}


	//metodo che mi restituisce l'insieme dei colori che caratterizza l'albero
	public ArrayList<Integer> colorSet(ColorNode x, ArrayList<Integer> color){
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
					ColorNode mergeRoot = new ColorNode(t1.root.data,t1.root.color);
					merge.root = mergeRoot;
					if(!t1.root.child.isEmpty()) {
						for (int j = 0; j<t1.root.child.size() ; j++)
						merge.root.child.add(t1.root.child.get(j));
					}
					merge.root.addChild(t2.root);
					int size1 = t1.size;
					int size2 = t2.size;
					merge.size = size1 + size2;
					merge.subtree = new LinkedList<Integer>();
					if (!t1.subtree.isEmpty()) {
					for(int i = 0 ; i<t1.subtree.size() ; i++) merge.subtree.addLast(t1.subtree.get(i));}
					merge.subtree.addLast(t2.num);
					merge.color=colorSet(merge.root, merge.color);
					String binary = binaryVisit(merge);
					merge.num = Integer.parseInt(binary, 2);
					int beta = t1.beta;
					if (!t1.subtree.isEmpty() && t1.subtree.getLast() == t2.num) merge.beta = beta + 1;
					else merge.beta=beta;
		return merge;
	}

	@Override
	public String toString() {
		String s = String.valueOf(hashCode());
		return s;
	}

	@Override
	public boolean equals(Object o) {
		if (o==null) return false;
		if(!(o instanceof Treelet)) return false;
		Treelet t= (Treelet) o;
		return this.toString().equals(t.toString());
	}

	@Override
	public int hashCode() {
		return this.num + this.color.hashCode();
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