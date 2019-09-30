import processing.core.PApplet;

public class ProfileScreen {

	private PApplet parent;
	private float x;
	private float y;
	private float w;
	private float h;
	private int[][] colourPalatte;
	private Button logOutButton;
	private Button newPostButton;
	private String username;

	public ProfileScreen(PApplet parent, float x, float y, float w, float h, int[][] colorPalatte) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.colourPalatte = colorPalatte;
		
		
		newPostButton = new Button(parent, "Create Post", (float) (x + w * 0.1), (float) (y + h * 0.5), (float) (w * 0.8),
				(float) (h * 0.1), colorPalatte, 30);
		logOutButton = new Button(parent, "Logout", (float) (x + w * 0.1), (float) (y + h * 0.65), (float) (w * 0.8),
				(float) (h * 0.1), colorPalatte, 30);
		username = "";
	}

	public void draw() {
		parent.fill(255);
		parent.rect(x, y, w, h, 10);
		parent.fill(0);
		parent.textAlign(parent.CENTER);
		parent.textSize(30);
		parent.text("Welcome " + username, x + w / 2, (float) (y + h * 0.2));
		logOutButton.draw();
		newPostButton.draw();
		
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	

	public boolean checkClickLogOut(float mX, float mY) {
		return logOutButton.clicked(mX, mY);
		
	}
	
	public boolean checkClickNewPost(float mX, float mY) {
		return newPostButton.clicked(mX, mY);
		
	}

	public void checkHover(float mX, float mY) {
		logOutButton.hover(mX, mY);
		newPostButton.hover(mX, mY);
	}
	
	public String getUser() {
		return username;
	}


}
