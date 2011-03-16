package com.julioterra.moodyjulio.dataviz.shapes.complex;

import com.julioterra.moodyjulio.dataviz.shapes.ShapeViz;

public interface CSMouseEventSettings {

	ShapeViz elements_style = new ShapeViz();

	void setElementAllShiftMouseOver(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_over, boolean description_shift_mouse_over);
	void setElementAllShiftMouseClicked(float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked);	
	
	void setElementShiftMouseOver(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float radius_shift, boolean label_shift_mouse_over, boolean description_shift_mouse_over);
	void setElementShiftMouseClicked(String button_id, float hue_shift, float saturation_shift, float brightness_shift, float scale_shift, boolean label_shift_mouse_clicked, boolean description_shift_mouse_clicked);

	void setElementAddMouseOverAction(String button_id, String action_id, Object object, String method_name);
	void setElementAddMouseOffAction(String button_id, String action_id, Object object, String method_name);	
	void setElementAddMouseClickedAction(String button_id, String action_id, Object object, String method_name);
	
}
