package assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
    	// ADD YOUR CODE HERE
    	ArrayList<K> heapList=buildHeap(results);
		int L=heapList.size()-1; //1-L+1
		for(int j=1;j<=L-1;j++) {
			K temp = heapList.get(1);
			heapList.set(1, heapList.get(L+1-j));
			heapList.set(L+1-j,temp);
			downHeap(results,heapList,L-j);// sorting in decreasing way
	}
	heapList.remove(0);//remove the first one to get a complete arraylist

	return heapList;
}
    private static <K, V extends Comparable> ArrayList<K> upHeap(HashMap<K, V> results,ArrayList<K> heapList, int k){
    	int i=k;
    	while(i>1&&results.get(heapList.get(i)).compareTo(results.get(heapList.get(i/2)))<0) {
    		K temp=heapList.get(i);//swap element i and i/2;
    		heapList.set(i, heapList.get(i/2));
    		heapList.set(i/2, temp);
    		i=i/2;
    	}
    	return heapList;
    }
    private static <K, V extends Comparable> ArrayList<K> buildHeap(HashMap<K, V> results){
    	ArrayList<K> heapList=new ArrayList<K>();
		heapList.add(null);//the heap is starting from 1
		heapList.addAll(results.keySet());//build heap
		for(int k=0;k<heapList.size();k++) {
		upHeap(results,heapList,k);
		}
    	return heapList;
    }
    private static <K, V extends Comparable> ArrayList<K> downHeap(HashMap<K, V> results,ArrayList<K> heapList,int max){
	int i=1;
	while(2*i<=max) {
		int child=2*i;//find child
		if(child<max) {
			if(results.get(heapList.get(child+1)).compareTo(results.get(heapList.get(child))) <0) {
				child=child+1;//define larger child
			}
		}
		if(results.get(heapList.get(child)).compareTo(results.get(heapList.get(i))) <0) {
				K temp = heapList.get(i);//swap child with i
				heapList.set(i, heapList.get(child));
				heapList.set(child,temp);
				i=child;
			}else {
				break;
				}
			}return heapList;
		}
    }
