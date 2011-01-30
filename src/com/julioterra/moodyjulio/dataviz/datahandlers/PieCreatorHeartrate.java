package com.julioterra.moodyjulio.dataviz.datahandlers;

import processing.core.PApplet;
import processing.core.PVector;

import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.PieHeartData;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie_Line;

public class PieCreatorHeartrate extends PieCreator {

	public PieCreatorHeartrate(float x, float y, int diameter, String name) {
		super(x, y, diameter);
		this.pie_slices = 360;
		this.pie_name = name;		

		PApplet.println("NEW PIE CONSTRUCTOR - number of slices " + this.pie_slices);
	} 

	public void loadPie(Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {
		int transparent_color = ShapeColor.colorARGB(0, 255, 255, 255);
		int red_color = ShapeColor.colorARGB(255, 255, 0, 0);

		pie_data = load_date_and_time_range(PieData_HeartRate, date_range_start, time_range_start, date_range_end, time_range_end);		

		// initialize the pie with pie-level information
		pie = new Pie_Line((int) location.x, (int) location.y, (float) diameter, (int)this.pie_slices, ShapeCircle.PIE_LINE_VAR_RADIUS);
		pie.setName(this.pie_name);
		pie.setColorARGB(transparent_color);
		pie.textLocationNameDescription(this.location_name.x, this.location_name.y, (this.location_description.x-this.location_name.x), (this.location_description.y-this.location_name.y));
		pie.setTextVisibleNameLocation();
		pie.setShiftMouseOverPie((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.0, false);
		pie.loadFontPie(font_main_heart, fonts_size[font_main_heart], 1);
		pie.setWidthAll(5);

		// initialize the slices of the pie
		for (int i = 0; i < pie_data.size(); i++) {
			PieHeartData pie_record = (PieHeartData)pie_data.get(i);
			String name = "" + pie_record.heart_rate;
			String description = "";
			if (pie_record.heart_rate == 0) name = "unplugged";
			pie.setSliceValue(i, (float) (pie_record.heart_rate));
			pie.textSetNameSlice(i, name);
			pie.textSetDescriptionSlice(i,description);
		}
		pie.setColorAllSlices(red_color);
		pie.setShiftMouseOverSlices((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.0, true);
		pie.textLocationNameDescriptionSlices(this.location_name_slice.x, this.location_name_slice.y, (this.location_description_slice.x-this.location_name_slice.x), (this.location_description_slice.y-this.location_name_slice.y));
		pie.loadFontSlices(font_main_heart_text, fonts_size[font_main_heart_text], 1);
		pie.applyValuesToSliceDisplay();

		// update the date and time start and end ranges
		this.date_range_start = new Date(date_range_start); 
		this.time_range_start = new Time(time_range_start);
		this.date_range_end = new Date(date_range_end);
		this.time_range_end = new Time(time_range_end);
	}

}
