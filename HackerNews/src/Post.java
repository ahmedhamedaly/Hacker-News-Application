import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Post extends PApplet{
    private int score;
    private String publisher;
    private int time;
    private int id;
    private String title;
    private String type;
    private int descendants;
    private String url;
    private int[] kids;
    private PFont textFont;
    private PApplet parent;
    private float yPos;
    private int[][] colourScheme;
    private Time timeObject;
    private float w;
    private float h;
    CommentWidget commentWidget;
    private int scoreColour;
    private boolean scoreClicked;
    private float scoreY;
    private int scoreBorderWeight;
    private int textHeight;
    private String[] paragraph;
    private static final int TEXT_WIDTH_LIMIT = 150;
    private static final Map<String, String> REPLACEMENTS = new HashMap<String, String>() {{
	    put("<p>", "\n");
	    put("&#34;", "\"");
	    put("\\\"", "\"");
	    put("&#62;", "");
	    put("<i>", "");
	    put("<\\/i>", "");
	    put("\\u2013", "-");
	    put("\\u2019", "'");
	    put("&#x27;","'");
	    put("&#x2;", "'");
	    put("&#x2f;", "/");
	    put("&#x2F;", "/");
	    put("&quot;", "\"");
	    put("\\n", " ");
	}};
    
    Post(int[][] colourScheme, PApplet parent, int score, String publisher, int time, int id, String title, String type, int descendants, String url, int[] kids,PImage commentImage,PImage postImage){
        this.score = score;
        this.publisher = publisher;
        this.time = time;
        this.id = id;
        this.title = filterString(title);
        this.type = type;
        this.descendants = descendants;
        this.url = url;
        this.kids = kids;
        this.parent = parent;
        this.colourScheme = colourScheme;
        textFont = new PFont(PFont.findFont("Courier New"), true);
        yPos = 0;
        timeObject = new Time(time);
        w = parent.width-850;
        h = 150;
        commentWidget = new CommentWidget(parent, commentImage, postImage);
        scoreClicked = false;
        scoreColour = color(colourScheme[1][0], colourScheme[1][1], colourScheme[1][2]);
        if(type.equals("text"))
        {
        	String text = filterString(url);
        	text = filterString(text);
        	paragraph = seperateString(text);
        	this.url = "Text Post";
        }
        else
        {
        	try {
	        	if (url.charAt(url.length()-2) == '"')
	    		{
	    			this.url = url.substring(0, url.length()-2);
	    		}
	    		else if (url.charAt(url.length()-1) == '"')
	    		{
	    			this.url = url.substring(0, url.length()-1);
	    		}
		        }
		        catch (Exception e) {
		
		        }
	        
        }
        if (this.url.equals("") || this.url.equals("\""))
        {
        	this.url = "Text Post";
        }
        
        scoreBorderWeight = 1;
        
    }
    
    public void draw(float i) {
    	//parent.strokeWeight((float) 1.5);
    	parent.fill(255);
    	parent.rect(150, i-18, w, h, 10);
    //	parent.strokeWeight(1);
    	parent.fill(0);
    	yPos = i;
    	//parent.stroke(0);
    	displayScore(yPos);
    	if(!url.equals("\""))
    		displayLink(yPos);
    	displayAuthor(yPos);
    	displayTime(yPos);
    	displayTitle(yPos);
    	parent.textSize(12);
        commentWidget.draw(w+80, yPos + 105);
        scoreY = i;
    	//parent.text("Score: " + score +", By: " + publisher + ", Time:" + time + 
    	//				",\nID: " + id + ", Title: " + title + ", ulrl: " + url + ", Kids: " + kids[0], 160, yPos);
    }
    
    public int drawComments(float y) {
    	if(type.equals("text"))
		{
    		parent.fill(250);
        	parent.rect(150, y-18, w, h + paragraph.length * 20 + 15, 10);
        //	parent.strokeWeight(1);
        	parent.fill(0);
        	yPos = y;
        	//parent.stroke(0);
        	displayScore(yPos);
        	displayAuthor(yPos-30);
        	displayTime(yPos-30);
        	displayTitle(yPos);
        	parent.textSize(12);
            commentWidget.draw(w+80, yPos + 105);		
            scoreY = y;
			parent.stroke(150);
			parent.fill(250);
			parent.rect(180, y + 125, w - 60,paragraph.length * 20, 5);
			parent.fill(0);
			parent.textAlign(LEFT, TOP);
			for(int i = 0; i < paragraph.length; i ++)
	    		parent.text(paragraph[i], 190, y + 135 + (i* 20));
			return 165 +  + paragraph.length * 20;
		}
    	else
    	{
    		draw(y);
    		return 150;
    	}
    }
    
    public static String arrayToString(Object[] array) {
    	String string = "";
    	for(int i = 0; i < array.length; i++) {
    		string += array[i] + ((i != array.length - 1)?", ":"");
    	}
    	return string;
    }
    
    public void openLink() {
		//if(mY > yPos && mY < yPos + 20) {
			try {
				
				//System.out.println(url.charAt(url.length()-1));
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		//}
    }
    
    public void displayScore(float y) 
    {
    	if (scoreClicked)
    	{
    		parent.fill(colourScheme[1][0],colourScheme[1][1],colourScheme[1][2]);
    	}
    	else {
    		parent.fill(colourScheme[2][0],colourScheme[2][1],colourScheme[2][2]);
    	}
    	parent.strokeWeight(scoreBorderWeight);
    	parent.ellipse(w+80, y+60, 50, 50);
    	parent.strokeWeight(1);
    	parent.textSize(20);
    	parent.fill(0);
    	parent.textAlign(CENTER);
    	parent.text(score, w+80, y+68);
    	parent.textAlign(LEFT);
    }
    
    public void displayLink(float y)
    {
    	String showUrl;
    	parent.textSize(20);
    	if (url.length() > 50)
    	{
    		showUrl = url.substring(0, 50);
    		showUrl += "...";
    	}
    	else {
    		showUrl = url;
    	}
    	parent.fill(0,0,255);
    	parent.text(showUrl, 180, y+51);
    }
    
    public void displayTitle(float y)
    {
    	String showTitle;
    	parent.textSize(25);
    	if (title.length() > 70)
    	{
    		showTitle = title.substring(0, 70);
    		showTitle += "...";
    	}
    	else {
    		showTitle = title;
    	}
    	parent.fill(0);
    	parent.text(showTitle, 180, y+16);
    }
    
    public void displayAuthor(float y)
    {
    	String byString = "By: ";
    	byString = byString + publisher;
    	parent.textSize(20);
    	parent.fill(80);
    	parent.text(byString, (float) (180), y+81);
    }
    
    public void checkUrlPresed(float mX, float mY)
    {
    	//System.out.println(mX + " " + mY + " " + yPos);
    	if (mX > 180 && mX < 180 + w/4 && mY > yPos  && mY < yPos + 51)
    	{
    		openLink();
    	}
    				
    }
    
    public void displayTime(float y)
    {
    	parent.textSize(15);
    	parent.fill(120);
    	parent.text(timeObject.getTimeString(), (float) (180), y+106);
    }
    
    public void hoverScore(float mX, float mY) {
		float dist = dist(mX, mY, w +80, scoreY + 60);
		if (dist <= 25) {
			scoreBorderWeight = 2;
		} else {
			scoreBorderWeight = 1;
		}
	}

	public void clickScore(float mX, float mY) {
		float dist = dist(mX, mY, w +80, scoreY + 60);
		if (dist <= 25) {
			if (!scoreClicked) {
				score++;
				scoreClicked = true;
			} else {
				score--;
				scoreClicked = false;
			}
		}

	}
	
	public boolean clickCommentWidget(float mX, float mY) {
		float dist = dist(mX, mY, w+80, yPos + 105);
		if (dist <= 18) {
			commentWidget.toggleActive();
			return true;
		}
		return false;

	}
    
    public String getUser()
    {
    	return publisher;
    }
    
    public int getScore()
    {
    	return score;
    }
    
    public int getTime() {
    	return time;
    }
    
    public String getTitle() {
    	return title;
    }
    public String getURL() {
    	return url;
    }
    public int getID() {
    	return id;
    }
    
    private String filterString(String text) {
    	StringBuffer result = new StringBuffer();
    	Matcher matcher = Pattern.compile("(<p>|&#34;|\\\\\"|\\\\n|&#62;|<\\\\/i>|<i>|\\\\u2013|\\\\u2019|&#x2;|&#x2f;|&#x2F;|&#x27;|&quot;)").matcher(text);
    	while(matcher.find())
    	{
    		String replacement = REPLACEMENTS.get(matcher.group());
    		if(replacement != null)
    			matcher.appendReplacement(result, replacement);
    	}
    	matcher.appendTail(result);
    	return result.toString();
    }
    
    private String[] seperateString(String text) {
    	if(text.isEmpty())
    		return new String[] {"[deleted]"};
    	ArrayList<String> paragraph = new ArrayList<String>();
    	Matcher matcher = Pattern.compile("(.{0,"+TEXT_WIDTH_LIMIT+"})(?:\\s|$)").matcher(text);
    	while(matcher.find())
    		paragraph.add(matcher.group(0));
    	return paragraph.toArray(new String[0]);
    }
}
