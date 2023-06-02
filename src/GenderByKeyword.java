package finalproject;


public class GenderByKeyword extends DataAnalyzer {
	private MyHashTable<String,MyHashTable<String,Integer>> master;


	public GenderByKeyword(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		String output = keyword.trim().toLowerCase();
		return this.master.get(output);

	}

	@Override
	public void extractInformation() {
		this.master = new MyHashTable<String,MyHashTable<String,Integer>>();
		int genderIndex = this.parser.fields.get("gender");
		int commentIndex = this.parser.fields.get("comments");
		for(String[] entry : this.parser.data){
			String gender = entry[genderIndex].trim().toUpperCase();
			String comment = entry[commentIndex].trim().toLowerCase();
			String[] words = comment.replaceAll("[^a-z']", " ").split("\\s+");
			MyHashTable<String,Integer> wordFreqTracker = new MyHashTable<String,Integer>();
			for(String word : words) {
				if (word.equals("") || word.equals("'")) {
					continue;
				}
				if (wordFreqTracker.get(word) == null) {
					wordFreqTracker.put(word, 1);
				}
			}
			for(String word : wordFreqTracker.getKeySet()){
				if(this.master.get(word) == null){
					MyHashTable<String,Integer> table = new MyHashTable<String,Integer>();
					table.put("F",0);
					table.put("M",0);
					table.put("X",0);
					table.put(gender,1);
					this.master.put(word,table);
				}
				else if (this.master.get(word) != null){
					this.master.get(word).put(gender,this.master.get(word).get(gender)+1);
				}
			}


		}
	}

}
