package com.julioterra.moodyjulio.dataviz.basicelements;

import de.bezier.data.sql.MySQL;
import processing.core.PApplet;
import processing.core.PFont;

public class DataVizElement {

	public static PApplet 			processing_app;
	public static MySQL 			database;
	public static boolean 			processing_app_linked = false;	

	public static final PFont [] 	fonts = new PFont[10];
	public static boolean 			font_loaded = false;
	
	public static int				data_view = 		0;
	public static final int			day_avg = 			1;
	public static final int			day_specific = 		2;
	public static final int			location_avg = 		3;
	public static final int			picture_avg = 		4;

	public static String			active_data_table	= "JournalData";
	public static final String []	database_name		= {"JournalData", "MobileData"};
	public static final int 		JournalData = 		0;
	public static final int 		MobileData = 		1;
	
	public static boolean 			debug_code = 		true;
	public static boolean 			auto_read = 		true;
	public static boolean 			load_data = 		false;	
	
	public static String 			file_name = 		"";
	public static String 			file_name_short = 	"";
	public static boolean 			file_active;
	public static boolean 			file_read;
	public static boolean 			file_completed;
	public static int 				total_readings;
	public static int 				valid_readings;	
	public static int 				data_batch;

	public static void application_init (PApplet processing_app) {
		DataVizElement.processing_app = processing_app; 
		DataVizElement.processing_app_linked = true;
//		DataVizElement.database_connect();
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
	
	public static void updated_data_table(int source_number) {
		DataVizElement.active_data_table = database_name[source_number];
	}
			
}
