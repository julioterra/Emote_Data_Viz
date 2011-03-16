package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;
import processing.core.PApplet;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.*;

public class DataProcessor extends DataVizElement{

	ArrayList <Data> data_list_raw;
	public int data_type_raw;
	ArrayList <Data> data_list_processed;
	public int data_type_processed;
	
	public boolean ready_to_start;			// flags master start
	public boolean reading_data;			// flags part of cycle
	public boolean processing_data;
	public boolean loading_data;
	
	Date date_range_start; 
	Time time_range_start; 
	Date date_range_end; 
	Time time_range_end;
	

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
		this.date_range_start = 		new Date (); 
		this.time_range_start = 		new Time (); 
		this.date_range_end =		 	new Date (); 
		this.time_range_end =		 	new Time ();

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
				default: break;
			}
		this.add(data_type, new_entry);
		return false;
	}

	public boolean add(int source_type, Data data_entry) {
		if (source_type == JOURNAL || source_type == MOBILE) {
			this.data_list_raw.add(data_entry);
			return true;
		} else if (source_type == EMOTION || source_type == HEART_RATE) {
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
	// **	
	public void createProcessedDataList_usingDateRange(int datatable_number, Date start_date, Time start_time, Date date_end, Time time_end, float interval_minutes) {
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
		
		if (debug_code) PApplet.println("beggining of created processed list " + this.date_range_start.get_date_for_sql() + " " +this.date_range_end.get_date_for_sql());

		while (!done_reading) {
			// calculate the time of the end of each sequence
			end_of_day = temp_time_end.update_minutes((int) interval_minutes); 
			if (end_of_day != 0) temp_day_end.update_day(1);

			// current this methods only supports creating arrays using emotion objects
			if (datatable_number == EMOTION) {
				PieEmotionData new_emotion_reading = new PieEmotionData(temp_day_start, temp_time_start, temp_day_end, temp_time_end);
				this.add(datatable_number, new_emotion_reading);				
				if (debug_code) PApplet.println("DATA PROCESSOR - ADD new EMOTION object to array [pie-data-processor] " + valid_readings + " " + new_emotion_reading.date_stamp.get_date_in_string() + " " + new_emotion_reading.time_stamp.get_time_in_string() + " -- " +  new_emotion_reading.date_end.get_date_in_string() + " " + new_emotion_reading.time_end.get_time_in_string());
			}
			else if (datatable_number == HEART_RATE) {
				PieHeartData new_heart_reading = new PieHeartData(temp_day_start, temp_time_start, temp_day_end, temp_time_end);
				this.add(datatable_number, new_heart_reading);				
				if (debug_code) PApplet.println("ADD new HEART object to array [pie-data-processor] " + valid_readings + " " + new_heart_reading.date_end.get_date_in_string() + " " + new_heart_reading.time_end.get_time_in_string());			
			}

			//check if we have reached the end of the time range
			if (this.date_range_end.equals(temp_day_start)) {
				if (debug_code) PApplet.println("difference " + this.date_range_end.equals(temp_day_end) + " " + Time.calculate_time_dif_seconds(temp_time_end, this.time_range_end));
				if (Time.calculate_time_dif_seconds(temp_time_end, this.time_range_start) < (60*interval_minutes)) done_reading = true;						
			}
			temp_time_start.set(temp_time_end);
			temp_day_start.set(temp_day_end);

			valid_readings++;
			if (debug_code) PApplet.println("valid readings " + valid_readings);
		}		
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
		String query_str = "SELECT * FROM "  + database_name[data_table_number] + 
		 					" WHERE date_stamp >= \"" + date_range_start.get_date_in_string() + 
		 					"\" AND date_stamp <= \"" + date_range_end.get_date_in_string() + "\"";

		if(DataVizElement.data_read && database.connection != null) {
			database.query(query_str);
			new_list = read_query_results(data_table_number);
		}
		return new_list;
	}

	
	public void create_end_date_time() {
		this.data_list_raw = load_date_and_time_range(JournalData, new Date("2011-02-21"), new Time("00:00:00"), new Date("2011-03-05"), new Time("23:59:59"));

		for (int i = 0; i < data_list_raw.size(); i++) {
			JournalData current_record = (JournalData) data_list_raw.get(i);
			// if this is not the last element in the array check the next entry to see if is within the expected timeframe
			if (i < data_list_raw.size()-1) {
				JournalData bar_end_record = new JournalData((JournalData) data_list_raw.get(i+1));
				if (Time.calculate_time_dif_seconds(current_record.time_stamp, bar_end_record.time_stamp) < DataVizElement.EmotionAgeThresholdSeconds) {
					bar_end_record.time_stamp.update_seconds(-1);
					current_record.date_end = new Date (bar_end_record.date_stamp);
					current_record.time_end = new Time (bar_end_record.time_stamp);
				} else {
					current_record.date_end = new Date (current_record.date_stamp);
					current_record.time_end = new Time (current_record.time_stamp);
					current_record.date_end.update_day(current_record.time_end.update_seconds((int)DataVizElement.EmotionAgeThresholdSeconds));					
				}
			} 
			// if this is the last element on the list, or if the next element on the list is more than 2 hours away then set the time appropriately
			else {
				current_record.date_end = new Date (current_record.date_stamp);
				current_record.time_end = new Time (current_record.time_stamp);
				current_record.date_end.update_day(current_record.time_end.update_hours(DataVizElement.EmotionAgeThresholdSeconds));
			}
		}

		// upload new data into database
		String insert_format = "UPDATE " + database_name[JournalData] + "\n";
		for (int j =  0; j < data_list_raw.size(); j++) {
			JournalData load_reading = (JournalData) data_list_raw.get(j);
			String insert_data = "SET date_end = \'"  + load_reading.date_end.get_date_for_sql() + "\', time_end = \'"  + load_reading.time_end.get_time_for_sql() + "\'\n" +
				"WHERE date_stamp = \'" + load_reading.date_stamp.get_date_for_sql() + "\' AND time_stamp = \'" + load_reading.time_stamp.get_time_for_sql() + "\'"; 
			if (DataVizElement.debug_code) PApplet.println(insert_format + insert_data);					
			if(database.connection != null && DataVizElement.data_load) { 
				PApplet.println("data uploading");					
				database.execute(insert_format + insert_data);
			}
		}

	}
	
	// LOAD_DATE_TIME_RANGE - loads data from database into an array list of data objects.  
	// input: the number of the source data table, the start date and time, and the end date and time of the range to laod 
	// returns: an array list with data objects - specific class of objects will depend on database that was read		
	public static ArrayList<Data> load_date_and_time_range(int data_table_number, Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		String query_str = "SELECT * FROM "  + database_name[data_table_number] + 
						   " WHERE ( date_stamp >= \"" + date_range_start.get_date_for_sql() + 
						   "\" AND date_stamp <= \"" + date_range_end.get_date_for_sql() +
						   "\") AND ( time_stamp >= \"" + time_range_start.get_time_for_sql();
		if (time_range_end.hour >= time_range_start.hour) query_str += "\" AND time_stamp < \"" + time_range_end.get_time_for_sql() + "\")";
		else query_str += "\" OR time_stamp < \"" + time_range_end.get_time_for_sql() + "\")";

		if(DataVizElement.data_read && database.connection != null) {
			if (debug_code) PApplet.println("load date range " + query_str);
			database.query(query_str);			
			new_list = read_query_results(data_table_number);
		}
		return new_list;
	}

	// LOAD_DATE_TIME_RANGE - loads data from database into an array list of data objects.  
	// input: the number of the source data table, the start date and time, and the end date and time of the range to laod 
	// returns: an array list with data objects - specific class of objects will depend on database that was read		
	public static ArrayList<Data> load_date_and_time_range_end(int data_table_number, Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		String query_str = "SELECT * FROM "  + database_name[data_table_number] + 
						   " WHERE ((date_stamp >= \"" + date_range_start.get_date_for_sql() + 
						   "\" AND date_stamp <= \"" + date_range_end.get_date_for_sql() +
						   "\" ) OR ( date_end >= \"" + date_range_start.get_date_for_sql() + 
						   "\" AND date_end <= \"" + date_range_end.get_date_for_sql() +
						   "\")) AND ( time_stamp >= \"" + time_range_start.get_time_for_sql();
		if (time_range_end.hour >= time_range_start.hour) query_str += "\" AND time_stamp < \"" + time_range_end.get_time_for_sql() + "\")";
		else query_str += "\" OR time_stamp < \"" + time_range_end.get_time_for_sql() + "\")";

		if(DataVizElement.data_read && database.connection != null) {
			if (debug_code) PApplet.println("load date range " + query_str);
			database.query(query_str);			
			new_list = read_query_results(data_table_number);
		}
		return new_list;
	}

	// LOAD_DATE_TIME_RANGE - loads data from database into an array list of data objects.  
	// input: the number of the source data table, the start date and time, and the end date and time of the range to laod 
	// returns: an array list with data objects - specific class of objects will depend on database that was read		
	public static ArrayList<Data> load_multiple_date_time_range(int data_table_number, String[] date_array, Time time_range_start, Time time_range_end) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		String query_str = "SELECT * FROM "  + database_name[data_table_number] + " WHERE ( "; 
		for (int i = 0; i < date_array.length; i++) {
			if(i != 0) query_str += "\" OR "; 
			query_str += "date_stamp = \"" + date_array[i];
		}
		query_str += "\" ) AND ( time_stamp >= \"" + time_range_start.get_time_for_sql();
		if (time_range_end.hour >= time_range_start.hour) query_str += "\" AND time_stamp < \"" + time_range_end.get_time_for_sql() + "\")";
		else query_str += "\" OR time_stamp < \"" + time_range_end.get_time_for_sql() + "\")";

		if(DataVizElement.data_read && database.connection != null) {
//			if (debug_code) PApplet.println("load date range " + query_str);
			database.query(query_str);			
			new_list = read_query_results(data_table_number);
		}
		return new_list;
		
	}

	
	// LOAD_DATE - loads data from database into an array list of data objects.  
	// input: the number of the source data table, the date for the query 
	// returns: an array list with data objects - specific class of objects will depend on database that was read		
	public static ArrayList<Data> load_date(int data_table_number, Date date_selected) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		String query_str = "SELECT * FROM "  + database_name[data_table_number] + 
						   " WHERE date_stamp >= \"" + date_selected.get_date_in_string() + 
						   "\" AND date_stamp <= \"" + date_selected.get_date_in_string() + "\"";

		if (DataVizElement.data_read && database.connection != null) {
			database.query(query_str);			
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

//		PApplet.println("GOT TO READ QUERY - data_table_number " + data_table_name);

		if (data_table_name == JournalData) {
			while (database.next()) {
				JournalData most_recent = new JournalData(database.getString("time_stamp"), database.getString("date_stamp"),
//								"00:00:00", "2000:01:01",
								database.getString("time_end"), database.getString("date_end"),
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
				new_list.add(most_recent);
				count++;
			}
		}
		if (data_table_name == PieData_HeartRate) {
			while (database.next()) {
				PieHeartData most_recent = new PieHeartData(database.getString("gsr"), database.getString("heart_rate"), 
						database.getString("emotion"), database.getString("time_stamp"), database.getString("date_stamp"), 
						database.getString("date_end"), database.getString("time_end"));
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
			time_date_in_string = "0" + String.valueOf(current_time);
		} 
		return time_date_in_string;
	}
	
	// ************************************
	// ** APPLY LINE BREAKS
	// ** applies line breaks to text creating lines of a specific length
	// **
	public static String applyLineBreaks(String input_string, int chars_per_line) {
		String output_string = "";
		while (input_string.length() > chars_per_line) {
			int index = input_string.indexOf(" ", chars_per_line);
			if (index < 0) break;
			String temp_size = input_string.substring(index);
			if (temp_size.length() < 10) break;
			output_string += input_string.substring(0, index) + "\n";
			input_string = input_string.substring(index);
		}
		if (PApplet.trim(input_string).length() > 1) output_string += input_string + "\n";
		output_string = output_string.replace("\\", "");
		return output_string;
	}
	
}
