package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;

public class Shape extends DataVizElement{

	public PVector location;
	public boolean visible;

	protected boolean mouse_over;
	protected float scale_shift_mouse_over;
	protected float scale_shift_mouse_press;
	protected boolean 	text_shift_mouse_over;
	protected boolean 	text_shift_mouse_press;

	public double scale;

	public String name;
	public PFont name_font;
	protected boolean name_font_loaded;
	public int name_font_size;
	public int name_font_color;
	public PVector name_location;
	public boolean name_visible;

	public String description;
	public PFont description_font;
	protected boolean description_font_loaded;
	public int description_font_size;
	public int description_font_color;
	public PVector description_location;
	public boolean description_visible;

	
	public Shape () {
		this(0,0);
	}
	
	public Shape (int x, int y) {
		this.location = new PVector(x, y);
		this.visible = true;

		this.name_location = new PVector(0,0);
		this.name_font_loaded = false;
		this.name = "";
		this.name_font_color = 0;

		this.description_location = new PVector(0,0);
		this.description_font_loaded = false;
		this.description = "";
		this.description_font_color = 0;

		this.scale = 1;
		this.scale_shift_mouse_over = 0;
		this.scale_shift_mouse_press = 0;
		
		this.description_visible = false;
		this.name_visible = false;
	}
	
	/*********************************************************
	 ** TEXT & FONT METHODS 
	 **/

	public void setName(String new_name) {
		this.name = new_name;
	}

	public void setDescription(String new_name) {
		this.description = new_name;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public void loadFontAll(int new_font_number, int size) {
		this.loadFontName(DataVizElement.fonts[new_font_number], size);
		this.loadFontDescription(DataVizElement.fonts[new_font_number], (int) (size*0.7));
	}

	public void loadFontName(PFont new_font, int size) {
		this.name_font = new_font;
		this.name_font_size = size;
		this.name_font_loaded = true;
	}

	public void loadFontDescription(PFont new_font, int size) {
		this.description_font = new_font;
		this.description_font_size = size;
		this.description_font_loaded = true;
	}

	public void setFontColorAll(int font_color) {
		this.name_font_color = font_color;
		this.description_font_color = font_color;
	}

	public void setFontColorName(int font_color) {
		this.name_font_color = font_color;
	}

	public void setFontColorDescription(int font_color) {
		this.description_font_color = font_color;
	}

	public void textLocationNameDescription(float x, float y, float offset_x, float offset_y) {
		this.textLocationName(x, y);
		this.textLocationDescriptionRel(offset_x, (float)(offset_y) );
	}

	public void textLocationName(float x, float y) {
		this.name_location = new PVector(x, y);
	}

	public void textLocationDescription(float x, float y) {
		this.description_location = new PVector(x, y);
	}

	public void textLocationDescriptionRel(float x, float y) {
		this.description_location = new PVector(this.name_location.x+x, this.name_location.y+y);
	}
	
	public void textSetVisibleNameLocation() {
		this.textNameSetVisible();
		this.textDescriptionSetVisible();
	}

	public void shiftTextNameLocation() {
		if(this.text_shift_mouse_over) {
			PApplet.println(this.name + " got to shift text visible ");
			this.textSetVisibleNameLocation();
		}
	}

	public void shiftResetTextNameLocation() {
		if(this.text_shift_mouse_over) {
			PApplet.println(this.name + " got to shift text INvisible ");
			this.textSetInvisibleNameLocation();
		}
	}

	public void textSetInvisibleNameLocation() {
		this.textNameSetInvisible();
		this.textDescriptionSetInvisible();
	}

	public void textNameSetVisible() {
		this.name_visible = true;
	}

	public void textDescriptionSetVisible() {
		this.description_visible = true;
	}
	
	public void textNameSetInvisible() {
		this.name_visible = false;
	}

	public void textDescriptionSetInvisible() {
		this.description_visible = false;
	}
	

	/*********************************************************
	 ** DISPLAY METHODS 
	 **/
	
	public void visible() {
		this.visible = true;
	}

	public void invisible() {
		this.visible = false;
	}

	public void display() {
		if (visible) {
			processing_app.smooth();
			processing_app.noStroke();

			// add text drawing to this method
			this.displayText();
		}
	}
	
	public void displayText() {
		if (this.name_font_loaded && this.name_visible) {
			processing_app.fill(this.name_font_color);
			processing_app.textFont(this.name_font, this.name_font_size);
			processing_app.text(this.name, this.name_location.x, this.name_location.y);
		}
		if (this.description_font_loaded && this.description_visible) {
			processing_app.fill(this.description_font_color);
			processing_app.textFont(this.description_font, this.description_font_size);
			processing_app.text(this.description, this.description_location.x, this.description_location.y);
		}

	}
	
	public void turn(float new_start_angle) {
	}

	public void move(float x, float y) {
		this.location = new PVector(x, y);
	}

	public void scale(float new_scale_perecent) {
		this.scale = this.scale * new_scale_perecent;
	}

	public void scaleRel(float new_scale_perecent) {
		this.scale = this.scale * new_scale_perecent;
	}

	
	  /*********************************************************
	   ** SCALE SHIFT METHODS 
	   **/
	  
	  public void scaleShift(float shift_scale) {
	  }

	  public void setScaleShiftMouseOver(float scale_shift) {
		  // input parameter should range from 0 - 1;
		  this.scale_shift_mouse_over = scale_shift;
	  }

	  public void scaleShiftMouseOver() {
		  scaleShift(this.scale_shift_mouse_over);
	  }

	  public void setScaleShiftMousePress(float scale_shift) {
		  // input parameter should range from 0 - 1;
		  this.scale_shift_mouse_press = scale_shift;
	  }

	  public void scaleShiftMousePress() {
		  scaleShift(this.scale_shift_mouse_press);
	  }

	  public void scaleShiftReset() {
	  }

	  public void scaleShiftResetToBase() {
	  }
	/*********************************************************
	 ** MOUSE OVER METHODS 
	 **/

	public void mouseOver() {
	}
	
	protected boolean contains(float x, float y) {
		return false;
	}


}
