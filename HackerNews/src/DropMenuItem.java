import processing.core.PApplet;

public class DropMenuItem extends PApplet{

	private float x;
	private float y;
	private float h;
	private float w;
	private int[][] colourPalatte;
	private int baseColour;
	private int currentColour;
	private int textSize;
	private String itemName;
	PApplet parent;
	public DropMenuItem(PApplet parent, float x, float y, float w, float h, int currentColour, String itemName, int textSize)
	{
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.baseColour = color(210);
		this.currentColour = baseColour;
		this.itemName = itemName;
		this.textSize = textSize;
	}
	
	public void draw() {
		parent.fill(currentColour);
		parent.rect(x, y, w, h);
		parent.textAlign(CENTER);
		parent.textSize(textSize);
		parent.fill(0);
		parent.text(itemName, x + w/2, (float) (y + h*0.6));
	}
	
	public void checkHover(float mX, float mY) {
		
			if (mX >= x && mX <= x + w && mY >= y && mY <= y + h) {
				currentColour = color(160, 160, 170);
			} else {
				currentColour = baseColour;
			}
		
	}
	
	public String checkClick(float mX, float mY) {
		
		if (mX >= x && mX <= x + w && mY >= y && mY <= y + h) {
			return itemName;
		}
		return null;
	
}
	
	
}
