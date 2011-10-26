package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.Data;
import com.julioterra.moodyjulio.dataviz.data.JournalData;
import com.julioterra.moodyjulio.dataviz.data.PieEmotionData;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRectText;
import com.julioterra.moodyjulio.dataviz.shapes.bar.Bar;
import com.julioterra.moodyjulio.dataviz.shapes.bar.BarArray;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ButtonDropDown;
import com.julioterra.moodyjulio.dataviz.shapes.panel.Panel;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ShapeText;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie_Arc;

public class BarCreator extends DataProcessor {

	BarArray bar_array;
	ArrayList<Data> bar_data;
	ArrayList<Bar> bars;
	public Bar bar;
	public PVector location;
	public PVector size;
	public float bar_offset;
	public float bar_slices;
    public int bar_mode;

	public String bar_title;
	public String bar_description;	
	public PVector location_title;
	public PVector location_description;
	public PVector location_title_slice;
	public PVector location_description_slice;

	public Panel panel;

	public Date date_range_start; 
	public Time time_range_start; 
	public Date date_range_end; 
	public Time time_range_end;
	
	public float[] time_per_activity;
	public float time_total;
    
	public BarCreator(float x, float y, float width, float height, float bar_offset){
		super();
		this.bar_data = new ArrayList<Data>();
		this.bars = new ArrayList<Bar>();
		this.panel = new Panel(0, 0, PApplet.WIDTH, PApplet.HEIGHT, Transparent_Color);

		this.location = 					new PVector(x, y);
		this.size = 						new PVector(width, height);
		this.location_title = 				new PVector(-1000, -1000);
		this.location_description = 		new PVector(-1000, -1000);
		this.location_title_slice = 		new PVector(-1000, -1000);
		this.location_description_slice = 	new PVector(-1000, -1000);
		this.bar_title = "";
		this.bar_description = "";
		this.bar_slices = 0;
		this.bar_offset = bar_offset;
		
		date_range_start = new Date (); 
		time_range_start = new Time (); 
		date_range_end = new Date (); 
		time_range_end = new Time ();
		
		this.time_total = 0;
		this.time_per_activity = new float[DataVizElement.Activity_Names.length];
		for (int i = 0; i < time_per_activity.length; i++) { time_per_activity[i] = 0f; }
		
	}

	public void scrollDays(int direction){
		this.date_range_start.update_day(direction); 
		this.date_range_end.update_day(direction); 
		PApplet.println("scroll days start " + date_range_start.get_date_for_sql() + " " + time_range_start.get_time_for_sql());
		load(date_range_start);
	}
	
	public void display() {
		for (int i = 0; i < bars.size(); i++) {
	    	bar = bars.get(i);	 
	    	bar.display();
	    }
		panel.display();
	}
		
	public void load(Date selected_date) {
		System.out.println("data requested: " + selected_date);
		this.bar_data = load_date_and_time_range_end(JournalData, selected_date, new Time("00:00:00"), selected_date, new Time("23:59:59"));
		this.date_range_start = new Date(selected_date);
		this.date_range_end = new Date(selected_date);
		this.bar_mode = 0;
		this.load();
	}

	public void load(Date date_start, Date date_end) {
		this.load(date_start);
		while(!date_start.equals(date_end)) {
			date_start.update_day(1);
			this.load(date_start);
		}
		this.print_count_time();
	}
	
	private void load() {		
		this.bar_slices = bar_data.size();
		this.bar_title = Date.getDateInString(date_range_start);
		
		bar = new Bar((int)(location.x + ((size.x+this.bar_offset)*(bars.size()))), (int)location.y, (int)size.x, (int)size.y, ShapeColor.colorARGB(255,220,220,220), 0, (int)Time.calculate_time_dif_seconds(new Time("00:00:00"), new Time("23:59:59")));
		bar.setShiftMouseOver((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.0, true, false);
		bar.setTextLocationAll(675, 175, (this.location_description.x-this.location_title.x), (this.location_description.y-this.location_title.y));
		bar.label.loadFont(font_main_header, DataVizElement.fonts_size[font_main_header]);
		bar.label.setText(this.bar_title);

		for (int i = 0; i < bar_data.size(); i++) {
			JournalData bar_record = new JournalData((JournalData) bar_data.get(i));
			String name = "";
			String description = "from " + bar_record.time_stamp.get_time_for_sql() + " to " + bar_record.time_end.get_time_for_sql() + "\n" +
						   "feeling " + applyLineBreaks((bar_record.emotion_L1.toLowerCase()+", "+bar_record.emotion_L2.toLowerCase()+", "+bar_record.emotion_L3.toLowerCase()), 40) +
						   "doing " + applyLineBreaks(bar_record.activity.toLowerCase(), 40) +
			 			   "at " + applyLineBreaks(bar_record.location.toLowerCase(), 40) + 
			 			   "with " + countListItems(bar_record.people) + " people";

			Time temp_time_end = new Time(bar_record.time_end);
			Time temp_time_start = new Time(bar_record.time_stamp);
			if (bar_record.date_end.month > bar_record.date_stamp.month || bar_record.date_end.day > bar_record.date_stamp.day) {
				if (bar.getElementCount() == 0) temp_time_start = new Time("00:00:00");
				else temp_time_end = new Time("23:59:59");
			}
			bar.addElement(name, description, bar_record.activity.toLowerCase(), Time.calculate_time_dif_seconds(new Time("00:00:00"), temp_time_start), Time.calculate_time_dif_seconds_maxout(new Time("00:00:00"), temp_time_end));
			this.add_time_count(bar_record.activity, Time.calculate_time_dif_seconds(bar_record.time_stamp, bar_record.time_end));
		}
		bar.setElementAllShiftMouseOver((float) 0.2, (float) 0.0, (float) -0.2, (float) 0, true, true);
		bar.setElementAllShiftMouseClicked((float) 0, (float) 0.0, (float) -0, (float) 0, false, false);
		bar.loadElementAllFont(DataVizElement.font_main_text, DataVizElement.fonts_size[DataVizElement.font_main_text]);
		bar.setElementAllTextLocation(675, 175, 0, 50);
		PApplet.println("number of slices " + bar.elements.size());
		bars.add(bar);
	}

	private void add_time_count(String activities, float length_seconds) {
		time_total += length_seconds;					
		for (int i = 0; i < DataVizElement.Activity_Names.length; i++) {
			if (activities.contains(DataVizElement.Activity_Names[i])) {
				time_per_activity[i] += length_seconds;
			}
		}
	}
	
	public void print_count_time() {
		for (int i = 0; i < DataVizElement.Activity_Names.length; i++) {
			PApplet.println(Activity_Names[i] + " " + (time_per_activity[i]/time_total*100));
		}
	}
	
	public float[] getActivityTimes() {
		return this.time_per_activity;
	}

	public float getActivityTotal() {
		return this.time_total;
	}


	
	/***************************************
	 * GETTER & SETTER METHODS
	 ***************************************/	

	
	public ArrayList<Data> getPie_data() {
		return bar_data;
	}

	public Bar getPie() {
		return bar;
	}

	public PVector getLocation() {
		return location;
	}

	public void setLocation(int x, int y) {
		this.location = new PVector(x, y);
	}

	public void setLocationNameDescriptionRel(int x, int y, int rel_x, int rel_y) {
		this.location_title = new PVector(x, y);
		this.location_description = new PVector(x+rel_x, y+rel_y);
	}
	
	public PVector getLocation_name() {
		return location_title;
	}

	public void setLocation_name(int x, int y) {
		this.location_title = new PVector(x, y);
	}

	public PVector getLocation_description() {
		return location_description;
	}

	public void setLocation_description(int x, int y) {
		this.location_description = new PVector(x, y);
	}
	
	public void setLocationNameDescriptionRelSlice(int x, int y, int rel_x, int rel_y) {
		this.setLocation_name_slice(x, y);
		this.setLocation_description_slice(x+rel_x, y+rel_y);
	}	

	public PVector getLocation_name_slice() {
		return location_title_slice;
	}

	public void setLocation_name_slice(int x, int y) {
		this.location_title_slice = new PVector(x, y);
	}

	public PVector getLocation_description_slice() {
		return location_description_slice;
	}

	public void setLocation_description_slice(int x, int y) {
		this.location_description_slice = new PVector(x, y);
	}

	public void setLocation_name(PVector location_name) {
		this.location_title = location_name;
	}

	public void setLocation_description(PVector location_description) {
		this.location_description = location_description;
	}

	public float getPie_slices() {
		return bar_slices;
	}

	public void setPie_slices(float pie_slices) {
		this.bar_slices = pie_slices;
	}

	public String getPie_name() {
		return bar_title;
	}

	public void setPie_name(String pie_name) {
		this.bar_title = pie_name;
	}

	public String getPie_description() {
		return bar_description;
	}

	public void setPie_description(String pie_description) {
		this.bar_description = pie_description;
	}

}
