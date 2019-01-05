package com.example.metier;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class DataSet {
	
	private String name;
	private String type;
	private ColumnState[] columns;
	
	
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
	
	public ColumnState[] getColumns() {
		return columns;
	}
	
	public void setColumns(ColumnState[] columns) {
		this.columns = columns;
	}
	
	public DataSet(String name, String type, ColumnState[] columns) {
		super();
		this.name = name;
		this.type = type;
		this.columns = columns;
	}
	
	
	
	
	
}
