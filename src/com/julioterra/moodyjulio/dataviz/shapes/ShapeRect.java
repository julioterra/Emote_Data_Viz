package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;

public class ShapeRect extends ShapeViz {

	/******************************
	 **	CONSTRUCTOR - RECT SHAPE
	 ******************************/

	public ShapeRect() {
		this(0,0);
	}

	public ShapeRect(int x, int y, int width, int height, int color) {
		super(x, y, color);
		this.setRotationActive(0);
		this.size_base = new PVector(width, height);
		this.size_active = new PVector(width, height);
		this.scale = 1;
	}

	public ShapeRect(int x, int y) {
		this(x, y, 0, 0, Transparent_Color);
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
		if (visible) {
			super.display();
			processing_app.pushMatrix();
				processing_app.translate((float)(location.x), (float)(location.y));
				processing_app.rotate(PApplet.radians(this.rotation_active));
				processing_app.fill(this.color_active);
				processing_app.rect(0,0, (float)(size_active.x), (float)(size_active.y));
			processing_app.popMatrix();
		}
	}
	
		
}
