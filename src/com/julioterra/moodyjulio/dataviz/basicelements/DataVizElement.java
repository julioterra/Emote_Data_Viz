package com.julioterra.moodyjulio.dataviz.basicelements;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;

import de.bezier.data.sql.MySQL;
import processing.core.PApplet;
import processing.core.PFont;

public class DataVizElement {

	public static PApplet 			processing_app;
	public static boolean 			processing_app_linked = false;	

	public static MySQL 			database;

	public static final PFont [] 	fonts = new PFont[10];
	public static boolean 			font_loaded = false;
	
	// Data Types for Use in Data Processor
	// RAW Data Type
	public static final int 		MOBILE = 0;
	public static final int 		JOURNAL = 1;
	// PROCESSED Data Type
	public static final int 		EMOTION = 2;
	public static final int 		ACTIVITY = 3;
	public static final int 		HEART_RATE = 4;
	public static final int 		PEOPLE = 5;

	public static final int 		EmotionAgeThreshold = 3;

	// Data Views for Use in Data Processor
	public static int				data_view = 		0;
	public static final int			EMOTION_PIE = 		1;
	public static final int			ACTIVITY_PIE = 		2;
	public static final int			PEOPLE_PIE = 		3;

	public static int				Base_Background_Color =	ShapeColor.colorARGB(255, 240, 240, 240);
	public static int				Cur_Background_Color =	Base_Background_Color;
	
	public String					active_data_table	= "JournalData";
	public static final String []	database_name		= {"MobileData", "JournalData", "PieData_Emotion", "PieData_HeartRate", "PieData_Activity"};
	public static final int 		MobileData = 		0;
	public static final int 		JournalData = 		1;
	public static final int 		PieData_Emotion =	2;
	public static final int 		PieData_HeartRate = 3;
	public static final int 		PieData_Activity =	4;
	
	public static boolean 			debug_code = 		true;
	public static boolean 			auto_read = 		true;
	public static boolean 			load_data = 		false;	
	
	public static String 			file_name = 		"";
	public static String 			file_name_short = 	"";
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
	}

	public static void loadFonts(String filename_prefix) {
		if (DataVizElement.processing_app_linked) {
			for(int i = 0; i < fonts.length; i ++) {
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
