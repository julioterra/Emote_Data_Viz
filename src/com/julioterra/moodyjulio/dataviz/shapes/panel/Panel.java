package com.julioterra.moodyjulio.dataviz.shapes.panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import processing.core.PApplet;
import com.julioterra.moodyjulio.dataviz.shapes.*;
import com.julioterra.moodyjulio.dataviz.shapes.bar.BarSlice;
import com.julioterra.moodyjulio.dataviz.shapes.complex.CSElementStyleSettings;
import com.julioterra.moodyjulio.dataviz.shapes.complex.CSMouseEventSettings;
import com.julioterra.moodyjulio.dataviz.shapes.pie.PieSlice;

public class Panel extends ShapeRect implements CSMouseEventSettings, CSElementStyleSettings{

	HashMap<String, ShapeRectText> elements;
	ShapeViz elements_style;

	/***************************************
	 ** CONSTRUCTORS
	 ***************************************/	

	public Panel() {
		this(0,0,0,0,0);
	}

	public Panel(int x, int y, int size_x, int size_y, int color) {
		super(x, y, size_x, size_y, color);
		processing_app.registerMouseEvent(this);
		
		elements = new HashMap<String, ShapeRectText>();
		this.elements_style = new ShapeViz();
		this.elements_style.setShiftMouseOver(0, 0, 0, 0, false, false);		
		this.elements_style.setShiftMouseClicked(0, 0, 0, 0, false, false);		
		this.setMouseOverActive(false);
		this.setMouseClickedActive(false);
	}	

	public Panel(int x, int y, int size_x, int size_y) {
		this(x, y, size_x, size_y, Transparent_Color);
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

	public void mouseOver() {
		super.mouseOver();
		Iterator<Map.Entry<String, ShapeRectText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeRectText> entry = (Map.Entry<String, ShapeRectText>) text_item_entries.next();
			entry.getValue().mouseOver();
		}		
	}

	public void mouseClicked() {
		super.mouseClicked();
		Iterator<Map.Entry<String, ShapeRectText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			Map.Entry<String, ShapeRectText> entry = (Map.Entry<String, ShapeRectText>) text_item_entries.next();
			entry.getValue().mouseClicked();
		}		
	}

	/***********************************************************************************************************************
	 ** ADD ELEMENT METHODS
	 ** 
	 ************/	
	
	// ADD ELEMENT: TEXT RECT
	public void addElement_TextRect(String id, int x, int y, int width, int height, String text, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, color, text_color, font_number, align_mode, rotation, false, false);
		new_button.setFontSizeAll(font_size);
		elements.put(id, new_button);
	}

	// ADD ELEMENT: TEXT
	public void addElement_Text(String id, int x, int y, String text, int color, int font_number, int align_mode){
		ShapeText new_button = new ShapeText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, false, false);
		elements.put(id, new_button);
	}

	// ADD ELEMENT: TEXT RECT BUTTON
	public void addElement_TextRectButton(String id, int x, int y, int width, int height, String text, int description_x, int description_y, String description, int color, int text_color, int font_number, int font_size, int align_mode, float rotation){
		ShapeRectText new_button = new ShapeRectText(x, y, width, height, text, description, color, text_color, font_number, align_mode, rotation, true, true);
		new_button.description.setLocation(description_x, description_y);
		new_button.setFontSizeAll(font_size);
		new_button.setShiftMouseOver(elements_style.hue_shift_mouse_over, elements_style.sat_shift_mouse_over, elements_style.bright_shift_mouse_over, elements_style.size_shift_mouse_over, elements_style.label_toggle_mouse_over, elements_style.label_toggle_mouse_over);
		new_button.setShiftMouseClicked(elements_style.hue_shift_mouse_clicked, elements_style.sat_shift_mouse_clicked, elements_style.bright_shift_mouse_clicked, elements_style.size_shift_mouse_clicked, elements_style.label_toggle_mouse_clicked, elements_style.label_toggle_mouse_clicked);
		elements.put(id, new_button);
	}

	// ADD ELEMENT: TEXT BUTTON
	public void addElement_TextButton(String id, int x, int y, String text, int color, int font_number, int font_size, int align_mode){
		ShapeText new_button = new ShapeText(x, y, text, color, font_number, align_mode, true, true);
		new_button.setFontSizeAll(font_size);
		new_button.setShiftMouseOver(elements_style.hue_shift_mouse_over, elements_style.sat_shift_mouse_over, elements_style.bright_shift_mouse_over, elements_style.size_shift_mouse_over, elements_style.label_toggle_mouse_over, elements_style.label_toggle_mouse_over);
		new_button.setShiftMouseClicked(elements_style.hue_shift_mouse_clicked, elements_style.sat_shift_mouse_clicked, elements_style.bright_shift_mouse_clicked, elements_style.size_shift_mouse_clicked, elements_style.label_toggle_mouse_clicked, elements_style.label_toggle_mouse_clicked);
		elements.put(id, new_button);
	}

	// ADD ELEMENT: DROP DOWN BUTTON
	public void addElement_DropDownButton(String id, int x, int y, int width, int height, String text, int color, int font_number, int align_mode){
		ButtonDropDown new_button = new ButtonDropDown((int)(location.x+x), (int)(location.y+y), width, height, text, color, font_number, align_mode);
		new_button.setShiftMouseOver(elements_style.hue_shift_mouse_over, elements_style.sat_shift_mouse_over, elements_style.bright_shift_mouse_over, elements_style.size_shift_mouse_over, elements_style.label_toggle_mouse_over, elements_style.label_toggle_mouse_over);
		new_button.setShiftMouseClicked(elements_style.hue_shift_mouse_clicked, elements_style.sat_shift_mouse_clicked, elements_style.bright_shift_mouse_clicked, elements_style.size_shift_mouse_clicked, elements_style.label_toggle_mouse_clicked, elements_style.label_toggle_mouse_clicked);
		elements.put(id, new_button);
	}

	// ADD ELEMENT: DROP DOWN SUB-BUTTON
	public void addElement_DropDownSubButton(String button_id, String text, Object object, String method){
		if (elements.get(button_id) != null) {
			ButtonDropDown current_button = (ButtonDropDown) elements.get(button_id);
			current_button.addSubButton(text, object, method);				
		}
	}
	
	
	/**********************************************************************************
	 ** COMPLEX SHAPE MOUSE EVENT SETTING - INTERFACE [ComplexShapeMouseEventSetting]
	 **
	 ** Here is where all of the methods from the ComplexShapeMouseEventSetting interface
	 ** are defined.
	 **
	 ************/	
	
	// SET MOUSE OVER STYLING
	public void setElementAllShiftMouseOver(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_over, boolean description_shift_mouse_over){
		elements_style.setShiftMouseOver(hue_shift, saturation_shift, brightness_shift, scale_shift, label_shift_mouse_over, description_shift_mouse_over);		
		Iterator<Map.Entry<String, ShapeRectText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			ShapeRectText display_button = text_item_entries.next().getValue();	 
			if (display_button instanceof ShapeRectText || display_button instanceof ShapeText)
				display_button.setShiftMouseOver(elements_style.hue_shift_mouse_over, elements_style.sat_shift_mouse_over, elements_style.bright_shift_mouse_over, elements_style.size_shift_mouse_over, elements_style.label_toggle_mouse_over, elements_style.label_toggle_mouse_over);
		}
	}

	// SET MOUSE CLICK STYLING
	public void setElementAllShiftMouseClicked(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked){
		elements_style.setShiftMouseClicked(hue_shift, saturation_shift, brightness_shift, scale_shift, label_shift_mouse_clicked, description_shift_mouse_clicked);
		Iterator<Map.Entry<String, ShapeRectText>> 	text_item_entries = elements.entrySet().iterator();
		while (text_item_entries.hasNext()) {
			ShapeRectText display_button = text_item_entries.next().getValue();	 
			if (display_button instanceof ShapeRectText || display_button instanceof ShapeText)
				display_button.setShiftMouseClicked(elements_style.hue_shift_mouse_clicked, elements_style.sat_shift_mouse_clicked, elements_style.bright_shift_mouse_clicked, elements_style.size_shift_mouse_clicked, elements_style.label_toggle_mouse_clicked, elements_style.label_toggle_mouse_clicked);
		}
	}
	
	// SET ELEMENT: MOUSE OVER STYLING
	public void setElementShiftMouseOver(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_over, boolean description_shift_mouse_over){
		if (elements.get(button_id) != null) {
			elements.get(button_id).setShiftMouseOver(hue_shift, saturation_shift, brightness_shift, scale_shift, label_shift_mouse_over, description_shift_mouse_over);
		}
	}

	// SET ELEMENT: MOUSE CLICKED STYLING
	public void setElementShiftMouseClicked(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked){
		if (elements.get(button_id) != null) {
			elements.get(button_id).setShiftMouseClicked(hue_shift, saturation_shift, brightness_shift, scale_shift, label_shift_mouse_clicked, description_shift_mouse_clicked);
		}
	}

	// SET ELEMENT: ADD MOUSE OVER ACTION
	public void setElementAddMouseOverAction(String button_id, String action_id, Object object, String method_name){
		if (elements.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) elements.get(button_id);
			updated_button.addMouseOverAction(action_id, object, method_name);
		}
	}

	// SET ELEMENT: ADD MOUSE OFF ACTION
	public void setElementAddMouseOffAction(String button_id, String action_id, Object object, String method_name){
		if (elements.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) elements.get(button_id);
			updated_button.addMouseOffAction(action_id, object, method_name);
		}
	}
	
	// SET ELEMENT: ADD MOUSE CLICKED ACTION
	public void setElementAddMouseClickedAction(String button_id, String action_id, Object object, String method_name){
		if (elements.get(button_id) != null) {
			ShapeRectText updated_button = (ShapeRectText) elements.get(button_id);
			updated_button.addMouseClickedAction(action_id, object, method_name);
		}
	}
	

	/**********************************************************************************
	 ** COMPLEX SHAPE ELEMENT STYLE SETTING - INTERFACE [ComplexShape_ElementStyleSettings]
	 **
	 ** Here is where all of the methods from the ComplexShape_ElementStyleSettings interface
	 ** are defined.
	 **
	 **********************************************************************************
	 ** SINGLE ELEMENT STYLE CONTROL - VISIBILITY, SIZE, ROTATION, LOCATION - INTERFACE
	 ************/	
	
	// GET ELEMENT COUNT
	public int getElementCount() {
		return elements.size();
	}

	// REMOVE ELEMENT
	public void removeElement(String button_id) {
		if (elements.get(button_id) != null) {
			elements.remove(button_id);
		}
	}

	// SET ELEMENT VISIBLE
	public void setElementVisible(String button_id) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setVisible();
		}
	}

	// SET ELEMENT INVISIBLE
	public void setElementInvisible(String button_id) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setInvisible();
		}
	}

	// SET ELEMENT LOCATION
	public void setElementLocation(String button_id, int x, int y) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setLocation(x, y);
		}
	}

	// SET ELEMENT SIZE
	public void setElementSize(String button_id, float x, float y) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setSize(x, y);
		}
	}
	
	public void setElementSizeActive(String button_id, float x, float y) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setSizeActive(x, y);
		}
	}

	public void resetElementSize(String button_id) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).resetSize();
		}
	}

	// SET ELEMENT ROTATION ACTIVE
	public void setElementRotation(String button_id, float angle_in_degrees) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setRotation(angle_in_degrees);
		}
	}

	public void setElementRotationActive(String button_id, float angle_in_degrees) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setRotationActive(angle_in_degrees);
		}
	}

	public void resetElementRotation(String button_id) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).resetRotation();
		}
	}

	// SET ELEMENT COLOR
	public void setElementColor(String button_id, int color) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setColorARGB(color);
		}
	}

	public void setElementColorActive(String button_id, int argb) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setColorActiveARGB(argb);
		}
	}

	public void resetElementColor(String button_id) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).resetColor();
		}
	}

	// SET ELEMENT: LABEL
	public void setElementLabel(String button_id, String text) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).label.setText(text);
		}
	}

	// SET ELEMENT: DESCRIPTION
	public void setElementDescription(String button_id, String text) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).description.setText(text);
		}
	}

	// SET ELEMENT: TEXT, LABEL AND DESCRIPTION LOCATION
	public void setElementTextLocation(String button_id, float x, float y, float offset_x, float offset_y) {
		setElementLabelLocation(button_id, x, y);	
		setElementDescriptionLocation(button_id, offset_x+x, offset_y+y);
	}

	// SET ELEMENT: LABEL LOCATION
	public void setElementLabelLocation(String button_id, float x, float y) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).label.setLocation(x, y);
		}
	}

	// SET ELEMENT: DESCRIPTION LOCATION
	public void setElementDescriptionLocation(String button_id, float x, float y) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).description.setLocation(x, y);
		}
	}

	// SET ELEMENT: FONT
	public void loadElementFont(String button_id, int font_number, int font_size){
		this.loadElementLabelFont(button_id, font_number, font_size);
		this.loadElementDescriptionFont(button_id, font_number, font_size);
	}

	// SET ELEMENT: LABEL FONT
	public void loadElementLabelFont(String button_id, int font_number, int font_size){
		if (elements.get(button_id) != null) {
			elements.get(button_id).label.loadFont(font_number, font_size);
		}		
	}
	
	// SET ELEMENT: DESCRIPTION FONT
	public void loadElementDescriptionFont(String button_id, int font_number, int font_size){
		if (elements.get(button_id) != null) {
			elements.get(button_id).description.loadFont(font_number, font_size);
		}		
	}
		
	// SET ELEMENT: LABEL AND DESCRIPTION FONT SIZE
	public void setElementFontSize(String button_id, int font_size) {
		this.setElementLabelFontSize(button_id, font_size);
		this.setElementDescriptionFontSize(button_id, font_size);
	}

	// SET ELEMENT: LABEL FONT SIZE
	public void setElementLabelFontSize(String button_id, int font_size){
		if (elements.get(button_id) != null) {
			elements.get(button_id).label.setSize(font_size);
		}
	}

	// SET ELEMENT: DESCRIPTION FONT SIZE
	public void setElementDescriptionFontSize(String button_id, int font_size){
		if (elements.get(button_id) != null) {
			elements.get(button_id).description.setSize(font_size);
		}
	}

	// SET ELEMENT: FONT COLOR 
	public void setElementFontColor(String button_id, int font_color) {
		if (elements.get(button_id) != null) {
			elements.get(button_id).setFontColorAll(font_color);
		}
	}

	// SET ELEMENT LABEL FONT COLOR
	public void setElementLabelFontColor(String button_id, int font_color){
		if (elements.get(button_id) != null) 
			elements.get(button_id).label.setColorActiveARGB(font_color);
	}

	// SET ELEMENT DESCRIPTION FONT COLOR
	public void setElementDescriptionFontColor(String button_id, int font_color){
		if (elements.get(button_id) != null)
			elements.get(button_id).description.setColorActiveARGB(font_color);
	}

	// SET ELEMENT: VALUE
	public void setElementValue(String button_id, String value_id, double new_value) {
		if (elements.get(button_id) != null)
			elements.get(button_id).setValue(value_id, new_value);
	}

	// SET ELEMENT: INCREMENT VALUE
	public void setElementValueIncrement(String button_id, String value_id, double new_value) {
		if (elements.get(button_id) != null) {
			ShapeRectText element = elements.get(button_id);
			element.setValue(value_id, (element.getValue(value_id)+new_value));
		}
	}

	// GET ELEMENT VALUE
	public double getElementValue(String button_id, String value_id) {
		if (elements.get(button_id) != null)
			return elements.get(button_id).getValue(value_id);
		return 0;
	}

	// SET ELEMENT CATEGORY
	public void setElementCategory(String button_id, String value_id, String category) {
		if (elements.get(button_id) != null)
			elements.get(button_id).setCategory(value_id, category);
	}

	// GET ELEMENT CATEGORY
	public String getElementCategory(String button_id, String value_id) {
		if (elements.get(button_id) != null)
			return elements.get(button_id).getCategory(value_id);
		return "";				
	}
	
	/**********************************************************************************
	 ** ALL ELEMENT STYLE CONTROL - VISIBILITY, SIZE, ROTATION, LOCATION - INTERFACE
	 ************/	

	public void setElementAllVisible() {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().setVisible();
	}

	public void setElementAllInvisible() {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().setInvisible();
	}

	public void setElementAllLocation(float x, float y) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().setLocation(x, y);
	}

	public void setElementAllSize(float x, float y) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().setSize(x, y);
	}

	public void setElementAllSizeActive(float x, float y) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().setSizeActive(x, y);
	}

	public void resetElementAllSize() {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().resetSize();
	}

	public void setElementAllRotation(float angle_in_degrees) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().setRotation(angle_in_degrees);
	}

	public void setElementAllRotationActive(float angle_in_degrees) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().setRotationActive(angle_in_degrees);
	}

	public void resetElementAllRotation() {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().resetRotation();
	}

	public void setElementAllColor(int argb) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().setColorARGB(argb);
	}

	public void setElementAllColorActive(int argb) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().setColorActiveARGB(argb);
	}

	public void resetElementAllColor() {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().resetColor();
	}

	public void setElementAllTextLocation(float x, float y, float offset_x, float offset_y) {
		setElementAllLabelLocation(x,y);
		setElementAllDescriptionLocation(offset_x+x, offset_y+y);
	}

	public void setElementAllLabelLocation(float x, float y) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().label.setLocation(x, y);
	}

	public void setElementAllDescriptionLocation(float x, float y) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().description.setLocation(x, y);
	}

	public void loadElementAllFont(int font_number, int font_size) {
		loadElementAllLabelFont(font_number, font_size);
		loadElementAllDescriptionFont(font_number, font_size);
	}

	public void loadElementAllLabelFont(int font_number, int font_size) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().label.loadFont(font_number, font_size);
	}

	public void loadElementAllDescriptionFont(int font_number, int font_size) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().description.loadFont(font_number, font_size);
	}

	public void setElementAllFontSize(int font_size) {
		setElementAllLabelFontSize(font_size);
		setElementAllDescriptionFontSize(font_size);
	}

	public void setElementAllLabelFontSize(int font_size) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().label.setSize(font_size);
	}

	public void setElementAllDescriptionFontSize(int font_size) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().description.setSize(font_size);
	}

	public void setElementAllFontColor(int font_color) {
		setElementAllLabelFontColor(font_color);
		setElementAllDescriptionFontColor(font_color);
	}

	public void setElementAllLabelFontColor(int font_color) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().label.setColorARGB(font_color);
	}

	public void setElementAllDescriptionFontColor(int font_color) {
		Iterator<Map.Entry<String, ShapeRectText>> element_list = elements.entrySet().iterator();
		while (element_list.hasNext()) element_list.next().getValue().description.setColorARGB(font_color);
	}


}
