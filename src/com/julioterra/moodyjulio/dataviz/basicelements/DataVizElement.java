package com.julioterra.moodyjulio.dataviz.basicelements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.julioterra.moodyjulio.dataviz.application.DataVizLib;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import de.bezier.data.sql.MySQL;
import processing.core.*;

public class DataVizElement {

	public static DataVizLib processing_app;
	public static PGraphics			processing_app_buffer;
	public static boolean 			processing_app_linked = false;	

	/*******************************************
	 ** FONT RELATED CONSTANTS
	 *******************************************/
	public static final String		font_prefix = "font";
	public static final int [] 		fonts_size = {27, 20, 27, 24, 18, 24, 24, 48, 14, 20, 34, 24};
	public static final PFont [] 	fonts = new PFont[fonts_size.length];
	public static boolean 			font_loaded = false;
	public static final int			font_nav_title = 		0;
	public static final int			font_nav_small = 		1;
	public static final int			font_nav_large = 		2;
	public static final int			font_main_header = 		3;
	public static final int			font_main_subheader = 	4;
	public static final int			font_main_text = 		5;
	public static final int			font_main_heart =		6;
	public static final int			font_main_heart_text =	7;
	public static final int			font_main_subnav =		8;
	public static final int			font_main_bar_legend =	9;
	public static final int			font_main_bar_title =	10;
	public static final int			font_main_bar_text =	11;
	
	
	/*******************************************
	 ** DEBUGGING RELATED GLOBAL VARIABLES
	 *******************************************/
	public static boolean 			debug_code = 		true;
	public static boolean			debug_mouse_over = 	false;
	
	
	/*******************************************
	 ** DATABASE RELATED CONSTANTS & GLOBAL VARIABLES
	 *******************************************/
	// this variable holds the database itself so that it can be accessed by all subclasses 
	public static MySQL 			database;

	// variables that govern whether the application can access the database or not 
	public static boolean 			auto_read = 		true;
	public static boolean 			data_load = 		false;	
	public static boolean 			data_read = 		true;	

	// array with name of databases 
	public static final String []	database_name		= {"MobileData", "JournalData", "PieData_Emotion", "PieData_HeartRate", "PieData_Activity"};

	// constants that are used to refer to the appropriate database name 
	public static final int 		MOBILE = 			0;
	public static final int 		MobileData = 		0;
	public static final int 		JOURNAL = 			1;
	public static final int 		JournalData = 		1;
	public static final int 		EMOTION = 			2;
	public static final int 		PieData_Emotion =	2;
	public static final int 		HEART_RATE = 		3;
	public static final int 		PieData_HeartRate = 3;
	public static final int 		PieData_Activity =	4;
	// constants that will be declared in the future if new databases are created
	// public static final int 		ACTIVITY = 			4;
	// public static final int 		PEOPLE = 			5;

	
	// variable used for converting journal data into emotion data
	public static final float 		EmotionAgeThresholdHours = 2.5f;
	public static final float 		EmotionAgeThresholdSeconds = EmotionAgeThresholdHours * 60 * 60;


	/*******************************************
	 ** COLOR RELATED CONSTANTS
	 *******************************************/
	public static final int			Nav_Bar_Color =	ShapeColor.colorARGB(255, 178, 178, 178);
	public static final int			Transparent_Color = ShapeColor.colorARGB(0, 255, 255, 255); 
	public static final int			Black = ShapeColor.colorARGB(255, 0, 0, 0); 
	public static final int			White = ShapeColor.colorARGB(255, 255, 255, 255); 
	public static final int			Base_Background_Color =	ShapeColor.colorARGB(255, 255, 255, 255);
	public static final int			Cur_Background_Color =	Base_Background_Color;

	/*******************************************
	 ** EMOTION CODING RELATED CONSTANTS
	 *******************************************/
	public static final int [] 		Emotion_Colors = {ShapeColor.colorHSB(40, 200, 255), ShapeColor.colorHSB(140, 200, 255)};
	public static final String []	Emotion_Legend = {"feeling positive", "feeling negative"};
	public static final String []	Emotion_Legend_Rollover = {"I felt positive x% of the time", "I felt negative x% of the time"};
	public static final String []	Emotion_Names = {"positive", "negative"};
	public static final int			emotion_positive = 0;
	public static final int			emotion_negative = 1;
	
	public static final int 		Heartrate_Color = ShapeColor.colorHSB(0, 255, 255);
	public static final String		Heartrate_Legend = "my heart rate";
	public static final String 		Heartrate_Legend_Rollover = "my average heart rate was x";

	
	/*******************************************
	 ** ACTIVITY CODING RELATED CONSTANTS
	 *******************************************/
	public static final int [] 		Activity_Colors = {ShapeColor.colorHSBAdobe(0, 50, 100), ShapeColor.colorHSBAdobe(0, 100, 100),
													   ShapeColor.colorHSBAdobe(0, 100, 80), ShapeColor.colorHSBAdobe(35, 99, 100),
													   ShapeColor.colorHSBAdobe(58, 100, 100), ShapeColor.colorHSBAdobe(105, 50, 100),
													   ShapeColor.colorHSBAdobe(107, 100, 100), ShapeColor.colorHSBAdobe(105, 100, 80),
													   ShapeColor.colorHSBAdobe(172, 100, 95), ShapeColor.colorHSBAdobe(205, 50, 100),
													   ShapeColor.colorHSBAdobe(208, 100, 100), 
													   ShapeColor.colorHSBAdobe(276, 40, 100), ShapeColor.colorHSBAdobe(279, 100, 100),
													   ShapeColor.colorHSBAdobe(314, 100, 100)};
	public static final String []	Activity_Names = {"personal", "family", "home", "school class", "school work", "school event","eating", "health", 
													  "social", "transportation", "csa", "shopping", "media time", "smoking"};
	public static final int			color_personal =	 	0;
	public static final int			color_family = 			1;
	public static final int			color_home = 			2;
	public static final int			color_school_class = 	3;
	public static final int			color_school_work = 	4;
	public static final int			color_school_event = 	5;
	public static final int			color_eating = 			6;
	public static final int			color_health = 			7;
	public static final int			color_social = 			8;
	public static final int			color_transportation = 	9;
	public static final int			color_csa = 			10;
	public static final int			color_shopping = 		11;
	public static final int			color_media_time = 		12;
	public static final int			color_smoking = 		13;
	
	public static int ActivityColor(String activity) {
		if (activity.contains("personal")) return Activity_Colors[color_personal];
		if (activity.contains("family")) return Activity_Colors[color_family];
		if (activity.contains("home")) return Activity_Colors[color_home];
		if (activity.contains("eating")) return Activity_Colors[color_eating];
		if (activity.contains("health")) return Activity_Colors[color_health];
		if (activity.contains("school work")) return Activity_Colors[color_school_work];
		if (activity.contains("school class")) return Activity_Colors[color_school_class];
		if (activity.contains("school event")) return Activity_Colors[color_school_event];
		if (activity.contains("social")) return Activity_Colors[color_social];
//		if (activity.contains("work")) return Activity_Colors[color_work];
		if (activity.contains("csa")) return Activity_Colors[color_csa];
		if (activity.contains("transportation")) return Activity_Colors[color_transportation];
		if (activity.contains("shopping")) return Activity_Colors[color_shopping];
		if (activity.contains("media time")) return Activity_Colors[color_media_time];
		if (activity.contains("smoking")) return Activity_Colors[color_smoking];
		return ShapeColor.colorARGB(255, (int)processing_app.random(255), (int)processing_app.random(255), (int)processing_app.random(255));
	}

	
	/*******************************************
	 ** MOUSE EVENT RELATED STATIC GLOBAL VARIABLS AND CONSTANTS
	 *******************************************/

	public static int				id_counter = 0;
	public static int getIDColorNumber() {
		id_counter--;
		return id_counter;
	}
	
	/*******************************************
	 ** FILE READING RELATED STATIC GLOBAL VARIABLES USED BY CHILD CLASSES
	 *******************************************/
	public static boolean 			file_active;
	public static boolean 			file_read;
	public static boolean 			file_completed;

	public int 						total_readings;
	public int 						valid_readings;	
	public int 						data_batch;
	

	/*******************************************
	 ** CALENDAR RELATED CONSTANTS
	 *******************************************/
	public final static String[]	Sunday = {"2010/11/21", "2010/11/28"};
	public final static String[]	Monday = {"2010/11/22", "2010/11/29"};
	public final static String[]	Tuesday = {"2010/11/23", "2010/11/30"};
	public final static String[]	Wednesday = {"2010/11/17", "2010/11/24", "2010/12/01"};
	public final static String[]	Thursday = {"2010/11/18", "2010/11/25", "2010/12/02"};
	public final static String[]	Friday = {"2010/11/19", "2010/11/26", "2010/12/03"};
	public final static String[]	Saturday = {"2010/11/20", "2010/11/27", "2010/12/04"};
	public final static String[]	AllWeeks = {		"2010/11/17", "2010/11/18", "2010/11/19",
													"2010/11/20", "2010/11/21", "2010/11/22",
													"2010/11/23", "2010/11/24", "2010/11/25",
													"2010/11/26", "2010/11/27", "2010/11/28",
													"2010/11/29", "2010/11/30",
													"2010/12/01", "2010/12/02",
													"2010/12/03", "2010/12/04"};
	public final static ArrayList<String[]>	DaysOfWeek = new ArrayList<String[]>();	
	public final static String[]	NamesOfDays = {"Sunday", "Monday" , "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Overall"};
	public final static String[]	NamesOfMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	public final static String[]	NamesOfMonthsShort = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	

	/*******************************************
	 ** APPLICATION INIT METHODS
	 *******************************************/
	
	public static void application_init (PApplet processing_app, PGraphics buffer) {
		DataVizElement.processing_app = (DataVizLib) processing_app;
		DataVizElement.processing_app_buffer = buffer;
		DataVizElement.processing_app_linked = true;
		DataVizElement.database_connect();
		DataVizElement.loadFonts(font_prefix);
		DaysOfWeek.add(Sunday);
		DaysOfWeek.add(Monday);
		DaysOfWeek.add(Tuesday);
		DaysOfWeek.add(Wednesday);
		DaysOfWeek.add(Thursday);
		DaysOfWeek.add(Friday);
		DaysOfWeek.add(Saturday);
		DaysOfWeek.add(AllWeeks);
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
				

	public static HashMap<String, Integer> adjustValuesInHashMap(int adjust_value, HashMap<String, Integer> map) {	
		HashMap<String, Integer> 				new_map 	= new HashMap<String, Integer>(map);
		Set<Map.Entry<String, Integer>> 		entries 	= new_map.entrySet();
		Iterator<Map.Entry<String, Integer>> 	it 			= entries.iterator();
		String 									remove_key 	= "";

		// loop through the hashmap
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
			// if the current value is larger then the adjust value then reduce it by one
			int cur_val_int = Integer.parseInt("" + entry.getValue());
		    if (cur_val_int > adjust_value) entry.setValue(cur_val_int-1);
			// if the current value is equal to current value then set the remove key variable to the current key
		    else if (cur_val_int == adjust_value) remove_key = "" + entry.getKey();
		}
		if (!remove_key.equals("")) new_map.remove(remove_key);
		return new_map;
	}

}
