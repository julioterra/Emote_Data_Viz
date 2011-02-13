package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.Data;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ButtonDropDown;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ButtonText;
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
	HashMap<String, Integer> text_items_names;
    
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
		
		text_items_names = new HashMap<String, Integer>();

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
	    for (int i = 0; i < text_items.size(); i++) {
	    	ShapeText display_button = text_items.get(i);	 
			display_button.display();
	    }
	    pie.display();
	}
	
	public void mouseOver() {
	    for (int i = 0; i < text_items.size(); i++) {
	    	ShapeText display_button = text_items.get(i);	 
			display_button.mouseOver();
	    }
	    pie.mouseOver();		
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
	
	
	/***********************************************************************************************************************
	 ** INTERFACE METHODS FOR TEXT ITEMS HOLDING CLASSES
	 **
	 ** Includes multiple methods for adding, updating, and managing text items.
	 ** The input parameter and return types have been standardized across classes
	 ** to facilitate usage 
	 ** 
	 ************/	
	
	public int getSizeTextItems() {
		return text_items.size();
	}

	public void addTextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode){
		ShapeText new_button = new ShapeText((int)(location.x+x), (int)(location.y+y), width, height, text, color, text_color, font_number, align_mode, false, false);
		text_items.add(new_button);
		text_items_names.put(id, text_items.size()-1);
	}

	public void addTextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode, float rotation){
		ShapeText new_button = new ShapeText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
		text_items.add(new_button);
		text_items_names.put(id, text_items.size()-1);
	}

	public void addTextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeText new_button = new ShapeText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
		new_button.setFontSizeAll(font_size);
		text_items.add(new_button);
		text_items_names.put(id, text_items.size()-1);
	}

	public void addTextRectButton(String id, int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int align_mode, float rotation){
		ShapeText new_button = new ShapeText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
		new_button.setTextLocationDescription(description_x, description_y);
		text_items.add(new_button);
		text_items_names.put(id, text_items.size()-1);
	}

	public void addTextRectButton(String id, int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeText new_button = new ShapeText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
		new_button.setTextLocationDescription(description_x, description_y);
		new_button.setFontSizeAll(font_size);
		text_items.add(new_button);
		text_items_names.put(id, text_items.size()-1);
	}

	public void addText(String id, int x, int y, String text, int color, int font_number, int align_mode){
		ButtonText new_button = new ButtonText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, false, false);
		text_items.add(new_button);
		text_items_names.put(id, text_items.size()-1);
	}

	public void addTextButton(String id, int x, int y, String text, int color, int font_number, int align_mode, boolean mouse_over_active, boolean mouse_press_active){
		ButtonText new_button = new ButtonText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, mouse_over_active, mouse_press_active);
		text_items.add(new_button);
		text_items_names.put(id, text_items.size()-1);
	}
	
	public void addTextButton(String id, int x, int y, String text, int color, int font_number, int font_size, int align_mode){
		ButtonText new_button = new ButtonText(x, y, text, color, font_number, align_mode, true, true);
		new_button.setFontSizeAll(font_size);
		text_items.add(new_button);
		text_items_names.put(id, text_items.size()-1);
	}

	public void addDropDownButton(String id, int x, int y, String text, int color, int font_number, int align_mode, boolean mouse_over_active, boolean mouse_press_active){
		ButtonDropDown new_button = new ButtonDropDown((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, mouse_over_active, mouse_press_active);
		text_items.add(new_button);
		text_items_names.put(id, text_items.size()-1);
	}

	public void addDropDownSubButton(String button_id, String text, int action, int color){
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			if(index < text_items.size()) { 
				ButtonDropDown current_button = (ButtonDropDown) text_items.get(index);
				current_button.addSubButton(text, action, color);				
			}
		}
	}

	public void addDropDownSubButton(String button_id, String text, int action){
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			if(index < text_items.size()) { 
				ButtonDropDown current_button = (ButtonDropDown) text_items.get(index);
				current_button.addSubButton(text, action);				
			}
		}
	}

	public void removeText(String button_id) {
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			if (index < text_items.size()) {
				text_items.remove(index);
				text_items_names = new HashMap<String, Integer>(adjustValuesInHashMap(index, text_items_names));
			}		
		}
	}

	public void removeText(int index) {
		if (index < text_items.size()) {
			text_items.remove(index);
			text_items_names = new HashMap<String, Integer>(adjustValuesInHashMap(index, text_items_names));
		}
	}

	public void setShiftMouseOverTextSlice(int index, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
		}		
	}

	public void setShiftMouseOverTextSlice(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over){
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			setShiftMouseOverTextSlice(index, hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
		}
	}

	public void setTextVisible(int index) {
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.visible();
		}		
	}

	public void setTextVisible(String button_id) {
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			setTextVisible(index);
		}
	}

	public void setTextInvisible(int index) {
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.invisible();
		}		
	}

	public void setTextInvisible(String button_id) {
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			setTextInvisible(index);
		}
	}

	public void updateTextLocation(int index, int x, int y){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setLocation(x, y);
		}
	}

	public void updateTextLocation(String button_id, int x, int y) {
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			updateTextLocation(index, x, y);
		}
	}

	public void updateText(int index, String text){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setName(text);
		}
	}

	public void updateText(String button_id, String text) {
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			updateText(index, text);
		}
	}

	public void updateTextDescription(int index, String text){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setDescription(text);
		}
	}

	public void updateTextDescription(String button_id, String text) {
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			updateText(index, text);
		}
	}

	public void updateTextColor(int index, int color){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setColorBaseARGB(color);
		}
	}

	public void updateTextColor(String button_id, int color) {
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			updateTextColor(index, color);
		}
	}

	public void loadTextFont(String button_id, int font_number, int font_size){
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			if (index < text_items.size()) {
				ShapeText updated_button = (ShapeText) text_items.get(index);
				updated_button.loadFontTitle(font_number, font_size);
			}
		}
	}

	public void loadTextFont(int index, int font_number, int font_size){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.loadFontTitle(font_number, font_size);
		}
	}

	public void setTextSize(String button_id, int font_size){
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			setTextSize(index, font_size);
		}
	}

	public void setTextSize(int index, int font_size){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setFontSizeTitle(font_size);
		}
	}

	public void setTextSizeDescription(String button_id, int font_size){
		if (text_items_names.get(button_id) != null) {
			int index = Integer.parseInt(""+text_items_names.get(button_id));
			setTextSizeDescription(index, font_size);
		}
	}

	public void setTextSizeDescription(int index, int font_size){
		if (index < text_items.size()) {
			ShapeText updated_button = (ShapeText) text_items.get(index);
			updated_button.setFontSizeDescription(font_size);
		}
	}
	
}
