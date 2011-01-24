package com.julioterra.moodyjulio.dataviz.basicelements;

import com.julioterra.moodyjulio.dataviz.datahandlers.DataProcessor;

import processing.core.PApplet;

public class Date {
	public int year;
	public int month;
	public int day;
	
	public static int [] days_in_month = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	
	/*************************
	 ** CONSTRUCTOR
	 *************************/	

	public Date () {
		this.year = 0;
		this.month = 0;
		this.day = 0;
	}
	
	public Date(Date new_date){
		this.year = 	new_date.year;
		this.month = 	new_date.month;
		this.day =  	new_date.day;
	}
	
	public Date (String new_date) {
		this.setYMD(new_date);
	}
	
	public Date (String new_year, String new_month, String new_day) {
		this.set(new_year, new_month, new_day);
	}

	public Date (int new_year, int new_month, int new_day) {
		this.setInt(new_year, new_month, new_day);
	}

	
	/*************************
	 ** SET FUNCTIONS
	 *************************/	

	public void setYMD(String new_date){
		if (new_date.length() == 6) this.set(PApplet.trim(new_date.substring(0,4)), PApplet.trim(new_date.substring(4,6)), PApplet.trim(new_date.substring(6)));
		else if (new_date.length() > 6) this.set(PApplet.trim(new_date.substring(0,4)), PApplet.trim(new_date.substring(5,7)), PApplet.trim(new_date.substring(8)));
	}

	public void set(String new_year, String new_month, String new_day){
		if (new_year.length() <= 2) new_year = "20" + new_year;
		this.setInt(Integer.parseInt(new_year), Integer.parseInt(new_month), Integer.parseInt(new_day));
	}

	public void setInt(int new_year, int new_month, int new_day){
		this.year = new_year;
		this.month = new_month;
		this.day =  new_day;
		if (this.year < 1900) this.year += 2000;
		this.is_leap_year();
	}

	public void set(Date new_date){
		this.year = 	new_date.year;
		this.month = 	new_date.month;
		this.day =  	new_date.day;
	}
	
	
	/*************************
	 ** UPDATE FUNCTIONS
	 *************************/	

	// WIP WIP WIP
	// function currently working for single day updates. Need to create version that works for multiple day updates
	public void update_day(int day_delta) {
		this.day += day_delta;

		if (this.day <= 0) {			
			if (this.month > 1) { this.month -= 1; } 
			else { 
				this.month = 12; 
				this.year -= 1;
				this.is_leap_year();
			}
			this.day = Date.days_in_month[month-1];
		} 
		
		else if ((this.day > Date.days_in_month[month-1])) {
			if (this.month < 12) { this.month += 1; } 
			else { 
				this.month = 1; 
				this.year += 1;
				this.is_leap_year();
			}
			this.day = 1;
		}
	}	

	public Date calculateDate(int day_delta) {
		Date new_date = new Date(this.year, this.month, this.day); 
		new_date.update_day(day_delta);
		return new_date;
	}	
	
	/*************************
	 ** GET FUNCTIONS
	 *************************/	

	public String get_date_in_string() {		
		return String.valueOf(this.year) + DataProcessor.time_date_part_to_string(this.month) + DataProcessor.time_date_part_to_string(this.day);	
	}	

	public String get_date_for_sql() {		
		return String.valueOf(this.year) + "-" + DataProcessor.time_date_part_to_string(this.month) + "-" + DataProcessor.time_date_part_to_string(this.day);	
	}	
	
	/*************************
	 ** CALCULATE FUNCTIONS
	 *************************/	

	protected void is_leap_year() {
		if (Date.is_leap_year(this)) days_in_month[1] = 29;
		else days_in_month[1] = 28;
	}
	
	public static boolean is_leap_year(Date temp_date) {
		if (temp_date.year % 400 == 0) return true;
		else if (temp_date.year % 100 == 0) return false;
		else if (temp_date.year % 4 == 0) return true;
		return false;
	}
	
	public boolean equals(Date compare_date) {
		if (compare_date.year == this.year && compare_date.month == this.month && compare_date.day == this.day) return true;
		return false;
	}
	
	// WIP WIP WIP
	public static int calculate_date_dif_days(Date start_date, Date end_date) {		
		return 0;
	}
	
	public void calculate_dates(Date start_date, Time start_time, double millis_delta_per_step, int step_index) {
		this.year = start_date.year;
		this.month = start_date.month;
		this.day = start_date.day;

		if (start_time.new_day(start_time, millis_delta_per_step, step_index)) { this.update_day (1); }
	}

	
}
