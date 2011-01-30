package com.julioterra.moodyjulio.dataviz.datahandlers;

import processing.core.PApplet;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.PieEmotionData;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie_Arc;

public class PieCreatorEmotion extends PieCreator {

	public PieCreatorEmotion(float x, float y, int diameter, String name) {
		super(x, y, diameter);
		this.pie_name = name;
	} 

	public void loadPie(Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {		
		pie_data = load_date_and_time_range(EMOTION, date_range_start, time_range_start, date_range_end, time_range_end);
		this.pie_slices = pie_data.size();
		this.pie_description = "from " + date_range_start.get_date_for_sql() + " to " + date_range_end.get_date_for_sql();
		
		pie = new Pie_Arc((int) location.x, (int) location.y, (float) diameter, ShapeCircle.PIE_ARC_SET_RADIUS);
		pie.setName(this.pie_name);
		pie.setDescription(this.pie_description);
		pie.setColorARGB(Cur_Background_Color);
		pie.setShiftMouseOverPie((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.0, false);
		pie.textLocationNameDescription(this.location_name.x, this.location_name.y, (this.location_description.x-this.location_name.x), (this.location_description.y-this.location_name.y));
		pie.loadFontPie(font_main_header, DataVizElement.fonts_size[font_main_header], 0.8f);
		pie.setTextVisibleNameLocation();
		float angle_pie = 0;

		PApplet.println("EMOTION PIE - CREATOR - number of slices " + pie.slices.size() + "  " + pie_data.size());

		for (int i = 0; i < pie_data.size(); i++) {
			PieEmotionData pie_record = new PieEmotionData((PieEmotionData) pie_data.get(i));
			angle_pie = Time.calculate_time_dif_seconds(pie_record.time_stamp, pie_record.time_end);

			int cur_color = Cur_Background_Color;
			int s = (int) ( ( (pie_record.emotion_intensity/4.0)*0.4 + 0.4) * 255);
			if (pie_record.emotion_type > 0 ) cur_color = ShapeColor.colorHSB(150, s, 255);
			else if (pie_record.emotion_type < 0 ) cur_color = ShapeColor.colorHSB(0, s, 255);

			String name = EmotionDataProcessor.convertEmotionToString(pie_record.emotion_type) + " mood, " +  EmotionDataProcessor.convertEmotionIntensityToString(pie_record.emotion_intensity) + " intensity";
			String description = "from " + pie_record.time_stamp.get_time_for_sql() + " to " + pie_record.time_end.get_time_for_sql();
			if (pie_record.emotion_intensity == 0) name = "unplugged";
			else description += "\n" + "feeling " + pie_record.emotion.toLowerCase() + "\n" + "doing " + pie_record.activity + "\n" +
				 "at " + pie_record.place + "\n" + "with " + countListItems(pie_record.people) + " people";
				
			pie.addSlice(name, description, angle_pie, cur_color);
		}
		pie.setShiftMouseOverSlices((float) 0.0, (float) -0.15, (float) 0.15, (float) 0.1, true);
		pie.textLocationNameDescriptionSlices(this.location_name_slice.x, this.location_name_slice.y, (this.location_description_slice.x-this.location_name_slice.x), (this.location_description_slice.y-this.location_name_slice.y));
		pie.loadFontSlices(font_main_body, DataVizElement.fonts_size[font_main_body], 0.9f);
		pie.applyValuesToSliceDisplay();
		PApplet.println("number of slices " + pie.slices.size() + "  " + angle_pie);

		this.date_range_start = new Date(date_range_start); 
		this.time_range_start = new Time(time_range_start);
		this.date_range_end = new Date(date_range_end);
		this.time_range_end = new Time(time_range_end);
	}
}

