package com.julioterra.moodyjulio.dataviz.shapes.bar;

import java.util.ArrayList;
import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;

public class BarArray extends ShapeRect {

	ArrayList<Bar> bars;
	public Bar bar;
	public float bar_offset;
	public float bar_slices;
    public int bar_mode;
    
    
	public void display() {
		for (int i = 0; i < bars.size(); i++) {
	    	bars.get(i).display();
	    }
	}
	
	
}
