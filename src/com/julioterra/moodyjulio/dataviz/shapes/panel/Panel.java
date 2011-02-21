package com.julioterra.moodyjulio.dataviz.shapes.panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRectText;

public class Panel extends ShapeRect {

	HashMap<String, ShapeRectText> elements;

	protected float hue_shift_mouse_over_element;
	protected float sat_shift_mouse_over_element;
	protected float bright_shift_mouse_over_element;
	protected float scale_shift_mouse_over_element;
	protected boolean label_toggle_mouse_over_element;
	protected boolean description_toggle_mouse_over_element;

	protected float hue_shift_mouse_clicked_element;
	protected float sat_shift_mouse_clicked_element;
	protected float bright_shift_mouse_clicked_element;
	protected float scale_shift_mouse_clicked_element;
	protected boolean label_toggle_mouse_clicked_element;
	protected boolean description_toggle_mouse_clicked_element;
	

	/***************************************
	 ** CONSTRUCTORS
	 ***************************************/	

	public Panel() {
		this(0,0,0,0,0);
	}

	public Panel(int x, int y, int size_x, int size_y, int color) {
		super(x, y, size_x, size_y, color);
		elements = new HashMap<String, ShapeRectText>();
	
		this.hue_shift_mouse_over_element = 0;
		this.sat_shift_mouse_over_element = 0;
		this.bright_shift_mouse_over_element = 0;
		this.scale_shift_mouse_over_element = 0;
		this.label_toggle_mouse_over_element = false;
		this.description_toggle_mouse_over_element = false;		

		this.hue_shift_mouse_clicked_element = 0;
		this.sat_shift_mouse_clicked_element = 0;
		this.bright_shift_mouse_clicked_element = 0;
		this.scale_shift_mouse_clicked_element = 0;
		this.label_toggle_mouse_clicked_element = false;
		this.description_toggle_mouse_clicked_element = false;		
	}	

	/***************************************
	 ** DISPLAY FUNCTIONS - GETTER & SETTER METHODS
	 ***************************************/	

	public void display(){
		if (visible) {
			super.display();
			Iterator<Map.Entry<String, ShapeRectText>> 	text_item_entries = elements.entrySet().iterator();
			while (text_item_entries.hasNext()) {
				Map.Entry<String, ShapeRectText> entry = (Map.Entry<String, ShapeRectText>) text_item_entries.next();
				if (entry.getKey().contains("background")) {
					ShapeRectText display_button = entry.getValue();	 
					display_button.display();
					display_button.displayText();
				}
			}
			text_item_entries = elements.entrySet().iterator();
			while (text_item_entries.hasNext()) {
				Map.Entry<String, ShapeRectText> entry = (Map.Entry<String, ShapeRectText>) text_item_entries.next();
				if (!entry.getKey().contains("background")) {
					ShapeRectText display_button = entry.getValue();	 
					display_button.display();
					display_button.displayText();
				}
			}
			super.displayText();
		}		
	}
	
	/***********************************************************************************************************************
	 ** MOUSE EVENT MANAGEMENT METHODS
	 **
	 ** These methods are enable the adding of actions and behaviors to mouse over
	 ** and mouse clicked events on the panel.
	 ** 
	 ************/	

	public void setShiftAllMouseOver(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_over, boolean description_shift_mouse_over){
		if (elements.get(button_id) != null) {
			elements.get(button_id).setShiftAllMouseOver(hue_shift, saturation_shift, brightness_shift, scale_shift, label_shift_mouse_over, description_shift_mouse_over);
		}
	}

	public void setShiftAllMouseOver(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_over, boolean description_shift_mouse_over){
		this.hue_shift_mouse_over_element = hue_shift;
		this.sat_shift_mouse_over_element = saturation_shift;
		this.bright_shift_mouse_over_element = brightness_shift;
		this.scale_shift_mouse_over_element = scale_shift;
		this.label_toggle_mouse_over_element = label_shift_mouse_over;
		this.description_toggle_mouse_over_element = description_shift_mouse_over;
		
		Iterator<Map.Entry<String, ShapeRectText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			ShapeRectText display_button = text_item_entries.next().getValue();	 
			if (display_button instanceof ShapeRectText || display_button instanceof ShapeText) {
				display_button.setShiftAllMouseOver(hue_shift_mouse_over_element, sat_shift_mouse_over_element, bright_shift_mouse_over_element, scale_shift_mouse_over_element, label_toggle_mouse_over_element, description_toggle_mouse_over_element);
			}
		}
	}

	// ADD MOUSE CLICK STYLING
	public void setAllShiftMouseClicked(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked){
		if (elements.get(button_id) != null) {
			elements.get(button_id).setShiftAllMouseClicked(hue_shift, saturation_shift, brightness_shift, scale_shift, label_shift_mouse_clicked, description_shift_mouse_clicked);
		}
	}

	public void setAllShiftMouseClicked(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked){
		this.hue_shift_mouse_clicked_element = hue_shift;
		this.sat_shift_mouse_clicked_element = saturation_shift;
		this.bright_shift_mouse_clicked_element = brightness_shift;
		this.scale_shift_mouse_clicked_element = scale_shift;
		this.label_toggle_mouse_clicked_element = label_shift_mouse_clicked;
		this.description_toggle_mouse_clicked_element = description_shift_mouse_clicked;
		
		Iterator<Map.Entry<String, ShapeRectText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			ShapeRectText display_button = text_item_entries.next().getValue();	 
			if (display_button instanceof ShapeRectText || display_button instanceof ShapeText) {
				display_button.setShiftAllMouseClicked(hue_shift_mouse_over_element, sat_shift_mouse_over_element, bright_shift_mouse_over_element, scale_shift_mouse_over_element, label_toggle_mouse_over_element, description_toggle_mouse_over_element);
			}
		}
	}

	// ADD MOUSE OVER ACTION TO ELEMENT
	public void addMouseOverAction(String button_id, String action_id, Object object, String method_name){
		if (elements.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) elements.get(button_id);
			updated_button.addMouseOverAction(action_id, object, method_name);
		}
	}

	public void addMouseOffAction(String button_id, String action_id, Object object, String method_name){
		if (elements.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) elements.get(button_id);
			updated_button.addMouseOffAction(action_id, object, method_name);
		}
	}
	
	public void addMouseClickedAction(String button_id, String action_id, Object object, String method_name){
		if (elements.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) elements.get(button_id);
			updated_button.addMouseClickedAction(action_id, object, method_name);
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
	
	public int getNumberOfItems() {
		return elements.size();
	}

	// ADD ELEMENT TO PANEL
	public void addElement_TextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode){
		ShapeRectText new_button = new ShapeRectText((int)(location.x+x), (int)(location.y+y), width, height, text, color, text_color, font_number, align_mode, false, false);
		elements.put(id, new_button);
	}

	public void addElement_TextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
		elements.put(id, new_button);
	}

	public void addElement_TextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
		new_button.setFontSizeAll(font_size);
		elements.put(id, new_button);
	}

	public void addElement_Text(String id, int x, int y, String text, int color, int font_number, int align_mode){
		ShapeText new_button = new ShapeText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, false, false);
		elements.put(id, new_button);
	}

	public void addElement_TextRectButton(String id, int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
		new_button.description.setLocation(description_x, description_y);
		new_button.setShiftAllMouseOver(hue_shift_mouse_over_element, sat_shift_mouse_over_element, bright_shift_mouse_over_element, scale_shift_mouse_over_element, label_toggle_mouse_over_element, description_toggle_mouse_over_element);
		elements.put(id, new_button);
	}

	public void addElement_TextRectButton(String id, int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
		new_button.description.setLocation(description_x, description_y);
		new_button.setFontSizeAll(font_size);
		new_button.setShiftAllMouseOver(hue_shift_mouse_over_element, sat_shift_mouse_over_element, bright_shift_mouse_over_element, scale_shift_mouse_over_element, label_toggle_mouse_over_element, description_toggle_mouse_over_element);
		elements.put(id, new_button);
	}

	public void addElement_TextButton(String id, int x, int y, String text, int color, int font_number, int align_mode, boolean mouse_over_active, boolean mouse_press_active){
		ShapeText new_button = new ShapeText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, mouse_over_active, mouse_press_active);
		new_button.setShiftAllMouseOver(hue_shift_mouse_over_element, sat_shift_mouse_over_element, bright_shift_mouse_over_element, scale_shift_mouse_over_element, label_toggle_mouse_over_element, description_toggle_mouse_over_element);
		elements.put(id, new_button);
	}
	
	public void addElement_TextButton(String id, int x, int y, String text, int color, int font_number, int font_size, int align_mode){
		ShapeText new_button = new ShapeText(x, y, text, color, font_number, align_mode, true, true);
		new_button.setFontSizeAll(font_size);
		new_button.setShiftAllMouseOver(hue_shift_mouse_over_element, sat_shift_mouse_over_element, bright_shift_mouse_over_element, scale_shift_mouse_over_element, label_toggle_mouse_over_element, description_toggle_mouse_over_element);
		elements.put(id, new_button);
	}

	public void addElement_DropDownButton(String id, int x, int y, int width, int height, String text, int color, int font_number, int align_mode){
		ButtonDropDown new_button = new ButtonDropDown((int)(location.x+x), (int)(location.y+y), width, height, text, color, font_number, align_mode);
		new_button.setShiftAllMouseOver(hue_shift_mouse_over_element, sat_shift_mouse_over_element, bright_shift_mouse_over_element, scale_shift_mouse_over_element, label_toggle_mouse_over_element, description_toggle_mouse_over_element);
		elements.put(id, new_button);
	}

	public void addElement_DropDownSubButton(String button_id, String text, int color, Object object, String method){
		if (elements.get(button_id) != null) {
			ButtonDropDown current_button = (ButtonDropDown) elements.get(button_id);
			current_button.addSubButton(text, color, object, method);							
		}
	}

	public void addElement_DropDownSubButton(String button_id, String text, Object object, String method){
		if (elements.get(button_id) != null) {
			ButtonDropDown current_button = (ButtonDropDown) elements.get(button_id);
			current_button.addSubButton(text, object, method);				
		}
	}

	public void removeElement(String button_id) {
		if (elements.get(button_id) != null) {
			elements.remove(button_id);
		}
	}
	
	public void setElementVisible(String button_id) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setVisible();
		}
	}

	public void setElementInvisible(String button_id) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setInvisible();
		}
	}

	public void setElementLabel(String button_id, String text) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).label.setText(text);
		}
	}

	public void setElementDescription(String button_id, String text) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).description.setText(text);
		}
	}

	public void setElementLocation(String button_id, int x, int y) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setLocation(x, y);
		}
	}

	public void setElementColor(String button_id, int color) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setColorARGB(color);
		}
	}

	public void loadTextFont(String button_id, int font_number, int font_size){
		if (elements.get(button_id) != null) {
			elements.get(button_id).label.loadFont(font_number, font_size);
		}		
	}

	public void setElementLabelFontSize(String button_id, int font_size){
		if (elements.get(button_id) != null) {
			elements.get(button_id).label.setSize(font_size);
		}
	}

	public void setElementDescriptionFontSize(String button_id, int font_size){
		if (elements.get(button_id) != null) {
			elements.get(button_id).description.setSize(font_size);
		}
	}

	
}
