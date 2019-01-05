package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.graph.Edge;
import com.example.graph.Graph;
import com.example.graph.NodeDataArray;
import com.example.metier.DataSetsOperations;


@Configuration
public class Config {

	@Bean
	public DataSetsOperations foundFiles() {
		return new DataSetsOperations();
	}
	
	@Bean
	@Scope("singleton")
	public Graph graph() {
		return new Graph();
	}
	
	@Bean
	public SparkSession sparksession() {
		return SparkSession
				  .builder()
				  .master("local[*]")
				  .appName("ETL")
				  .getOrCreate();
	}
	
	@Bean
	public List<NodeDataArray> nodeDataArray(){
		return new ArrayList<NodeDataArray>();
	}
	
	@Bean
	public List<Edge> linkDataArray(){
		return new ArrayList<>();
	}
	
	@Bean 
	public Map<Integer, NodeDataArray> map(){
		return new HashMap<Integer, NodeDataArray>(); 
	}
	
	@Bean
	public Map<Integer, List<Integer>> neighbors(){
		return new HashMap<>();
	}
	
	
	
}
