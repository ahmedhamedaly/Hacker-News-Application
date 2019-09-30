import java.awt.Menu;

import processing.core.PApplet;
import processing.core.PImage;

public class Header extends PApplet {
	private int state;
	
	PApplet parent;
	int[][] colourScheme;
	
	Button statsButton;
	DropMenu timeSortMenu;
	DropMenu chartMenu;
	PImage raaddit;
	PImage raadditLogo;
	
	
	public Header(PApplet parent, int[][] colourScheme, PImage raaddit, PImage raadditLogo)
	{
		this.parent = parent;
		this.colourScheme = colourScheme;
		state = 1;
		this.raaddit = raaddit;
		this.raadditLogo = raadditLogo;
	}
	
	public void draw() {
		//parent.stroke(0);
		//parent.strokeWeight((float) 1.5);
		parent.noStroke();
		parent.textAlign(LEFT);
		parent.fill(255);
		parent.rect(0, 0, parent.width, 80);
		parent.strokeWeight(1);
		parent.stroke(0);
		displayWebsiteTitle();
		displayMenu();
	}
	
	public void displayWebsiteTitle() {
//		parent.textSize(50);
//		parent.fill(0);
//		
//		parent.text("Raaddit", 10 + 160, 60);
//		parent.textSize(15);
		raaddit.resize(150, 70);
		parent.image(raaddit, 250, 10);
		raadditLogo.resize(70, 70);
		parent.image(raadditLogo, 165, 10);
	}
	
	public void displaySignIn()	{
		
	}
	
	public void displaySettingsButton() {
		parent.fill(colourScheme[1][0], colourScheme[1][1], colourScheme[1][2]);
		parent.textSize(30);
		parent.fill(0);
		parent.text("Stats", (float) (parent.width*0.93), (float) (parent.height*0.95));
		
		parent.textSize(15);
	}
	
	public void createSortMenu(PApplet parent, float x, float y, float w, float h, int[][] colourPalatte, String name,
			String[] items)
	{
		timeSortMenu = new DropMenu(parent, x, y, w, h, colourPalatte, name, items);
	}
	
	public void createChartMenu(PApplet parent, float x, float y, float w, float h, int[][] colourPalatte, String name,
			String[] items)
	{
		chartMenu = new DropMenu(parent, x, y, w, h, colourPalatte, name, items);
	}
	
	public void changeState(int state) {
		this.state = state;
	}
	
	public void displayMenu() {
		switch(state) {
		case 1:
			timeSortMenu.draw();
			break;
		case 3:
			chartMenu.draw();
			break;
		default:
		}
	}
	
	public void menuHover(float mX, float mY) {
		switch(state) {
		case 1:
			timeSortMenu.checkHover(mX, mY);
			break;
		case 3:
			chartMenu.checkHover(mX, mY);;
			break;
		default:
		}
	}
	
	public String menuClick(float mX, float mY) {
		String result;
		switch(state) {
		case 1:
			result = timeSortMenu.checkClick(mX, mY);
			break;
		case 3:
			result =  chartMenu.checkClick(mX, mY);
			break;
		default:
			result =  null;
		}
		return result;
		
	}
	
	public boolean clickWebsiteTitle (float mX, float mY)
	{
		if (mX >= 155 && mY >= 5 && mX <= 455 && mY <= 80)
		{
			return true;
		}
		return false;
	}
	
	public void clickMic (float mX, float mY) {
		if (mX >= 1165 && mY >= 17 && mX <= 1213 && mY <= 65) {
			if (Raaddit.micActive) {
				Raaddit.micActive = false;
				return;
			}
			Raaddit.micActive = true;
			return;
		}
	}
}
