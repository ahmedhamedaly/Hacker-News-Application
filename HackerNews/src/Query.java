import java.util.ArrayList;
import java.util.regex.*;

import com.mysql.cj.xdevapi.Result;

import processing.core.PApplet;
import processing.core.PImage;

import java.sql.*;

// Authors:
//
//	Matthew Howard - Post and comment database
//	John Burke - User Database
//

public class Query {
	
	private static final int PAGE_LENGTH = 50;

	private ArrayList<Post> postList;
	private String currentQuery;
	private Connection connection;
	private int page;
	PImage commentImage;
	PImage postImage;
	private final String DATABASE = "jdbc:h2:./src/raaddit;DATABASE_TO_UPPER=false";
	private final String USERNAME = "sa";
	private final String PASSWORD = "";
	private String sortBy;
	private int nextID;

	public PApplet parent;

	// Query constructor. Initialises the connection to the database at creates an initial query
	Query(PApplet parent, PImage commentImage, PImage postImage) {
		this.parent = parent;
		this.commentImage = commentImage;
		this.postImage = postImage;
		initialiseConnection();
		sortBy = " ORDER BY postTime DESC";
		currentQuery = "SELECT * FROM posts";
		page = 1;
		nextID = getNextID();
	}

	// Creates a connection to the MySQL database
	private void initialiseConnection() {
		connection = null;
		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// Parses the string entered in the search bar and sets currentQuery to an
	// ArrayList of the relevant posts
	public void newQuery(String searchQuery) {
		if (!searchQuery.isEmpty()) {
			ArrayList<String> queryFields = new ArrayList<String>();
			ArrayList<String> queryTerms = new ArrayList<String>();
			Matcher option = Pattern.compile("( ?(\\w*):)(\\w*)").matcher(searchQuery); // Find search terms in the format field:term e.g. username:pg
			if (option.find()) {
				// Add the results to two ArrayLists
				queryFields.add(option.group(2));
				queryTerms.add(option.group(3));
				int matchCount = 1;
				while (option.find()) {
					queryFields.add(option.group(2 + matchCount));
					queryTerms.add(option.group(3 + matchCount));
					matchCount++;
				}
				String search = option.replaceAll("");
				if (!search.isEmpty())
					queryFields.add("");
				queryTerms.add(search);
			} else {
				// Add the remaining search terms in the default field, which searches the titles
				queryFields.add("");
				queryTerms.add(searchQuery);
			}
			// Send the ArrayLists to the createQuery function
			String query = createQuery(queryFields.toArray(new String[0]), queryTerms.toArray(new String[0]));
			currentQuery = query;
			System.out.println(String.format("%s%s LIMIT %d, %d", query, sortBy, 50 * (page - 1), PAGE_LENGTH));
			// Query the database using the query + the current order + the current page
			postList = processQuery(String.format("%s%s LIMIT %d, %d", query, sortBy, 50 * (page - 1), PAGE_LENGTH));
		} else
			postList = processQuery("SELECT * FROM posts ORDER BY postTime DESC LIMIT 0, 50"); // if an empty search is passed, use a default query.
	}
	public ArrayList<String> websiteComposition() {
		ArrayList<String> queryResultList = new ArrayList<String>();
		try {
			Statement sqlQuery = connection.createStatement();
			String websiteQuery = String.format("SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(url, '/',3), '/', -1) as website FROM(%s AND url != \"\" AND url != \"\\\"\"%s LIMIT 0, 1000) as websites", currentQuery, sortBy);
			sqlQuery.execute(websiteQuery);
			ResultSet queryResult = sqlQuery.getResultSet();
			while(queryResult.next()) {
				String url = queryResult.getString("website");
				queryResultList.add(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryResultList;
	}
	
	public FrequentWebsites[] websitesCount() {
		ArrayList<FrequentWebsites> tempCount = new ArrayList<FrequentWebsites>();
		try {
			Statement sqlQuery = connection.createStatement();
			String websiteQuery = String.format("SELECT website, COUNT(website) as frequency FROM(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(url, '/',3), '/', -1) as website FROM(%s url != \"\" AND url != \"\\\"\"%s LIMIT 0, 1000) as websites) as websiteCount GROUP BY website ORDER BY frequency DESC LIMIT 10;", ((currentQuery.contains("WHERE"))?currentQuery + " AND ":currentQuery + " WHERE "), sortBy);
			sqlQuery.execute(websiteQuery);
			ResultSet queryResult = sqlQuery.getResultSet();
			while(queryResult.next()) {
				String url = queryResult.getString("website");
				int frequency = queryResult.getInt("frequency");
				tempCount.add(new FrequentWebsites(url, frequency));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempCount.toArray(new FrequentWebsites[0]);
	}
	// Increases the page by one and refreshes the query
	public void nextPage() {
		page++;
		System.out.println(currentQuery);
		postList = processQuery(String.format("%s%s LIMIT %d, %d", currentQuery, sortBy, 50 * (page - 1), PAGE_LENGTH));
		System.out.println(page);
	}

	// Decreases the page by one and refreshes the query
	public void previousPage() {
		if (page > 1) {
			page--;
			postList = processQuery(
					String.format("%s%s LIMIT %d, %d", currentQuery, sortBy, 50 * (page - 1), PAGE_LENGTH));
		}
	}

	// Returns an ArrayList of the posts of the current query
	public ArrayList<Post> getPostList() {
		return postList;
	}

	// Creates a valid SQL query from the array of fields and terms passed to it by
	// newQuery()
	private String createQuery(String[] fields, String[] searchTerms) {
		String query = "SELECT * FROM posts";
		int score;
		if(!searchTerms[0].isEmpty())
		{
			for (int term = 0; term < fields.length; term++) { // For each of the fields, add the relevant condition to the query
				if (term == 0)
					query += " WHERE ";
				switch (fields[term].toLowerCase()) {
				case "username":
					query += ("username='" + searchTerms[term] + "'");
					break;
				case "minimum_score":
					score = Integer.parseInt(searchTerms[term]);
					query += "score>" + score;
					break;
				case "maximum_score":
					score = Integer.parseInt(searchTerms[term]);
					query += "score<" + score;
					break;
				default:
					query += "title like \'%" + searchTerms[term] + "%\'";
					break;
				}
				if (term < fields.length - 1)
					query += " AND ";
			}
		}
		// query;
		return query;
	}

	public void setSortToOldestPosts() {
		sortBy = " ORDER BY postTime ASC";
		resetPage();
	}

	public void setSortToNewestPosts() {
		sortBy = " ORDER BY postTime DESC";
		resetPage();
	}

	public void setSortToHighestScore() {
		sortBy = " ORDER BY score DESC";
		resetPage();
	}

	// Returns the first 100 posts with the highest score
	public ArrayList<Post> getTop100Posts() {
		ArrayList<Post> queryResultList = new ArrayList<Post>();
		try {
			Statement sqlQuery = connection.createStatement();
			sqlQuery.execute("SELECT * FROM posts ORDER BY score DESC LIMIT 0, 100");
			ResultSet queryResult = sqlQuery.getResultSet();
			while(queryResult.next()) {
				int id = queryResult.getInt("id");
				int descendants = queryResult.getInt("descendants");
				String title = queryResult.getString("title");
				String username = queryResult.getString("username");
				int score = queryResult.getInt("score");
				int time = queryResult.getInt("postTime");
				String url = queryResult.getString("url");
				Post post = new Post(CONSTANTS.COLOUR_PALATTE_1, parent, score, username, time, id, title, "post", descendants, url, null, commentImage,postImage);
				queryResultList.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryResultList;
	}
	
	// Queries the MySQL database with the given query and creates and returns an
	// ArrayList of Post objects.
	private ArrayList<Post> processQuery(String query) {
		ArrayList<Post> queryResultList = new ArrayList<Post>();
		try {
			Statement sqlQuery = connection.createStatement();
			sqlQuery.execute(query);
			ResultSet queryResult = sqlQuery.getResultSet();
			while (queryResult.next()) {
				int id = queryResult.getInt("id");
				int descendants = queryResult.getInt("descendants");
				String title = queryResult.getString("title");
				String username = queryResult.getString("username");
				int score = queryResult.getInt("score");
				int time = queryResult.getInt("postTime");
				String url = queryResult.getString("url");
				String type = "link";
				if(url.equals("\""))
				{
					type = "text";
					url = queryResult.getString("postsText");
				}
				Post post = new Post(CONSTANTS.COLOUR_PALATTE_GREYS, parent, score, username, time, id, title, type,
						descendants, url, null, commentImage, postImage);
				queryResultList.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryResultList;
	}

	public boolean userMatch(String username, String password) {
		try {
			Statement sqlQuery = connection.createStatement();
			String query = "SELECT user_password FROM users WHERE user_username='" + username + "'";
			//System.out.println(query);
			sqlQuery.execute(query);
			ResultSet queryResult = sqlQuery.getResultSet();
			queryResult.next();
			String result = queryResult.getString("user_password");
			//System.out.println(result + " result");
			if (result != null) {
				if (result.equals(password))
					return true;
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return false;
	}
	
	public boolean addUser(String username, String password) {
		try {
			Statement sqlQuery = connection.createStatement();
			String query = "SELECT * FROM users WHERE user_username='" + username + "'";
			sqlQuery.execute(query);
			ResultSet queryResult = sqlQuery.getResultSet();
			queryResult.next();
			String result = queryResult.getString("user_username");
			if (result!=null)
				return false;
		}
		catch (Exception e)
		{
			
		}
		try {
			Statement sqlQuery = connection.createStatement();
			String query = "INSERT INTO users(user_username, user_password)" + " VALUES ('" + username +  "', '" + password + "');";
			sqlQuery.execute(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// Finds the comments of the passed post and returns them as an ArrayList of
	// Comment objects
	public ArrayList<Comment> getComments(Post post) {
		int id = post.getID();
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement query = connection.createStatement();
			query.execute("SELECT * FROM comments WHERE parent=" + id);
			ResultSet result = query.getResultSet();
			while (result.next()) {
				String text = result.getString("body");
				int time = result.getInt("postTime");
				int commentID = result.getInt("id");
				String username = result.getString("username");
				Comment comment = new Comment(parent, username, time, commentID, text, "Comment", id, null, 0);
				commentList.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commentList;
	}

	// Finds the comments of the passed comment and returns them as an ArrayList of
	// Comment objects
	public ArrayList<Comment> getComments(Comment parentComment) {
		int id = parentComment.getID();
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement query = connection.createStatement();
			query.execute("SELECT * FROM comments WHERE parent=" + id);
			ResultSet result = query.getResultSet();
			while (result.next()) {
				String text = result.getString("body");
				int time = result.getInt("postTime");
				int commentID = result.getInt("id");
				String username = result.getString("username");
				Comment comment = new Comment(parent, username, time, commentID, text, "Comment", id, null, parentComment.getLayer() + 1);
				commentList.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commentList;
	}

	// Returns the data of the users in the passed post list. Used for the graphs.
	// Data is returned as an arrayList of arrays containing the username at index 0,
	// the number of that user's posts in index 1, and the number of comments at index 2
	public ArrayList<Object[]> getUserData(ArrayList<Post> postList){
		ArrayList<Object[]> userData = new ArrayList<Object[]>();
		try {
			PreparedStatement getPostCount = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE username=?");
			PreparedStatement getCommentCount = connection.prepareStatement("SELECT COUNT(*) FROM comments WHERE username=?");
		for(Post post : postList)
		{
			String username = post.getUser();
			getPostCount.setString(1, username);
			getPostCount.execute();
			getCommentCount.setString(1, username);
			getCommentCount.execute();
			ResultSet postResultSet = getPostCount.getResultSet();
			ResultSet commentResultSet = getCommentCount.getResultSet();
			int postCount = (postResultSet.next())?getPostCount.getResultSet().getInt("COUNT(*)"):0;
			int commentCount = (commentResultSet.next())?getCommentCount.getResultSet().getInt("COUNT(*)"):0;
			userData.add(new Object[] {username, postCount, commentCount});
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userData;
	}
	
	// Resets the current page to 1 and refreshes the current query
	public void resetPage() {
		page = 1;
		postList = processQuery(String.format("%s%s LIMIT %d, %d", currentQuery, sortBy, 50 * (page - 1), PAGE_LENGTH));
	}
	
	public boolean sendPost(String title, String URL, String username) {
			String queryString = "INSERT INTO posts(id, title, username, url, postTime, score)" + " VALUES ('";
			Long time = System.currentTimeMillis() / 1000;
			String temp = Long.toString(time);
			int time2 = Integer.valueOf(temp);
			int score = 0;
			queryString += nextID++ + ", " + title + "', '" + username + "', '" + URL + "', '" + time2 + "', '" + score + "');";
			Statement query;
			try {
				query = connection.createStatement();
				query.execute(queryString);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;
	}
	
	public int getNextID() {
		int id = 0;
		try {
			Statement query = connection.createStatement();
			query.execute("select greatest(max(posts.id), max(comments.id)) as nextID from posts, comments");
			ResultSet result = query.getResultSet();
			if(result.next())
				id = result.getInt("nextID") + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public int getPostCount(String username) {
		int postCount = 0;
		try
		{
			Statement query = connection.createStatement();
			query.execute(String.format("SELECT COUNT(*) as postCount FROM posts WHERE username = '%s'", username));
			ResultSet result = query.getResultSet();
			if(result.next())
				postCount = result.getInt("postCount");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return postCount;
	}
	
	public int getUserScore(String username) {
		int userScore = 0;
		try
		{
			Statement query = connection.createStatement();
			query.execute(String.format("SELECT SUM(score) as userScore FROM posts WHERE username = '%s'", username));
			ResultSet result = query.getResultSet();
			if(result.next())
				userScore = result.getInt("userScore");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return userScore;
	}
	
	public void commitChanges() {
		try {
			PreparedStatement updateScore = connection.prepareStatement("UPDATE posts SET score = ? WHERE id = ?");
			for(Post post : postList)
			{
				int id = post.getID();
				int score = post.getScore();
				updateScore.setInt(1, score);
				updateScore.setInt(2, id);
				updateScore.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}