import java.util.ArrayList;
import processing.core.*;

public class statsGraph extends PApplet {

	int x, y, event;
	final int EVENT_BUTTON1 = 1;
	final int EVENT_BUTTON2 = 2;
	final int EVENT_BUTTON3 = 3;
	public static final int EVENT_NULL = 0;
	ArrayList widgetList;
	int screenX = 1000, screenY = 800;
	Widget widget1, widget2, widget3;
	PApplet parent;

	public statsGraph(PApplet parent) {
		this.parent = parent;
		widgetList = new ArrayList();
		widget1 = new Widget(200, 3 / 4 * screenX, 180, 40, "Users", EVENT_BUTTON1, this);
		widget2 = new Widget(400, 3 / 4 * screenX, 180, 40, "Posts", EVENT_BUTTON2, this);
		widget3 = new Widget(600, 3 / 4 * screenX, 180, 40, "Score", EVENT_BUTTON3, this);

		widgetList.add(widget1);
		widgetList.add(widget2);
		widgetList.add(widget3);
	}

	public void draw(ArrayList<Object[]> userData) {
		for (int i = 0; i < widgetList.size(); i++) {
			Widget aWidget = (Widget) widgetList.get(i);
			aWidget.draw();

		}
	}
}

class Widget extends PApplet {
	PApplet parent;
	int x, y;
	String label;
	int event;
	int widgetColor, labelColor;
	

	Widget(int x, int y, int width, int height, String label, int event, PApplet parent) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
		this.event = event;
		labelColor = color(0);
		this.parent = parent;
	}

	public void draw() {
		mouseMoved();

		parent.strokeWeight(3);
		parent.fill(widgetColor);
		parent.rect(x, y, width, height);
		parent.fill(labelColor);
		parent.text(label, x + 10, y + height - 10);
	}

	public int getEvent(int mX, int mY) {
		if (mX > x && mX < x + width && mY > y && mY < y + height) {
			return event;
		}
		return 0;
	}

	public void mouseMoved() {
		if (getEvent(parent.mouseX, parent.mouseY) != 0)
			parent.stroke(255);
		else
			parent.stroke(0);
	}
}
