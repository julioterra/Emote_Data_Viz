package com.julioterra.moodyjulio.dataviz.view;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.basicelements.Date;
import com.julioterra.moodyjulio.dataviz.basicelements.Time;
import com.julioterra.moodyjulio.dataviz.datahandlers.DataProcessorHeartrate;
import com.julioterra.moodyjulio.dataviz.datahandlers.EmotionDataProcessor;
import com.julioterra.moodyjulio.dataviz.datahandlers.PieCreatorEmotion;
import com.julioterra.moodyjulio.dataviz.datahandlers.PieCreatorHeartrate;
import com.julioterra.moodyjulio.dataviz.shapes.Shape;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRectText;
import com.julioterra.moodyjulio.dataviz.shapes.bar.Bar;
import com.julioterra.moodyjulio.dataviz.shapes.panel.Panel;
import com.julioterra.moodyjulio.dataviz.shapes.pie.Pie;

public class View extends Shape {

	public String id;
	public HashMap<String, Integer> id_numbers;
	public HashMap<String, Pie> pies;
	public HashMap<String, Bar> bars;
	public HashMap<String, Panel> panels;

	public View(String id) {
		super(0,0);
		processing_app.registerDraw(this);

		this.id = id;
		this.id_numbers = new HashMap<String, Integer>();
		this.pies = new HashMap<String, Pie>();
		this.bars = new HashMap<String, Bar>();
		this.panels = new HashMap<String, Panel>();
	}
	
	public void display() {
		if (visible) {
			Iterator<Map.Entry<String, Pie>> pies_entries = pies.entrySet().iterator();
			while (pies_entries.hasNext()) {
				Map.Entry<String, Pie> pie_entry = (Map.Entry<String, Pie>) pies_entries.next();
				Pie pie = pie_entry.getValue();	 
				pie.display();
			}
	
			Iterator<Map.Entry<String, Bar>> bars_entries = bars.entrySet().iterator();
			while (bars_entries.hasNext()) {
				Map.Entry<String, Bar> bar_entry = (Map.Entry<String, Bar>) bars_entries.next();
				Bar bar = bar_entry.getValue();	 
				bar.display();
			}
	
			Iterator<Map.Entry<String, Panel>> panels_entries = panels.entrySet().iterator();
			while (panels_entries.hasNext()) {
				Map.Entry<String, Panel> panel_entry = (Map.Entry<String, Panel>) panels_entries.next();
				Panel panel = panel_entry.getValue();	 
				panel.display();
			}
		}
	}

}
