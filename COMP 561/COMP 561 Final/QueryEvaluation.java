import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QueryEvaluation {
	private static double extendThreshold = 6.0;
	
	private List<Map<String, Double>> probMapList; // probability map list
	private Map<String, List<Integer>> dbIndex = new HashMap<>();
	private int w = 11;
	private int scoreThreshold = 10; // default HSP score threshold
	private String database;
	
	public QueryEvaluation(List<Map<String, Double>> probMapList, String database, int w, int scoreThreshold) {
		this.probMapList = probMapList;
		this.w = w;
		this.scoreThreshold = scoreThreshold;
		this.database = database;
		indexingDB(database, w);
	}

	public MatchIndex evaluate(String query) throws Exception {
		Map<MatchIndex, Double> hspMap = findHits(query);
		
		double maxScore = -Double.MAX_VALUE;
		MatchIndex maxKey = null;
		for (MatchIndex key : hspMap.keySet()) {
			MatchIndex nwKey = nwAlignment(key, query);
			if (nwKey.getScore() > maxScore) {
				maxScore = nwKey.getScore();
				maxKey = nwKey;
			}
		}
		
		if (maxKey == null) {
			throw new Exception("Error found in evaluation");
		}
		return maxKey;
	}
	
	private Map<MatchIndex, Double> findHits(String query) {
		Map<MatchIndex, Double> hspMap = new HashMap<>();
		
		for (int i = 0; i < query.length() - w; i++) {
			String subQuery = query.substring(i, i + w);
			List<Integer> indexes = dbIndex.get(subQuery);
			if (indexes == null) continue;
			//System.out.println(i + " -> " + indexes.size() + " -> " + indexes.get(0));
			
			for (int j : indexes) {
				MatchIndex key = new MatchIndex(i, i+w-1, j, j+w-1);
				double score = calculateHspScore(key, query);
				key.setScore(score);
				if (score > scoreThreshold) hspMap.put(key, score);
			}
		}
		
		//for (MatchIndex indexKey : hspMap.keySet()) {
		//	System.out.println("Hit => " + indexKey.toString());
		//}
		
		return hspMap;
	}
	
	private double calculateHspScore(MatchIndex indexKey, String query) {
		// score is the number of steps extended from either side
		return checkDirection(indexKey, query, -1) + checkDirection(indexKey, query, 1);
	}
	
	private MatchIndex nwAlignment(MatchIndex mi, String query) throws Exception {
		double score = mi.getScore();
		int rows = -1;
		int cols = -1;
		if (mi.getQueryStart() > 0) {
			if (mi.getDbStart() < 4 * (mi.getQueryStart() + 1)) {
				rows = mi.getDbStart() + 1;
				cols = mi.getQueryStart() + 1;
			}
			else {
				rows = 4 * (mi.getQueryStart() + 1);
				cols = mi.getQueryStart() + 1;
			}
			QueryNWAlignment nwaLeft = new QueryNWAlignment(probMapList, mi, query, -1, rows, cols); // left
			score += nwaLeft.getScore();
		}

		int dbLen = database.length();
		int queryLen = query.length();

		if (mi.getQueryEnd() < queryLen - 1) {
			if (dbLen > 4 * (queryLen - mi.getQueryEnd()) + mi.getDbEnd()) {
				rows = dbLen - mi.getDbEnd() - 1;
				cols = queryLen - mi.getQueryEnd();
			}
			else {
				rows = 4 * (queryLen - mi.getQueryEnd());
				cols = queryLen - mi.getQueryEnd();
			}
			QueryNWAlignment nwaRight = new QueryNWAlignment(probMapList, mi, query, 1, rows, cols); // right
			score += nwaRight.getScore();
		}
		
		mi.setScore(score);
		
		return mi;
	}
	
	private double checkDirection(MatchIndex indexKey, String query, int direction) {
		double score = 0;
		double maxScore = 0;
		int dbIndex = -1;
		int queryIndex = -1;
		int maxDbIndex = -1;
		int maxQueryIndex = -1;
		int step = 0;
		int steps = 0;
		
		if (direction < 0) { // look left
			dbIndex = indexKey.getDbStart();
			queryIndex = indexKey.getQueryStart();
			step = -1;
			score = 1;
		}
		else { // look right
			dbIndex = indexKey.getDbEnd();
			queryIndex = indexKey.getQueryEnd();
			step = 1;
			score = 2;
		}
		
		int queryLen = query.length();
		int dbLen = database.length();
		while(true) {
			dbIndex += step;
			queryIndex += step;
			if (dbIndex < 0 || queryIndex < 0 || dbIndex > dbLen - 1 || queryIndex > queryLen - 1) {
				break;
			}
			
			Map<String, Double> probMap = probMapList.get(dbIndex);
			String qChr = query.substring(queryIndex, queryIndex+1);
			for (String dCh : probMap.keySet()) {
				double prob = probMap.get(dCh);
				if (qChr.equals(dCh)) {
					score += prob;
				}
				else {
					score += (1 - prob)/3.0 - prob;
				}
			}
			if (extendThreshold < (maxScore - score)) {
				break;
			}
			
			if (score >= maxScore) {
				maxScore = score;
				maxDbIndex = dbIndex;
				maxQueryIndex = queryIndex;
			}
			
			steps++;
		}
		
		if (direction < 0) {
			if (maxDbIndex >= 0) {
				indexKey.setDbStart(maxDbIndex);
				indexKey.setQueryStart(maxQueryIndex);
			}
		}
		else {
			if (maxDbIndex >= 0) {
				indexKey.setDbEnd(maxDbIndex);
				indexKey.setQueryEnd(maxQueryIndex);
			}
		}
		
		return maxScore;
	}
	
	private void indexingDB(String database, int w) {
		for (int i = 0; i < database.length() - w; i++) {
			String tempStr = database.substring(i, i+w);
			List<Integer> indexes = dbIndex.get(tempStr);
			if (indexes == null) {
				indexes = new ArrayList<>();
				dbIndex.put(tempStr, indexes);
			}
			indexes.add(i);
		}
	}
	
	public static class MatchIndex {
		private int queryStart;
		private int queryEnd;
		private int dbStart;
		private int dbEnd;
		private double score;
		
		public MatchIndex(int queryStart, int queryEnd, int dbStart, int dbEnd) {
			this.queryStart = queryStart;
			this.queryEnd = queryEnd;
			this.dbStart = dbStart;
			this.dbEnd = dbEnd;
		}
		
		public double getScore() {
			return score;
		}

		public void setScore(double score) {
			this.score = score;
		}
		
		public int getDbStart() {
			return dbStart;
		}

		public void setDbStart(int dbStart) {
			this.dbStart = dbStart;
		}

		public int getDbEnd() {
			return dbEnd;
		}


		public void setDbEnd(int dbEnd) {
			this.dbEnd = dbEnd;
		}

		public int getQueryStart() {
			return queryStart;
		}

		public void setQueryStart(int queryStart) {
			this.queryStart = queryStart;
		}

		public int getQueryEnd() {
			return queryEnd;
		}

		public void setQueryEnd(int queryEnd) {
			this.queryEnd = queryEnd;
		}

		@Override
		public String toString() {
			return "[(" + queryStart + "," + queryEnd + "),(" + dbStart + "," + dbEnd + ")]=" + score;
		}
		
		@Override
    public int hashCode() {
        return Objects.hash(queryStart, queryEnd, dbStart, dbEnd);
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof MatchIndex) {
				MatchIndex okey = (MatchIndex)o;
				return dbStart == okey.dbStart && dbEnd == okey.dbEnd && queryStart == okey.queryStart && queryEnd == okey.queryEnd;
			}
			
			return false;
		}
	}
}
