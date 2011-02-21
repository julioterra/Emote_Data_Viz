package com.julioterra.moodyjulio.dataviz.shapes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import processing.core.*;

import com.julioterra.moodyjulio.dataviz.basicelements.DataVizElement;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;

public class Shape extends DataVizElement{

	public int id_number;
	public int display_layer;
	public PVector location;
	public boolean visible;
	public double scale;
	public float rotation;

	protected boolean mouse_over_active;		
	public boolean mouse_over;	
	public float size_shift_mouse_over;
	
	protected boolean mouse_clicked_active;		
	public boolean mouse_pressed;				
	public boolean mouse_clicked_toggle;			
	public float size_shift_mouse_clicked;

	protected HashMap<String, Object> mouse_over_action_objects;
	protected HashMap<String, Method> mouse_over_action_methods;
	protected HashMap<String, Object> mouse_off_action_objects;
	protected HashMap<String, Method> mouse_off_action_methods;
	protected HashMap<String, Object> mouse_pressed_action_objects;
	protected HashMap<String, Method> mouse_pressed_action_methods;

	public Shape () {
		this(0,0);
	}

	public Shape (float x, float y) {
		processing_app.registerMouseEvent(this);

		this.id_number = getShapeID();
		this.display_layer = 1;
		this.location = new PVector(x, y, display_layer);
		this.visible = true;
		this.scale = 1;
		this.rotation = 0;

		this.mouse_over_active = true;
		this.mouse_over = false;
		this.size_shift_mouse_over = 0;
		
		this.mouse_clicked_active = true;
		this.mouse_pressed = false;
		this.size_shift_mouse_clicked = 0;
		this.mouse_clicked_toggle = false;
		
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

	/*********************************************************
	 ** VISIBILITY AND MOVEMENT METHODS 
	 **/
	
	public void setVisible() {
		this.visible = true;
	}

	public void setInvisible() {
		this.visible = false;
	}

	public void setRotation(float angle_in_degrees) {
		rotation += angle_in_degrees;
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

	public void setLocation(float x, float y) {
		this.location = new PVector(x, y);
	}
	
	public PVector getLocation() {
		return location;
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
	
	  public void shiftSize(float shift_size) {
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
	  
}
