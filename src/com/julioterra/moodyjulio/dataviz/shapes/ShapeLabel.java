package com.julioterra.moodyjulio.dataviz.shapes;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class ShapeLabel extends ShapeColor {
	
	public int alignment_text;
	public String label;
	public String description;
	public PFont font_label;
	public PFont font_description;
	protected boolean font_loaded_description;
	protected boolean font_loaded_label;
	public PVector location_label;
	public PVector location_description;
	public float font_size_label_base;
	public float font_size_description_base;
	public int font_color_label_base;
	public int font_color_description_base;
	public int font_number_label;
	public int font_number_description;
	
	protected int font_size_label_active;
	protected int font_size_description_active;
	protected int font_color_label_active;
	protected int font_color_description_active;

	public boolean visible_label;
	public boolean visible_description;
	protected boolean text_toggle_mouse_over;
	protected boolean text_toggle_mouse_clicked;
	
	public ShapeLabel () {
		this(0,0, Transparent_Color);
	}

	public ShapeLabel (float x, float y, int color) {
		super(x, y, color);
		this.alignment_text = PApplet.LEFT;
		this.visible_description = false;
		this.visible_label = false;
		
		this.location_label = new PVector(0,0);
		this.font_loaded_label = false;
		this.label = "";
		this.font_color_label_active = 0;
		this.font_color_label_base = 0;

		this.location_description = new PVector(0,0);
		this.font_loaded_description = false;
		this.description = "";
		this.font_color_description_active = 0;
		this.font_color_description_base = 0;
		
		this.text_toggle_mouse_over = false;
		this.text_toggle_mouse_clicked = false;
	}

	public ShapeLabel (float x, float y) {
		this(x, y, Transparent_Color);
	}
	
	public void display() {
		super.display();
		if (visible) this.displayText();
	}

	public void displayText() {
		if (this.font_loaded_label && this.visible_label) {
			processing_app.fill(this.font_color_label_active);
			processing_app.textFont(this.font_label, this.font_size_label_active);
			processing_app.textAlign(alignment_text);
			processing_app.text(this.label, this.location_label.x, this.location_label.y);
		}
		if (this.font_loaded_description && this.visible_description) {
			processing_app.fill(this.font_color_description_active);
			processing_app.textFont(this.font_description, this.font_size_description_active);
			processing_app.textAlign(alignment_text);
			processing_app.text(this.description, this.location_description.x, this.location_description.y);
		}

	}

	/*********************************************************
	 ** MOUSE OVER METHODS 
	 **/

	public void mouseOverActions() {
		if(!this.mouse_over) {
			if(this.mouse_over_active) {
				this.shiftHue(this.hue_shift_mouse_over);
				this.shiftSat(this.sat_shift_mouse_over);
				this.shiftBright(this.bright_shift_mouse_over);
				
				this.shiftSize(this.size_shift_mouse_over);
				if(this.text_toggle_mouse_over) this.setTextVisibleNameDescription();
				this.mouseOverExternalActions();
//				PApplet.println("Mouse Over");
			}
			this.mouse_over = true;			             // important to only change the flag at the end of the function
		}
	}

	public void mouseOffActions() {
		if (this.mouse_over) {
			if(this.mouse_over_active) {
				this.shiftScaleReset();
				if(this.text_toggle_mouse_over) this.setTextInvisibleNameDescription();
				this.mouseOffExternalActions();
				this.shiftColorReset();
//				PApplet.println("Mouse Gone");
			}
			this.mouse_over = false;
		}
	}

	public void mouseClickedActions() {
		if(this.mouse_clicked_active) {
			if (this.mouse_clicked_toggle) {
				this.mouse_clicked_toggle = false;
				this.shiftColorReset();
				this.shiftScaleReset();
				if(this.text_toggle_mouse_clicked) this.setTextInvisibleNameDescription();
//					PApplet.println("Mouse Clicked - toggle off");
			} else {
				this.mouse_clicked_toggle = true;
				this.shiftSize(this.size_shift_mouse_clicked);
				if(this.text_toggle_mouse_clicked) this.setTextVisibleNameDescription();
				this.shiftHue(this.hue_shift_mouse_clicked);
				this.shiftSat(this.sat_shift_mouse_clicked);
				this.shiftBright(this.bright_shift_mouse_clicked);
//					PApplet.println("Mouse Clicked - toggle on");
			}
			this.mouseClickedExternalActions();	
//			}
		this.mouse_pressed = false;
	}
	}

	public void setShiftAllMouseOver(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over) {
//		this.hue_shift_mouse_over = hue_shift;
//		this.sat_shift_mouse_over = saturation_shift;
//		this.bright_shift_mouse_over = brightness_shift;
		this.setColorShiftMouseOver(hue_shift, saturation_shift, brightness_shift);
		this.size_shift_mouse_over = scale_shift;
		this.text_toggle_mouse_over = text_shift_mouse_over;
		this.mouse_over_active = true;
	}
	
	public void setShiftAllMouseClicked(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over) {
//		this.hue_shift_mouse_clicked = hue_shift;
//		this.sat_shift_mouse_clicked = saturation_shift;
//		this.bright_shift_mouse_clicked = brightness_shift;
		this.setColorShiftMouseClicked(hue_shift, saturation_shift, brightness_shift);
		this.size_shift_mouse_clicked = scale_shift;
		this.text_toggle_mouse_clicked = text_shift_mouse_over;
		this.mouse_clicked_active = true;
	}
	
	/*********************************************************
	 ** TEXT & FONT METHODS 
	 **/

	public void setName(String new_name) {
		this.label = new_name;
	}

	public void setDescription(String new_name) {
		this.description = new_name;
	}

	public String getName() {
		return this.label;
	}

	public String getDescription() {
		return this.description;
	}

	public void loadFontAll(int new_font_number, int size, float font_scale) {
		this.loadFontTitle(new_font_number, size);
		this.loadFontDescription(new_font_number, (int) (size*font_scale));
	}

	public void loadFontTitle(int new_font_number, int size) {
		this.font_number_label = new_font_number;
		this.font_label = DataVizElement.fonts[new_font_number];
		this.setFontSizeTitle(size);
		this.font_loaded_label = true;
	}

	public void loadFontDescription(int new_font_number, int size) {
		this.font_number_description = new_font_number;
		this.font_description = DataVizElement.fonts[new_font_number];
		this.setFontSizeDescription(size);
		this.font_loaded_description = true;
	}

	public void setFontColorAll(int font_color) {
		this.setFontColorName(font_color);
		this.setFontColorDescription(font_color);
	}

	public void setFontColorName(int font_color) {
		this.font_color_label_active = font_color;
		this.font_color_label_base = this.font_color_label_active;
	}

	public void setFontColorDescription(int font_color) {
		this.font_color_description_active = font_color;
		this.font_color_description_base = this.font_color_description_active;
	}

	public void setFontSizeAll(float size) {
		this.setFontSizeDescription(size);
		this.setFontSizeTitle(size);
	}

	public void setFontSizeTitle(float size) {
		this.font_size_label_active = (int)(size*this.scale);
		this.font_size_label_base = size;
	}

	public void setFontSizeDescription(float size) {
		this.font_size_description_active = (int)(size*this.scale);
		this.font_size_description_base = size;
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
		this.location_label = new PVector(x, y);
	}

	public void setTextLocationDescription(float x, float y) {
		this.location_description = new PVector(x, y);
	}

	public void setTextLocationDescriptionRel(float x, float y) {
		this.location_description = new PVector(this.location_label.x+x, this.location_label.y+y);
	}
	
	public void setTextVisibleNameDescription() {
		this.setTextVisibleName();
		this.setTextVisibleDescription();
	}

	public void setTextInvisibleNameDescription() {
		this.setTextInvisibleName();
		this.setTextInvisibleDescription();
	}

	public void shiftOffTextMouseOver() {
		if(this.text_toggle_mouse_over) {
			this.setTextInvisibleNameDescription();
		}
	}

	public void setTextVisibleName() {
		this.visible_label = true;
	}

	public void setTextInvisibleName() {
		this.visible_label = false;
	}

	public void setTextVisibleDescription() {
		this.visible_description = true;
	}
	
	public void setTextInvisibleDescription() {
		this.visible_description = false;
	}


}
