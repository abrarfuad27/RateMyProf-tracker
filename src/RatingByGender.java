package finalproject;


public class RatingByGender extends DataAnalyzer{
	private MyHashTable<String,MyHashTable<String,Integer>> qualityTable;
	private MyHashTable<String,MyHashTable<String,Integer>> difficultyTable;

	public RatingByGender(Parser p) {
		super(p);
	}




	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		MyHashTable<String,Integer> map = null;
		String gender = "";
		String type = "";
		String output = keyword.trim().toLowerCase();
		String[] words = output.replaceAll("[^a-z']", " ").split("\\s+");
		if(words.length != 2){
			return null;
		}
		for(String word: words){
			if(word.toUpperCase().equals("M") || word.toUpperCase().equals("F")){
				gender = word.toUpperCase();
			}
			if(word.equals("difficulty") || word.equals("quality")){
				type = word;
			}
		}
		if(type.equals("difficulty")){
			map = this.difficultyTable.get(gender);
		}
		if(type.equals("quality")){
			map = this.qualityTable.get(gender);
		}
		return map;
	}

	@Override
	public void extractInformation() {
		this.qualityTable = new MyHashTable<String,MyHashTable<String,Integer>>();
		this.difficultyTable = new MyHashTable<String,MyHashTable<String,Integer>>();
		int qualityIndex = this.parser.fields.get("student_star");
		int genderIndex = this.parser.fields.get("gender");
		int difficultyIndex = this.parser.fields.get("student_difficult");
		for(String[] entry : this.parser.data) {
			String gender = entry[genderIndex].trim().toUpperCase();
			int quality = (int) Double.parseDouble(entry[qualityIndex]);
			int difficulty = (int) Double.parseDouble(entry[difficultyIndex]);
			if (!gender.equals("M") && !gender.equals("F")) {
				continue;
			}
			if (this.qualityTable.get(gender) == null) {
				MyHashTable<String, Integer> table = new MyHashTable<String, Integer>();
				table.put("1", 0);
				table.put("2", 0);
				table.put("3", 0);
				table.put("4", 0);
				table.put("5", 0);
				table.put("" + quality, 1);
				this.qualityTable.put(gender, table);
			} else if (this.qualityTable.get(gender) != null) {
				this.qualityTable.get(gender).put("" + quality, this.qualityTable.get(gender).get("" + quality) + 1);
			}

			if (this.difficultyTable.get(gender) == null) {
				MyHashTable<String, Integer> table = new MyHashTable<String, Integer>();
				table.put("1", 0);
				table.put("2", 0);
				table.put("3", 0);
				table.put("4", 0);
				table.put("5", 0);
				table.put("" + difficulty, 1);
				this.difficultyTable.put(gender, table);
			} else if (this.difficultyTable.get(gender) != null) {
				this.difficultyTable.get(gender).put("" + difficulty, this.difficultyTable.get(gender).get("" + difficulty) + 1);
			}
		}
	}
}
