import java.io.ObjectInputStream.GetField;

import javax.xml.soap.Text;

import processing.core.PApplet;

public class TextBox extends PApplet{
	private float x;
	private float y;
	private float w;
	private float h;
	
	private PApplet parent;
	private String placeHolder;
	private int[][] colorPalatte;
	private String text;
	private int textSize;
	private boolean active;
	private boolean permanent;
	private String textSaved;
	
	public TextBox(PApplet parent, float x, float y, float w, float h, String placeHolder, int[][] colorPalatte, int textSize)
	{
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		
		this.parent = parent;
		this.placeHolder = placeHolder;
		this.colorPalatte = colorPalatte;
		text = placeHolder;
		this.textSize = textSize;
		active  = false;
		permanent = false;
		textSaved = "";
	}
	
	public void draw() {
		parent.fill(255);
		parent.rect(x, y, w, h);
		parent.textAlign(LEFT);
		parent.textSize(textSize);
		parent.fill(0);
		parent.text(text, (float)(x + w * 0.05), (float) (y + h*0.65));
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void updateText(String input) {
		text = input;
	}
	
	public void setActive() {
		if (active)
		{
			active = false;
			//if (!permanent)
		//	text = placeHolder;
		}
		else {
			active = true;
			//if (!permanent)
			text = "";
		}
	}
	
	public String getText() {
		return text;
	}
	
	public void click(float mX, float mY) {
		if (mX >= x && mY >= y && mX <= x + w && mY <= y + h)
		{
			setActive();
		}
		else {
			active = false;
			//if (!permanent)
			//text = placeHolder;
		}
	}
	
	public void setTextPermanent() {
		permanent = true;
	}
	
	public void updateTextSecure(String input) {
		text = "";
		for(int i = 0; 	i< input.length(); i++)
		{
			text += "*";
		}
		textSaved = input;
	}
	
	public void resetBox()
	{
		text = placeHolder;
		textSaved = "";
	}
	
	public String getSecure()
	{
		return textSaved;
	}
	
	
}
