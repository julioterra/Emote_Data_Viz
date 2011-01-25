package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;
import processing.core.PVector;
import processing.core.PApplet;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.*;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie;
import com.julioterra.moodyjulio.dataviz.shapes.pie.PieElement;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie_Arc;

public class PieDataProcessor extends DataProcessor {
	
	ArrayList<Data> pie_data;
	public Pie pie;
	public PVector location;
	
	Date date_range_start; 
	Time time_range_start; 
	Date date_range_end; 
	Time time_range_end;
	
	public PieDataProcessor(float x, float y, int radius){
		super();
		this.data_type_raw = JOURNAL;
		this.location = new PVector(x, y);
		this.radius = radius;
		this.pie = new Pie_Arc((int)location.x, (int) location.y, this.radius*2, PieElement.PIE_ARC_SET_RADIUS);
		this.pie_data = new ArrayList<Data>();
		
		date_range_start = new Date (); 
		time_range_start = new Time (); 
		date_range_end = new Date (); 
		time_range_end = new Time ();
	}

	public void process() {

	}

	public void scrollDays(int direction){
		this.date_range_start.update_day(direction); 
		this.date_range_end.update_day(direction); 
		PApplet.println("scroll days start " + date_range_start.get_date_for_sql() + " " + time_range_start.get_time_for_sql());

		loadPie(date_range_start, time_range_start, date_range_end, time_range_end);
	}
	
	public void loadPie(Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {
		
		pie = new Pie_Arc((int) location.x, (int) location.y, (float) radius, PieElement.PIE_ARC_SET_RADIUS);
		pie_data = load_date_and_time_range(EMOTION, date_range_start, time_range_start, date_range_end, time_range_end);
		pie.setName("my emotions");
		pie.setDescription("from " + date_range_start.get_date_for_sql() + " to " + date_range_end.get_date_for_sql());
		pie.setColorARGB(Cur_Background_Color);
		float angle_pie = 0;
		for (int i = 0; i < pie_data.size(); i++) {
			PieEmotionData pie_record = new PieEmotionData((PieEmotionData) pie_data.get(i));
			angle_pie = Time.calculate_time_dif_seconds(pie_record.time_stamp, pie_record.time_end);

			int cur_color = Cur_Background_Color;
			int s = (int) ( ( (pie_record.emotion_intensity/4.0)*0.4 + 0.4) * 255);
			if (pie_record.emotion_type > 0 ) cur_color = ShapeColor.colorHSB(150, s, 255);
			else if (pie_record.emotion_type < 0 ) cur_color = ShapeColor.colorHSB(0, s, 255);

			String name = convertEmotionToString(pie_record.emotion_type) + " mood, " +  convertEmotionIntensityToString(pie_record.emotion_intensity) + " intensity";
			String description = "from " + pie_record.time_stamp.get_time_for_sql() + " to " + pie_record.time_end.get_time_for_sql();
			if (pie_record.emotion_intensity == 0) name = "unplugged";
			else description += "\n" + "feeling " + pie_record.emotion.toLowerCase() + "\n" + "doing " + pie_record.activity + "\n" +
				 "at " + pie_record.place + "\n" + "with " + countListItems(pie_record.people) + " people";
				
			pie.addSlice(name, description, angle_pie, cur_color);
		}
		pie.setShiftMouseOverPie((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.0, false);
		pie.setShiftMouseOverSlices((float) 0.0, (float) -0.15, (float) 0.15, (float) 0.1, true);
		pie.textLocationNameDescription((float)(processing_app.width*0.6), (float)(processing_app.height*0.1), 0, 20);
		pie.textLocationNameDescriptionSlices((float)(processing_app.width*0.7), (float)(processing_app.height*0.5+60), 0, 20);
		pie.loadFontPie(3, 25);
		pie.loadFontSlices(3, 18);
		pie.applyValuesToSliceDisplay();
		pie.textSetVisibleNameLocation();
		PApplet.println("number of slices " + pie.slices.size() + "  " + angle_pie);

		this.date_range_start = new Date(date_range_start); 
		this.time_range_start = new Time(time_range_start);
		this.date_range_end = new Date(date_range_end);
		this.time_range_end = new Time(time_range_end);
	}
	
	public void display() {
	    pie.display();
	}

	
	// ************************************
	// CREATE_PROCESSED_DATA_LIST - USING A TIME RANGE
	// function that creates an array of emotion data objects based on a specified time window 
	// **
	public void createProcessedDataList_usingDateRange(int datatable_number, Date start_date, Date date_end, Time start_time, Time time_end, float interval_hours) {
		this.data_list_processed = new ArrayList<Data>();							// initialize the data_list_processed array
		this.time_range_start = 	new Time(start_time);
		this.date_range_start = 	new Date(start_date);
		this.time_range_end = 		new Time(time_end);
		this.date_range_end = 		new Date(date_end);
		this.valid_readings = 0;

		boolean done_reading = false;
		int end_of_day = 0;
		Time temp_time_start = 	new Time(start_time);
		Date temp_day_start = 	new Date(start_date);
		Time temp_time_end = 	new Time(temp_time_start);
		Date temp_day_end = 	new Date(temp_day_start);
		
		while (!done_reading) {
			// calculate the time of the end of each sequence
			end_of_day = temp_time_end.update_hours(1); 
			if (end_of_day != 0) { temp_day_end.update_day(1); }
			PieEmotionData new_emotion_reading = new PieEmotionData(temp_day_start, temp_time_start, temp_day_end, temp_time_end);

			// create new record using the appropriate time intervals
			this.add(datatable_number, new_emotion_reading);

			if (debug_code) 
				PApplet.println("CREATE ARRAY - object data " + valid_readings + " " + new_emotion_reading.date_stamp.get_date_in_string() + " " + new_emotion_reading.time_stamp.get_time_in_string() + " -- " +  new_emotion_reading.date_end.get_date_in_string() + " " + new_emotion_reading.time_end.get_time_in_string());

			//check if we have reached the end of the time range
			if (date_end.equals(temp_day_end)) 
				if (Time.calculate_time_dif_seconds(temp_time_end, time_end) < (3600.0*interval_hours)) done_reading = true;						
			temp_time_start.set(temp_time_end);
			temp_day_start.set(temp_day_end);
			valid_readings++;
		}		
	}
	
	// ************************************
	// UPDATE_PROCESSED_DATA_LIST - USING DATA FROM RAW DATA ARRAY
	// data from Journal database and re-saving it to the Emotion Pie table
	// **
	public void updateProcessedDataList_usingRawDataList() {
		if (debug_code) PApplet.println("** EMOTION DATA PROCESSOR ** PROCESS DATA LIST W RAW JOURNAL DATA " + this.data_list_processed.size());
		JournalData last_journal_entry = new JournalData();
		int last_journal_entry_age = 0;
		
		// loop through the data_list_processed array 
		for(int i = 0; i < this.data_list_processed.size(); i++) {
			if (debug_code) PApplet.println("* UPDATING ANOTHER EMOTION OBJECT *");

			// Get one pie emotion object from the array and query database for related JournalData entries
			PieEmotionData current_emotion_reading = (PieEmotionData) data_list_processed.get(i);
			ArrayList<Data> current_journal_entries = load_date_and_time_range(JOURNAL, current_emotion_reading.date_stamp, current_emotion_reading.time_stamp, 
															        current_emotion_reading.date_end, current_emotion_reading.time_end);

			// initialize all variables for the calculations
			float emotion_weighted_ave_cur = 0;
			float emotion_weighted_ave_total = 0;			
			float emotion_intensity_weighted_ave_cur = 0;
			float emotion_intensity_weighted_ave_total = 0;			
			float current_minute = 60;
			String people_text_remove = "no one,?(\\s)?";
			String people_text_replace = "";
			String emotion_text = "";
			String activity_text = "";
			String people_text = "";
			String place_text = "";
			
			// if there are one or more journal entries from the query then process them
			if (current_journal_entries.size() >  0) { 

				// go through each journal entry and capture data to calculate values for the emotion data entry
				for(int k = current_journal_entries.size() - 1; k >= 0 ; k--) {
					JournalData current_journal_entry = (JournalData) current_journal_entries.get(k);
					emotion_weighted_ave_cur = (float) (convertEmotionToFloat(current_journal_entry.emotion_L1) * ((current_minute - current_journal_entry.time_stamp.minute)/60.0) );
					emotion_weighted_ave_total += emotion_weighted_ave_cur;
					emotion_intensity_weighted_ave_cur = (float) (convertEmotionIntensityToFloat(current_journal_entry.emotion_L2) * ((current_minute - current_journal_entry.time_stamp.minute)/60.0) );
					emotion_intensity_weighted_ave_total += emotion_intensity_weighted_ave_cur;
					emotion_text = joinLists(emotion_text, (current_journal_entry.emotion_L2 + ", " + current_journal_entry.emotion_L3));
					activity_text = joinLists(activity_text, current_journal_entry.activity);
					people_text = joinLists_People(people_text, current_journal_entry.people, people_text_remove, people_text_replace);
					place_text = joinLists(place_text, current_journal_entry.location);
					
					current_minute = current_journal_entry.time_stamp.minute;
				}
				// if the most recent journal entry prior to the emotion entry is less than 3 hours old then extract data as appropriate
				if (last_journal_entry_age < DataVizElement.EmotionAgeThreshold) {
					emotion_weighted_ave_total += (float) (convertEmotionToFloat(last_journal_entry.emotion_L1) * (current_minute/60.0) );
					emotion_intensity_weighted_ave_total += (float) (convertEmotionIntensityToFloat(last_journal_entry.emotion_L2) * ((current_minute - last_journal_entry.time_stamp.minute)/60.0) );
					emotion_text = joinLists(emotion_text, (last_journal_entry.emotion_L2 + ", " + last_journal_entry.emotion_L3));
					activity_text = joinLists(activity_text, last_journal_entry.activity);
					people_text = joinLists_People(people_text, last_journal_entry.people, people_text_remove, people_text_replace);
					place_text = joinLists(place_text, last_journal_entry.location);
				}
				
				current_emotion_reading.setEmotionType((float)emotion_weighted_ave_total);
				current_emotion_reading.setEmotionIntensity((float)emotion_intensity_weighted_ave_total);
				current_emotion_reading.setName(convertEmotionToString(convertEmotionToFloat(last_journal_entry.emotion_L1)) + " mood, " +  convertEmotionIntensityToString(emotion_intensity_weighted_ave_total) + " intensity");
				current_emotion_reading.setEmotion(emotion_text);
				current_emotion_reading.setActivity(activity_text);
				current_emotion_reading.setPeople(people_text);
				current_emotion_reading.setPlace(place_text);
				current_emotion_reading.setDescription("from " + current_emotion_reading.time_stamp.get_time_for_sql() + " to " + current_emotion_reading.time_end.get_time_for_sql() + "\n" +
						   "feeling " + current_emotion_reading.emotion.toLowerCase() + "\n" +
						   "doing " + current_emotion_reading.activity + "\n" +
						   "at " + current_emotion_reading.place + "\n" +
						   "with " + countListItems(current_emotion_reading.people) + " people");

				last_journal_entry_age = 0;
				last_journal_entry = (JournalData) current_journal_entries.get(current_journal_entries.size()-1);			
				
			} else {
				if (last_journal_entry_age < EmotionAgeThreshold) {
					emotion_text = joinLists(emotion_text, (last_journal_entry.emotion_L2 + ", " + last_journal_entry.emotion_L3));
					activity_text = joinLists(activity_text, last_journal_entry.activity);
					people_text = joinLists_People(people_text, last_journal_entry.people, people_text_remove, people_text_replace);
					place_text = joinLists(place_text, last_journal_entry.location);

					current_emotion_reading.setEmotionType(convertEmotionToFloat(last_journal_entry.emotion_L1));
					current_emotion_reading.setEmotionIntensity(convertEmotionIntensityToFloat(last_journal_entry.emotion_L2));
					current_emotion_reading.setName(convertEmotionToString(convertEmotionToFloat(last_journal_entry.emotion_L1)) + " mood, " +  convertEmotionIntensityToString(convertEmotionIntensityToFloat(last_journal_entry.emotion_L2)) + " intensity");
					current_emotion_reading.setEmotion(emotion_text);
					current_emotion_reading.setActivity(activity_text);
					current_emotion_reading.setPeople(people_text);
					current_emotion_reading.setPlace(place_text);
					current_emotion_reading.setDescription("from " + current_emotion_reading.time_stamp.get_time_for_sql() + " to " + current_emotion_reading.time_end.get_time_for_sql() + "\n" +
							   "feeling " + current_emotion_reading.emotion.toLowerCase() + "\n" +
							   "doing " + current_emotion_reading.activity + "\n" +
							   "at " + current_emotion_reading.place + "\n" +
							   "with " + countListItems(current_emotion_reading.people) + " people");
				} else {

					current_emotion_reading.setName("unplugged");
					current_emotion_reading.setDescription("from " + current_emotion_reading.time_stamp.get_time_for_sql() + " to " + current_emotion_reading.time_end.get_time_for_sql());

				}

				last_journal_entry_age++;
			}

			if (debug_code) PApplet.println("PROCESS RAW DATA DATE " + current_emotion_reading.date_stamp.get_date_in_string());
			if (debug_code) PApplet.println("PROCESS RAW DATA TIME " + current_emotion_reading.time_stamp.get_time_in_string());
			if (debug_code) PApplet.println("PROCESS RAW DATA NAME " + current_emotion_reading.name);
			if (debug_code) PApplet.println("PROCESS RAW DATA EMOTION NUMBER " + current_emotion_reading.emotion_type);
			if (debug_code) PApplet.println("PROCESS RAW DATA EMOTION INTENSITY " + current_emotion_reading.emotion_intensity);
			if (debug_code) PApplet.println("PROCESS RAW DATA EMOTION TEXT " + current_emotion_reading.emotion);
			if (debug_code) PApplet.println("PROCESS RAW DATA ACTIVITY TEXT " + current_emotion_reading.activity);
			if (debug_code) PApplet.println("PROCESS RAW DATA PEOPLE TEXT " + current_emotion_reading.people);
			if (debug_code) PApplet.println("PROCESS RAW DATA PLACE TEXT " + current_emotion_reading.place);
			if (debug_code) PApplet.println("PROCESS RAW DATA DESCRIPTION TEXT " + current_emotion_reading.description);
		}
	}

	
	// ************************************
	// LOAD TO DATABASE METHOD - USING DATA FROM RAW DATA ARRAY
	// data from Journal database and re-saving it to the Emotion Pie table
	// **
	public void load_2_database() {
		this.loading_data = true;

			// checking the time of the last piece of data input into the database
			if(database.connection != null) {			
				String insert_format = "INSERT INTO " + database_name[EMOTION] + "\n";
				for (int i =  0; i < data_list_processed.size(); i++) {					
					PieEmotionData current_reading = (PieEmotionData) data_list_processed.get(i);
					String insert_data = "VALUES (\'"  + current_reading.date_stamp.get_date_for_sql() + "\', \'" + 
														 current_reading.time_stamp.get_time_for_sql() + "\', \'" +  
														 current_reading.date_end.get_date_for_sql() + "\', \'" +  
														 current_reading.time_end.get_time_for_sql() + "\', \'" +  
														 current_reading.emotion_type + "\', \'" + 
														 current_reading.emotion_intensity + "\', \'" +  
														 current_reading.name + "\', \'" + 
														 current_reading.description + "\', \'" +  
														 current_reading.emotion + "\', \'" +  
														 current_reading.activity + "\', \'" +  
														 current_reading.place + "\', \'" +  
														 current_reading.people + "\')";
					if (debug_code) PApplet.println("** ready to upload data\n" + insert_format + insert_data);					
	
					// *********************************************************************************************************
					// *********************************************************************************************************
					// ** ADD HERE FUNCTION THAT CHECKS IF DATE AND TIME OF THIS READING IS MORE RECENT THAN ELEMENT IN DATABASE
					// *********************************************************************************************************
					// *********************************************************************************************************

					if(database.connection != null && DataVizElement.load_data) {
						database.execute(insert_format + insert_data);
						if(debug_code) PApplet.println("** data uploaded to database");
					}
				}
			}
		this.loading_data = false;
	}
	
	
	// CONVERT EMOTION FROM STRING TO FLOAT - returns a strings that identifies if a number is positive or negative
	// input: emotion number (emotion_number from the EmotionPieData)
	// output: string that notes either positive or negative
	public static String convertEmotionToString(float emotion_number) {
		if(emotion_number > 0) return "positive";
		else if (emotion_number < 0) return "negative";			
		return "";
	}

	// CONVERT EMOTION INTENSITY FROM STRING TO FLOAT - returns a strings that identifies if a number is low, medium, elevated or high
	// input: emotion intensity number (emotion_intensity from the EmotionPieData)
	// output: string denotes intensity of emotion
	public static String convertEmotionIntensityToString(float emotion_intensity) {
		if (emotion_intensity < 1.0) return "low";
		if (emotion_intensity < 2.0) return "medium";
		if (emotion_intensity < 3.0) return "elevated";
		if (emotion_intensity <= 4.0) return "high";	
		return "";
	}
	
	// CONVERT EMOTIONS FROM STRING TO FLOAT
	// input: string of the current emotion (emotion_L1 from the JournalData)
	// output: 1 if string says "positive", -1 if string says "negative", or 0 if string does not contain either of those words
	public static float convertEmotionToFloat(String emotion_type) {
		emotion_type = emotion_type.toLowerCase();
		if(emotion_type.contains("positive")) return 1;
		else if (emotion_type.contains("negative")) return -1;			
		return 0;
	}

	// CONVERT EMOTIONS INTENSITY FROM STRING TO FLOAT
	// input: string of the current emotion intensity (emotion_L2 from the JournalData)
	// output: 1 if string says "positive", -1 if string says "negative", or 0 if string does not contain either of those words
	public static float convertEmotionIntensityToFloat(String emotion_type) {
		emotion_type = PApplet.trim(emotion_type.toLowerCase());
		if(emotion_type.contains("forceful") || emotion_type.contains("lively")) return 4;
		else if(emotion_type.contains("control") || emotion_type.contains("caring")) return 3;
		else if(emotion_type.contains("agitation") || emotion_type.contains("reactive")) return 2;
		else if(emotion_type.contains("thought")) return 2;
		else if(emotion_type.contains("passive") || emotion_type.contains("quiet") || emotion_type.contains("quite")) return 1;
		PApplet.println(" no match " + emotion_type);
		return 0;
	}

	
}
