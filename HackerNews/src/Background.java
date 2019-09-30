import processing.core.*;

public class Background extends PApplet{

	int[][] colourScheme;
	PApplet parent;
	
	public Background(PApplet parent, int[][] colourScheme)
	{
		this.colourScheme = colourScheme;
		this.parent = parent;
	}
	
	public void draw() {
		//parent.background(colourScheme[3][0], colourScheme[3][1], colourScheme[3][2]);
		parent.background(255);
	}
	
	
}
