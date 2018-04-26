package extendedSearch2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.rapidminer.extension.json.Correspondence;
import com.rapidminer.extension.json.JSONRelatedTablesResponse;
import com.rapidminer.extension.json.JSONTableResponse;
import com.rapidminer.extension.json.MetaDataTable;
import com.rapidminer.extension.json.TableInformation;

import au.com.bytecode.opencsv.CSVWriter;
import de.mannheim.uni.ds4dm.utils.ReadWriteGson;
import de.mannheim.uni.types.ColumnTypeGuesser.ColumnDataType;
import de.mannheim.uni.utils.TableColumnTypeGuesser;
import extendedSearch.DS4DMBasicMatcherX;
import extendedSearch.GetCorrespondingTablesX;
import extendedSearch.GetTableInformationX;
import extendedSearch.MakeMatcherX;
import extendedSearch.SearchForTablesX;
import model.ExtendedTableInformation;
import model.QueryTable;
import extendedSearch.GetTableInformationX.DataSource;
import javafx.util.Pair;
import play.mvc.Controller;
import play.mvc.Result;

public class ExtenededSearch extends Controller {

	
	public Result extendedSearch(String repositoryName) {
		System.out.println("extendedSearch");
		
		GlobalVariables globalVariables = new GlobalVariables("public/globalVariables.conf");
		
		Gson gson = new Gson();	
		System.out.println(request().body().asJson().toString());
		model.QueryTable queryTable = gson.fromJson(request().body().asJson().toString(), model.QueryTable.class);
		globalVariables.setQueryTable(queryTable);
		globalVariables.setRepositoryName(repositoryName);
	
		Map<String, ExtendedTableInformation> directlyFoundTables = new HashMap<String, ExtendedTableInformation>();	
		Map<String, ExtendedTableInformation> indirectlyFoundTables = new HashMap<String, ExtendedTableInformation>();

		
		
		// GET THE DIRECT MATCHES  ----------------------------
		try {
			directlyFoundTables = LuceneQueries.searchForTables_InLucene(globalVariables);
		} catch (IOException e) { e.printStackTrace();}
		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
		for (ExtendedTableInformation directlyFoundTable: directlyFoundTables.values()){System.out.println("directlyFound1: " + directlyFoundTable.getTableName());	}
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		
		if (globalVariables.getLogFoundTables())  LogFoundTables(directlyFoundTables, "found.csv", globalVariables);
		
		System.out.println("Number of initally found tables:" + String.valueOf(directlyFoundTables.values().size()));
		
		try {	
			directlyFoundTables = LuceneQueries.addTheTableInformation_FromLucene(directlyFoundTables, globalVariables);
		} catch (IOException e) { e.printStackTrace();}
		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
		for (ExtendedTableInformation directlyFoundTable: directlyFoundTables.values()){System.out.println("directlyFound2: " + directlyFoundTable.getTableName());	}
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		
		if (globalVariables.getLogFoundTables())  LogFoundTables(directlyFoundTables, "found_with_instance_matches.csv", globalVariables);
		if (directlyFoundTables == null) System.out.println("directlyFoundTables == null");
		System.out.println("Number of initally found tables with instance matches:" + String.valueOf(directlyFoundTables.values().size()));
		
//		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
//		for (ExtendedTableInformation directlyFoundTable: directlyFoundTables.values()){
//			for (String foundTableColumn: directlyFoundTable.getTableSchema2TargetSchema().keySet()){
//				System.out.println("TableSchema2TargetSchema1 " + directlyFoundTable.getTableName() + " - FT: " + foundTableColumn + " <--> QT: " + directlyFoundTable.getTableSchema2TargetSchema().get(foundTableColumn).getMatching());
//			}
//		}
//		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		
		
		
		// GET THE INDIRECT MATCHES  ----------------------------
		Pair<Map<String, ExtendedTableInformation>, Map<String, ExtendedTableInformation>> foundTables = null; 
		try {	
			foundTables =  CorrespondenceManager.findIndirectMatches(directlyFoundTables, globalVariables);
		} catch (IOException e) { e.printStackTrace();}
		directlyFoundTables = foundTables.getKey();
		indirectlyFoundTables = foundTables.getValue();
		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
		for (ExtendedTableInformation indirectlyFoundTable: indirectlyFoundTables.values()){System.out.println("indirectlyFound1: " + indirectlyFoundTable.getTableName());	}
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		if (globalVariables.getLogFoundTables())  LogFoundTables(indirectlyFoundTables, "extensions.csv", globalVariables);		

		try {
			indirectlyFoundTables = LuceneQueries.addTheTableInformation_FromLucene(indirectlyFoundTables, globalVariables);
		} catch (IOException e) { e.printStackTrace();}
		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
		for (ExtendedTableInformation indirectlyFoundTable: indirectlyFoundTables.values()){System.out.println("indirectlyFound2: " + indirectlyFoundTable.getTableName());	}
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		if (globalVariables.getLogFoundTables())  LogFoundTables(indirectlyFoundTables, "extensions_with_instance_matches.csv", globalVariables);
		System.out.println("Number of indirectly found tables with instance matches:" + String.valueOf(indirectlyFoundTables.values().size()));
//		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
//		for (ExtendedTableInformation directlyFoundTable: directlyFoundTables.values()){
//			for (String foundTableColumn: directlyFoundTable.getTableSchema2TargetSchema().keySet()){
//				System.out.println("TableSchema2TargetSchema2 " + directlyFoundTable.getTableName() + " - FT: " + foundTableColumn + " <--> QT: " + directlyFoundTable.getTableSchema2TargetSchema().get(foundTableColumn).getMatching());
//			}
//		}
//		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		
		// GET ADDITIONAL INSTANCES THROUGH INSTANCE CORRESPONDENCES  ---------------
		indirectlyFoundTables.putAll(directlyFoundTables);
		Map<String, ExtendedTableInformation> allFoundTables = indirectlyFoundTables;	
		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
		for (ExtendedTableInformation foundTable: allFoundTables.values()){System.out.println("allFoundTable1: " + foundTable.getTableName());	}
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		System.out.println("Number of tables in total:" + String.valueOf(allFoundTables.values().size()));
		try {
			allFoundTables = CorrespondenceManager.GetCorrespondenceBasedInstanceMatches(allFoundTables, globalVariables);
		} catch (Exception e) { e.printStackTrace();}
		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
		for (ExtendedTableInformation foundTable: allFoundTables.values()){System.out.println("allFoundTable2: " + foundTable.getTableName());	}
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		// Save the Tabledata for the /FetchTable-method to return   ---------------
//		for (ExtendedTableInformation table: allFoundTables.values()){
		for (ExtendedTableInformation table: directlyFoundTables.values()){	
			saveTableDataForFetching(table, globalVariables);
		}
//		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
//		for (ExtendedTableInformation directlyFoundTable: directlyFoundTables.values()){
//			for (String foundTableColumn: directlyFoundTable.getTableSchema2TargetSchema().keySet()){
//				System.out.println("TableSchema2TargetSchema3 " + directlyFoundTable.getTableName() + " - FT: " + foundTableColumn + " <--> QT: " + directlyFoundTable.getTableSchema2TargetSchema().get(foundTableColumn).getMatching());
//			}
//		}
//		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
//		// Testing  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
//		for (ExtendedTableInformation foundTable: allFoundTables.values()){
//			for (String foundTableColumn: foundTable.getTableSchema2TargetSchema().keySet()){
//				System.out.println("TableSchema2TargetSchema3 " + foundTable.getTableName() + " - FT: " + foundTableColumn + " <--> QT: " + foundTable.getTableSchema2TargetSchema().get(foundTableColumn).getMatching());
//			}
//		}
//		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step1");} catch (IOException e){}

			


		// build the ResponseObject and return it as JsonString  --------------------------
		QueryTable the_queryTable = globalVariables.getQueryTable();
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step1.5");} catch (IOException e){}
		JSONRelatedTablesResponse responseObject = the_queryTable.getResponseObject();
		
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step2");} catch (IOException e){}
		ArrayList<TableInformation> relatedTables = new ArrayList<TableInformation>();
//		for (ExtendedTableInformation table: allFoundTables.values()){
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step3");} catch (IOException e){}
		for (ExtendedTableInformation table: directlyFoundTables.values()){
			try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step4");} catch (IOException e){}
			relatedTables.add(table.getTableInformation());    // downsize to the smaller TableInformation-object
		}
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step5");} catch (IOException e){}
		responseObject.setRelatedTables(relatedTables);
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step6");} catch (IOException e){}
		String resonseJsonString = gson.toJson(responseObject);
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step7");} catch (IOException e){}
		return ok(resonseJsonString);
	}
	
	
	

	
	public void LogFoundTables(Map<String, ExtendedTableInformation> tables, String logFilename, GlobalVariables globalVariables){
		if (tables != null){
			if (tables.values().size()>0){
				try{
					CSVWriter csvwriter = new CSVWriter(new FileWriter(globalVariables.getLogFolderPath() + "/" + logFilename), ',');
					ExtendedTableInformation[] tablesArray = tables.values().toArray(new ExtendedTableInformation[tables.values().size()]);
					
					for (int i = 0; i < tablesArray.length ; i++){
					     String[] entry = {tablesArray[i].getTableName()};
					     csvwriter.writeNext(entry);    
					}
					csvwriter.close();
				} catch (IOException e){e.printStackTrace();}		
			}
			else{
				try{
					FileUtils.writeStringToFile(new File(globalVariables.getLogFolderPath() + "/" + logFilename), "There are no tables at this step!");
				} catch (IOException e){e.printStackTrace();}
			}
		}	
	}
	
	
	
	public static void saveTableDataForFetching(ExtendedTableInformation table, GlobalVariables globalVariables)
	//(Map<String, Correspondence> instancesCorrespondences2QueryTable, DS4DMBasicMatcherX matcher, String[][] relation, String tableName, String[] columnHeaders, String[] keyColumn, Integer keyColumnIndex, double tableScore, DataSource dataSource, String repositoryName) throws IOException 
	{
		
		Date lastModified = new Date(System.currentTimeMillis());
		
		
		String textBeforeTable = "";
		String textAfterTable = "";
		
		String title = "";
		double coverage = (double) table.getInstancesCorrespondences2QueryTable().size()/ globalVariables.getQueryTable().getKeyColumn().length;
		double ratio = (double) table.getInstancesCorrespondences2QueryTable().size() / table.getKeyColumn().length;
		double trust = 1; 
		double emptyValues = 0;
		


		
		MetaDataTable meta = new MetaDataTable(table.getSchemaSimilarityScore(), 
											   lastModified.toString(),
											   table.getMatchingType(), //table.getValue().getUrl()
											   textBeforeTable, 
											   textAfterTable, 
											   title, 
											   coverage, 
											   ratio,
											   trust, 
											   emptyValues);
		
	
//		-------------------------------------------------------------------------------------------------------------------
		
		
		JSONTableResponse t_sab = new JSONTableResponse();
		t_sab.setMetaData(meta);
		t_sab.setHasHeader(true);
		t_sab.setHasKeyColumn(true);
		t_sab.setHeaderRowIndex(Integer.toString(0));
		t_sab.setTableName(table.getTableName());
		
		//t_sab.setKeyColumnIndex			
		String indexCol = Integer.toString(table.getKeyColumnIndex()) + "_" + table.getKeyColumn()[0];
		t_sab.setKeyColumnIndex(indexCol);
		
		
		//t_sab.setRelation			
		List<List<String>> new_relation = new ArrayList<List<String>>();

		for(int columnNumber =0; columnNumber < table.getRelation().length; columnNumber++){
			
			List<String> new_column = Arrays.asList(table.getRelation()[columnNumber]);
			String indexedHeader = columnNumber + "_" + new_column.get(0);
			new_column.set(0, indexedHeader);
			
			new_relation.add(new_column);
		}
		t_sab.setRelation(new_relation);
		
		
		//t_sab.setDataTypes
		Map<String, String> dataTypes = guessTypes(new_relation);
		t_sab.setDataTypes(dataTypes);

		
		//t_sab.setMetaData
		meta.setURL(""); // table.getValue().getUrl()
		t_sab.setMetaData(meta);
		

//		-------------------------------------------------------------------------------------------------------------------
		ReadWriteGson<JSONTableResponse> resp = new ReadWriteGson<JSONTableResponse>(t_sab);
		
		File fetchedTablesFolder = new File("public/repositories/" + globalVariables.getRepositoryName() + "/fetchedTables");
		if (!fetchedTablesFolder.exists())
			fetchedTablesFolder.mkdirs();
		File current_table = new File(fetchedTablesFolder.getAbsolutePath() + "/" + table.getTableName());
		try{
			resp.writeJson(current_table);
		} catch(Exception e) {e.printStackTrace();}
	}

	
	
	
	public static Map<String, String> guessTypes (List<List<String>> relation){
		Map<String, String> dataTypes = new HashMap<String, String>();
		TableColumnTypeGuesser tctg = new TableColumnTypeGuesser();
		for (List<String> e: relation){
			ColumnDataType type = ColumnDataType.string;;
			String columnName = e.get(0);
			List<String> columnValues = new ArrayList<String>();
			columnValues.addAll(e);
			columnValues.remove(0);
			type = tctg.guessTypeForColumn(columnValues, columnName, false, null);
			dataTypes.put(columnName, type.toString());
		}
		return dataTypes;
	}
	
	
}
