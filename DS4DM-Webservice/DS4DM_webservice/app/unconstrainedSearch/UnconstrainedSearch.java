package unconstrainedSearch;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.matching.aggregators.TopKCorrespondencesAggregator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.AggregateBySecondRecordRule;
import de.uni_mannheim.informatik.dws.winter.matching.rules.FlattenAggregatedCorrespondencesRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.preprocessing.datatypes.DataType;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.webtables.Table;
import de.uni_mannheim.informatik.dws.winter.webtables.TableColumn;
import de.uni_mannheim.informatik.dws.winter.webtables.TableRow;
import de.uni_mannheim.informatik.dws.winter.webtables.parsers.CsvTableParser;
import de.uni_mannheim.informatik.dws.winter.webtables.parsers.TableFactory;
import uploadTable.additionalWinterClasses.MatchableTableColumn;
import uploadTable.additionalWinterClasses.MatchableTableRow;
import uploadTable.additionalWinterClasses.WebTableDataSetLoader;

public class UnconstrainedSearch {

	public static Table getFusedTable(model.QueryTable queryTableObject, String repositoryName){
		
		queryTableObject.saveToFile("public/exampleData/temp_query_table.csv");
		CsvTableParser csvtableparser = new CsvTableParser();
		Table queryTable = csvtableparser.parseTable(new File("public/exampleData/temp_query_table.csv"));
		
		Table fused = null;
		
		// get keyColumnIndex
		Integer keyColumnIndex = Integer.parseInt(queryTableObject.getKeyColumnIndex());
		if (keyColumnIndex == null || keyColumnIndex < 0){
			keyColumnIndex = queryTable.getSubjectColumnIndex();
		}
		
		/*******************************************************
		 * SEARCH
		 *******************************************************/
		System.out.println("Unconstrianed Search2");
		
		TableFactory fac = new TableFactory();
		
		// load the query table
		Map<Integer, Table> tablesById = new HashMap<>();
		tablesById.put(queryTable.getTableId(), queryTable);
		
		// search 
		List<String> foundTablenames = SearchForTables.search(queryTableObject, keyColumnIndex, repositoryName);
		System.out.println("Unconstrianed Search7.1");

		if(foundTablenames!=null && foundTablenames.size()>0) {
			System.out.println("Unconstrianed Search7.2");
			
			// load the tables from the search result
			Collection<Table> tables = new LinkedList<>();
			int foundTableId = 1;
			for(String foundTablename : foundTablenames) {
				Table foundTable = fac.createTableFromFile(new File("public/repositories/" + repositoryName + "/tables/" + foundTablename));
				if(foundTable!=null && !foundTable.getPath().equals(queryTable.getPath())) {
					foundTable.setTableId(foundTableId++);
					tablesById.put(foundTable.getTableId(), foundTable);
					tables.add(foundTable);
				}
			}

			// add the query table to the search results
			// it will match the query table perfectly and make sure that all records are kept in the final result
			// even if no other table contained a certain record
			queryTable.setPath("query");
			queryTable.setTableId(foundTableId);
			tablesById.put(queryTable.getTableId(), queryTable);
			tables.add(queryTable);
			
			/*******************************************************
			 * SCHEMA MATCHING
			 *******************************************************/
			System.out.println("Unconstrianed Search8");
			
			// load the tables into datasets
			WebTableDataSetLoader loader = new WebTableDataSetLoader();
			FusibleDataSet<MatchableTableRow, MatchableTableColumn> queryDS = loader.createQueryDataSet(queryTable);
			FusibleDataSet<MatchableTableRow, MatchableTableColumn> tablesDS = loader.createTablesDataSet(tables);
			
			// run schema matching
			WebTableMatcher matcher = new WebTableMatcher();
			Processable<Correspondence<MatchableTableColumn, Matchable>> schemaCorrespondences = null;
			try {
				schemaCorrespondences = matcher.matchSchemas(queryDS, tablesDS);
			} catch (Exception e) {e.printStackTrace();}
			// get the ids of all tables in the search result that could be matched
			Set<Integer> matchedTables = new HashSet<>(schemaCorrespondences.map(
					(Correspondence<MatchableTableColumn, Matchable> cor, DataIterator<Integer> c)
						-> c.next(new Integer(cor.getSecondRecord().getTableId()))).distinct().get()
					);
			
			// remove unmatched tables
			Processable<MatchableTableColumn> attributes = tablesDS.getSchema().where(((c)->matchedTables.contains(c.getTableId())));

			/*******************************************************
			 * SCHEMA CONSOLIDATION
			 *******************************************************/
			System.out.println("Unconstrianed Search9");
			
			// transform query table and result tables into the consolidated schema
			attributes = queryDS.getSchema().append(attributes);
			SearchJoinSchemaConsolidator consolidator = new SearchJoinSchemaConsolidator(tablesById);
			Pair<Table, Table> consolidated = consolidator.consolidate(queryDS, tablesDS, attributes, schemaCorrespondences);

			if(consolidated!=null) {
			
				Table queryConsolidated = consolidated.getFirst();
				Table tablesConsolidated = consolidated.getSecond();
				
				// set the subject column to the column that was the subject column in the query table
				for(TableColumn c: queryConsolidated.getSchema().getRecords()) {
					if(c.getProvenance().contains(queryTable.getSubjectColumn().getIdentifier())) {
						queryConsolidated.setSubjectColumnIndex(c.getColumnIndex());
						break;
					}
				}
				for(TableColumn c: tablesConsolidated.getSchema().getRecords()) {
					if(c.getProvenance().contains(queryTable.getSubjectColumn().getIdentifier())) {
						tablesConsolidated.setSubjectColumnIndex(c.getColumnIndex());
						break;
					}
				}

				/*******************************************************
				 * IDENTITY RESOLUTION
				 *******************************************************/
				System.out.println("Unconstrianed Search10");
				
				// create datasets from the consolidated tables
				queryDS = loader.createQueryDataSet(queryConsolidated);
				tablesDS = loader.createQueryDataSet(tablesConsolidated);
				
				// run identity resolution
				Processable<Correspondence<MatchableTableRow, Matchable>> recordCorrespondences = matcher.matchRecords(queryDS, tablesDS);

				// make sure that no two records from the query table are mapped to the same record in a result table
				// the result would be that these records are merged in the final result
				recordCorrespondences = recordCorrespondences
						.aggregate(
								new AggregateBySecondRecordRule<MatchableTableRow, Matchable>(0.0), 
								new TopKCorrespondencesAggregator<>(1))
						.map(new FlattenAggregatedCorrespondencesRule<>());
				
				/*******************************************************
				 * DATA FUSION
				 *******************************************************/
				System.out.println("Unconstrianed Search11");
				
				// fuse the records into a final table
				WebTableFuser fuser = new WebTableFuser();
				
				try {
					fused = fuser.fuseTables(queryConsolidated, queryDS, tablesDS, recordCorrespondences);		
					fused = consolidator.removeSparseColumns(fused, 0.1);  // remove columns that are mostly NULL
				} catch (Exception e) {e.printStackTrace();}
				
			}
			
		} else {
			System.out.println("For this table no matches were found in the KeyColumnIndex");
			
		}
		
		return fused;
		
	}
	
	
	
	
	
	public static Table removeSparselyPopulatedColumns(Table fused, Double mimimumDensity){
		// initialize numberOfNullsDict  
		Map<Integer, Pair<Integer, Integer>> numberOfNullsDict = new HashMap<Integer, Pair<Integer, Integer>>();
		for (int columnIndex = 0; columnIndex< fused.getSchema().getSize(); columnIndex ++){
			numberOfNullsDict.put(columnIndex, new Pair(0,0));
		}
		
		// loop through values and increment numberOfValues- and numberOfNulls- counters
		for (TableRow row :fused.getRows()){
			for (int columnIndex = 0; columnIndex< fused.getSchema().getSize(); columnIndex ++){
				Integer numberOfValues = numberOfNullsDict.get(columnIndex).getFirst() +1;
				Integer numberOfNulls = numberOfNullsDict.get(columnIndex).getSecond();

				if (row.get(columnIndex)==null) numberOfNulls ++;
				numberOfNullsDict.put(columnIndex, new Pair(numberOfValues, numberOfNulls)); 
			}
		}
		
		//filter out the columns with too low density
		for (int columnIndex = 0; columnIndex< fused.getSchema().getSize(); columnIndex ++){
			Double density = ((double) numberOfNullsDict.get(columnIndex).getFirst())/ ((double) numberOfNullsDict.get(columnIndex).getSecond());
			if (density < mimimumDensity) fused.removeColumn(fused.getSchema().get(columnIndex));
		}
		
		return fused;
	}
	
	
	
	
//	public static Table removeUncorrelatedColumns(Table fused, String correlationAttribute, LinkedList headers){
//		
//		Integer subjectColumnIndex = fused.getSubjectColumnIndex()
//		Integer correlationAttributeIndex = headers.indexOf(correlationAttribute);
//		
//		// extract the column information from the fused-Table
//		HashMap<Integer, Column> columns = new HashMap<Integer, Column>();
//		for (int columnIndex = 0; columnIndex< fused.getSchema().getSize(); columnIndex ++){
//			Column column= new Column();
//			column.setHeader(fused.getSchema().get(columnIndex).getHeader());
//			column.setDataType(fused.getSchema().get(columnIndex).getDataType());
//			columns.put(columnIndex, column);
//		}
//		
//		//add the values to the column information
//		for (TableRow row :fused.getRows()){
//			for (int columnIndex = 0; columnIndex< fused.getSchema().getSize(); columnIndex ++){
//				columns.get(columnIndex).addValue(row.get(columnIndex)); 
//			}
//		}
//		
//
//		
//		DataType correlationAttributeDataType = columns.get(correlationAttributeIndex).getDataType();
//		for (int evaluatedColumnIndex = 0; evaluatedColumnIndex< fused.getSchema().getSize(); evaluatedColumnIndex ++){
//			if ((evaluatedColumnIndex != subjectColumnIndex) && (evaluatedColumnIndex != correlationAttributeIndex)){
//				
//				CorrelationCalculator CorrelationCalculator = new CorrelationCalculator(columns.get(correlationAttributeIndex), columns.get(evaluatedColumnIndex));
//				
//				Double correlationCoefficient = CorrelationCalculator.calculateCorrelation();
//				
//				
//				
//				
//			}
//		}
//		
//		return fused;
//	}
//	
//	
//	
//	
//	public static Double calcualtePearsonsCorrelationCoefficient(Column column1, Column column2){
//		Double correlationCoefficient = 0.0;
//		return correlationCoefficient;
//	}
	
	
	
}
