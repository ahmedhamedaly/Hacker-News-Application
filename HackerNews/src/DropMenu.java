import processing.core.PApplet;

public class DropMenu extends PApplet {

	private float x;
	private float y;
	private float h;
	private float w;
	private int[][] colourPalatte;
	private int currentColour;
	private String name;
	private String[] items;
	private boolean open;
	private DropMenuItem[] menuItems;
	private boolean hideText;
	private float newX;
	private float newY;
	private int newColour;
	PApplet parent;
	boolean centerText;

	public DropMenu(PApplet parent, float x, float y, float w, float h, int[][] colourPalatte, String name,
			String[] items) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.colourPalatte = colourPalatte;
		this.name = name;
		this.items = items;
		newColour = color(210);
		// currentColour = color(colourPalatte[0][0], colourPalatte[0][1],
		// colourPalatte[0][2]);
		currentColour = color(210);
		open = false;
		menuItems = new DropMenuItem[items.length];
		for (int i = 0; i < menuItems.length; i++) {
			menuItems[i] = new DropMenuItem(parent, x, (float) (y + h + (i * (h * 0.8))), w, (int) (h * 0.8),
					currentColour, items[i], (int) (h * 0.24));
		}
		hideText = false;
		newX = x;
		newY = y;
		centerText = true;
	}

	public void draw() {
		parent.fill(currentColour);
		parent.noStroke();
		parent.rect(x, y, w, h);
		if (centerText)
			parent.textAlign(CENTER);
		else
			parent.textAlign(LEFT);
		parent.textSize(30);
		parent.fill(0);
		if (!hideText)
			parent.text(name, newX + w / 2, newY + h / 2 + 12);
		if (open) {
			openMenu();
		}

	}

	public void openMenu() {
		for (int i = 0; i < menuItems.length; i++) {
			menuItems[i].draw();
		}
	}

	public void checkHover(float mX, float mY) {
		if (open) {
			for (int i = 0; i < menuItems.length; i++) {
				menuItems[i].checkHover(mX, mY);
			}
			if ((mX >= x && mX <= x + w && mY >= y && mY <= y + h + (menuItems.length) * h * 0.8))
			/* || (mX >= newX && mX <= newX + newW && mY >= newY && mY <= newY + newH)) */ {
				open = true;
			} else {
				open = false;
			}
		} else {
			if (mX >= x && mX <= x + w && mY >= y && mY <= y + h) {
				open = true;
			} else {
				open = false;
			}
		}
	}

	public String checkClick(float mX, float mY) {
		if (open) {
			for (int i = 0; i < menuItems.length; i++) {
				String result = menuItems[i].checkClick(mX, mY);
				if (result != null) {
					name = result;
					return name;
				}

			}
		}
		return null;
	}

	public boolean getOpen() {
		return open;
	}

	public void hideText() {
		hideText = true;
	}

	public void setNewText(float x, float y) {
		newX = x;
		newY = y;
		centerText = false;

		// updateMenu();
	}

//	public void updateMenu() {
//		for (int i = 0; i < menuItems.length; i++) {
//			menuItems[i] = new DropMenuItem(parent, x, (float) (newY + newH + (i * (h * 0.5))), w, (int) (h * 0.7),
//					newColour, items[i], (int) (h * 0.18));
//		}
//	}
}
