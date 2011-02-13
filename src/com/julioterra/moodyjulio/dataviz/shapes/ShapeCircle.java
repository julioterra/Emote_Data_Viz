package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;


public class ShapeCircle extends ShapeColor{

	  public static final int UNDEFINED = -1;
	  public static final int PIE_ARC_SET_RADIUS = 0;
	  public static final int PIE_ARC_VAR_RADIUS = 1;
	  public static final int PIE_LINE_SET_RADIUS = 2;
	  public static final int PIE_LINE_VAR_RADIUS = 3; 
	  protected int pie_shape_type = UNDEFINED;

	  public static final int ANGLE = 0;
	  public static final int RADIUS = 1;
	  public static final int HUE = 2; 
	  public static final int SATURATION = 3; 
	  protected int value_1_type = UNDEFINED;
	  protected int value_2_type = UNDEFINED;
	  protected int value_3_type = UNDEFINED;

	  protected float radius;
	  protected float radius_base;
	  protected float diameter;
	  protected float diameter_base;

	  protected float angle_start;
	  protected float angle_slice;
	  public double value_one;
	  public double value_two;
	  public double value_three;

	  // the slice_mouse_over variable holds a Arc2D object used to determine if mouse is hovering
	  // the slice that is displayed on screen is created using the PApplet arc() method.
	  protected java.awt.geom.Arc2D mouse_over_shape;
	  protected PVector mouse_over_location;
	  protected float mouse_over_angle_start;
	  protected float mouse_over_angle_slice;

	  
	  /*********************************************************
	   ** CONSTRUCTOR METHODS 
	   **/

	  public ShapeCircle(int x_loc, int y_loc, float radius, float angle_start) {
		  super(x_loc, y_loc, ShapeColor.colorARGB(255,255,255,255));
	      this.radius = radius;
	      this.radius_base = this.radius;
	      this.diameter = this.radius*2;
	      this.diameter_base = this.diameter;

	      this.angle_slice = 0;
	      this.value_one = PApplet.degrees(this.angle_slice);
	      this.angle_start = PApplet.radians(angle_start - 90);
	      this.angle_slice = PApplet.radians(360);

	      resetMouseOverShape();

//	      PApplet.println("set-up arc: x " + x_loc + " y " + y_loc + " diameter " + diameter + " angle_start " +  angle_start + " angle_slice " +  PApplet.degrees(angle_slice) + " color " +  this.color);      
//	      PApplet.println("set-up mouse: x " + mouse_over_location.x + " y " + mouse_over_location.y + " radius " + radius + " angle_start " +  mouse_over_angle_start + " angle_slice " +  mouse_over_angle_slice + " color " +  color);
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

	  public void turn(float turn_start_angle) {
		  this.angle_start += PApplet.radians(turn_start_angle);
	      this.mouse_over_shape.setAngleStart(mouse_over_shape.getAngleStart() - turn_start_angle);
	  }

	  public void move(float x, float y) {
	      this.location = new PVector(x, y);
	      this.mouse_over_shape.setArcByCenter((double)x, (double)y, mouse_over_shape.getWidth()/2, mouse_over_shape.getAngleStart(), mouse_over_shape.getAngleExtent(), java.awt.geom.Arc2D.PIE);
	  }

	  public void scale(float percent_scale) {
		  super.scale(percent_scale);
		  setRadius((float) (this.radius_base * this.scale));
		  this.resetMouseOverShape();
	  }


	  /*********************************************************
	   ** SCALE SHIFT METHODS 
	   **/
	  
	  public void shiftScale(float shift_scale) {		  
		  this.setRadius((float) (this.radius * (this.scale+shift_scale)));
	  }

	  public void shiftScaleReset() {
		  this.setRadius((float) (this.radius_base * this.scale));
	  }

	  public void shiftScaleResetToBase() {
		  this.scale = 1;
		  this.setRadius(this.radius_base);
	  }

	  /*********************************************************
	   ** MOUSE OVER METHODS 
	   **/

	  public void mouseOver() {
		  boolean is_mouse_over = this.contains(processing_app.mouseX, processing_app.mouseY);
	      if ((this.mouse_over != is_mouse_over) && this.mouse_over_active) {
	            if (is_mouse_over) {
	            	this.shiftHueMouseOver();
	            	this.shiftBrightMouseOver();
	            	this.shiftSatMouseOver();
	            	this.shiftScaleMouseOver();
	            	this.shiftTextNameDescription();
	            } else {
	            	this.shiftColorReset();
	            	this.shiftScaleReset();
	            	this.shiftTextNameLocationReset();
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

	  /* ****** SET METHODS ******* */

	  public void setBaseRadius(float radius) {
		  this.radius = radius;
	      this.radius_base = this.radius;
		  this.diameter = this.radius * 2;
	      this.diameter_base = this.diameter;
		  this.resetMouseOverShape();
	  }

	  public void setRadius(float radius) {
		  this.radius = radius;
		  this.diameter = this.radius * 2;
		  this.resetMouseOverShape();
  }

	  public void resetRadius() {
		  this.setRadius(this.radius_base);
	  }

	  /* ****** GET METHODS ******* */
	  
	  public float getRadius() {
		  return this.radius;
	  }

	  public float getDiameter() {
		  return this.diameter;
	  }
	  
	  /*********************************************************
	   ** LOCATION METHODS 
	   **/

	  /* ****** ANGLE START GET & SET METHODS ******* */
	  	
	  public float getPieElementAngleStart() {
		  return this.angle_start;
	  }

	  public void setPieElementAngleStart(float angle_start) {
		  this.angle_start = PApplet.radians(angle_start - 90);
	      this.mouse_over_shape.setAngleStart(mouse_over_shape.getAngleStart() - angle_start);
	  }
	  
	  /*********************************************************
	   ** SET VALUE METHODS 
	   **/
	  
	  public void setValue(double new_value) {
		  this.value_one = new_value;
	  }
	  
	  public double getValue() {
		  return this.value_one;
	  }

	  public void setValue2(double new_value) {
		  this.value_two = new_value;
	  }
	  
	  public double getValue2() {
		  return this.value_two;
	  }

	  public void setValue3(double new_value) {
		  this.value_three = new_value;
	  }
	  
	  public double getValue3() {
		  return this.value_three;
	  }

}
