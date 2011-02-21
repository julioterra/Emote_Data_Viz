package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.Data;
import com.julioterra.moodyjulio.dataviz.data.PieEmotionData;
import com.julioterra.moodyjulio.dataviz.data.PieHeartData;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie_Line;

public class PieCreatorHeartrate extends PieCreator {

	public PieCreatorHeartrate(float x, float y, int diameter, int pie_slices, String name) {
		super(x, y, diameter);
		this.pie_slices = pie_slices;
		this.title = name;		

	} 

	public void loadDayOfWeek(int day_of_week) {
		pie_data = new ArrayList<Data>();
		Time temp_start_time = new Time(time_range_start);
		float time_dif = Time.calculate_time_dif_seconds(time_range_start, time_range_end)/pie_slices;
		Time temp_end_time = new Time(time_range_start);
		temp_end_time.update_seconds((int)time_dif);
		for(int i = 0; i < pie_slices; i ++) {
			pie_data.add(loadPieSlice(day_of_week, temp_start_time, temp_end_time));
			temp_start_time.update_seconds((int)time_dif);
			temp_end_time.update_seconds((int)time_dif);
		}
		loadPie();
	}
	
	private PieHeartData loadPieSlice(int day_of_week, Time time_range_start, Time time_range_end) {
		ArrayList<Data> temp_pie_data = load_multiple_date_time_range(PieData_HeartRate, DaysOfWeek.get(day_of_week), time_range_start, time_range_end);
		PieHeartData new_data = new PieHeartData(new Date("2000/01/01"), time_range_start, new Date("2000/01/01"), time_range_end);
		int valid_reads_gsr = 0;
		int valid_reads_heart = 0;
		for (int i = 0; i < temp_pie_data.size(); i++) {
			PieHeartData current_data = (PieHeartData) temp_pie_data.get(i);
			if (current_data.gsr > 0) {
				new_data.gsr += current_data.gsr;
				new_data.gsr_filtered += current_data.gsr_filtered;
				valid_reads_gsr ++; 
			}
			if (current_data.heart_rate > 0) {
				new_data.heart_rate += current_data.heart_rate;
				valid_reads_heart++;
			}
		}
		if (valid_reads_gsr > 0) {
			new_data.gsr = (int) (new_data.gsr / valid_reads_gsr);
			if (new_data.gsr <= 0) new_data.gsr = 0;
			new_data.gsr_filtered = (int) (new_data.gsr_filtered / valid_reads_gsr);		
			if (new_data.gsr_filtered <= 0) new_data.gsr_filtered = 0;
		}	
		if (valid_reads_heart > 0) {
			new_data.heart_rate = (int) (new_data.heart_rate / valid_reads_heart);		
			if (new_data.heart_rate <= 0) new_data.heart_rate = 0;
			if (new_data.heart_rate > 180) {
				new_data.heart_rate = 180;
			}
			new_data.name = "" + new_data.heart_rate;
		} 
		return new_data;
	}
	
	public void loadDateTimeRange(Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {
		pie_data = load_date_and_time_range(PieData_HeartRate, date_range_start, time_range_start, date_range_end, time_range_end);				
		this.date_range_start = new Date (date_range_start); 
		this.time_range_start = new Time (time_range_start); 
		this.date_range_end = new Date (date_range_end); 
		this.time_range_end = new Time (time_range_end);		
		this.loadPie();

	}

	public void loadDate(Date date) {		
		this.pie_data = load_date_and_time_range(PieData_HeartRate, date, new Time("00:00:00"), date, new Time("23:59:59"));
		this.loadPie();

		this.date_range_start = new Date (date); 
		this.time_range_start = new Time (new Time("00:00:00")); 
		this.date_range_end = new Date (date); 
		this.time_range_end = new Time (new Time("23:59:59"));	
	}
	
	private void loadPie() {
		int transparent_color = ShapeColor.colorARGB(0, 255, 255, 255);
		int red_color = ShapeColor.colorARGB(255, 255, 0, 0);

		// initialize the pie with pie-level information
		pie = new Pie_Line((int) location.x, (int) location.y, (float) diameter, (int)this.pie_slices, ShapeCircle.PIE_LINE_VAR_RADIUS);
		pie.label.setText(this.title);
		pie.setColorActiveARGB(transparent_color);
		pie.setTextLocationAll(this.location_name.x, this.location_name.y, (this.location_description.x-this.location_name.x), (this.location_description.y-this.location_name.y));
		pie.setTextVisibleAll();
		pie.setShiftAllMouseOverPieSlices((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.0, false);
		pie.loadFontPie(font_main_bar_legend, 16, 1.0f);
		pie.setWidthAll(4);

		// initialize the slices of the pie
		for (int i = 0; i < pie_data.size(); i++) {
			PieHeartData pie_record = (PieHeartData)pie_data.get(i);
			String name = "heart rate";
			String description = "" + pie_record.heart_rate;
			if (pie_record.heart_rate == 0) name = "unplugged";
			pie.setSliceValue(i, (float) (pie_record.heart_rate));
			pie.textSetNameSlice(i, name);
			pie.textSetDescriptionSlice(i,description);
		}
		pie.setColorAllSlices(red_color);
		pie.setShiftAllMouseOverSlices((float) 0.0, (float) 0.0, (float) -0.1, (float) 0.0, true);
		pie.textLocationNameDescriptionSlices(this.location_name_slice.x, this.location_name_slice.y, (this.location_description_slice.x-this.location_name_slice.x), (this.location_description_slice.y-this.location_name_slice.y));
		pie.loadFontTitleSlices(font_main_bar_legend, 16);
		pie.loadFontDescriptionSlices(font_main_text, 20);
		pie.loadFontDescriptionSlices(font_main_text, fonts_size[font_main_text]);
		pie.applyValuesToSliceDisplay();
	}
	
}
