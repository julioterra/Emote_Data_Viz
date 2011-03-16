package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;

public class ShapeRectText extends ShapeRect {

	public float text_indent;
	
	public ShapeRectText() {
		super();
	}

	// **********************************************************************
	// ADD A SEPARATE COLOR INPUT FOR THE SHAPE, STROKE, TEXT, THEN STROKE WIDTH - NEED TO ALLOW SETTING ALL OF THESE.
	// **********************************************************************
	public ShapeRectText(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int text_align) {
		super(x, y, width, height, color);
		this.setLocation(x, y);
		this.setSize(width, height);
		this.setColorARGB(color);
		this.setVisible();
		this.setRotationActive(0);
		this.loadFontAll(font_number, fonts_size[font_number], 1);
		this.label.setAlignment(text_align);
		this.label.setText(text);
		this.label.setColorARGB(text_color);
		this.label.setVisible();
		this.description.setAlignment(text_align);
		this.description.setColorARGB(text_color);
		this.text_indent = 4;
		this.setLocationText();
		this.mouse_over_active = true;
		this.mouse_clicked_active = true;
	}

	public ShapeRectText(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int text_align, float rotation) {
		this(x, y, width, height, text, color, text_color, font_number, text_align);
		this.setRotationActive(rotation);
	}

	public ShapeRectText(int x, int y, int width, int height, String text, String description, int color, int text_color, int font_number, int text_align, float rotation, boolean activate_mouse_over, boolean activate_mouse_press) {
		this(x, y, width, height, text, color, text_color, font_number, text_align, rotation, activate_mouse_over, activate_mouse_press);
		this.description.setText(description);
	}

	public ShapeRectText(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int text_align, float rotation, boolean activate_mouse_over, boolean activate_mouse_press) {
		this(x, y, width, height, text, color, text_color, font_number, text_align, activate_mouse_over, activate_mouse_press);
		this.setRotationActive(rotation);
	}

	public ShapeRectText(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int text_align, boolean activate_mouse_over, boolean activate_mouse_press) {
		this(x, y, width, height, text, color, text_color, font_number, text_align);
		this.mouse_over_active = activate_mouse_over;
		this.mouse_clicked_active = activate_mouse_press;
	}
	
	public ShapeRectText(int x, int y) {
		super(x, y);
	}

	
	/*********************************************************************
	 ** SET LOCATION OF RECTANGLE AND DETERMINE RELATIVE LOCATION OF LABEL
	 *********************************************************************/
		
	public void setLocationText() {
		processing_app.textFont(this.label.font);
		processing_app.textSize(this.label.size_active.x);
		float text_height = processing_app.textAscent()+processing_app.textDescent();
		float y_text_offset = text_height/2f - processing_app.textDescent()/2f;
		float y_center = location.y + (this.size_active.y/2f) + y_text_offset;
		if (this.label.alignment == PApplet.LEFT) this.label.location = new PVector(location.x + text_indent, y_center) ;	
		if (this.label.alignment == PApplet.CENTER) this.label.location = new PVector((location.x + this.size_active.x/2), y_center);	
		if (this.label.alignment == PApplet.RIGHT) this.label.location = new PVector((size_active.x + location.x) - text_indent, y_center);
	}
	
	
	/*********************************************************************
	 ** DISPLAY METHODS
	 *********************************************************************/

	public void display() {
		if (visible) {
			processing_app.pushMatrix();
				processing_app.translate((float)(location.x), (float)(location.y));
				processing_app.rotate(PApplet.radians(this.rotation_active));
				processing_app.noStroke();
				processing_app.fill(this.color_active);
				processing_app.rect(0,0, (float)(size_active.x), (float)(size_active.y));
			processing_app.popMatrix();
			
		}		
	}


	/*********************************************************************
	 ** SET ROTATION METHODS
	 *********************************************************************/

	public void setRotationActive(float angle_in_degrees) {
		super.setRotationActive(angle_in_degrees);
		this.label.setRotationActive(angle_in_degrees);
		this.description.setRotationActive(angle_in_degrees);
	}

	/************************************
	 ** SHIFT SIZE AND SCALE METHODS
	 ************************************/	

	  public void shiftScale(float shift_size) {		
		  super.shiftScale(shift_size);
		  this.label.shiftScale(shift_size);
		  this.setLocationText();
	  }

	  public void shiftScaleReset() {
		  super.shiftScaleReset();
		  this.label.shiftScaleReset();
		  this.setLocationText();
	  }

	  public void resetSize() {
		  super.resetSize();
		  this.label.resetSize();
		  this.setLocationText();
	  }
	  
}
