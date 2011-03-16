package com.julioterra.moodyjulio.dataviz.shapes.pie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRectText;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeViz;
import com.julioterra.moodyjulio.dataviz.shapes.complex.CSElementStyleSettings;
import com.julioterra.moodyjulio.dataviz.shapes.complex.CSMouseEventSettings;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ShapeText;

import processing.core.PApplet;
import processing.core.PVector;

public class Pie extends ShapeCircle implements CSMouseEventSettings, CSElementStyleSettings {
	
	public ArrayList<PieSlice> elements;
	ShapeViz elements_style;

	public Pie(int x, int y, float diameter, int pie_shape_type) {
		super(x, y, (diameter/2), 0);
		processing_app.registerMouseEvent(this);

		this.pie_shape_type = pie_shape_type;
		this.elements = new ArrayList<PieSlice>();		

		this.elements_style = new ShapeViz();
		this.elements_style.setShiftMouseOver(0, 0, 0, 0, false, false);		
		this.elements_style.setShiftMouseClicked(0, 0, 0, 0, false, false);		

		this.setColorActiveARGB(Transparent_Color);
		this.setMouseOverActive(false);
		this.setMouseClickedActive(false);
	}

	public Pie(int x, int y, float diameter, int number_of_slices, int pie_shape_type) {
		this(x, y, diameter, pie_shape_type);
		this.setNumberOfSlices(number_of_slices);
	}
	
	/*****************************************
	 ** SET MODE METHODS 
	 **/

	public void setNumberOfSlices(int number_of_slices) {
		this.elements = new ArrayList<PieSlice>();
		if (number_of_slices > PApplet.degrees(this.angle_start)) {
			double temp_angle_start = 0;
			double degrees_per_slice = (float)(360.0 / (float)number_of_slices);
			double size_in_percent = (float)((float)degrees_per_slice / 360.0);
			for (int i = 0; i < number_of_slices; i++) {
				if (this.pie_shape_type == PIE_ARC_SET_RADIUS) {
					this.elements.add(new PieSliceArc((int)this.location.x, (int)this.location.y, this.radius_base.x, (float) size_in_percent, (float) temp_angle_start, this.color_active));				
					 this.setValue("base", 0);
				}
				else if (this.pie_shape_type == PIE_LINE_VAR_RADIUS)
					this.elements.add(new PieSliceLine((int)this.location.x, (int)this.location.y, 0, (float)size_in_percent, (float) temp_angle_start, this.color_active));
				temp_angle_start = i * degrees_per_slice;
			}
		}
	}
	
	/*********************************************************
	 ** DISPLAY METHOD 
	 **/

	public void display() {
		super.display();
		for (int i = 0; i < elements.size(); i++) {
			PieSlice slice = elements.get(i);
			slice.display();
			slice.displayText();
		}
		super.displayText();
	}

	public void mouseOver() {
	super.mouseOver();
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).mouseOver();
		}		
	}

	public void mouseClicked() {
		super.mouseClicked();
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).mouseClicked();
		}		
	}

	  /*********************************************************
	   ** MOVE, SCALE and SIZE METHODS 
	   ************/	

	  public void move(float x, float y) {
		  super.move(x, y);
		  for (int i = 0; i < elements.size(); i++) {
			  PieSlice slice = elements.get(i);
			  slice.move(x, y);
		  }
	  }
	  
	  public void setScale(float new_scale) {
		  super.setScale(new_scale);
		  for (int i = 0; i < elements.size(); i++) {
			  PieSlice slice = elements.get(i);
			  slice.setScale(new_scale);
		  }
		  this.shiftScaleReset();
	  }

	  public void setSize(float diameter) {
		  super.setSize(diameter, diameter);
		  for (int i = 0; i < elements.size(); i++) {
			  PieSlice slice = elements.get(i);
			  slice.setSize(diameter, diameter);
		  }
		  this.shiftScaleReset();
	  }

	  public void setSizeActive(float diameter) {
		  super.setSizeActive(diameter, diameter);
		  for (int i = 0; i < elements.size(); i++) {
			  PieSlice slice = elements.get(i);
			  slice.setSizeActive(diameter, diameter);
		  }
		  this.shiftScaleReset();
	  }

	/***********************************************************************************************************************
	 ** TURN AND WIDTH METHOD - FOR CIRCLE SHAPES ONLY
	 ************/	
	  public void setWidthAll(float width) {
		  for (int i = 0; i < elements.size(); i++) {
				PieSlice slice = elements.get(i);
				slice.setWidth(width);
		  }
	  }

	/***********************************************************************************************************************
	 ** ADD ELEMENT METHODS
	 ** 
	 ************/	
	  public void addElement(String name, String description, double value, int color){
		  this.addElement(value, color);
		  PieSlice element = elements.get(elements.size()-1);
		  element.label.setText(name);
		  element.description.setText(description);		  
		  element.setShiftMouseOver(elements_style.hue_shift_mouse_over, elements_style.sat_shift_mouse_over, elements_style.bright_shift_mouse_over, elements_style.size_shift_mouse_over, elements_style.label_toggle_mouse_over, elements_style.label_toggle_mouse_over);
		  element.setShiftMouseClicked(elements_style.hue_shift_mouse_clicked, elements_style.sat_shift_mouse_clicked, elements_style.bright_shift_mouse_clicked, elements_style.size_shift_mouse_clicked, elements_style.label_toggle_mouse_clicked, elements_style.label_toggle_mouse_clicked);
		  if (elements_style.label.font_loaded) element.label.loadFont(elements_style.label.font_number, (int)(elements_style.label.getFontSize()));
		  if (elements_style.description.font_loaded) element.description.loadFont(elements_style.description.font_number, (int)(elements_style.description.getFontSize()));
	  }
	  
	  private void addElement(double value, int color){
		  if (this.pie_shape_type == PIE_ARC_SET_RADIUS) {
			  PieSlice slice = new PieSliceArc((int)this.location.x, (int)this.location.y, this.radius_base.x, 0, 0, color);
			  slice.setValue("base", value);
			  this.elements.add(slice);
			  this.setValueIncrement("base", value);
//			  this.value_one += value;
		  }
		  else if (this.pie_shape_type == PIE_LINE_VAR_RADIUS) {
			  PieSlice slice = new PieSliceLine((int)this.location.x, (int)this.location.y, 0, 0, 0, this.color_active);
			  slice.setValue("base", value);
			  this.elements.add(slice);
			  if (value > this.getValue("base")) this.setValue("base", value); 
//			  if (value > this.value_one) this.value_one = value; 
		  }
		  this.applyValuesToSliceDisplay("base");
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
		for (int i = 0; i < elements.size(); i++) {
			PieSlice slice = elements.get(i);
			slice.setShiftMouseOver(elements_style.hue_shift_mouse_over, elements_style.sat_shift_mouse_over, elements_style.bright_shift_mouse_over, elements_style.size_shift_mouse_over, elements_style.label_toggle_mouse_over, elements_style.label_toggle_mouse_over);
		}
	}

	// SET MOUSE CLICK STYLING
	public void setElementAllShiftMouseClicked(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked){
		elements_style.setShiftMouseClicked(hue_shift, saturation_shift, brightness_shift, scale_shift, label_shift_mouse_clicked, description_shift_mouse_clicked);
		for (int i = 0; i < elements.size(); i++) {
			PieSlice slice = elements.get(i);
			slice.setShiftMouseClicked(elements_style.hue_shift_mouse_clicked, elements_style.sat_shift_mouse_clicked, elements_style.bright_shift_mouse_clicked, elements_style.size_shift_mouse_clicked, elements_style.label_toggle_mouse_clicked, elements_style.label_toggle_mouse_clicked);
		}
	}
	
	// SET ELEMENT: MOUSE OVER STYLING
	public void setElementShiftMouseOver(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean label_shift_mouse_over, boolean description_shift_mouse_over) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setShiftMouseOver(hue_shift, saturation_shift, brightness_shift, radius_shift, label_shift_mouse_over, description_shift_mouse_over);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's removeElement method " + e.getMessage());
		}
	}

	// SET ELEMENT: MOUSE CLICKED STYLING
	public void setElementShiftMouseClicked(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setShiftMouseClicked(hue_shift, saturation_shift, brightness_shift, scale_shift, label_shift_mouse_clicked, description_shift_mouse_clicked);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's removeElement method " + e.getMessage());
		}
	}

	// SET ELEMENT: ADD MOUSE OVER ACTION
	public void setElementAddMouseOverAction(String button_id, String action_id, Object object, String method_name){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).addMouseOverAction(action_id, object, method_name);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's removeElement method " + e.getMessage());
		}
	}

	// SET ELEMENT: ADD MOUSE OFF ACTION
	public void setElementAddMouseOffAction(String button_id, String action_id, Object object, String method_name){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).addMouseOffAction(action_id, object, method_name);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's removeElement method " + e.getMessage());
		}
	}
	
	// SET ELEMENT: ADD MOUSE CLICKED ACTION
	public void setElementAddMouseClickedAction(String button_id, String action_id, Object object, String method_name){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).addMouseClickedAction(action_id, object, method_name);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's removeElement method " + e.getMessage());
		}
	}
		
	
	/**********************************************************************************
	 ** ELEMENT VISIBILITY, SIZE, LOCATION - INTERFACE
	 ** 
	 ************/	

	// GET ELEMENT COUNT
	public int getElementCount() {
		return elements.size();
	}
	
	// REMOVE ELEMENT
	public void removeElement(String button_id) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.remove(Integer.parseInt(button_id));
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's removeElement method " + e.getMessage());
		}
	}

	// SET ELEMENT VISIBLE
	public void setElementVisible(String button_id) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setVisible();
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementVisible method " + e.getMessage());
		}
	}

	// SET ELEMENT INVISIBLE
	public void setElementInvisible(String button_id) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setInvisible();
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementInvisible method " + e.getMessage());
		}
	}

	// SET ELEMENT LOCATION
	public void setElementLocation(String button_id, int x, int y) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setLocation(x, y);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementLocation method " + e.getMessage());
		}
	}

	// SET ELEMENT SIZE
	public void setElementSize(String button_id, float x, float y) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setSize(x, y);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementSize method " + e.getMessage());
		}
	}

	public void setElementSizeActive(String button_id, float x, float y) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setSizeActive(x, y);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementSizeActive method " + e.getMessage());
		}
	}

	public void resetElementSize(String button_id) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).resetSize();
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's resetElementSize method " + e.getMessage());
		}
	}

	// SET ELEMENT ROTATION
	public void setElementRotation(String button_id, float angle_in_degrees) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setRotation(angle_in_degrees);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementRotation method " + e.getMessage());
		}
	}

	public void setElementRotationActive(String button_id, float angle_in_degrees) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setRotationActive(angle_in_degrees);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementRotationActive method " + e.getMessage());
		}
	}

	public void resetElementRotation(String button_id) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).resetRotation();
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's resetElementRotation method " + e.getMessage());
		}
	}
	
	// SET ELEMENT COLOR
	public void setElementColor(String button_id, int argb) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setColorARGB(argb);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementColor method " + e.getMessage());
		}
	}

	public void setElementColorActive(String button_id, int argb) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setColorActiveARGB(argb);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementColorActive method " + e.getMessage());
		}
	}

	public void resetElementColor(String button_id) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).resetColor();
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's resetElementColor method " + e.getMessage());
		}
	}

	// SET ELEMENT: LABEL
	public void setElementLabel(String button_id, String name) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).label.setText(name);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementLabel method " + e.getMessage());
		}
	}

	// SET ELEMENT: DESCRIPTION
	public void setElementDescription(String button_id, String description) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).description.setText(description);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementDescription method " + e.getMessage());
		}
	}

	public void setElementTextLocation(String button_id, float x, float y, float offset_x, float offset_y) {
		setElementLabelLocation(button_id, x, y);		
		setElementDescriptionLocation(button_id, offset_x+x, offset_y+y);		
	}

	// SET ELEMENT: LABEL LOCATION
	public void setElementLabelLocation(String button_id, float x, float y) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).label.setLocation(x, y);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's loadElementLabelLocation method " + e.getMessage());
		}
	}

	// SET ELEMENT: DESCRIPTION LOCATION
	public void setElementDescriptionLocation(String button_id, float x, float y) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).description.setLocation(x, y);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's loadElementDescriptionLocation method " + e.getMessage());
		}
	}

	// SET ELEMENT: FONT
	public void loadElementFont(String button_id, int font_number, int font_size){
		this.loadElementLabelFont(button_id, font_number, font_size);
		this.loadElementDescriptionFont(button_id, font_number, font_size);
	}

	// SET ELEMENT: LABEL FONT
	public void loadElementLabelFont(String button_id, int font_number, int font_size){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).label.loadFont(font_number, font_size);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's loadElementLabelFont method " + e.getMessage());
		}
	}
	
	// SET ELEMENT: DESCRIPTION FONT
	public void loadElementDescriptionFont(String button_id, int font_number, int font_size){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).description.loadFont(font_number, font_size);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's loadElementDescriptionFont method " + e.getMessage());
		}
	}

	// SET ELEMENT: LABEL AND DESCRIPTION FONT SIZE
	public void setElementFontSize(String button_id, int font_size) {
		this.setElementLabelFontSize(button_id, font_size);
		this.setElementDescriptionFontSize(button_id, font_size);
	}

	// SET ELEMENT: LABEL FONT SIZE
	public void setElementLabelFontSize(String button_id, int font_size){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).label.setSize(font_size);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementLabelFontSize method " + e.getMessage());
		}
	}

	// SET ELEMENT: DESCRIPTION FONT SIZE
	public void setElementDescriptionFontSize(String button_id, int font_size){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).description.setSize(font_size);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementDescriptionFontSize method " + e.getMessage());
		}
	}

	// SET ELEMENT: FONT COLOR 
	public void setElementFontColor(String button_id, int font_color) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setFontColorAll(font_color);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementFontColor method " + e.getMessage());
		}
	}

	// SET ELEMENT LABEL FONT COLOR
	public void setElementLabelFontColor(String button_id, int font_color){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).label.setColorActiveARGB(font_color);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementLabelFontColor method " + e.getMessage());
		}
	}

	// SET ELEMENT DESCRIPTION FONT COLOR
	public void setElementDescriptionFontColor(String button_id, int font_color){
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).description.setColorActiveARGB(font_color);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementDescriptionFontColor method " + e.getMessage());
		}
	}

	// SET ELEMENT: VALUE
	public void setElementValue(String button_id, String value_id, double new_value) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setValue(value_id, new_value);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementDescriptionFontColor method " + e.getMessage());
		}		
	}

	// SET ELEMENT: INCREMENT VALUE
	public void setElementValueIncrement(String button_id, String value_id, double new_value) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) { 
				PieSlice element = elements.get(Integer.parseInt(button_id));
				element.setValue(value_id, (element.getValue(value_id)+new_value));
			}
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementDescriptionFontColor method " + e.getMessage());
		}				
	}

	// GET ELEMENT VALUE
	public double getElementValue(String button_id, String value_id) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) return elements.get(Integer.parseInt(button_id)).getValue(value_id);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementDescriptionFontColor method " + e.getMessage());
		}		
		return 0;		
	}

	// SET ELEMENT CATEGORY
	public void setElementCategory(String button_id, String value_id, String category) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setCategory(value_id, category);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementDescriptionFontColor method " + e.getMessage());
		}				
	}

	// GET ELEMENT CATEGORY
	public String getElementCategory(String button_id, String value_id) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) return elements.get(Integer.parseInt(button_id)).getCategory(value_id);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementDescriptionFontColor method " + e.getMessage());
		}		
		return "";				
	}

	/*******************************/	
	/** NOT PART OF THE INTERFACE **/	
	/*******************************/	
	// SET ELEMENT ANGLE EXTEND
	public void setElementAngleExtent(String button_id, float slice_angle) {
		try {
			if (elements.size() > Integer.parseInt(button_id)) elements.get(Integer.parseInt(button_id)).setAngleExtentDegrees(slice_angle);
		} catch (Exception e) {
			PApplet.println("error converting string to number in Pie's setElementLocation method " + e.getMessage());
		}
	}

	/**********************************************************************************
	 ** ALL ELEMENT STYLE CONTROL - VISIBILITY, SIZE, ROTATION, LOCATION - INTERFACE
	 ************/	

	// SET ELEMENT ALL: VISIBLE
	public void setElementAllVisible() {
		elements_style.setVisible();
		for (int i = 0; i < elements.size(); i++) elements.get(i).setVisible();
	}

	// SET ELEMENT ALL: INVISIBLE
	public void setElementAllInvisible() {
		elements_style.setInvisible();
		for (int i = 0; i < elements.size(); i++) elements.get(i).setInvisible();
	}

	// SET ELEMENT ALL: LOCATION
	public void setElementAllLocation(float x, float y) {
		elements_style.setLocation(x, y);
		for (int i = 0; i < elements.size(); i++) elements.get(i).setLocation(x, y);
	}

	// SET ELEMENT ALL: SIZE
	public void setElementAllSize(float x, float y) {
		elements_style.setSize(x, y);
		for (int i = 0; i < elements.size(); i++) elements.get(i).setSize(x, y);
	}
	public void setElementAllSizeActive(float x, float y) {
		elements_style.setSizeActive(x, y);
		for (int i = 0; i < elements.size(); i++) elements.get(i).setSizeActive(x, y);
	}

	public void resetElementAllSize() {
		for (int i = 0; i < elements.size(); i++) elements.get(i).resetSize();
	}


	// SET ELEMENT ALL: ROTATION
	public void setElementAllRotation(float angle_in_degrees) {
		elements_style.setRotation(angle_in_degrees);
		for (int i = 0; i < elements.size(); i++) 
			elements.get(i).setRotation(angle_in_degrees);
	}

	public void setElementAllRotationActive(float angle_in_degrees) {
		elements_style.setRotationActive(angle_in_degrees);
		for (int i = 0; i < elements.size(); i++) 
			elements.get(i).setRotationActive(angle_in_degrees);
	}

	public void resetElementAllRotation() {
		for (int i = 0; i < elements.size(); i++) elements.get(i).resetRotation();
	}
	

	// SET ELEMENT ALL: COLOR
	public void setElementAllColor(int argb) {
		elements_style.setColorARGB(argb);
		for (int i = 0; i < elements.size(); i++) 
			elements.get(i).setColorARGB(argb);
	}

	public void setElementAllColorActive(int argb) {
		elements_style.setColorActiveARGB(argb);
		for (int i = 0; i < elements.size(); i++) 
			elements.get(i).setColorActiveARGB(argb);
	}

	public void resetElementAllColor() {
		for (int i = 0; i < elements.size(); i++) elements.get(i).resetColor();
	}

	// SET ELEMENT ALL: TEXT LOCATION
	public void setElementAllTextLocation(float x, float y, float offset_x, float offset_y) {
		setElementAllLabelLocation(x,y);
		setElementAllDescriptionLocation(offset_x+x, offset_y+y);
	}

	public void setElementAllLabelLocation(float x, float y) {
		elements_style.label.setLocation(x, y);
		for (int i = 0; i < elements.size(); i++) 
			elements.get(i).label.setLocation(x, y);
	}

	public void setElementAllDescriptionLocation(float x, float y) {
		elements_style.description.setLocation(x, y);
		for (int i = 0; i < elements.size(); i++) 
			elements.get(i).description.setLocation(x, y);
	}

	// SET ELEMENT ALL: LOAD FONT LABEL AND DESCRIPTION
	public void loadElementAllFont(int font_number, int font_size) {
		loadElementAllLabelFont(font_number, font_size);		
		loadElementAllDescriptionFont(font_number, font_size);		
	}
			
	public void loadElementAllLabelFont(int new_font_number, int size) {
		elements_style.label.loadFont(new_font_number, size);
		for (int i = 0; i < elements.size(); i++) 
			elements.get(i).label.loadFont(new_font_number, size);
	}

	public void loadElementAllDescriptionFont(int new_font_number, int size) {
		elements_style.description.loadFont(new_font_number, size);
		for (int i = 0; i < elements.size(); i++)
			elements.get(i).description.loadFont(new_font_number, size);
	}

	// SET ELEMENT ALL: FONT SIZE LABEL AND DESCRIPTION
	public void setElementAllFontSize(int font_size) {
		setElementAllLabelFontSize(font_size);
		setElementAllDescriptionFontSize(font_size);
	}

	public void setElementAllLabelFontSize(int font_size) {
		elements_style.label.setSize(font_size);
		for (int i = 0; i < elements.size(); i++)
			elements.get(i).label.setSize(font_size);
	}

	public void setElementAllDescriptionFontSize(int font_size) {
		elements_style.description.setSize(font_size);
		for (int i = 0; i < elements.size(); i++)
			elements.get(i).description.setSize(font_size);
	}

	// SET ELEMENT ALL: FONT COLOR LABEL AND DESCRIPTION
	public void setElementAllFontColor(int font_color) {
		setElementAllLabelFontColor(font_color);
		setElementAllDescriptionFontColor(font_color);
	}

	public void setElementAllLabelFontColor(int font_color) {
		elements_style.label.setColorARGB(font_color);
		for (int i = 0; i < elements.size(); i++)
			elements.get(i).label.setColorARGB(font_color);
	}

	public void setElementAllDescriptionFontColor(int font_color) {
		elements_style.description.setColorARGB(font_color);
		for (int i = 0; i < elements.size(); i++)
			elements.get(i).description.setColorARGB(font_color);
	}

	// ******************************************************
	// SET VALUE and CALCULATE EXTEND ANGLES
	// functionality to add here
	//		calculate the change in overall pie size with all angles added up
	//		redefine the size of each slice based on new pie size calculation
	
	public void setElementValue(int index, String value_id, float new_value) {
		PieSlice set_slice = elements.get(index);
		set_slice.setValue(value_id, new_value);
	}
	
	public void applyValuesToElementAngle(String value_id) {
	    int total_value = 0;
		for (int i = 0; i < elements.size(); i++) {
			PieSlice slice = elements.get(i);
			total_value += slice.getValue(value_id);
		}
		
		double slice_start_degrees = 0; 
		for (int j = 0; j < elements.size(); j++) {
			PieSlice element = this.elements.get(j);
			double slice_extent_percent = element.getValue(value_id) / total_value;
			double slice_extent_degrees = slice_extent_percent * 360;
			this.setElementRotation(""+j, (float) slice_start_degrees);
			this.setElementAngleExtent(""+j, (float) slice_extent_degrees);
			slice_start_degrees += slice_extent_degrees;
		}
		this.shiftScaleReset();
	}
	
	
	public void applyValuesToElementSize(String value_id) {
		int max_value = 0;
		for (int i = 0; i < elements.size(); i++) {
			PieSlice slice = elements.get(i);
			if (max_value < (int)slice.getValue(value_id)) max_value = (int)slice.getValue(value_id);
		}

		for (int j = 0; j < elements.size(); j++) {
			PieSlice element = this.elements.get(j);
			float radius_x = (float) ( ((element.getValue(value_id)/2)/max_value) * (this.size_base.x) );
			float radius_y = (float) ( ((element.getValue(value_id)/2)/max_value) * (this.size_base.y) );
			element.setSize(radius_x*2, radius_y*2);	
		}
	}
	
	public void applyValuesToSliceDisplay(String value_id) {
		if (this.pie_shape_type == PIE_ARC_SET_RADIUS)
			applyValuesToElementAngle(value_id);
		else if (this.pie_shape_type == PIE_LINE_VAR_RADIUS)
			applyValuesToElementSize(value_id);
	}

	/*****************************************
	 ** GET METHODS 
	 ** @return
	 **/
	
}
