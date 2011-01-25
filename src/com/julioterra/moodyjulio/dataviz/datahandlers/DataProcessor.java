package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;
import processing.core.PApplet;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.*;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie;

public class DataProcessor extends DataVizElement{

	ArrayList <Data> data_list_raw;
	public int data_type_raw;
	ArrayList <Data> data_list_processed;
	public int data_type_processed;
    public float radius;
	
	public boolean ready_to_start;			// flags master start
	public boolean reading_data;			// flags part of cycle
	public boolean processing_data;
	public boolean loading_data;
	
	/***************************
	 *** CONSTRUCTORS
	 ***************************/

	public DataProcessor() {
		super();
		this.init();
	}

	public void init() {
		this.data_list_raw = 			new ArrayList<Data>();
		this.data_list_processed = 		new ArrayList<Data>();
		this.ready_to_start = 			false;
		this.reading_data = 			false;
		this.processing_data = 			false;
		this.loading_data = 			false;
	}
	
	public void init(int data_type_raw) {
		this.data_type_raw = data_type_raw;
		this.init();
	}

	public boolean add(int data_type, String[] data_entry) {
		Data new_entry = new Data();
			switch (data_type) {
				case JOURNAL:
					new_entry = new JournalData(data_entry);				
					break;
				case MOBILE:
					new_entry = new MobileData(data_entry);								
					break;
				case EMOTION:
					new_entry = new PieEmotionData(data_entry);				
					break;
				case ACTIVITY:
					break;
				case PEOPLE:
					break;
				default: break;
			}
		this.add(data_type, new_entry);
		return false;
	}

	public boolean add(int data_type, Data data_entry) {
		if (data_type == JOURNAL || data_type == MOBILE) {
			this.data_list_raw.add(data_entry);
			return true;
		} else if (data_type == EMOTION) {
			this.data_list_processed.add(data_entry);
			return true;
		}
		return false;
	}

	public void process() {
	}
		
	public void load_2_database() {
	}
	
	// ************************************
	// CREATE_PROCESSED_DATA_LIST - USING A TIME RANGE
	// function that creates an array of emotion data objects based on a specified time window 
	public void createProcessedDataList_usingDateRange(Date start_date, Date date_end, Time start_time, Time time_end, float interval_hours) {		
	}

	// ************************************
	// UPDATE_PROCESSED_DATA_LIST - USING DATA FROM RAW DATA ARRAY
	// data from Journal database and re-saving it to the Emotion Pie table
	// **
	public void updateProcessedDataList_usingRawDataList() {
	}


	public void print() {
		PApplet.println("** RAW DATA ** total rows " + data_list_raw.size()); 
		for (int i = 0; i < data_list_raw.size(); i++) {
			Data current_data = (Data) data_list_raw.get(i);
			PApplet.println(current_data.getString()); 
		}
		
		PApplet.println("** PROCESSED DATA ** total rows " + data_list_processed.size()); 
		for (int i = 0; i < data_list_processed.size(); i++) {
			Data current_data = (Data) data_list_processed.get(i);
			PApplet.println(i + " reading - " + current_data.getString()); 
		}
	}

	/***************************
	 *** GET FUNCTIONS 
	 ***************************/
	
	public int get_size_raw() {
		return data_list_raw.size();
	}
	
	public Data get_raw(int index) {
		return data_list_raw.get(index);
	}

	public ArrayList<Data> get_raw() {
		return data_list_raw;
	}

	public int get_size_processed() {
		return data_list_processed.size();
	}
	
	public Data get_processed(int index) {
		return data_list_processed.get(index);
	}

	public ArrayList<Data> get_processed() {
		return data_list_processed;
	}

	/***************************
	 *** STATIC FUNCTIONS 
	 *********************
	 *************
	 *****
	 *** DATABASE QUERIES  
	 ***************************/
	
	// MOST_RECENT_DATE - finds out what is the most recent date in the database 
	// input: none
	// returns: most recent date from database		
	public static Date get_most_recent_date(int data_table_number) {
		Date most_recent = new Date();
		if(database.connection != null) {
			database.query("SELECT MAX(date_stamp) FROM " + database_name[data_table_number]);
			if(database.next()) {
				most_recent.setYMD(database.getString("MAX(date_stamp)"));
				if (DataVizElement.debug_code) PApplet.println(most_recent.get_date_in_string());
			}
		}
		return most_recent;
	}
	
	// MOST_RECENT_TIME - finds out what is the most recent time in the database 
	// input:  most recent date from database
	// returns: most recent time from the database		
	public static Time get_most_recent_time(int data_table_number, Date search_date) {
		Time most_recent = new Time();
		if(database.connection != null) {
			database.query("SELECT MAX(time_stamp) FROM "  + database_name[data_table_number] + " WHERE date_stamp = \'search_date\'");
			if(database.next()) {
				most_recent.set(database.getString("MAX(time_stamp)"));
				if (DataVizElement.debug_code) PApplet.println(most_recent.get_time_in_string());
			}
		}
		return most_recent;
	}

	// LOAD_DATE_RANGE - loads data from database into an array list of data objects.  
	// input: the number of the source data table, the start date and end date of the range to laod 
	// returns: an array list with data objects - specific class of objects will depend on database that was read		
	public static ArrayList<Data> load_date_range(int data_table_number, Date date_range_start, Date date_range_end) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		if(database.connection != null) {
			database.query("SELECT * FROM "  + database_name[data_table_number] + 
							 " WHERE date_stamp >= \"" + date_range_start.get_date_in_string() + 
							 "\" AND date_stamp <= \"" + date_range_end.get_date_in_string() + "\"");
			new_list = read_query_results(data_table_number);
		}
		return new_list;
	}

	// LOAD_DATE_TIME_RANGE - loads data from database into an array list of data objects.  
	// input: the number of the source data table, the start date and time, and the end date and time of the range to laod 
	// returns: an array list with data objects - specific class of objects will depend on database that was read		
	public static ArrayList<Data> load_date_and_time_range(int data_table_number, Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		if(database.connection != null) {
			String query_str = "SELECT * FROM "  + database_name[data_table_number] + 
							   " WHERE ( date_stamp >= \"" + date_range_start.get_date_for_sql() + 
							   "\" AND date_stamp <= \"" + date_range_end.get_date_for_sql() +
							   "\" ) AND ( time_stamp >= \"" + time_range_start.get_time_in_string();
			if (time_range_end.hour > time_range_start.hour) 
				query_str += "\" AND time_stamp < \"" + time_range_end.get_time_in_string() + "\")";
				else query_str += "\" OR time_stamp < \"" + time_range_end.get_time_in_string() + "\")";

			database.query(query_str);

			if (debug_code) PApplet.println(query_str);
			
			new_list = read_query_results(data_table_number);
		}
		return new_list;
	}
	
	// LOAD_DATE - loads data from database into an array list of data objects.  
	// input: the number of the source data table, the date for the query 
	// returns: an array list with data objects - specific class of objects will depend on database that was read		
	public static ArrayList<Data> load_date(int data_table_number, Date date_selected) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		if(database.connection != null) {
			database.query("SELECT * FROM "  + database_name[data_table_number] + 
							 " WHERE date_stamp >= \"" + date_selected.get_date_in_string() + 
							 "\" AND date_stamp <= \"" + date_selected.get_date_in_string() + "\"");
			new_list = read_query_results(data_table_number);
		}
		return new_list;
	}

	// LOAD_TIME_RANGE - loads data from database into an array list of data objects.  
	// input: the number of the source data table, the start and end time of the time range 
	// returns: an array list with data objects - specific class of objects will depend on database that was read		
	// note: the results will use include records from the same window time from multiple different days
	public static ArrayList<Data> load_time_range(int data_table_number, Time time_range_start, Time time_range_end) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		if(database.connection != null) {
			database.query("SELECT * FROM "  + database_name[data_table_number] + 
							 " WHERE time_stamp >= \"" + time_range_start.get_time_in_string() + 
							 "\" AND time_stamp <= \"" + time_range_end.get_time_in_string() + "\"");
			new_list = read_query_results(data_table_number);
		}
		return new_list;
	}

	// READ_QUERY_RESULTS - reads results from any query using the appropriate data type.  
	// input: the number of the source data table 
	// returns: an array list with data objects - specific class of objects will depend on database that was read		
	public static ArrayList<Data> read_query_results(int data_table_name) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		int count = 0;

		if (data_table_name == JournalData) {
			while (database.next()) {
				JournalData most_recent = new JournalData(database.getString("time_stamp"), database.getString("date_stamp"), 
								database.getString("emotion_L1"), database.getString("emotion_L2"),
								database.getString("emotion_L3"), database.getString("activity"), 
								database.getString("location"), database.getString("people"), 
								database.getString("description"));
				new_list.add(most_recent);
				count++;
			}
		}
		if (data_table_name == MobileData) {
			while (database.next()) {
				MobileData most_recent = new MobileData(database.getString("gsr"), database.getString("heart_rate"), 
						database.getString("emotion"), database.getString("time_stamp"), database.getString("date_stamp"), 
						database.getString("gps_status"), database.getString("longitude"), database.getString("long_orient"), 
						database.getString("latitude"), database.getString("lat_orient"));
				new_list.add(most_recent);
				count++;
			}
		}
		if (data_table_name == PieData_Emotion) {
			while (database.next()) {
				PieEmotionData most_recent = new PieEmotionData(database.getString("date_stamp"), database.getString("time_stamp"), 
						database.getString("date_end"), database.getString("time_end"), database.getString("emotion_number"), 
						database.getString("emotion_intensity"), database.getString("name"), database.getString("description"), 
						database.getString("emotion"), database.getString("activity"),
						database.getString("people"), database.getString("place"));
				PApplet.println("** read from data table method - " + database.getString("date_stamp") + " , "+ database.getString("time_stamp") + " - " +  most_recent.getString());
				new_list.add(most_recent);
				count++;

			}
		}
		return new_list;
	}

	/***************************
	 *** STATIC FUNCTIONS 
	 *********************
	 *************
	 *****
	 *** LIST PROCESSING METHODS  
	 ***************************/

	// JOIN LIST METHOD - Joins two strings that contain comma separate lists
	// input: two strings with command separated values
	// output: a string with all entries from both original lists, dedupped
	public static String joinLists(String base_string, String new_string) {
		String final_string = base_string + ", " + new_string;
		String[] temp_string_array = PApplet.trim(PApplet.split(final_string, ","));
		ArrayList<String> final_string_phrases = new ArrayList<String>();

		for (int i = 0; i < temp_string_array.length; i ++) {
			if (!temp_string_array[i].equals("")) {
				final_string_phrases.add(temp_string_array[i]);
			}
		}
		for (int i = final_string_phrases.size()-1; i >= 1; i --) {
			String current_word = final_string_phrases.get(i); 
			for (int j = i-1; j >= 0; j --) {
	 			if (current_word.equals(final_string_phrases.get(j))) final_string_phrases.remove(i);
			}
		}		
		if (final_string_phrases.size() > 0) {
			final_string = final_string_phrases.get(0);
			for (int i = 1; i < final_string_phrases.size(); i ++) {
				final_string += ", " + final_string_phrases.get(i);
			}
		}
		return final_string;
	}	

	// ************************************
	// ** JOIN LIST AND REPLACE STRING METHOD
	// ** Join two strings that contain comma separate lists, and replaces specified text.
	// ** the text to remove can be matched using Regex.
	// **
	public static String joinLists_People(String base_string, String new_string, String remove_string, String replace_string) {
		String updated_string = joinLists(base_string, new_string);
		updated_string = updated_string.replaceAll(remove_string, replace_string);
		return updated_string;
	}

	// ************************************
	// ** JOIN STRINGS AND REPLACE METHOD
	// ** Join two strings that contain comma separate lists, and replaces specified text
	// **
	public static int countListItems(String base_string) {
		String[] string_count = PApplet.split(base_string, ",");
		int count = string_count.length;
		if(base_string.contains("more")) count += 5;
		if(base_string.contains("many")) count += 5;
		return count;
	}

	// ************************************
	// ** CONVERT INT TO STRING FOR  TIME AND DATE (BASE-10)
	// ** Join two strings that contain comma separate lists, and replaces specified text
	// **
	public static String time_date_part_to_string(long current_time) {
		String time_date_in_string = String.valueOf(current_time);
		if (current_time < 10) {
			time_date_in_string = "0";
			time_date_in_string += String.valueOf(current_time);
		} 
		return time_date_in_string;
	}
	

	
}
