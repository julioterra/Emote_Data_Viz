package com.julioterra.moodyjulio.dataviz.shapes.panel;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import processing.core.PApplet;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeRectText;
import com.julioterra.moodyjulio.dataviz.shapes.pie.PieSlice;

public class ButtonDropDown extends ShapeRectText {

	public HashMap <String, ShapeRectText> button_map;
	public boolean drop_down_active;
	protected long last_mouse_over;
	protected static long drop_down_pause = 300;
	
	public ButtonDropDown(int x, int y, int width, int height, String text, int color, int font_number, int text_align) {
		super(x, y, width, height, text, color, font_number, text_align, 0, true, true);
		button_map = new HashMap <String, ShapeRectText>();
	}
	
	
	/***************************************
	 ** ADD & REMOVE & UPDATE SUB BUTTONS 
	 ***************************************/
	
	public void addSubButton(String text, int color, Object object, String method_name){
		float location_y = this.location.y + (this.size_active.y * (button_map.size()+1));
		ShapeRectText new_button = new ShapeRectText((int)location.x, (int)location_y, (int)this.size_base.x, (int)this.size_base.y, text, color, this.font_number_label, this.alignment_text, PApplet.LEFT, true, true);
		new_button.addMouseClickedAction(""+button_map.size(), object, method_name);
		button_map.put(text, new_button);
	}

	public void addSubButton(String text, Object object, String method_name){
		float location_y = this.location.y + (this.size_active.y * (button_map.size()+1));
		ShapeRectText new_button = new ShapeRectText((int)location.x, (int)location_y,(int)size_base.x, (int)size_base.y, text, this.color_base, this.font_number_label, this.alignment_text, PApplet.LEFT, true, true);
		new_button.addMouseClickedAction(""+button_map.size(), object, method_name);
		button_map.put(text, new_button);
	}

	/***************************************
	 ** DISPLAY & MOUSE OVER EVENTS 
	 ***************************************/

	 public void display() {
		  super.display();
		  if(drop_down_active) {
				Iterator<Map.Entry<String, ShapeRectText>> button_it = button_map.entrySet().iterator();
				while (button_it.hasNext()) {
					Map.Entry<String, ShapeRectText> button_entry = (Map.Entry<String, ShapeRectText>) button_it.next();
					ShapeRectText button = button_entry.getValue();	 
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
				Iterator<Map.Entry<String, ShapeRectText>> button_it = button_map.entrySet().iterator();
				while (button_it.hasNext()) {
					Map.Entry<String, ShapeRectText> button_entry = (Map.Entry<String, ShapeRectText>) button_it.next();
					ShapeRectText button = button_entry.getValue();	 
					button.mouseOver();
					if (button.mouse_over) { last_mouse_over = processing_app.millis(); }
				}
			  if (!this.mouse_over && (processing_app.millis() - last_mouse_over > drop_down_pause)) {
				  drop_down_active = false;
			  }
		  }
	  }
	  
		/***************************************
		 ** NEW METHODS TO COME:
		 ** 1. scale
		 ** 2. size 
		 ***************************************/

	
}
