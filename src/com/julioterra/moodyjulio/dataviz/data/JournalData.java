package com.julioterra.moodyjulio.dataviz.data;

public class JournalData extends Data {

	public String emotion_L1; 
	public String emotion_L2;
	public String emotion_L3; 
	public String activity; 
	public String location;
	public String people;
	public String description;
	

	/*******************************
	 ** CONSTRUCTOR FUNCTIONS
	 **/
	
	public JournalData(String timeStamp, String dateStamp, String emotion_L1, String emotion_L2, String emotion_L3,
			String activity, String location, String people, String description) {
		super();
		this.setAll(timeStamp, dateStamp, emotion_L1, emotion_L2, emotion_L3,
					activity, location, people, description);

	}

	public JournalData(String[] data_entry) {
		super();
		if (data_entry.length >= 9) {
			this.setAll(data_entry[0], data_entry[1], data_entry[2], data_entry[3], data_entry[4], data_entry[5], 
						data_entry[6],data_entry[7], data_entry[8]);
		}

	}

	public JournalData() {
		super();
		this.description = 	"";
		this.emotion_L1 = 	"";
		this.emotion_L2 = 	"";
		this.emotion_L3 = 	"";
		this.activity = 	"";
		this.location = 	"";
		this.people = 		"";
	}

	
	/*******************************
	 ** GETTER AND SETTER FUNCTIONS FOR ALL MAIN CLASS ATTRIBUTES
	 **/
	
	public void setAll(String timeStamp, String dateStamp, String emotion_L1, String emotion_L2, String emotion_L3,
			String activity, String location, String people, String description) {

			if (dateStamp.length() == 6) this.date_stamp.set(dateStamp.substring(0, 4), dateStamp.substring(4, 6), dateStamp.substring(6));
			if (dateStamp.length() >= 8) this.date_stamp.set(dateStamp.substring(0, 4), dateStamp.substring(5, 7), dateStamp.substring(8));
			if (timeStamp.length() >= 6) this.time_stamp.set(timeStamp.substring(0, 2), timeStamp.substring(3, 5), timeStamp.substring(6));

			this.emotion_L1 = 	emotion_L1;
			this.emotion_L2 = 	emotion_L2;
			this.emotion_L3 = 	emotion_L3;
			this.activity = 	activity;
			this.location = 	location;
			this.people = 		people;
			this.description = 	description;
	}

	/*******************************
	 ** GETTER AND SETTER FUNCTIONS INDIVIDUAL CLASS ATTRIBUTES
	 **/

	public String getEmotion_L1() {
		return emotion_L1;
	}

	public void setEmotion_L1(String emotion_L1) {
		this.emotion_L1 = emotion_L1;
	}

	public String getEmotion_L2() {
		return emotion_L2;
	}

	public void setEmotion_L2(String emotion_L2) {
		this.emotion_L2 = emotion_L2;
	}

	public String getEmotion_L3() {
		return emotion_L3;
	}

	public void setEmotion_L3(String emotion_L3) {
		this.emotion_L3 = emotion_L3;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getString() {
		return "start date " + this.date_stamp.get_date_in_string() + " start time " + this.time_stamp.get_time_in_string() + " description: " + this.description + 
			   " emotions " + this.emotion_L1 + " " + this.emotion_L2 + " " + this.emotion_L3 + " activity " + this.activity + 
			   " location " + this.location + " people " + this.people;
	}

	
}
