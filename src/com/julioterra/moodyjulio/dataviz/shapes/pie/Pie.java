package com.julioterra.moodyjulio.dataviz.shapes.pie;

import java.util.ArrayList;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;

import processing.core.PApplet;

public class Pie extends ShapeCircle {
	
	public final int DYNAMIC = 0;
	public final int PRESET = 1;
	public int pie_type;

	public final int RADIUS = 0;
	public final int ANGLE = 1;
	public int pie_value_assign;
	
	public ArrayList<PieSlice> slices;
	public boolean pie_is_complete;
	protected float angle_total_degrees;
	protected float angle_total_percent;
	protected int number_of_slices;
	
	public Pie(int x, int y, float diameter, int pie_shape_type) {
		super(x, y, (diameter/2), 0);
		this.pie_shape_type = pie_shape_type;
		if (this.pie_shape_type == PIE_ARC_SET_RADIUS) this.pie_value_assign = ANGLE;
		else if (this.pie_shape_type == PIE_LINE_VAR_RADIUS) this.pie_value_assign = RADIUS;

		this.slices = new ArrayList<PieSlice>();
		this.diameter = diameter;
		this.color = colorARGB(255, 255, 255, 255);
	}

	public Pie(int x, int y, float diameter, int number_of_slices, int radius_type) {
		super(x, y, (diameter/2), 0);
		PApplet.println("pie - constructor " + number_of_slices + " - " + radius_type);
		this.pie_shape_type = radius_type;
		if (this.pie_shape_type == PIE_ARC_SET_RADIUS) this.pie_value_assign = ANGLE;
		else if (this.pie_shape_type == PIE_LINE_VAR_RADIUS) this.pie_value_assign = RADIUS;
		this.setNumberOfSlices(number_of_slices);
	}
	
	/*****************************************
	 ** SET MODE METHODS 
	 **/

	public void setNumberOfSlices(int number_of_slices) {
		this.number_of_slices = number_of_slices;
		this.slices = new ArrayList<PieSlice>();
		this.value_one = 0;

		if (this.number_of_slices > PApplet.degrees(this.angle_start)) {
			double temp_angle_start = 0;
			double degrees_per_slice = (float)(360.0 / (float)this.number_of_slices);
			double size_in_percent = (float)((float)degrees_per_slice / 360.0);
			for (int i = 0; i < this.number_of_slices; i++) {

				if (this.pie_shape_type == PIE_ARC_SET_RADIUS) {
					this.slices.add(new PieSliceArc((int)this.location.x, (int)this.location.y, this.diameter/2, (float) size_in_percent, (float) temp_angle_start, this.color));
					this.value_one += degrees_per_slice;
				}
				
				else if (this.pie_shape_type == PIE_LINE_VAR_RADIUS) {
					this.slices.add(new PieSliceLine((int)this.location.x, (int)this.location.y, 0, (float)size_in_percent, (float) temp_angle_start, this.color));
					this.value_one = degrees_per_slice; 
				}

				temp_angle_start = i * degrees_per_slice;
				this.angle_total_degrees = (float) temp_angle_start;
				this.angle_total_percent = (float)(this.angle_total_degrees / 360.0);
//				PApplet.println("Preset Mode: number of slices: " + i + " this one " + this.number_of_slices + " degrees per slice " + degrees_per_slice + " size in percent " + size_in_percent);
			}

		}

	}
	
	  /*********************************************************
	   ** DISPLAY METHODS 
	   **/

	  public void display() {
		  super.display();
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
			  slice.display();
		  }
	  }

	  public void mouseOver() {
		  super.mouseOver();
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
		      slice.mouseOver();
		  }	  
	  }
	  
	  public void turn(float turn_start_angle) {
		  super.turn(turn_start_angle);
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
			  slice.turn(turn_start_angle);
		  }
	  }

	  public void move(float x, float y) {
		  super.move(x, y);
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
			  slice.move(x, y);
		  }
	  }
	  
	  public void scale(float percent_scale) {
		  super.scale(percent_scale);
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
			  slice.scale(percent_scale);
		  }
		  this.shiftScaleReset();
	  }

	  public void shiftScaleReset() {
		  super.shiftScaleReset();
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
			  slice.shiftScaleReset();
		  }
	  }

	  public void shiftScaleResetToBase() {
		  super.shiftScaleResetToBase();
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
			  slice.shiftScaleResetToBase();
		  }
	  }

	/*****************************************
	 ** ADD and DELETE METHODS 
	 **
	 **/

	  public void addSlice(String name, String description, double value, int color){
		  this.addSlice(value, color);
		  if (this.pie_shape_type == PIE_ARC_SET_RADIUS) {
			  PieSlice slice = slices.get(slices.size()-1);
			  slice.setName(name);
			  slice.setDescription(description);		  
//			  if (debug_code) PApplet.println(slice.name + " " + slice.description);
		  } else if (this.pie_shape_type == PIE_LINE_VAR_RADIUS) {
			  PieSlice slice = slices.get(slices.size()-1);
			  slice.setName(name);
			  slice.setDescription(description);		  
//			  if (debug_code) PApplet.println(slice.name + " " + slice.description);
		  }
	  }
	  
	  public void addSlice(double value, int color){
		if (this.pie_shape_type == PIE_ARC_SET_RADIUS) {
			this.slices.add(new PieSliceArc((int)this.location.x, (int)this.location.y, this.radius_base, 0, 0, color));
			this.value_one += value;
			PieSlice slice = slices.get(slices.size()-1);
			slice.setValue(value);
			if (font_loaded_title) slice.loadFontDescription(this.font_number_title, this.font_size_title);
		}
		else if (this.pie_shape_type == PIE_LINE_VAR_RADIUS) {
			this.slices.add(new PieSliceLine((int)this.location.x, (int)this.location.y, 0, 0, 0, this.color));
			if (value > this.value_one) this.value_one = value; 
			PieSlice slice = slices.get(slices.size()-1);
			slice.setValue(value);
			if (font_loaded_title) slice.loadFontDescription(this.font_number_title, this.font_size_title);
		}
		this.applyValuesToSliceDisplay();
		this.setShiftMouseOverAll();
	  }

	  public void removeSlice(int index) {
		  slices.remove(index);
		  this.applyValuesToSliceDisplay();
	  }
	  
	/*****************************************
	 ** SET ALL METHODS 
	 **
	 **/

	public void setColorAll(int argb) {
		this.setColorBaseARGB(argb);
		for (int i = 0; i < slices.size(); i++) {
			this.setColorSlice(i, argb);
		}
	}

	// SET_SHIFT_MOUSE_OVER_ALL - call this method to set the mouse over shift settings on all pie slices
	public void setShiftMouseOverAll(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
		super.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
		setShiftMouseOverAll();
	}

	public void setShiftMouseOverSlices(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
		}
	}
	
	public void setShiftMouseOverAll() {
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.setShiftMouseOverAll(this.hue_shift_mouse_over, this.sat_shift_mouse_over, this.bright_shift_mouse_over, this.scale_shift_mouse_over, this.text_shift_mouse_over);
		}
	}	

	public void setShiftMouseOverPie(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
		super.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
	}
	
	public void scaleShiftAll() {
		super.shiftScaleMouseOver();
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
			  slice.shiftScaleMouseOver();
		  }
	  }

	public void scaleShiftResetAllBase() {
		super.shiftScaleResetToBase();
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.shiftScaleResetToBase();
		}
	}

	public void scaleShiftResetAll() {
		super.shiftScaleReset();
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.shiftScaleReset();
		}
	}

	public void setWidthAll(float width) {
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.setWidth(width);
		}
	}



	/*****************************************
	 ** SET SLICE METHODS 
	 **
	 **/

	public void setColorSlice(int index, int argb) {
//		this.pie_value_assign = ANGLE;
		if (index < slices.size()) {
			PieSlice slice = slices.get(index);
			slice.setColorBaseARGB(argb);
		}
	}

	public void setColorAllSlices(int argb) {
//		this.pie_value_assign = ANGLE;
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.setColorBaseARGB(argb);
		}
	}

	public void setMouseOverShiftSlice(int index, float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
		if (index < slices.size()) {
			PieSlice slice = slices.get(index);
			slice.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
		}
	}
	
	public void setAngleStartSlice(int index, float start_angle) {
		if (index < slices.size()) {
			PieSlice slice = slices.get(index);
			slice.setPieElementAngleStart(start_angle);
		}	
	}

	public void setAngleExtentSliceDegrees(int index, float slice_angle) {
		if (index < slices.size()) {
			PieSlice slice = slices.get(index);
			slice.setAngleExtentDegrees(slice_angle);
		}			
	}

	public void setRadiusSlice(int index, float radius) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setRadius(radius);
		}

		if (radius > this.radius) this.radius = radius;
	}


	/*****************************************
	 ** SET TEXT SLICE METHODS 
	 **
	 **/
	
	public void loadFontAll(int new_font_number, int size, float font_scale) {
		super.loadFontAll(new_font_number, size, font_scale);
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.loadFontAll(new_font_number, size, font_scale);
		}
	}

	public void loadFontPie(int new_font_number, int size, float font_scale) {
		super.loadFontAll(new_font_number, size, font_scale);
	}

	public void loadFontSlices(int new_font_number, int size, float font_scale) {
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.loadFontAll(new_font_number, size, font_scale);
		}
	}
	public void loadFontTitleSlices(int new_font_number, int size) {
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.loadFontTitle(new_font_number, size);
		}
	}

	public void loadFontDescriptionSlices(int new_font_number, int size) {
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.loadFontDescription(new_font_number, size);
		}
	}
	
	public void setTextLocationNameDescription(float x, float y, float offset_x, float offset_y) {
		super.setTextLocationNameDescription(x, y, offset_x, offset_y);
	}

	public void textLocationNameDescriptionSlices(float x, float y, float offset_x, float offset_y) {
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.setTextLocationNameDescription(x, y, offset_x, offset_y);
		}
	}

	public void textSetNameSlice(int index, String name) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setName(name);
		}		
	}

	public void textSetDescriptionSlice(int index, String description) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setDescription(description);
		}		
	}

	public void setFontColorSlice(int index, int font_color) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setDescription(description);
			this.setFontColorAll(font_color);
		}
	}

	public void textVisibleSlice(int index) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setTextVisibleNameDescription();
		}		
	}

	public void textInvisibleSlice(int index) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setTextInvisibleNameDescription();
		}		
	}

	public void textNameVisibleSlice(int index) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setTextVisibleName();
		}		
	}

	public void textNameInvisibleSlice(int index) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setTextInvisibleName();
		}		
	}
	
	public void textDescriptionVisibleSlice(int index) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setTextVisibleDescription();
		}		
	}

	public void textDescriptionInvisibleSlice(int index) {
		if (slices.size() > index) {
			PieSlice slice = slices.get(index);
			slice.setTextInvisibleDescription();
		}		
	}
	
	// ******************************************************
	// SET VALUE and CALCULATE EXTEND ANGLES
	// functionality to add here
	//		calculate the change in overall pie size with all angles added up
	//		redefine the size of each slice based on new pie size calculation
	
	public void setSliceValue(int index, float new_value) {
		PieSlice set_slice = slices.get(index);
		set_slice.setValue(new_value);
	}
	
	  public void applyValuesToSliceDisplay() {
		  if (this.pie_shape_type == PIE_ARC_SET_RADIUS) {
			  this.value_one = 0;
			  for (int i = 0; i < slices.size(); i++) {
					PieSlice slice = slices.get(i);
					this.value_one += slice.getValue();
			  }
	
			  double slice_start_degrees = 0; 
			  for (int j = 0; j < slices.size(); j++) {
					PieSlice slice = this.slices.get(j);
					double slice_extent_percent = slice.getValue() / this.value_one;
					double slice_extent_degrees = slice_extent_percent * 360;
					this.setAngleStartSlice(j, (float) slice_start_degrees);
					this.setAngleExtentSliceDegrees(j, (float) (slice_extent_degrees));
					slice_start_degrees += slice_extent_degrees;
			  }
		  }
		  else if (this.pie_shape_type == PIE_LINE_VAR_RADIUS) {
			  this.value_one = 0;
			  for (int i = 0; i < slices.size(); i++) {
					PieSlice slice = slices.get(i);
					slice.getValue();
					if (this.value_one < slice.getValue()) this.value_one = slice.getValue();
//					PApplet.println(" current value " + slice.getValue() + " radius " + slice.getRadius());
			  }

			  for (int i = 0; i < slices.size(); i++) {
					PieSlice slice = slices.get(i);
					slice.setBaseRadius((float) (slice.getValue()/this.value_one*this.radius) );
					if (this.value_one < slice.getValue()) this.value_one = slice.getValue();
//					PApplet.println(" current value " + slice.getValue() + " radius " + slice.getRadius());
			  }

		  	  double extend_angle = (float) (360.0 / (float)slices.size());
			  double start_angle_temp = 0.0; 
			  for (int j = 0; j < slices.size(); j++) {
					this.setAngleStartSlice(j, (float) start_angle_temp);
					this.setAngleExtentSliceDegrees(j, (float) extend_angle);
					start_angle_temp += extend_angle;
			  }
			  this.setBaseRadius((float) (this.value_one));
			  
		  }
		  // makes sure the proper scalling is applied to radius of pies 
		  this.shiftScaleReset();
	  }

	public void setShiftScaleSlice(int index) {
		  PieSlice slice = slices.get(index);
		  slice.shiftScaleMouseOver();
	}

	public void shiftScaleResetSlice(int index) {
		PieSlice slice = slices.get(index);
		slice.shiftScaleResetToBase();
	}
			
	public void scaleSlice(int index, int percent_larger) {
		if (index < slices.size()) {
			PieSlice slice = slices.get(index);
			slice.scale(percent_larger);
		}
	}

	  public void turnSlice(int index, float turn_start_angle) {
		  if (index < slices.size()) {
			  PieSlice slice = slices.get(index);
			  slice.turn(turn_start_angle);
		  }
	  }



	/*****************************************
	 ** GET METHODS 
	 ** @return
	 **/
	
	public int getNumberOfSlices() {
		return this.number_of_slices;
	}

	public int getBaseColor() {
		return this.color_base;
	}

	public int getColor() {
		return this.color;
	}

	public int getBaseColorSlice(int index) {
		PieSlice slice = slices.get(index);
		return slice.getBaseColor();
	}

	public int getColorSlice(int index) {
		PieSlice slice = slices.get(index);
		return slice.getColor();
	}

	public float getPieRemainingInPercent() {
		if (this.angle_total_percent >= 1) {
			this.pie_is_complete = true;
			return 1;
		} else {
			this.pie_is_complete = false;
			return 1 - this.angle_total_percent;
		}
	}
	
	public float getPieRemainingInDegrees() {
		if (this.angle_total_degrees >= 360) {
			this.pie_is_complete = true;
			return 360;
		} else {
			return 360 - this.angle_total_degrees;
		}
	}

}
