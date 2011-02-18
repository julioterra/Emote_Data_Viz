package com.julioterra.moodyjulio.dataviz.shapes.bar;

import java.util.ArrayList;
//import processing.core.PApplet;
import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;

public class Bar extends ShapeRect {

	public static int PRESET = 0;
	public static int VARIABLE = 1;	
	public static int HORIZONTAL = 3;
	public static int VERTICAL = 4;	
	
	public int bar_type;
	public ArrayList<BarSlice> slices;
	public boolean bar_is_complete;	
	protected float max_value;
	protected float min_value;
	protected float value_range_size;
	protected float completed_total_value;
	protected float completed_total_percent;
	protected int number_of_slices;

	public Bar(int x, int y, int size_x, int size_y, int color, float min_value, float max_value) {
		super(x, y, size_x, size_y, color);
		slices = new ArrayList<BarSlice>();
		this.number_of_slices = 0;
		this.max_value = max_value;
		this.min_value = min_value;
		this.value_range_size = this.max_value - this.min_value;
		this.bar_type = PRESET;
	}

	/*********************************************************
	 ** DISPLAY & MOUSEOVER METHODS 
	 **/

	public void display() {
		  super.display();
		  for (int i = 0; i < slices.size(); i++) {
			  BarSlice slice = slices.get(i);
			  slice.display();
		  }
	  }

	  /*********************************************************
	   ** MOVE, SIZE & SCALE METHODS 
	   **/

	  public void move(float x, float y) {
		  super.move(x, y);
		  for (int i = 0; i < slices.size(); i++) {
			  BarSlice slice = slices.get(i);
			  slice.move(x, y);
		  }
	  }
	  
	  public void setScale(float percent_scale) {
		  super.setScale(percent_scale);
		  for (int i = 0; i < slices.size(); i++) {
			  BarSlice slice = slices.get(i);
			  slice.setScale(percent_scale);
		  }
		  this.shiftScaleReset();
	  }

	  public void size(float x, float y) {
		  this.size_active = new PVector(x, y);
		  this.size_base = new PVector(x, y);

// 		add code here that resizes and re-locates all of the bar slices
		  
//		  for (int i = 0; i < slices.size(); i++) {
//			  BarSlice slice = slices.get(i);
//			  slice.size(percent_scale);
//		  }
	  }

	  public void shiftScaleReset() {
		  super.shiftScaleReset();
		  for (int i = 0; i < slices.size(); i++) {
			  BarSlice slice = slices.get(i);
			  slice.shiftScaleReset();
		  }
	  }

	  public void shiftScaleResetToBase() {
		  super.shiftScaleResetToBase();
		  for (int i = 0; i < slices.size(); i++) {
			  BarSlice slice = slices.get(i);
			  slice.shiftScaleResetToBase();
		  }
	  }

	  public void shiftSize(float shift_scale) {	
//		  this.shiftScale(shift_scale);
//		  size = new PVector ((float)(this.size.x * (this.scale + shift_scale)), (float)(this.size.y * (this.scale + shift_scale)));
//		  PApplet.println("SHIFT SCALE METHOD - SHAPE RECT - scale " + this.scale + " base size " + this.size_base.x + " size " + this.size.x + " base color " + this.color_base);
	  }



	
		/*****************************************
		 ** ADD and DELETE METHODS 
		 **
		 **/

		  public void addSlice(String name, String description, String text, double start_value, double end_value){
//			  PApplet.println("ADD SLICE - INPUT - start value " + start_value + " end value " + end_value + " max " + this.max_value + " min " + this.min_value + " range " + value_range_size);
			  this.addSliceInLocation(text, start_value, end_value);
			  BarSlice slice = slices.get(slices.size()-1);
			  slice.setName(name);
			  slice.setDescription(description);		  
			  slice.setMouseOverActive(true);
			  if (font_loaded_label) slice.loadFontDescription(this.font_number_label, this.font_size_label_active);
//			  PApplet.println("ADD SLICE " + slice.getString());
		  }
		  
		  protected void addSliceInLocation(String text, double start_value, double end_value){
			float start_loc_y = (float)(start_value - this.min_value);
			start_loc_y = (float)((start_loc_y / this.value_range_size) * this.size_active.y);
			float length_y = (float)(end_value - start_value);
			length_y = (float)((length_y / this.value_range_size) * this.size_active.y);
//			PApplet.println(slice.getString());
			this.slices.add(new BarSlice((int)this.location.x, (int)(this.location.y+start_loc_y), (int)this.size_active.x, (int)length_y, text));
		  }

		  public void removeSlice(int index) {
			  slices.remove(index);
		  }
		  
		  public int getSizeSlices() {
			  return slices.size();
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
		public void setShiftAllMouseOver(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
			super.setShiftAllMouseOver(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
			setShiftMouseOverAll();
		}

		public void setShiftMouseOverSlices(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
			for (int i = 0; i < slices.size(); i++) {
				BarSlice slice = slices.get(i);
				slice.setShiftAllMouseOver(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
			}
		}
		
		public void setShiftMouseOverBar(float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
			super.setShiftAllMouseOver(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
		}

		public void setShiftMouseOverAll() {
			for (int i = 0; i < slices.size(); i++) {
				BarSlice slice = slices.get(i);
				slice.setShiftAllMouseOver(this.hue_shift_mouse_over, this.sat_shift_mouse_over, this.bright_shift_mouse_over, this.size_shift_mouse_over, this.text_toggle_mouse_over);
			}
		}	
		
		public void scaleShiftAll() {
			super.shiftSize(this.size_shift_mouse_over);
			for (int i = 0; i < slices.size(); i++) {
				  BarSlice slice = slices.get(i);
				  slice.shiftSize(slice.size_shift_mouse_over);
			}
		}

		public void scaleShiftResetAllBase() {
			super.shiftScaleResetToBase();
			for (int i = 0; i < slices.size(); i++) {
				BarSlice slice = slices.get(i);
				slice.shiftScaleResetToBase();
			}
		}

		public void scaleShiftResetAll() {
			super.shiftScaleReset();
			for (int i = 0; i < slices.size(); i++) {
				BarSlice slice = slices.get(i);
				slice.shiftScaleReset();
			}
		}
		
		/*****************************************
		 ** SET SLICE METHODS 
		 **
		 **/

		public void setColorSlice(int index, int argb) {
			if (index < slices.size()) {
				BarSlice slice = slices.get(index);
				slice.setColorBaseARGB(argb);
			}
		}

		public void setColorAllSlices(int argb) {
			for (int i = 0; i < slices.size(); i++) {
				BarSlice slice = slices.get(i);
				slice.setColorBaseARGB(argb);
			}
		}

		public void setMouseOverShiftSlice(int index, float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean text_shift_mouse_over) {
			if (index < slices.size()) {
				BarSlice slice = slices.get(index);
				slice.setShiftAllMouseOver(hue_shift, saturation_shift, brightness_shift, radius_shift, text_shift_mouse_over);
			}
		}
		

		/*****************************************
		 ** SET TEXT SLICE METHODS 
		 **
		 **/
		
		public void loadFontAll(int new_font_number, int size, float font_scale) {
			super.loadFontAll(new_font_number, size, font_scale);
			for (int i = 0; i < slices.size(); i++) {
				BarSlice slice = slices.get(i);
				slice.loadFontAll(new_font_number, size, font_scale);
			}
		}

		public void loadFontPie(int new_font_number, int size, float font_scale) {
			super.loadFontAll(new_font_number, size, font_scale);
		}

		public void loadFontSlices(int new_font_number, int size,  float font_scale) {
			for (int i = 0; i < slices.size(); i++) {
				BarSlice slice = slices.get(i);
				slice.loadFontAll(new_font_number, size, font_scale);
			}
		}

		public void setTextLocationNameDescription(float x, float y, float offset_x, float offset_y) {
			super.setTextLocationNameDescription(x, y, offset_x, offset_y);
		}

		public void textLocationNameDescriptionSlices(float x, float y, float offset_x, float offset_y) {
			for (int i = 0; i < slices.size(); i++) {
				BarSlice slice = slices.get(i);
				slice.setTextLocationNameDescription(x, y, offset_x, offset_y);
			}
		}

		public void textSetNameSlice(int index, String name) {
			if (slices.size() > index) {
				BarSlice slice = slices.get(index);
				slice.setName(name);
			}		
		}

		public void textSetDescriptionSlice(int index, String description) {
			if (slices.size() > index) {
				BarSlice slice = slices.get(index);
				slice.setDescription(description);
			}		
		}

		public void setFontColorSlice(int index, int font_color) {
			if (slices.size() > index) {
				BarSlice slice = slices.get(index);
				slice.setDescription(description);
				this.setFontColorAll(font_color);
			}
		}

		public void textVisibleSlice(int index) {
			if (slices.size() > index) {
				BarSlice slice = slices.get(index);
				slice.setTextVisibleNameDescription();
			}		
		}

		public void textInvisibleSlice(int index) {
			if (slices.size() > index) {
				BarSlice slice = slices.get(index);
				slice.setTextInvisibleNameDescription();
			}		
		}

		public void textNameVisibleSlice(int index) {
			if (slices.size() > index) {
				BarSlice slice = slices.get(index);
				slice.setTextVisibleName();
			}		
		}

		public void textNameInvisibleSlice(int index) {
			if (slices.size() > index) {
				BarSlice slice = slices.get(index);
				slice.setTextInvisibleName();
			}		
		}
		
		public void textDescriptionVisibleSlice(int index) {
			if (slices.size() > index) {
				BarSlice slice = slices.get(index);
				slice.setTextVisibleDescription();
			}		
		}

		public void textDescriptionInvisibleSlice(int index) {
			if (slices.size() > index) {
				BarSlice slice = slices.get(index);
				slice.setTextInvisibleDescription();
			}		
		}
		
		public String getString() {
			return "Bar - location x " + location.x + " location y " + location.y + " size x " + size_active.x + " size y " + size_active.y;
		}

}
