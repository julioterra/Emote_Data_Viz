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
		this.label.alignment = text_align;
		this.label.text = text;
		this.label.setColorActiveARGB(text_color);
		this.setLocation(x, y);
		this.setSize(processing_app.textWidth(text), processing_app.textAscent()+processing_app.textDescent());
		this.label.setVisible();
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
			
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		processing_app.textFont(this.label.font);
		processing_app.textSize(this.label.size_active);
		this.label.location = new PVector(location.x, (location.y+processing_app.textAscent()) );	
	}
	
	
	/************************************
	 ** 
	 ************************************/	

	public void display() {
		if (visible) {
			processing_app.fill(this.label.color_active);
			processing_app.textFont(this.label.font);
			processing_app.textSize((float) (this.label.size_active*this.label.scale));
			processing_app.textAlign(this.label.alignment);
			processing_app.text(this.label.text, this.label.location.x, this.label.location.y);
			// DEBUG MOUSE OVER
			if (DataVizElement.debug_mouse_over) this.debugMouseOver();			
		}		
	}

	public void mouseOver() {
		if (visible && mouse_over_active) {
			if (this.label.alignment == PApplet.LEFT) { this.isMouseOverRect(location.x, location.y, size_active.x, size_active.y); }
			else if (this.label.alignment == PApplet.RIGHT) { this.isMouseOverRect(location.x-size_active.x, location.y, size_active.x, size_active.y); }
			else if (this.label.alignment == PApplet.CENTER) { this.isMouseOverRect(location.x-(size_active.x/2), location.y, size_active.x, size_active.y); }
		}
	}

	protected void debugMouseOver() {
		processing_app.noFill();
		processing_app.stroke(255, 0, 0);
		processing_app.strokeWeight(2);
		if (this.label.alignment == PApplet.LEFT) processing_app.rect(this.location.x, this.location.y, this.location.x+size_active.x, this.location.y+size_active.y);
		if (this.label.alignment == PApplet.RIGHT) processing_app.rect(this.location.x, this.location.y, this.location.x-size_active.x, this.location.y+size_active.y);
		if (this.label.alignment == PApplet.CENTER) processing_app.rect(this.location.x-size_active.x/2, this.location.y, this.location.x+size_active.x/2, this.location.y+size_active.y);
	}
	
	/************************************
	 ** 
	 ************************************/	

	  public void shiftSize(float shift_scale) {		
		  this.label.size_active = (int) (this.label.size_base * (this.label.scale + shift_scale));
	  }

	  public void shiftScaleReset() {
		  this.label.size_active = (int) (this.label.size_base * (this.label.scale));
	  }

	  public void shiftScaleResetToBase() {
		  this.scale = 1;
		  size_active = new PVector (this.size_base.x, this.size_base.y);
		  this.label.size_active = this.label.size_base;
	  }

	
}
