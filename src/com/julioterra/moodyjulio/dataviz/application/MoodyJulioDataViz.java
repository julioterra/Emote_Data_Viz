package com.julioterra.moodyjulio.dataviz.application;

import com.julioterra.moodyjulio.dataviz.basicelements.*;
import com.julioterra.moodyjulio.dataviz.datahandlers.*;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;
import com.julioterra.moodyjulio.dataviz.shapes.panel.Panel;
import com.sun.java.swing.plaf.nimbus.ButtonPainter;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class MoodyJulioDataViz extends PApplet {

	EmotionDataProcessor emotion_data_processor;
	PieCreatorEmotion emotion_pie;

	DataProcessorHeartrate heartrate_data_processor;
	PieCreatorHeartrate heart_pie;
	
	Panel pie_nav;
	Panel main_nav;

	int update_count = 0;
	boolean process_data = false;
	ShapeText[] pie_options = new ShapeText[2];
	
	public void setup() {
		DataVizElement.application_init(this);
		size(1200, 700);
		background(100);
		float diameter = 550;

		emotion_data_processor = new EmotionDataProcessor();
    	heartrate_data_processor = new DataProcessorHeartrate();
    	
    	main_nav = new Panel(0, 0, width, 25, ShapeColor.colorARGB(255, 178, 178, 178));
    	main_nav.addText(50, 8, "MoodyJulio", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_title, PApplet.LEFT);
    	main_nav.addTextButton(300, 12, "ME", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.addTextButton(400, 12, "PIE", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.addTextButton(500, 12, "BARS", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.addTextButton(600, 12, "IMGS", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.setShiftMouseOverAll(0, 0, 0, 0.2f, false);
    	
    	pie_nav = new Panel(width-125, 50, width-25, 200, ShapeColor.colorARGB(0, 178, 178, 178));
    	pie_nav.addTextButton(100, 0, "pie flavors:", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_subnav, PApplet.RIGHT, true, false);
    	pie_nav.addTextButton(100, 38, "pie cuts:", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_subnav, PApplet.RIGHT, true, false);
    	
    	pie_options[0] = new ShapeText(width-25, 50, "pie flavors:", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_subnav, PApplet.RIGHT);
		pie_options[1] = new ShapeText(width-25, 88, "pie cuts:", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_subnav, PApplet.RIGHT);
		
		emotion_pie = new PieCreatorEmotion(350, 375, (int) diameter, "pie of emotions");
		emotion_pie.setLocationNameDescriptionRel(675, 275, 0, -25);
		emotion_pie.setLocationNameDescriptionRelSlice(675, 325, 0, 20);
    	emotion_pie.loadPie(new Date(2010, 11, 19), new Time(0, 0, 0), new Date(2010, 11, 19), new Time(23, 59, 59));
    	emotion_pie.addText(350, 72, "24", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_header, PApplet.CENTER);
    	emotion_pie.addText(350, 660, "12", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_header, PApplet.CENTER);
    	emotion_pie.addText(65, 364, "18", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_header, PApplet.RIGHT);
    	
    	heart_pie = new PieCreatorHeartrate(350, 375, (int) (diameter*0.5), "e");
    	heart_pie.setLocationNameDescriptionRel(675, 500, 0, 20);
    	heart_pie.setLocationNameDescriptionRelSlice(675+30, 505, 20, 0);
    	heart_pie.loadPie(new Date(2010, 11, 19), new Time(0, 0, 0), new Date(2010, 11, 19), new Time(23, 59, 59));    	
	}                        

	public void draw() {
	    smooth();
	    background(DataVizElement.Cur_Background_Color);

	    emotion_pie.mouseOver();
	    heart_pie.mouseOver();
	    main_nav.mouseOver();
	    pie_nav.mouseOver();

	    emotion_pie.display();
	    heart_pie.display();
	    main_nav.display();
	    pie_nav.display();
	    
//	    for (int i = 0; i < pie_options.length; i++) {
//	    	pie_options[i].display();
//	    }
	    
	}


	public void keyPressed() {
	    if (key == '-') {
	    	emotion_pie.scrollDays(-1);
	    	heart_pie.scrollDays(-1);
	    }
	    else if (key == '+') {
	    	emotion_pie.scrollDays(1);
	    	heart_pie.scrollDays(1);
	    }
	    else if (key == 'P') {
	    	emotion_data_processor.print();
	    }
	    else if (key == '1') {
	    }
	    else if (key == '2') {
	    }
	}
	
}
