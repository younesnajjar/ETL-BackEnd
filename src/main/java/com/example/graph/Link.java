package com.example.graph;

public class Link {

	private Integer key;
	private String column;
	
	
	public Link(Integer key, String column) {
		super();
		this.key = key;
		this.column = column;
	}

	public Link() {
		
	}
	
	public Integer getKey() {
		return key;
	}
	
	public void setKey(Integer key) {
		this.key = key;
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	
	
}
