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
import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;
import com.julioterra.moodyjulio.dataviz.shapes.bar.Bar;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ButtonDropDown;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ButtonText;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie_Arc;

public class BarCreator extends DataProcessor {

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

	public ArrayList<ShapeText> text_items;
	HashMap<String, Integer> text_items_names;

	public Date date_range_start; 
	public Time time_range_start; 
	public Date date_range_end; 
	public Time time_range_end;
	
	public float[] time_per_activity;
	public float time_total;
    
	public BarCreator(float x, float y, float width, float height, float bar_offset){
		super();
		this.text_items = new ArrayList<ShapeText>();
		text_items_names = new HashMap<String, Integer>();
		this.bar_data = new ArrayList<Data>();
		this.bars = new ArrayList<Bar>();

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
	    for (int i = 0; i < text_items.size(); i++) {
	    	ShapeText display_button = text_items.get(i);	 
			display_button.display();
	    }
	}
	
	public void mouseOver() {
//	    super.mouseOver();		
	    for (int i = 0; i < bars.size(); i++) {
	    	bar = bars.get(i);	 
	    	bar.mouseOver();
	    }
	    for (int j = 0; j < text_items.size(); j++) {
	    	ShapeText text_item = text_items.get(j);
	    	text_item.mouseOver();
		}
	}
	
	public void load(Date selected_date) {
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
		bar.setShiftMouseOverBar((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.0, true);
		bar.setTextLocationNameDescription(675, 175, (this.location_description.x-this.location_title.x), (this.location_description.y-this.location_title.y));
		bar.loadFontAll(font_main_header, DataVizElement.fonts_size[font_main_header], 0.8f);
		bar.setName(this.bar_title);

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
				if (bar.getSizeSlices() == 0) temp_time_start = new Time("00:00:00");
				else temp_time_end = new Time("23:59:59");
			}
			bar.addSlice(name, description, bar_record.activity.toLowerCase(), Time.calculate_time_dif_seconds(new Time("00:00:00"), temp_time_start), Time.calculate_time_dif_seconds_maxout(new Time("00:00:00"), temp_time_end));
			this.add_time_count(bar_record.activity, Time.calculate_time_dif_seconds(bar_record.time_stamp, bar_record.time_end));
		}
		bar.setShiftMouseOverSlices((float) 0.0, (float) 0.0, (float) -0.1, (float) 0.0, true);
		bar.loadFontSlices(DataVizElement.font_main_text, DataVizElement.fonts_size[DataVizElement.font_main_text], 1f);
		bar.textLocationNameDescriptionSlices(675, 175, 0, 50);
		PApplet.println("number of slices " + bar.slices.size());
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

//	/***************************************
//	 * TEXT_ITEMS ARRAY FUNCTIONS - GETTER & SETTER METHODS
//	 ***************************************/	
//	
//	public void addTextRect(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode){
//		ShapeText new_button = new ShapeText(x, y, width, height, text, color, text_color, font_number, align_mode, false, false);
//		text_items.add(new_button);
//	}
//	
//	public void addTextRect(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode, float rotation){
//		ShapeText new_button = new ShapeText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
//		text_items.add(new_button);
//	}
//	
//	public void addTextRect(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
//		ShapeText new_button = new ShapeText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
//		new_button.setFontSizeAll(font_size);
//		text_items.add(new_button);
//	}
//
//	public void addTextRectButton(int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int align_mode, float rotation){
//		ShapeText new_button = new ShapeText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
//		new_button.setTextLocationDescription(description_x, description_y);
//		text_items.add(new_button);
//	}
//
//	public void addTextRectButton(int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
//		ShapeText new_button = new ShapeText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
//		new_button.setTextLocationDescription(description_x, description_y);
//		new_button.setFontSizeAll(font_size);
//		text_items.add(new_button);
//	}
//
//	public void addText(int x, int y, String text, int color, int font_number, int align_mode){
//		ButtonText new_button = new ButtonText(x, y, text, color, font_number, align_mode);
//		text_items.add(new_button);
//	}
//
//	public void addTextButton(int x, int y, String text, int color, int font_number, int align_mode){
//		ButtonText new_button = new ButtonText(x, y, text, color, font_number, align_mode, true, true);
//		text_items.add(new_button);
//	}
//
//	public void addTextButton(int x, int y, String text, int color, int font_number, int font_size, int align_mode){
//		ButtonText new_button = new ButtonText(x, y, text, color, font_number, align_mode, true, true);
//		new_button.setFontSizeAll(font_size);
//		text_items.add(new_button);
//	}
//
//	public void removeTextItem(int index) {
//		if (index < text_items.size()) {
//			text_items.remove(index);
//		}		
//	}
//
//	public void setShiftMouseOverTextSlice(int index, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over) {
//		if (index < text_items.size()) {
//			ShapeText updated_button = (ShapeText) text_items.get(index);
//			updated_button.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
//		}		
//	}
//	
//	public void resizeFontTextItem (int index, float resize_scale) {
//		if (index < text_items.size()) {
//			ShapeText updated_button = (ShapeText) text_items.get(index);
//			updated_button.loadFontAll(updated_button.font_number_title, (int)(updated_button.font_size_title*resize_scale), 1);
//		}				
//	}
//
//	public int getSizeTextItems() {
//		return this.text_items.size();
//	}
//
//	public void setTextVisible(int index) {
//		if (index < text_items.size()) {
//			ButtonText updated_button = (ButtonText) text_items.get(index);
//			updated_button.visible();
//		}		
//	}
//
//	public void setTextInvisible(int index) {
//		if (index < text_items.size()) {
//			ButtonText updated_button = (ButtonText) text_items.get(index);
//			updated_button.invisible();
//		}		
//	}
//
//	public void updateTextLocation(int index, int x, int y){
//		if (index < text_items.size()) {
//			ButtonText updated_button = (ButtonText) text_items.get(index);
//			updated_button.setLocation(x, y);
//		}
//	}
//
//	public void updateText(int index, String text){
//		if (index < text_items.size()) {
//			ButtonText updated_button = (ButtonText) text_items.get(index);
//			updated_button.setName(text);
//		}
//	}
//
//	public void updateTextColor(int index, int color){
//		if (index < text_items.size()) {
//			ButtonText updated_button = (ButtonText) text_items.get(index);
//			updated_button.setColorBaseARGB(color);
//		}
//	}
//
//	public void updateTextFont(int index, int font_number){
//		if (index < text_items.size()) {
//			ButtonText updated_button = (ButtonText) text_items.get(index);
//			updated_button.loadFontTitle(font_number, DataVizElement.fonts_size[font_number]);
//		}
//	}
	
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
