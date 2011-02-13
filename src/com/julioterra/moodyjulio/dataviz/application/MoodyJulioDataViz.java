package com.julioterra.moodyjulio.dataviz.application;

import com.julioterra.moodyjulio.dataviz.basicelements.*;
import com.julioterra.moodyjulio.dataviz.datahandlers.*;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;
import com.julioterra.moodyjulio.dataviz.shapes.bar.Bar;
import com.julioterra.moodyjulio.dataviz.shapes.bar.BarSlice;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ButtonDropDown;
import com.julioterra.moodyjulio.dataviz.shapes.panel.ButtonText;
import com.julioterra.moodyjulio.dataviz.shapes.panel.Panel;
import com.julioterra.moodyjulio.dataviz.view.PieView;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class MoodyJulioDataViz extends PApplet {

	PieView emotion_view;
	
	Bar bar_view;
	BarCreator bar_creator;
	
	Panel pie_nav;
	Panel main_nav;
		
	int update_count = 0;
	boolean process_data = false;
	ButtonText[] pie_options = new ButtonText[2];
	
	public void setup() {
		DataVizElement.application_init(this);
		size(1200, 750);
		background(100);

		bar_creator = new BarCreator(50, 75, 30, 600, 2);
    	bar_creator.load(new Date("2010/11/17"), new Date("2010/12/04"));

    	// writing text above the bars
    	for (int i = 0; i < DataVizElement.AllWeeks.length; i++) {
        	bar_creator.addTextRect("date_"+DataVizElement.AllWeeks[i], (int)(40+(32*i)), 50, 50, 25, DataVizElement.AllWeeks[i].substring(8), DataVizElement.Transparent_Color, DataVizElement.Black, DataVizElement.font_main_bar_legend, CENTER, 0);
    		bar_creator.setTextSize("date_"+DataVizElement.AllWeeks[i], 10);
    		if(DataVizElement.AllWeeks[i].substring(8).contains("01") || i == 0) {
    	    	bar_creator.addTextRect("month_"+Date.getMonthInString(new Date(DataVizElement.AllWeeks[i])),(int)(40+(32*i)), 35, 50, 25, Date.getMonthInString(new Date(DataVizElement.AllWeeks[i])), DataVizElement.Transparent_Color, DataVizElement.Black, DataVizElement.font_main_bar_legend, CENTER, 0);
    			bar_creator.setTextSize("month_"+Date.getMonthInString(new Date(DataVizElement.AllWeeks[i])), 10);  
    		}
		}

    	// writing text on the left of the bars
    	for (int i = 0; i <= 24; i+=2) {
    		String date = "" + i;
    		if (i < 10) date = "0" + date;
    		bar_creator.addTextRect("time_"+i, 10, (int)(60 + (600/24*i)), 50, 25, date, DataVizElement.Transparent_Color, DataVizElement.Black, DataVizElement.font_main_bar_legend, CENTER, 0);
    		bar_creator.setTextSize("time_"+i, 10);
    	}
    	
    	
    	float[] activity_times = bar_creator.getActivityTimes();
    	for (int i = 0; i < DataVizElement.Activity_Colors.length; i ++) {
    		String description = DataVizElement.Activity_Names[i] + " activities: \n" + (int)(activity_times[i]/bar_creator.getActivityTotal()*100) + "% of my time";
    		bar_creator.addTextRect("color_"+DataVizElement.Activity_Names[i], 690+(31*i), 630, 150, 30, DataVizElement.Activity_Names[i], DataVizElement.Transparent_Color, DataVizElement.Black, DataVizElement.font_main_bar_legend, LEFT, 270);
    		bar_creator.setTextSize("color_"+DataVizElement.Activity_Names[i], 10);
    		bar_creator.addTextRectButton("color_chip_"+DataVizElement.Activity_Names[i], 675+(31*i), 660, 30, 15 , "", 675, 175, description, DataVizElement.Activity_Colors[i], DataVizElement.Transparent_Color, DataVizElement.font_main_header, LEFT, 0);
//    		bar_creator.addText(675+(31*i), 660, 30, 15 + (int)(50*(activity_times[i]/bar_creator.getActivityTotal())), "", 675, 175, description, DataVizElement.Activity_Colors[i], DataVizElement.Transparent_Color, DataVizElement.font_main_header, LEFT, 0);
    		bar_creator.setShiftMouseOverTextSlice(bar_creator.getSizeTextItems()-1, 0, 0, 0, 0, true);
    	}    	


    	emotion_view = new PieView();
		emotion_view.createEmotionPie();
		
    	main_nav = new Panel(0, 0, width, 20, ShapeColor.colorARGB(255, 178, 178, 178));
    	main_nav.addText("nav_logo", 50, 3, "MoodyJulio", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_title, PApplet.LEFT);
    	main_nav.addTextButton("nav_me", 300, 7, "ME", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.addTextButton("nav_pie", 400, 7, "PIE", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.addTextButton("nav_bars", 500, 7, "BARS", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.addTextButton("nav_imgs", 600, 7, "IMGS", ShapeColor.colorARGB(255, 255, 255, 255), DataVizElement.font_nav_small, PApplet.LEFT, true, false);
    	main_nav.setShiftMouseOverAll(0, 0, 0, 0.2f, false);

	}  

	public void draw() {
	    smooth();
	    background(DataVizElement.Cur_Background_Color);

//	    bar_creator.display();
//	    bar_creator.mouseOver();

	    emotion_view.mouseOver();
	    emotion_view.display();

	    main_nav.mouseOver();
	    main_nav.display();
	    
	    
	    
	}


	public void keyPressed() {
	    if (key == '-') {
	    	emotion_view.scrollDays(-1);
	    }
	    else if (key == '+') {
	    	emotion_view.scrollDays(1);
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
	    	DataVizElement.test_hashmap();
	    	main_nav.removeText("nav_me");
	    }
	}
	
}
