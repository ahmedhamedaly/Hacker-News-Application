import java.awt.Stroke;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class SearchBarContainer extends PApplet{

	private PApplet parent;
	
	private float x;
	private float y;
	private float w;
	private float h;
	public SearchEngine searchEngine;
	private int[][] colourPalatte;
	private DropMenu searchDropMenu;
	private ArrayList<Post> postList;
	private String input;
	private String type;
	public SearchBarContainer(PApplet parent, float x, float y, float w, float h, int[][] colourPalatte, ArrayList<Post> postList,PImage commentImage,PImage postImage)
	{
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.colourPalatte = colourPalatte;
		this.postList = postList;
		searchEngine = new SearchEngine(parent, colourPalatte, postList, (float)(x + (w*0.15)), y, (float)(w*0.85), h,commentImage,postImage);
		searchDropMenu = new DropMenu(parent, (float) (x-w*0.08), y+15, (float) (w*0.2), h-30, colourPalatte, "", CONSTANTS.SEARCH_TERMS);
		input = "";
		type = "Posts";
		//searchDropMenu.newText();
		searchDropMenu.setNewText((float) (x  - (h-12)) , (float) (y + 14.5));
	}
	
	
	public void draw() {
		parent.fill(255);
		//parent.stroke(0);
		//parent.strokeWeight(2);
		//parent.rect(x,  y,  w,  h);
		parent.noStroke();
		searchDropMenu.draw();
		searchEngine.displaySearchEngine(input);
		//parent.fill(210);
		//parent.rect((float) (x + w*0.15 - (h-12)) , y + 15, h-30, h-30, 2);
		parent.image(Raaddit.menuIcon, (float) (x - w*0.07), y + 20);
	}
	
	public void menuHover(float mX, float mY) {
		searchDropMenu.checkHover(mX, mY);
	}
	
	public void menuClick(float mX, float mY)
	{
		String menuInput = searchDropMenu.checkClick(mX, mY);
		type = (menuInput!=null)?menuInput:type;
	}
	
	public void searchEngineClicked(float mX,float mY)
	{
		searchEngine.isClicked(mX, mY);
	}
	
	public boolean searchEngineActive() {
		return searchEngine.isActive();
	}
	
	public void setInput(String input)
	{
		this.input = input;
	}
	
	public void sendInput(String input)
	{
		switch(type) {
			case "Posts":
				searchEngine.sendString(input);
				break;
			case "User":
				searchEngine.sendString("username:"+input);
				break;
			//case "Front Page":
				//searchEngine.sendString("");
			default:
		}
	}
	
	public void setSearchActive() {
		searchEngine.setActive();
	}
	
	public void nextPage() {
		searchEngine.nextPage();
	}
	
	public void previousPage() {
		searchEngine.previousPage();
	}
	
	public boolean isMenuOpen()
	{
		return searchDropMenu.getOpen();
	}
			
}
