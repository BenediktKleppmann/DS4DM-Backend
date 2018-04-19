package extendedSearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

import au.com.bytecode.opencsv.CSVReader;
import model.ExtendedTableInformation;

public class StringNormalization {
	

	
	
	

	
	 
 	public String normalizeString(String myString){
 		
 		//1. lower-case
 		myString = myString.toLowerCase();

 		//2. replace substrings that appear in the redirects-file by its redirect partner
// 		myString = standardizeWordingWithRedirects(myString);

 		//3. remove all substrings that are within brackets
 		myString = removeBrackets('(', ')', myString);
 		myString = removeBrackets('[', ']', myString);
 		myString = removeBrackets('{', '}', myString);
 		myString = removeBrackets('<', '>', myString);
 		

		    
	    
 		return myString;
 	}
 
 	
 	
 	public String removeBrackets(char openBracket, char closeBracket, String myString){
			Integer bracketsOpenPos = myString.indexOf(openBracket);
			Integer bracketsClosedPos = myString.indexOf(closeBracket);
 		
 		if ((0 <= bracketsOpenPos) && (bracketsOpenPos <= bracketsClosedPos) && (bracketsClosedPos <= myString.length())) {
// 			System.out.println("Substring of " + myString + " [" + String.valueOf(bracketsOpenPos) + ":" + String.valueOf(bracketsClosedPos) + "]");
			String substringWithBracket = myString.substring(bracketsOpenPos, bracketsClosedPos + 1);
			myString = myString.replace(substringWithBracket, "");
 		}
 		return myString;
 	}
 	
 	
// 	public String standardizeWordingWithRedirects(String myString){
// 		
// 		try {
// 		QueryParser queryParser = new QueryParser(Version.LUCENE_46, "redirectSource", new StandardAnalyzer(Version.LUCENE_46));
// 		Query query = queryParser.parse(myString);
//
//
// 		ScoreDoc[] hits = indexSearcher.search(query, 10).scoreDocs;
//
//
//	 		if (hits != null){
//				for (ScoreDoc hit: hits) {
//					Document doc = this.indexSearcher.doc(hit.doc);
//					
//					String redirectSource = doc.getFields("redirectSource")[0].stringValue();
//					String redirectTarget = doc.getFields("redirectTarget")[0].stringValue();
//					
//					if (myString.contains(redirectSource)){
//						myString = myString.replace(redirectSource, redirectTarget);
//					}
//				}
//	 		}
//
// 		} catch (ParseException | IOException e) {}
//			
// 		
// 		
//
// 		
// 		return myString;
// 	}
// 	

}
