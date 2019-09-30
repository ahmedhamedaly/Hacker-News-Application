import processing.core.PApplet;

public class ScrollWheelWidget extends PApplet{
	PApplet parent;
	float y;
	/*boolean activated = false;
	final int time = 60;
	int timeO = 0;*/
	public ScrollWheelWidget(PApplet parent) {
		this.parent = parent;
	}
	public void drawWheel (){
		parent.fill(225);
		parent.stroke(180);
		parent.rect(1900, 80, 20, 1000,18);
		if (y<=80) y = 80;
		else if (y>=1060) y = 1060;
		parent.fill(80);
		parent.noStroke();
		parent.rect(1900, y, 20, 1000/50,18);
	}
	public void move(float mouseY) {
		y = mouseY;
	}
	//Buffer for time 
	/*public void keepOnScreen () {
		if (activated==true&&timeO<=0) {
			timeO = time;
			System.out.println("oi");
		}
	}*/
}
