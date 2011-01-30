package com.julioterra.moodyjulio.dataviz.shapes.panel;

import java.util.ArrayList;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeRect;
import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;



public class Panel extends ShapeRect {

	ArrayList<ShapeText> buttons;
	
	/***************************************
	 ** CONSTRUCTORS
	 ***************************************/	

	public Panel() {
		super();
		// TODO Auto-generated constructor stub
		buttons = new ArrayList<ShapeText>();
	}

	public Panel(int x, int y, int size_x, int size_y, int color) {
		super(x, y, size_x, size_y, color);
		buttons = new ArrayList<ShapeText>();
		// TODO Auto-generated constructor stub
	}	

	/***************************************
	 ** DISPLAY FUNCTIONS - GETTER & SETTER METHODS
	 ***************************************/	

	public void display(){
		super.display();
		for (int i = 0; i < buttons.size(); i++) {
			ShapeText display_button = buttons.get(i);	 
			display_button.display();
	    }
	}
	
	/***************************************
	 ** DISPLAY FUNCTIONS - GETTER & SETTER METHODS
	 ***************************************/	

	public void mouseOver() {
//		super.mouseOver();		// this checks for mouse_over event on the panel itself
		for (int i = 0; i < buttons.size(); i++) {
			ShapeText display_button = buttons.get(i);	 
			display_button.mouseOver();
	    }		
	}
	
	public void setShiftMouseOverAll(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean text_shift_mouse_over){
		super.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
		for (int i = 0; i < buttons.size(); i++) {
			ShapeText display_button = buttons.get(i);	 
			display_button.setShiftMouseOverAll(hue_shift, saturation_shift, brightness_shift, scale_shift, text_shift_mouse_over);
	    }
	}

	/***************************************
	 ** ADD & REMOVE & UPDATE TEXT AND BUTTONS 
	 ***************************************/	

	public void addText(int x, int y, String text, int color, int font_number, int align_mode){
		ShapeText new_button = new ShapeText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, false, false);
		buttons.add(new_button);
	}

	public void addTextButton(int x, int y, String text, int color, int font_number, int align_mode, boolean mouse_over_active, boolean mouse_press_active){
		ShapeText new_button = new ShapeText((int)(location.x+x), (int)(location.y+y), text, color, font_number, align_mode, mouse_over_active, mouse_press_active);
		buttons.add(new_button);
	}

	public void removeText(int index) {
		if (index < buttons.size()) {
			buttons.remove(index);
		}		
	}

	public void setTextVisible(int index) {
		if (index < buttons.size()) {
			ShapeText updated_button = (ShapeText) buttons.get(index);
			updated_button.visible();
		}		
	}

	public void setTextInvisible(int index) {
		if (index < buttons.size()) {
			ShapeText updated_button = (ShapeText) buttons.get(index);
			updated_button.invisible();
		}		
	}

	public void updateTextLocation(int index, int x, int y){
		if (index < buttons.size()) {
			ShapeText updated_button = (ShapeText) buttons.get(index);
			updated_button.setLocation(x, y);
		}
	}

	public void updateText(int index, String text){
		if (index < buttons.size()) {
			ShapeText updated_button = (ShapeText) buttons.get(index);
			updated_button.setName(text);
		}
	}

	public void updateTextColor(int index, int color){
		if (index < buttons.size()) {
			ShapeText updated_button = (ShapeText) buttons.get(index);
			updated_button.setColorBaseARGB(color);
		}
	}

	public void updateTextFont(int index, int font_number){
		if (index < buttons.size()) {
			ShapeText updated_button = (ShapeText) buttons.get(index);
			updated_button.loadFontName(font_number, fonts_size[font_number]);
		}
	}
	
}
