package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;

public class ShapeRect extends ShapeColor {

	public PVector size;
	public PVector size_base;
	public int alignment;
	
	/******************************
	 **	CONSTRUCTOR - RECT SHAPE
	 ******************************/

	public ShapeRect() {
		super();
		this.size = new PVector(0,0);
		this.size_base = new PVector(size.x,size.y);
		this.alignment = PApplet.LEFT;
		// TODO Auto-generated constructor stub
	}

	public ShapeRect(int x, int y, int size_x, int size_y, int color) {
		super(x, y, color);
		this.size = new PVector(size_x, size_y);
		this.size_base = new PVector(size_x,size_x);
		this.alignment = PApplet.LEFT;
		// TODO Auto-generated constructor stub

		PApplet.println("Base Size " + this.size_base.x + " " + this.size_base.y);
	}

	public ShapeRect(int x, int y) {
		super(x, y);
		this.size = new PVector(0,0);
		this.size_base = new PVector(size.x,size.y);
		this.alignment = PApplet.LEFT;
		// TODO Auto-generated constructor stub
	}

	
	/******************************
	 **	MOUSE OVER 
	 ******************************/

	public void mouseOver() {
		super.mouseOver();
		if (visible && mouse_over_active) {
			if (alignment == PApplet.LEFT) { this.checkMouseOver(location.x, location.y, size.x, size.y); }
			else if (alignment == PApplet.RIGHT) { this.checkMouseOver(location.x-size.x, location.y, size.x, size.y); }
			else if (alignment == PApplet.CENTER) { this.checkMouseOver(location.x-(size.x/2), location.y, size.x, size.y); }
		}
	}

	public void checkMouseOver(float x, float y, float width, float height) {

		if(	!this.mouse_over && (processing_app.mouseX > x && processing_app.mouseX < x+width) && 
						   (processing_app.mouseY > y && processing_app.mouseY < y+height)	) {
				this.shiftHueMouseOver();
				this.shiftSatMouseOver();
				this.shiftBrightMouseOver();
				this.shiftTextNameLocation();
				this.shiftScaleMouseOver();
				this.mouse_over = true;
				PApplet.println("Mouse Over");
		
		} else if ( this.mouse_over && !((processing_app.mouseX > x && processing_app.mouseX < x+width) && 
									(processing_app.mouseY > y && processing_app.mouseY < y+height))  ) {
				this.shiftColorReset();
				this.shiftTextNameLocationReset();
				this.shiftScaleReset();
				this.mouse_over = false;
				PApplet.println("Mouse Gone");
				
		}
	}
	
	/******************************
	 **	DISPLAY METHOD 
	 ******************************/

	public void display() {
		super.display();
		if (visible) {
			processing_app.rectMode(this.alignment);
			processing_app.fill(this.color);
			processing_app.rect((float)(location.x*scale), (float)(location.y*scale), (float)(size.x*scale), (float)(size.y*scale));
		}
	}
	
	/******************************
	 **	GETTERS & SETTERS - RECT SHAPE 
	 ******************************/
	
	public PVector getSize() {
		return size;
	}

	public void setBaseSize(float x, float y) {
		this.size = new PVector(x, y);
		this.size_base = new PVector(size.x,size.y);
		this.scale = 1;
	}

	public void setSize(float x, float y) {
		this.size = new PVector(x, y);
		this.scale = (float)((float)x/size.x);
	}
	
	  public void shiftScale(float shift_scale) {		  
		  size = new PVector ((float)(this.size.x * (this.scale + shift_scale)), (float)(this.size.y * (this.scale + shift_scale)));
	  }

	  public void shiftScaleReset() {
		  size = new PVector ((float)(this.size_base.x * (this.scale)), (float)(this.size_base.y * (this.scale)) );
	  }

	  public void shiftScaleResetToBase() {
		  this.scale = 1;
		  size = new PVector (this.size_base.x, this.size_base.y);
	  }


		
}
