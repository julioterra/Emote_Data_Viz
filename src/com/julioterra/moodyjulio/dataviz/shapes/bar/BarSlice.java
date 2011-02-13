package com.julioterra.moodyjulio.dataviz.shapes.bar;

import java.util.ArrayList;
import processing.core.PApplet;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;

public class BarSlice extends ShapeRect {

	public ArrayList<ShapeRect> slices;
	String activities;
	public float value;

	public BarSlice() {
		super();
		slices = new ArrayList<ShapeRect>();
	}

	public BarSlice(int x, int y, int size_x, int size_y, String activities) {
		super(x, y, size_x, size_y, Transparent_Color);
		slices = new ArrayList<ShapeRect>();
		this.activities = activities;
		this.createSliceParts();
	}

	public BarSlice(int x, int y) {
		super(x, y);
		slices = new ArrayList<ShapeRect>();
	}
	
	public void createSliceParts() {
		String[] activity_list = PApplet.split(activities, ", ");
		int slice_width = (int)(size.x / activity_list.length);
		int remaining_width = (int)this.size.x; 
		for (int i = 0; i < activity_list.length; i++) {
			activity_list[i] = PApplet.trim(activity_list[i]);
			int start_loc_y = slice_width * i;
			if ((remaining_width -= slice_width) < slice_width) slice_width += remaining_width;
			ShapeRect new_shape = new ShapeRect((int)(this.location.x + start_loc_y), (int)this.location.y, (int)slice_width, (int)this.size.y, ActivityColor(activity_list[i]));
			slices.add(new_shape);
		}
	}
	
	public void display() {
		super.display();
		for (int i = 0; i < slices.size(); i++) {
			ShapeRect current_slice = slices.get(i);
			current_slice.display();
		}
	}
	
	public void mouseOverShiftOn() {
		super.mouseOverShiftOn();
		for (int i = 0; i < slices.size(); i++) {
			ShapeRect current_slice = slices.get(i);
			current_slice.mouseOverShiftOn();
		}
	}

	public void mouseOverShiftOff() {
		super.mouseOverShiftOff();
		for (int i = 0; i < slices.size(); i++) {
			ShapeRect current_slice = slices.get(i);
			current_slice.mouseOverShiftOff();
		}
	}
	
	public void shiftColorReset() {
		for (int i = 0; i < slices.size(); i++) {
			ShapeRect current_slice = slices.get(i);
			current_slice.shiftColorReset();
		}		
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getValue() {
		return this.value;
	}
	
	public void shiftScale(float shift_scale) {	
//		  this.shiftScale(shift_scale);
//		  size = new PVector ((float)(this.size.x * (this.scale + shift_scale)), (float)(this.size.y * (this.scale + shift_scale)));
//		  PApplet.println("SHIFT SCALE METHOD - SHAPE RECT - scale " + this.scale + " base size " + this.size_base.x + " size " + this.size.x + " base color " + this.color_base);
	}

	
	
	// SET_SHIFT_MOUSE_OVER_ALL - call this method to set the mouse over shift settings on all pie slices
	public void setShiftMouseOverAll(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
		super.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
		setShiftMouseOverAll();
	}

	public void setShiftMouseOverSlices(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
		for (int i = 0; i < slices.size(); i++) {
			ShapeRect slice = slices.get(i);
			slice.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
		}
	}
	
	public void setShiftMouseOverBar(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
		super.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
	}

	public void setShiftMouseOverAll() {
		for (int i = 0; i < slices.size(); i++) {
			ShapeRect slice = slices.get(i);
			slice.setShiftMouseOverAll(this.hue_shift_mouse_over, this.sat_shift_mouse_over, this.bright_shift_mouse_over, this.scale_shift_mouse_over, this.text_shift_mouse_over);
		}
	}	
	
	public void scaleShiftAll() {
		super.shiftScaleMouseOver();
		  for (int i = 0; i < slices.size(); i++) {
			  ShapeRect slice = slices.get(i);
			  slice.shiftScaleMouseOver();
		  }
	  }

	public void scaleShiftResetAllBase() {
		super.shiftScaleResetToBase();
		for (int i = 0; i < slices.size(); i++) {
			ShapeRect slice = slices.get(i);
			slice.shiftScaleResetToBase();
		}
	}

	public void scaleShiftResetAll() {
		super.shiftScaleReset();
		for (int i = 0; i < slices.size(); i++) {
			ShapeRect slice = slices.get(i);
			slice.shiftScaleReset();
		}
	}
	
	
	
	public String getString() {
		return "Bar Slice - location x " + location.x + " location y " + location.y + " size x " + size.x + " size y " + size.y;
	}

	
}
