import java.awt.datatransfer.FlavorTable;

import processing.core.PApplet;

public class InformationView extends PApplet {

	private PApplet parent;
	private float x;
	private float y;
	private float w;
	private float h;
	private int[][] colourPalatte;
	private Button statsButton;
	private Button nextPageButton;
	private Button previousPageButton;
	private Button loginButton;
	private Button registerButton;

	private Login login;
	private Register register;
	private int currentScreen;
	private boolean loggedIn;
	private ProfileScreen userProfile;

	public InformationView(PApplet parent, float x, float y, float w, float h, int[][] colourPalatte) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.colourPalatte = colourPalatte;
		statsButton = new Button(parent, "Statistics", x + 24, (float) (y + h * 0.9), (float) (w - 48),
				(float) (h * 0.08), colourPalatte, 30);
		nextPageButton = new Button(parent, "Next", (float) (x + w * 0.8 - 24), (float) (y + h * 0.81),
				(float) (w * 0.2), (float) (h * 0.06), colourPalatte, 15);
		previousPageButton = new Button(parent, "Previous", x + 24, (float) (y + h * 0.81), (float) (w * 0.2),
				(float) (h * 0.06), colourPalatte, 15);

		register = new Register(parent, (float) (x + w * 0.05), (float) (y + h * 0.2), (float) (w * 0.9),
				(float) (h * 0.5), colourPalatte);
		login = new Login(parent, (float) (x + w * 0.05), (float) (y + h * 0.2), (float) (w * 0.9), (float) (h * 0.5),
				colourPalatte);
		currentScreen = 1;

		loggedIn = false;
		loginButton = new Button(parent, "Login", (float) (x + w * 0.05), (float) (y + h * 0.03), (float) (w * 0.35),
				(float) (h * 0.1), colourPalatte, 40);
		registerButton = new Button(parent, "Register", (float) (x + w * 0.6), (float) (y + h * 0.03),
				(float) (w * 0.35), (float) (h * 0.1), colourPalatte, 40);
		userProfile = new ProfileScreen(parent, (float) (x + w * 0.05), (float) (y + h * 0.2), (float) (w * 0.9),
				(float) (h * 0.5), colourPalatte);
	}

	public void draw() {
		parent.fill(255);
		parent.strokeWeight((float) 1.5);
		parent.rect(x, y, w, h, 10);
		parent.strokeWeight(1);
		statsButton.draw();
		nextPageButton.draw();
		previousPageButton.draw();
		
		parent.textAlign(CENTER);
		parent.textSize(30);
		parent.text("Change Page", x + w / 2, (float) (y + h * 0.84 + 10));
		parent.textSize(40);
		if (loggedIn)
		{
			parent.text("You are Logged In", x + w / 2, y + h / 10);
		} else {
		loginButton.draw();
		registerButton.draw();
		parent.text("or", x + w / 2, y + h / 10);
		}
		if (loggedIn) {
			userProfile.draw();
		} else {

			if (currentScreen == 1)
				register.draw();
			else
				login.draw();
		}

	}

	public void hoverStats(float mX, float mY) {
		statsButton.hover(mX, mY);
		nextPageButton.hover(mX, mY);
		previousPageButton.hover(mX, mY);
		userProfile.checkHover(mX, mY);
	}

	public boolean clickStats(float mX, float mY) {
		return statsButton.clicked(mX, mY);
	}

	public boolean clickNextPage(float mX, float mY) {
		return nextPageButton.clicked(mX, mY);
	}

	public boolean clickPreviousPage(float mX, float mY) {
		return previousPageButton.clicked(mX, mY);
	}

	public boolean clickRegister(float mX, float mY) {
		if (currentScreen == 1)
			return register.checkClick(mX, mY);
		return false;
	}

	public boolean clickLogin(float mX, float mY) {
		if (currentScreen == 2) {
			return login.checkClick(mX, mY);
		}
		return false;
	}

	public void clickLoginAndRegister(float mX, float mY) {
		if (loginButton.clicked(mX, mY))
			currentScreen = 2;
		if (registerButton.clicked(mX, mY))
			currentScreen = 1;
	}

	public void hoverLoginAndRegister(float mX, float mY) {
		loginButton.hover(mX, mY);
		registerButton.hover(mX, mY);
	}

	public void hoverScreens(float mX, float mY) {
		if (currentScreen == 1)
			register.checkHover(mX, mY);
		else
			login.checkHover(mX, mY);
	}
	
	public void clickLogout(float mX, float mY)
	{
		if (loggedIn && userProfile.checkClickLogOut(mX, mY))
			setLoggedOut();
	}

	public Register getRegister() {
		return register;
	}

	public Login getLogin() {
		return login;
	}

	public int getCurrentScreen() {
		return currentScreen;
	}

	public void setLoggedIn() {
		loggedIn = true;
	}
	
	public void setProfileUsername(String username)
	{
		userProfile.setUsername(username);
	}
	
	public void setLoggedOut() {
		loggedIn = false;
	}
	
	public boolean getLoggedIn() {
		return loggedIn;
	}
	
	public String getUser() {
		return userProfile.getUser();
	}
	
	public boolean clickCreatePost(float mX, float mY) {
		return userProfile.checkClickNewPost(mX, mY);
	}
	

}
