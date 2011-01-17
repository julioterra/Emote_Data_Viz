package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PApplet;
import processing.core.PVector;

public class PieSlice extends Shape{

		  float radius;
		  float diameter;
		  float size_in_percent;
		  float angle_start;
		  float angle_slice;
		  float angle_end;

		  // the slice_mouse_over variable holds a Arc2D object used to determine if mouse is hovering
		  // the slice that is displayed on screen is created using the PApplet arc() method.
		  java.awt.geom.Arc2D mouse_over_shape;
		  PVector mouse_over_location;
		  float mouse_over_angle_start;
		  float mouse_over_angle_slice;

		  
		  /*********************************************************
		   ** CONSTRUCTOR METHODS 
		   **/

		  public PieSlice(int x_loc, int y_loc, float diameter, float percent_size, float angle_start, int color) {
			  super(x_loc, y_loc, color);
		      this.diameter = diameter;
		      this.radius = this.diameter / 2;
		      this.size_in_percent = percent_size;
		      this.angle_start = PApplet.radians(angle_start);
		      this.angle_slice = PApplet.radians(this.size_in_percent * 360);
		      this.angle_end = this.angle_start + this.angle_slice;

		      this.mouse_over = false;
		      this.mouse_over_angle_slice = PApplet.degrees(this.angle_slice);
		      this.mouse_over_angle_start = - PApplet.degrees(this.angle_start) - mouse_over_angle_slice;
		      this.mouse_over_location = new PVector (location.x - radius, location.y - radius);   
		      this.mouse_over_shape = new java.awt.geom.Arc2D.Float(this.mouse_over_location.x, this.mouse_over_location.y, 
		                                                       this.diameter, this.diameter, 
		                                                       this.mouse_over_angle_start, this.mouse_over_angle_slice, 
		                                                       java.awt.geom.Arc2D.PIE);

		      PApplet.println("set-up arc: x " + x_loc + " y " + y_loc + " diameter " + diameter + " angle_start " +  angle_start + " angle_slice " +  PApplet.degrees(angle_slice) + " color " +  this.color + " percent " +  percent_size);      
		      PApplet.println("set-up mouse: x " + mouse_over_location.x + " y " + mouse_over_location.y + " radius " + radius + " angle_start " +  mouse_over_angle_start + " angle_slice " +  mouse_over_angle_slice + " color " +  color);
		  }
		  
		  /*********************************************************
		   ** DISPLAY METHODS 
		   **/

		  public void display() {
			  super.display();
		      processing_app.arc(location.x, location.y, diameter, diameter, angle_start, angle_end);
		  }
		  
		  public void turn(float new_start_angle) {
		      this.angle_start = this.angle_start + PApplet.radians(new_start_angle);
		      this.angle_end = this.angle_start + this.angle_slice;
		      mouse_over_shape.setAngleStart(mouse_over_shape.getAngleStart() - new_start_angle);
		  }

		  public void move(float x, float y) {
		      this.location = new PVector(x, y);
		      mouse_over_shape.setArcByCenter((double)x, (double)y, mouse_over_shape.getWidth()/2, mouse_over_shape.getAngleStart(), mouse_over_shape.getAngleExtent(), java.awt.geom.Arc2D.PIE);
		  }

		  /*********************************************************
		   ** MOUSE OVER METHODS 
		   **/

		  public void mouseOver() {
		      boolean is_mouse_over =  contains(processing_app.mouseX, processing_app.mouseY);
		      if (mouse_over != is_mouse_over) {
		            if (is_mouse_over) {
		                this.color(255, 255, (int)(PApplet.degrees(angle_slice) * 3), (int)(PApplet.degrees(angle_slice) * 3));
		                this.diameter = (float) (this.diameter * 1.10);
		            } else {
		                this.color(255, (int)(PApplet.degrees(angle_slice) * 3.0), (int)(PApplet.degrees(angle_slice) * 3.0), 255);
		                this.diameter = (float) (this.diameter / 1.10);
		            }
		            mouse_over = is_mouse_over;
		      }        
		  }
		  
		  protected boolean contains(float x, float y) {
		      return mouse_over_shape.contains(x, y);
		  }

		
	
}
