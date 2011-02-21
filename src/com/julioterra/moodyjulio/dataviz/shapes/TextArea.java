package com.julioterra.moodyjulio.dataviz.shapes;

public class TextArea extends ShapeRect{
	
	public TextArea() {
		super();
	}

	public TextArea(int x, int y, int size_x, int size_y, int color) {
		super(x, y, size_x, size_y, color);
	}

	public TextArea(int x, int y) {
		super(x, y);
	}

	public void displayText(String display_text) {
		super.display();
	}
}
