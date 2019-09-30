import java.awt.event.MouseAdapter;

import processing.core.PApplet;
import processing.core.PConstants;

public class CreatePostScreen {

	private float x;
	private float y;
	private float w;
	private float h;
	private boolean active;
	private Button createPostButton;
	private Button copyFromClipboardButton;
	private TextBox titleBox;
	private TextBox urlBox;
	private String title;
	private String url;

	private PApplet parent;

	public CreatePostScreen(PApplet parent, float x, float y, float w, float h, int[][] colourPalatte) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		active = false;
		copyFromClipboardButton = new Button(parent, "Clipboard", (float) (x + w * 0.82), (float) (y + h * 0.44),
				(float) (w * 0.1), (float) (h * 0.1), colourPalatte, 15);
		createPostButton = new Button(parent, "Create Post", (float) (x + w * 0.1), (float) (y + h * 0.8),
				(float) (w * 0.8), (float) (h * 0.1), colourPalatte, 30);
		titleBox = new TextBox(parent, (float) (x + w * 0.18), (float) (y + h * 0.305), (float) (w * 0.6),
				(float) (h * 0.07), "Title", colourPalatte, 15);
		urlBox = new TextBox(parent, (float) (x + w * 0.18), (float) (y + h * 0.455), (float) (w * 0.6),
				(float) (h * 0.07), "URL", colourPalatte, 15);
	}

	public void draw() {
		if (active) {
			parent.fill(255);
			parent.strokeWeight(1);
			parent.stroke(0);
			parent.rect(x, y, w, h, 10);
			createPostButton.draw();
			copyFromClipboardButton.draw();
			parent.textAlign(PConstants.CENTER);
			
			parent.textSize(50);
			parent.text("Create your own Content!", x + w / 2, (float) (y + h * 0.2));
			titleBox.draw();
			urlBox.draw();
			parent.textSize(30);
			parent.textAlign(parent.CENTER);
			parent.text("Title: " , (float) (x + w * 0.12), (float) (y + h * 0.35));
			parent.text("URL: ", (float) (x + w * 0.12), (float) (y + h * 0.5));
		}
	}

	public int textBoxActive() {
		if (titleBox.getActive()) {
			return 1;
		} else if (urlBox.getActive()) {
			return 2;
		}
		return -1;
	}
	
	public boolean clipBoardClicked(float mX, float mY)
	{
		return copyFromClipboardButton.clicked(mX, mY);
	}
	
	public void clipBoardHover(float mX, float mY)
	{
		copyFromClipboardButton.hover(mX, mY);
	}
	
	public void createPostHover(float mX, float mY)
	{
		createPostButton.hover(mX, mY);
	}
	
	public boolean createPostClick(float mX, float mY)
	{
		return createPostButton.clicked(mX, mY);
	}

	public String titleString() {
		return titleBox.getText();
	}

	public String urlString() {
		return urlBox.getText();
	}

	public void sendTitle(String title) {
		titleBox.updateText(title);
		this.title = title;
	}

	public void enterTitle(String title) {
		titleBox.setTextPermanent();

	}

	public void sendUrl(String url) {
		urlBox.updateText(url);
		this.url = url;
	}

	public void enterUrl(String url) {
		urlBox.setTextPermanent();

	}

	public void setActiveTrue() {
		active = true;
	}

	public void setActiveFalse() {
		active = false;
	}

	public boolean getActive() {
		return active;
	}

	public void clickTitle(float mX, float mY) {
		if (active)
			titleBox.click(mX, mY);
	}

	public void clickUrl(float mX, float mY) {
		if (active)
			urlBox.click(mX, mY);
	}

}
