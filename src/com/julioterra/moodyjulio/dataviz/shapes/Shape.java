package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;

public class Shape extends DataVizElement{

	public PVector location;
	public double scale;
	public int alignment_text;
	public boolean visible;

	protected boolean mouse_over_active;		// not yet integrated
	public boolean mouse_over;	
	protected float scale_shift_mouse_over;
	protected float scale_shift_mouse_press;

	protected boolean mouse_press_active;		// not yet integrated
	public boolean mouse_pressed;			// not yet integrated
	protected boolean text_shift_mouse_over;
	protected boolean text_shift_mouse_press;

	public String title;
	public PFont font_title;
	protected boolean font_loaded_title;
	public int font_size_title;
	public float font_size_title_base;
	public int font_color_title;
	public int font_number_title;
	public PVector location_title;
	public boolean visible_title;

	public String description;
	public PFont font_description;
	protected boolean font_loaded_description;
	public int font_size_description;
	public float font_size_description_base;
	public int font_color_description;
	public int font_number_description;
	public PVector location_description;
	public boolean visible_description;

	public Shape () {
		this(0,0);
		this.alignment_text = PApplet.LEFT;
	}
	
	public Shape (float x, float y) {
		this.location = new PVector(x, y);
		this.visible = true;

		this.location_title = new PVector(0,0);
		this.font_loaded_title = false;
		this.title = "";
		this.font_color_title = 0;

		this.location_description = new PVector(0,0);
		this.font_loaded_description = false;
		this.description = "";
		this.font_color_description = 0;

		this.scale = 1;
		this.scale_shift_mouse_over = 0;
		this.scale_shift_mouse_press = 0;
		
		this.visible_description = false;
		this.visible_title = false;
		
		this.mouse_over_active = true;
		this.mouse_press_active = true;
		this.alignment_text = PApplet.LEFT;
	}
	
	/*********************************************************
	 ** TEXT & FONT METHODS 
	 **/

	public void setName(String new_name) {
		this.title = new_name;
	}

	public void setDescription(String new_name) {
		this.description = new_name;
	}

	public String getName() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public void loadFontAll(int new_font_number, int size, float font_scale) {
		this.loadFontTitle(new_font_number, size);
		this.loadFontDescription(new_font_number, (int) (size*font_scale));
	}

	public void loadFontTitle(int new_font_number, int size) {
		this.font_number_title = new_font_number;
		this.font_title = DataVizElement.fonts[new_font_number];
		this.setFontSizeTitle(size);
		this.font_loaded_title = true;
	}

	public void loadFontDescription(int new_font_number, int size) {
		this.font_number_description = new_font_number;
		this.font_description = DataVizElement.fonts[new_font_number];
		this.setFontSizeDescription(size);
		this.font_loaded_description = true;
	}

	public void setFontColorAll(int font_color) {
		this.font_color_title = font_color;
		this.font_color_description = font_color;
	}

	public void setFontColorName(int font_color) {
		this.font_color_title = font_color;
	}

	public void setFontColorDescription(int font_color) {
		this.font_color_description = font_color;
	}

	public void setFontSizeAll(float size) {
		this.setFontSizeDescription(size);
		this.setFontSizeTitle(size);
	}

	public void setFontSizeTitle(float size) {
		this.font_size_title = (int)(size*this.scale);
		this.font_size_title_base = size;
//		if (size <= 0) this.font_size_title = DataVizElement.fonts_size[this.font_number_title];
	}

	public void setFontSizeDescription(float size) {
		this.font_size_description = (int)(size*this.scale);
		this.font_size_description_base = size;
//		if (size <= 0) this.font_size_description = DataVizElement.fonts_size[this.font_number_description];
	}

	public PVector getLocation() {
		return location;
	}

	public void setLocation(int x, int y) {
		this.location = new PVector(x, y);
	}

	public void setTextLocationNameDescription(float x, float y, float offset_x, float offset_y) {
		this.setTextLocationName(x, y);
		this.setTextLocationDescriptionRel(offset_x, (float)(offset_y) );
	}

	public void setTextLocationName(float x, float y) {
		this.location_title = new PVector(x, y);
	}

	public void setTextLocationDescription(float x, float y) {
		this.location_description = new PVector(x, y);
	}

	public void setTextLocationDescriptionRel(float x, float y) {
		this.location_description = new PVector(this.location_title.x+x, this.location_title.y+y);
	}
	
	public void setTextVisibleNameDescription() {
		this.setTextVisibleName();
		this.setTextVisibleDescription();
	}

	public void shiftTextNameDescription() {
		if(this.text_shift_mouse_over) {
			this.setTextVisibleNameDescription();
		}
	}

	public void shiftTextNameLocationReset() {
		if(this.text_shift_mouse_over) {
			this.setTextInvisibleNameDescription();
		}
	}

	public void setTextInvisibleNameDescription() {
		this.setTextInvisibleName();
		this.setTextInvisibleDescription();
	}

	public void setTextVisibleName() {
		this.visible_title = true;
	}

	public void setTextVisibleDescription() {
		this.visible_description = true;
	}
	
	public void setTextInvisibleName() {
		this.visible_title = false;
	}

	public void setTextInvisibleDescription() {
		this.visible_description = false;
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
		if (this.font_loaded_title && this.visible_title) {
			processing_app.fill(this.font_color_title);
			processing_app.textFont(this.font_title, this.font_size_title);
			processing_app.textAlign(alignment_text);
			processing_app.text(this.title, this.location_title.x, this.location_title.y);
		}
		if (this.font_loaded_description && this.visible_description) {
			processing_app.fill(this.font_color_description);
			processing_app.textFont(this.font_description, this.font_size_description);
			processing_app.textAlign(alignment_text);
			processing_app.text(this.description, this.location_description.x, this.location_description.y);
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
