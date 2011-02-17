package com.julioterra.moodyjulio.dataviz.shapes.panel;

import processing.core.PApplet;
import processing.core.PVector;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;

public class ButtonText extends ShapeText {

	public ButtonText() {
		super();
	}

	public ButtonText(int x, int y, String text, int text_color, int font_number, int text_align) {
		super();
		this.loadFontAll(font_number, fonts_size[font_number], 1);
		this.font_size_title_active = fonts_size[font_number];
		this.alignment_text = text_align;
		this.setLocation(x, y);
		this.setBaseSize(processing_app.textWidth(text), processing_app.textAscent()+processing_app.textDescent());
		this.title = text;
		this.font_number_title = font_number;
		this.font_size_title_active = fonts_size[font_number];
		this.font_color_title_active = text_color;
		this.setTextVisibleName();
		this.visible = true;
		this.mouse_over_active = true;
		this.mouse_clicked_active = true;
	}

	public ButtonText(int x, int y, String text, int text_color, int font_number, int text_align, boolean activate_mouse_over, boolean activate_mouse_press) {
		this(x, y, text, text_color, font_number, text_align);
		this.mouse_over_active = activate_mouse_over;
		this.mouse_clicked_active = activate_mouse_press;
	}

	public ButtonText(int x, int y) {
		super(x, y);
	}

	
	/************************************
	 ** 
	 ************************************/
		
	public void setTextAlignMode(int text_align) {
		this.alignment_text = text_align;
	}
	
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		processing_app.textFont(this.font_title);
		processing_app.textSize(this.font_size_title_active);
		this.location_title = new PVector(location.x, (location.y+processing_app.textAscent()) );	
	}
	
	
	/************************************
	 ** 
	 ************************************/	

	public void display() {
		if (visible) {
			processing_app.fill(this.font_color_title_active);
			processing_app.textFont(this.font_title);
			processing_app.textSize((float) (this.font_size_title_active*scale));
			processing_app.textAlign(this.alignment_text);
			processing_app.text(this.title, this.location_title.x, this.location_title.y);
			// DEBUG MOUSE OVER
			if (DataVizElement.debug_mouse_over) this.debugMouseOver();
		}		
	}

	public void mouseOver() {
		if (visible && mouse_over_active) {
			if (alignment_text == PApplet.LEFT) { this.isMouseOverRect(location.x, location.y, size.x, size.y); }
			else if (alignment_text == PApplet.RIGHT) { this.isMouseOverRect(location.x-size.x, location.y, size.x, size.y); }
			else if (alignment_text == PApplet.CENTER) { this.isMouseOverRect(location.x-(size.x/2), location.y, size.x, size.y); }
		}
	}

	protected void debugMouseOver() {
		processing_app.noFill();
		processing_app.stroke(255, 0, 0);
		processing_app.strokeWeight(2);
		if (alignment_text == PApplet.LEFT) processing_app.rect(this.location.x, this.location.y, this.location.x+size.x, this.location.y+size.y);
		if (alignment_text == PApplet.RIGHT) processing_app.rect(this.location.x, this.location.y, this.location.x-size.x, this.location.y+size.y);
		if (alignment_text == PApplet.CENTER) processing_app.rect(this.location.x-size.x/2, this.location.y, this.location.x+size.x/2, this.location.y+size.y);
	}
	
	/************************************
	 ** 
	 ************************************/	

	  public void shiftSize(float shift_scale) {		
//		  super.shiftScale(shift_scale);
		  this.font_size_title_active = (int) (font_size_title_base * (this.scale + shift_scale));
	  }

	  public void shiftScaleReset() {
		  this.font_size_title_active = (int) (font_size_title_base * (this.scale));
	  }

	  public void shiftScaleResetToBase() {
		  this.scale = 1;
		  size = new PVector (this.size_base.x, this.size_base.y);
		  this.font_size_title_active = (int) this.font_size_title_base;
	  }

	
}
