package com.julioterra.moodyjulio.dataviz.shapes;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import processing.core.PVector;

public class Shape extends DataVizElement {

	public PVector location;
	int color;
	boolean mouse_over;

	
	/*********************************************************
	 ** CONSTRUCTOR METHODS 
	 **/

	public Shape() {
	}
	
	public Shape (int x, int y) {
		this.location = new PVector(x, y);
	}

	public Shape (int x, int y, int color) {
		this.location = new PVector(x, y);
		this.color = color;
	}

	
	/*********************************************************
	 ** DISPLAY METHODS 
	 **/
	
	public void display() {
		processing_app.smooth();
		processing_app.noStroke();
	    processing_app.fill(color);
	}

	  public void turn(float new_start_angle) {
	  }

	  public void move(float x, float y) {
	      this.location = new PVector(x, y);
	  }

	
	/*********************************************************
	 ** MOUSE OVER METHODS 
	 **/

	public void mouseOver() {
	}
	
	protected boolean contains(float x, float y) {
		return false;
	}

	
	/*********************************************************
	 ** COLOR METHOD 
	 **/

	public void color(int a, int r, int g, int b) {
		this.color = Shape.colorARGB(a, r, g, b);
	}
	
	
	/*********************************************************
	 ** STATIC METHODS 
	 **/

	public static int colorARGB(int a, int r, int g, int b) {
		a = a << 24;  // bit shift a value by 24 bits
		r = r << 16;  // bit shift r value by 16 bits
		g = g << 8;   // bit shift g value by 8 bits	
		return  a | r | g | b;
	}
	
}
