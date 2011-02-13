package com.julioterra.moodyjulio.dataviz.data;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;

import processing.core.PApplet;

public class Data extends DataVizElement {

	public Time time_stamp;
	public Date date_stamp;
	public Time time_end;
	public Date date_end;
	
	public Data () {
		this.time_stamp = new Time("0", "0", "0", "0");
		this.date_stamp = new Date("0", "0", "0");
		this.time_end = new Time("0", "0", "0", "0");
		this.date_end = new Date("0", "0", "0");
	}
	
	Data (String[] data_entry[]) {		
		this.time_stamp = new Time("0", "0", "0", "0");
		this.date_stamp = new Date("0", "0", "0");
		this.time_end = new Time("0", "0", "0", "0");
		this.date_end = new Date("0", "0", "0");
	}
	
	Data (Data data_entry) {		
		this.time_stamp = new Time(data_entry.time_stamp);
		this.date_stamp = new Date(data_entry.date_stamp);
		this.time_end = new Time("0", "0", "0", "0");
		this.date_end = new Date("0", "0", "0");
	}

	Data (Time time_stamp, Date date_stamp) {		
		this.time_stamp = new Time(time_stamp);
		this.date_stamp = new Date(date_stamp);
		this.time_end = new Time("0", "0", "0", "0");
		this.date_end = new Date("0", "0", "0");
	}

	Data (Time time_stamp, Date date_stamp, Time time_end, Date date_end) {		
		this.time_stamp = new Time(time_stamp);
		this.date_stamp = new Date(date_stamp);
		this.time_end = new Time(time_end);
		this.date_end = new Date(date_end);
	}

	public String getTimeStamp() {
		return this.time_stamp.get_time_with_millis_in_string();
	}

	public void setTimeStamp(String timeStamp) {
		this.time_stamp = new Time(timeStamp);
	}

	public void setTimeStamp(Time timeStamp) {
		this.time_stamp = new Time(timeStamp);
	}

	public String getDateStamp() {
		return this.date_stamp.get_date_in_string();
	}

	public void setDateStamp(String dateStamp) {
		this.date_stamp = new Date(dateStamp);
	}

	public void setDateStamp(Date dateStamp) {
		this.date_stamp = new Date(dateStamp);
	}

	public String getTimeEnd() {
		return this.time_end.get_time_with_millis_in_string();
	}

	public void setTimeEnd(String timeStamp) {
		this.time_end = new Time(timeStamp);
	}

	public void setTimeEnd(Time timeStamp) {
		this.time_end = new Time(timeStamp);
	}

	public String getDateEnd() {
		return this.date_end.get_date_in_string();
	}

	public void setDateEnd(String dateStamp) {
		this.date_end = new Date(dateStamp);
	}

	public void setDateEnd(Date dateStamp) {
		this.date_stamp = new Date(dateStamp);
	}

	public String getString() {
		return "";
	}

}
