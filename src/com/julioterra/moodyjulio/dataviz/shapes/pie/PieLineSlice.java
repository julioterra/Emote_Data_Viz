package com.julioterra.moodyjulio.dataviz.shapes.pie;

public class PieLineSlice extends PieSlice {	  
	
	  /*********************************************************
	   ** CONSTRUCTOR METHODS 
	   **/

	  public PieLineSlice(int x_loc, int y_loc, float radius, float size_in_percent, float angle_start, int color) {
		  super(x_loc, y_loc, radius, size_in_percent, angle_start, color);
		  this.pie_shape_type = PIE_LINE_SLICE;
		  this.setRadiusShift((float) 0.0);
		  this.setColorShift((float) 0.0, (float) -0.25, (float) 0.0);
	  }

	  /*********************************************************
	   ** COLOR METHODS - defined in Shape parent 
	   ** 
	   ** Functions defined in the parent class Shape: 
	   ** 	(1) set & get functions: enable setting and getting current and base colors
	   ** 	(2) shift set and exec: enables setting, executing and resetting colors shifts
	   **
	   **/

	  /*********************************************************
	   ** SIZE METHODS - defined in PieSlice parent 
	   ** 
	   ** Functions defined in the parent class PieSlice: 
	   ** 	(1) set & get functions: enables setting and getting active and base width and radius 
	   ** 	(2) shift set and exec: enables setting, executing and resetting width and radius shifts
	   **
	   **/

	  /*********************************************************
	   ** DISPLAY METHODS - defined in PieSlice parent
	   ** 
	   ** Functions defined in the parent class include: 
	   ** 	(1) display: displays a pie shape of type pie_slice or pie_slice_line
	   ** 	(2) turn: turns the pie shape on the center axis
	   ** 	(3) move: moves the pie shape on the center axis
	   **
	   **/
	  
	  /*********************************************************
	   ** MOUSE OVER METHOD - defined in PieSlice parent
	   ** 
	   ** Function defined in parent that changes the look of the pie based on 
	   ** hue_shift, bright_shift, sat_shift, and radius_shift
	   **
	   **/

}
