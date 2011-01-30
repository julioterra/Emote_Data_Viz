package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.Data;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie_Arc;

public class PieCreator extends DataProcessor {

	ArrayList<Data> pie_data;
	public Pie pie;
	public String pie_name;
	public String pie_description;
	public PVector location;
	public PVector location_name;
	public PVector location_description;
	public PVector location_name_slice;
	public PVector location_description_slice;
    public float radius;
    public float diameter;
    public float pie_slices;
    public ArrayList<ShapeText> text_items;
    
	public PieCreator(float x, float y, int diameter){
		super();
		this.text_items = new ArrayList<ShapeText>();
		this.data_type_raw = JOURNAL;
		this.location = 					new PVector(x, y);
		this.location_name = 				new PVector(-1000, -1000);
		this.location_description = 		new PVector(-1000, -1000);
		this.location_name_slice = 			new PVector(-1000, -1000);
		this.location_description_slice = 	new PVector(-1000, -1000);
		this.pie_name = "";
		this.pie_description = "";

		this.diameter = diameter;
		this.radius = (float) (diameter/2.0);
		this.pie = new Pie_Arc((int)location.x, (int) location.y, this.diameter, ShapeCircle.PIE_ARC_SET_RADIUS);
		this.pie_data = new ArrayList<Data>();
		this.pie_slices = 0;
		
		date_range_start = new Date (); 
		time_range_start = new Time (); 
		date_range_end = new Date (); 
		time_range_end = new Time ();
	}

	public void scrollDays(int direction){
		this.date_range_start.update_day(direction); 
		this.date_range_end.update_day(direction); 
		PApplet.println("scroll days start " + date_range_start.get_date_for_sql() + " " + time_range_start.get_time_for_sql());
		loadPie(date_range_start, time_range_start, date_range_end, time_range_end);
	}
	
	public void loadPie(Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {
	}
	
	public void display() {
	    pie.display();
	    for (int i = 0; i < text_items.size(); i++) {
			ShapeText display_button = text_items.get(i);	 
			display_button.display();
	    }
	}
	
	public void mouseOver() {
	    pie.mouseOver();		
	}

	/***************************************
	 * TEXT_ITEMS ARRAY FUNCTIONS - GETTER & SETTER METHODS
	 ***************************************/	

	public void addText(int x, int y, String text, int color, int font_number, int align_mode){
		ShapeText new_button = new ShapeText(x, y, text, color, font_number, align_mode);
		text_items.add(new_button);
	}

	public void removeText(int index) {
		if (index < text_items.size()) {
			text_items.remove(index);
		}		
	}

	public void setTextVisible(int index) {
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.visible();
		}		
	}

	public void setTextInvisible(int index) {
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.invisible();
		}		
	}

	public void updateTextLocation(int index, int x, int y){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setLocation(x, y);
		}
	}

	public void updateText(int index, String text){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setName(text);
		}
	}

	public void updateTextColor(int index, int color){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setColorBaseARGB(color);
		}
	}

	public void updateTextFont(int index, int font_number){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.loadFontName(font_number, fonts_size[font_number]);
		}
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
		return pie_name;
	}

	public void setPie_name(String pie_name) {
		this.pie_name = pie_name;
	}

	public String getPie_description() {
		return pie_description;
	}

	public void setPie_description(String pie_description) {
		this.pie_description = pie_description;
	}

	
	
}
