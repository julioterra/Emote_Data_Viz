package com.julioterra.moodyjulio.dataviz.shapes;

import processing.core.PVector;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;

public class Shape extends DataVizElement{

	public PVector location;

	public Shape () {
		this.location = new PVector(0, 0);
	}

	public Shape (int x, int y) {
		this.location = new PVector(x, y);
	}
	
	public void move(float x, float y) {
	    this.location = new PVector(x, y);
	}

}
