
import processing.core.PApplet;
import processing.core.PImage;

public class CommentWidget extends PApplet{
	PApplet parent;
	boolean activated = false;
	PImage commentImage;
	PImage postImage;
	public CommentWidget(PApplet parent, PImage commentImage,PImage postImage) {
		this.parent = parent;
		this.commentImage = commentImage;
		this.postImage = postImage;
	}
	
	public void draw(float x, float y) {
		if (activated==false) {
		parent.image(commentImage, x-12, y-12);
		}else if (activated==true) {
			parent.image(postImage, x-12, y-12);
		}
	}
	
	public void toggleActive() {
		activated = !activated;
	}
	
	public boolean isActive() {
		return activated;
	}
}
