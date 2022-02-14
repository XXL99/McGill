import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class QueryTest {
	private static String[] nts = new String[] {"A", "C", "G", "T"};

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Usage: java QueryTest sequence_file probability_file");
			System.exit(-1);
		}
		
		String sequence = readSequence(args[0]);
		//System.out.println("sequence length: " + sequence.length());
		
		List<Map<String, Double>> probMapList = generateProbMapList(sequence, readProb(args[1]));
		
		//System.out.println("prob length: " + ppp.size());

		QueryGenerator qg = new QueryGenerator(sequence);
		
		String newDatabase = createNewDatabase(probMapList, sequence);
		//System.out.println("new db is ready");
		
		int w = 11; // search length
		int scoreThreshold = 10; // score threshold

		QueryEvaluation eval = new QueryEvaluation(probMapList, newDatabase, w, scoreThreshold);
		
		int numOfQueries = 10;
		int queryLength = 100;
		double errorRate = 0.01; // insert random character and delete random one
		int accuracy = 0;
		int seqNo = 1;
		Map<String, Integer> queries = qg.createQueries(numOfQueries, queryLength, errorRate);
		for (Map.Entry<String, Integer> query : queries.entrySet()) {
			System.out.println("Finding " + seqNo++ + " => "+ query.getKey());
			QueryEvaluation.MatchIndex result = eval.evaluate(query.getKey());
			if (result == null) continue;
			int startPos = query.getValue();
			System.out.println(startPos + " - " + result.toString());
			if (startPos >= result.getDbStart() - 50 && startPos < result.getDbStart() + 50) {
				accuracy++;
			}
		}
		System.out.println("Accuracy: " + 100.0*accuracy/numOfQueries + "%");
	}

	private static String readSequence(String filename) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String line : Files.readAllLines(Paths.get(filename))) {
			sb.append(line.trim());
		}
		
		return sb.toString();
	}
	
	private static List<Double> readProb(String filename) throws Exception {
		List<Double> ppp = new ArrayList<>();
		for (String line : Files.readAllLines(Paths.get(filename))) {
			String[] numbers = line.trim().split("\\s+");
			for (String a : numbers) {
				if (a != null && !a.isEmpty()) {
					try {
						ppp.add(Double.parseDouble(a));
					}
					catch(Exception e) {
						// ignore non-number
					}
				}
			}
		}
		return ppp;
	}
	
	private static List<Map<String, Double>> generateProbMapList(String sequence, List<Double> ppp) {
		List<Map<String, Double>> probMapList = new ArrayList<>();
		for (int i = 0; i < sequence.length(); i++) {
			char ch = sequence.charAt(i);
			double p = ppp.get(i);
			double p1 = (1 - p) / 3;
			Map<String, Double> m = new HashMap<>();
			for (String s : nts) {
				m.put(s, p1);
			}
			if (ch == 'A') {
				m.put("A", p);
			}
			else if (ch == 'C') {
				m.put("C", p);
			}
			else if (ch == 'G') {
				m.put("G", p);
			}
			else if (ch == 'T') {
				m.put("T", p);
			}
			probMapList.add(m);
		}
		return probMapList;
	}
	
	private static String createNewDatabase(List<Map<String, Double>> probMapList, String sequence) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		NavigableMap<Double, String> map = new TreeMap<Double, String>();
		for (Map<String, Double> m  : probMapList) {
			map.clear();
			double total = 0;
			for (String k : m.keySet()) {
				total += m.get(k);
				map.put(total, k);
			}
			double value = random.nextDouble() * total;
			sb.append(map.ceilingEntry(value).getValue());
		}
		
		return sb.toString();
	}
}
