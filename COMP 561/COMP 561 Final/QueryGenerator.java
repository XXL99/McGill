import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QueryGenerator {
	private String sequence;
	private Random rand = new Random();
	private String[] nts = new String[] {"A", "C", "G", "T"};
	
	public QueryGenerator(String sequence) {
		this.sequence = sequence;
	}
	
	public Map<String, Integer> createQueries(int numOfQueries, int queryLen, double errorRate) {
		Map<String, Integer> queries = new HashMap<>();
		
		for (int i = 0; i < numOfQueries; i++) {
			queries.putAll(createQuery(queryLen, errorRate));;
		}
		return queries;
	}
	
	private Map<String, Integer> createQuery(int queryLen, double errorRate) {
		int pos = rand.nextInt(sequence.length() - queryLen - 1);
		String query = sequence.substring(pos, pos + queryLen);
		
		boolean isInsertion = true;
		
		int loopNum = (int)(errorRate * queryLen);
		for (int i = 0; i < loopNum; i++) {
			if (isInsertion) {
				query = insertOne(query);
				isInsertion = false;
			}
			else {
				query = deleteOne(query);
				isInsertion = true;
			}
		}

		Map<String, Integer> qMap = new HashMap<>();
		qMap.put(query, pos);
		
		return qMap;
		
	}
	
	private String insertOne(String query) {
		int pos = rand.nextInt(query.length());
		String ch = nts[rand.nextInt(4)];
		if (pos == 0) {
			query = ch + query;
		}
		else if (pos == (query.length() - 1)) {
			query = query + ch;
		}
		else {
			query = query.substring(0, pos) + ch + query.substring(pos);
		}

		return query;
	}
	
	private String deleteOne(String query) {
		int pos = rand.nextInt(query.length());
		if (pos == 0) {
			query = query.substring(1);
		}
		else if (pos == (query.length() - 1)) {
			query = query.substring(0, query.length() - 1);
		}
		else {
			query = query.substring(0, pos) + query.substring(pos + 1);
		}

		return query;
	}
}
