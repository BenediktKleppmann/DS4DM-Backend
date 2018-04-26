package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.rapidminer.extension.json.JSONRelatedTablesResponse;

import au.com.bytecode.opencsv.CSVWriter;
import de.mannheim.uni.types.ColumnTypeGuesser.ColumnDataType;
import de.mannheim.uni.utils.TableColumnTypeGuesser;
import extendedSearch2.StringNormalization;

public class QueryTable {
	
	private String[] extensionAttributes;
	private String correlationAttribute;
	private Double mimimumDensity; 
	private String keyColumnIndex;
	private Integer maximalNumberOfTables;
	private String[][] queryTable;
	private String rankingPolicy;
	
	
	//=========================================================================
	// get and set methods
	//=========================================================================
	
	//extensionAttributes --------------------------------------------
	public void setExtensionAttributes(String[] extensionAttributes){
		this.extensionAttributes = extensionAttributes;
	}
	
	public String[] getExtensionAttributes(){
		return this.extensionAttributes;
	}

	public String getExtensionAttribute(){
		String extensionAttribute = "";
		if (this.extensionAttributes != null){
			if (this.extensionAttributes.length > 0)
				extensionAttribute = this.extensionAttributes[0];
			
		}
		return extensionAttribute
;
	}
	
	//correlationAttribute --------------------------------------------
	public void setCorrelationAttribute(String correlationAttribute){
		this.correlationAttribute = correlationAttribute;
	}
	
	public String getCorrelationAttribute(){
		return this.correlationAttribute;
	}
	
	//mimimumDensity --------------------------------------------
	public void setMimimumDensity(Double mimimumDensity){
		this.mimimumDensity = mimimumDensity;
	}
	
	public Double getMimimumDensity(){
		return this.mimimumDensity;
	}
	
	
	
	
	//keyColumnIndex --------------------------------------------
	public void setKeyColumnIndex(String keyColumnIndex){
		this.keyColumnIndex = keyColumnIndex;
	}
	
	public String getKeyColumnIndex(){
		return this.keyColumnIndex;
	}
	
	//keyColumnIndex --------------------------------------------
	public void setMaximalNumberOfTables(Integer maximalNumberOfTables){
		this.maximalNumberOfTables = maximalNumberOfTables;
	}
	
	public Integer getMaximalNumberOfTables(){
		return this.maximalNumberOfTables;
	}
	
	//keyColumnIndex --------------------------------------------
	public void setQueryTable(String[][] queryTable){
		this.queryTable = queryTable;
	}
	
	public String[][] getQueryTable(){
		return this.queryTable;
	}
	
	//keyColumnIndex --------------------------------------------
	public void setRankingPolicy(String rankingPolicy){
		this.rankingPolicy = rankingPolicy;
	}
	
	public String getRankingPolicy(){
		return this.rankingPolicy;
	}
	
	
	//=========================================================================
	// get the values necessary for the ResponseObject
	//=========================================================================
	public List<String> getTargetSchema(){
		
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step2.1");} catch (IOException e){}
		List<String> targetSchema = new ArrayList<String>();
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step2.2");} catch (IOException e){}
		for (String[] column: queryTable){
			try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step2.3");} catch (IOException e){}
			targetSchema.add(column[0]);
		}
		try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step2.4");} catch (IOException e){}
		for (String extensionAttribute: extensionAttributes){
			try{ FileUtils.writeStringToFile(new File("/home/bkleppma/ds4dm_webservice/DS4DM/DS4DM_webservice/public/exampleData/test.txt"), "extendedSearch step2.5");} catch (IOException e){}
			targetSchema.add(extensionAttribute);
		}
		return targetSchema;
	}
	
	
	public List<String> getNormalizedTargetSchema() {
		StringNormalization stringNormalizer = new StringNormalization();
		List<String> targetSchema = getTargetSchema();
		List<String> normalizedTargetSchema = new ArrayList<String>();
		for (String columnHeader: targetSchema){
			normalizedTargetSchema.add(stringNormalizer.normalizeString(columnHeader));
		}
		return normalizedTargetSchema;
	}
	
	
	public HashMap<String, String> getExtensionAttributes2TargetSchema(){
		
		HashMap<String, String> getExtensionAttributes2TargetSchema = new HashMap<String, String>();
		for (String extensionAttribute: extensionAttributes){
			getExtensionAttributes2TargetSchema.put(extensionAttribute, extensionAttribute);
		}
		return getExtensionAttributes2TargetSchema;
	}
	
	
	public Map<String, String> getQueryTable2TargetSchema(){
		
		Map<String, String> queryTable2TargetSchema = new HashMap<String, String>();
		for (String[] column: queryTable){
			queryTable2TargetSchema.put(column[0], column[0]);
		}
		return queryTable2TargetSchema;
	}
	
	
	public Map<String, String> getDataTypes(){
		
		Map<String, String> dataTypes = new HashMap<String, String>();
		TableColumnTypeGuesser tctg = new TableColumnTypeGuesser();
		
		for (String[] column: queryTable){
		
			List<String> columnValues = new ArrayList<>(Arrays.asList(column));
			String columnHeader = columnValues.get(0);
			columnValues.remove(0);

			ColumnDataType type = tctg.guessTypeForColumn(columnValues, columnHeader, false, null);
			dataTypes.put(columnHeader, type.toString());
		}
		
		
		dataTypes.put(extensionAttributes[0], "string");
		return dataTypes;
	}
	
	//=========================================================================
	// Construct the ResponseObject
	//=========================================================================
	
	public JSONRelatedTablesResponse getResponseObject() {
		JSONRelatedTablesResponse responseMappimg = new JSONRelatedTablesResponse();
		responseMappimg.setTargetSchema(getTargetSchema());
		responseMappimg.setExtensionAttributes2TargetSchema(getExtensionAttributes2TargetSchema());
		responseMappimg.setQueryTable2TargetSchema(getQueryTable2TargetSchema());
		responseMappimg.setDataTypes(getDataTypes());
		return responseMappimg;
	}
	
	
	//=========================================================================
	// Additional utilities
	//=========================================================================
	
	public String[] getKeyColumn(){
		return this.getQueryTable()[Integer.valueOf(this.getKeyColumnIndex())];
	}
	
	
	public String[] getNormalizedKeyColumn(){
		
		StringNormalization stringNormalizer = new StringNormalization();
			
		String[] keyColumn = this.getQueryTable()[Integer.valueOf(this.getKeyColumnIndex())];
		for (int i = 0; i<keyColumn.length; i++){
			keyColumn[i] = stringNormalizer.normalizeString(keyColumn[i]);
		}
		return keyColumn;
	}
	
	
	public void saveToFile(String filepath)  {
		try{ 
		    CSVWriter csvwriter = new CSVWriter(new FileWriter(filepath), ',');
		    
		    String[][] transposedQueryTable = transposeRelation(this.queryTable);
		    
		    for (String[] row:  transposedQueryTable){
		    	csvwriter.writeNext(row);
		    	
		    }
		    csvwriter.close();
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Query Table Object couldn't be saved to filelocation " + filepath);
		}
	}
	
	
	public String[][] transposeRelation(String[][] relation){
	    int m = relation.length;
	    int n = relation[0].length;

	    String[][] trasposedRelation = new String[n][m];

	    for(int x = 0; x < n; x++)
	    {
	        for(int y = 0; y < m; y++)
	        {
	        	trasposedRelation[x][y] = relation[y][x];
	        }
	    }
	    return trasposedRelation;
	}
	
	
	
}
