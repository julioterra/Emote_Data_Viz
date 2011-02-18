package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;

public class ShapeRect extends ShapeLabel {

	public PVector size_active;
	public PVector size_base;
	
	/******************************
	 **	CONSTRUCTOR - RECT SHAPE
	 ******************************/

	public ShapeRect() {
		super();
		this.size_active = new PVector(0,0);
		this.size_base = new PVector(size_active.x,size_active.y);
		this.alignment_text = PApplet.LEFT;
		this.rotation = 0;
	}

	public ShapeRect(int x, int y, int size_x, int size_y, int color) {
		super(x, y, color);
		this.size_active = new PVector(size_x, size_y);
		this.size_base = new PVector(size_active.x,size_active.y);
		this.alignment_text = PApplet.LEFT;
		this.rotation = 0;
	}

	public ShapeRect(int x, int y) {
		super(x, y);
		this.size_active = new PVector(0,0);
		this.size_base = new PVector(size_active.x,size_active.y);
		this.alignment_text = PApplet.LEFT;
		this.rotation = 0;
	}

	
	/******************************
	 **	MOUSE OVER 
	 ******************************/

	public void mouseOver() {
		if(visible) this.isMouseOverRect(location.x, location.y, size_active.x, size_active.y);
	}

	public void isMouseOverRect(float x, float y, float width, float height) {
		if ((processing_app.mouseX > x && processing_app.mouseX < x+width) && 
		   (processing_app.mouseY > y && processing_app.mouseY < y+height)	) {
				mouseOverActions();
		} else if ((!(processing_app.mouseX > x && processing_app.mouseX < x+width) || 
				  !(processing_app.mouseY > y && processing_app.mouseY < y+height))  ) {
				mouseOffActions();
		}
	}

	/******************************
	 **	DISPLAY METHOD 
	 ******************************/

	public void display() {
		super.display();
		if (visible) {
			processing_app.pushMatrix();
			processing_app.translate((float)(location.x*scale), (float)(location.y*scale));
			processing_app.rotate(PApplet.radians(this.rotation));
			processing_app.fill(this.color_active);
			processing_app.rect(0,0, (float)(size_active.x*scale), (float)(size_active.y*scale));
			processing_app.popMatrix();
		}
	}
	
	/******************************
	 **	GETTERS & SETTERS - RECT SHAPE 
	 ******************************/
	
	public PVector getSize() {
		return size_active;
	}

	public void setBaseSize(float x, float y) {
		this.size_active = new PVector(x, y);
		this.size_base = new PVector(size_active.x,size_active.y);
		this.scale = 1;
	}

	public void setSize(float x, float y) {
		this.size_active = new PVector(x, y);
		this.scale = (float)((float)x/size_active.x);
	}
	
	  public void shiftSize(float shift_scale) {	
		  super.shiftSize(shift_scale);
		  size_active = new PVector ((float)(this.size_active.x * (this.scale + shift_scale)), (float)(this.size_active.y * (this.scale + shift_scale)));
//		  PApplet.println("SHIFT SCALE METHOD - SHAPE RECT - scale " + this.scale + " base size " + this.size_base.x + " size " + this.size.x + " base color " + this.color_base);
	  }

	  public void shiftScaleReset() {
		  super.shiftScaleReset();
		  size_active = new PVector ((float)(this.size_base.x * this.scale), (float)(this.size_base.y * this.scale) );
//		  PApplet.println("SHIFT SCALE RESET METHOD - SHAPE RECT - scale " + this.scale + " base size " + this.size_base.x + " size " + this.size.x + " base color " + this.color_base);
	  }

	  public void shiftScaleResetToBase() {
		  this.scale = 1;
		  size_active = new PVector (this.size_base.x, this.size_base.y);
	  }


		
}
