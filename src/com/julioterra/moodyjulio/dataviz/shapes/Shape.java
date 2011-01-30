package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;

public class Shape extends DataVizElement{

	public PVector location;
	public boolean visible;
	public double scale;

	protected boolean mouse_over_active;		// not yet integrated
	public boolean mouse_over;	
	protected float scale_shift_mouse_over;
	protected float scale_shift_mouse_press;

	protected boolean mouse_press_active;		// not yet integrated
	public boolean mouse_pressed;			// not yet integrated
	protected boolean text_shift_mouse_over;
	protected boolean text_shift_mouse_press;

	public String name;
	public PFont name_font;
	protected boolean name_font_loaded;
	public int name_font_size;
	public int name_font_color;
	public int name_font_number;
	public PVector name_location;
	public boolean name_visible;

	public String description;
	public PFont description_font;
	protected boolean description_font_loaded;
	public int description_font_size;
	public int description_font_color;
	public int description_font_number;
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
		
		this.mouse_over_active = true;
		this.mouse_press_active = true;
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

	public void loadFontAll(int new_font_number, int size, float font_scale) {
		this.loadFontName(new_font_number, size);
		this.loadFontDescription(new_font_number, (int) (size*font_scale));
	}

	public void loadFontName(int new_font_number, int size) {
		this.name_font_number = new_font_number;
		this.name_font = DataVizElement.fonts[new_font_number];
		this.name_font_size = size;
		this.name_font_loaded = true;
	}

	public void loadFontDescription(int new_font_number, int size) {
		this.description_font_number = new_font_number;
		this.description_font = DataVizElement.fonts[new_font_number];
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

	public PVector getLocation() {
		return location;
	}

	public void setLocation(int x, int y) {
		this.location = new PVector(x, y);
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
	
	public void setTextVisibleNameLocation() {
		this.setTextVisibleName();
		this.setTextVisibleDescription();
	}

	public void shiftTextNameLocation() {
		if(this.text_shift_mouse_over) {
			PApplet.println(this.name + " got to shift text visible ");
			this.setTextVisibleNameLocation();
		}
	}

	public void shiftTextNameLocationReset() {
		if(this.text_shift_mouse_over) {
			PApplet.println(this.name + " got to shift text INvisible ");
			this.setTextInvisibleNameLocation();
		}
	}

	public void setTextInvisibleNameLocation() {
		this.setTextInvisibleName();
		this.setTextInvisibleDescription();
	}

	public void setTextVisibleName() {
		this.name_visible = true;
	}

	public void setTextVisibleDescription() {
		this.description_visible = true;
	}
	
	public void setTextInvisibleName() {
		this.name_visible = false;
	}

	public void setTextInvisibleDescription() {
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
			processing_app.textAlign(PApplet.LEFT);
			processing_app.fill(this.name_font_color);
			processing_app.textFont(this.name_font, this.name_font_size);
			processing_app.text(this.name, this.name_location.x, this.name_location.y);
		}
		if (this.description_font_loaded && this.description_visible) {
			processing_app.textAlign(PApplet.LEFT);
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
	  

	
	  public double getScale() {
			return scale;
		}

		public void setScale(double scale) {
			this.scale = scale;
		}
		
	  public void shiftScale(float scale_shift) {
	  }

	  public void setScaleShiftMouseOver(float scale_shift) {
		  // input parameter should range from 0 - 1;
		  this.scale_shift_mouse_over = scale_shift;
	  }

	  public void shiftScaleMouseOver() {
		  if(mouse_over_active) shiftScale(scale_shift_mouse_over);
	  }

	  public void setScaleShiftMousePress(float scale_shift) {
		  // input parameter should range from 0 - 1;
		  this.scale_shift_mouse_press = scale_shift;
	  }

	  public void shiftScaleMousePress() {
		  shiftScale(scale_shift_mouse_press);
	  }

	  public void shiftScaleReset() {
	  }

	  public void shiftScaleResetToBase() {
	  }

	  
	  /*********************************************************
	 ** MOUSE OVER METHODS 
	 **/

	public void mouseOver() {
	}
	
	public void mousePress() {
	}
	
	
	public boolean isMouse_over_active() {
		return mouse_over_active;
	}

	public void setMouse_over_active(boolean mouse_over_active) {
		this.mouse_over_active = mouse_over_active;
	}

	public boolean isMouse_press_active() {
		return mouse_press_active;
	}

	public void setMouse_press_active(boolean mouse_press_active) {
		this.mouse_press_active = mouse_press_active;
	}

	protected boolean contains(float x, float y) {
		return false;
	}


}
