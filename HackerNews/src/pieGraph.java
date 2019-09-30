import processing.core.*;

public class pieGraph extends PApplet {

	PApplet parent;
	float diameter;
	float prevAngle = 0;
	int[] colors;
	String user;
	float posts;
	float comments;
	float[] angles;
	int xOffset = 20;
	int yOffset = 8;
	int margin = 50;
	PGraphics pieGraph, displayPie;

	public pieGraph(PApplet parent, String user, int posts, int comments) {
		this.parent = parent;

		this.posts = posts;
		this.comments = comments;
		this.user = user;

		float postsInAngle = ((float) (this.posts / (this.posts + this.comments)) * 360);
		float commentsInAngle = ((float) (this.comments / (this.posts + this.comments)) * 360);
		angles = new float[] { postsInAngle, commentsInAngle };

		diameter = (float) (min(parent.width, parent.height) * 0.75);
		colors = new int[angles.length];

		for (int i = 0; i < angles.length; i++) {
			int r = (int) random(255);
			int g = (int) random(255);
			int b = (int) random(255);

			if (r > 190)
				r -= 75;
			if (r < 65)
				r += 75;

			if (g > 190)
				g -= 75;
			if (g < 65)
				g += 75;

			if (b > 190)
				b -= 75;
			if (b < 65)
				b += 75;

			int randomColor = color(r, g, b);
			colors[i] = randomColor;
		}

		pieGraph = parent.createGraphics(parent.width, parent.height);
		displayPie = parent.createGraphics(parent.width, parent.height);

		pieGraph.beginDraw();

		for (int i = 0; i < angles.length; i++) {
			pieGraph.fill(colors[i]);
			pieGraph.arc(parent.width / 2, parent.height / 2, diameter, diameter, prevAngle,
					prevAngle + radians(angles[i]));
			prevAngle += radians(angles[i]);
		}

		pieGraph.endDraw();

		displayPie.beginDraw();

		for (int i = 0; i < angles.length; i++) {
			displayPie.fill(0);
			displayPie.stroke(150);
			displayPie.arc(parent.width / 2, parent.height / 2, diameter, diameter, prevAngle,
					prevAngle + radians(angles[i]));

			displayPie.fill(colors[i]);
			displayPie.arc(parent.width / 2, parent.height / 2, diameter, diameter, prevAngle + radians(1),
					prevAngle + radians(angles[i]));
			prevAngle += radians(angles[i]);
		}

		displayPie.endDraw();

	}

	public void draw() {
		parent.background(220);
		parent.image(displayPie, 0, 0);

		parent.textSize(40);
		parent.textAlign(LEFT, CENTER);
		parent.strokeWeight(4);
		parent.fill(100);

		parent.text("User: " + "'" + user + "'", (float) (0.02 * parent.width), (float) (0.4*parent.height));

		for (int i = 0; i < angles.length; i++) {
			if (i == 0) {
			      parent.fill(colors[i]);
			      parent.rect(50, 3*parent.height/4, 20, 20);
			      parent.fill(127);
			      parent.textAlign(LEFT);
			      parent.textSize(14);
			      parent.text("Posts = " + (int)posts, 60 + 20, 3*parent.height/4 + 15);
			    } else if (i == 1) {
			      parent.fill(colors[i]);
			      parent.rect(50, 3*parent.height/4 + 40, 20, 20);
			      parent.fill(127);
			      parent.textAlign(LEFT);
			      parent.textSize(14);
			      parent.text("Comments = " + (int)comments, 60 + 20, 3*parent.height/4 + 55);
			    }
			if (isMouseOver(colors[i])) {

				parent.textSize(15);
				parent.textAlign(LEFT);
				parent.fill(0);

				if (i == 0) {
					parent.text("Posts: " + (int) posts, parent.mouseX + xOffset, parent.mouseY + yOffset);
				}

				else if (i == 1) {
					parent.text("Comments: " + (int) comments, parent.mouseX + xOffset, parent.mouseY + yOffset);
				}
			}
		}
	}

	public boolean isMouseOver(int colorSector) {
		if (pieGraph.get(parent.mouseX, parent.mouseY) == colorSector)
			return true;
		return false;
	}
}
