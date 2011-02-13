package com.julioterra.moodyjulio.dataviz.data;

import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;

public class PieHeartData extends MobileData {

	public String name;
	public String description;

	public boolean data_exists = false;
	

	/*******************************
	 ** CONSTRUCTOR FUNCTIONS
	 **/
	public PieHeartData(String gsr, String heartRate, String emotion, String timeStamp, String dateStamp, String gpsStatus, String longitude, 
			  String longOrient, String latitude, String latOrient) {
		super(gsr, heartRate, emotion, timeStamp, dateStamp, gpsStatus, longitude, longOrient, latitude, latOrient);
	}

	public PieHeartData(String gsr, String heartRate, String emotion, String timeStamp, String dateStamp, String dateEnd, String timeEnd) {
		super(gsr, heartRate, emotion, timeStamp, dateStamp, "", "0", "", "0", "");
		setTime(new Date(dateStamp), new Time (timeStamp), new Date (dateEnd), new Time (timeEnd));
	}

	public PieHeartData(MobileData mobileData) {
		super(mobileData);
		if (mobileData instanceof PieHeartData) {
			this.date_end = new Date(((PieHeartData)mobileData).date_end);
			this.time_end = new Time(((PieHeartData)mobileData).time_end);
		}
	}

	public PieHeartData(String[] new_data) {
		super(new_data);	
	}

	public PieHeartData(Date date_stamp, Time time_stamp, Date date_end, Time time_end) {
		this.setTime(date_stamp, time_stamp, date_end, time_end);
	}

	public PieHeartData() {
		super();
	}

	public void setTime(Date _date_stamp, Time _time_stamp, Date _date_end, Time _time_end) {
		this.date_stamp = 			new Date(_date_stamp);
		this.time_stamp = 			new Time(_time_stamp);
		this.time_end = 			new Time(_time_end);
		this.date_end = 			new Date(_date_end);
	}
	
	/*******************************
	 ** COPY FUNCTION
	 **/	
	public PieHeartData copy() {
		return new PieHeartData(this);
	}
	
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

	/*******************************
	 ** GET STRING FUNCTION
	 **/	
	public String getString() {
		return  "start date " + this.date_stamp.get_date_in_string() + " start time " + this.time_stamp.get_time_in_string() + 
				" end date " + this.date_end.get_date_in_string() + " end time " + this.time_end.get_time_in_string() +
				" gsr " + this.gsr + " gsr high pass " + this.gsr_filtered +
				" heart rate " + this.heart_rate + " emotion " + this.emotion;
	}
	
}
