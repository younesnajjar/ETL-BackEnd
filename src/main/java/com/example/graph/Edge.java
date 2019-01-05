package com.example.graph;

public class Edge {

	private Integer from;
	private Integer to;
	
	public Edge(Integer from, Integer to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public Edge() {
		
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}
	
	@Override
	public String toString() {
		String reString = "from " + from +" to " + to;
		return reString;
	}
	
	
	
}
