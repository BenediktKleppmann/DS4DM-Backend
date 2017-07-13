package de.uni_mannheim.informatik.dws.ds4dm.CreateLuceneIndex;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.opencsv.CSVReader;

import de.uni_mannheim.informatik.dws.winter.webtables.Table;
import de.uni_mannheim.informatik.dws.winter.webtables.TableColumn;
import de.uni_mannheim.informatik.dws.winter.webtables.TableRow;
import de.uni_mannheim.informatik.dws.winter.webtables.detectors.TableHeaderDetectorContentBased;
import de.uni_mannheim.informatik.dws.winter.webtables.detectors.TableKeyIdentification;
import de.uni_mannheim.informatik.dws.winter.webtables.parsers.CsvTableParser;


/**
 * This is the Main Class of the DS4DM Preprocessing Component called "CreateLuceneIndex".
 * 
 * About DS4DM:
 * DS4DM stands for 'Data Search for Data Mining'. It is a extension to the RapidMiner software.
 * This extension allows users to extend a data table with additional attributes(=columns) of their choice.
 * E.g. if you have a table with company information uploaded in RapidMiner, you can use the DS4DM-RapidMiner-operators for getting the additional column 'Headquarter location' - ready populated with the correct values.
 * 
 * The DS4DM Webservice:
 * The table extension operator of the DS4DM-RapiMiner extension uses a custom-made webservice. 
 * This webservice hosts a large corpus of datatables. Whenever a table needs to be extended, the webservice looks for the right tables (in our example: tables with company headquarter information) 
 * and returns these tables to the RapidMiner operator.
 * 
 * Reason for the Indexes:
 * The DS4DM Webservice has to be fast at identifying the correct tables to return to the DS4DM RapidMiner operator.
 * To achieve this speed despite the large corpus of data tables it has to search through, Indexes are needed.
 * In total 3 lucene indexes are used; they contain information about the data tables in the webservice's corpus.
 * 
 * 
 *  ***********************************************************************************************************
 *  *                      This Software component creates these 3 lucene indexes                             *
 *  *  You run it before you start the webservice, or whenever you would like to use a new corpus of tables.  *
 *  *                      This is why it is called a 'Preprocessing Component'.                              *
 *  ***********************************************************************************************************
 *
 *
 * The 3 Indexes are: 
 * 
 *   * ColumnNameIndex
 *     This index has an entry for each column of each table (in the corpus)
 *     In every entry following information is saved: tableHeader (=table name), value (=column name), columnDataType, tableCardinality, columnDistinctValues, columnindex, columnOriginalHeader, fullTablePath (=folder in which the original table is located)
 *     
 *   * TableIndex
 *     This index has an entry for each distinct value in each column of each table
 *     In every entry following information is saved: id (=the distinct-value-index for each column), tableHeader, columnHeader, columnDataType, tableCardinality, columnDistinctValues, valueMultiplicity (=how often the distinct value appears in this column), value (=the distinct value), fullTablePath, isPrimaryKey (=true if the column is the PK), originalValue
 *      
 *   * KeyColumnIndex
 *     This index has an entry for each table
 *     In every entry following information is saved: tableHeader, columnHeader, keyColumnString (= a list of all the values in the table's key column concatenated into one long string), keyColumnIndex 
 *     Unlike the other two, this index is not used by the DS4DM webservice. It is used by another Preprocessing component: CreateCorrespondenceFiles
 *     
 *     
 *  Operation:
 *  This java program can be executed by...
 *    * calling its .jar-file via the commandline/terminal.  
 *      In this case the command should look like this:  java -jar CreateLuceneIndex-0.0.1-SNAPSHOT-jar-with-dependencies.jar "path/to/the/csvTables/you'd/like/to/have/indexed"  "path/to/where/the/indexes/will/be/saved"
 *      
 *    * running the main-Method of the Main-Class in an java environment with the same two parameters
 * 
 * 
 * @author Benedikt Kleppmann
 *
 */
public class Main {
	
	
	
	
	
	
	/**
	 * The main Method
	 * 
	 * This is where the program starts.
	 * When calling it two parameters must be passed to it:  datafilePath (=path/to/the/folder/where/the/to-be-indexed/csv-files/are) and indexFolderPath (=path/to/the/folder/where/the/indexes/should/be/saved)
	 * If these parameters aren't passed. The program ends with an error message.
	 * 
	 * the steps in this function are:
	 * 1. open the indexes
	 * 2. loop through the csv-files and call the indexing-method
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		
		if (args.length!=2){
			System.out.println("======================================================================================");
			System.out.println("|  Error: the wrong number of Parameters have been passed to this function.          |");
			System.out.println("|  2 Parameters have to be passed:                                                   |");
			System.out.println("|     1. datafilePath  (=path/to/the/folder/where/the/to-be-indexed/csv-files/are)   |");
			System.out.println("|     2. indexFolderPath   (=path/to/the/folder/where/the/indexes/should/be/saved)   |");
			System.out.println("======================================================================================");
		} else {
			
			String datafilePath = args[0];
			String indexFolderPath = args[1];
	
			
	
			// OPEN INDEXES ============================================
			//if the last character of the indexFolderPath is '/', remove it
			if (indexFolderPath.substring(indexFolderPath.length() - 1)=="/") {
				indexFolderPath = indexFolderPath.substring(0, indexFolderPath.length() - 1);
			}
			
			//index config
	    	Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
	
			// open keyColumnIndex
	    	IndexWriterConfig config1 = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
			String indexPath1 = indexFolderPath + "/KeyColumnIndex/";
		    Directory directory1 = FSDirectory.open(new File(indexPath1));
	    	IndexWriter keyColumnIndexWriter = new IndexWriter(directory1, config1);
	    	
			// open ColumnNameIndex
	    	IndexWriterConfig config2 = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
			String indexPath2 = indexFolderPath + "/ColumnNameIndex/";
		    Directory directory2 = FSDirectory.open(new File(indexPath2));
	    	IndexWriter columnNameIndexWriter = new IndexWriter(directory2, config2);
	    	
			// open TableIndex
	    	IndexWriterConfig config3 = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
			String indexPath3 = indexFolderPath + "/TableIndex/";
		    Directory directory3 = FSDirectory.open(new File(indexPath3));
	    	IndexWriter tableIndexWriter = new IndexWriter(directory3, config3);
	    	
	    	
	    	
	    	// LOOP THROUGH FILES IN datafilePath =======================================================================
			File dir = new File(datafilePath);
			File[] dataFiles = dir.listFiles();
		
	    	for (File dataFile: dataFiles){
	    		if (FilenameUtils.getExtension(dataFile.getName()).equals("csv"))
	    		{
	    			System.out.println(dataFile.getName() + "===============================================================================");
	    			writeTableToIndexes(dataFile, keyColumnIndexWriter, columnNameIndexWriter, tableIndexWriter);
	
	    		}
	    	}
	    	
	    	// CLOSE INDEXES ============================================
	    	keyColumnIndexWriter.close();
	    	columnNameIndexWriter.close();
	    	tableIndexWriter.close();
		}
	}
	
	
	/**
	 * writeTableToIndexes
	 * 
	 * This method saves information about the csv-dataFile to the following Indexes: KeyColumnIndex, ColumnNameIndex, TableIndex
	 * This method does following steps:
	 * 1. read the csv-table into a table-object
	 * 2. determine the key-column of the table
	 * 3. if a key column was detected: 
	 * 		3.1. create a hashmap with the distinct values of every column (this will be needed for the TableIndex and the KeyColumnIndex)
	 * 		3.2. call the indexing-methods for the individual Indexes
	 * 
	 * @param dataFile
	 * @param keyColumnIndexWriter
	 * @param columnNameIndexWriter
	 * @param tableIndexWriter
	 * @throws IOException
	 */
	public static void writeTableToIndexes(File dataFile, IndexWriter keyColumnIndexWriter, IndexWriter columnNameIndexWriter, IndexWriter tableIndexWriter) throws IOException{
		
		// get Table-object for tableBeingIndexed
		CsvTableParser csvtableparser = new CsvTableParser();
		csvtableparser.setTableHeaderDetector(new TableHeaderDetectorContentBased());
		Table table = csvtableparser.parseTable(dataFile);
		
	
		// identify the key column
		TableKeyIdentification tableKeyIdentification = new TableKeyIdentification();
		tableKeyIdentification.identifyKeys(table);
		int keyColumnIndex = table.getSubjectColumnIndex();
		
		if (keyColumnIndex==-1) {
			System.out.println("error: no key column was detected for the table " + dataFile.getName());
		} else {
			
			HashMap<String, HashMap<String, Integer>> distinctTableValues = makeDistinctValuesMap(table);
					
			boolean columnNameIndexSuccess = writeTableToColumnNameIndex(table, dataFile, distinctTableValues, columnNameIndexWriter);
			boolean tableIndexIndexSuccess = writeTableToTableIndex(table, dataFile, distinctTableValues, tableIndexWriter);
			boolean keyColumnIndexSuccess = writeTableToKeyColumnIndex(table, dataFile.getName(), keyColumnIndexWriter, keyColumnIndex);
			
		}

	}
	
	/**
	 * makeDistinctValuesMap
	 * 
	 * This method creates a hashmap containing the distinct values of the table's columns
	 * the input is a Table, the output is a hashmap with the following structure:
	 * { 
	 *   column1: {first_value_of_column1: occurence_count, second_value_of_column1: occurence_count,..},
	 *   column2: {first_value_of_column2: occurence_count, second_value_of_column2: occurence_count,..},
	 *   :
	 * }
	 * 
	 * @param table
	 * @return distinctValues-hashmap
	 */
	public static HashMap<String, HashMap<String, Integer>> makeDistinctValuesMap(Table table){
		HashMap<String, HashMap<String, Integer>> distinctValues = new HashMap<String, HashMap<String, Integer>>();
		
		for (TableRow row: table.getRows()){
			for (int columnIndex = 0; columnIndex< table.getSchema().getSize(); columnIndex++){
				
				String columnname = table.getSchema().get(columnIndex).getHeader();
				
				if (row.get(columnIndex) != null){
					String value = row.get(columnIndex).toString();
					
					if (distinctValues.keySet().contains(columnname)){
						if (distinctValues.get(columnname).keySet().contains(value)){
							HashMap<String, Integer> thisColumnsValueCount = distinctValues.get(columnname);
							thisColumnsValueCount.put(value, thisColumnsValueCount.get(value) + 1);
							distinctValues.put(columnname,thisColumnsValueCount);
						} else {
							HashMap<String, Integer> thisColumnsValueCount = distinctValues.get(columnname);
							thisColumnsValueCount.put(value, 1);
							distinctValues.put(columnname,thisColumnsValueCount);
						}
			
						
					} else {
						HashMap<String, Integer> newValueCountMap  = new HashMap<String, Integer>();
						newValueCountMap.put("columnIndex", columnIndex);
						newValueCountMap.put(value, 1);
						distinctValues.put(columnname,newValueCountMap);
					}
					
					
					
					
				}
			}
		}
		
		return distinctValues;
	}
	
	
	/**
	 * writeTableToKeyColumnIndex
	 * 
	 * This method saves the key column of the table to the KeyColumnIndex
	 * It does the following steps:
	 * 1. make the keyColumnString (=the concatenated values of the table's keyColumn (seperated by " "))
	 * 2. save a document with the following values to the keyColumnIndex: tableHeader, columnHeader, keyColumnString, keyColumnIndex
	 * 
	 * @param table
	 * @param tablename
	 * @param keyColumnIndexWriter
	 * @param keyColumnIndex
	 * @return boolean: 'success' 
	 */
	public static boolean writeTableToKeyColumnIndex(Table table, String tablename, IndexWriter keyColumnIndexWriter, int keyColumnIndex){
		boolean success = true;
		
		// read in the key column values and append them to keyColumnString
		String keyColumnString = "";

		for (TableRow row : table.getRows()){
			keyColumnString += row.get(keyColumnIndex) + " ";
		}


	    // make a document
		Document doc = new Document();	
	    doc.add(new Field("tableHeader", tablename, TextField.TYPE_STORED));
	    doc.add(new Field("columnHeader", table.getSchema().get(keyColumnIndex).getHeader(), TextField.TYPE_STORED));
	    doc.add(new Field("keyColumnString", keyColumnString, TextField.TYPE_STORED));
	    doc.add(new Field("keyColumnIndex", String.valueOf(keyColumnIndex), TextField.TYPE_STORED));
	
	    // add the document to the index
	    try {
			keyColumnIndexWriter.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}
	    
	    
		return success;
	}
	
	/**
	 * writeTableToColumnNameIndex
	 * 
	 * This method saves the names of the table's columns to the ColumnNameIndex
	 * It does the following steps:
	 * 1. loop through every column in the table:
	 * 		1.1. 
	 * 
	 * @param table
	 * @param dataFile
	 * @param distinctTableValues
	 * @param columnNameIndexWriter
	 * @return
	 */
	public static boolean writeTableToColumnNameIndex(Table table, File dataFile, HashMap<String, HashMap<String, Integer>> distinctTableValues, IndexWriter columnNameIndexWriter){
		boolean success = true;

		for (int columnIndex = 0 ; columnIndex< table.getSchema().getSize(); columnIndex++){	
			String columnname = table.getSchema().get(columnIndex).getHeader();
			
			if (distinctTableValues.get(columnname) != null){
				
				Integer columnDistinctValues = distinctTableValues.get(columnname).keySet().size() - 1;
					
			    // make a document
				Document doc = new Document();	
			    doc.add(new Field("tableHeader", dataFile.getName(), TextField.TYPE_STORED));
			    doc.add(new Field("value", columnname, TextField.TYPE_STORED));
			    doc.add(new Field("columnDataType", table.getSchema().get(columnIndex).getDataType().toString(), TextField.TYPE_STORED));
			    doc.add(new Field("tableCardinality", String.valueOf(table.getSchema().getSize()), TextField.TYPE_STORED));
			    doc.add(new Field("columnDistinctValues", String.valueOf(columnDistinctValues), TextField.TYPE_STORED));
			    doc.add(new Field("columnindex", String.valueOf(columnIndex), TextField.TYPE_STORED));
			    doc.add(new Field("columnOriginalHeader", columnname, TextField.TYPE_STORED));
			    doc.add(new Field("fullTablePath", dataFile.getAbsolutePath(), TextField.TYPE_STORED));
				
			    // add the document to the index
			    try {
			    	columnNameIndexWriter.addDocument(doc);
				} catch (IOException e) {
					e.printStackTrace();
					success = false;
				}
			    
			}
		}

		return success;
	}
	
	
	/**
	 * writeTableToTableIndex
	 * 
	 * This method saves the distinct values of every of the table's columns to the TableIndex
	 * It does the following steps:
	 * 1. loop through all the columns in the table
	 * 		1.1. loop through all the distinct values in this column
	 * 			1.1.1. save a document with following values to the TableIndex: id, tableHeader, columnHeader, columnDataType, tableCardinality, columnDistinctValues, valueMultiplicity, value, fullTablePath, isPrimaryKey, originalValue
	 * 
	 * @param table
	 * @param dataFile
	 * @param distinctTableValues
	 * @param tableIndexWriter
	 * @return boolean: 'success'
	 */
	public static boolean writeTableToTableIndex(Table table, File dataFile, HashMap<String, HashMap<String, Integer>> distinctTableValues, IndexWriter tableIndexWriter){
		boolean success = true;

		for (String columnname: distinctTableValues.keySet()){
			
			Integer columnIndex = distinctTableValues.get(columnname).get("columnIndex");
			String dataType =  table.getSchema().get(columnIndex).getDataType().toString();	
			Integer columnDistinctValues = distinctTableValues.get(columnname).keySet().size() - 1;
			Integer valueCounter = 0;
			boolean isPrimaryKey = (table.getSubjectColumnIndex()==columnIndex ? true : false);
			
			
			for (String columnvalue: distinctTableValues.get(columnname).keySet()){
				valueCounter++;
				Integer valueMultiplicity = distinctTableValues.get(columnname).get(columnvalue);
				
				
				
			    // make a document
				Document doc = new Document();	
			    doc.add(new Field("id", String.valueOf(valueCounter), TextField.TYPE_STORED));
			    doc.add(new Field("tableHeader", dataFile.getName(), TextField.TYPE_STORED));
			    doc.add(new Field("columnHeader", columnname, TextField.TYPE_STORED));
			    doc.add(new Field("columnDataType", dataType, TextField.TYPE_STORED));
			    doc.add(new Field("tableCardinality", String.valueOf(distinctTableValues.keySet().size()), TextField.TYPE_STORED));
			    doc.add(new Field("columnDistinctValues", String.valueOf(columnDistinctValues), TextField.TYPE_STORED));
			    doc.add(new Field("valueMultiplicity", String.valueOf(valueMultiplicity), TextField.TYPE_STORED));
			    doc.add(new Field("value", columnvalue, TextField.TYPE_STORED));
			    doc.add(new Field("fullTablePath", dataFile.getAbsolutePath(), TextField.TYPE_STORED));
			    doc.add(new Field("isPrimaryKey", String.valueOf(isPrimaryKey), TextField.TYPE_STORED));
			    doc.add(new Field("originalValue", columnvalue, TextField.TYPE_STORED));

				
			    // add the document to the index
			    try {
			    	tableIndexWriter.addDocument(doc);
				} catch (IOException e) {
					e.printStackTrace();
					success = false;
				}
			    
			    
			}
		}
		return success;
	}
	
}

