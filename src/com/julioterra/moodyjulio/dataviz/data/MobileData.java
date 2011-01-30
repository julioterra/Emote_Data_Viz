package com.julioterra.moodyjulio.dataviz.data;

import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;

public class MobileData extends Data{

	// core database values attributes
	public int gsr;
	public int gsr_filtered;
	public int heart_rate;
	public String emotion; 
	public String gps_status;
	public float longitude; 
	public String long_orient; 
	public float latitude;
	public String lat_orient;

	
	public MobileData(String gsr, String heartRate, String emotion, String timeStamp, String dateStamp, String gpsStatus, String longitude, 
					  String longOrient, String latitude, String latOrient) {
		super();
		this.setAll(gsr, heartRate, emotion, timeStamp, dateStamp, gpsStatus,
					longitude, longOrient, latitude, latOrient);
	}

	public MobileData(String[] new_data) {
		super();
		this.setAll(new_data[0], new_data[1], new_data[2], new_data[3], new_data[4], new_data[5],
				new_data[6], new_data[7], new_data[8], new_data[9]);
	}

	public MobileData(MobileData new_data) {
		super();
		this.gsr = new_data.gsr;
		this.gsr_filtered = new_data.gsr_filtered;
		this.heart_rate = new_data.heart_rate;
		this.emotion = new_data.emotion;
		this.gps_status = new_data.gps_status;
		this.longitude = new_data.longitude;
		this.long_orient = new_data.long_orient;
		this.latitude = new_data.latitude;
		this.lat_orient = new_data.lat_orient;
	}
	
	public MobileData() {
		super();
		this.gsr = 0;
		this.gsr_filtered = 0;
		this.heart_rate = 0;
		this.emotion = "0";
		this.gps_status = "0";
		this.longitude = 0;
		this.long_orient = "0";
		this.latitude = 0;
		this.lat_orient = "0";
	}

	
	/*******************************
	 * GETTER AND SETTER FUNCTIONS FOR ALL MAIN CLASS ATTRIBUTES
	 */
	
	public void setAll(String gsr, String heartRate, String emotion,
			String timeStamp, String dateStamp, String gpsStatus, String longitude,
			String longOrient, String latitude, String latOrient) {

			this.gsr = Integer.parseInt(gsr);
			this.heart_rate = Integer.parseInt(heartRate);
			this.emotion = emotion;
			this.date_stamp = new Date(dateStamp);
			this.time_stamp = new Time(timeStamp);

			this.gps_status = gpsStatus;
			this.longitude = Float.parseFloat(longitude);
			this.long_orient = longOrient;
			this.latitude = Float.parseFloat(latitude);
			this.lat_orient = latOrient;
	
	}
	
	public int getGsr() {
		return gsr;
	}

	public void setGsr(int gsr) {
		this.gsr = gsr;
	}

	public int setGsrHighPass() {
		return this.gsr_filtered;
	}

	public void setGsrHighPass(int gsr) {
		this.gsr_filtered = gsr;
	}

	public int getHeartRate() {
		return heart_rate;
	}

	public void setHeartRate(int heartRate) {
		this.heart_rate = heartRate;
	}
	
	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public String getGpsStatus() {
		return gps_status;
	}

	public void setGpsStatus(String gpsStatus) {
		this.gps_status = gpsStatus;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getLongOrient() {
		return long_orient;
	}

	public void setLongOrient(String longOrient) {
		this.long_orient = longOrient;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getLatOrient() {
		return lat_orient;
	}

	public void setLatOrient(String latOrient) {
		this.lat_orient = latOrient;
	}	

	public String getString() {
		return "start date " + this.date_stamp.get_date_in_string() + " start time " + this.time_stamp.get_time_in_string() + " gsr " + this.gsr + " gsr high pass " + this.gsr_filtered +
				" heart rate " + this.heart_rate + " emotion " + this.emotion;
	}

}
