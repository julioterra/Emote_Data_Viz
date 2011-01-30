package com.julioterra.moodyjulio.dataviz.shapes.panel;

import java.util.ArrayList;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeText;
import com.julioterra.moodyjulio.dataviz.shapes.pie.PieSlice;

public class ButtonDropDown extends ShapeText {

	public ArrayList<ShapeText> buttons;
	public int dropdown_color;
	public boolean drop_down_active;
	
	public ButtonDropDown(int x, int y, String text, int color, int font_number, int text_align, boolean activate_mouse_over, boolean activate_mouse_press) {
		super(x, y, text, color, font_number, text_align, activate_mouse_over, activate_mouse_press);
		buttons = new ArrayList<ShapeText>();
		this.dropdown_color = this.name_font_color;
	}

	public ButtonDropDown(int x, int y, String text, int color, int font_number, int text_align) {
		super(x, y, text, color, font_number, text_align, true, true);
		buttons = new ArrayList<ShapeText>();
		this.dropdown_color = this.name_font_color;
	}
	
	
	/***************************************
	 ** ADD & REMOVE & UPDATE SUB BUTTONS 
	 ***************************************/
	
	public void addSubButton(String text, int action, int color){
		float location_y = this.location.y + (this.size.y * (buttons.size()+1));
		ShapeText new_button = new ShapeText((int)location.x, (int)location_y, text, color, this.name_font_number, this.alignment, false, false);
		buttons.add(new_button);
	}

	public void addSubButton(String text, int action){
		float location_y = this.location.y + (this.size.y * (buttons.size()+1));
		ShapeText new_button = new ShapeText((int)location.x, (int)location_y, text, this.dropdown_color, this.name_font_number, this.alignment, false, false);
		buttons.add(new_button);
	}

	/***************************************
	 ** DISPLAY & MOUSE OVER EVENTS 
	 ***************************************/

	 public void display() {
		  super.display();
		  if(mouse_over) {
			  for (int i = 0; i < buttons.size(); i++) {
				  ShapeText button = buttons.get(i);
				  button.display();
			  }
		  }
	  }

	  public void mouseOver() {
		  super.mouseOver();
		  if(mouse_over) drop_down_active = true; 
		  if(drop_down_active) {
			  int mouse_over_sub_count = 0;
			  for (int i = 0; i < buttons.size(); i++) {
				  ShapeText button = buttons.get(i);
				  button.mouseOver();
				  if (button.mouse_over) mouse_over_sub_count++;
			  }
			  if (mouse_over_sub_count == 0) drop_down_active = false;
		  }
	  }
	
}
