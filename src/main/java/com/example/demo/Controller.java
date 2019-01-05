package com.example.demo;



import java.util.List;

import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.metier.DataSetsOperations;


import com.example.graph.Graph;
import com.example.metier.DataSet;

@RestController
@Component
public class Controller {
	
	@Autowired
	private DataSetsOperations dataSetsOperations;
	@Autowired
	private Graph graph;
	
	
	@GetMapping("/datasets")
	public List<DataSet> test(){
		return dataSetsOperations.getDataSets();
	}
	
	
	@PostMapping("/run")
	public void run(@RequestBody Graph graph) {
		this.graph = graph;
		this.graph.init();
		this.graph.process();
	}
	
	@GetMapping("/data/{key}")
	public List<String> getData(@PathVariable("key") int key) {
		List<String> a =  graph.getMap().get(key).getDataSet().toJSON().collectAsList();
		return a.subList(0, Math.min(30,a.size()));
		
	}	
	
	
	
	
	
}
