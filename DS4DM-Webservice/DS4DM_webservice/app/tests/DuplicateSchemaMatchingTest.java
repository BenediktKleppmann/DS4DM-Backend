package tests;

import java.io.File;

import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.webtables.Table;
import de.uni_mannheim.informatik.dws.winter.webtables.parsers.CsvTableParser;
import uploadTable.FindCorrespondences;
import uploadTable.additionalWinterClasses.MatchableTableColumn;
import uploadTable.additionalWinterClasses.MatchableTableRow;
import uploadTable.additionalWinterClasses.WebTableDataSetLoader;



public class DuplicateSchemaMatchingTest {
	
	public static void main(String[] args) throws Exception{
		
		
		String correspondenceFolderPath = "C:/Users/UNI-Mannheim/Documents/Test Scripts/temporary files/2017-09-14/dummy data/correspondences";
		
		CsvTableParser csvParser = new CsvTableParser();
		WebTableDataSetLoader loader = new WebTableDataSetLoader();
		
		File newCSVFile = new File("C:/Users/UNI-Mannheim/Documents/Test Scripts/temporary files/2017-09-14/dummy data/test_dataset1.csv");
		Table newTable = csvParser.parseTable(newCSVFile);
		DataSet<MatchableTableRow, MatchableTableColumn> newDataset = loader.createTableDataSet(newTable);
		
		File foundCSVFile = new File("C:/Users/UNI-Mannheim/Documents/Test Scripts/temporary files/2017-09-14/dummy data/test_dataset2.csv");
		Table foundTable = csvParser.parseTable(foundCSVFile);
		DataSet<MatchableTableRow, MatchableTableColumn> foundDataset = loader.createTableDataSet(foundTable);
		
		
		
		
		FindCorrespondences matchingFunctions = new FindCorrespondences();
		Processable<Correspondence<MatchableTableRow, MatchableTableColumn>> instanceCorrespondences = matchingFunctions.getInstanceMatches(newDataset, foundDataset, newCSVFile, foundCSVFile, correspondenceFolderPath);
		
		System.out.println("INSTANCE CORRESPONDENCES =======================================");
		for (Correspondence<MatchableTableRow, MatchableTableColumn> instanceCorrespondence: instanceCorrespondences.get()){
			System.out.println(instanceCorrespondence.getFirstRecord().getRowNumber() + "," + instanceCorrespondence.getFirstRecord().getRowNumber() + "," + instanceCorrespondence.getSimilarityScore());
		}
		if (instanceCorrespondences.get().size()>0){
			matchingFunctions.getDuplicateBasedSchemaMatches(newDataset, foundDataset, newCSVFile, foundCSVFile, instanceCorrespondences, correspondenceFolderPath);
		}
	}

}
