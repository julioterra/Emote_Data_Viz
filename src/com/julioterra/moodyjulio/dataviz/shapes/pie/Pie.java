package com.julioterra.moodyjulio.dataviz.shapes.pie;

import java.util.ArrayList;
import com.julioterra.moodyjulio.dataviz.shapes.Shape;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeColor;

class Pie extends ShapeColor {
	
	public final int DYNAMIC = 0;
	public final int PRESET = 1;
	public int pie_type = DYNAMIC;
	
	public ArrayList<PieSlice> slices;
	public boolean is_pie_complete;
	public float diameter;
	protected float angle_total_degrees;
	protected float angle_total_percent;
	protected int number_of_slices;
	
	public Pie(int x, int y, float diameter) {
		super(x, y);
		this.slices = new ArrayList<PieSlice>();
		this.diameter = diameter;
		this.color = colorARGB(255, 255, 255, 255);
	}

	public void setPresetPie(int number_of_slices) {
		this.pie_type = PRESET;
		this.number_of_slices = number_of_slices;
		this.slices = new ArrayList<PieSlice>();

		if (this.number_of_slices > 0) {
			float angle_start = 0;
			float degrees_per_slice = 360 / this.number_of_slices;
			float size_in_percent = degrees_per_slice / 360;
			for (int i = 0; i < this.number_of_slices; i++) {
				this.slices.add(new PieArcSlice((int)this.location.x, (int)this.location.y, this.diameter/2, size_in_percent, angle_start, this.color));
				angle_start = i * degrees_per_slice;
			}
		}
	}
	
	
}
