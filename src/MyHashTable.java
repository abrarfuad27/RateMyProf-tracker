package finalproject;

import java.util.*;


public class MyHashTable<K,V> implements Iterable<MyPair<K,V>>{
	// num of entries to the table
	private int size;
	// num of buckets 
	private int capacity = 16;
	// load factor needed to check for rehashing 
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<MyPair<K,V>>> buckets; 


	// constructors
	public MyHashTable() {
		this.size = 0;
		this.buckets = new ArrayList<LinkedList<MyPair<K,V>>>(this.capacity);
		for(int i =0;i<this.capacity;i++){
			this.buckets.add(new LinkedList<MyPair<K, V>>());
		}

	}

	public MyHashTable(int initialCapacity) {
		this.size = 0;
		this.capacity = initialCapacity;
		this.buckets = new ArrayList<LinkedList<MyPair<K,V>>>(this.capacity);
		for(int i =0;i<this.capacity;i++) {
			this.buckets.add(new LinkedList<MyPair<K, V>>());
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int numBuckets() {
		return this.capacity;
	}

	/**
	 * Returns the buckets variable. Useful for testing  purposes.
	 */
	public ArrayList<LinkedList< MyPair<K,V> > > getBuckets(){
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode())%this.capacity;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair
	 * to this HashTable. Expected average run time  O(1)
	 */

	public V put(K key, V value) {
		V output = null;
		int index = hashFunction(key);
		for(MyPair<K,V> pair : this.buckets.get(index)) { // roughly O(1)
			if (pair.getKey().equals(key)) {
				output = pair.getValue();
				pair.setValue(value);
			}
		}
		if(output == null){
			this.buckets.get(index).add(new MyPair<K,V>(key,value));
			this.size++;
			double curSize = (double) this.size;
			double curCap = (double) this.capacity;
			if(curSize/curCap > MAX_LOAD_FACTOR){
				rehash();
			}
		}
		return output;
	}




	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {
		V output = null;
		int index = hashFunction(key);
		for(MyPair<K,V> pair : this.buckets.get(index)){
			if(pair.getKey().equals(key)){
				output = pair.getValue();
			}
		}
		return output;
	}

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1) 
	 */
	public V remove(K key) {
		V output = null;
		int index = hashFunction(key);
		for(MyPair<K,V> pair : this.buckets.get(index)){
			if(pair.getKey().equals(key)){
				output = pair.getValue();
				this.buckets.get(index).remove(pair);
			}
		}
		return output;
	}




	/** 
	 * Method to double the size of the hashtable if load factor increases
	 * beyond MAX_LOAD_FACTOR.
	 * Made public for ease of testing.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */
	public void rehash() {
		ArrayList<LinkedList<MyPair<K,V>>> newBuckets = new ArrayList<LinkedList<MyPair<K,V>>>(this.capacity*2);
		for (int i = 0; i < this.capacity * 2; i++) {
			newBuckets.add(new LinkedList<MyPair<K, V>>());
		}
		this.capacity *= 2;
		// Rehash each element in the old table and put it in the new table
		for (MyPair<K, V> pair : this) {
			int newBucketIndex = hashFunction(pair.getKey());
			newBuckets.get(newBucketIndex).add(pair);
		}
		this.buckets = newBuckets;
	}


	/**
	 * Return a list of all the keys present in this hashtable.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> getKeySet() {
		ArrayList<K> keys = new ArrayList<K>(this.size);
		for (LinkedList<MyPair<K,V>> bucket : this.buckets) {
			for (MyPair<K,V> pair : bucket) {
				keys.add(pair.getKey());
			}
		}
		return keys;
	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> getValueSet() {
		ArrayList<V> values = new ArrayList<V>(this.size);
		MyHashTable<V,Boolean> tracker = new MyHashTable<V,Boolean>(); // can keys be mutable??
 		for (LinkedList<MyPair<K,V>> bucket : this.buckets) {
			for (MyPair<K,V> pair : bucket) {
				if(tracker.get(pair.getValue()) == null){
					tracker.put(pair.getValue(),true);
				}
			}
		}
		for(MyPair<V,Boolean> pair : tracker){
			values.add(pair.getKey());
		}
		return values;

	}

	public static void main(String[] args) {
		Random gen = new Random();
		//MyHashTable<Integer,Integer> map1 = new MyHashTable<Integer,Integer>(1000);

//		long startTime = System.currentTimeMillis();
//		for(int i=0; i<1000;i++){
//			map1.put(gen.nextInt(), gen.nextInt());
//		}
//		long endTime = System.currentTimeMillis();
//		long executionTime = endTime - startTime;
//		System.out.println("Execution time for 1000 entries: " + executionTime + " milliseconds");
//
//		MyHashTable<Integer,Integer> map2 = new MyHashTable<Integer,Integer>(10000);
//		startTime = System.currentTimeMillis();
//		for(int i=0; i<10000;i++){
//			map2.put(gen.nextInt(), gen.nextInt());
//		}
//		 endTime = System.currentTimeMillis();
//		 executionTime = endTime - startTime;
//		System.out.println("Execution time for 10000 entries: " + executionTime + " milliseconds");
//
//		MyHashTable<Integer,Integer> map3 = new MyHashTable<Integer,Integer>(100000);
//		startTime = System.currentTimeMillis();
//		for(int i=0; i<100000;i++){
//			map3.put(gen.nextInt(), gen.nextInt());
//		}
//		 endTime = System.currentTimeMillis();
//		 executionTime = endTime - startTime;
//		System.out.println("Execution time for 100000 entries: " + executionTime + " milliseconds");

		MyHashTable<Integer,Integer> map4 = new MyHashTable<Integer,Integer>(1000000);
		long startTime = System.currentTimeMillis();
		for(int i=0; i<100000;i++){
			map4.put(gen.nextInt(), gen.nextInt());
			map4.get(gen.nextInt());
			map4.remove(gen.nextInt());
		}
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		System.out.println(" MyHashTable Execution time for 100000 entries: " + executionTime + " milliseconds");

		HashMap<Integer,Integer> map5 = new HashMap<Integer,Integer>();
		startTime = System.currentTimeMillis();
		for(int i=0; i<100000;i++){
			map5.put(gen.nextInt(), gen.nextInt());
			map5.get(gen.nextInt());
			map5.remove(gen.nextInt());
		}
		endTime = System.currentTimeMillis();
		executionTime = endTime - startTime;
		System.out.println(" HashMap Execution time for 100000 entries: " + executionTime + " milliseconds");





	}


	/**
	 * Returns an ArrayList of all the key-value pairs present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<MyPair<K, V>> getEntries() {
		//ADD CODE BELOW HERE
		ArrayList<MyPair<K,V>> entries = new ArrayList<MyPair<K,V>>(this.size);
		for (LinkedList<MyPair<K,V>> bucket : this.buckets) {
			for (MyPair<K,V> pair : bucket) {
				entries.add(pair);
			}
		}
		return entries;



		//ADD CODE ABOVE HERE
	}
	
	
	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}   

	
	private class MyHashIterator implements Iterator<MyPair<K,V>> {
		private Iterator<MyPair<K, V>> currentIterator;

		private MyHashIterator() {
			this.currentIterator = MyHashTable.this.getEntries().iterator();

		}

		@Override
		public boolean hasNext() {
			return this.currentIterator.hasNext();
		}

		@Override
		public MyPair<K, V> next() { // need to ask if null should be returned
			MyPair<K, V> output = null;
			if (this.hasNext()) {
				output = this.currentIterator.next();
			}
			return output;
		}
	}
}
