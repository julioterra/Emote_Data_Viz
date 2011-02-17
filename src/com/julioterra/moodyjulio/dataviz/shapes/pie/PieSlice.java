package com.julioterra.moodyjulio.dataviz.shapes.pie;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;

import processing.core.PApplet;

public class PieSlice extends ShapeCircle {

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
		      	  case PIE_ARC_SET_RADIUS:
		      		  processing_app.noStroke();
				      processing_app.fill(this.color);
				      processing_app.arc(0, 0, diameter, diameter, 0, angle_slice);
				      break;
		      	  case PIE_LINE_VAR_RADIUS:
				      processing_app.strokeWeight(this.width);
				      processing_app.stroke(this.color);
				      processing_app.line(this.radius,0,0,0);
				      break;
		      }		      
		 processing_app.popMatrix();
	  }

	  /*********************************************************
	   ** SIZE SETTING METHODS 
	   **/

	  /* ****** SET METHODS ******* */

	  public void setScale(float percent_scale) {
		  super.setScale(percent_scale);
		  this.scaleWidth();
//		  PApplet.println(" WIDTH " + this.width + " SCALE " + this.scale);
	  }
	  
	  public void setWidth(float width) {
	      this.width = width;		  
		  this.width_base = this.width;
	  }

	  public void scaleWidth() {
		  if (this.scale > 0) this.width = (float) (this.width_base * this.scale);		  
	  }

	  public void resetScaleWidth() {
		  if (this.scale > 0) this.width = (this.width_base);		  
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
	  	this.value_one = this_slice;
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