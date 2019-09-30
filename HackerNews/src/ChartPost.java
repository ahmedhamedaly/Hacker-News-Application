import java.util.ArrayList;
import processing.core.*;
import java.applet.*;
import java.lang.*;
import java.util.regex.*;

public class ChartPost extends PApplet {
	PApplet parent;
	
	ArrayList<Post> topPosts;

	int mainColor = color(69, 242, 135), 
		textColor = color(34, 31, 46), 
		popColor = color(247, 89, 176);
	
	int topPost = 0;
	int margin = 200;
	int barWidth = 2;
	
	int posts = -1;
	int comments = -1;
	String user = "";
	float scaling = 23;
	

	public ChartPost(PApplet parent, ArrayList<Object[]> userData)
	{
		this.parent = parent;
		for (int i = 0; i < userData.size(); i++) 
		{
			if((int)userData.get(i)[1] > topPost) 
			{
				topPost = (int)userData.get(i)[1];
			}
		}
		//System.out.println(topPost);
		/*
		if (userData.size() > 100) {
			for (int i = 0; i < userData.size(); i++) {
				int topPostTemp = (int) userData.get(i)[1];
				String topPostUser = (String) userData.get(i)[0];
				for (int j = 0; j < topPosts.size(); j++) {
					
				}
			}
		}
		*/
	}

	public void draw(ArrayList<Object[]> userData) {
		
		parent.textSize(14);
		parent.textAlign(RIGHT);
		parent.background(255);
		parent.strokeCap(SQUARE);
	
		for (int i = 0; i <= userData.size(); i++)
		{
			
			int grid = (parent.width - (2 * margin)) / userData.size() * i + margin;
			
			// x-axis
			if (i == 0 || i % 10 == 0) 
			{
				parent.strokeWeight(1);
				parent.stroke(textColor, 150);
				parent.fill(textColor, 150);
				parent.textAlign(CENTER);
				
				parent.line(grid, parent.height - margin, grid, parent.height - margin + 3);
				parent.text(i, grid, parent.height - margin + 15);
				parent.noFill();
			}

			// Drawing data lines
			
			if (i > 0)
			{
			posts = (int) userData.get(i - 1)[1];
			user = (String) userData.get(i - 1)[0];
			comments = (int) userData.get(i - 1)[2];
			
			scaling = (float) (posts * topPost/9000);
				if (scaling <= parent.height / 3 ) 
				{
					parent.stroke(mainColor);
					parent.strokeWeight(barWidth);
					parent.line(grid, parent.height - margin, grid, parent.height - margin - scaling);
					parent.strokeWeight(1);
				} else
				{
					parent.strokeWeight(barWidth);
					parent.stroke(popColor);
					parent.line(grid, parent.height - margin - parent.height/3, grid, parent.height - margin - scaling);
					parent.stroke(mainColor);
					parent.line(grid, parent.height - margin, grid, parent.height - margin - parent.height/3);
					parent.strokeWeight(1);
				}
			}

			if ((parent.mouseX > margin) && (parent.mouseX < parent.width - margin) && (parent.mouseX < grid + 3) && (parent.mouseX > grid - 3))
			{
				parent.textAlign(LEFT);
				if (abs(parent.mouseX - grid) < 3) 
				{
					//parent.textFont(mainFont);
					parent.stroke(textColor, 150);
					parent.line(grid, margin, grid, parent.height - margin - scaling);
					if (posts <= parent.height / 2)
					{
						parent.fill(popColor);
					} else 
					{
						parent.fill(mainColor);
					}

					// labelling
					if (i > 0) {
						parent.text("User: " + user, grid + 6, margin + 15);
						parent.fill(textColor, 255);
						parent.ellipse(grid, parent.height - margin - scaling, 5, 5);
						
						parent.stroke(textColor, 150);
						parent.noFill();
						parent.ellipse(grid, parent.height - margin - scaling, 11, 11);
						
						//parent.textFont(mainFont);
						parent.fill(textColor);
						parent.text("Posts: " + posts, grid + 6, margin + 35);
						parent.text("Comments: " + comments, grid + 6, margin + 55);
						parent.noFill();
					}

					//parent.textFont(mainFont);
				}
			}
			
		}

		// Draw x and y axis
		parent.stroke(textColor, 150);
		parent.line(margin, parent.height - margin, parent.width - margin, parent.height - margin);
		parent.textAlign(CENTER);
		parent.textSize(30);
		parent.fill(69);
		parent.text("Top 100 Posters", parent.width/2, 9*parent.height/10);
	}
	
	public String currentUser(ArrayList<Object[]> userData) 
	{
		String user = null;
		for (int i = 0; i < userData.size(); i++) {
			int grid = (parent.width - (2 * margin)) / userData.size() * i + margin;

			if ((parent.mouseX > margin) && (parent.mouseX < parent.width - margin) && (parent.mouseX < grid + 3) && (parent.mouseX > grid - 3)) {
				user = (String) userData.get(i - 1)[0];
			}
		}
		if (user == null)
			return null;
		return user;
	}
	
	public int currentPosts(ArrayList<Object[]> userData)
	{
		int posts = -1;
		for (int i = 0; i < userData.size(); i++) {
			int grid = (parent.width - (2 * margin)) / userData.size() * i + margin;

			if ((parent.mouseX > margin) && (parent.mouseX < parent.width - margin) && (parent.mouseX < grid + 3) && (parent.mouseX > grid - 3)) {
				posts = (int) userData.get(i - 1)[1];
			}
		}
		return posts;
	}
	
	public int currentComments(ArrayList<Object[]> userData)
	{
		int comments = -1;
		for (int i = 0; i < userData.size(); i++) {
			int grid = (parent.width - (2 * margin)) / userData.size() * i + margin;

			if ((parent.mouseX > margin) && (parent.mouseX < parent.width - margin) && (parent.mouseX < grid + 3) && (parent.mouseX > grid - 3)) {
				comments = (int) userData.get(i - 1)[2];
			}
		}
		return comments;
	}
}
