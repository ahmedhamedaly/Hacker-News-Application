import java.util.ArrayList;

import processing.core.*;

public class CommentPage extends PApplet {
	PApplet parent;
	Boolean page = false;
	Post post;
	ArrayList<Comment> commentList;
	private int[][] colourScheme;
	private Time timeObject;
	private float w;
	private float h;
	private float totalHeight;
	
	public CommentPage(PApplet parent, Post post) {
		this.parent = parent;
		this.post = post;
		commentList = Raaddit.query.getComments(post);
		w = 0;
		totalHeight = 0;
	}

	public void draw(float scroll) {
		float commentsPosition = post.drawComments(100 - scroll) + 100;
		for(int i = 0; i < commentList.size(); i++) {
			commentsPosition += commentList.get(i).draw(150, (int)(commentsPosition + i*CONSTANTS.COMMENT_MARGIN - scroll));
		}
		totalHeight = commentsPosition;
	}
	
	public Post currentPost() {
		return post;
	}
	
	public void checkCommentCollapseClicked(int mouseX, int mouseY) {
		for(Comment comment : commentList)
			if(comment.checkCollapseClicked(mouseX, mouseY))
				break;
	}
	
	public float totalHeight() {
		return totalHeight;
	}
}
