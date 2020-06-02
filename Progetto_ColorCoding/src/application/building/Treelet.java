package application.building;

import java.util.*;




public class Treelet{

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
		ArrayList<Integer> binaryNum= new ArrayList<Integer>(31);
		if(t.root.hasChild()) {
			binaryNum = Visit(t.root, binaryNum);
		}

		while ( binaryNum.size() <31){
			binaryNum.add(0);
		}

		String binary = new String() ;
		for (int i = 0 ; i<31 ; i++) {
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
	public int hashCode() {
		return this.num+this.color.hashCode();
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