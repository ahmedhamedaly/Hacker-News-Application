import java.awt.Color;

import processing.core.PApplet;

public class Button extends PApplet{

	private String text;
	private float xPos;
	private float yPos;
	private float width;
	private float height;
	private float margin = 65;
	private int textSize;
	int[][] colourPalatte;
	int buttonColour;
	int borderColour;
	
	PApplet parent;
	public Button(PApplet parent, String text, float x, float y, float w, float h, int[][] palatte, int textSize)
	{
		this.parent = parent;
		this.text = text;
		xPos = x;
		yPos = y;
		width = w;
		height = h;
		colourPalatte = palatte;
		this.textSize = textSize;
		borderColour = color(0);
		buttonColour = color(colourPalatte[0][0], colourPalatte[0][1], colourPalatte[0][2]);
		//buttonColour = color(100, 100, 100);
	}
	
	public void draw() {
		parent.stroke(borderColour);
		parent.fill(buttonColour);
		parent.rect(xPos, yPos, width, height, 20);
		parent.fill(0);
		parent.textAlign(CENTER);
		parent.textSize(textSize);
		parent.text(text,xPos + width/2, yPos + height/2 + textSize/2 - 2);
	}
	
	public boolean clicked(float mX, float mY)
	{
		if (mX >= xPos && mY >= yPos && mX <= xPos + width && mY <= height + yPos)
		{
			return true;
		}
		return false;
	}
	
	public void hover(float mX, float mY)
	{
		if (mX >= xPos && mY >= yPos && mX <= xPos + width && mY <= height + yPos)
		{
			buttonColour = color(colourPalatte[2][0], colourPalatte[2][1], colourPalatte[2][2]);
			//borderColour = color(255);
		}
		else {
			buttonColour = color(colourPalatte[0][0], colourPalatte[0][1], colourPalatte[0][2]);
			//borderColour = color(0);
		}
	}
	
	
}
