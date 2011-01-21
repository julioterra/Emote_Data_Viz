package com.julioterra.moodyjulio.dataviz.shapes.pie;

public class Pie_Arc extends Pie {

	public Pie_Arc(int x, int y, float diameter) {
		super(x, y, diameter, PieElement.PIE_ARC);
	}
	
	public Pie_Arc(int x, int y, float diameter, int number_of_slices) {
		super(x, y, diameter, PieElement.PIE_ARC, number_of_slices);
	}

}
