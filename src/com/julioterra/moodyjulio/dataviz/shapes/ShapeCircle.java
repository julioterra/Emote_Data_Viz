package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;


public class ShapeCircle extends ShapeViz {

	  public static final int UNDEFINED = -1;
	  public static final int PIE_ARC_SET_RADIUS = 0;
	  public static final int PIE_ARC_VAR_RADIUS = 1;
	  public static final int PIE_LINE_SET_RADIUS = 2;
	  public static final int PIE_LINE_VAR_RADIUS = 3; 
	  protected int pie_shape_type = UNDEFINED;

	  protected PVector radius_active;
	  protected PVector radius_base;

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

	  public ShapeCircle(int x_loc, int y_loc, float radius, float angle_start) {
		  super(x_loc, y_loc, ShapeColor.colorARGB(255,255,255,255));
	      this.rotation_base = angle_start;
	      this.rotation_active = this.rotation_base;
	      
	      this.value_one = 0;
	      this.angle_start = convertRotationToAngleStart(this.rotation_active);
	      this.angle_slice = PApplet.radians(360);

	      this.radius_base = new PVector (radius, radius);
	      this.radius_active = new PVector(this.radius_base.x, this.radius_base.y);
	      this.size_base = new PVector(this.radius_base.x*2, this.radius_base.y*2);
	      this.size_active = new PVector(this.size_base.x, this.size_base.y);

	      resetMouseOverShape();
	  }
	  
	  public void resetMouseOverShape() {
	      this.mouse_over_angle_slice = PApplet.degrees(this.angle_slice);
	      this.mouse_over_angle_start = -PApplet.degrees(this.angle_start) - this.mouse_over_angle_slice;
	      this.mouse_over_location = new PVector (this.location.x - this.radius_active.x, this.location.y - this.radius_active.y);   
	      this.mouse_over_shape = new java.awt.geom.Arc2D.Float(this.mouse_over_location.x, this.mouse_over_location.y, 
	                                                       (float)(this.size_active.x), (float)(this.size_active.y), 
	                                                       this.mouse_over_angle_start, this.mouse_over_angle_slice, 
	                                                       java.awt.geom.Arc2D.PIE);
	  }

	  /*********************************************************
	   ** DISPLAY METHODS 
	   **/

	  public float convertRotationToAngleStart(float angle_in_degrees) {
		  return PApplet.radians(angle_in_degrees - 90);
	  }
	  
	  public void display() {
		  super.display();
		  processing_app.pushMatrix();
		      processing_app.translate(location.x, location.y);
		      processing_app.rotate(this.angle_start);
	  		  processing_app.noStroke();
		      processing_app.fill(this.color_active);
		      processing_app.arc(0, 0, (float)(size_active.x), (float)(size_active.y), 0, angle_slice);
		 processing_app.popMatrix();
	  }

	  /* ****** ANGLE START GET & SET METHODS ******* */
	  	

	  public void move(float x, float y) {
		  super.move(x, y);
	      this.mouse_over_shape.setArcByCenter((double)x, (double)y, mouse_over_shape.getWidth()/2, mouse_over_shape.getAngleStart(), mouse_over_shape.getAngleExtent(), java.awt.geom.Arc2D.PIE);
	  }

	  public void setScale(float percent_scale) {
		  super.setScale(percent_scale);
		  this.resetMouseOverShape();
	  }

	  public void setSize(float width, float height) {
		  super.setSize(width, height);
		  this.radius_base = new PVector(width/2, height/2);
		  this.radius_active = new PVector(width/2, height/2);
		  this.resetMouseOverShape();
	  }

	  public void setSizeActive(float width, float height) {
		  super.setSizeActive(width, height);
		  this.radius_active = new PVector(width/2, height/2);
		  this.resetMouseOverShape();
	  }

	  public void resetSize() {
		  this.setSize(this.size_base.x, this.size_base.y);
	  }

	  public void setRadius(float radius_x, float radius_y) {
	      this.radius_base = new PVector(radius_x, radius_y);
		  this.radius_active = new PVector(radius_base.x, radius_base.y);
	      this.size_base = new PVector(radius_base.x*2, radius_base.y*2);
		  this.size_active = new PVector(size_base.x, size_base.y);
		  this.resetMouseOverShape();
	  }

	  public void setRadiusActive(float radius_x, float radius_y) {
	      this.radius_active = new PVector(radius_x, radius_y);
	      this.size_active = new PVector(radius_active.x*2, radius_active.y*2);
		  this.resetMouseOverShape();
  }

	  public void resetRadius() {
		  this.setRadiusActive(this.radius_base.x, this.radius_base.y);
	  }

	  public void rotate(float angle_in_degrees) {
		  super.rotate(angle_in_degrees);
		  this.angle_start = PApplet.radians(this.rotation_active);
	      this.mouse_over_shape.setAngleStart(mouse_over_shape.getAngleStart() - angle_in_degrees);
	  }

	  public void setRotation(float angle_in_degrees) {
		  super.setRotation(angle_in_degrees);
		  this.angle_start = PApplet.radians(this.rotation_active - 90);
	      this.mouse_over_shape.setAngleStart(mouse_over_shape.getAngleStart() - angle_in_degrees);
	  }
	  
	  public void setRotationActive(float angle_in_degrees) {
		  super.setRotationActive(angle_in_degrees);
		  this.angle_start = PApplet.radians(this.rotation_active - 90);
	      this.mouse_over_shape.setAngleStart(mouse_over_shape.getAngleStart() - angle_in_degrees);
	  }
	  
	  public void resetRotation() {
		  float reset_rotation = this.rotation_active - this.rotation_base;
		  super.resetRotation();
		  this.angle_start = PApplet.radians(this.rotation_active - 90);
	      this.mouse_over_shape.setAngleStart(mouse_over_shape.getAngleStart() - reset_rotation);
	  }
	  

	  /*********************************************************
	   ** SCALE SHIFT METHODS 
	   **/
	  
	  public void shiftScale(float shift_scale) {		  
		  this.setRadiusActive((float)(this.radius_base.x * (this.scale+shift_scale)), (float)(this.radius_base.y * (this.scale+shift_scale)));
	  }

	  public void shiftScaleReset() {
		  this.setRadiusActive((float) (this.radius_base.x * this.scale), (float) (this.radius_base.y * this.scale));
	  }

	  public void shiftScaleResetToBase() {
		  this.scale = 1;
		  this.shiftScaleReset();
	  }

	  /*********************************************************
	   ** MOUSE OVER METHODS 
	   **/

	  public void mouseOver() {
		  boolean is_mouse_over = this.mouse_over_shape.contains(processing_app.mouseX, processing_app.mouseY);
	      if ((this.mouse_over != is_mouse_over)) {
	            if (is_mouse_over) { mouseOverActions(); } 
	            else { mouseOffActions(); }
	      }        
	  }

	  
}
