import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.*;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Comment extends PApplet{
	private static final Map<String, String> REPLACEMENTS = new HashMap<String, String>() {{
	    put("<p>", "\n");
	    put("&#34;", "\"");
	    put("\\\"", "\"");
	    put("&#62;", "");
	    put("&#x2;", "'");
	    put("&#x2f;", "/");
	    put("&#x2F;", "/");
	    put("&#x27;","'");
	    put("<i>", "");
	    put("<\\/i>", "");
	    put("\\u2013", "-");
	    put("\\u2019", "'");
	    put("\\n", "\n");
	    put("&quot;", "\"");
	    put("&gt;", ">");
	}};
    private String publisher;
    private int time;
    private int id;
    private String text;
    private String[] paragraph;
    private String type;
    private int commentParent;
    private int[] kids;
    private PFont textFont;
    private PApplet parent;
    private float xPos;
    private float yPos;
    private int height;
    private int width;
    private int boxHeight;
    private boolean collapsed;
    private static final int COMMENT_WIDTH_LIMIT = 120;
    private PImage collapse;
    private int layer;
    
    public ArrayList<Comment> childCommentList;
    
    Comment(PApplet parent, String publisher, int time, int id, String text, String type, int commentParent, int[] kids, int layer)
    {
        this.publisher = (publisher.isEmpty())?"[deleted]":publisher;
        this.time = time;
        this.id = id;
        this.text = text;
        this.type = type;
        this.commentParent = commentParent;
        this.kids = kids;
        this.parent = parent;
        this.layer = layer;
        text = filterString(text);
        textFont = new PFont(PFont.findFont("Courier New"), true);
        yPos = 0;
        childCommentList = Raaddit.query.getComments(this);
        width = parent.displayWidth - 850;
        paragraph = seperateString(text);
        height = paragraph.length * 20 + CONSTANTS.COMMENT_MARGIN;
        boxHeight = getTotalHeight();
        collapsed = false;
        collapse = Raaddit.commentCollapse;
    }
    
    public int draw(int x, int y) {
    	xPos = x;
    	yPos = y;
    	int totalHeight = getTotalHeight();
    	parent.stroke(220);
    	parent.fill((layer % 2 == 0)?250:235);
    	if(!collapsed)
    	{
    		int currentHeight = height;
	    	parent.rect(x, y, width -layer*60, totalHeight);
	    	parent.fill(100);
	    	parent.image(collapse, x+5, y+5);
	    	parent.textAlign(LEFT, TOP);
	    	parent.text(publisher, x + 25, y + 3);
	    	parent.fill(0);
	    	for(int i = 0; i < paragraph.length; i ++)
	    		parent.text(paragraph[i], x+25, y + CONSTANTS.COMMENT_MARGIN + 10 + (i* 20));
	    	for(int i = 0; i < childCommentList.size(); i++)
	    	{
	    		Comment comment = childCommentList.get(i);
	    		currentHeight += comment.draw(x + 50, y + currentHeight + CONSTANTS.COMMENT_MARGIN*i);
	    	}
	    	parent.noStroke();
	    	parent.fill(215);
	    	parent.rect(x+11, y+25, 3, totalHeight - (20 + CONSTANTS.COMMENT_MARGIN));
    	}
    	else
    	{
    		parent.rect(x, y, width -layer*60, 25);
	    	parent.fill(100);
	    	parent.image(collapse, x+5, y+5);
	    	parent.textAlign(LEFT, TOP);
	    	parent.text(publisher, x + 25, y + 3);
    	}
    	return totalHeight;
    }
    
    public static String arrayToString(Object[] array) {
    	String string = "";
    	for(int i = 0; i < array.length; i++) {
    		string += array[i] + ((i != array.length - 1)?", ":"");
    	}
    	return string;
    }
    
    public String getUser()
    {
    	return publisher;
    }
    
    public String getText()
    {
    	return text;
    }
    
    public int getID() {
    	return id;
    }
    
    protected int getTotalHeight() {
    	if(collapsed) return 25;
    	int totalHeight = height;
    	for(Comment comment : childCommentList)
    		totalHeight += comment.getTotalHeight() + CONSTANTS.COMMENT_MARGIN;
    	return totalHeight;
    }
    
    public int getBoxHeight() {
    	return boxHeight;
    }
    
    private String filterString(String text) {
    	StringBuffer result = new StringBuffer();
    	Matcher matcher = Pattern.compile("(\\\\n|<p>|&#34;|\\\\\"|&#62;|<\\\\/i>|<i>|\\\\u2013|\\\\u2019|&#x2;|&#x2f;|&#x2F;|&#x27;|&quot;|&gt;)").matcher(text);
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
    		return new String[] {"[deleted]", ""};
    	ArrayList<String> paragraph = new ArrayList<String>();
    	Matcher matcher = Pattern.compile("(.{0,"+(COMMENT_WIDTH_LIMIT-((layer>5)?10*(layer-5):0))+"})(?:\\s|$)").matcher(text);
    	while(matcher.find())
    		paragraph.add(matcher.group(0));
    	return paragraph.toArray(new String[0]);
    }
    
    public boolean checkCollapseClicked(int mouseX, int mouseY) {
    	if(mouseX < xPos + 20 && mouseX > xPos + 5 && mouseY > yPos + 5 && mouseY < yPos + 20)
    	{
    		if(collapsed)
    			collapse = Raaddit.commentCollapse;
    		else
    			collapse = Raaddit.commentOpen;
    		collapsed = !collapsed;
    		return true;
    	}
    	else
    		for(Comment comment : childCommentList)
    			if(comment.checkCollapseClicked(mouseX, mouseY))
    				break;
    	return false;
    }
    
    public int getLayer() {
    	return layer;
    }
}

