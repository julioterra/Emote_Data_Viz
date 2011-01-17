package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;

public class CircleLine extends Shape {

	  float radius;
	  float height;
	  float width;
	  float size_in_percent;
	  float angle_start;

	  java.awt.geom.Arc2D mouse_over_shape;
	  PVector mouse_over_location;
	  float mouse_over_angle_start;
	  float mouse_over_angle_slice;


	  /*********************************************************
	   ** CONSTRUCTOR METHODS 
	   **/

	  public CircleLine(int x_loc, int y_loc, float height, float width, float size_in_percent, float angle_start, int color) {
		  super(x_loc, y_loc, color);
	      this.radius = this.height / 2;
	      this.size_in_percent = size_in_percent;
	      this.height = -height;
	      this.width = width;
	      this.angle_start = PApplet.radians(angle_start);

	      this.mouse_over = false;
	      this.mouse_over_angle_slice = this.size_in_percent * 360;
	      this.mouse_over_angle_start = -PApplet.degrees(this.angle_start) - mouse_over_angle_slice;
	      this.mouse_over_location = new PVector (location.x - radius, location.y - radius);   
	      this.mouse_over_shape = new java.awt.geom.Arc2D.Float(this.mouse_over_location.x, this.mouse_over_location.y, 
	                                                       this.height, this.height, 
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
		      processing_app.stroke(color);
		      processing_app.rotate(this.angle_start);
		      processing_app.line(0,0,0,this.height);
		  processing_app.popMatrix();
	  }

	  public void turn(float new_start_angle) {
	      this.angle_start = this.angle_start + PApplet.radians(new_start_angle);
	      mouse_over_shape.setAngleStart(mouse_over_shape.getAngleStart() - new_start_angle);
	  }

	  public void move(float x, float y) {
	      this.location = new PVector(x, y);
	      mouse_over_shape.setArcByCenter((double)x, (double)y, mouse_over_shape.getWidth()/2, mouse_over_shape.getAngleStart(), mouse_over_shape.getAngleExtent(), java.awt.geom.Arc2D.PIE);
	  }

	  
}
