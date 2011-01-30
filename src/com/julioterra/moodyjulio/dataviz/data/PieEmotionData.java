package com.julioterra.moodyjulio.dataviz.data;

import com.julioterra.moodyjulio.dataviz.basicelements.*;

public class PieEmotionData extends Data {

	public float emotion_type;				// above 0 is positive, below 0 is negative emotions
	public float emotion_intensity;	
	public float people_number;
	public float main_location;	
	
	public String name;
	public String description;
	public String emotion;
	public String activity; 
	public String place;
	public String people;

	public Time time_end;
	public Date date_end;

	public boolean data_exists = false;
	

	/*******************************
	 ** CONSTRUCTOR FUNCTIONS
	 **/
	
	public PieEmotionData(String date_stamp, String time_stamp, String date_end,  String time_end, String emotion_type, String emotion_intensity,
						  String name, String description, String emotion, String activity, String people, String place) {
		super();
		this.setTime(new Date(date_stamp), new Time(time_stamp), new Date(date_end), new Time(time_end));
		this.setAllNonTime(Float.parseFloat(emotion_type), Float.parseFloat(emotion_intensity), name, description, emotion, activity, people, place);
	}

	public PieEmotionData(Date date_stamp, Time time_stamp, Date date_end,  Time time_end) {
		super();
		this.setTime(date_stamp, time_stamp, date_end, time_end);
		this.setAllNonTime(0, 0, "", "", "", "", "", "");
	}

	public PieEmotionData(String[] new_data) {
		super();
		this.setBasic(new Date(new_data[0]), new Time(new_data[1]), new Date(new_data[2]), new Time(new_data[3]), new_data[4], 
							 new_data[5], Float.parseFloat(new_data[6]), Float.parseFloat(new_data[7]));
	}

	public PieEmotionData(PieEmotionData new_data) {
		super();
		this.setTime(new_data.date_stamp, new_data.time_stamp, new_data.date_end, new_data.time_end);
		this.setAllNonTime(new_data.emotion_type, new_data.emotion_intensity, new_data.name, new_data.description, new_data.emotion, new_data.activity, new_data.people, new_data.place);
	}

	public PieEmotionData() {
		super();
		this.name = "";
		this.description = 	"";
		this.emotion_type = 0;
		this.emotion_intensity = 0;
		this.emotion = 	"";
		this.activity =	"";
		this.people = "";
		this.place = "";

	}

		/*******************************
	 ** GETTER AND SETTER FUNCTIONS FOR ALL MAIN CLASS ATTRIBUTES
	 **/
	
	public void setAllNonTime(float emotion_type, float emotion_intensity, String name, String description, String emotion, String activity, String people, String place) {
		this.emotion_type = 		emotion_type;
		this.emotion_intensity = 	emotion_intensity;
		this.name = 				name;
		this.description = 			description;
		this.emotion = 				emotion;
		this.activity =				activity;
		this.people = 				people;
		this.place = 				place;
		this.data_exists = 			true;
	}

	public void setAll(Date date_stamp, Time time_stamp, Date date_end, Time time_end, float emotion_type, float emotion_intensity, String name, String description, String emotion, String activity, String people, String place) {
		this.setTime(date_stamp, time_stamp, date_end, time_end);
		this.setAllNonTime(emotion_type, emotion_intensity, name, description, emotion, activity, people, place);
	}
	
	public void setBasic(Date date_stamp, Time time_stamp, Date date_end, Time time_end, String name, String description, float emotion_type, float emotion_intensity) {
		this.setTime(date_stamp, time_stamp, date_end, time_end);
		this.emotion_type = 		emotion_type;
		this.emotion_intensity = 	emotion_intensity;
		this.name = 				name;
		this.description = 			description;
		this.data_exists = 			true;
	}


	public void setTime(Date date_stamp, Time time_stamp, Date date_end, Time time_end) {
		this.date_stamp = 			new Date(date_stamp);
		this.date_end = 			new Date(date_end);
		this.time_stamp = 			new Time(time_stamp);
		this.time_end = 			new Time(time_end);
	}

	/*******************************
	 ** GETTER AND SETTER FUNCTIONS INDIVIDUAL CLASS ATTRIBUTES
	 **/


	public Time getTime_end() {
		return time_end;
	}

	public void setTime_end(Time time_end) {
		this.time_end = new Time(time_end);
	}

	public Date getDate_end() {
		return date_end;
	}

	public void setDate_end(Date date_end) {
		this.date_end = new Date(date_end);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getEmotionType() {
		return emotion_type;
	}

	public void setEmotionType(float emotion_type) {
		this.emotion_type = emotion_type;
	}
	
	public float getEmotionIntensity() {
		return emotion_intensity;
	}

	public void setEmotionIntensity(float emotion_intensity) {
		this.emotion_intensity = emotion_intensity;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public boolean isData_exists() {
		return data_exists;
	}

	public void setData_exists(boolean data_exists) {
		this.data_exists = data_exists;
	}

	
	public float getPeople_number() {
		return people_number;
	}

	public void setPeople_number(float people_number) {
		this.people_number = people_number;
	}

	public String getString() {
		return ("start date " + this.date_stamp.get_date_in_string() + " start time " + this.time_stamp.get_time_in_string() + 
				" date end " + this.date_end.get_date_in_string() + " time end " + this.time_end.get_time_in_string() +
				"\n name " + name + " description " + description + " emotion type " + emotion_type + " emotion strength " + emotion_intensity);
	}
	
	public PieEmotionData copy() {
		PieEmotionData new_data = new PieEmotionData();
		new_data.setAll(this.date_stamp, this.time_stamp, this.date_end, this.time_end, this.emotion_type, this.emotion_intensity, 
				this.name, this.description, this.emotion, this.activity, this.people, this.place);
		return new_data;
	}
}
