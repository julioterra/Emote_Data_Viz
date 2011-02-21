package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import processing.core.PApplet;
import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.Data;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRectText;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ButtonDropDown;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ShapeText;
import com.julioterra.moodyjulio.dataviz.shapes.panel.Panel;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie_Arc;

public class PieCreator extends DataProcessor {

	ArrayList<Data> pie_data;
	public Pie pie;
    public float radius;
    public float diameter;
    public float pie_slices;

	public String title;
	public String description;
	public PVector location;
	public PVector location_name;
	public PVector location_description;
	public PVector location_name_slice;
	public PVector location_description_slice;
	
	public Panel panel;

	public PieCreator(float x, float y, int diameter){
		super();
		
		this.title = "";
		this.description = "";
		this.data_type_raw = JOURNAL;
		this.location = 					new PVector(x, y);
		this.location_name = 				new PVector(-1000, -1000);
		this.location_description = 		new PVector(-1000, -1000);
		this.location_name_slice = 			new PVector(-1000, -1000);
		this.location_description_slice = 	new PVector(-1000, -1000);

		this.diameter = diameter;
		this.radius = (float) (diameter/2.0);
		this.pie = new Pie_Arc((int)location.x, (int) location.y, this.diameter, ShapeCircle.PIE_ARC_SET_RADIUS);
		this.pie_data = new ArrayList<Data>();
		this.pie_slices = 0;
		
		this.panel = new Panel(0, 0, PApplet.WIDTH, PApplet.HEIGHT, Transparent_Color);

		date_range_start = new Date (); 
		time_range_start = new Time (); 
		date_range_end = new Date (); 
		time_range_end = new Time ();
	}

	public void scrollDays(int direction){
		this.date_range_start.update_day(direction); 
		this.date_range_end.update_day(direction); 
		PApplet.println("PIE CREATOR - scroll days start " + date_range_start.get_date_for_sql() + " " + time_range_start.get_time_for_sql());
		loadDateTimeRange(date_range_start, time_range_start, date_range_end, time_range_end);
	}
	
	public void loadDateTimeRange(Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {
	}
	
	public void display() {
		panel.display();		
		pie.display();
	}
		
	/***************************************
	 * GETTER & SETTER METHODS
	 ***************************************/	

	
	public ArrayList<Data> getPie_data() {
		return pie_data;
	}

	public Pie getPie() {
		return pie;
	}

	public PVector getLocation() {
		return location;
	}

	public void setLocation(int x, int y) {
		this.location = new PVector(x, y);
	}

	public void setLocationNameDescriptionRel(int x, int y, int rel_x, int rel_y) {
		this.location_name = new PVector(x, y);
		this.location_description = new PVector(x+rel_x, y+rel_y);
	}
	
	public PVector getLocation_name() {
		return location_name;
	}

	public void setLocation_name(int x, int y) {
		this.location_name = new PVector(x, y);
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
		return location_name_slice;
	}

	public void setLocation_name_slice(int x, int y) {
		this.location_name_slice = new PVector(x, y);
	}

	public PVector getLocation_description_slice() {
		return location_description_slice;
	}

	public void setLocation_description_slice(int x, int y) {
		this.location_description_slice = new PVector(x, y);
	}

	public void setLocation_name(PVector location_name) {
		this.location_name = location_name;
	}

	public void setLocation_description(PVector location_description) {
		this.location_description = location_description;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getDiameter() {
		return diameter;
	}

	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}

	public float getPie_slices() {
		return pie_slices;
	}

	public void setPie_slices(float pie_slices) {
		this.pie_slices = pie_slices;
	}

	public String getPie_name() {
		return title;
	}

	public void setPie_name(String pie_name) {
		this.title = pie_name;
	}

	public String getPie_description() {
		return description;
	}

	public void setPie_description(String pie_description) {
		this.description = pie_description;
	}

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
		if (input_string.length() > 3) output_string += input_string + "\n";
		return output_string;
	}
	
}
