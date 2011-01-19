package com.julioterra.moodyjulio.dataviz.shapes.pie;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;

import processing.core.PApplet;
import processing.core.PVector;

public class PieSlice extends ShapeColor {

	  public static final int PIE_ARC_SLICE = 0;
	  public static final int PIE_LINE_SLICE = 1; 
	  protected int pie_shape_type;
	
	  protected float radius;
	  protected float radius_base;
	  protected	float radius_shift;
	  protected float diameter;
	  protected float diameter_base;
	  protected float width;
	  protected float width_base;
	  protected float size_in_percent;
	  protected float angle_start;
	  protected float angle_slice;

	  // the slice_mouse_over variable holds a Arc2D object used to determine if mouse is hovering
	  // the slice that is displayed on screen is created using the PApplet arc() method.
	  protected java.awt.geom.Arc2D mouse_over_shape;
	  protected PVector mouse_over_location;
	  protected float mouse_over_angle_start;
	  protected float mouse_over_angle_slice;

	  
	  /*********************************************************
	   ** CONSTRUCTOR METHODS 
	   **/

	  public PieSlice(int x_loc, int y_loc, float radius, float size_in_percent, float angle_start, int color) {
		  super(x_loc, y_loc, color);
	      this.radius = radius;
	      this.radius_base = this.radius;
	      this.radius_shift = 0;
	      this.diameter = this.radius*2;
	      this.diameter_base = this.diameter;
		  this.width = 0;
		  this.width_base = this.width;

	      this.size_in_percent = size_in_percent;
	      this.angle_start = PApplet.radians(angle_start - 90);
	      this.angle_slice = PApplet.radians(this.size_in_percent * 360);

	      this.mouse_over = false;
	      resetMouseOverShape();

	      PApplet.println("set-up arc: x " + x_loc + " y " + y_loc + " diameter " + diameter + " angle_start " +  angle_start + " angle_slice " +  PApplet.degrees(angle_slice) + " color " +  this.color + " percent " +  size_in_percent);      
	      PApplet.println("set-up mouse: x " + mouse_over_location.x + " y " + mouse_over_location.y + " radius " + radius + " angle_start " +  mouse_over_angle_start + " angle_slice " +  mouse_over_angle_slice + " color " +  color);
	  }
	  
	  public void resetMouseOverShape() {
	      this.mouse_over_angle_slice = PApplet.degrees(this.angle_slice);
	      this.mouse_over_angle_start = -PApplet.degrees(this.angle_start) - this.mouse_over_angle_slice;
	      this.mouse_over_location = new PVector (this.location.x - this.radius, this.location.y - this.radius);   
	      this.mouse_over_shape = new java.awt.geom.Arc2D.Float(this.mouse_over_location.x, this.mouse_over_location.y, 
	                                                       this.diameter, this.diameter, 
	                                                       this.mouse_over_angle_start, this.mouse_over_angle_slice, 
	                                                       java.awt.geom.Arc2D.PIE);
	  }

	  /*********************************************************
	   ** DISPLAY METHODS 
	   **/

	  public void display() {
		  super.display();
		  processing_app.pushMatrix();
		      processing_app.translate(location.x, location.y);
		      processing_app.strokeWeight(this.width);
		      processing_app.stroke(this.color);
		      processing_app.rotate(this.angle_start);
		      switch (this.pie_shape_type) {
		      	  case PIE_ARC_SLICE:
				      processing_app.arc(0, 0, diameter, diameter, 0, angle_slice);
				      break;
		      	  case PIE_LINE_SLICE:
				      processing_app.line(this.radius,0,0,0);
				      break;
		      }		      
		 processing_app.popMatrix();
	  }

	  public void turn(float turn_start_angle) {
		  this.angle_start += PApplet.radians(turn_start_angle);
	      mouse_over_shape.setAngleStart(mouse_over_shape.getAngleStart() - turn_start_angle);
	  }

	  public void move(float x, float y) {
	      this.location = new PVector(x, y);
	      mouse_over_shape.setArcByCenter((double)x, (double)y, mouse_over_shape.getWidth()/2, mouse_over_shape.getAngleStart(), mouse_over_shape.getAngleExtent(), java.awt.geom.Arc2D.PIE);
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
	            	this.shiftRadius();
	            } else {
	            	super.colorReset();
	            	this.resetRadius();
	            }
	            mouse_over = is_mouse_over;
	      }        

	  }
	  
	  protected boolean contains(float x, float y) {
	      return mouse_over_shape.contains(x, y);
	  }

	  /*********************************************************
	   ** SIZE SETTING METHODS 
	   **/

	  /* ****** SHIFT METHODS ******* */
	  	
	  public void setRadiusShift(float radius_shift) {
		  this.radius_shift = radius_shift;
	  }

	  public void shiftRadius() {
		  this.radius = this.radius + (this.radius_shift * this.radius_base);
		  this.diameter = this.radius * 2;
		  this.resetMouseOverShape();
	  }

	  /* ****** SET METHODS ******* */

	  public void setWidth(float width) {
	      this.width = width;		  
		  this.width_base = this.width;
	  }

	  public void setRadius(float radius) {
		  this.radius = radius;
	      this.radius_base = this.radius;
		  this.diameter = this.radius * 2;
	      this.diameter_base = this.diameter;
		  this.resetMouseOverShape();
	  }

	  public void resetRadius() {
		  this.radius = this.radius - (this.radius_shift * this.radius_base);
		  this.diameter = this.radius * 2;
	  }

	  public void setSize_in_percent(float size_in_percent) {
		  this.size_in_percent = size_in_percent;
		  this.angle_slice = PApplet.radians(this.size_in_percent * 360);
		  this.resetMouseOverShape();
	  }
	  
	  public void setAngle_slice(float angle_slice) {
		  this.angle_slice = angle_slice;
		  this.size_in_percent = angle_slice / 360;
		  this.resetMouseOverShape();
	  }

	  /* ****** GET METHODS ******* */
	  
	  public float getWidth() {
		  return this.width;
	  }

	  public float getRadius() {
		  return this.radius;
	  }

	  public float getSize_in_percent() {
		  return this.size_in_percent;
	  }

	  public float getAngle_slice() {
		  return this.angle_slice;
	  }
	  
	  /*********************************************************
	   ** LOCATION METHODS 
	   **/

	  /* ****** ANGLE START GET & SET METHODS ******* */
	  	
	  public float getAngle_start() {
		  return this.angle_start;
	  }

	  public void setAngle_start(float angle_start) {
		  this.angle_start = PApplet.radians(angle_start);
		  this.resetMouseOverShape();
	  }
	  
}
