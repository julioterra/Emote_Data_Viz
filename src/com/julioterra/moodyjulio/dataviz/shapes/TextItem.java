package com.julioterra.moodyjulio.dataviz.shapes;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class TextItem extends ShapeColor {

	public String text;
	public int font_number;
	public float size_base;
	public int alignment;
	public String output_area;
	
	public PFont font;
	public boolean font_loaded;
	public float size_active;
	
	public TextItem () {
		super(-1000, -1000, Black);
		this.text = "";
		this.alignment = PApplet.LEFT;
		this.output_area = "this";
	}

	public TextItem(String label, float x, float y, int color, int font_number, int font_size, int alignment) {
		super(x, y, color);
		this.text = label;
		this.location = new PVector(x, y);
		this.alignment = alignment;
		this.loadFont(font_number, font_size);
		this.output_area = "this";
		visible = false;
	}

	public TextItem(String label, String display_via) {
		super(-1000, -1000, Black);
		this.text = label;
		this.setOutputArea(display_via);
		this.alignment = PApplet.LEFT;
	}

	
	/*********************************************************
	 ** TEXT & FONT METHODS 
	 **/

	public void display() {
		if (visible) {
			if (output_area.equals("this") && this.font_loaded) {
				super.display();
				processing_app.pushMatrix();
					processing_app.translate(this.location.x, this.location.y);
					processing_app.rotate(PApplet.radians(this.rotation));
					processing_app.fill(this.color_active);
					processing_app.textFont(this.font);
					processing_app.textSize((float) (this.size_active*this.scale));
					processing_app.textAlign(this.alignment);
					processing_app.text(this.text, 0,0);
				processing_app.popMatrix();

			} else if (!output_area.equals("this")) {
				DataVizElement.text_areas.get(output_area).displayText(this.text);
			}
		}
	}

	public boolean setOutputArea(String display_via) {
		if(DataVizElement.text_areas.get(display_via) != null || display_via.equals("this")) {
			this.output_area = display_via;
			return true;
		}
		return false;
	}


	/*********************************************************
	 ** TEXT & FONT METHODS 
	 **/

	public void setText(String new_label) {
		this.text = new_label;
	}

	public String getText() {
		return this.text;
	}

	public void loadFont(int new_font_number, int size) {
		if (new_font_number < DataVizElement.fonts.length) {
			this.font_number = new_font_number;
			this.font = DataVizElement.fonts[new_font_number];
			this.setSize(size);
			this.font_loaded = true;
		}
	}

	public void setSize(float size) {
		this.size_base = size;
		this.size_active = (float)(this.size_base*this.scale);
	}

	public float getSize() {
		return this.size_base;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public int getAlignment() {
		return this.alignment;
	}

	/*********************************************************
	 ** MOUSE SHIFT METHODS 
	 **/

	public void shiftSize(float shift_size) {
		super.shiftSize(shift_size);
		this.size_active = (float)(this.size_base * (this.scale + shift_size));
	}

	public void shiftScaleReset() {
		super.shiftScaleReset();
		this.size_active = (float)(this.size_base * this.scale);		
	}

	public void shiftScaleResetToBase() {
		super.shiftScaleResetToBase();
		this.scale = 1;
		this.shiftScaleReset();
	}

	public void shiftMouseOverOn() {
		if (this.mouse_over_active) {
			this.setVisible();
		}
	}

	public void shiftMouseOverOff() {
		if (this.mouse_over_active) {
			this.setInvisible();
		}
	}

	public void shiftMouseClickedVisible() {
		if (this.mouse_clicked_active) {
			this.setVisible();
		}
	}

	public void shiftMouseClickedInvisible() {
		if (this.mouse_clicked_active) {
			this.setInvisible();
		}
	}

}
