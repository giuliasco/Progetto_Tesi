package application.building;

import java.util.ArrayList;

public class ColorNode {
	protected int data;
	protected ArrayList<Integer> child;
	protected int color;
	
	public ColorNode(int data,int color) {
		this.data=data;
		this.child=null;
		this.color=color;
	}
	
	public ColorNode(int data, ArrayList<Integer> child, int color) {
		this.data=data;
		this.child=child;
		this.color=color;
	}
	
	public void setData(int data) {
		
	}
	
	
}
