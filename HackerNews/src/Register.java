import java.io.ObjectInputStream.GetField;

import processing.core.PApplet;

public class Register {

	private PApplet parent;
	private float x;
	private float y;
	private float w;
	private float h;
	private TextBox usernameTextBox;
	private TextBox passwordTextBox;
	private TextBox confirmPasswordTextBox;
	private int[][] colourPalatte;
	private Button enterButton;
	private String username;
	private String password;
	private String confirmPassword;
	private boolean error;
	private boolean correctCredentials;

	public Register(PApplet parent, float x, float y, float w, float h, int[][] colorPalatte) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.colourPalatte = colorPalatte;
		usernameTextBox = new TextBox(parent, (float) (x + w * 0.1), (float) (y + h * 0.4), (float) (w * 0.8),
				(float) (h * 0.1), "USERNAME", colorPalatte, 20);
		passwordTextBox = new TextBox(parent, (float) (x + w * 0.1), (float) (y + h * 0.55), (float) (w * 0.8),
				(float) (h * 0.1), "PASSWORD", colorPalatte, 20);
		confirmPasswordTextBox = new TextBox(parent, (float) (x + w * 0.1), (float) (y + h * 0.7), (float) (w * 0.8),
				(float) (h * 0.1), "CONFIRM PASSWORD", colorPalatte, 20);
		enterButton = new Button(parent, "Signup", (float) (x + w * 0.1), (float) (y + h * 0.85), (float) (w * 0.8),
				(float) (h * 0.1), colorPalatte, 30);
		error = false;
		correctCredentials = false;
	}

	public void draw() {
		parent.fill(255);
		parent.rect(x, y, w, h, 10);
		parent.fill(0);
		;
		parent.textAlign(parent.CENTER);
		parent.textSize(50);
		parent.text("Signup", x + w / 2, (float) (y + h * 0.2));
		usernameTextBox.draw();
		passwordTextBox.draw();
		confirmPasswordTextBox.draw();
		enterButton.draw();
		if (error)
			errorWithCredentials();
	}

	public void errorWithCredentials() {
		parent.fill(200, 0, 0);
		parent.textAlign(parent.CENTER);
		parent.textSize(15);
		parent.text("Username taken or password\ndo not match", x + w / 2, (float) (y + h * 0.3));
	}

	public int textBoxActive() {
		if (usernameTextBox.getActive()) {
			return 1;
		} else if (passwordTextBox.getActive()) {
			return 2;
		} else if (confirmPasswordTextBox.getActive()) {
			return 3;
		}
		return -1;
	}

	public String usernameString() {
		return usernameTextBox.getText();
	}

	public void sendUsername(String username) {
		usernameTextBox.updateText(username);
		this.username = username;
	}

	public void enterUsername(String username) {
		usernameTextBox.setTextPermanent();

	}

	public String passwordString() {
		return passwordTextBox.getSecure();
	}

	public void sendPassword(String password) {
		passwordTextBox.updateTextSecure(password);
		this.password = passwordTextBox.getSecure();
	}

	public void enterPassword(String password) {
		passwordTextBox.setTextPermanent();
	}

	public String confirmPasswordString() {
		return confirmPasswordTextBox.getSecure();
	}

	public void sendConfirmPassword(String password) {
		confirmPasswordTextBox.updateTextSecure(password);
		this.confirmPassword = confirmPasswordTextBox.getSecure();
	}

	public void enterConfirmPassword(String password) {
		confirmPasswordTextBox.setTextPermanent();
	}

	public boolean checkClick(float mX, float mY) {
		usernameTextBox.click(mX, mY);
		passwordTextBox.click(mX, mY);
		confirmPasswordTextBox.click(mX, mY);

		if (enterButton.clicked(mX, mY)) {
			sendData();
			return getCorrectCredentials();
		}
		return false;
	}

	public void checkHover(float mX, float mY) {
		enterButton.hover(mX, mY);
	}

	public void sendData() {
		System.out.println(username + " " + password);
		if (password == null || confirmPassword == null)
			error = true;
		else if (!password.equals(confirmPassword))
			error = true;
		else
		{
			error = false;
			correctCredentials = true;
		}

		usernameTextBox.resetBox();
		passwordTextBox.resetBox();
		confirmPasswordTextBox.resetBox();
	}
	
	public boolean getCorrectCredentials()
	{
		return correctCredentials;
	}
	
	public void resetCorrectCredentials()
	{
		correctCredentials = false;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setError() {
		error = true;
	}

}