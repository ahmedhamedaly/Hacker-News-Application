import processing.core.*;


public class Opening extends PApplet{

	PApplet parent;
	PImage raaddit;
	int i = 600;
	int x = 590;
	int y = 400;
	int w = 750;
	int h = 300;
	int yUp = 1080;
	int yDown = -1080;
	boolean finishedOpening = false;
	
	public Opening(PApplet parent, PImage opening)
	{
		this.parent = parent;
		raaddit = opening;
	}
	
	public void draw() {
		if (Raaddit.currentScreen == 10) {
			parent.background(255);
			parent.image(raaddit, x, y, w, h);
		}
		
		if ((i += 15) < 1300) {
		    parent.fill(255);
		    parent.noStroke();
		    
		    parent.rect(i, 400, 800, 400);
		    
		  } else {
			parent.noStroke();
		    parent.fill(0);
		    parent.fill(CONSTANTS.COLOUR_PALATTE_GREYS[1][0],CONSTANTS.COLOUR_PALATTE_GREYS[1][1],CONSTANTS.COLOUR_PALATTE_GREYS[1][2]);
		    parent.rect(0*parent.width/5, yUp, parent.width/5, parent.height);
		    parent.fill(CONSTANTS.COLOUR_PALATTE_GREYS[2][0],CONSTANTS.COLOUR_PALATTE_GREYS[2][1],CONSTANTS.COLOUR_PALATTE_GREYS[2][2]);		    
		    parent.rect(1*parent.width/5, yDown, parent.width/5, parent.height);
		    parent.fill(CONSTANTS.COLOUR_PALATTE_GREYS[1][0],CONSTANTS.COLOUR_PALATTE_GREYS[1][1],CONSTANTS.COLOUR_PALATTE_GREYS[1][2]);
		    parent.rect(2*parent.width/5, yUp, parent.width/5, parent.height);
		    parent.fill(CONSTANTS.COLOUR_PALATTE_GREYS[2][0],CONSTANTS.COLOUR_PALATTE_GREYS[2][1],CONSTANTS.COLOUR_PALATTE_GREYS[2][2]);
		    parent.rect(3*parent.width/5, yDown, parent.width/5, parent.height);
		    parent.fill(CONSTANTS.COLOUR_PALATTE_GREYS[1][0],CONSTANTS.COLOUR_PALATTE_GREYS[1][1],CONSTANTS.COLOUR_PALATTE_GREYS[1][2]);
		    parent.rect(4*parent.width/5, yUp, parent.width/5, parent.height);
		    
		    yUp -= 15;
		    yDown += 15;
		    
		    if (yDown >= 0)
		      Raaddit.currentScreen = 1;
		    
		    if (yUp < -1080)
		    	finishedOpening = true;
		    	
		  }
	}
	
	
}
