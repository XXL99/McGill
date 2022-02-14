import java.util.List;
import java.util.Map;

/**
 * Needleman-Wunsch algorithm for sequence alignment
 *
 */
public class QueryNWAlignment {
	private static int LEFT = -1;
	private static int UP = 1;
	private static int DIAG = 0;
	private static int ORIG = 9;
	private String[] nts = new String[] {"A", "C", "G", "T"};
	
	private double[][] scoreMatrix;
	private int[][] tracebackMatrix;
	
	private int rows;
	private int cols;
	
	public QueryNWAlignment(List<Map<String, Double>> probMapList, QueryEvaluation.MatchIndex indexKey, 
			String query, int searchDirection, int rows, int cols) {
		init(probMapList, indexKey, query, searchDirection, rows, cols);
	}
	
	private void init(List<Map<String, Double>> probMapList, QueryEvaluation.MatchIndex indexKey, 
			String query, int searchDirection, int rows, int cols) {
		int gapPenalty = -1;
		
		this.rows = rows;
		this.cols = cols;
		
		scoreMatrix = new double[rows][cols];
		tracebackMatrix = new int[rows][cols];
		
		scoreMatrix[0][0] = 0;
		tracebackMatrix[0][0] = ORIG;
		
		// fill in first row and first column
		for (int j = 1; j < cols; j++) {
			scoreMatrix[0][j] = scoreMatrix[0][j-1] + gapPenalty;
			tracebackMatrix[0][j] = LEFT;
		}
		
		for (int i = 1; i < rows; i++) {
			scoreMatrix[i][0] = scoreMatrix[i-1][0] + gapPenalty;
			tracebackMatrix[i][0] = UP;
		}
		
		//System.out.println("Processing " + indexKey.toString());
		//System.out.println("rows=" + rows + ", cols=" + cols + ", dir=" + searchDirection);
		
		// fill in the rest
		for (int i = 1; i < rows; i++) {
		  for (int j = 1; j < cols; j++) {
		  	double diag_score = scoreMatrix[i-1][j-1];
		  	String qChr;
		  	if (searchDirection < 0) qChr = query.substring(indexKey.getQueryStart()-j, indexKey.getQueryStart()-j+1);
		  	else qChr = query.substring(indexKey.getQueryEnd() + j, indexKey.getQueryEnd() + j + 1);
		  	Map<String, Double> p;
		  	if (searchDirection < 0) p = probMapList.get(indexKey.getDbStart() - i);
		  	else p = probMapList.get(indexKey.getDbEnd() + i);
		  	for (String chr : nts) {
		  		double score = p.get(chr);
		  		if (chr.equals(qChr)) {
		  			diag_score += score;
		  		}
		  		else {
		  			diag_score -= score;
		  		}
		  	}
		  	double left_score = scoreMatrix[i][j-1] + gapPenalty;
		  	double up_score = scoreMatrix[i-1][j] + gapPenalty;
		  	ScoreDirection scoreDirection = calculateScoreAndDirection(diag_score, left_score, up_score);
		  	scoreMatrix[i][j] = scoreDirection.score;
		  	tracebackMatrix[i][j] = scoreDirection.direction;
		  }
		}
	}
	
	public double getScore() throws Exception {
		if (scoreMatrix == null) throw new Exception("Initialization is not done");
		
		return scoreMatrix[rows-1][cols-1];
	}
	
	public void printScoreMatrix() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(String.format("%5d", scoreMatrix[i][j]));
			}
			System.out.println();
		}
	}
	
	private ScoreDirection calculateScoreAndDirection(double diag_score, double left_score, double up_score) {
		double score = diag_score;
		int direction = DIAG;
		
		if (left_score > score) {
			score = left_score;
			direction = LEFT;
		}
		
		if (up_score > score) {
			score = up_score;
			direction = UP;
		}
		
		return new ScoreDirection(score, direction);
	}
	
	class ScoreDirection {
		double score;
		int direction;
		
		ScoreDirection(double score, int direction) {
			this.score = score;
			this.direction = direction;
		}
	}
}
