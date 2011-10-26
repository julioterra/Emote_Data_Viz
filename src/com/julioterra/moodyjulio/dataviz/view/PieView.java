package com.julioterra.moodyjulio.dataviz.view;

import processing.core.PApplet;
import processing.core.PVector;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.datahandlers.DataProcessorHeartrate;
import com.julioterra.moodyjulio.dataviz.datahandlers.EmotionDataProcessor;
import com.julioterra.moodyjulio.dataviz.datahandlers.PieCreatorEmotion;
import com.julioterra.moodyjulio.dataviz.datahandlers.PieCreatorHeartrate;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.panel.Panel;

public class PieView extends DataVizElement {

	PVector location_pie_day;
	PVector location_pie_night;
	PVector location_emotion_pie_title;
	PVector location_emotion_pie_description_rel;
	PVector location_emotion_slices_title;
	PVector location_emotion_slices_description_rel;
	PVector location_emotion_pie_legend;
	PVector location_heartrate_pie_title;
	PVector location_heartrate_pie_description_rel;
	PVector location_heartrate_slices_title;
	PVector location_heartrate_slices_description_rel;
	PVector location_heartrate_pie_legend;
	PVector location_nav_average;
	PVector location_nav_next_day;
	PVector location_nav_previous_day;

	float diameter_large_pie_day;
	float diameter_small_pie_day;
	float number_of_slices_small_pie_day;
	public PieCreatorEmotion large_pie_day;	
	public PieCreatorHeartrate small_pie_day;

	float diameter_large_pie_night;
	float diameter_small_pie_night;
	float number_of_slices_small_pie_night;
	public PieCreatorEmotion large_pie_night;	
	public PieCreatorHeartrate small_pie_night;

	EmotionDataProcessor large_pie_data_processor_day;
	DataProcessorHeartrate small_pie_data_processor_day;
	EmotionDataProcessor large_pie_data_processor_night;
	DataProcessorHeartrate small_pie_data_processor_night;

	public PieView() {
		super();

		// pie locations locations
		this.location_pie_day = new PVector (325, 265);
		this.location_pie_night = new PVector (875, 265);
		
		// pie title and description locations
		this.location_emotion_pie_title = new PVector (50, 600);
		this.location_emotion_pie_description_rel = new PVector (0, -25);
		this.location_heartrate_pie_title = new PVector(0, 0);										// the heart rate pie title does not exist
		this.location_heartrate_pie_description_rel = new PVector(0,0);								// the heart rate pie description does not exist

		// slice title and description locations
		this.location_emotion_slices_title = new PVector (location_emotion_pie_title.x+525, location_emotion_pie_title.y-25);
		this.location_emotion_slices_description_rel = new PVector (0, 25);
		this.location_heartrate_slices_title = new PVector(400, location_emotion_slices_title.y);	
		this.location_heartrate_slices_description_rel = new PVector(0,25);

		// heart rate locations
		this.location_heartrate_pie_legend = new PVector (location_emotion_pie_title.x,650);
		this.location_emotion_pie_legend = new PVector (location_heartrate_pie_legend.x,location_heartrate_pie_legend.y+20);
		
		// navigation buttons pie view menu location
		this.location_nav_average = new PVector(processing_app.width-50, 560);
		this.location_nav_next_day = new PVector(processing_app.width-50, 500);
		this.location_nav_previous_day = new PVector(50, location_nav_next_day.y);
			
		this.diameter_large_pie_day = 400;
		this.diameter_small_pie_day = this.diameter_large_pie_day/2;
		this.number_of_slices_small_pie_day = 360/2;

		this.diameter_large_pie_night = 400;
		this.diameter_small_pie_night = this.diameter_large_pie_night/2;
		this.number_of_slices_small_pie_night = 360/2;

}
	
	public void createEmotionPie() {
		int legend_color = DataVizElement.Black;


		/************************************
		 ** PIE SETTINGS
		 **/	
		// DAY TIME PIES
		large_pie_day = new PieCreatorEmotion(location_pie_day.x, location_pie_day.y, (int)diameter_large_pie_day, "pie of my daily emotions");
		large_pie_day.setLocationNameDescriptionRel((int)location_emotion_pie_title.x, (int)location_emotion_pie_title.y, (int)location_emotion_pie_description_rel.x, (int)location_emotion_pie_description_rel.y);
		large_pie_day.setLocationNameDescriptionRelSlice((int)location_emotion_slices_title.x, (int)location_emotion_slices_title.y, (int)location_emotion_slices_description_rel.x, (int)location_emotion_slices_description_rel.y);

		small_pie_day = new PieCreatorHeartrate(location_pie_day.x, location_pie_day.y, (int) (this.diameter_small_pie_day), (int) this.number_of_slices_small_pie_day, "");
		small_pie_day.setLocationNameDescriptionRel((int)location_heartrate_pie_title.x, (int)location_heartrate_pie_title.y, (int)location_heartrate_pie_description_rel.x, (int)location_heartrate_pie_description_rel.y);
		small_pie_day.setLocationNameDescriptionRelSlice((int)location_heartrate_slices_title.x, (int)location_heartrate_slices_title.y, (int)location_heartrate_slices_description_rel.x, (int)location_heartrate_slices_description_rel.y);

		// NIGHT TIME PIES
		large_pie_night = new PieCreatorEmotion(location_pie_night.x, location_pie_night.y, (int)diameter_large_pie_night, "pie of my daily emotions");
		large_pie_night.setLocationNameDescriptionRel((int)location_emotion_pie_title.x, (int)location_emotion_pie_title.y, (int)location_emotion_pie_description_rel.x, (int)location_emotion_pie_description_rel.y);
		large_pie_night.setLocationNameDescriptionRelSlice((int)location_emotion_slices_title.x, (int)location_emotion_slices_title.y, (int)location_emotion_slices_description_rel.x, (int)location_emotion_slices_description_rel.y);

		small_pie_night = new PieCreatorHeartrate(location_pie_night.x, location_pie_night.y, (int) (this.diameter_small_pie_night), (int) this.number_of_slices_small_pie_night, "");
		small_pie_night.setLocationNameDescriptionRel((int)location_heartrate_pie_title.x, (int)location_heartrate_pie_title.y, (int)location_heartrate_pie_description_rel.x, (int)location_heartrate_pie_description_rel.y);
		small_pie_night.setLocationNameDescriptionRelSlice((int)location_heartrate_slices_title.x, (int)location_heartrate_slices_title.y, (int)location_heartrate_slices_description_rel.x, (int)location_heartrate_slices_description_rel.y);

		// LOAD DATA INTO PIES
		large_pie_day.loadViz_DateTimeRange(new Date(DataVizElement.AllWeeks[0]), new Time("00:00:00"), new Date( DataVizElement.AllWeeks[0]), new Time("12:00:00"));
		small_pie_day.loadViz_DateTimeRange(new Date(DataVizElement.AllWeeks[0]), new Time("00:00:00"), new Date( DataVizElement.AllWeeks[0]), new Time("12:00:00"));
		large_pie_night.loadViz_DateTimeRange(new Date(DataVizElement.AllWeeks[0]), new Time("12:00:00"), new Date( DataVizElement.AllWeeks[0]), new Time("24:00:00"));
		small_pie_night.loadViz_DateTimeRange(new Date(DataVizElement.AllWeeks[0]), new Time("12:00:00"), new Date( DataVizElement.AllWeeks[0]), new Time("24:00:00"));

		
		/************************************
		 ** PANEL SETTINGS
		 **/	
		// DAY TIME PIES - HOURS
		large_pie_day.panel.addElement_TextButton("pie_3", (int)(location_pie_day.x + ((diameter_large_pie_day/2)+27)), (int)(location_pie_day.y-5), "3 ", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_bar_legend, 14, PApplet.RIGHT);
		large_pie_day.panel.addElement_TextButton("pie_6", (int)(location_pie_day.x), (int)(location_pie_day.y + (diameter_large_pie_day/2) + 3), "6", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_bar_legend, 14, PApplet.CENTER);
		large_pie_day.panel.addElement_TextButton("pie_9", (int)(location_pie_day.x - ((diameter_large_pie_day/2)+27)), (int)(location_pie_day.y-5), " 9", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_bar_legend, 14, PApplet.LEFT);
		large_pie_day.panel.addElement_TextButton("pie_12", (int)(location_pie_day.x), (int)(location_pie_day.y - (diameter_large_pie_day/2) - 15), "12", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_bar_legend, 14, PApplet.CENTER);
		large_pie_day.panel.addElement_TextRect("pie_title", (int)(location_pie_day.x - ((diameter_large_pie_day/2)+10)), (int)(location_pie_day.y - (diameter_large_pie_day/2) - 15), 
															  100, 20, "am\nhours", DataVizElement.Transparent_Color, ShapeColor.colorARGB(255, 50, 50, 50), 
															  DataVizElement.font_main_bar_legend, 18, PApplet.LEFT, 0);

		// NIGHT TIME PIES - HOURS
		large_pie_night.panel.addElement_TextButton("pie_3", (int)(location_pie_night.x + ((diameter_large_pie_night/2)+27)), (int)(location_pie_night.y-5), "3 ", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_bar_legend, 14, PApplet.RIGHT);
		large_pie_night.panel.addElement_TextButton("pie_6", (int)(location_pie_night.x), (int)(location_pie_night.y + (diameter_large_pie_night/2) + 3), "6", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_bar_legend, 14, PApplet.CENTER);
		large_pie_night.panel.addElement_TextButton("pie_9", (int)(location_pie_night.x - ((diameter_large_pie_night/2)+27)), (int)(location_pie_night.y-5), " 9", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_bar_legend, 14, PApplet.LEFT);
		large_pie_night.panel.addElement_TextButton("pie_12", (int)(location_pie_night.x), (int)(location_pie_night.y - (diameter_large_pie_night/2) - 15), "12", ShapeColor.colorARGB(255, 0, 0, 0), DataVizElement.font_main_bar_legend, 14, PApplet.CENTER);
		large_pie_night.panel.addElement_TextRect("pie_title", (int)(location_pie_night.x - ((diameter_large_pie_night/2)+10)), 	(int)(location_pie_night.y - (diameter_large_pie_night/2) - 15), 
																  100, 20, "pm\nhours", DataVizElement.Transparent_Color, ShapeColor.colorARGB(255, 50, 50, 50), 
																  DataVizElement.font_main_bar_legend, 18, PApplet.LEFT, 0);
		
		// TEXT BACKGROUND
		large_pie_day.panel.addElement_TextRect("background", (int)0, (int)535, processing_app.width, 500, "", 
								  ShapeColor.colorARGB(255, 235, 245, 255), DataVizElement.Transparent_Color, 
								  DataVizElement.font_main_text, 14, PApplet.LEFT,  0);

		// NAV BUTTONS - Day of Week Averages Buttons
		for (int i = 0; i < DataVizElement.NamesOfDays.length; i++) {
			String current_id = "nav_"+DataVizElement.NamesOfDays[i];
			large_pie_day.panel.addElement_TextButton(current_id,(int)(location_nav_average.x), (int)(location_nav_average.y+(20*i)), DataVizElement.NamesOfDays[i], 
										legend_color, DataVizElement.font_main_bar_legend, 14, PApplet.RIGHT);		
			large_pie_day.panel.setElementShiftMouseOver(current_id, 0, 0, 0, 0.2f, false, false);
		}

		// NAV BUTTONS - Next & Previous Day Buttons
		large_pie_day.panel.addElement_TextButton("nav_next_day", (int)(location_nav_next_day.x), (int)(location_nav_next_day.y), "next day >", 
				legend_color, DataVizElement.font_main_bar_legend, 14, PApplet.RIGHT);		
		large_pie_day.panel.setElementShiftMouseOver("nav_next_day", 0, 0, 0, 0.1f, false, false);
		large_pie_day.panel.addElement_TextButton("nav_previous_day", (int)(location_nav_previous_day.x), (int)(location_nav_previous_day.y), "< previous day", 
				legend_color, DataVizElement.font_main_bar_legend, 14, PApplet.LEFT);		
		large_pie_day.panel.setElementShiftMouseOver("nav_previous_day", 0, 0, 0, 0.1f, false, false);
		
		// LEGEND INFORMATION WITH ROLLOVER 
		// how can I feed in information from the running application into these precreated markers?
		large_pie_night.panel.addElement_TextRectButton("leg_pos_box", (int)location_emotion_pie_legend.x, (int)location_emotion_pie_legend.y, 30, 20, "", 
									 (int)location_heartrate_slices_title.x, (int)location_heartrate_slices_title.y,Emotion_Legend_Rollover[emotion_positive], 
									 Emotion_Colors[emotion_positive], legend_color, DataVizElement.font_main_bar_legend, 14, PApplet.LEFT, 0);
		large_pie_night.panel.setElementShiftMouseOver("leg_pos_box", 0, 0, 0, 0, false, true);
		large_pie_night.panel.addElement_TextRect("leg_pos_text", (int)location_emotion_pie_legend.x+35, (int)location_emotion_pie_legend.y, 100, 20,Emotion_Legend[emotion_positive], 
									 DataVizElement.Transparent_Color, legend_color, DataVizElement.font_main_bar_legend, 14, PApplet.LEFT, 0);

		large_pie_night.panel.addElement_TextRectButton("leg_neg_box", (int)location_emotion_pie_legend.x, (int)location_emotion_pie_legend.y+20, 30, 20, "", 
				 					 (int)location_heartrate_slices_title.x, (int)location_heartrate_slices_title.y, Emotion_Legend_Rollover[emotion_negative], 
									 Emotion_Colors[emotion_negative], legend_color, DataVizElement.font_main_bar_legend, 14, PApplet.LEFT, 0);
		large_pie_night.panel.setElementShiftMouseOver("leg_neg_box", 0, 0, 0, 0, false, true);
		large_pie_night.panel.addElement_TextRect("leg_neg_text", (int)location_emotion_pie_legend.x+35, (int)location_emotion_pie_legend.y+20, 100, 20, Emotion_Legend[emotion_negative], 
									 DataVizElement.Transparent_Color, legend_color, DataVizElement.font_main_bar_legend, 14, PApplet.LEFT, 0);

		small_pie_night.panel.addElement_TextRectButton("leg_heartrate_box", (int)location_heartrate_pie_legend.x, (int)location_heartrate_pie_legend.y, 30, 20, "", 
				 					 (int)location_heartrate_slices_title.x, (int)location_heartrate_slices_title.y,Heartrate_Legend_Rollover, 
									 Heartrate_Color, legend_color, DataVizElement.font_main_bar_legend, 14, PApplet.LEFT, 0);
		small_pie_night.panel.setElementShiftMouseOver("leg_heartrate_box", 0, 0, 0, 0, false, true);
		small_pie_night.panel.addElement_TextRect("leg_heartrate_text", (int)location_heartrate_pie_legend.x+35, 	(int)location_heartrate_pie_legend.y, 100, 20, Heartrate_Legend, 
									 DataVizElement.Transparent_Color, legend_color, DataVizElement.font_main_bar_legend, 14, PApplet.LEFT, 0);

	}
	
	public void display() {
	    large_pie_day.display();
	    small_pie_day.display();
	    large_pie_night.display();
	    small_pie_night.display();
	}

	public void scrollDaysUp() {
		scrollDays(1);
	}

	public void scrollDaysDown() {
		scrollDays(-1);
	}

	public void scrollDays(int days) {
		this.large_pie_day.scrollDays(days);
		this.small_pie_day.scrollDays(days);
		this.large_pie_night.scrollDays(days);
		this.small_pie_night.scrollDays(days);
	}

	public void loadDate(Date date) {
		this.large_pie_night.loadDate(date);
		this.small_pie_night.loadViz_Date(date);
		this.large_pie_day.loadDate(date);
		this.small_pie_day.loadViz_Date(date);
	}

	public void loadDayOfWeek(int date) {
		this.large_pie_night.loadDayOfWeek(date);
		this.small_pie_night.loadViz_DayOfWeekAvg(date);
		this.large_pie_day.loadDayOfWeek(date);
		this.small_pie_day.loadViz_DayOfWeekAvg(date);
	}
}
