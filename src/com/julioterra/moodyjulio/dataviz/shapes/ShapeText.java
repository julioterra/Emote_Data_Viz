package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;

public class ShapeText extends ShapeRect {

	public float text_indent;
//	public float font_size_title_base;
	
	public ShapeText() {
		super();
	}

	
	// **********************************************************************
	// ADD A SEPARATE COLOR INPUT FOR THE SHAPE, STROKE, TEXT, THEN STROKE WIDTH - NEED TO ALLOW SETTING ALL OF THESE.
	// **********************************************************************
	public ShapeText(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int text_align) {
		super();
		this.loadFontAll(font_number, fonts_size[font_number], 1);
//		this.font_size_title_base = fonts_size[font_number];
		this.size = new PVector(width, height);
		this.size_base = new PVector(width, height);		
		this.alignment_text = text_align;
		this.text_indent = 4;
		this.setLocation(x, y);
		this.setTextLocation();
		this.font_color_title = text_color;
		this.setColorBaseARGB(color);
		this.title = text;
		this.setTextVisibleName();
		this.visible = true;
		this.mouse_over_active = false;
		this.mouse_press_active = false;
		this.rotation = 0;
	}

	public ShapeText(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int text_align, float rotation) {
		this(x, y, width, height, text, color, text_color, font_number, text_align);
		this.rotation = rotation;
	}

	public ShapeText(int x, int y, int width, int height, String text, String description, int color, int text_color, int font_number, int text_align, float rotation, boolean activate_mouse_over, boolean activate_mouse_press) {
		this(x, y, width, height, text, color, text_color, font_number, text_align, rotation, activate_mouse_over, activate_mouse_press);
		this.description = description;
	}

	public ShapeText(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int text_align, float rotation, boolean activate_mouse_over, boolean activate_mouse_press) {
		this(x, y, width, height, text, color, text_color, font_number, text_align, activate_mouse_over, activate_mouse_press);
		this.rotation = rotation;
	}

	public ShapeText(int x, int y, int width, int height, String text, int color, int text_color, int font_number, int text_align, boolean activate_mouse_over, boolean activate_mouse_press) {
		this(x, y, width, height, text, color, text_color, font_number, text_align);
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
		this.alignment_text = text_align;
	}
	
	public void setTextLocation() {
		processing_app.textFont(this.font_title);
		processing_app.textSize(this.font_size_title);
		float text_height = processing_app.textAscent()+processing_app.textDescent();
		float y_text_offset = text_height/2f - processing_app.textDescent()/2f;
		float y_center = location.y + (this.size.y/2f) + y_text_offset;
		if (alignment_text == PApplet.LEFT) this.location_title = new PVector(location.x + text_indent, y_center) ;	
		if (alignment_text == PApplet.CENTER) this.location_title = new PVector((location.x + this.size.x/2), y_center);	
		if (alignment_text == PApplet.RIGHT) this.location_title = new PVector((size.x + location.x) - text_indent, y_center);
	}
	
	
	/************************************
	 ** 
	 ************************************/	

	public void display() {
		processing_app.pushMatrix();
		processing_app.translate((float)(location.x*scale), (float)(location.y*scale));
		processing_app.rotate(PApplet.radians(this.rotation));
		if (visible) {
			processing_app.noStroke();
			processing_app.fill(this.color);
			processing_app.rect(0,0, (float)(size.x*scale), (float)(size.y*scale));
		}
		processing_app.popMatrix();

		processing_app.pushMatrix();
		processing_app.translate(this.location_title.x, this.location_title.y);
		processing_app.rotate(PApplet.radians(this.rotation));
		if (visible) {
			processing_app.fill(this.font_color_title);
			processing_app.textFont(this.font_title);
			processing_app.textSize((float) (this.font_size_title*scale));
			processing_app.textAlign(this.alignment_text);
			processing_app.text(this.title, 0,0);
		}		
		processing_app.popMatrix();

		if(visible_description) {
			processing_app.fill(this.font_color_description);
			processing_app.textFont(this.font_description);
			processing_app.textSize((float) (this.font_size_description*scale));
			processing_app.textAlign(this.alignment_text);
			processing_app.text(this.description, this.location_description.x, this.location_description.y);		
			PApplet.println("Description is Visible");
		}
	}

	public void mouseOver() {
//		PApplet.println("SHAPE TEXT MOUSE OVER - visible " + visible + " mouse active " + mouse_over_active + " mouse over " + mouse_over);

//		*********************
//		CHECK IF ROTATION, CALCULATE NEW ROLL OVER
		if (visible && mouse_over_active) {
			this.checkMouseOver(location.x, location.y, size.x, size.y); 
		}
	}

	/************************************
	 ** 
	 ************************************/	

	  public void shiftScale(float shift_scale) {		
		  super.shiftScale(shift_scale);
		  this.font_size_title = (int) (this.font_size_title_base * (this.scale + shift_scale));
		  this.setTextLocation();
	  }

	  public void shiftScaleReset() {
		  super.shiftScaleReset();
		  this.font_size_title = (int) (this.font_size_title_base * (this.scale));
		  this.setTextLocation();
	  }

	  public void shiftScaleResetToBase() {
		  super.shiftScaleResetToBase();
		  this.font_size_title = (int) this.font_size_title_base;
		  this.setTextLocation();
	  }
	  
		public void shiftTextNameDescription() {
			super.shiftTextNameDescription();
//			PApplet.println(this.description + " got to shift text visible " + this.text_shift_mouse_over);
		}
		
	  public void loadFontAll(int new_font_number, int size, float font_scale) {
		  super.loadFontAll(new_font_number, size, font_scale);
		  this.font_size_title_base = size;
	  }
	  
}
