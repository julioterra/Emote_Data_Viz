package com.julioterra.moodyjulio.dataviz.shapes.pie;

import processing.core.PApplet;

public class PieSlice extends PieElement {

	  protected float width;
	  protected float width_base;
	  protected float size_in_percent;

	  /*********************************************************
	   ** CONSTRUCTOR METHODS 
	   **/

	  public PieSlice(int x_loc, int y_loc, float radius, float size_in_percent, float angle_start, int color) {
		  super(x_loc, y_loc, radius, angle_start);
		  this.setColorBaseARGB(color);

	      this.width = 0;
		  this.width_base = this.width;
	      this.size_in_percent = size_in_percent;
	      this.angle_slice = PApplet.radians(this.size_in_percent * 360);

	      resetMouseOverShape();

//	      PApplet.println("set-up arc: x " + x_loc + " y " + y_loc + " diameter " + diameter + " angle_start " +  angle_start + " angle_slice " +  PApplet.degrees(angle_slice) + " color " +  this.color + " percent " +  size_in_percent);      
//	      PApplet.println("set-up mouse: x " + mouse_over_location.x + " y " + mouse_over_location.y + " radius " + radius + " angle_start " +  mouse_over_angle_start + " angle_slice " +  mouse_over_angle_slice + " color " +  color);
	  }
	  
	  /*********************************************************
	   ** DISPLAY METHODS 
	   **/

	  public void display() {
		  super.display();
		  processing_app.pushMatrix();
		      processing_app.translate(location.x, location.y);
		      processing_app.rotate(this.angle_start);
		      switch (this.pie_shape_type) {
		      	  case PIE_ARC:
				      processing_app.noStroke();
				      processing_app.fill(this.color);
				      processing_app.arc(0, 0, diameter, diameter, 0, angle_slice);
				      break;
		      	  case PIE_LINE:
				      processing_app.strokeWeight(this.width);
				      processing_app.stroke(this.color);
				      processing_app.line(this.radius,0,0,0);
				      break;
		      }		      
//		      if (pie_shape_type == PIE_ARC) {
//			      processing_app.noStroke();
//			      processing_app.fill(this.color);
//			      processing_app.arc(0, 0, diameter, diameter, 0, angle_slice);
//			      PApplet.println("got to PIE_ARC DISPLAY - radius " + this.radius);
//		      } else if (pie_shape_type == PIE_LINE) {
//			      processing_app.strokeWeight(this.width);
//			      processing_app.stroke(this.color);
//			      processing_app.line(this.radius,0,0,0);
//			      PApplet.println("got to PIE_LINE DISPLAY - radius " + this.radius);
//		      }		      
		 processing_app.popMatrix();
	  }

	  /*********************************************************
	   ** MOUSE OVER METHODS 
	   **/

	  public void mouseOver() {
		  boolean is_mouse_over = this.contains(processing_app.mouseX, processing_app.mouseY);
	      if (this.mouse_over != is_mouse_over) {
	            if (is_mouse_over) {
	            	this.shiftHue();
	            	this.shiftBright();
	            	this.shiftSat();
	            	this.scaleShift();
	            } else {
	            	super.colorReset();
	            	this.resetRadius();
	            }
	            mouse_over = is_mouse_over;
	      }        
	  }
	  
	  /*********************************************************
	   ** SIZE SETTING METHODS 
	   **/

	  /* ****** SET METHODS ******* */

	  public void setWidth(float width) {
	      this.width = width;		  
		  this.width_base = this.width;
	  }

	  public void resetRadius() {
		  this.radius = this.radius - (this.scale_shift * this.radius_base);
		  this.diameter = this.radius * 2;
	  }

	  public void setAngleExtentPercent(float size_in_percent) {
		  this.size_in_percent = size_in_percent;
		  this.angle_slice = PApplet.radians((float) this.size_in_percent * 360);
		  this.resetMouseOverShape();
	  }
	  
	  public void setAngleExtentDegrees(float angle_slice) {
		  this.angle_slice = PApplet.radians(angle_slice);
		  this.size_in_percent = angle_slice / PApplet.radians(360);
		  this.resetMouseOverShape();
	  }
	  
	  public void setPieSliceValue(double this_slice) {
	  	this.value = this_slice;
	  }

	  /* ****** GET METHODS ******* */
	  
	  public float getWidth() {
		  return this.width;
	  }

	  public float getSize_in_percent() {
		  return this.size_in_percent;
	  }

	  public float getSize_in_degrees() {
		  return PApplet.degrees(this.angle_slice);
	  }

	  public float getAngle_slice() {
		  return this.angle_slice;
	  }
	  
	  /*********************************************************
	   ** LOCATION METHODS 
	   ** 
	   ** set and get location methods are defined in Shape parent class
	   ** set and get angle start methods defined in PieElement parent class
	   **
	   **/

}