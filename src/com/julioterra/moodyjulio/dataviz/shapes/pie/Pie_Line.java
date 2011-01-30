package com.julioterra.moodyjulio.dataviz.shapes.pie;

import processing.core.PApplet;

public class Pie_Line extends Pie{

	public Pie_Line(int x, int y, float diameter, int number_of_slices, int radius_type) {
		super(x, y, diameter, number_of_slices, radius_type);
		PApplet.println("pie line - constructor " + number_of_slices + " - " + radius_type);
		this.setWidthAll(0);
	}

	public Pie_Line(int x, int y, float diameter, int number_of_slices, float width,  int radius_type) {
		super(x, y, diameter, number_of_slices, radius_type);
		this.setWidthAll(width);
	}

}
