package com.julioterra.moodyjulio.dataviz.shapes.panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;

public class Panel extends ShapeRect {

	HashMap<String, ShapeText> elements;
	
	/***************************************
	 ** CONSTRUCTORS
	 ***************************************/	

	public Panel() {
		this(0,0,0,0,0);
	}

	public Panel(int x, int y, int size_x, int size_y, int color) {
		super(x, y, size_x, size_y, color);
		elements = new HashMap<String, ShapeText>();
	}	

	/***************************************
	 ** DISPLAY FUNCTIONS - GETTER & SETTER METHODS
	 ***************************************/	

	public void display(){
		super.display();

		Iterator<Map.Entry<String, ShapeText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeText> entry = (Map.Entry<String, ShapeText>) text_item_entries.next();
			if (entry.getKey().contains("background")) {
				ShapeText display_button = entry.getValue();	 
				display_button.display();
			}
		}

		text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeText> entry = (Map.Entry<String, ShapeText>) text_item_entries.next();
			if (!entry.getKey().contains("background")) {
				ShapeText display_button = entry.getValue();	 
				display_button.display();
			}
		}
	}
	
	/***************************************
	 ** DISPLAY FUNCTIONS - GETTER & SETTER METHODS
	 ***************************************/	

	public void setAllShiftMouseOverPanelItem(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over){
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.setShiftAllMouseOver(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
		}
	}

	public void setAllShiftMouseOverPanelItems(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over){
		Iterator<Map.Entry<String, ShapeText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeText> entry = (Map.Entry<String, ShapeText>) text_item_entries.next();
	    	ShapeText display_button = entry.getValue();	 
			display_button.setShiftAllMouseOver(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
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
		return elements.size();
	}

	// ADD ELEMENT TO PANEL
	public void addTextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode){
		ShapeText new_button = new ShapeText((int)(location.x+x), (int)(location.y+y), width, height, text, color, text_color, font_number, align_mode, false, false);
		elements.put(id, new_button);
	}

	public void addTextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode, float rotation){
		ShapeText new_button = new ShapeText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
		elements.put(id, new_button);
	}

	public void addTextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeText new_button = new ShapeText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
		new_button.setFontSizeAll(font_size);
		elements.put(id, new_button);
	}

	public void addTextRectButton(String id, int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int align_mode, float rotation){
		ShapeText new_button = new ShapeText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
		new_button.setTextLocationDescription(description_x, description_y);
		elements.put(id, new_button);
	}

	public void addTextRectButton(String id, int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeText new_button = new ShapeText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
		new_button.setTextLocationDescription(description_x, description_y);
		new_button.setFontSizeAll(font_size);
		elements.put(id, new_button);
	}

	public void addText(String id, int x, int y, String text, int color, int font_number, int align_mode){
		ButtonText new_button = new ButtonText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, false, false);
		elements.put(id, new_button);
	}

	public void addTextButton(String id, int x, int y, String text, int color, int font_number, int align_mode, boolean mouse_over_active, boolean mouse_press_active){
		ButtonText new_button = new ButtonText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, mouse_over_active, mouse_press_active);
		elements.put(id, new_button);
	}
	
	public void addTextButton(String id, int x, int y, String text, int color, int font_number, int font_size, int align_mode){
		ButtonText new_button = new ButtonText(x, y, text, color, font_number, align_mode, true, true);
		new_button.setFontSizeAll(font_size);
		elements.put(id, new_button);
	}

	public void addDropDownButton(String id, int x, int y, String text, int color, int font_number, int align_mode){
		ButtonDropDown new_button = new ButtonDropDown((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, true, true);
		elements.put(id, new_button);
	}

	public void addDropDownSubButton(String button_id, String text, int color, Object object, String method){
		if (elements.get(button_id) != null) {
			ButtonDropDown current_button = (ButtonDropDown) elements.get(button_id);
			current_button.addSubButton(text, color, object, method);							
		}
	}

	public void addDropDownSubButton(String button_id, String text, Object object, String method){
		if (elements.get(button_id) != null) {
			ButtonDropDown current_button = (ButtonDropDown) elements.get(button_id);
			current_button.addSubButton(text, object, method);				
		}
	}

	public void removeText(String button_id) {
		if (elements.get(button_id) != null) {
			elements.remove(button_id);
		}
	}

	// CONFIGURE MOUSE OVER EVENTS

	public void setShiftAllMouseOverElements(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over){
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.setShiftAllMouseOver(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
		}
	}

	public void addMouseOverActionElement(String button_id, String action_id, Object object, String method_name){
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.addMouseOverAction(action_id, object, method_name);
		}
	}

	public void addMouseOverActionPanel(String action_id, Object object, String method_name){
		this.addMouseOverAction(action_id, object, method_name);
		Iterator<Map.Entry<String, ShapeText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeText> entry = (Map.Entry<String, ShapeText>) text_item_entries.next();
	    	ShapeText updated_button = entry.getValue();	 
			updated_button.addMouseOverAction(action_id, object, method_name);
		}
	}
	
	public void addMouseOffActionElement(String button_id, String action_id, Object object, String method_name){
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.addMouseOffAction(action_id, object, method_name);
		}
	}
	
	public void addMouseOffActionPanel(String action_id, Object object, String method_name){
		this.addMouseOffAction(action_id, object, method_name);
		Iterator<Map.Entry<String, ShapeText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeText> entry = (Map.Entry<String, ShapeText>) text_item_entries.next();
	    	ShapeText updated_button = entry.getValue();	 
			updated_button.addMouseOffAction(action_id, object, method_name);
		}
	}
	
	public void addMouseClickedActionElement(String button_id, String action_id, Object object, String method_name){
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.addMouseClickedAction(action_id, object, method_name);
		}
	}
	
	public void addMouseClckedActionPanel(String action_id, Object object, String method_name){
		this.addMouseClickedAction(action_id, object, method_name);
		Iterator<Map.Entry<String, ShapeText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeText> entry = (Map.Entry<String, ShapeText>) text_item_entries.next();
	    	ShapeText updated_button = entry.getValue();	 
			updated_button.addMouseClickedAction(action_id, object, method_name);
		}
	}
	
	
	
	public void setTextVisible(String button_id) {
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.visible();
		}
	}

	public void setTextInvisible(String button_id) {
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.invisible();
		}
	}

	public void updateTextLocation(String button_id, int x, int y) {
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.setLocation(x, y);
		}
	}

	public void updateText(String button_id, String text) {
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.setName(text);
		}
	}

	public void updateTextDescription(String button_id, String text) {
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.setDescription(text);
		}
	}

	public void updateTextColor(String button_id, int color) {
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.setColorBaseARGB(color);
		}
	}

	public void loadTextFont(String button_id, int font_number, int font_size){
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.loadFontTitle(font_number, font_size);
		}		
	}

	public void setTextSize(String button_id, int font_size){
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.setFontSizeTitle(font_size);
		}
	}

	public void setTextSizeDescription(String button_id, int font_size){
		if (elements.get(button_id) != null) {
			ShapeText updated_button = (ShapeText) elements.get(button_id);
			updated_button.setFontSizeDescription(font_size);
		}
	}

	
}
