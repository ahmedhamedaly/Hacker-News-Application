import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import websockets.WebsocketServer;

public class Raaddit extends PApplet {
	ArrayList<Post> postList;
	ArrayList<Comment> commentList;
	ArrayList<Object[]> top100userData;
	
	String micUrl = "https://codepen.io/ahmedhamedaly/pen/NmrLYX";
	static String search = "";

	float scroll;
	private float previousScroll;
	private float scrollLimit;
	
	static int currentScreen;
	int[][] colourScheme;
	int i;
	int previousScreen;
	
	static boolean micActive;
	boolean loggedIn;
	
	Background background;
	Header header;
	ChartPost chartPost;
	ChartComment chartComment;
	SearchEngine searchEngine;
	DropMenu sortTopMenu;
	pieGraph pieGraph;
	Opening opening;
	InformationView informationView;
	ScrollWheelWidget scrollWheelWidget;
	Register register;
	Login login;
	CommentPage commentView;
	WebsocketServer socket;
	AutoComplete autoComplete;
	WebsiteCompositionPieChart websiteCompositionPieChart;
	static SearchBarContainer searchBar;
	public static Query query;
	
	PImage commentImage;
	PImage postImage;
	PImage backgroundPic;
	PImage headerRaaddit;
	PImage raadditLogo;
	PImage raaddit;
	CreatePostScreen createPost;
	
	static PImage commentCollapse;
	static PImage commentOpen;
	static PImage menuIcon;
	static PImage voice;	
		
	public static void main(String[] args) {
		PApplet.main("Raaddit");
	}

	public void settings() {
		size(1920, 1080);
		//fullScreen();
	}

	public void setup() {
		loadImages();
		
		loggedIn = false;
		micActive = false;
		
		colourScheme = CONSTANTS.COLOUR_PALATTE_GREYS;
		previousScreen = currentScreen;
		
		currentScreen = 10;
		scroll = 0;
		previousScroll = 0;
		scrollLimit = 152 * 45;
		
				
		scrollWheelWidget = new ScrollWheelWidget(this);
		background = new Background(this, colourScheme);
		header = new Header(this, colourScheme, headerRaaddit, raadditLogo);
		postList = new ArrayList<Post>();
		commentList = new ArrayList<Comment>();
		
		query = new Query(this, commentImage, postImage);
		query.newQuery("");
		
		top100userData = query.getUserData(query.getTop100Posts());
		
		searchBar = new SearchBarContainer(this, 380 + 100, 0, 815 - 50 - 100, 80, colourScheme, postList, commentImage, postImage);
		chartPost = new ChartPost(this, top100userData);
		autoComplete = new AutoComplete(this, 580, 15, 565, 50);
		opening = new Opening(this, raaddit);
		header.createSortMenu(this, (float) (width *0.905), 20, (float) (width * 0.081), 50, colourScheme, "Sort By", CONSTANTS.SORT_TOP_TIME_ITEMS);
		header.createChartMenu(this, (float) (width * 0.905), 20, (float) (width * 0.081), 50, colourScheme, "Posts", CONSTANTS.CHART_TYPES);
		informationView = new InformationView(this, width - 790 + 110, 82, 490, (float) (height * 0.85 - 3), CONSTANTS.COLOUR_PALATTE_GREYS);
		socket = new WebsocketServer(this, 6969, "/p5websocket");
		
		register = informationView.getRegister();
		login = informationView.getLogin();
		createPost = new CreatePostScreen(this, 150, 82, width - 850, (float) (height * 0.85 - 3),
				CONSTANTS.COLOUR_PALATTE_GREYS);
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(micUrl));
		} catch (IOException e) {
			System.out.println(e);
		}
		createPost.setActiveFalse();
		websiteCompositionPieChart = new WebsiteCompositionPieChart(this);
		//System.out.println("herei");
		
	}

	public void draw() {
		//image(backgroundPic, 0, 0, 1920, 1080);
		
		background(50);
		if (currentScreen == 0 || currentScreen == 1) {
			for (int i = 0; i < query.getPostList().size(); i++) {
				query.getPostList().get(i).draw(i * 155 + 100 - scroll);
			}
			if (query.getPostList().size() == 0) {
				//System.out.println("0w0 we made an oopsie");
				textAlign(CENTER);
				textSize(100);
				text("0w0 we made an oopsie", width/3, height/2);
			}
			informationView.draw();

			//createPost.draw();

		}

		else if (currentScreen == 3) {
			chartPost.draw(top100userData);
		}

		else if (currentScreen == 4) {
			pieGraph.draw();
		}

		else if (currentScreen == 5) {
			commentView.draw(scroll);
			informationView.draw();
			scrollWheelWidget.drawWheel();
		}
		else if (currentScreen == 2) {
			websiteCompositionPieChart.pieChart();
		}
		else if (currentScreen == 7)
		{
			createPost.draw();
			informationView.draw();
		}

		// if (currentScreen != 4)
		header.draw();
		


		if (currentScreen == 1 || currentScreen == 0) {
			searchBar.draw();
			autoComplete.draw();
			scrollWheelWidget.drawWheel();
		}
		

		if (!opening.finishedOpening)
			opening.draw();
		

	}

	public void mouseWheel(MouseEvent event) {
		if (currentScreen == 5)
			scrollLimit = commentView.totalHeight() - 155 * 6;
		else
			scrollLimit = 152 * 45;
		float newScroll = scroll + event.getCount() * 10;
		if (newScroll < scrollLimit)
			scroll = newScroll;
		scroll = (scroll < 0) ? 0 : scroll;
//		if (currentScreen == 1 || currentScreen == 0) {
//			float temp = scroll;
//			temp /= 155;
//			temp *= (scrollLimit / 88);
//			temp += 80;
//			scrollWheelWidget.move(temp);
//		} else if (currentScreen == 5) {
		float ratio = scroll / scrollLimit;
		float position = ((1000) * ratio) + 80;
		scrollWheelWidget.move(position);
//		System.out.println("Scroll Limit = " + scrollLimit);
//		System.out.println("Position = " + position);
		// }
	}

	public void mousePressed() {
		if (currentScreen == 0 || currentScreen == 1) {
			for (int i = 0; i < query.getPostList().size(); i++) {
				Post post = query.getPostList().get(i);
				if (informationView.getLoggedIn()==true)
				post.clickScore(mouseX, mouseY);
				post.checkUrlPresed(mouseX, mouseY);
				if (post.clickCommentWidget(mouseX, mouseY)) {
					query.commitChanges();
					commentView = new CommentPage(this, post);
					previousScroll = scroll;
					scroll = 0;
					currentScreen = 5;
				}
			}
		} else if (currentScreen == 5) {
			if (commentView.currentPost().clickCommentWidget(mouseX, mouseY)) {
				currentScreen = 1;
				scroll = previousScroll;
				scrollLimit = 152 * 45;
			}
			commentView.checkCommentCollapseClicked(mouseX, mouseY);
		}
		if (previousScreen != currentScreen) {
			query.commitChanges();
			query.resetPage();
			previousScreen = currentScreen;
		}

		if (header.clickWebsiteTitle(mouseX, mouseY)) {
			currentScreen = 1;
			header.changeState(currentScreen);
		}

		searchBar.menuClick(mouseX, mouseY);
		searchBar.searchEngineClicked(mouseX, mouseY);

		String result = header.menuClick(mouseX, mouseY);
		if (result != null) {
			query.commitChanges();
			if (result.equals(CONSTANTS.CHART_TYPES[0])) {
				currentScreen = 3;
			} else if (result.equals(CONSTANTS.CHART_TYPES[1])) {
				websiteCompositionPieChart.update();
				currentScreen = 2;
			} else {
				switch (result) {
				case "Top Score":
					query.setSortToHighestScore();
					break;
				case "Latest":
					query.setSortToNewestPosts();
					break;
				case "Oldest":
					query.setSortToOldestPosts();
				}

			}
		}
		

		if (currentScreen == 3) {
			String user = chartPost.currentUser(top100userData);
			int posts = chartPost.currentPosts(top100userData);
			int comments = chartPost.currentComments(top100userData);
			i++;
			if (user != null && i > 0) {
				pieGraph = new pieGraph(this, user, posts, comments);
				currentScreen = 4;
			}
		}

		if (currentScreen != 3 && currentScreen != 4) {
			if (informationView.clickStats(mouseX, mouseY)) {
				if (currentScreen == 1) {
					query.commitChanges();
					currentScreen = 3;
					header.changeState(currentScreen);

				} else {
					currentScreen = 1;
					header.changeState(currentScreen);
					scroll = 0;
				}
			}

			if (informationView.clickNextPage(mouseX, mouseY)) {
				query.commitChanges();
				query.nextPage();
				scroll = 0;
			} else if (informationView.clickPreviousPage(mouseX, mouseY)) {
				query.commitChanges();
				query.previousPage();
				scroll = 0;
			}

			if (informationView.clickLogin(mouseX, mouseY)) {

				if (query.userMatch(login.usernameString(), login.getPassword())) {
					System.out.println("Logged in");
					informationView.setLoggedIn();
					informationView.setProfileUsername(login.usernameString());
				} else {
					login.setError();
				}
				
			} else if (informationView.clickRegister(mouseX, mouseY)) {
				if(query.addUser(register.getUsername(), register.getPassword()))
				{
					informationView.setLoggedIn();
					informationView.setProfileUsername(register.getUsername());
				}
				else {
					register.setError();
				}
			}
			if (informationView.getLoggedIn())
			{
				informationView.clickLogout(mouseX, mouseY);
				if (informationView.clickCreatePost(mouseX, mouseY))
				{
					currentScreen = 7;
					createPost.setActiveTrue();
				}
			}
			
			
			
			informationView.clickLogout(mouseX, mouseY);
			
			informationView.clickLoginAndRegister(mouseX, mouseY);

		}
		
		header.clickMic(mouseX, mouseY);
		
		System.out.println(pmouseX + " " + pmouseY);
		
		if (createPost.getActive()) {
			createPost.clickTitle(mouseX, mouseY);
			createPost.clickUrl(mouseX, mouseY);
			if (createPost.clipBoardClicked(mouseX, mouseY)) {
				createPost.sendUrl(onPaste());
			}
			if (createPost.createPostClick(mouseX, mouseY))
			{
				if (query.sendPost(createPost.titleString(), createPost.urlString(), informationView.getUser()))
				{
				query.commitChanges();
				currentScreen = 0;
				query.newQuery("");
				scroll = 0;
				createPost.setActiveFalse();
				}
				else {
					System.out.println("Error with creating post");
				}
			}
		}

	}

	public void mouseMoved() {
		
		informationView.hoverStats(mouseX, mouseY);
		
		informationView.hoverScreens(mouseX, mouseY);
		
		header.menuHover(mouseX, mouseY);

		createPost.clipBoardHover(mouseX, mouseY);
		createPost.createPostHover(mouseX, mouseY);
		searchBar.menuHover(mouseX, mouseY);
		
		informationView.hoverLoginAndRegister(mouseX, mouseY);
		
		for (int i = 0; i < query.getPostList().size(); i++) {
			query.getPostList().get(i).hoverScore(mouseX, mouseY);
		}
	}

	public void loadImages() {
		commentImage = loadImage("rright.png");
		commentImage.resize(25, 25);
		postImage = loadImage("ddown.png");
		postImage.resize(25, 25);
		raaddit = loadImage("raaddit.png");
		backgroundPic = loadImage("background.png");
		headerRaaddit = loadImage("banner2.png");
		raadditLogo = loadImage("raadditLogo.png");
		voice = loadImage("voice.png");
		voice.resize(35, 35);
		menuIcon = loadImage("menu.png");
		menuIcon.resize(40, 40);
		commentCollapse = loadImage("ddown.png");
		commentCollapse.resize(15, 15);
		commentOpen = loadImage("rright.png");
		commentOpen.resize(15, 15);
	}

	public void keyPressed() {
		if (searchBar.searchEngineActive()) {
			if (key == BACKSPACE) {
				ArrayList<Character> chars = new ArrayList<Character>();
				for (char c : search.toCharArray()) {
					chars.add(c);
				}
				if (chars.size() >= 1) {
					int index = chars.size() - 1;
					chars.remove(index);
					index--;
					char[] temp = new char[chars.size()];
					for (int indexTemp = 0; indexTemp < temp.length; indexTemp++) {
						temp[indexTemp] = chars.get(indexTemp);
					}
					search = new String(temp);
					autoComplete.newEntry(search);
				}
			}
			if (key == ENTER || key == RETURN) {
				searchBar.sendInput(search);
				currentScreen = 0;
				searchBar.setSearchActive();
				search = "";
				autoComplete.newEntry(search);
			}
			int test = key;
			if (test >= 0x20 && test <= 0x7E) {
				search += key;
				autoComplete.newEntry(search);
			}
			searchBar.setInput(search);
		} else if (!informationView.getLoggedIn()) {
			switch (login.textBoxActive()) {
			case 1:
				String username = login.usernameString();

				if (!username.equals("") && key == BACKSPACE) {
					username = username.substring(0, username.length() - 1);
					login.sendUsername(username);
				} else if (!username.equals("") && key == ENTER || key == RETURN) {
					login.enterUsername(username);
				} else {
					if ((int) (key) >= 32 && (int) (key) <= 126)
						username += key;
					login.sendUsername(username);
				}
				break;
			case 2:
				String password = login.passwordString();

				if (!password.equals("") && key == BACKSPACE) {
					password = password.substring(0, password.length() - 1);
					login.sendPassword(password);
				} else if (!password.equals("") && key == ENTER || key == RETURN) {
					login.enterPassword(password);
				} else {
					if ((int) (key) >= 32 && (int) (key) <= 126)
						password += key;
					login.sendPassword(password);
				}
				break;
			default:

			}

			switch (register.textBoxActive()) {
			case 1:
				String username = register.usernameString();

				if (!username.equals("") && key == BACKSPACE) {
					username = username.substring(0, username.length() - 1);
					register.sendUsername(username);
				} else if (!username.equals("") && key == ENTER || key == RETURN) {
					register.enterUsername(username);
				} else {
					if ((int) (key) >= 32 && (int) (key) <= 126)
						username += key;
					register.sendUsername(username);
				}
				break;
			case 2:
				String password = register.passwordString();

				if (!password.equals("") && key == BACKSPACE) {
					password = password.substring(0, password.length() - 1);
					register.sendPassword(password);
				} else if (!password.equals("") && key == ENTER || key == RETURN) {
					register.enterPassword(password);
				} else {
					if ((int) (key) >= 32 && (int) (key) <= 126)
						password += key;
					register.sendPassword(password);
				}
				break;
			case 3:
				String confirmPassword = register.confirmPasswordString();

				if (!confirmPassword.equals("") && key == BACKSPACE) {
					confirmPassword = confirmPassword.substring(0, confirmPassword.length() - 1);
					register.sendConfirmPassword(confirmPassword);
				} else if (!confirmPassword.equals("") && key == ENTER || key == RETURN) {
					register.enterConfirmPassword(confirmPassword);
				} else {
					if ((int) (key) >= 32 && (int) (key) <= 126)
						confirmPassword += key;
					register.sendConfirmPassword(confirmPassword);
				}
				break;
			default:
			}
		} else if (createPost.getActive()) {
			System.out.println(createPost.textBoxActive());
			switch (createPost.textBoxActive()) {
			case 1:
				String title = createPost.titleString();

				if (!title.equals("") && key == BACKSPACE) {
					title = title.substring(0, title.length() - 1);
					createPost.sendTitle(title);
				} else if (!title.equals("") && key == ENTER || key == RETURN) {
					createPost.enterTitle(title);
				} else {
					if ((int) (key) >= 32 && (int) (key) <= 126)
						title += key;
					createPost.sendTitle(title);
				}
				break;
			case 2:
				String url = createPost.urlString();

				if (!url.equals("") && key == BACKSPACE) {
					url = url.substring(0, url.length() - 1);
					createPost.sendUrl(url);
				} else if (!url.equals("") && key == ENTER || key == RETURN) {
					createPost.enterUrl(url);
				} else {
					if ((int) (key) >= 32 && (int) (key) <= 126)
						url += key;
					createPost.sendUrl(url);
				}
				break;

			default:
				break;
			}
		}
	}

	public void mouseDragged() {
		// this is extra to look nicer
		if (currentScreen == 5)
			scrollLimit = commentView.totalHeight() - 155 * 6;
		else
			scrollLimit = 152 * 45;
		if (currentScreen == 1 || currentScreen == 0||currentScreen==5) {
			if (mouseX >= 1900 && mouseY >= 79) {
				float temp = mouseY;
				temp/=1000;
				scroll = temp*scrollLimit;
//				float ratio = scroll / scrollLimit;
//				float position = ((1000) * ratio) + 80;
				scrollWheelWidget.move(mouseY);
			}
		} 
	}

	public void webSocketServerEvent(String msg) {
		System.out.println(msg);
		if (micActive) 
			searchBar.sendInput(msg);
	}
	
	private String onPaste(){
	    Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
	    Transferable t = c.getContents(this);
	    if (t == null)
	        return "";
	    try {
	        return (String) t.getTransferData(DataFlavor.stringFlavor);
	    } catch (Exception e){
	        e.printStackTrace();
	    }//try
	    return "";
	}//onPaste
	

}