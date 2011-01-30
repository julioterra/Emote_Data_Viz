package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.data.Data;
import com.julioterra.moodyjulio.dataviz.data.MobileData;
import com.julioterra.moodyjulio.dataviz.data.PieHeartData;

import processing.core.PApplet;

public class DataProcessorHeartrate extends DataProcessor {
	
	public DataProcessorHeartrate(){
		super();
		this.data_type_raw = MOBILE;
	}

	public void scrollDays(int direction){
		this.date_range_start.update_day(direction); 
		this.date_range_end.update_day(direction); 
		PApplet.println("scroll days start " + date_range_start.get_date_for_sql() + " " + time_range_start.get_time_for_sql());
		createProcessedDataList_usingDateRange(DataVizElement.HEART_RATE, date_range_start, time_range_start, date_range_end, time_range_end, 4);
		process();
		load_2_database();
	}


	public void process() {

		if (debug_code) PApplet.println("** HEARTRATE DATA PROCESSOR ** PROCESS DATA LIST W RAW HEART RATE DATA " + this.data_list_processed.size());
		for (int i = 0; i < data_list_processed.size(); i++) {
			PieHeartData current_heart_data = (PieHeartData) data_list_processed.get(i);
			ArrayList<Data> current_mobile_data = load_date_and_time_range(MOBILE, current_heart_data.date_stamp, current_heart_data.time_stamp, 
															        current_heart_data.date_end, current_heart_data.time_end);
			// go through each element in the pie_data array to calculate the slice information
			float heartrate_avg_cur = 0;
			float heartrate_avg_total = 0;			
			float gsr_avg_cur = 0;
			float gsr_avg_total = 0;	
			float gsr_filtered_avg_cur = 0;
			float gsr_filtered_avg_total = 0;	
			String name;
			String description;
			String emotion = "";

			PApplet.println("** 	- time " + current_heart_data.date_stamp.get_date_for_sql() + " end " + current_heart_data.date_end.get_date_for_sql());
			if (current_mobile_data.size() > 20) {
				for (int j = 0; j < current_mobile_data.size(); j++) {
					MobileData current_mobile_record = (MobileData) current_mobile_data.get(j);
					
					heartrate_avg_cur = (float)current_mobile_record.heart_rate / (float)current_mobile_data.size();
					heartrate_avg_total += heartrate_avg_cur;
					gsr_avg_cur = (float)current_mobile_record.gsr / (float)current_mobile_data.size();
					gsr_avg_total += gsr_avg_cur;
					gsr_filtered_avg_cur = (float)current_mobile_record.gsr_filtered / (float)current_mobile_data.size();
					gsr_filtered_avg_total += gsr_filtered_avg_cur;

					if (!current_mobile_record.emotion.contains("\\w")) emotion = current_mobile_record.emotion;
					PApplet.println("** 	- heart rate " + heartrate_avg_cur + " total " + heartrate_avg_total);
					PApplet.println("**		- gsr " + gsr_avg_cur + " total " + gsr_avg_total);
	
				}
			}
			name = "" + heartrate_avg_total;
			description = "from " + current_heart_data.time_stamp.get_time_for_sql() + " to " + current_heart_data.time_end.get_time_for_sql();
			if (heartrate_avg_total == 0) name = "unplugged";
			current_heart_data.setName(name);
			current_heart_data.setDescription(description);
			current_heart_data.setGsr((int) gsr_avg_total);
			current_heart_data.setGsrHighPass((int) gsr_filtered_avg_cur);			
			current_heart_data.setHeartRate((int) heartrate_avg_total);
			current_heart_data.setEmotion(emotion);
			if (debug_code) PApplet.println("** HEARTRATE DATA END OF UPDATE " + current_heart_data.name + " - " + current_heart_data.description);
		}
	}
	
	// ************************************
	// LOAD TO DATABASE METHOD - USING DATA FROM RAW DATA ARRAY
	// data from Journal database and re-saving it to the Emotion Pie table
	// **
	public void load_2_database() {
		this.loading_data = true;

		String insert_format = "INSERT INTO " + database_name[HEART_RATE] + "\n";
		for (int i =  0; i < data_list_processed.size(); i++) {					
			PieHeartData current_reading = (PieHeartData) data_list_processed.get(i);
			String insert_data = "VALUES (\'"  + current_reading.date_stamp.get_date_for_sql() + "\', \'" + 
												 current_reading.time_stamp.get_time_for_sql() + "\', \'" +  
												 current_reading.date_end.get_date_for_sql() + "\', \'" +  
												 current_reading.time_end.get_time_for_sql() + "\', \'" +  
												 current_reading.heart_rate + "\', \'" + 
												 current_reading.gsr + "\', \'" +  
												 current_reading.gsr_filtered + "\', \'" + 
												 current_reading.name + "\', \'" +  
												 current_reading.description + "\', \'" +  
												 current_reading.emotion + "\')";

			if (debug_code) PApplet.println("** ready to upload data\n" + i + " " + insert_format + insert_data);					

			// *********************************************************************************************************
			// *********************************************************************************************************
			// ** ADD HERE FUNCTION THAT CHECKS IF DATE AND TIME OF THIS READING IS MORE RECENT THAN ELEMENT IN DATABASE
			// *********************************************************************************************************
			// *********************************************************************************************************

			if(database.connection != null && DataVizElement.data_load) {
				database.execute(insert_format + insert_data);
				if(debug_code) PApplet.println("** data uploaded to database");
			}
		}
		this.loading_data = false;
	}


}
