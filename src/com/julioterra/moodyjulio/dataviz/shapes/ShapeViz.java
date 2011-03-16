package com.julioterra.moodyjulio.dataviz.shapes;

import java.util.HashMap;

import processing.core.*;

public class ShapeViz extends ShapeColor {
		
	public TextItem label; 
	public TextItem description; 
	
	public boolean label_toggle_mouse_over;
	public boolean label_toggle_mouse_clicked;
	public boolean description_toggle_mouse_over;
	public boolean description_toggle_mouse_clicked;
	
	public double value_one;
	HashMap<String, Double> values;
	HashMap<String, String> categories;
	
	public ShapeViz () {
		this(0,0, Transparent_Color);
	}

	public ShapeViz (float x, float y, int color) {
		super(x, y, color);
		this.label_toggle_mouse_over = false;
		this.label_toggle_mouse_clicked = false;
		this.description_toggle_mouse_over = false;
		this.description_toggle_mouse_clicked = false;

		this.label = new TextItem("", 0, 0, Black, 0, 0, PApplet.LEFT); 
		this.description = new TextItem("", 0, 0, Black, 0, 0, PApplet.LEFT); 
		
		this.values = new HashMap<String, Double>();
	}

	public ShapeViz (float x, float y) {
		this(x, y, Transparent_Color);
	}
	
	/*********************************************************
	 ** DISPLAY METHODS 
	 **/
	
	public void display() {
		super.display();
	}

	public void displayText() {
		super.display();
		if (this.visible) {
			this.label.display();
			this.description.display();
		}
	}	
		
	/*********************************************************
	 ** MOUSE OVER METHODS 
	 **/

	public void mouseOverActions() {
		if(!this.mouse_over) {
			if(this.mouse_over_active) {
				this.shiftHue(this.hue_shift_mouse_over);
				this.shiftSat(this.sat_shift_mouse_over);
				this.shiftBright(this.bright_shift_mouse_over);				
				this.shiftScale(this.size_shift_mouse_over);
				this.label.shiftMouseOverOn();
				this.description.shiftMouseOverOn();
				this.mouseOverExternalActions();
//				PApplet.println("Mouse Over");
			}
		}
		this.mouse_over = true;			             // important to only change the flag at the end of the function
	}

	public void mouseOffActions() {
		if (this.mouse_over) {
			if(this.mouse_over_active) {
				this.shiftScaleReset();
				this.label.shiftMouseOverOff();
				this.description.shiftMouseOverOff();
				this.mouseOffExternalActions();
				this.resetColor();
//				PApplet.println("Mouse Gone");
			}
		}
		this.mouse_over = false;
	}

	/*********************************************************
	 ** MOUSE CLICKED METHODS 
	 **/

	public void mouseClickedActions() {
		if(this.mouse_clicked_active) {
			if (this.mouse_clicked_toggle) {
				this.mouse_clicked_toggle = false;
				this.resetColor();
				this.shiftScaleReset();
				this.label.shiftMouseClickedVisible();
				this.description.shiftMouseClickedVisible();
//					PApplet.println("Mouse Clicked - toggle off");
			} else {
				this.mouse_clicked_toggle = true;
				this.shiftScale(this.size_shift_mouse_clicked);
				this.label.shiftMouseClickedInvisible();
				this.description.shiftMouseClickedInvisible();
				this.shiftHue(this.hue_shift_mouse_clicked);
				this.shiftSat(this.sat_shift_mouse_clicked);
				this.shiftBright(this.bright_shift_mouse_clicked);
//					PApplet.println("Mouse Clicked - toggle on");
			}
			this.mouseClickedExternalActions();	
			this.mouse_pressed = false;
		}
	}

	public void setShiftMouseOver(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_over, boolean description_shift_mouse_over) {
		this.setColorShiftMouseOver(hue_shift, saturation_shift, brightness_shift);
		this.size_shift_mouse_over = scale_shift;
		this.label_toggle_mouse_over = label_shift_mouse_over;
		this.description_toggle_mouse_over = description_shift_mouse_over;
		this.label.setMouseOverActive(label_shift_mouse_over);
		this.description.setMouseOverActive(description_shift_mouse_over);
		this.mouse_over_active = true;
	}
	

	public void setShiftMouseClicked(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked) {
		this.setColorShiftMouseClicked(hue_shift, saturation_shift, brightness_shift);
		this.size_shift_mouse_clicked = scale_shift;
		this.label_toggle_mouse_clicked = label_shift_mouse_clicked;
		this.description_toggle_mouse_clicked = description_shift_mouse_clicked;
		this.label.setMouseClickedActive(label_shift_mouse_clicked);
		this.description.setMouseClickedActive(description_shift_mouse_clicked);
		this.mouse_clicked_active = true;
	}
	
	/*********************************************************
	 ** TEXT & FONT METHODS 
	 **/

	public String getLabel() {
		return this.label.getText();
	}

	public String getDescription() {
		return this.description.getText();
	}

	public void loadFontAll(int new_font_number, int size, float font_scale) {
		this.label.loadFont(new_font_number, size);
		this.description.loadFont(new_font_number, (int) (size*font_scale));
	}

	public void setFontColorAll(int font_color) {
		this.label.setColorARGB(font_color);
		this.description.setColorARGB(font_color);
	}

	public void setFontSizeAll(float size) {
		this.label.setSize(size);
		this.description.setSize(size);
	}

	public void setTextLocationAll(float x, float y, float offset_x, float offset_y) {
		this.label.setLocation(x, y);
		this.description.setLocation(x+offset_x, y+offset_y);
	}

	public void setTextVisibleAll() {
		this.label.setVisible();
		this.description.setVisible();
	}

	public void setTextInvisibleAll() {
		this.label.setInvisible();
		this.description.setInvisible();
	}

	public void setAlignmentAll(int text_align) {
		this.label.setAlignment(text_align);
		this.description.setAlignment(text_align);
	}

	public void setValue(String value_id, double new_value) {
		this.values.put(value_id, new_value);
	}

	public void setValueIncrement(String value_id, double new_value) {
		if (this.values.get(value_id) == null ) this.values.put(value_id, new_value);
		else this.values.put(value_id, (this.values.get(value_id) + new_value));
	}

	public double getValue(String value_id) {
		if (this.values.get(value_id) == null) return 0;
		return this.values.get(value_id);
	}

	public void setCategory(String value_id, String new_cat) {
		this.categories.put(value_id, new_cat);
	}

	public String getCategory(String value_id) {
		if (this.values.get(value_id) == null) return "";
		return this.categories.get(value_id);
	}
	
}
