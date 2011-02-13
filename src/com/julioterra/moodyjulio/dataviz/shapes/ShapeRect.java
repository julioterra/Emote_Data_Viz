package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;

public class ShapeRect extends ShapeColor {

	public PVector size;
	public PVector size_base;
	public float rotation;
	
	/******************************
	 **	CONSTRUCTOR - RECT SHAPE
	 ******************************/

	public ShapeRect() {
		super();
		this.size = new PVector(0,0);
		this.size_base = new PVector(size.x,size.y);
		this.alignment_text = PApplet.LEFT;
		this.rotation = 0;
	}

	public ShapeRect(int x, int y, int size_x, int size_y, int color) {
		super(x, y, color);
		this.size = new PVector(size_x, size_y);
		this.size_base = new PVector(size.x,size.y);
		this.alignment_text = PApplet.LEFT;
		this.rotation = 0;
	}

	public ShapeRect(int x, int y) {
		super(x, y);
		this.size = new PVector(0,0);
		this.size_base = new PVector(size.x,size.y);
		this.alignment_text = PApplet.LEFT;
		this.rotation = 0;
	}

	
	/******************************
	 **	MOUSE OVER 
	 ******************************/

	public void mouseOver() {
		if (mouse_over_active) { this.checkMouseOver(location.x, location.y, size.x, size.y); }
	}

	public void checkMouseOver(float x, float y, float width, float height) {
		if(	this.mouse_over == false && (processing_app.mouseX > x && processing_app.mouseX < x+width) && 
						   (processing_app.mouseY > y && processing_app.mouseY < y+height)	) {
				mouseOverShiftOn();
				this.mouse_over = true;
		} else if ( this.mouse_over == true && (!(processing_app.mouseX > x && processing_app.mouseX < x+width) || 
									!(processing_app.mouseY > y && processing_app.mouseY < y+height))  ) {
				mouseOverShiftOff();
		}
	}
	
	public void mouseOverShiftOn() {
		this.shiftHueMouseOver();
		this.shiftSatMouseOver();
		this.shiftBrightMouseOver();
		this.shiftTextNameDescription();
		this.shiftScaleMouseOver();
		this.mouse_over = true;
//		PApplet.println("Mouse Over");
	}

	public void mouseOverShiftOff() {
		this.shiftColorReset();
		this.shiftTextNameLocationReset();
		this.shiftScaleReset();
		this.mouse_over = false;
//		PApplet.println("Mouse Gone");
	}

	
	/******************************
	 **	DISPLAY METHOD 
	 ******************************/

	public void display() {
		super.display();
		processing_app.pushMatrix();
		processing_app.translate((float)(location.x*scale), (float)(location.y*scale));
		processing_app.rotate(PApplet.radians(this.rotation));
		if (visible) {
			processing_app.fill(this.color);
			processing_app.rect(0,0, (float)(size.x*scale), (float)(size.y*scale));
		}
		processing_app.popMatrix();
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
		  super.shiftScale(shift_scale);
		  size = new PVector ((float)(this.size.x * (this.scale + shift_scale)), (float)(this.size.y * (this.scale + shift_scale)));
//		  PApplet.println("SHIFT SCALE METHOD - SHAPE RECT - scale " + this.scale + " base size " + this.size_base.x + " size " + this.size.x + " base color " + this.color_base);
	  }

	  public void shiftScaleReset() {
		  super.shiftScaleReset();
		  size = new PVector ((float)(this.size_base.x * this.scale), (float)(this.size_base.y * this.scale) );
//		  PApplet.println("SHIFT SCALE RESET METHOD - SHAPE RECT - scale " + this.scale + " base size " + this.size_base.x + " size " + this.size.x + " base color " + this.color_base);
	  }

	  public void shiftScaleResetToBase() {
		  this.scale = 1;
		  size = new PVector (this.size_base.x, this.size_base.y);
	  }


		
}
