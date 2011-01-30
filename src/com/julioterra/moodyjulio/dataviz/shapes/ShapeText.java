package com.julioterra.moodyjulio.dataviz.shapes;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;

import processing.core.PApplet;
import processing.core.PVector;

public class ShapeText extends ShapeRect {

	public ShapeText() {
		super();
	}

	public ShapeText(int x, int y, String text, int color, int font_number, int text_align) {
		super();
		this.loadFontAll(font_number, fonts_size[font_number], 1);
		this.name_font_size = fonts_size[font_number];
		this.alignment = text_align;
		this.setLocation(x, y);
		this.name = text;
		this.setBaseSize(processing_app.textWidth(text), processing_app.textAscent()+processing_app.textDescent());
		this.name_font_number = font_number;
		this.name_font_size = fonts_size[font_number];
		this.name_font_color = color;
		this.setTextVisibleName();
		this.visible = true;
		this.mouse_over_active = true;
		this.mouse_press_active = true;
	}

	public ShapeText(int x, int y, String text, int color, int font_number, int text_align, boolean activate_mouse_over, boolean activate_mouse_press) {
		this(x, y, text, color, font_number, text_align);
		this.mouse_over_active = activate_mouse_over;
		this.mouse_press_active = activate_mouse_press;
	}

	public ShapeText(int x, int y) {
		super(x, y);
	}

	
	/************************************
	 ** 
	 ************************************/
		
	public void setTextAlignMode(int text_align) {
		this.alignment = text_align;
	}
	
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		processing_app.textFont(this.name_font);
		processing_app.textSize(this.name_font_size);
		this.name_location = new PVector(location.x, (location.y+processing_app.textAscent()) );	
	}
	
	
	/************************************
	 ** 
	 ************************************/	

	public void display() {
		if (visible) {
			processing_app.fill(this.name_font_color);
			processing_app.textFont(this.name_font);
			processing_app.textSize((float) (this.name_font_size*scale));
			processing_app.textAlign(this.alignment);
			processing_app.text(this.name, this.name_location.x, this.name_location.y);
			// DEBUG MOUSE OVER CODE
			if (DataVizElement.debug_mouse_over) this.debugMouseOver();
		}		
	}

	protected void debugMouseOver() {
		processing_app.noFill();
		processing_app.stroke(255, 0, 0);
		processing_app.strokeWeight(2);
		if (alignment == PApplet.LEFT) processing_app.rect(this.location.x, this.location.y, this.location.x+size.x, this.location.y+size.y);
		if (alignment == PApplet.RIGHT) processing_app.rect(this.location.x, this.location.y, this.location.x-size.x, this.location.y+size.y);
		if (alignment == PApplet.CENTER) processing_app.rect(this.location.x-size.x/2, this.location.y, this.location.x+size.x/2, this.location.y+size.y);
	}
	
	/************************************
	 ** 
	 ************************************/	

	  public void shiftScale(float shift_scale) {		
		  super.shiftScale(shift_scale);
		  this.name_font_size = (int) (fonts_size[name_font_number] * (this.scale + shift_scale));
	  }

	  public void shiftScaleReset() {
		  super.shiftScaleReset();
		  this.name_font_size = (int) (fonts_size[name_font_number] * (this.scale));
	  }

	  public void shiftScaleResetToBase() {
		  this.scale = 1;
		  size = new PVector (this.size_base.x, this.size_base.y);
		  this.name_font_size = fonts_size[name_font_number];
	  }


	
}
