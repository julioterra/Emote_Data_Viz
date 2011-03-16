package com.julioterra.moodyjulio.dataviz.shapes.bar;

import java.util.ArrayList;
import processing.core.PApplet;
import com.julioterra.moodyjulio.dataviz.shapes.*;

public class BarSlice extends ShapeRect {

	public ArrayList<ShapeRect> slices;
	String activities;

	/******************************
	 ** CONSTRUCTOR METHODS 
	 ******************************/

	private BarSlice() {
		super();
		slices = new ArrayList<ShapeRect>();
	}

	public BarSlice(int x, int y, int size_x, int size_y, String activities) {
		super(x, y, size_x, size_y, Transparent_Color);
		slices = new ArrayList<ShapeRect>();
		this.activities = activities;
		this.createSliceParts();
	}

	public BarSlice(int x, int y, int size_x, int size_y, int color) {
		super(x, y, size_x, size_y, color);
		slices = new ArrayList<ShapeRect>();
	}

	public void createSliceParts() {
		String[] activity_list = PApplet.split(activities, ", ");
		int slice_width = (int)(size_active.x / activity_list.length);
		int remaining_width = (int)this.size_active.x; 
		for (int i = 0; i < activity_list.length; i++) {
			activity_list[i] = PApplet.trim(activity_list[i]);
			int start_loc_y = slice_width * i;
			if ((remaining_width -= slice_width) < slice_width) slice_width += remaining_width;
			ShapeRect new_shape = new ShapeRect((int)(this.location.x + start_loc_y), (int)this.location.y, (int)slice_width, (int)this.size_active.y, ActivityColor(activity_list[i]));
			slices.add(new_shape);
		}
		setShiftMouseOverAll();
	}
	
	/******************************
	 **	DISPLAY METHOD 
	 ******************************/

	public void display() {
		super.display();
		for (int i = 0; i < slices.size(); i++) {
			ShapeRect current_slice = slices.get(i);
			current_slice.display();
		}
	}

	/******************************
	 **	MOUSE OVER 
	 ******************************/

	public void mouseOver() {
		super.mouseOver();
		if (this.mouse_over) 
			for (int i = 0; i < slices.size(); i++) slices.get(i).mouseOverActions();
		if (!this.mouse_over) 
			for (int i = 0; i < slices.size(); i++) slices.get(i).mouseOffActions();
	}
	
	/******************************
	 **	SHIFT STYLE METHODS 
	 ******************************/
	
	// SET_SHIFT_MOUSE_OVER_ALL - call this method to set the mouse over shift settings on all pie slices
	public void setShiftMouseOver(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean name_shift_mouse_over, boolean description_shift_mouse_over) {
		super.setShiftMouseOver(hue_shift, saturation_shift, brightness_shift, radius_shift, name_shift_mouse_over, description_shift_mouse_over);
		setShiftMouseOverAll();
	}

	public void setShiftMouseOverAll() {
		for (int i = 0; i < slices.size(); i++)
			slices.get(i).setShiftMouseOver(this.hue_shift_mouse_over, this.sat_shift_mouse_over, this.bright_shift_mouse_over, this.size_shift_mouse_over, this.label_toggle_mouse_over, this.label_toggle_mouse_over);
	}	
	
	public void shiftScale(float shift_scale) {
		super.shiftScale(shift_scale);
		for (int i = 0; i < slices.size(); i++)
			  slices.get(i).shiftScale(shift_scale);
	}

	public void scaleShiftReset() {
		super.shiftScaleReset();
		for (int i = 0; i < slices.size(); i++)
			slices.get(i).shiftScaleReset();
	}
	
	public void resetSize() {
		super.resetSize();
		for (int i = 0; i < slices.size(); i++) 
			slices.get(i).resetSize();
	}

	public String getString() {
		return "Bar Slice - location x " + location.x + " location y " + location.y + " size x " + size_active.x + " size y " + size_active.y;
	}

	
}
