package finalproject;


public class RatingDistributionByProf extends DataAnalyzer {

	private MyHashTable<String,MyHashTable<String,Integer>> master;
    public RatingDistributionByProf(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		String profdetails = keyword.trim().toLowerCase();
		String[] names = profdetails.split("\\s+");
		profdetails = "";
		for(int i=0;i< names.length;i++){
			profdetails += names[i];
			if(i< names.length-1){
				profdetails += " ";
			}
		}
		return this.master.get(profdetails);
	}

	@Override
	public void extractInformation() {
		this.master = new MyHashTable<String,MyHashTable<String,Integer>>();
		int ratingIndex = this.parser.fields.get("student_star");
		int profIndex = this.parser.fields.get("professor_name");
		for(String[] entry : this.parser.data){
			int rating = (int)Double.parseDouble(entry[ratingIndex]);
			String profName = entry[profIndex].trim().toLowerCase();
			String[] names = profName.split("\\s+");
			profName = "";
			for(int i=0;i< names.length;i++){
				profName += names[i];
				if(i< names.length-1){
					profName += " ";
				}
			}
			if(this.master.get(profName) == null){
				MyHashTable<String,Integer> table = new MyHashTable<String,Integer>();
				table.put("1",0);
				table.put("2",0);
				table.put("3",0);
				table.put("4",0);
				table.put("5",0);
				table.put(""+rating,1);
				this.master.put(profName,table);
			}
			else if(this.master.get(profName) != null){
				this.master.get(profName).put(""+rating,this.master.get(profName).get(""+rating)+1);
			}
		}

	}

}
