package de.uni_mannheim.informatik.dws.ds4dm.customMatchingRules;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.additionalWinterClasses.MatchableTableColumn;
import de.uni_mannheim.informatik.additionalWinterClasses.MatchableTableRow;
import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matrices.SimilarityMatrix;
import de.uni_mannheim.informatik.dws.winter.matrices.SparseSimilarityMatrixFactory;
import de.uni_mannheim.informatik.dws.winter.matrices.matcher.BestChoiceMatching;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.preprocessing.datatypes.DataType;
import de.uni_mannheim.informatik.dws.winter.similarity.date.NormalisedDateSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.DeviationSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;



/**
 * {@link Comparator} for finding instance-matches between {@link Table}s 
 * It calculates a similarity score for two instances
 * 
 * For calculating the similarity score, only the non-key-column values of the instances are considered.
 * For each of these non-key-column values of the left instance, the non-key-column value on the right side with the highest similarity is identified as corresponding value.
 * The overall similarity score is the average of the scores of corresponding values.
 * 
 *  When calculating the similarity between a pair of values, the datatype of these values is considered:
 *    * If they are both numeric, then we use DeviationSimilarity.
 *    * If they are both dates, then we use NormalisedDateSimilarity.
 *    * If they are both boolean, then we check for equality 
 *    * Otherwise, we use TokenizingJaccardSimilarity
 *    
 * 
 * @author Benedikt Kleppmann (benedikt@dwslab.de)
 * 
 */


public class NonKeyColumnComparator_withDType implements Comparator<MatchableTableRow, MatchableTableColumn> {

	private static final long serialVersionUID = 1L;
	
	private DeviationSimilarity deviationSim = new DeviationSimilarity();
	private NormalisedDateSimilarity dateSim = new NormalisedDateSimilarity();
	private TokenizingJaccardSimilarity jaccardSim = new TokenizingJaccardSimilarity();
	
	@Override
	public double compare(
			MatchableTableRow record1,
			MatchableTableRow record2,
			Correspondence<MatchableTableColumn, Matchable> schemaCorrespondences) {
		
		SimilarityMatrix<MatchableTableColumn> sim = new SparseSimilarityMatrixFactory().createSimilarityMatrix(0, 0);
		
		for (int columnIndex1 = 0; columnIndex1< record1.getSchema().length; columnIndex1++){
			for (int columnIndex2 = 0; columnIndex2< record2.getSchema().length; columnIndex2++){
				if(columnIndex1!=record1.getSubjectColumnIndex() && columnIndex2!=record2.getSubjectColumnIndex()){
					
					MatchableTableColumn c1 = record1.getSchema()[columnIndex1];
					MatchableTableColumn c2 = record2.getSchema()[columnIndex2];
					
					DataType datatype1 = c1.getType();
					DataType datatype2 = c2.getType();
					
					double similarity = 0;
					
					if(record1.get(columnIndex1)!=null && record2.get(columnIndex2)!=null){
						
						if (datatype1 == DataType.numeric && datatype2 == DataType.numeric)	{
							similarity = deviationSim.calculate(Double.valueOf(record1.get(columnIndex1).toString()), Double.valueOf(record2.get(columnIndex2).toString()));
						} else if (datatype1 == DataType.date && datatype2 == DataType.date)	{
							similarity = dateSim.calculate( (DateTime) record1.get(columnIndex1), (DateTime) record2.get(columnIndex2));
						} else if (datatype1 == DataType.bool && datatype2 == DataType.bool)	{
							similarity = (record1.get(columnIndex1)==record2.get(columnIndex2) ? 1 : 0);
						} else{
							similarity = jaccardSim.calculate(record1.get(columnIndex1).toString(),record2.get(columnIndex2).toString());
						}
					}
					
					if (!Double.isNaN(similarity)) {
						
						sim.add(c1, c2, similarity);
						
					}
				}	
			}
		}
		
		BestChoiceMatching bcm = new BestChoiceMatching();
		
		sim = bcm.match(sim);
		
		double finalSim = sim.getSum() / (double)sim.getNumberOfNonZeroElements();
		
		return finalSim;
	}

}

