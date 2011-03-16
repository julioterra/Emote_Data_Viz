package com.julioterra.moodyjulio.dataviz.shapes.bar;

import java.util.ArrayList;
//import processing.core.PApplet;
import processing.core.*;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeViz;
import com.julioterra.moodyjulio.dataviz.shapes.complex.CSElementStyleSettings;
import com.julioterra.moodyjulio.dataviz.shapes.complex.CSMouseEventSettings;
import com.julioterra.moodyjulio.dataviz.shapes.pie.PieSlice;

public class Bar extends ShapeRect implements CSMouseEventSettings, CSElementStyleSettings {

	public static int PRESET = 0;
	public static int VARIABLE = 1;	
	public static int HORIZONTAL = 3;
	public static int VERTICAL = 4;	
	public int bar_type;

	public ArrayList<BarSlice> elements;
	ShapeViz elements_style;
	public boolean bar_is_complete;	
	protected float value_max;
	protected float value_min;
	protected float value_range;

	public Bar(int x, int y, int size_x, int size_y, int color, float min_value, float max_value) {
		super(x, y, size_x, size_y, color);
		processing_app.registerMouseEvent(this);
		
		this.elements = new ArrayList<BarSlice>();
		this.elements_style = new ShapeViz();
		this.elements_style.setShiftMouseOver(0, 0, 0, 0, false, false);		
		this.elements_style.setShiftMouseClicked(0, 0, 0, 0, false, false);		

		this.value_max = max_value;
		this.value_min = min_value;
		this.value_range = this.value_max - this.value_min;
		this.bar_type = PRESET;
		
		this.setMouseOverActive(false);
		this.setMouseClickedActive(false);
	}

	/*********************************************************
	 ** DISPLAY & MOUSEOVER METHODS 
	 **/

	public void display() {
		super.display();
		for (int i = 0; i < elements.size(); i++) {
			BarSlice slice = elements.get(i);
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
			  BarSlice slice = elements.get(i);
			  slice.move(x, y);
		  }
	  }
	  
	  public void setScale(float percent_scale) {
		  super.setScale(percent_scale);
		  for (int i = 0; i < elements.size(); i++) {
			  elements.get(i).setScale(percent_scale);
		  }
		  this.shiftScaleReset();
	  }

	  public void setSize(float x, float y) {
		  super.setSize(x, y);
		  if (elements.size() > 0) {
			  for (int i = 0; i < elements.size(); i++)
				  elements.get(i).setSize(0, 0);
		  }
	  }

	  public void setSizeActive(float x, float y) {
		  super.setSizeActive(x, y);
		  if (elements.size() > 0) {
			  for (int i = 0; i < elements.size(); i++)
				  elements.get(i).setSizeActive(0, 0);
		  }
	  }

//	  public void shiftScaleReset() {
//		  super.shiftScaleReset();
//		  for (int i = 0; i < elements.size(); i++) 
//			  elements.get(i).shiftScaleReset();
//	  }
//
//	  public void shiftScaleResetToBase() {
//		  super.shiftScaleResetToBase();
//		  for (int i = 0; i < elements.size(); i++) 
//			  elements.get(i).shiftScaleResetToBase();
//	  }
//
//	  public void shiftScale(float shift_scale) {	
//		  super.shiftScale(shift_scale);
//		  for (int i = 0; i < elements.size(); i++) 
//			  elements.get(i).shiftScale(shift_scale);
//	  }

	
		/*****************************************
		 ** ADD and DELETE METHODS 
		 **
		 **/

		  public void addElement(String name, String description, String csv_list, double start_value, double end_value){
			  this.addElement(csv_list, start_value, end_value);
			  BarSlice element = elements.get(elements.size()-1);
			  element.label.setText(name);
			  element.description.setText(description);		  
			  element.setMouseOverActive(true);
			  if (elements_style.label.font_loaded) element.label.loadFont(elements_style.label.font_number, (int)(elements_style.label.getFontSize()));
			  if (elements_style.description.font_loaded) element.description.loadFont(elements_style.description.font_number, (int)(elements_style.description.getFontSize()));
		  }
		  
		  private void addElement(String text, double start_value, double end_value){
			float start_loc_y = (float)(start_value - this.value_min);
			start_loc_y = (float)((start_loc_y / this.value_range) * this.size_active.y);
			float length_y = (float)(end_value - start_value);
			length_y = (float)((length_y / this.value_range) * this.size_active.y);
			this.elements.add(new BarSlice((int)this.location.x, (int)(this.location.y+start_loc_y), (int)this.size_active.x, (int)length_y, text));
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
			for (int i = 0; i < elements.size(); i++)
				elements.get(i).setShiftMouseOver(elements_style.hue_shift_mouse_over, elements_style.sat_shift_mouse_over, elements_style.bright_shift_mouse_over, elements_style.size_shift_mouse_over, elements_style.label_toggle_mouse_over, elements_style.label_toggle_mouse_over);
		}

		// SET MOUSE CLICK STYLING
		public void setElementAllShiftMouseClicked(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked){
			elements_style.setShiftMouseClicked(hue_shift, saturation_shift, brightness_shift, scale_shift, label_shift_mouse_clicked, description_shift_mouse_clicked);
			for (int i = 0; i < elements.size(); i++)
				elements.get(i).setShiftMouseClicked(elements_style.hue_shift_mouse_clicked, elements_style.sat_shift_mouse_clicked, elements_style.bright_shift_mouse_clicked, elements_style.size_shift_mouse_clicked, elements_style.label_toggle_mouse_clicked, elements_style.label_toggle_mouse_clicked);
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
					BarSlice element = elements.get(Integer.parseInt(button_id));
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


		/***************************
		 ** GET STRING METHOD
		 ***************************/
		public String getString() {
			return "Bar - location x " + location.x + " location y " + location.y + " size x " + size_active.x + " size y " + size_active.y;
		}

}
