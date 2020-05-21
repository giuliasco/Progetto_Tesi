package application.building;

import java.util.ArrayList;

public class ColorNode {
	public int data;
	public ArrayList<ColorNode> child;
	public int color;

	//costruttore vuoto
	public ColorNode() {
		this.child=new ArrayList<ColorNode>();
		this.data=0;
		this.color=0;
	}

	//costruttore a cui passo solo il dato e il colore
	public ColorNode(int data,int color) {
		this.data=data;
		this.child=new ArrayList<ColorNode>();
		this.color=color;
	}

	//costruttore a cui passo tutto
	public ColorNode(int data, ArrayList<ColorNode> child, int color) {
		this.data=data;
		this.child=child;
		this.color=color;
	}

	//metodo per aggiungere un figlio alla lista dei figli del nodo
	public void addChild(ColorNode c1) {
		this.child.add(c1);
	}

	//metodo che mi permette di vedere se il grafo figli
	public boolean hasChild() {
		return this.child!=null;
	}
	
	
		
	
	
}
