package com.example.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.metier.ColumnState;
import com.example.metier.DataSetsOperations;


@Component
public class Graph{

	private List<NodeDataArray> nodeDataArray;
	private List<Edge> linkDataArray;
	private Map<Integer, NodeDataArray> map;
	private Map<Integer, List<Integer>> neighbors;
	@Autowired
	private SparkSession sparksession = SparkSession
			  .builder()
			  .master("local[*]")
			  .appName("ETL")
			  .getOrCreate();

	public Graph(List<NodeDataArray> nodeDataArray, List<Edge> linkDataArray) {
		super();
		this.nodeDataArray = nodeDataArray;
		this.linkDataArray = linkDataArray;
	}
	
	public Graph() {
	
	}
	
	
	public List<NodeDataArray> getNodeDataArray() {
		return nodeDataArray;
	}

	@Autowired
	public void setNodeDataArray(List<NodeDataArray> nodeDataArray) {
		this.nodeDataArray = nodeDataArray;
	}

	public List<Edge> getLinkDataArray() {
		return linkDataArray;
	}

	@Autowired
	public void setLinkDataArray(List<Edge> linkDataArray) {
		this.linkDataArray = linkDataArray;
	}

	public Map<Integer, NodeDataArray> getMap() {
		return map;
	}

	@Autowired
	public void setMap(Map<Integer, NodeDataArray> map) {
		this.map = map;
	}

	public Map<Integer, List<Integer>> getNeighbors() {
		return neighbors;
	}

	@Autowired
	public void setNeighbors(Map<Integer, List<Integer>> neighbors) {
		this.neighbors = neighbors;
	}

	
	public void init() {
		this.map = new HashMap<>();
		this.neighbors = new HashMap();
		
		for(NodeDataArray node : nodeDataArray) {
			map.put(node.getKey(), node);
		}
		
		for(Edge edge : linkDataArray) {
			
			List<Integer> nodes2 = neighbors.getOrDefault(edge.getFrom(), new ArrayList<Integer>());
			nodes2.add(edge.getTo());
			
			neighbors.put(edge.getFrom(), nodes2);
		}
		
	}
	
	
	public void process() {
		initializeFiles();
		
	}
	
	public void initializeFiles() {
		Iterator<Integer> keys = map.keySet().iterator();
		Queue<NodeDataArray> files = new LinkedList<>();
		Map<Integer, Boolean> visited = new HashMap<>();
		
		while(keys.hasNext()) {
			int key = keys.next();
			System.err.println("key " + key);
			NodeDataArray node = map.get(key);
			if(node.getCategory().equals("csvFile")) {
				map.get(key).setDataSet(sparksession
						.read()
						.format("csv")
						.option("header", "true")
						.option("sep", ";")
				        .load(DataSetsOperations.repository + "\\" + node.getName()));
				
				files.add(node);
				
			}else if(node.getCategory().equalsIgnoreCase("SELECT")) {
				StringBuilder query = new StringBuilder();
				StringBuilder columns = new StringBuilder();
				for(ColumnState columnState : node.getColumns()) {
					if(columnState.getState() == true) {
						columns.append(columnState.getName() + ",");
					}
				}
				columns.deleteCharAt(columns.length() - 1);
				query.append("SELECT ");
				query.append(columns.toString());
				query.append(" FROM TABLE ");
				query.append(node.getQuery());
				node.setQuery(query.toString());
		
			}
		}
		
		
		while(!files.isEmpty()) {
			NodeDataArray node2 = files.poll();
			
			if(!visited.containsKey(node2.getKey())) {
					
				if(neighbors.get(node2.getKey()) != null) {
					
					for(Integer neighbor : neighbors.get(node2.getKey())) {
						
						if(!visited.containsKey(neighbor)) {
							
							files.add(map.get(neighbor));
							
							if(map.get(neighbor).getCategory().equalsIgnoreCase("SELECT")) {
								node2.getDataSet().createOrReplaceTempView("TABLE");
								
								Dataset<Row> newData = sparksession.sql(map.get(neighbor).getQuery());
								
								map.get(neighbor).setDataSet(newData);
							}else {
								NodeDataArray node = map.get(neighbor);
								node.setDataSet(
										map.get(node.getLeftLink().getKey()).getDataSet().join(
												map.get(node.getRightLink().getKey()).getDataSet()
												,
												map.get(node.getLeftLink().getKey()).getDataSet().col(node.getLeftLink().getColumn()).equalTo(
												map.get(node.getRightLink().getKey()).getDataSet().col(node.getRightLink().getColumn()))
												,"outer"));
							}
						}
					}
				}
			}
		}
		
	}
}
