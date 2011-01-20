package com.julioterra.moodyjulio.dataviz.application;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.pie.*;
import com.julioterra.moodyjulio.dataviz.shapes.pie.PieSliceLine;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class MoodyJulioDataViz extends PApplet {

	PieSliceArc[] slices;
	PieSliceLine[] lines;
	PieSliceLine line;

	Pie_Arc pie_emotions;
	Pie_Line pie_heart;

	int[] angs =           {30, 10, 45, 35, 60, 38, 75, 67};
	int[] height_heart =   {120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130,
	                        120, 130, 125, 135, 140, 145, 150, 135, 130, 110, 120, 130};
//	                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//	                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//	                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	public void setup() {
		DataVizElement.application_init(this);
		size(900, 700);
		background(100);
		float radius = (float) (min(width, height) * 0.75) /2;

		lines = new PieSliceLine[height_heart.length];

		pie_emotions = new Pie_Arc(width/2, height/2, radius*2);
		pie_emotions.setNumberOfSlices(angs.length);
		pie_emotions.setColorSizeShiftAll((float) 0.15, (float) -0.15, (float) 0.15, (float) 0.1);
		
	    // load the pie chart
		float lastAng = (float) 0.0;
		for (int i = 0; i < pie_emotions.getNumberOfSlices(); i++){
			int cur_color = ShapeColor.colorARGB(255, (int)(angs[i] * 3), (int)(angs[i] * 3), 255);
			pie_emotions.setSliceValue(i, angs[i]);
			pie_emotions.setColorSlice(i, cur_color);
		}
		pie_emotions.calculateSliceExtentFromValues();

		
		pie_heart = new Pie_Line(width/2, height/2, radius*2, height_heart.length);

	    // load the heart rate lines
		lastAng = (float) 0.0;
		pie_heart.setColorSizeShiftAll((float) 0.0, (float) -0.25, (float) 0.0, (float) 0.0);
		pie_heart.setWidthAll(7);
		int color_line = ShapeColor.colorARGB(255, 255, (int)(360.0/60 * 3), (int)(360.0/60 * 3));
		pie_heart.setColorAll(color_line);
		for (int i = 0; i < height_heart.length; i++){
			pie_heart.setSliceValue(i, (float) (height_heart[i]));
//			pie_heart.setColorSlice(i, color_line);
		}
		pie_heart.calculateSliceRadiusFromValues();

	    // load the heart rate lines
//		lastAng = (float) 0.0;
//		for (int i = 0; i < lines.length; i++){
//			int cur_color_line = ShapeColor.colorARGB(255, 255, (int)(360.0/60 * 3), (int)(360.0/60 * 3));
//			lines[i] = new PieSliceLine(width/2, height/2, (float) (height_heart[i]*0.8), (float) ((360/lines.length)/360.0), lastAng, cur_color_line);
//			lines[i].setWidth(7);
//			lastAng += 360.0/lines.length;  
//		}

	}                        

	public void draw() {
	    smooth();
	    background(100);

	    pie_emotions.display();
	    pie_heart.display();
	    
	    // draw the heart rate lines
//	    for (int i = 0; i < lines.length; i++){
//	        lines[i].mouseOver();
//	        lines[i].display();
//	    }
	}


	public void keyPressed() {
	    if (key == 'r') {
	    	pie_emotions.move(pie_emotions.location.x + 50, pie_emotions.location.y);
//	       for (int i = 0; i < lines.length; i++) lines[i].move(lines[i].location.x + 50, lines[i].location.y);
	    } 
	    else if (key == 'l') {
	    	pie_emotions.move(pie_emotions.location.x - 50, pie_emotions.location.y);
//	       for (int i = 0; i < lines.length; i++) lines[i].move(lines[i].location.x - 50, lines[i].location.y);
	    } 
	    else {
	    	pie_emotions.turn(-40);
//	      for (int i = 0; i < lines.length; i++) lines[i].turn(-40);
	    }
	}
	
}
