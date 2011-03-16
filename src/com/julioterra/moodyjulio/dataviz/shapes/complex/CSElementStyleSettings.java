package com.julioterra.moodyjulio.dataviz.shapes.complex;

public interface CSElementStyleSettings {

	public void removeElement(String button_id);
	public int getElementCount();
	
	// VISIBILITY SETTINGS
	public void setElementAllVisible();
	public void setElementAllInvisible();
	public void setElementVisible(String button_id);
	public void setElementInvisible(String button_id);

	// LOCATION SETTINGS
	public void setElementAllLocation(float x, float y);
	public void setElementLocation(String button_id, int x, int y);
	
	// SIZE SETTINGS
	public void setElementAllSize(float x, float y);
	public void setElementAllSizeActive(float x, float y);
	public void resetElementAllSize();
	public void setElementSize(String button_id, float x, float y);
	public void setElementSizeActive(String button_id, float x, float y);
	public void resetElementSize(String button_id);
	
	// ROTATION SETTINGS	
	public void setElementAllRotation(float angle_in_degrees);
	public void setElementAllRotationActive(float angle_in_degrees);
	public void resetElementAllRotation();	
	public void setElementRotation(String button_id, float angle_in_degrees);
	public void setElementRotationActive(String button_id, float angle_in_degrees);
	public void resetElementRotation(String button_id);	

	// COLOR SETTINGS
	public void setElementAllColor(int argb);
	public void setElementAllColorActive(int argb);	
	public void resetElementAllColor();
	public void setElementColor(String button_id, int argb);
	public void setElementColorActive(String button_id, int argb);
	public void resetElementColor(String button_id);
	
	// SET VALUE
	public void setElementValue(String button_id, String value_id, double value);
	public void setElementValueIncrement(String button_id, String value_id, double value);
	public double getElementValue(String button_id, String value_id);

	// SET CATEGORY
	public void setElementCategory(String button_id, String value_id, String category);
	public String getElementCategory(String button_id, String value_id);

	// SET LABEL AND DESCRIPTION TEXT
	public void setElementLabel(String button_id, String name);
	public void setElementDescription(String button_id, String description);

	// SET LABEL AND DESCRIPTION LOCATION
	public void setElementAllTextLocation(float x, float y, float offset_x, float offset_y);
	public void setElementAllLabelLocation(float x, float y);
	public void setElementAllDescriptionLocation(float x, float y);
	public void setElementTextLocation(String button_id, float x, float y, float offset_x, float offset_y);
	public void setElementLabelLocation(String button_id, float x, float y);
	public void setElementDescriptionLocation(String button_id, float x, float y);

	// SET LABEL AND DESCRIPTION FONTS
	public void loadElementAllFont(int font_number, int font_size);
	public void loadElementAllLabelFont(int font_number, int font_size);
	public void loadElementAllDescriptionFont(int font_number, int font_size);
	public void loadElementFont(String button_id, int font_number, int font_size);
	public void loadElementLabelFont(String button_id, int font_number, int font_size);
	public void loadElementDescriptionFont(String button_id, int font_number, int font_size);

	// SET LABEL AND DESCRIPTION SIZE
	public void setElementAllFontSize(int font_size);
	public void setElementAllLabelFontSize(int font_size);
	public void setElementAllDescriptionFontSize(int font_size);
	public void setElementFontSize(String button_id, int font_size);
	public void setElementLabelFontSize(String button_id, int font_size);
	public void setElementDescriptionFontSize(String button_id, int font_size);

	// SET LABEL AND DESCRIPTION COLORS
	public void setElementAllFontColor(int font_color);
	public void setElementAllLabelFontColor(int font_color);
	public void setElementAllDescriptionFontColor(int font_color);
	public void setElementFontColor(String button_id, int font_color);
	public void setElementLabelFontColor(String button_id, int font_color);
	public void setElementDescriptionFontColor(String button_id, int font_color);
	
}
