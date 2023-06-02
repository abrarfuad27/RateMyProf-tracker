package finalproject;


import java.util.ArrayList;


public class RatingDistributionBySchool extends DataAnalyzer {
	private MyHashTable<String,MyHashTable<String,Integer>> master;


	public RatingDistributionBySchool(Parser p) {
		super(p);
	}
	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		String uni = keyword.trim().toLowerCase();
		String[] uniNames = uni.split("\\s+");
		uni = "";
		for(int i=0;i<uniNames.length;i++){
			uni += uniNames[i];
			if(i< uniNames.length-1){
				uni += " ";
			}
		}
		return this.master.get(uni);
	}

	@Override
	public void extractInformation() {
		this.master = new MyHashTable<String,MyHashTable<String,Integer>>(); // MAIN HASHTABLE
		int ratingIndex = this.parser.fields.get("student_star");
		int profIndex = this.parser.fields.get("professor_name");
		int uniIndex = this.parser.fields.get("school_name");

		MyHashTable<String,MyHashTable<String,ArrayList<Double>>> tracker = new MyHashTable<String,MyHashTable<String,ArrayList<Double>>>();
		for(String[] entry: this.parser.data){
			String uniName = entry[uniIndex].trim().toLowerCase();
			String[] uniNames = uniName.split("\\s+");
			uniName = "";
			for(int i=0;i<uniNames.length;i++){
				uniName += uniNames[i];
				if(i<uniNames.length-1){
					uniName += " ";
				}
			}

			String profName = entry[profIndex].trim().toLowerCase();
//			String[] profNames =  profName.split("\\s+");
//			profName = "";
//			for(int i=0;i<profNames.length;i++){
//				profName += profNames[i].trim().toLowerCase();
//				if(i<profNames.length-1){
//					profName += " ";
//				}
//			}
			double rating = Double.parseDouble(entry[ratingIndex]);
			if(tracker.get(uniName) == null){
				ArrayList<Double> array = new ArrayList<Double>(2);
				array.add(1.0);
				array.add(rating);
				MyHashTable<String,ArrayList<Double>> table  = new MyHashTable<String,ArrayList<Double>>();
				table.put(profName,array);
				tracker.put(uniName,table);
			}
			else if(tracker.get(uniName) != null){
				if(tracker.get(uniName).get(profName) == null){
					ArrayList<Double> array = new ArrayList<Double>(2);
					array.add(1.0);
					array.add(rating);
					tracker.get(uniName).put(profName,array);
				}
				else if (tracker.get(uniName).get(profName) != null){
					tracker.get(uniName).get(profName).set(0, tracker.get(uniName).get(profName).get(0)+1.0);
					tracker.get(uniName).get(profName).set(1, tracker.get(uniName).get(profName).get(1)+rating);
				}
			}
		}
		for (String uniName : tracker.getKeySet()) {
			MyHashTable<String, Integer> profTable = new MyHashTable<String, Integer>();
			for (String profName : tracker.get(uniName).getKeySet()) {
				ArrayList<Double> profData = tracker.get(uniName).get(profName);
				double numReviews =profData.get(0);
				double totalRating = (double)profData.get(1);
				double avgRating = totalRating / numReviews;
				avgRating = Math.round(avgRating * 100.0) / 100.0;
				profTable.put(profName + "\n" + avgRating,(int)numReviews);
			}
			this.master.put(uniName, profTable);
		}



	}
}
