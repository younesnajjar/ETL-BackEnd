package com.example.graph;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.example.metier.ColumnState;

public class NodeDataArray {

	private String name;
	private String type;
	private String category;
	private Integer key;
	private ColumnState[] columns;
	private String query;
	private Dataset<Row> dataSet;
	private Link leftLink;
	private Link rightLink;
	
	
	public Dataset<Row> getDataSet() {
		return dataSet;
	}

	public void setDataSet(Dataset<Row> dataSet) {
		this.dataSet = dataSet;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public NodeDataArray(String name, String type, String category, Integer key, ColumnState[] columns, String query) {
		super();
		this.name = name;
		this.type = type;
		this.category = category;
		this.key = key;
		this.columns = columns;
		this.query = query;
	
	}
	
	public NodeDataArray() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Integer getKey() {
		return key;
	}
	
	public void setKey(Integer key) {
		this.key = key;
	}
	
	public ColumnState[] getColumns() {
		return columns;
	}
	
	public void setColumns(ColumnState[] columns) {
		this.columns = columns;
	}

	public Link getLeftLink() {
		return leftLink;
	}

	public void setLeftLink(Link leftLinks) {
		this.leftLink = leftLinks;
	}

	public Link getRightLink() {
		return rightLink;
	}

	public void setRightLink(Link rightLinks) {
		this.rightLink = rightLinks;
	}
	
	
	
}
