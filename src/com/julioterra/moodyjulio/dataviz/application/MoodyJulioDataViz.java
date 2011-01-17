package com.julioterra.moodyjulio.dataviz.application;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.shapes.CircleLine;
import com.julioterra.moodyjulio.dataviz.shapes.PieSlice;
import com.julioterra.moodyjulio.dataviz.shapes.Shape;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class MoodyJulioDataViz extends PApplet {

	PieSlice[] slices;
	CircleLine[] lines;
	CircleLine line;

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

		slices = new PieSlice[angs.length];
		lines = new CircleLine[height_heart.length];
	  
	    // load the pie chart
		float diameter = (float) (min(width, height) * 0.85);
		float lastAng = (float) 0.0;
		for (int i = 0; i < slices.length; i++){
			int cur_color = Shape.colorARGB(255, (int)(angs[i] * 3), (int)(angs[i] * 3), 255);
			slices[i] = new PieSlice(width/2, height/2, diameter, (float) (angs[i]/360.0), lastAng, cur_color);
			lastAng += angs[i];  
		}

	    // load the heart rate lines
		lastAng = (float) 0.0;
		for (int i = 0; i < lines.length; i++){
			int cur_color_line = Shape.colorARGB(255, (int)(360.0/60 * 3), (int)(360.0/60 * 3), 255);
			lines[i] = new CircleLine(width/2, height/2, height_heart[i], 10, (float) (360.0/lines.length), lastAng, cur_color_line);
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
//	        lines[i].mouseOver();
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
	      for (int i = 0; i < slices.length; i++) slices[i].turn(-30);
	      for (int i = 0; i < lines.length; i++) lines[i].turn(-30);
	    }
	}
	
}
