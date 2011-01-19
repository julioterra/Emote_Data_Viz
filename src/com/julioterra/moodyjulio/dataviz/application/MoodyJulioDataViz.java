package com.julioterra.moodyjulio.dataviz.application;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.pie.PieArcSlice;
import com.julioterra.moodyjulio.dataviz.shapes.pie.PieLineSlice;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class MoodyJulioDataViz extends PApplet {

	PieArcSlice[] slices;
	PieLineSlice[] lines;
	PieLineSlice line;

	int[] angs =           {30, 10, 45, 35, 60, 38, 75, 67};
	int[] height_heart =   {120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
	                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	public void setup() {
		DataVizElement.application_init(this);
		size(900, 700);
		background(100);

		slices = new PieArcSlice[angs.length];
		lines = new PieLineSlice[height_heart.length];
	  
	    // load the pie chart
		float radius = (float) (min(width, height) * 0.85) /2;
		float lastAng = (float) 0.0;
		for (int i = 0; i < slices.length; i++){
			int cur_color = ShapeColor.colorARGB(255, (int)(angs[i] * 3), (int)(angs[i] * 3), 255);
			slices[i] = new PieArcSlice(width/2, height/2, radius, (float) (angs[i]/360.0), lastAng, cur_color);
			lastAng += angs[i];  
		}

	    // load the heart rate lines
		lastAng = (float) 0.0;
		for (int i = 0; i < lines.length; i++){
			int cur_color_line = ShapeColor.colorARGB(255, 255, (int)(360.0/60 * 3), (int)(360.0/60 * 3));
			lines[i] = new PieLineSlice(width/2, height/2, height_heart[i], (float) ((360/lines.length)/360.0), lastAng, cur_color_line);
			lines[i].setWidth(7);
			lastAng += 360.0/lines.length;  
		}

	}                        

	public void draw() {
	    smooth();
	    background(100);

	    // draw the pie chart
	    for (int i = 0; i < angs.length; i++){
	        slices[i].mouseOver();
	        slices[i].display();
	    }

	    // draw the heart rate lines
	    for (int i = 0; i < lines.length; i++){
	        lines[i].mouseOver();
	        lines[i].display();
	    }
	}


	public void keyPressed() {
	    if (key == 'r') {
	       for (int i = 0; i < slices.length; i++) slices[i].move(slices[i].location.x + 50, slices[i].location.y);
	       for (int i = 0; i < lines.length; i++) lines[i].move(lines[i].location.x + 50, lines[i].location.y);
	    } 
	    else if (key == 'l') {
	       for (int i = 0; i < slices.length; i++) slices[i].move(slices[i].location.x - 50, slices[i].location.y);
	       for (int i = 0; i < lines.length; i++) lines[i].move(lines[i].location.x - 50, lines[i].location.y);
	    } 
	    else {
	      for (int i = 0; i < slices.length; i++) slices[i].turn(-40);
	      for (int i = 0; i < lines.length; i++) lines[i].turn(-40);
	    }
	}
	
}
