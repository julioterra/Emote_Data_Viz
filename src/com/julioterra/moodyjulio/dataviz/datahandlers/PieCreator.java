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
    HashMap<String, ShapeRectText> text_items_map; 
	
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
		
		text_items_map = new HashMap<String, ShapeRectText>();

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
		Iterator<Map.Entry<String, ShapeRectText>> 	text_item_entries = text_items_map.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeRectText> entry = (Map.Entry<String, ShapeRectText>) text_item_entries.next();
			if (entry.getKey().contains("background")) {
				ShapeRectText display_button = entry.getValue();	 
				display_button.display();
			}
		}

		text_item_entries = text_items_map.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeRectText> entry = (Map.Entry<String, ShapeRectText>) text_item_entries.next();
			if (!entry.getKey().contains("background")) {
				ShapeRectText display_button = entry.getValue();	 
				display_button.display();
			}
		}

		pie.display();
	}
	
	public void mouseOver() {
		Iterator<Map.Entry<String, ShapeRectText>> 	text_item_entries = text_items_map.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeRectText> entry = (Map.Entry<String, ShapeRectText>) text_item_entries.next();
	    	ShapeRectText display_button = entry.getValue();	 
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
	
	
	/***********************************************************************************************************************
	 ** INTERFACE METHODS FOR TEXT ITEMS HOLDING CLASSES
	 **
	 ** Includes multiple methods for adding, updating, and managing text items.
	 ** The input parameter and return types have been standardized across classes
	 ** to facilitate usage 
	 ** 
	 ************/	
	
	public int getSizeTextItems() {
		return text_items_map.size();
	}

	public void addTextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode){
		ShapeRectText new_button = new ShapeRectText((int)(location.x+x), (int)(location.y+y), width, height, text, color, text_color, font_number, align_mode, false, false);
		text_items_map.put(id, new_button);
	}

	public void addTextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
		text_items_map.put(id, new_button);
	}

	public void addTextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
		new_button.setFontSizeAll(font_size);
		text_items_map.put(id, new_button);
	}

	public void addTextRectButton(String id, int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
		new_button.setTextLocationDescription(description_x, description_y);
		text_items_map.put(id, new_button);
	}

	public void addTextRectButton(String id, int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
		new_button.setTextLocationDescription(description_x, description_y);
		new_button.setFontSizeAll(font_size);
		text_items_map.put(id, new_button);
	}

	public void addText(String id, int x, int y, String text, int color, int font_number, int align_mode){
		ShapeText new_button = new ShapeText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, false, false);
		text_items_map.put(id, new_button);
	}

	public void addTextButton(String id, int x, int y, String text, int color, int font_number, int align_mode, boolean mouse_over_active, boolean mouse_press_active){
		ShapeText new_button = new ShapeText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, mouse_over_active, mouse_press_active);
		text_items_map.put(id, new_button);
	}
	
	public void addTextButton(String id, int x, int y, String text, int color, int font_number, int font_size, int align_mode){
		ShapeText new_button = new ShapeText(x, y, text, color, font_number, align_mode, true, true);
		new_button.setFontSizeAll(font_size);
		text_items_map.put(id, new_button);
	}

//	public void addDropDownButton(String id, int x, int y, String text, int color, int font_number, int align_mode, boolean mouse_over_active, boolean mouse_press_active){
//		ButtonDropDown new_button = new ButtonDropDown((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, mouse_over_active, mouse_press_active);
//		text_items_map.put(id, new_button);
//	}
//
//	public void addDropDownSubButton(String button_id, String text, int color, Object object, String method){
//		if (text_items_map.get(button_id) != null) {
//			ButtonDropDown current_button = (ButtonDropDown) text_items_map.get(button_id);
//			current_button.addSubButton(text, color, object, method);							
//		}
//	}
//
//	public void addDropDownSubButton(String button_id, String text, Object object, String method){
//		if (text_items_map.get(button_id) != null) {
//			ButtonDropDown current_button = (ButtonDropDown) text_items_map.get(button_id);
//			current_button.addSubButton(text, object, method);				
//		}
//	}

	public void removeText(String button_id) {
		if (text_items_map.get(button_id) != null) {
			text_items_map.remove(button_id);
		}
	}

	public void setShiftMouseOverTextSlice(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over){
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.setShiftAllMouseOver(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
		}
	}

	public void setTextVisible(String button_id) {
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.visible();
		}
	}

	public void setTextInvisible(String button_id) {
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.invisible();
		}
	}

	public void updateTextLocation(String button_id, int x, int y) {
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.setLocation(x, y);
		}
	}

	public void updateText(String button_id, String text) {
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.setName(text);
		}
	}

	public void updateTextDescription(String button_id, String text) {
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.setDescription(text);
		}
	}

	public void updateTextColor(String button_id, int color) {
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.setColorBaseARGB(color);
		}
	}

	public void loadTextFont(String button_id, int font_number, int font_size){
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.loadFontTitle(font_number, font_size);
		}		
	}

	public void setTextSize(String button_id, int font_size){
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.setFontSizeTitle(font_size);
		}
	}

	public void setTextSizeDescription(String button_id, int font_size){
		if (text_items_map.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) text_items_map.get(button_id);
			updated_button.setFontSizeDescription(font_size);
		}
	}
	
}
