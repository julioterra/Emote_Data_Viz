package com.julioterra.moodyjulio.dataviz.shapes;

import java.awt.Color;

import processing.core.PApplet;

public class NewShapeColor extends Shape {

	protected int color_base = colorARGB(0, 255, 255, 255);
	protected int color_active = color_base;

	protected float 	hue_shift_mouse_over;
	protected float 	sat_shift_mouse_over;
	protected float 	bright_shift_mouse_over;	

	protected float 	hue_shift_mouse_clicked;
	protected float 	sat_shift_mouse_clicked;
	protected float 	bright_shift_mouse_clicked;	

	/*********************************************************
	 ** CONSTRUCTOR METHODS 
	 **/

	public NewShapeColor() {
		super();
		this.color_active = Transparent_Color;
	}
	
	public NewShapeColor (float x, float y) {
		super(x, y);
		this.color_active = Transparent_Color;
	}

	public NewShapeColor (float x, float y, int color) {
		super(x, y);
		this.color_base = color;
		this.color_active = this.color_base;
	}

	public void setColorShiftMouseOver(float hue_shift, float sat_shift, float bright_shift) {
		// all values should range from 0 - 1
		this.hue_shift_mouse_over = hue_shift;
		this.sat_shift_mouse_over = sat_shift;
		this.bright_shift_mouse_over = bright_shift;
	}

	public void setColorShiftMouseClicked(float hue_shift, float sat_shift, float bright_shift) {
		// all values should range from 0 - 1
		this.hue_shift_mouse_clicked = hue_shift;
		this.sat_shift_mouse_clicked = sat_shift;
		this.bright_shift_mouse_clicked = bright_shift;
	}

	public void shiftHue(float shift_hue) {
		PApplet.println("changing hue - before" + id_number + " shift " + shift_hue + " hue " + processing_app.hue(this.color_base));
		float hue = processing_app.hue(this.color_base);
		float hue_offshift = (float) (255f*shift_hue);
		this.color_active = adjustHue(this.color_active, (hue + hue_offshift));
		PApplet.println("changing hue - after" + id_number + " shift " + shift_hue + " hue " + processing_app.hue(this.color_base));
	}
	
	public void shiftSat(float shift_sat) {
		float saturation = processing_app.saturation(this.color_base);
		float sat_offshift = (float) (255f*shift_sat);
		this.color_active = adjustSat(this.color_active, (saturation + sat_offshift));
		PApplet.println("changing saturation " + id_number);
	}
	
	public void shiftBright(float shift_bright) {
		float brightness = processing_app.brightness(this.color_base);
		float bright_shift = (float) (255f*shift_bright);
		this.color_active = adjustBright(this.color_active, (brightness + bright_shift));
	}

	/** SET BASE COLOR METHODS **/

	public void setColorBaseARGB(int a, int r, int g, int b) {
		this.color_base = ShapeColor.colorARGB(a, r, g, b);
		this.color_active = this.color_base;
	}
	
	public void setColorBaseARGB(int argb) {
		this.color_base = argb;
		this.color_active = this.color_base;
	}

	public void setColorBase(int rgb) {
		this.color_base = colorRGB(rgb);
		this.color_active = this.color_base;
	}

	/** RESET COLOR METHOD **/

	public void shiftColorReset() {
		this.color_active = this.color_base;

	}

	/** SET ACTIVE RGB COLOR METHODS **/

	public void setColorARGB(int a, int r, int g, int b) {
		this.color_active = colorARGB(a, r, g, b);
	}

	public void setColorARGB(int argb) {
		this.color_active = argb;
	}

	public void setColorRGB(int r, int g, int b) {
		int a = 255;
		this.setColorARGB(a, r, g, b);
	}

	public void setColorRGB(int rgb) {
		this.color_active = colorRGB(rgb);
	}

	/** SET ACTIVE HSB COLOR METHODS **/
	
	public void setColorHSB(float hue, float saturation, float brightness) {
		this.color_active = Color.HSBtoRGB(hue, saturation, brightness);
	}

	public void setColorHue(float hue) {
		this.color_active = adjustHue(this.color_active, hue);
	}

	public void setColorSat(float saturation) {
		this.color_active = adjustSat(this.color_active, saturation);	
	}

	public void setColorBright(float brightness) {
		this.color_active = adjustBright(this.color_active, brightness);
	}

	/** GET METHODS **/

	public int getBaseColor() {
		return this.color_base;
	}

	public int getColor() {
		return this.color_active;
	}
	
	public float getColorHue() {
		return processing_app.hue(this.color_active);
	}

	public float getColorSat() {
		return processing_app.saturation(this.color_active);
	}

	public float getColorBright() {
		return processing_app.brightness(this.color_active);
	}

	/*********************************************************
	 ** STATIC METHODS 
	 **/

	/** GET COLOR METHODS **/

	public static int colorHSB(float hue, float saturation, float brightness) {
		hue = (float) (hue/255.0);
		saturation = (float) (saturation/255.0);
		brightness = (float) (brightness/255.0);
		return Color.HSBtoRGB(hue, saturation, brightness);
	}

	public static int colorHSBAdobe(float hue, float saturation, float brightness) {
		hue = (float) (hue/360.0);
		saturation = (float) (saturation/100.0);
		brightness = (float) (brightness/100.0);
		return Color.HSBtoRGB(hue, saturation, brightness);
	}

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
//		if (DataVizElement.debug_code) PApplet.println("color hue: hue " + hue + " sat " + saturation + " bright " + brightness);

		return colorRGB(Color.HSBtoRGB(hue, saturation, brightness));
	}

	public static int adjustSat(int color, float saturation) {
		if (saturation > 255) saturation = 1;
		else saturation = (saturation / 255);
		float hue = (processing_app.hue(color) / 255);
		float brightness = (processing_app.brightness(color) / 255);
//		if (DataVizElement.debug_code) PApplet.println("color sat: hue " + hue + " sat " + saturation + " bright " + brightness);

		return colorRGB(Color.HSBtoRGB(hue, saturation, brightness));
	}
	
	public static int adjustBright(int color, float brightness) {
		if (brightness > 255) brightness = 1;
		else brightness = (brightness / 255);
		float hue = (processing_app.hue(color) / 255);
		float saturation = (processing_app.saturation(color) / 255);
//		if (DataVizElement.debug_code) PApplet.println("color bright: hue " + hue + " sat " + saturation + " bright " + brightness);

		return colorRGB(Color.HSBtoRGB(hue, saturation, brightness));
	}

}
