package application.building;

import java.util.ArrayList;

public class ColorNode {
	public int data;
	public ArrayList<ColorNode> child;
	public int color;
	
	public ColorNode() {
		this.child=new ArrayList<ColorNode>();
		this.data=0;
		this.color=0;
	}
	
	public ColorNode(int data,int color) {
		this.data=data;
		this.child=new ArrayList<ColorNode>();
		this.color=color;
	}
	
	public ColorNode(int data, ArrayList<ColorNode> child, int color) {
		this.data=data;
		this.child=child;
		this.color=color;
	}
	
	public void setData(int data) {
		this.data=data;
	}
	
	public int getData() {
		return this.data;
	}
	
	public void setChild(ArrayList<ColorNode> child) {
		this.child=child;
	}

	
	public ArrayList<ColorNode> getChild(){
		return this.child;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public void addChild(ColorNode c1) {
		this.child.add(c1);
	}
	
	public boolean hasChild() {
		return this.child!=null;
	}
	
	
		
	
	
}
