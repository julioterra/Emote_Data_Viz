package com.julioterra.moodyjulio.dataviz.shapes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import processing.core.*;
import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;

public class Shape extends DataVizElement{

	PApplet test_app;
	public int id_number;
	public int display_layer;
	public PVector location;
	public boolean visible;
	public double scale;
	public float rotation;

	protected boolean mouse_over_active;		// not yet integrated
	public boolean mouse_over;	
	public float size_shift_mouse_over;
	
	protected boolean mouse_clicked_active;		
	public boolean mouse_pressed;				
	public boolean mouse_click_toggle;			
	public PVector mouse_pressed_location;			
	public float size_shift_mouse_clicked;

	public HashMap<String, Object> mouse_over_action_objects;
	public HashMap<String, Method> mouse_over_action_methods;
	public HashMap<String, Object> mouse_off_action_objects;
	public HashMap<String, Method> mouse_off_action_methods;
	public HashMap<String, Object> mouse_pressed_action_objects;
	public HashMap<String, Method> mouse_pressed_action_methods;

	public int alignment_text;
	public String title;
	public String description;
	public PFont font_title;
	public PFont font_description;
	protected boolean font_loaded_description;
	protected boolean font_loaded_title;
	public PVector location_title;
	public PVector location_description;
	public int font_size_title_active;
	public int font_size_description_active;
	public float font_size_title_base;
	public float font_size_description_base;
	public int font_color_title_active;
	public int font_color_description_active;
	public int font_color_title_base;
	public int font_color_description_base;
	public int font_number_title;
	public int font_number_description;
	public boolean visible_title;
	public boolean visible_description;
	protected boolean text_toggle_mouse_over;
	protected boolean text_toggle_mouse_clicked;


	public Shape () {
		this(0,0);
	}

	public Shape (int id, float x, float y) {
		this(0,0);
	}

	public Shape (float x, float y) {
		test_app = processing_app;
		processing_app.registerMouseEvent(this);
		this.id_number = getIDColorNumber();
		this.display_layer = 0;
		this.location = new PVector(x, y);
		this.visible = true;
		this.scale = 1;
		this.rotation = 0;

		this.mouse_over_active = true;
		this.mouse_clicked_active = true;

		this.size_shift_mouse_over = 0;
		this.size_shift_mouse_clicked = 0;
		
		this.alignment_text = PApplet.LEFT;
		this.visible_description = false;
		this.visible_title = false;
		
		this.location_title = new PVector(0,0);
		this.font_loaded_title = false;
		this.title = "";
		this.font_color_title_active = 0;
		this.font_color_title_base = 0;

		this.location_description = new PVector(0,0);
		this.font_loaded_description = false;
		this.description = "";
		this.font_color_description_active = 0;
		this.font_color_description_base = 0;
		
		this.text_toggle_mouse_over = false;
		this.text_toggle_mouse_clicked = false;

		this.mouse_pressed_location = new PVector(-1000,-1000);
		mouse_over_action_objects = new HashMap<String, Object>();
		mouse_over_action_methods = new HashMap<String, Method>();
		mouse_off_action_objects = new HashMap<String, Object>();
		mouse_off_action_methods = new HashMap<String, Method>();
		mouse_pressed_action_objects = new HashMap<String, Object>();
		mouse_pressed_action_methods = new HashMap<String, Method>();

	}
	
	/*********************************************************
	 ** DISPLAY METHODS 
	 **/
	
	public void display() {
		if (visible) {
			processing_app.smooth();
			processing_app.noStroke();
			// add text drawing to this method
			this.displayText();
		}
	}

	public void displayBuffer() {
		if (visible) {
			processing_app_buffer.beginDraw();
			processing_app_buffer.smooth();
			processing_app_buffer.noStroke();
			processing_app_buffer.endDraw();
		}
	}

	public void displayText() {
		if (this.font_loaded_title && this.visible_title) {
			processing_app.fill(this.font_color_title_active);
			processing_app.textFont(this.font_title, this.font_size_title_active);
			processing_app.textAlign(alignment_text);
			processing_app.text(this.title, this.location_title.x, this.location_title.y);
		}
		if (this.font_loaded_description && this.visible_description) {
			processing_app.fill(this.font_color_description_active);
			processing_app.textFont(this.font_description, this.font_size_description_active);
			processing_app.textAlign(alignment_text);
			processing_app.text(this.description, this.location_description.x, this.location_description.y);
		}

	}
	

	/*********************************************************
	 ** VISIBILITY AND MOVEMENT METHODS 
	 **/
	
	public void visible() {
		this.visible = true;
	}

	public void invisible() {
		this.visible = false;
	}

	public void rotate(float rotation_angle) {
		rotation += rotation_angle;
	}

	public float getRotation() {
		return rotation;
	}

	public void move(float x, float y) {
		this.location = new PVector(x, y);
	}

	public void setScale(float new_scale) {
		this.scale = new_scale;
	}

	public void setScaleRel(float new_scale_perecent) {
		this.scale = this.scale * new_scale_perecent;
	}

	public double getScale() {
		return scale;
	}

	
	/*********************************************************
	 ** MOUSE EVENT METHODS 
	 **/

	public void mouseEvent(MouseEvent event) {
	    switch (event.getID()) {
	    case MouseEvent.MOUSE_MOVED:
//	    	if (id_number == -1) PApplet.println("mouse over " + id_number);
    		this.mouseOver();
	    case MouseEvent.MOUSE_PRESSED:
//	    	if (id_number == -1) PApplet.println("mouse pressed " + id_number);
	    	this.mousePressed();
	    	break;
	    case MouseEvent.MOUSE_DRAGGED:
//	    	if (id_number == -1) PApplet.println("mouse dragged " + id_number);
	    	this.mouseDragged();
	    	break;
	    case MouseEvent.MOUSE_RELEASED:
//	    	if (id_number == -1) PApplet.println("mouse released " + id_number);
	    	this.mouseReleased();
	    	break;
	    case MouseEvent.MOUSE_CLICKED:
//	    	if (id_number == -1) PApplet.println("mouse clicked " + id_number);
	    	this.mouseClicked();
	    	break;
	    }
	}
	
	public void mouseOver() {
	}
	
	public void mousePressed(){
		if (!this.mouse_pressed && mouse_over) { this.mouse_pressed = true; }
	}

	public void mouseDragged() {
	}

	public void mouseReleased() {
		this.mouse_pressed = false;
	}

	public void mouseClicked(){
		if(mouse_over) this.mouseClickedActions();
	}

	public void mouseOverActions() {		
	}

	public void mouseOffActions() {		
	}

	public void mouseClickedActions() {		
	}

	public void addMouseOverAction(String action_id, Object object, String method_name){
	    if (method_name != null && !method_name.equals("") && object != null){
	        try{
	    	  	Method m = object.getClass().getMethod(method_name, null);
	        	mouse_over_action_objects.put(action_id, object);
	        	mouse_over_action_methods.put(action_id, m);
	        	mouse_over_active = true;
	        } catch (SecurityException e){
	    	    e.printStackTrace();
	    	    PApplet.println("issue setting mouse over action");
	        } catch (NoSuchMethodException e){
	    	    e.printStackTrace();
	    	    PApplet.println("issue setting mouse over action");
	      	}
	    }
	}

	protected void mouseOverExternalActions(){
		if (mouse_over_action_objects.size() == 0 || mouse_over_action_methods.size() == 0) return;
	    try{
			Iterator<Map.Entry<String, Object>> object_iterator = mouse_over_action_objects.entrySet().iterator();
			Iterator<Map.Entry<String, Method>> method_iterator = mouse_over_action_methods.entrySet().iterator();
			while (object_iterator.hasNext() || method_iterator.hasNext()) {
				Map.Entry<String, Object> object_entry = (Map.Entry<String, Object>) object_iterator.next();
				Map.Entry<String, Method> method_entry = (Map.Entry<String, Method>) method_iterator.next();
				Object object = object_entry.getValue();	 
				Method method = method_entry.getValue();	 
				method.invoke(object, null);
			}
	    } catch (Exception e){
    	    PApplet.println("issue getting the mouse over action to work");
	        e.printStackTrace();
	    }
	}

	public void addMouseOffAction(String id, Object object, String method){
	    if (method != null && !method.equals("") && object != null){
	        try{
	    	  	Method m = object.getClass().getMethod(method, null);
	        	mouse_off_action_objects.put(id, object);
	        	mouse_off_action_methods.put(id, m);
	        	mouse_over_active = true;
	        } catch (SecurityException e){
	    	    e.printStackTrace();
	    	    PApplet.println("issue setting mouse over action");
	        } catch (NoSuchMethodException e){
	    	    e.printStackTrace();
	    	    PApplet.println("issue setting mouse over action");
	      	}
	    }
	}

	protected void mouseOffExternalActions(){
		if (mouse_off_action_objects.size() == 0 || mouse_off_action_methods.size() == 0) return;
	    try{
			Iterator<Map.Entry<String, Object>> object_iterator = mouse_off_action_objects.entrySet().iterator();
			Iterator<Map.Entry<String, Method>> method_iterator = mouse_off_action_methods.entrySet().iterator();
			while (object_iterator.hasNext() || method_iterator.hasNext()) {
				Map.Entry<String, Object> object_entry = (Map.Entry<String, Object>) object_iterator.next();
				Map.Entry<String, Method> method_entry = (Map.Entry<String, Method>) method_iterator.next();
				Object object = object_entry.getValue();	 
				Method method = method_entry.getValue();	 
				method.invoke(object, null);
			}
	    } catch (Exception e){
    	    PApplet.println("issue getting the mouse over action to work");
	        e.printStackTrace();
	    }
	}

	public void addMouseClickedAction(String id, Object object, String method){
	    if (method != null && !method.equals("") && object != null){
	        try{
	    	  	Method m = object.getClass().getMethod(method, null);
	        	mouse_pressed_action_objects.put(id, object);
	        	mouse_pressed_action_methods.put(id, m);
	        	mouse_clicked_active = true;
	        } catch (SecurityException e){
	    	    e.printStackTrace();
	    	    PApplet.println("issue setting mouse click action");
	        } catch (NoSuchMethodException e){
	    	    e.printStackTrace();
	    	    PApplet.println("issue setting mouse click action");
	      	}
	    }
	}

	protected void mouseClickedExternalActions(){
		if (mouse_pressed_action_objects.size() == 0 || mouse_pressed_action_methods.size() == 0) return;
	    try{
			Iterator<Map.Entry<String, Object>> object_iterator = mouse_pressed_action_objects.entrySet().iterator();
			Iterator<Map.Entry<String, Method>> method_iterator = mouse_pressed_action_methods.entrySet().iterator();
			while (object_iterator.hasNext() || method_iterator.hasNext()) {
				Map.Entry<String, Object> object_entry = (Map.Entry<String, Object>) object_iterator.next();
				Map.Entry<String, Method> method_entry = (Map.Entry<String, Method>) method_iterator.next();
				Object object = object_entry.getValue();	 
				Method method = method_entry.getValue();	 
				method.invoke(object, null);
			}
	    } catch (Exception e){
    	    PApplet.println("issue getting the mouse clicked action to work");
	        e.printStackTrace();
	    }
	}

	public void setMouseOverActive(boolean mouse_over_active) {
		this.mouse_over_active = mouse_over_active;
	}

	public void setMouseClickedActive(boolean mouse_press_active) {
		this.mouse_clicked_active = mouse_press_active;
	}

	public boolean isMouseOverActive() {
		return mouse_over_active;
	}

	public boolean isMouseClickedActive() {
		return mouse_clicked_active;
	}

	  /*********************************************************
	   ** SCALE SHIFT METHODS 
	   **/
	
	  public void shiftSize(float scale_shift) {
	  }

	  public void shiftScaleReset() {
	  }

	  public void shiftScaleResetToBase() {
	  }

	  public void setSizeShiftMouseOver(float scale_shift) {
		  // input parameter should range from 0 - 1;
		  this.size_shift_mouse_over = scale_shift;
	  }

	  public void setSizeShiftMousePress(float scale_shift) {
		  // input parameter should range from 0 - 1;
		  this.size_shift_mouse_clicked = scale_shift;
	  }
	  
	/*********************************************************
	 ** TEXT & FONT METHODS 
	 **/

	public void setName(String new_name) {
		this.title = new_name;
	}

	public void setDescription(String new_name) {
		this.description = new_name;
	}

	public String getName() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public void loadFontAll(int new_font_number, int size, float font_scale) {
		this.loadFontTitle(new_font_number, size);
		this.loadFontDescription(new_font_number, (int) (size*font_scale));
	}

	public void loadFontTitle(int new_font_number, int size) {
		this.font_number_title = new_font_number;
		this.font_title = DataVizElement.fonts[new_font_number];
		this.setFontSizeTitle(size);
		this.font_loaded_title = true;
	}

	public void loadFontDescription(int new_font_number, int size) {
		this.font_number_description = new_font_number;
		this.font_description = DataVizElement.fonts[new_font_number];
		this.setFontSizeDescription(size);
		this.font_loaded_description = true;
	}

	public void setFontColorAll(int font_color) {
		this.setFontColorName(font_color);
		this.setFontColorDescription(font_color);
	}

	public void setFontColorName(int font_color) {
		this.font_color_title_active = font_color;
		this.font_color_title_base = this.font_color_title_active;
	}

	public void setFontColorDescription(int font_color) {
		this.font_color_description_active = font_color;
		this.font_color_description_base = this.font_color_description_active;
	}

	public void setFontSizeAll(float size) {
		this.setFontSizeDescription(size);
		this.setFontSizeTitle(size);
	}

	public void setFontSizeTitle(float size) {
		this.font_size_title_active = (int)(size*this.scale);
		this.font_size_title_base = size;
	}

	public void setFontSizeDescription(float size) {
		this.font_size_description_active = (int)(size*this.scale);
		this.font_size_description_base = size;
	}

	public PVector getLocation() {
		return location;
	}

	public void setLocation(int x, int y) {
		this.location = new PVector(x, y);
	}

	public void setTextLocationNameDescription(float x, float y, float offset_x, float offset_y) {
		this.setTextLocationName(x, y);
		this.setTextLocationDescriptionRel(offset_x, (float)(offset_y) );
	}

	public void setTextLocationName(float x, float y) {
		this.location_title = new PVector(x, y);
	}

	public void setTextLocationDescription(float x, float y) {
		this.location_description = new PVector(x, y);
	}

	public void setTextLocationDescriptionRel(float x, float y) {
		this.location_description = new PVector(this.location_title.x+x, this.location_title.y+y);
	}
	
	public void setTextVisibleNameDescription() {
		this.setTextVisibleName();
		this.setTextVisibleDescription();
	}

	public void setTextInvisibleNameDescription() {
		this.setTextInvisibleName();
		this.setTextInvisibleDescription();
	}

	public void shiftOffTextMouseOver() {
		if(this.text_toggle_mouse_over) {
			this.setTextInvisibleNameDescription();
		}
	}

	public void setTextVisibleName() {
		this.visible_title = true;
	}

	public void setTextInvisibleName() {
		this.visible_title = false;
	}

	public void setTextVisibleDescription() {
		this.visible_description = true;
	}
	
	public void setTextInvisibleDescription() {
		this.visible_description = false;
	}


}
