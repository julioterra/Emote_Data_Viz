package com.julioterra.moodyjulio.dataviz.application;

import com.julioterra.moodyjulio.dataviz.basicelements.*;
import com.julioterra.moodyjulio.dataviz.datahandlers.DataProcessor;
import com.julioterra.moodyjulio.dataviz.datahandlers.EmotionPieCreator;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.pie.*;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class MoodyJulioDataViz extends PApplet {

	Pie_Arc pie_emotions;
	Pie_Line pie_heart;
	EmotionPieCreator data_processor;
	
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
	                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
	                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	public void setup() {
		DataVizElement.application_init(this);
		DataVizElement.loadFonts("font_48");
		size(900, 700);
		background(100);
		float radius = (float) (min(width, height) * 0.75) /2;

		data_processor = new EmotionPieCreator(width/2-100, height/2, (int) (height*0.75));
    	data_processor.loadPie(new Date(2010, 11, 24), new Time(0, 0, 0), new Date(2010, 11, 24), new Time(23, 59, 59));

		pie_emotions = new Pie_Arc(width/2-100, height/2, radius*2, PieElement.PIE_ARC_SET_RADIUS);
		pie_emotions.setName("Emotions");
		for (int i = 0; i < angs.length; i++){
			int cur_color = ShapeColor.colorARGB(255, (int)(angs[i] * 3), (int)(angs[i] * 3), 255);
			pie_emotions.addSlice(("pie slice #" + i), ("description - number " + angs[i]), angs[i], cur_color);
			pie_emotions.setSliceValue(i, angs[i]);
			pie_emotions.setColorSlice(i, cur_color);
		}
		pie_emotions.setShiftMouseOverAll((float) 0.15, (float) -0.15, (float) 0.15, (float) 0.1, true);
		pie_emotions.textLocationNameDescription((float)(width*0.85)-100, (float)(height*0.5), 0, 1200);
		pie_emotions.textLocationNameDescriptionSlices((float)(width*0.85)-100, (float)(height*0.5+30), 0, 20);
		pie_emotions.loadFontAll(1, 20);
		pie_emotions.applyValuesToSliceDisplay();

		
		// load the emotion chart
		pie_heart = new Pie_Line(width/2-100, height/2, radius, height_heart.length, 10);
		pie_heart.setName("Heart Rate");
		int cur_color = ShapeColor.colorARGB(255, 255, (int)(360.0/60 * 3), (int)(360.0/60 * 3));
		for (int i = 0; i < height_heart.length; i++){
			pie_heart.setSliceValue(i, (float) (height_heart[i]));
			pie_heart.setColorSlice(i, cur_color);
			pie_heart.textSetNameSlice(i, ("read " + i + " rate " + height_heart[i]));
			pie_heart.textSetDescriptionSlice(i, ("description " + height_heart[i]));
		}
		pie_heart.setShiftMouseOverAll((float) 0.0, (float) -0.25, (float) 0.0, (float) 0.0, true);
		pie_heart.textLocationNameDescription((float)(width*0.5 + 60), (float)(height*0.5), 0, 1200);
		pie_heart.textLocationNameDescriptionSlices((float)(width*0.5)+60, (float)(height*0.5+30), 0, 16);
		pie_heart.scale((float) 0.7);
		pie_heart.loadFontAll(3, 15);
		pie_heart.applyValuesToSliceDisplay();

	}                        

	public void draw() {
	    smooth();
	    background(DataVizElement.Cur_Background_Color);

//	    pie_emotions.display();
//	    pie_heart.display();
	    data_processor.pie.display();
	    
	}


	public void keyPressed() {
	    if (key == '1') {
	    	pie_emotions.move(pie_emotions.location.x + 50, pie_emotions.location.y);
	    	pie_heart.move(pie_heart.location.x + 50, pie_heart.location.y);
	    } 
	    else if (key == '2') {
	    	pie_emotions.move(pie_emotions.location.x - 50, pie_emotions.location.y);
	    	pie_heart.move(pie_heart.location.x - 50, pie_heart.location.y);
	    } 
	    else if (key == '-') {
//	    	pie_heart.scale((float) 0.75);
//	    	pie_emotions.scale((float) 0.75);
	    	data_processor.scrollDays(-1);
	    }
	    else if (key == '+') {
//	    	pie_heart.scale((float) 1.25);
//	    	pie_emotions.scale((float) 1.25);
	    	data_processor.scrollDays(1);
	    }
	    else if (key == 'z') {
	    	data_processor.createProcessedDL_wTimeRange(new Date(2010, 11, 17), new Date(2010, 12, 9), new Time(0, 0, 0), new Time(23, 59, 59), (float) 1);
	    	data_processor.updateProcessedDL_wRawData();
	    }
	    else if (key == 'x') {
//	    	data_processor.updateProcessedDL_wRawData();
//	    	pie_heart.scaleShiftReset();
//	    	pie_emotions.scaleShiftReset();
	    }
	    else if (key == 'r') {
	    	data_processor.loadPie(new Date(2010, 11, 24), new Time(0, 0, 0), new Date(2010, 11, 24), new Time(23, 59, 59));
//	    	data_processor.load_time_range(new Time(12, 0, 0), new Time(15, 59, 59));
//	    	data_processor.load_date_range(DataVizElement.JOURNAL, new Date(2010, 11, 01), new Date(2010, 12, 20));
	    }
	    else if (key == 'l') {
	    	data_processor.load_2_database();
	    }
	    else if (key == 'L') {
	    }
	    else if (key == 'p') {
	    	data_processor.print();
	    }
	    else {
	    	pie_emotions.turn(-40);
	    	pie_heart.turn(-40);
	    }
	}
	
}
