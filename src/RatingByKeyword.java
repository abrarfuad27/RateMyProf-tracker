package finalproject;



public class RatingByKeyword extends DataAnalyzer {
	private MyHashTable<String,MyHashTable<String,Integer>> master;


	public RatingByKeyword(Parser p) {
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
		int ratingIndex = this.parser.fields.get("student_star");
		int commentIndex = this.parser.fields.get("comments");
		for(String[] entry : this.parser.data){
			String comment = entry[commentIndex].trim().toLowerCase();
			String[] words = comment.replaceAll("[^a-z']", " ").split("\\s+");
			int rating = (int)Double.parseDouble(entry[ratingIndex]);
			MyHashTable<String,Integer> wordFreqTracker = new MyHashTable<String,Integer>();
			for(String word : words){
				if(wordFreqTracker.get(word) == null){
					wordFreqTracker.put(word,1);
				}
			}
			for(String word : wordFreqTracker.getKeySet()){
				if(this.master.get(word) == null){
					MyHashTable<String,Integer> table = new MyHashTable<String,Integer>();
					table.put("1",0);
					table.put("2",0);
					table.put("3",0);
					table.put("4",0);
					table.put("5",0);
					table.put(""+rating,1);
					this.master.put(word,table);
				}
				else if (this.master.get(word) != null){
					this.master.get(word).put(""+rating,this.master.get(word).get(""+rating)+1);
				}
			}
		}

	}
}
