package com.julioterra.moodyjulio.dataviz.shapes.panel;

import java.util.ArrayList;
import java.util.HashMap;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;



public class Panel extends ShapeRect {

	ArrayList<ShapeText> text_items;
	ArrayList<Integer> drop_down_index;
	HashMap<String, Integer> text_items_names;
	
	/***************************************
	 ** CONSTRUCTORS
	 ***************************************/	

	public Panel() {
		super();
		text_items = new ArrayList<ShapeText>();
		drop_down_index = new ArrayList<Integer>();
		text_items_names = new HashMap<String, Integer>();
	}

	public Panel(int x, int y, int size_x, int size_y, int color) {
		super(x, y, size_x, size_y, color);
		text_items = new ArrayList<ShapeText>();
		drop_down_index = new ArrayList<Integer>();
		text_items_names = new HashMap<String, Integer>();
	}	

	/***************************************
	 ** DISPLAY FUNCTIONS - GETTER & SETTER METHODS
	 ***************************************/	

	public void display(){
		super.display();
		for (int i = 0; i < text_items.size(); i++) {
			ShapeText display_button = text_items.get(i);	 
			display_button.display();
	    }
	}
	
	/***************************************
	 ** DISPLAY FUNCTIONS - GETTER & SETTER METHODS
	 ***************************************/	

	public void mouseOver() {
//		super.mouseOver();		// this checks for mouse_over event on the panel itself
		for (int i = 0; i < text_items.size(); i++) {
			ShapeText display_button = text_items.get(i);	 
			display_button.mouseOver();
	    }		
	}
	
	public void setShiftMouseOverAll(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over){
		super.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
		for (int i = 0; i < text_items.size(); i++) {
			ShapeText display_button = text_items.get(i);	 
			display_button.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
	    }
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
			ButtonText updated_button = (ButtonText) text_items.get(index);
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
			ButtonText updated_button = (ButtonText) text_items.get(index);
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
			ButtonText updated_button = (ButtonText) text_items.get(index);
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
			ButtonText updated_button = (ButtonText) text_items.get(index);
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
			ButtonText updated_button = (ButtonText) text_items.get(index);
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
			ButtonText updated_button = (ButtonText) text_items.get(index);
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
				ButtonText updated_button = (ButtonText) text_items.get(index);
				updated_button.loadFontTitle(font_number, font_size);
			}
		}
	}

	public void loadTextFont(int index, int font_number, int font_size){
		if (index < text_items.size()) {
			ButtonText updated_button = (ButtonText) text_items.get(index);
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
			ButtonText updated_button = (ButtonText) text_items.get(index);
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
			ButtonText updated_button = (ButtonText) text_items.get(index);
			updated_button.setFontSizeDescription(font_size);
		}
	}


	
}
