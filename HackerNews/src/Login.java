import processing.core.PApplet;

public class Login {

	private PApplet parent;
	private float x;
	private float y;
	private float w;
	private float h;
	private TextBox usernameTextBox;
	private TextBox passwordTextBox;
	private int[][] colourPalatte;
	private Button enterButton;
	private String username;
	private String password;
	private boolean error;
	private boolean sendData;

	public Login(PApplet parent, float x, float y, float w, float h, int[][] colorPalatte) {
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
		enterButton = new Button(parent, "Login", (float) (x + w * 0.1), (float) (y + h * 0.7), (float) (w * 0.8),
				(float) (h * 0.1), colorPalatte, 30);
		error = false;
		sendData = false;
	}

	public void draw() {
		parent.fill(255);
		parent.rect(x, y, w, h, 10);
		parent.fill(0);
		if (error)
			errorWithCredentials();
		parent.textAlign(parent.CENTER);
		parent.textSize(50);
		parent.fill(0);
		parent.text("Login", x + w / 2, (float) (y + h * 0.2));
		usernameTextBox.draw();
		passwordTextBox.draw();
		enterButton.draw();
	}

	public int textBoxActive() {
		if (usernameTextBox.getActive()) {
			return 1;
		} else if (passwordTextBox.getActive()) {
			return 2;
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

	public boolean checkClick(float mX, float mY) {
		usernameTextBox.click(mX, mY);
		passwordTextBox.click(mX, mY);

		if (enterButton.clicked(mX, mY)) {
			return true;
		}
		return false;
	}

	public void checkHover(float mX, float mY) {
		enterButton.hover(mX, mY);
	}

	public void sendData() {
		System.out.println(username + " " + password);
		sendData = true;
		usernameTextBox.resetBox();
		passwordTextBox.resetBox();
	}
	
	public void errorWithCredentials() {
		parent.fill(200, 0, 0);
		parent.textAlign(parent.CENTER);
		parent.textSize(15);
		parent.text("Wrong username and\npassword", x + w / 2, (float) (y + h * 0.3));
	}
	
	public boolean getSendData()
	{
		return sendData;
	}
	
	public String getPassword() {
		return password;
	}
		
	public void setError() {
		error = true;
	}

}
