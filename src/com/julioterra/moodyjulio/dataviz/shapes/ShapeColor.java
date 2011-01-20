package com.julioterra.moodyjulio.dataviz.shapes;

import java.awt.Color;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import processing.core.PApplet;

public class ShapeColor extends Shape {

	protected int color;
	protected int color_base;
	protected float hue_shift;
	protected float sat_shift;
	protected float bright_shift;	
	protected boolean mouse_over;

	/*********************************************************
	 ** CONSTRUCTOR METHODS 
	 **/

	public ShapeColor() {
		super();
	}
	
	public ShapeColor (int x, int y) {
		super(x, y);
	}

	public ShapeColor (int x, int y, int color) {
		super(x, y);
		this.color_base = color;
		this.color = this.color_base;
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
	      super.move(x, y);
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

	/** Shift Methods **/

	public void setColorShift(float hue_shift, float sat_shift, float bright_shift) {
		// all values should range from 0 - 1
		this.hue_shift = hue_shift;
		this.sat_shift = sat_shift;
		this.bright_shift = bright_shift;
	}
	
	public void shiftHue() {
		float hue = processing_app.hue(this.color_base);
		float hue_offshift = (float) (hue*this.hue_shift);
		this.color = adjustHue(this.color, (hue + hue_offshift));

		if (DataVizElement.debug_code) PApplet.println("current hue " + (hue / 255) + " hue shift " + this.hue_shift);
	}

	public void shiftSat() {
		float saturation = processing_app.saturation(this.color_base);
		float sat_offshift = (float) (saturation*this.sat_shift);
		this.color = adjustSat(this.color, (saturation + sat_offshift));

		if (DataVizElement.debug_code) PApplet.println("saturation " + (saturation / 255) + " saturation shift " + this.sat_shift);
	}

	public void shiftBright() {
		float brightness = processing_app.brightness(this.color_base);
		float bright_shift = (float) (brightness*this.bright_shift);
		this.color = adjustBright(this.color, (brightness + bright_shift));

		if (DataVizElement.debug_code) PApplet.println("brightness " + (brightness / 255)  + " brightness shift " + this.bright_shift);
	}

	/** SET BASE COLOR METHODS **/

	public void setColorBaseARGB(int a, int r, int g, int b) {
		this.color_base = ShapeColor.colorARGB(a, r, g, b);
		this.color = this.color_base;
	}
	
	public void setColorBaseARGB(int argb) {
		this.color_base = argb;
		this.color = this.color_base;
	}

	public void setColorBase(int rgb) {
		this.color_base = colorRGB(rgb);
		this.color = this.color_base;
	}

	/** RESET COLOR METHOD **/

	public void colorReset() {
		this.color = this.color_base;
		float hue = processing_app.hue(this.color);
		float saturation = processing_app.saturation(this.color);
		float brightness = processing_app.brightness(this.color);
		if (DataVizElement.debug_code) PApplet.println("current hue " + (hue) + " saturation " + saturation + " brightness " + brightness);
	}

	/** SET ACTIVE RGB COLOR METHODS **/

	public void setColorARGB(int a, int r, int g, int b) {
		this.color = colorARGB(a, r, g, b);
	}

	public void setColorARGB(int argb) {
		this.color = argb;
	}

	public void setColorRGB(int r, int g, int b) {
		int a = 255;
		this.setColorARGB(a, r, g, b);
	}

	public void setColorRGB(int rgb) {
		this.color = colorRGB(rgb);
	}

	/** SET ACTIVE HSB COLOR METHODS **/
	
	public void setColorHSB(float hue, float saturation, float brightness) {
		this.color = Color.HSBtoRGB(hue, saturation, brightness);
	}

	public void setColorHue(float hue) {
		this.color = adjustHue(this.color, hue);
	}

	public void setColorSat(float saturation) {
		this.color = adjustSat(this.color, saturation);	
	}

	public void setColorBright(float brightness) {
		this.color = adjustBright(this.color, brightness);
	}

	/** GET METHODS **/

	public int getBaseColor() {
		return this.color_base;
	}

	public int getColor() {
		return this.color;
	}
	
	public float getColorHue() {
		return processing_app.hue(this.color);
	}

	public float getColorSat() {
		return processing_app.saturation(this.color);
	}

	public float getColorBright() {
		return processing_app.brightness(this.color);
	}

	/*********************************************************
	 ** STATIC METHODS 
	 **/

	/** GET COLOR METHODS **/
	
	public static int colorARGB(int a, int r, int g, int b) {
		a = a << 24;  // bit shift a value by 24 bits
		r = r << 16;  // bit shift r value by 16 bits
		g = g << 8;   // bit shift g value by 8 bits	
		return  a | r | g | b;
	}

	public static int colorRGB(int r, int g, int b) {
		int a = 255;
		a = a << 24;  // bit shift a value by 24 bits
		r = r << 16;  // bit shift r value by 16 bits
		g = g << 8;   // bit shift g value by 8 bits	
		return  a | r | g | b;
	}

	public static int colorRGB(int rgb) {
		int a = 255;
		a = a << 24;  // bit shift a value by 24 bits
		return  a | rgb;
	}

	/** ADJUST METHODS **/
	
	public static int adjustHue(int color, float hue) {
		if (hue > 255) hue = 1;
		else hue = (hue / 255);
		float saturation = (processing_app.saturation(color) / 255);
		float brightness = (processing_app.brightness(color) / 255);
		if (DataVizElement.debug_code) PApplet.println("color hue: hue " + hue + " sat " + saturation + " bright " + brightness);

		return colorRGB(Color.HSBtoRGB(hue, saturation, brightness));
	}

	public static int adjustSat(int color, float saturation) {
		if (saturation > 255) saturation = 1;
		else saturation = (saturation / 255);
		float hue = (processing_app.hue(color) / 255);
		float brightness = (processing_app.brightness(color) / 255);
		if (DataVizElement.debug_code) PApplet.println("color sat: hue " + hue + " sat " + saturation + " bright " + brightness);

		return colorRGB(Color.HSBtoRGB(hue, saturation, brightness));
	}
	
	public static int adjustBright(int color, float brightness) {
		if (brightness > 255) brightness = 1;
		else brightness = (brightness / 255);
		float hue = (processing_app.hue(color) / 255);
		float saturation = (processing_app.saturation(color) / 255);
		if (DataVizElement.debug_code) PApplet.println("color bright: hue " + hue + " sat " + saturation + " bright " + brightness);

		return colorRGB(Color.HSBtoRGB(hue, saturation, brightness));
	}

}
