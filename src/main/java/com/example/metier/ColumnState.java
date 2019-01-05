package com.example.metier;

public class ColumnState {
	
	private String name;
	private boolean state;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public ColumnState(String name) {
		super();
		this.name = name;
		state = false; 
	}
	
	public ColumnState() {
		
	}
	
	
}
