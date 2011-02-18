package com.julioterra.moodyjulio.dataviz.datahandlers;

import javax.activation.DataHandler;

import processing.core.PApplet;
import processing.core.PVector;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import com.julioterra.moodyjulio.dataviz.datahandlers.*;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;
import com.julioterra.moodyjulio.dataviz.view.View;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import processing.core.*;

public class ViewCreator extends DataProcessor {

	public HashMap<String, View> views;
	public HashMap<String, PVector> location_element;
	public HashMap<String, PVector> size_element;
	public HashMap<String, PVector> location_label;
	public HashMap<String, PVector> location_description;
	
	public ViewCreator (HashMap<String, View> views) {
		super();
		this.views = views;
		this.location_element = new HashMap<String, PVector>();
		this.size_element = new HashMap<String, PVector>();
		this.location_label = new HashMap<String, PVector>();
		this.location_description = new HashMap<String, PVector>();
	}

	/********************************************
	 * CREATE VIEW
	 * This is where the style elements are all applied to the shapes
	 * These elements can be defined via an xml sheet or via code 
	 * 
	 ********************************************/
	public void createView() {

	}
	
	public void updateView() {
		
	}
	
}
