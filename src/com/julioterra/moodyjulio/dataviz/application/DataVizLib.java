package com.julioterra.moodyjulio.dataviz.application;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import processing.core.*;
import com.julioterra.moodyjulio.dataviz.basicelements.*;
import com.julioterra.moodyjulio.dataviz.datahandlers.*;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRectText;
import com.julioterra.moodyjulio.dataviz.shapes.TextItem;
import com.julioterra.moodyjulio.dataviz.shapes.bar.Bar;
import com.julioterra.moodyjulio.dataviz.shapes.bar.BarSlice;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ButtonDropDown;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ShapeText;
import com.julioterra.moodyjulio.dataviz.shapes.panel.Panel;
import com.julioterra.moodyjulio.dataviz.view.PieView;
import com.julioterra.moodyjulio.dataviz.view.View;

import processing.core.*;

//import processing.core.PApplet;

@SuppressWarnings("serial")
public class DataVizLib extends PApplet {

	PGraphics buffer;

	PieView emotion_view;
	Bar bar_view_old;
	Panel main_nav;
	BarCreator bar_creator;	
	
	public HashMap<String, View> views;
		
	int update_count = 0;
	boolean process_data = false;
	int current_view = 0;
	
	public void setup() {
		size(1200, 750);
		buffer = createGraphics(width, height, P3D);
		DataVizElement.application_init(this, buffer);
		background(100);

		bar_creator = new BarCreator(50, 75, 30, 600, 2);
    	bar_creator.load(new Date("2010/11/17"), new Date("2010/12/04"));

    	// writing text above the bars
    	for (int i = 0; i < DataVizElement.AllWeeks.length; i++) {
        	bar_creator.panel.addElement_TextRect("date_"+DataVizElement.AllWeeks[i], (int)(40+(32*i)), 50, 50, 25, DataVizElement.AllWeeks[i].substring(8), DataVizElement.Transparent_Color, DataVizElement.Black, DataVizElement.font_main_bar_legend, 10, CENTER, 0);
    		if(DataVizElement.AllWeeks[i].substring(8).contains("01") || i == 0) {
    	    	bar_creator.panel.addElement_TextRect("month_"+Date.getMonthInString(new Date(DataVizElement.AllWeeks[i])),(int)(40+(32*i)), 35, 50, 25, Date.getMonthInString(new Date(DataVizElement.AllWeeks[i])), DataVizElement.Transparent_Color, DataVizElement.Black, DataVizElement.font_main_bar_legend, 10, CENTER, 0);
    		}
		}

    	// writing text on the left of the bars
    	for (int i = 0; i <= 24; i+=2) {
    		String date = "" + i;
    		if (i < 10) date = "0" + date;
    		bar_creator.panel.addElement_TextRect("time_"+i, 10, (int)(60 + (600/24*i)), 50, 25, date, DataVizElement.Transparent_Color, DataVizElement.Black, DataVizElement.font_main_bar_legend, 10, CENTER, 0);
    	}
    	
    	
    	float[] activity_times = bar_creator.getActivityTimes();
    	for (int i = 0; i < DataVizElement.Activity_Colors.length; i ++) {
    		String description = DataVizElement.Activity_Names[i] + " activities: \n" + (int)(activity_times[i]/bar_creator.getActivityTotal()*100) + "% of my time";
    		bar_creator.panel.addElement_TextRect("color_"+DataVizElement.Activity_Names[i], 690+(31*i), 630, 150, 30, DataVizElement.Activity_Names[i], DataVizElement.Transparent_Color, DataVizElement.Black, DataVizElement.font_main_bar_legend, 10, LEFT, 270);
    		bar_creator.panel.addElement_TextRectButton("color_chip_"+DataVizElement.Activity_Names[i], 675+(31*i), 660, 30, 15 , "", 675, 175, description, DataVizElement.Activity_Colors[i], DataVizElement.Black, DataVizElement.font_main_header, LEFT, 0);
    		bar_creator.panel.setShiftAllMouseOver("color_chip_"+DataVizElement.Activity_Names[i], 0, 0, 0, 0, true, true);
    	}    	


    	emotion_view = new PieView();
		emotion_view.createEmotionPie();
		
    	main_nav = new Panel(0, 0, width, 20, ShapeColor.colorARGB(255, 178, 178, 178));
    	main_nav.addElement_Text("nav_logo", 50, 3, "MoodyJulio", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_title, PApplet.LEFT);
    	main_nav.addElement_TextButton("nav_me", 300, 7, "ME", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.addElement_TextButton("nav_pie", 400, 7, "PIE", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.addElement_TextButton("nav_bars", 500, 7, "BARS", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.addElement_TextButton("nav_imgs", 600, 7, "IMGS", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.setShiftAllMouseOver(0, 0, 0, 0.2f, false, false);
    	main_nav.addMouseClickedAction("nav_pie", "pie_view", this, "pie_view");
    	main_nav.addMouseClickedAction("nav_bars", "bar_view", this, "bar_view");

	    buffer.beginDraw();
	    buffer.fill(200);
	    buffer.rect(500,100,200,200);
	    buffer.endDraw();
	    PImage img = buffer.get(0, 0, buffer.width, buffer.height);
	    image(img, 50, 50);

	}  

	public void pie_view() {
		current_view = 1;
	}
	
	public void bar_view() {
		current_view = 0;
	}

	public void draw() {
	    smooth();
	    background(DataVizElement.Cur_Background_Color);

	    if (current_view == 0) bar_creator.display();
	    else emotion_view.display();
	    main_nav.display();	    	    
	}
	
	public void keyPressed() {
	    if (key == '-') {
	    	emotion_view.scrollDays(-1);
	    }
	    else if (key == '+') {
	    	emotion_view.scrollDays(1);
	    }
	    else if (key == 'c') {
		    if (current_view == 0) current_view = 1;
		    else current_view = 0;
	    }
	    else if (key == 'l') {
	    	bar_creator.load(new Date("2010/11/17"), new Date("2010/12/04"));
	    }
	    else if (key == '0') {
	    	emotion_view.loadDayOfWeek(0);
	    }
	    else if (key == '1') {
	    	emotion_view.loadDayOfWeek(1);
	    }
	    else if (key == '2') {
	    	emotion_view.loadDayOfWeek(2);
	    }
	    else if (key == '3') {
	    	emotion_view.loadDayOfWeek(3);
	    }
	    else if (key == '4') {
	    	emotion_view.loadDayOfWeek(4);
	    }
	    else if (key == '5') {
	    	emotion_view.loadDayOfWeek(5);
	    }
	    else if (key == '6') {
	    	emotion_view.loadDayOfWeek(6);
	    }
	    else if (key == '7') {
	    	emotion_view.loadDayOfWeek(7);
	    }
	    else if (key == '8') {
	    	main_nav.removeElement("nav_me");
	    }
	}
	
}
