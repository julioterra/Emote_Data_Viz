package com.julioterra.moodyjulio.dataviz.shapes.pie;

import java.util.ArrayList;

import processing.core.PApplet;

public class Pie extends PieElement {
	
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
		if (this.pie_shape_type == PIE_ARC) this.pie_value_assign = ANGLE;
		else if (this.pie_shape_type == PIE_LINE) this.pie_value_assign = RADIUS;

		this.slices = new ArrayList<PieSlice>();
		this.diameter = diameter;
		this.color = colorARGB(255, 255, 255, 255);
	}

	public Pie(int x, int y, float diameter,  int pie_shape_type, int number_of_slices) {
		super(x, y, (diameter/2), 0);
		this.pie_shape_type = pie_shape_type;
		if (this.pie_shape_type == PIE_ARC) this.pie_value_assign = ANGLE;
		else if (this.pie_shape_type == PIE_LINE) this.pie_value_assign = RADIUS;
		this.setNumberOfSlices(number_of_slices);
	}
	
	/*****************************************
	 ** SET MODE METHODS 
	 **/

	public void setNumberOfSlices(int number_of_slices) {
		this.number_of_slices = number_of_slices;
		this.slices = new ArrayList<PieSlice>();

		if (this.number_of_slices > PApplet.degrees(this.angle_start)) {
			double temp_angle_start = PApplet.degrees(0);
			double degrees_per_slice = 360 / this.number_of_slices;
			double size_in_percent = degrees_per_slice / 360;
			for (int i = 0; i < this.number_of_slices; i++) {
				if (this.pie_shape_type == PIE_ARC)
					this.slices.add(new PieSliceArc((int)this.location.x, (int)this.location.y, this.diameter/2, (float) size_in_percent, (float) temp_angle_start, this.color));
				else if (this.pie_shape_type == PIE_LINE)
					this.slices.add(new PieSliceLine((int)this.location.x, (int)this.location.y, 0, (float)size_in_percent, (float) temp_angle_start, this.color));
				temp_angle_start = i * degrees_per_slice;
				this.angle_total_degrees = (float) temp_angle_start;
				this.value = 0;
				this.angle_total_percent = this.angle_total_degrees / 360;

				PApplet.println("Preset Mode: number of slices: " + i + " this one " + this.number_of_slices + " degrees per slice " + degrees_per_slice + " size in percent " + size_in_percent);
			}

		}

	}
	
	  /*********************************************************
	   ** DISPLAY METHODS 
	   **/

	  public void display() {
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
		      slice.mouseOver();
			  slice.display();
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
	  }

	/*****************************************
	 ** ADD and DELETE METHODS 
	 **
	 **/

	  public void addSlice(double value, int color){
		  slices.add(new PieSlice((int)this.location.x, (int)this.location.y, 0, 0, 0, color));
		  this.calculateSliceExtentFromValues();
	  }

	  public void removeSlice(int index) {
		  slices.remove(index);
		  this.calculateSliceExtentFromValues();
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

	public void setColorSizeShiftAll(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift) {
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.setColorShift(hue_shift, saturation_shift, brightness_shift);
			slice.setScaleShift(radius_shift);
		}
	}

	public void scaleShiftAll() {
		super.scaleShift();
		  for (int i = 0; i < slices.size(); i++) {
			  PieSlice slice = slices.get(i);
			  slice.scaleShift();
		  }
	  }

	public void scaleShiftResetAll() {
		super.scaleShiftReset();
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.scaleShiftReset();
		}
	}
	

	/*****************************************
	 ** SET SLICE METHODS 
	 **
	 **/

	public void setColorSlice(int index, int argb) {
		this.pie_value_assign = ANGLE;
		if (index < slices.size()) {
			PieSlice slice = slices.get(index);
			slice.setColorBaseARGB(argb);
		}
	}

	public void setMouseOverShiftSlice(int index, float hue_shift, float saturation_shift, float brightness_shift, float radius_shift) {
		if (index < slices.size()) {
			PieSlice slice = slices.get(index);
			slice.setColorShift(hue_shift, saturation_shift, brightness_shift);
			slice.setScaleShift(radius_shift);
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
			slice.setWidth(radius);
		}

		if (radius > this.radius) this.radius = radius;
	}

	public void setWidthAll(float width) {
		for (int i = 0; i < slices.size(); i++) {
			PieSlice slice = slices.get(i);
			slice.setWidth(width);
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
	
	  public void calculateSliceExtentFromValues() {
		  if (this.pie_shape_type == PIE_ARC) {
			  this.value = 0;
			  for (int i = 0; i < slices.size(); i++) {
					PieSlice slice = slices.get(i);
					this.value += slice.getValue();
			  }
	
			  double slice_start_degrees = 0; 
			  for (int j = 0; j < slices.size(); j++) {
					PieSlice slice = this.slices.get(j);
					double slice_extent_percent = slice.getValue() / this.value;
					double slice_extent_degrees = slice_extent_percent * 360;
					this.setAngleStartSlice(j, (float) slice_start_degrees);
					this.setAngleExtentSliceDegrees(j, (float) (slice_extent_degrees));
					slice_start_degrees += slice_extent_degrees;
			  }
		  }
	  }

	  public void calculateSliceRadiusFromValues() {
			PApplet.println("recalculate radius - number method ************************* " + this.pie_shape_type);
		  if (this.pie_shape_type == PIE_LINE) {
			  this.value = 0;
			  for (int i = 0; i < slices.size(); i++) {
					PieSlice slice = slices.get(i);
					if (this.value < slice.getValue()) this.value = slice.getValue();
					slice.setRadius((float) slice.getValue());
					PApplet.println("recalculate radius - number: " + i + " radius: " + slice.radius + " value: " + slice.value);
			  }

//		  double start_angle_temp = 0;
		  	  double extend_angle = (360 / slices.size());
			  double start_angle_temp = 0; 
			  for (int j = 0; j < slices.size(); j++) {
//					PieSlice slice = this.slices.get(j);
					this.setAngleStartSlice(j, (float) start_angle_temp);
					this.setAngleExtentSliceDegrees(j, (float) (extend_angle));
					start_angle_temp += extend_angle;
					PApplet.println("recalculate size - number: " + j + " start angle: " + start_angle_temp + " extend "+ extend_angle + " " + slices.size()*extend_angle);
			  }
		  }
	  }

	public void setShiftScaleSlice(int index) {
		  PieSlice slice = slices.get(index);
		  slice.scaleShift();
	}

	public void shiftScaleResetSlice(int index) {
		PieSlice slice = slices.get(index);
		slice.scaleShiftReset();
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

	public int getTotalValue() {
		return this.number_of_slices;
	}

	public int getBaseColor(int index) {
		PieSlice slice = slices.get(index);
		return slice.getBaseColor();
	}

	public int getBaseColor() {
		return this.color_base;
	}

	public int getColor(int index) {
		PieSlice slice = slices.get(index);
		return slice.getColor();
	}

	public int getColor() {
		return this.color;
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
