package com.julioterra.moodyjulio.dataviz.shapes.pie;

public class Pie_Line extends Pie{

	public Pie_Line(int x, int y, float diameter, int number_of_slices) {
		super(x, y, diameter, PieElement.PIE_LINE, number_of_slices);
	}

	public Pie_Line(int x, int y, float diameter, int number_of_slices, float width) {
		super(x, y, diameter, PieElement.PIE_LINE, number_of_slices);
		this.setWidthAll(width);
	}

}
