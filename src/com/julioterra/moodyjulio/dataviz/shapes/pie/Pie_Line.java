package com.julioterra.moodyjulio.dataviz.shapes.pie;

import processing.core.PApplet;

public class Pie_Line extends Pie{

	public Pie_Line(int x, int y, float diameter, int number_of_slices) {
		super(x, y, diameter, PieElement.PIE_LINE, number_of_slices);
		PApplet.println("pie_line constructor - number of slices " + number_of_slices);
	}

}
