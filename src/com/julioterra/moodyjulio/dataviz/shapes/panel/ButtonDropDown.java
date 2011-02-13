package com.julioterra.moodyjulio.dataviz.shapes.panel;

import java.util.ArrayList;
import processing.core.PApplet;
import com.julioterra.moodyjulio.dataviz.shapes.pie.PieSlice;

public class ButtonDropDown extends ButtonText {

	public ArrayList<ButtonText> buttons;
	public int dropdown_color;
	public boolean drop_down_active;
	protected long last_mouse_over;
	protected long drop_down_pause = 300;
	
	public ButtonDropDown(int x, int y, String text, int color, int font_number, int text_align, boolean activate_mouse_over, boolean activate_mouse_press) {
		super(x, y, text, color, font_number, text_align, activate_mouse_over, activate_mouse_press);
		buttons = new ArrayList<ButtonText>();
		this.dropdown_color = this.font_color_title;
	}

	public ButtonDropDown(int x, int y, String text, int color, int font_number, int text_align) {
		super(x, y, text, color, font_number, text_align, true, true);
		buttons = new ArrayList<ButtonText>();
		this.dropdown_color = this.font_color_title;
	}
	
	
	/***************************************
	 ** ADD & REMOVE & UPDATE SUB BUTTONS 
	 ***************************************/
	
	public void addSubButton(String text, int action, int color){
		float location_y = this.location.y + (this.size.y * (buttons.size()+1));
		ButtonText new_button = new ButtonText((int)location.x, (int)location_y, text, color, this.font_number_title, this.alignment_text, true, false);
		buttons.add(new_button);
	}

	public void addSubButton(String text, int action){
		float location_y = this.location.y + (this.size.y * (buttons.size()+1));
		ButtonText new_button = new ButtonText((int)location.x, (int)location_y, text, this.dropdown_color, this.font_number_title, this.alignment_text, true, false);
		buttons.add(new_button);
	}

	/***************************************
	 ** DISPLAY & MOUSE OVER EVENTS 
	 ***************************************/

	 public void display() {
		  super.display();
		  if(drop_down_active) {
			  for (int i = 0; i < buttons.size(); i++) {
				  ButtonText button = buttons.get(i);
				  button.display();
			  }
		  }
	  }

	  public void mouseOver() {
		  super.mouseOver();
		  if(this.mouse_over) {
			  drop_down_active = true; 
			  last_mouse_over = processing_app.millis();
		  }
		  if(drop_down_active) {
			  for (int i = 0; i < buttons.size(); i++) {
				  ButtonText button = buttons.get(i);
				  button.mouseOver();
				  if (button.mouse_over) { last_mouse_over = processing_app.millis(); }
			  }
			  if (!this.mouse_over && (processing_app.millis() - last_mouse_over > drop_down_pause)) {
				  drop_down_active = false;
			  }
		  }
	  }
	
}
