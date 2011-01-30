package com.julioterra.moodyjulio.dataviz.basicelements;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import de.bezier.data.sql.MySQL;
import processing.core.PApplet;
import processing.core.PFont;

public class DataVizElement {

	public static PApplet 			processing_app;
	public static boolean 			processing_app_linked = false;	

	public static MySQL 			database;

	public static final String		font_prefix = "font";
	public static final PFont [] 	fonts = new PFont[9];
	public static final int [] 		fonts_size = {27, 20, 27, 24, 18, 24, 24, 48, 14};
	public static boolean 			font_loaded = false;
	public static final int			font_nav_title = 		0;
	public static final int			font_nav_small = 		1;
	public static final int			font_nav_large = 		2;
	public static final int			font_main_header = 		3;
	public static final int			font_main_subheader = 	4;
	public static final int			font_main_body = 		5;
	public static final int			font_main_heart =		6;
	public static final int			font_main_heart_text =	7;
	public static final int			font_main_subnav =		8;
	
	public static boolean 			debug_code = 		true;
	public static boolean			debug_mouse_over = 	false;
	public static boolean 			auto_read = 		true;
	public static boolean 			data_load = 		true;	
	public static boolean 			data_read = 		true;	
	
	// Data Types for Use in Data Processor
	public static final int 		MOBILE = 0;
	public static final int 		JOURNAL = 1;
	public static final int 		EMOTION = 2;
	public static final int 		HEART_RATE = 3;
	public static final int 		ACTIVITY = 4;
	public static final int 		PEOPLE = 5;

	// variable used for converting journal data into emotion data
	public static final int 		EmotionAgeThreshold = 1;
	
	// Data Views for Use in Data Processor
	public static int				data_view = 		0;
	public static final int			EMOTION_PIE = 		2;
	public static final int			ACTIVITY_PIE = 		4;
	public static final int			PEOPLE_PIE = 		5;

	public String					active_data_table	= "JournalData";
	public static final String []	database_name		= {"MobileData", "JournalData", "PieData_Emotion", "PieData_HeartRate", "PieData_Activity"};
	public static final int 		MobileData = 		0;
	public static final int 		JournalData = 		1;
	public static final int 		PieData_Emotion =	2;
	public static final int 		PieData_HeartRate = 3;
	public static final int 		PieData_Activity =	4;
	
	public static int				NavBar_Color =	ShapeColor.colorARGB(255, 178, 178, 178);
	public static int				Base_Background_Color =	ShapeColor.colorARGB(255, 255, 255, 255);
	public static int				Cur_Background_Color =	Base_Background_Color;
	
	public static boolean 			file_active;
	public static boolean 			file_read;
	public static boolean 			file_completed;

	public int 						total_readings;
	public int 						valid_readings;	
	public int 						data_batch;

	public static void application_init (PApplet processing_app) {
		DataVizElement.processing_app = processing_app; 
		DataVizElement.processing_app_linked = true;
		DataVizElement.database_connect();
		DataVizElement.loadFonts(font_prefix);
	}

	public static void loadFonts(String filename_prefix) {
		if (DataVizElement.processing_app_linked) {
			for(int i = 0; i < fonts_size.length; i ++) {
				String filename = filename_prefix + "_" + i +".vlw";
				fonts[i] = processing_app.loadFont(filename);
			}
			DataVizElement.font_loaded = true;
		}
	}
	
	public static void database_connect () {
		DataVizElement.database = new MySQL(processing_app, "localhost:8889", "MoodyJULIO", "root", "root" );
		DataVizElement.database.connect();
	}
				
}
