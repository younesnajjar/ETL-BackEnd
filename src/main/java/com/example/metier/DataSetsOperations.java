package com.example.metier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unchecked")
public class DataSetsOperations {
	
	@Autowired
	private SparkSession sparkSession;
	
	public static final String repository = "C:\\Users\\younes\\eclipse-workspace\\demo\\repository";
	
	public DataSetsOperations() {
		
	}
	
	public List<DataSet> getDataSets(){
		List<DataSet> response = new ArrayList<>();
		
		String[] extensions = new String[] {"csv","xlsx"};
		File directory = new File(repository);
		List<File> files = (List<File>) FileUtils.listFiles(directory, extensions, true);
		
		for(File file : files) {
			String name = file.getName();
			String type = FilenameUtils.getExtension(name);
			
			Dataset<Row> df = sparkSession.read()
					.option("header", "true")
					.option("sep", ";")
					.csv(file.getAbsolutePath());
			
			String[] columns = df.columns();
			ColumnState[] columnsState = new ColumnState[columns.length];
			for(int i = 0; i < columns.length; i++) {
				columnsState[i] = new ColumnState(columns[i]);
			}
			
			DataSet element = new DataSet(name, type, columnsState);
			response.add(element);
		}

		return response;
	}
}
