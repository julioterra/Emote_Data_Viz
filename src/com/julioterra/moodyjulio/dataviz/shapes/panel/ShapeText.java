package com.julioterra.moodyjulio.dataviz.shapes.panel;

import processing.core.PApplet;
import processing.core.PVector;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRectText;

public class ShapeText extends ShapeRectText {

	public ShapeText() {
		super();
	}

	public ShapeText(int x, int y, String text, int text_color, int font_number, int text_align) {
		super();
		this.loadFontAll(font_number, fonts_size[font_number], 1);
		this.font_size_label_active = fonts_size[font_number];
		this.alignment_text = text_align;
		this.setLocation(x, y);
		this.setBaseSize(processing_app.textWidth(text), processing_app.textAscent()+processing_app.textDescent());
		this.label = text;
		this.font_number_label = font_number;
		this.font_size_label_active = fonts_size[font_number];
		this.font_color_label_active = text_color;
		this.setTextVisibleName();
		this.visible = true;
		this.mouse_over_active = true;
		this.mouse_clicked_active = true;
	}

	public ShapeText(int x, int y, String text, int text_color, int font_number, int text_align, boolean activate_mouse_over, boolean activate_mouse_press) {
		this(x, y, text, text_color, font_number, text_align);
		this.mouse_over_active = activate_mouse_over;
		this.mouse_clicked_active = activate_mouse_press;
	}

	public ShapeText(int x, int y) {
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
		processing_app.textFont(this.font_label);
		processing_app.textSize(this.font_size_label_active);
		this.location_label = new PVector(location.x, (location.y+processing_app.textAscent()) );	
	}
	
	
	/************************************
	 ** 
	 ************************************/	

	public void display() {
		if (visible) {
			processing_app.fill(this.font_color_label_active);
			processing_app.textFont(this.font_label);
			processing_app.textSize((float) (this.font_size_label_active*scale));
			processing_app.textAlign(this.alignment_text);
			processing_app.text(this.label, this.location_label.x, this.location_label.y);
			// DEBUG MOUSE OVER
			if (DataVizElement.debug_mouse_over) this.debugMouseOver();
			
			if(visible_description) {
				processing_app.fill(this.font_color_description_active);
				processing_app.textFont(this.font_description);
				processing_app.textSize((float) (this.font_size_description_active*scale));
				processing_app.textAlign(this.alignment_text);
				processing_app.text(this.description, this.location_description.x, this.location_description.y);		
			}
		}		
	}

	public void mouseOver() {
		if (visible && mouse_over_active) {
			if (alignment_text == PApplet.LEFT) { this.isMouseOverRect(location.x, location.y, size_active.x, size_active.y); }
			else if (alignment_text == PApplet.RIGHT) { this.isMouseOverRect(location.x-size_active.x, location.y, size_active.x, size_active.y); }
			else if (alignment_text == PApplet.CENTER) { this.isMouseOverRect(location.x-(size_active.x/2), location.y, size_active.x, size_active.y); }
		}
	}

	protected void debugMouseOver() {
		processing_app.noFill();
		processing_app.stroke(255, 0, 0);
		processing_app.strokeWeight(2);
		if (alignment_text == PApplet.LEFT) processing_app.rect(this.location.x, this.location.y, this.location.x+size_active.x, this.location.y+size_active.y);
		if (alignment_text == PApplet.RIGHT) processing_app.rect(this.location.x, this.location.y, this.location.x-size_active.x, this.location.y+size_active.y);
		if (alignment_text == PApplet.CENTER) processing_app.rect(this.location.x-size_active.x/2, this.location.y, this.location.x+size_active.x/2, this.location.y+size_active.y);
	}
	
	/************************************
	 ** 
	 ************************************/	

	  public void shiftSize(float shift_scale) {		
		  this.font_size_label_active = (int) (font_size_label_base * (this.scale + shift_scale));
	  }

	  public void shiftScaleReset() {
		  this.font_size_label_active = (int) (font_size_label_base * (this.scale));
	  }

	  public void shiftScaleResetToBase() {
		  this.scale = 1;
		  size_active = new PVector (this.size_base.x, this.size_base.y);
		  this.font_size_label_active = (int) this.font_size_label_base;
	  }

	
}
