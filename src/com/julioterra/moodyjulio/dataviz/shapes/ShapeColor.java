package com.julioterra.moodyjulio.dataviz.shapes;

import java.awt.Color;

import processing.core.PApplet;

public class ShapeColor extends Shape {

	protected int color_base = colorARGB(0, 255, 255, 255);
	protected int color = color_base;

	protected float 	hue_shift_mouse_over;
	protected float 	sat_shift_mouse_over;
	protected float 	bright_shift_mouse_over;	

	protected float 	hue_shift_mouse_clicked;
	protected float 	sat_shift_mouse_clicked;
	protected float 	bright_shift_mouse_clicked;	

	/*********************************************************
	 ** CONSTRUCTOR METHODS 
	 **/

	public ShapeColor() {
		super();
		this.color = Transparent_Color;
	}
	
	public ShapeColor (int x, int y) {
		super(x, y);
		this.color = Transparent_Color;
	}

	public ShapeColor (int x, int y, int color) {
		super(x, y);
		this.color_base = color;
		this.color = this.color_base;
	}

	/*********************************************************
	 ** DISPLAY METHODS 
	 ** defined in shape class along with move, turn, scale
	 **/
	
	/*********************************************************
	 ** MOUSE OVER METHODS 
	 **/

	public void mouseOverActions() {
		if(!this.mouse_over) {
			if(this.mouse_over_active) {
				this.shiftHue(this.hue_shift_mouse_over);
				this.shiftSat(this.sat_shift_mouse_over);
				this.shiftBright(this.bright_shift_mouse_over);
				
				this.shiftSize(this.size_shift_mouse_over);
				if(this.text_toggle_mouse_over) this.setTextVisibleNameDescription();
				this.mouseOverExternalActions();
//				PApplet.println("Mouse Over");
			}
			this.mouse_over = true;			             // important to only change the flag at the end of the function
		}
	}

	public void mouseOffActions() {
		if (this.mouse_over) {
			if(this.mouse_over_active) {
				this.shiftScaleReset();
				if(this.text_toggle_mouse_over) this.setTextInvisibleNameDescription();
				this.mouseOffExternalActions();
				this.shiftColorReset();
//				PApplet.println("Mouse Gone");
			}
			this.mouse_over = false;
		}
	}

	public void mouseClickedActions() {
//		if(this.mouse_pressed) {
			if(this.mouse_clicked_active) {
				if (this.mouse_click_toggle) {
					this.mouse_click_toggle = false;
					this.shiftColorReset();
					this.shiftScaleReset();
					if(this.text_toggle_mouse_clicked) this.setTextInvisibleNameDescription();
//					PApplet.println("Mouse Clicked - toggle off");
				} else {
					this.mouse_click_toggle = true;
					this.shiftSize(this.size_shift_mouse_clicked);
					if(this.text_toggle_mouse_clicked) this.setTextVisibleNameDescription();
					this.shiftHue(this.hue_shift_mouse_clicked);
					this.shiftSat(this.sat_shift_mouse_clicked);
					this.shiftBright(this.bright_shift_mouse_clicked);
//					PApplet.println("Mouse Clicked - toggle on");
				}
				this.mouseClickedExternalActions();	
//			}
			this.mouse_pressed = false;
		}
	}

	/*********************************************************
	 ** COLOR METHOD 
	 **/

	/** Shift Methods **/

	public void setShiftAllMouseOver(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over) {
		this.hue_shift_mouse_over = hue_shift;
		this.sat_shift_mouse_over = saturation_shift;
		this.bright_shift_mouse_over = brightness_shift;
		this.size_shift_mouse_over = scale_shift;
		this.text_toggle_mouse_over = text_shift_mouse_over;
//		this.mouse_over_active = true;
	}
	
	public void setColorShiftMouseOver(float hue_shift, float sat_shift, float bright_shift) {
		// all values should range from 0 - 1
		this.hue_shift_mouse_over = hue_shift;
		this.sat_shift_mouse_over = sat_shift;
		this.bright_shift_mouse_over = bright_shift;
	}

	public void shiftHue(float shift_hue) {
		float hue = processing_app.hue(this.color_base);
		float hue_offshift = (float) (255f*shift_hue);
		this.color = adjustHue(this.color, (hue + hue_offshift));
	}
	
	public void shiftSat(float shift_sat) {
		float saturation = processing_app.saturation(this.color_base);
		float sat_offshift = (float) (255f*shift_sat);
		this.color = adjustSat(this.color, (saturation + sat_offshift));
	}
	
	public void shiftBright(float shift_bright) {
		float brightness = processing_app.brightness(this.color_base);
		float bright_shift = (float) (255f*shift_bright);
		this.color = adjustBright(this.color, (brightness + bright_shift));
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

	public void shiftColorReset() {
		this.color = this.color_base;

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
