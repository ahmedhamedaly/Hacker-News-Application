import java.util.ArrayList;

import processing.core.*;

public class ChartComment extends PApplet {
	PApplet parent;
	int mainColor = color(69, 242, 135), textColor = color(34, 31, 46), popColor = color(247, 89, 176);

	int margin = 200;
	int barWidth = 2;
	int topComment = 0;

	int comments = -1;
	String user = "";
	float scaling = -1;

	public ChartComment(PApplet parent, ArrayList<Object[]> userData) {
		this.parent = parent;

		for (int i = 0; i < userData.size(); i++) {
			if ((int) userData.get(i)[1] > topComment) {
				topComment = (int) userData.get(i)[1];
			}
		}
		// System.out.println(topComment);
	}

	public void draw(ArrayList<Object[]> userData) {
		parent.textSize(14);
		parent.textAlign(RIGHT);
		parent.background(255);
		parent.strokeCap(SQUARE);

		// parent.textFont(mainFont);

		// parent.textFont(mainFont);
		// parent.textFont(mainFont);
		parent.fill(textColor);
		parent.stroke(0);

		//parent.text("Bar Chart - Comments Data", margin, margin - 40);
		//parent.line(margin, margin - 35, parent.width - margin, margin - 35);
		// parent.textFont(mainFont);

		// y axis
		for (int y = 0; y <= topComment; y++) {
			int grid2 = (parent.height - (2 * margin)) / topComment * y + margin;

			if (y == 0 || y % (topComment / 10) == 0) {
				parent.stroke(textColor, 150);
				parent.line(margin, grid2, margin - 3, grid2);
				parent.fill(textColor, 150);
				// parent.textFont(mainFont);

				parent.textAlign(RIGHT);
				parent.text(topComment - y, margin - 5, grid2 + 3);
				parent.noFill();
			}
		}

		for (int i = 0; i <= userData.size(); i++) {

			int grid = (parent.width - (2 * margin)) / userData.size() * i + margin;
			// x-axis

			if (i == 0 || i % 10 == 0) {
				parent.strokeWeight(1);
				parent.stroke(textColor, 150);
				parent.fill(textColor, 150);
				parent.textAlign(CENTER);

				parent.line(grid, parent.height - margin, grid, parent.height - margin + 3);
				parent.text(i, grid, parent.height - margin + 15);
				parent.noFill();
			}

			// Drawing data lines

			if (i > 0) {
				comments = (int) userData.get(i - 1)[1];
				user = (String) userData.get(i - 1)[0];
				scaling = (float) (comments * topComment / 2);
				if (scaling <= parent.height / 3) {
					parent.stroke(mainColor);
					parent.strokeWeight(barWidth);
					parent.line(grid, parent.height - margin, grid, parent.height - margin - scaling);
					parent.strokeWeight(1);
				} else {
					parent.strokeWeight(barWidth);
					parent.stroke(popColor);
					parent.line(grid, parent.height - margin - parent.height / 3, grid,
							parent.height - margin - scaling);
					parent.stroke(mainColor);
					parent.line(grid, parent.height - margin, grid, parent.height - margin - parent.height / 3);
					parent.strokeWeight(1);
				}
			}

			// Hover labels

			if ((parent.mouseX > margin) && (parent.mouseX < parent.width - margin) && (parent.mouseX < grid + 3)
					&& (parent.mouseX > grid - 3)) {
				parent.textAlign(LEFT);

				if (abs(parent.mouseX - grid) < 3) {
					// parent.textFont(mainFont);
					parent.stroke(textColor, 150);
					parent.line(grid, margin, grid, parent.height - margin - scaling);

					if (comments <= parent.height / 2) {
						parent.fill(popColor);
					} else {
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
						// parent.textFont(mainFont);
						parent.fill(textColor);
						parent.text("Comments: " + comments, grid + 6, margin + 35);
						parent.noFill();
					}

					// parent.textFont(mainFont);
				}
			}

		}

		// Draw x and y axis
		parent.stroke(textColor, 150);
		parent.line(margin, parent.height - margin, parent.width - margin, parent.height - margin);
		parent.line(margin, margin, margin, parent.height - margin);

	}

	public int currentComments(ArrayList<Object[]> userData) {
		int comments = -1;
		for (int i = 0; i < userData.size(); i++) {
			int grid = (parent.width - (2 * margin)) / userData.size() * i + margin;

			if ((parent.mouseX > margin) && (parent.mouseX < parent.width - margin) && (parent.mouseX < grid + 3) && (parent.mouseX > grid - 3)) {
				comments = (int) userData.get(i - 1)[1];
			}
		}
		return comments;
	}

}
