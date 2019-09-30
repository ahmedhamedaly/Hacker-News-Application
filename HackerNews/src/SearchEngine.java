
//Search Engine Widget
//Creates a widget that allows  a query to be searched when enter is pressed.
import java.awt.List;
import java.util.ArrayList;

import processing.core.*;

public class SearchEngine extends PApplet {
	static Boolean active = false;
	PApplet parent;
	int[][] colourScheme;
	ArrayList<Post> postList;
	private float x;
	private float y;
	private float w;
	private float h;

	public SearchEngine(PApplet parent, int[][] colourScheme, ArrayList<Post> postList, float x, float y, float w,
			float h, PImage commentImage, PImage postImage) {
		this.parent = parent;
		this.colourScheme = colourScheme;
		this.postList = postList;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

	}

	public void sendString(String inputString) {
		Raaddit.query.newQuery(inputString);
	}

	public void displaySearchEngine(String search) {
		parent.fill(210);
		parent.noStroke();

		parent.rect(x, y + 15, w, h - 30, 2);
		// menu
		
		
		// mic

		if (Raaddit.micActive)
			parent.fill(0, 127, 0);
		else
			parent.fill(210);

		parent.rect(x + 705 - 120, y + 15, h - 30, h - 30, 2);
		parent.image(Raaddit.voice, x + 712 - 120, y + 25);

		// Raaddit.searchImage.resize(700, 50);
		 //parent.image(Raaddit.searchImage, x, y + 15);
		if (!active) {
			String searchHelp = "Search";
			parent.textSize((float) (h * 0.2));
			parent.fill(0);
			parent.textAlign(LEFT);
			parent.text(searchHelp, x + 10, y + h / 2 + 5);
		} else {
			String presentation = search;
			if (search.toCharArray().length>63) {
				int index = search.toCharArray().length - 63;
				presentation = search.substring(index);
			}
			parent.fill(0);
			parent.textSize((float) (h * 0.2));
			parent.textAlign(LEFT);
			parent.text(presentation, x + 10, y + h / 2 + 5);
		}
	}

	public boolean isActive() {
		return active;
	}

	public void isClicked(float mX, float mY) {
		if (mX >= x && mY >= y && mX <= x + w && mY <= h + y) {
			active = true;
		} else {
			active = false;
		}
	}

	public void setActive() {
		active = false;
	}

	public void nextPage() {
		Raaddit.query.nextPage();
	}

	public void previousPage() {
		Raaddit.query.previousPage();
	}
}
