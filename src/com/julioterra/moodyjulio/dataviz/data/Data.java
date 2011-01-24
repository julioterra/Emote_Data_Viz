package com.julioterra.moodyjulio.dataviz.data;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;

import processing.core.PApplet;

public class Data extends DataVizElement {

	public Time time_stamp;
	public Date date_stamp;
	
	public Data () {
		this.time_stamp = new Time("0", "0", "0", "0");
		this.date_stamp = new Date("0", "0", "0");
	}
	
	Data (String[] data_entry[]) {		
		this.time_stamp = new Time("0", "0", "0", "0");
		this.date_stamp = new Date("0", "0", "0");
	}
	
	Data (Data data_entry) {		
		this.time_stamp = new Time("0", "0", "0", "0");
		this.date_stamp = new Date("0", "0", "0");
	}

	Data (Time time_stamp, Date date_stamp) {		
		this.time_stamp = new Time(time_stamp);
		this.date_stamp = new Date(date_stamp);
	}

	public String getTimeStamp() {
		return this.time_stamp.get_time_with_millis_in_string();
	}

	public void setTimeStamp(String timeStamp) {
		this.time_stamp.set(timeStamp);
	}

	public String getDateStamp() {
		return this.date_stamp.get_date_in_string();
	}

	public void setDateStamp(String dateStamp) {
		if (dateStamp.length() >=6) this.date_stamp.set(PApplet.trim(dateStamp.substring(4)), PApplet.trim(dateStamp.substring(2, 4)), PApplet.trim(dateStamp.substring(0, 2)) );
	}

	public String getString() {
		return "";
	}

}
