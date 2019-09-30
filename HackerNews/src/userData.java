import java.util.ArrayList;

import processing.core.PApplet;

public class userData extends PApplet{
	//ArrayList<Object[]> userData;
	//ArrayList<String> userList;
	//ArrayList<Integer> userPostNumber;
	
	// returns user post count and scores from list of posts
	public static ArrayList<Object[]> getUserInformationFromPosts(ArrayList<Post> userPosts) {
		ArrayList<Object[]> userData = new ArrayList<Object[]>();
		ArrayList<String> userList = new ArrayList<String>();;
		ArrayList<Integer> userPostNumber = new ArrayList<Integer>();
		ArrayList<Integer> userScore = new ArrayList<Integer>();
		String user;
		for(int i = 0; i < userPosts.size(); i++)
		{
			Post currentPost = userPosts.get(i);
			user = currentPost.getUser();
			if (userList.contains(user))
			{
				int currentIndex = userList.indexOf(user);
				int postNumber = userPostNumber.get(currentIndex);
				postNumber++;
				userPostNumber.set(currentIndex, postNumber);
				int oldUserScore = userScore.get(currentIndex);
				oldUserScore+=currentPost.getScore();
				userScore.set(currentIndex, oldUserScore);
			}
			else {
				userList.add(user);
				userPostNumber.add(1);
				userScore.add(currentPost.getScore());
			}
		}
		
		for(int i = 0; i < userList.size(); i++)
		{
			Object[] userObject = new Object[3];
			
			user = userList.get(i);
			int userPostAmount = userPostNumber.get(i);
			int userScoreAmount = userScore.get(i);
			userObject[0] = user;
			userObject[1] = userPostAmount;
			userObject[2] = userScoreAmount;
			userData.add(userObject);
		}
		
		return userData;
	}
	
	public static ArrayList<Object[]> getUserInformationFromComments(ArrayList<Comment> userComments) {
		ArrayList<Object[]> userData = new ArrayList<Object[]>();
		ArrayList<String> userList = new ArrayList<String>();;
		ArrayList<Integer> userCommentNumber = new ArrayList<Integer>();
		String user;
		for(int i = 0; i < userComments.size(); i++)
		{
			Comment currentComment = userComments.get(i);
			user = currentComment.getUser();
			if (userList.contains(user))
			{
				int currentIndex = userList.indexOf(user);
				int commentNumber = userCommentNumber.get(currentIndex);
				commentNumber++;
				userCommentNumber.set(currentIndex, commentNumber);
			}
			else {
				userList.add(user);
				userCommentNumber.add(1);
			}
		}
		
		for(int i = 0; i < userList.size(); i++)
		{
			Object[] userObject = new Object[2];
			
			user = userList.get(i);
			int userPostAmount = userCommentNumber.get(i);
			userObject[0] = user;
			userObject[1] = userPostAmount;
			userData.add(userObject);
		}
		
		return userData;
	}
	
	

}
