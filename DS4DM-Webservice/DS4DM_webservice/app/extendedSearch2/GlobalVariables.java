package extendedSearch2;


import java.io.File;
import java.io.FileNotFoundException;
import  java.util.Scanner;

import com.google.gson.Gson;

import model.QueryTable;

public class GlobalVariables {
	
	//========  PARAMETERS  =====================================================
	private String repositoryName;
	
	private String columnNameIndexPath;
	
	private model.QueryTable queryTable;
	
	private Boolean logFoundTables;
	
	private String logFolderPath;
	
	

	
	
	//========  METHODS  ========================================================

	// constructor ---------------------------------
	public GlobalVariables(String globalVariablesFilePath) {
		
		String globalVariablesString = "{}";
		try {
			globalVariablesString = new Scanner(new File(globalVariablesFilePath)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		
		Gson gson = new Gson();	
		GlobalVariables temporaryObject = gson.fromJson(globalVariablesString, GlobalVariables.class);
		
		

		this.queryTable = temporaryObject.getQueryTable();
		this.logFoundTables = temporaryObject.getLogFoundTables();
		this.logFolderPath = temporaryObject.getLogFolderPath();
		
		
		final String theRepositoryName =  temporaryObject.getRepositoryName();
		
		if (temporaryObject.getRepositoryName()!= null) this.repositoryName = temporaryObject.getRepositoryName();
		else  this.repositoryName = "DefaultRepository";
		
		if (temporaryObject.getColumnNameIndexPath() != null) this.columnNameIndexPath = temporaryObject.getColumnNameIndexPath();
		else this.columnNameIndexPath = "public/repositories/" + temporaryObject.getRepositoryName() + "/indexes/ColumnNameIndex";

	}



	
	
	// repositoryName -------------------------------
	public String getRepositoryName(){
		return this.repositoryName;
	}
	
	public void setRepositoryName(String repositoryName){
		this.repositoryName = repositoryName;
		
		if (this.columnNameIndexPath.equals("public/repositories/DefaultRepository/indexes/ColumnNameIndex"))
			this.columnNameIndexPath = "public/repositories/" + repositoryName + "/indexes/ColumnNameIndex";
	}
	
	

	// columnNameIndexPath -------------------------------
	public String getColumnNameIndexPath(){
		return this.columnNameIndexPath;
	}
	
	public void setColumnNameIndexPath(String columnNameIndexPath){
		this.columnNameIndexPath = columnNameIndexPath;
	}

	
	// queryTable -------------------------------
	public QueryTable getQueryTable(){
		return this.queryTable;
	}
	
	public void setQueryTable(QueryTable queryTable) {
		this.queryTable = queryTable;
	}
	
	
	// logFoundTables -------------------------------
	public Boolean getLogFoundTables(){
		return this.logFoundTables;
	}
	
	public void setLogFoundTables(Boolean logFoundTables) {
		this.logFoundTables = logFoundTables;
	}

	// logFolderPath -------------------------------
	public String getLogFolderPath(){
		if (this.logFolderPath == null) this.logFoundTables = false;
		return this.logFolderPath;
	}
	
	public void setLogFolderPath(String logFolderPath){
		this.logFolderPath = logFolderPath;
	}
	
	

}
