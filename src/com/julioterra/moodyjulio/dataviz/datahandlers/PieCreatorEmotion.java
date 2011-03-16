package com.julioterra.moodyjulio.dataviz.datahandlers;

import java.util.ArrayList;

import processing.core.PApplet;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.data.Data;
import com.julioterra.moodyjulio.dataviz.data.PieEmotionData;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeCircle;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie_Arc;

public class PieCreatorEmotion extends PieCreator {

	public int pie_mode;
	public float[] time_per_emotion;
	public float time_emotion_total;

	public PieCreatorEmotion(float x, float y, int diameter, String name) {
		super(x, y, diameter);
		this.title = name;
		this.pie_mode = 0;
		this.time_per_emotion = new float[Emotion_Names.length];
		this.time_emotion_total = 0;
	} 

	public void loadDayOfWeek(int day_of_week) {
		pie_data = new ArrayList<Data>();
		Time temp_start_time = new Time(time_range_start);
		float time_dif = Time.calculate_time_dif_seconds(time_range_start, time_range_end)/pie_slices;
		Time temp_end_time = new Time(time_range_start);
		temp_end_time.update_seconds((int)time_dif);
		for(int i = 0; i < pie_slices; i ++) {
			pie_data.add(loadTimeRangeFromDate(day_of_week, temp_start_time, temp_end_time));
			temp_start_time.update_seconds((int)time_dif);
			temp_end_time.update_seconds((int)time_dif);
		}
		this.pie_mode = 1;
		loadPie();
		this.description = NamesOfDays[day_of_week] + " average";
		pie.description.setText(this.description);
	}
	
	private PieEmotionData loadTimeRangeFromDate(int day_of_week, Time time_range_start, Time time_range_end) {
		ArrayList<Data> temp_pie_data = load_multiple_date_time_range(PieData_Emotion, DaysOfWeek.get(day_of_week), time_range_start, time_range_end);
		PieEmotionData new_data = new PieEmotionData(new Date("2000/01/01"), time_range_start, new Date("2000/01/01"), time_range_end);
		int valid_reads = 0;
		for (int i = 0; i < temp_pie_data.size(); i++) {
			PieEmotionData current_data = (PieEmotionData) temp_pie_data.get(i);
			if (!current_data.name.contains("unplug")) {
				new_data.emotion_type += current_data.emotion_type;
				new_data.emotion_intensity += current_data.emotion_intensity;
				new_data.emotion = joinLists(new_data.emotion, current_data.emotion);
				new_data.activity = joinLists(new_data.activity, current_data.activity);
				new_data.place = joinLists(new_data.place, current_data.place);
				new_data.people = joinLists(new_data.people, current_data.people);
				valid_reads ++;
			}
		}
		if (valid_reads > 0) {
			PApplet.println("EMOTION LOAD PIE calc - reads " + valid_reads + " emotion type "+ new_data.emotion_type + " intensity " + new_data.emotion_intensity);
			new_data.emotion_type = (float) (new_data.emotion_type / valid_reads);
			if (new_data.emotion_type == 0) new_data.emotion_type = 0.01f;
			new_data.emotion_intensity = (int) (new_data.emotion_intensity / valid_reads);		
			if (new_data.emotion_intensity <= 0) new_data.emotion_intensity = 0.01f;
			new_data.name = EmotionDataProcessor.convertEmotionToString(new_data.emotion_type) + " mood, " +  EmotionDataProcessor.convertEmotionIntensityToString(new_data.emotion_intensity) + " intensity";		
		} else {
			new_data.emotion_type = 0;
			new_data.emotion_intensity += 0;		
			new_data.name = "unplugged";		
		}
		PApplet.println("EMOTION LOAD PIE final - emotion type "+ new_data.emotion_type + " intensity " + new_data.emotion_intensity);
		return new_data;
	}

	
	public void loadViz_DateTimeRange(Date date_range_start, Time time_range_start, Date date_range_end, Time time_range_end) {		
		this.pie_data = load_date_and_time_range(EMOTION, date_range_start, time_range_start, date_range_end, time_range_end);
		this.pie_mode = 0;
		this.date_range_start = new Date (date_range_start); 
		this.time_range_start = new Time (time_range_start); 
		this.date_range_end = new Date (date_range_end); 
		this.time_range_end = new Time (time_range_end);		
		this.loadPie();
	}


	public void loadDate(Date date) {		
		this.pie_data = load_date_and_time_range(EMOTION, date, new Time("00:00:00"), date, new Time("23:59:59"));
		this.pie_mode = 0;
		date_range_start = new Date (date); 
		time_range_start = new Time (new Time("00:00:00")); 
		date_range_end = new Date (date); 
		time_range_end = new Time (new Time("23:59:59"));		
		this.loadPie();
	}

	private void loadPie() {		
		this.time_per_emotion = new float[Emotion_Names.length];
		this.time_emotion_total = 0;
		this.pie_slices = pie_data.size();
		this.description = Date.getDateInString(date_range_start);
		
		pie = new Pie_Arc((int) location.x, (int) location.y, (float) diameter, ShapeCircle.PIE_ARC_SET_RADIUS);
		pie.label.setText(this.title);
		pie.description.setText(this.description);
		pie.setColorActiveARGB(Cur_Background_Color);
		pie.setShiftMouseOver((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.0, false, false);
		pie.setTextLocationAll(this.location_name.x, this.location_name.y, (this.location_description.x-this.location_name.x), (this.location_description.y-this.location_name.y));
		pie.loadFontAll(font_main_bar_legend, 18, 1.4f);
		pie.setTextVisibleAll();

		float slice_time = 0;
		for (int i = 0; i < pie_data.size(); i++) {
			PieEmotionData pie_record = new PieEmotionData((PieEmotionData) pie_data.get(i));
			slice_time = Time.calculate_time_dif_seconds(pie_record.time_stamp, pie_record.time_end);

			int cur_color = ShapeColor.colorARGB(200, 240, 240, 240);
			int temp_s = (int) PApplet.abs((pie_record.emotion_intensity/4f) * 4f);
			int s = (int) (((temp_s/4.0) * 0.5 + 0.5) * 255);
			if (pie_record.emotion_type > 0 ) {
				cur_color = ShapeColor.colorHSB(40, s, 255);
				time_per_emotion[emotion_positive] += slice_time;
				time_emotion_total += slice_time;
			}
			else if (pie_record.emotion_type < 0 ) {
				cur_color = ShapeColor.colorHSB(140, s, 255);
				time_per_emotion[emotion_negative] += slice_time;
				time_emotion_total += slice_time;
			}

			String name = "from " + pie_record.time_stamp.get_time_for_sql() + " to " + pie_record.time_end.get_time_for_sql();
			String description = EmotionDataProcessor.convertEmotionToString(pie_record.emotion_type) + " mood, " +  EmotionDataProcessor.convertEmotionIntensityToString(pie_record.emotion_intensity) + " intensity";
			if (pie_record.emotion_intensity == 0) description = "unplugged";
			else if (this.pie_mode == 0) {
				description += "\n" + "feeling " + applyLineBreaks(pie_record.emotion.toLowerCase(), 40) +
							   "doing " + applyLineBreaks(pie_record.activity.toLowerCase(), 40) +
				 			   "at " + applyLineBreaks(pie_record.place.toLowerCase(), 40) + 
				 			   "with " + countListItems(pie_record.people) + " people";
			}
			else if (this.pie_mode == 1) {
				description += "\n" + "feeling " + applyLineBreaks(pie_record.emotion.toLowerCase(), 40);
			}
			pie.addElement(name, description, slice_time, cur_color);
		}

		pie.setElementAllShiftMouseOver((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.1, true, true);
		pie.setElementAllTextLocation(this.location_name_slice.x, this.location_name_slice.y, (this.location_description_slice.x-this.location_name_slice.x), (this.location_description_slice.y-this.location_name_slice.y));
		pie.loadElementAllLabelFont(font_main_bar_legend, 16);
		pie.loadElementAllDescriptionFont(font_main_text, 20);
		pie.applyValuesToSliceDisplay("base");

		PApplet.println("number of slices " + pie.elements.size() + "  " + slice_time);
		Emotion_Legend_Rollover[emotion_positive] = "" + (int)(time_per_emotion[emotion_positive]/time_emotion_total*100);
		Emotion_Legend_Rollover[emotion_negative] = "" + (int)(time_per_emotion[emotion_negative]/time_emotion_total*100);
		PApplet.println("time per emotion type " + time_per_emotion[emotion_positive] + " " + time_per_emotion[emotion_negative] + " " + time_emotion_total);
	}
	
	public float[] getEmotionTime() {
		float[] time_emotion_array = {time_per_emotion[emotion_positive], time_per_emotion[emotion_negative], time_emotion_total};
		Emotion_Legend_Rollover[emotion_positive] = "" + (int)(time_per_emotion[emotion_positive]/time_emotion_total*100);
		Emotion_Legend_Rollover[emotion_negative] = "" + (int)(time_per_emotion[emotion_negative]/time_emotion_total*100);
		return time_emotion_array;
	}

}

