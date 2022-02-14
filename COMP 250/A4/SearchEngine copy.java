package assignment4;

import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		// TODO : Add code here
		
		ArrayList<String> linkedVertex=new ArrayList<String>();// list for storing liked urls
		
		if(internet.addVertex(url)) {
			internet.setVisited(url,true);//any added node, need to be set as visited
		}
			linkedVertex.addAll(parser.getLinks(url));//add all linked urls to this url
			for(String vertex: linkedVertex) {//check each url in the linkedVertex
				if(!internet.getVisited(vertex)) {
					crawlAndIndex(vertex);// call recursive steps
				}
				internet.addEdge(url, vertex);
			}
				
			for(int k=0;k<parser.getContent(url).size();k++) {//update wordIndex
				ArrayList<String> rightLinks=new ArrayList<String>();
				if(!linkedVertex.contains(url)) {
				linkedVertex.add(url);
				}
				for(int j=0;j<linkedVertex.size();j++) {
				if(parser.getContent(linkedVertex.get(j)).contains(parser.getContent(url).get(k))) {
					rightLinks.add(linkedVertex.get(j));
					}
				}
				wordIndex.put(parser.getContent(url).get(k),rightLinks);
			}
				
		}
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		// TODO : Add code here
		ArrayList<Double> pre=new ArrayList<Double>();
		ArrayList<Double> now=new ArrayList<Double>();
		ArrayList<String> vertices;
		int count=0;
		vertices=this.internet.getVertices();//all the vertices in the graph
		for(int i=0;i<vertices.size();i++) {
		internet.setPageRank(vertices.get(i), 1);//initialize all the ranks with 1
		pre.add(internet.getPageRank(vertices.get(i))); //initial orders
		}
		//1st literation;
		now.addAll(computeRanks(vertices));
		//literations;
		while(count!=vertices.size()) {
			count=0;
			for(int j=0;j<vertices.size();j++) {
				double diff=0;
				diff=Math.abs(now.get(j)-pre.get(j));
				if(diff<epsilon) {
					count++;
					}
				}
			for(int k=0;k<vertices.size();k++) {
				internet.setPageRank(vertices.get(k), now.get(k));
				}//reset vertices;
			if(count==vertices.size()) {
				break;
			}
			ArrayList<Double> temp=computeRanks(vertices);
				for(int f=0;f<vertices.size();f++) {//reset 
				pre.set(f, now.get(f));//reset pre
				now.set(f, temp.get(f));//reset now
				}
		}
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		// TODO : Add code here
		ArrayList<Double> ranks=new ArrayList<Double>();
		ArrayList<String> temp;
		double rank=0;
		
		int i=0;
		while(i<vertices.size())
		{
			double tempRank=0;
			temp=internet.getEdgesInto(vertices.get(i));//all vertices into this;
			for(int j=0;j<temp.size();j++) {
				double a=internet.getPageRank(temp.get(j));
				double b=internet.getOutDegree(temp.get(j));
				tempRank+=(a/b);//sum of all intoEdges' rank
			}
			rank=0.5+(0.5*tempRank);//calculate with formula 
			ranks.add(i,rank);
			i++;
		}		
		
		return ranks;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		// TODO: Add code here
		ArrayList<String> result=new ArrayList<String>();
		ArrayList<Integer> indexSorts;	
		outerloop:
		for(int s=0;s<internet.getVertices().size();s++) {
			for(int p=0;p<parser.getContent(internet.getVertices().get(s)).size();p++) {
				if(query.equalsIgnoreCase(parser.getContent(internet.getVertices().get(s)).get(p))) {
				//if the query exist in the graph without case sensitive
					ArrayList<String> linkList=wordIndex.get(query);
					int indexSort;
					HashMap<Integer, Integer> order = new HashMap<>();//create hashmap for ordering;
	
					if(wordIndex.isEmpty()==false) {
							for(int n=0;n<linkList.size();n++) {
								int rank=(int)(internet.getPageRank(linkList.get(n))*1000);
								order.put(n, rank);
							}//fill the hashmap with (rank & index)
							indexSorts=Sorting.fastSort(order);//sorted rank
							for(int m=0;m<indexSorts.size();m++) {
								indexSort=indexSorts.get(m);//find index of url from ordered rank;
								result.add(linkList.get(indexSort));//find url from vertices list;
								}	
							}
					break outerloop;
						}
					}
				}
			return result;//if query not in the web sites, return empty.
	}
}
