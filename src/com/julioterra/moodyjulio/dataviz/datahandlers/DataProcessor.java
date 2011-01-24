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
	Pie pie;

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
					new_entry = new DataPieEmotion(data_entry);				
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
	
	public int get_size() {
		return data_list_raw.size();
	}
	
	public Data get(int index) {
		return data_list_raw.get(index);
	}
	
	public ArrayList<Data> get() {
		return data_list_raw;
	}

	public int loadDataFromTable(int data_table_name) {
		int count = 0;
		if (data_table_name == JournalData) {
			this.data_type_raw = JOURNAL;
			while (database.next()) {
				JournalData most_recent = new JournalData(database.getString("time_stamp"), database.getString("date_stamp"), 
								database.getString("emotion_L1"), database.getString("emotion_L2"),
								database.getString("emotion_L3"), database.getString("activity"), 
								database.getString("location"), database.getString("people"), 
								database.getString("description"));
				this.add(JOURNAL, most_recent);
				count++;
			}
		}
		if (data_table_name == MobileData) {
			this.data_type_raw = MOBILE;
			while (database.next()) {
				MobileData most_recent = new MobileData(database.getString("gsr"), database.getString("heart_rate"), 
						database.getString("emotion"), database.getString("time_stamp"), database.getString("date_stamp"), 
						database.getString("gps_status"), database.getString("longitude"), database.getString("long_orient"), 
						database.getString("latitude"), database.getString("lat_orient"));
				this.add(MOBILE, most_recent);
				count++;
			}
		}
		if (data_table_name == PieData_Emotion) {
			this.data_type_processed = EMOTION;
			while (database.next()) {
				DataPieEmotion most_recent = new DataPieEmotion(database.getString("date_stamp"), database.getString("time_stamp"), 
						database.getString("date_end"), database.getString("time_end"), database.getString("emotion_number"), 
						database.getString("emotion_intensity"), database.getString("name"), database.getString("description"), 
						database.getString("emotion"), database.getString("activity"), database.getString("place"),
						database.getString("people"));
				this.add(EMOTION, most_recent);
				count++;
			}
		}
		return count;
	}



	// ************************************
	// CREATE_PROCESSED_DATA_LIST - USING A TIME RANGE
	// function that creates an array of emotion data objects based on a specified time window 
	public void createProcessedDL_wTimeRange(Date start_date, Date date_end, Time start_time, Time time_end, float interval_hours) {
		
	}

	// ************************************
	// UPDATE_PROCESSED_DATA_LIST - USING DATA FROM RAW DATA ARRAY
	// data from Journal database and re-saving it to the Emotion Pie table
	// **
	public void updateProcessedDL_wRawData() {
		
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

	public static ArrayList<Data> load_date_range(int data_table_number, Date date_range_start, Date date_range_end) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		if(database.connection != null) {
			database.query("SELECT * FROM "  + database_name[data_table_number] + 
							 " WHERE date_stamp >= \"" + date_range_start.get_date_in_string() + 
							 "\" AND date_stamp <= \"" + date_range_end.get_date_in_string() + "\"");
			new_list = getDataFromTable(data_table_number);
		}
		return new_list;
	}

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
			
			new_list = getDataFromTable(data_table_number);
		}
		return new_list;
	}
	
	public static ArrayList<Data> load_date(int data_table_number, Date date_selected) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		if(database.connection != null) {
			database.query("SELECT * FROM "  + database_name[data_table_number] + 
							 " WHERE date_stamp >= \"" + date_selected.get_date_in_string() + 
							 "\" AND date_stamp <= \"" + date_selected.get_date_in_string() + "\"");
			new_list = getDataFromTable(data_table_number);
		}
		return new_list;
	}

	public static ArrayList<Data> load_time_range(int data_table_number, Time time_range_start, Time time_range_end) {
		ArrayList<Data> new_list = new ArrayList<Data>();
		if(database.connection != null) {
			database.query("SELECT * FROM "  + database_name[data_table_number] + 
							 " WHERE time_stamp >= \"" + time_range_start.get_time_in_string() + 
							 "\" AND time_stamp <= \"" + time_range_end.get_time_in_string() + "\"");
			new_list = getDataFromTable(data_table_number);
		}
		return new_list;
	}
	public static ArrayList<Data> getDataFromTable(int data_table_name) {
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
				DataPieEmotion most_recent = new DataPieEmotion(database.getString("date_stamp"), database.getString("time_stamp"), 
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

	public static String time_date_part_to_string(long current_time) {
		String time_date_in_string = String.valueOf(current_time);
		if (current_time < 10) {
			time_date_in_string = "0";
			time_date_in_string += String.valueOf(current_time);
		} 
		return time_date_in_string;
	}
	

	
}
