import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONConverter {

    static final String RAADDITDB = "jdbc:mysql://localhost/news?useSSL=false";
    static final String USERNAME = "raaddit";
    static final String PASSWORD = "password";
    static final int length = 0x4000000;

    public static void convertJSON() {

    Connection conn = null;
    PreparedStatement insertPost = null;
    PreparedStatement insertComment = null;
    long startTime = System.currentTimeMillis();
    try
    {
        System.out.println("Connecting to database...");
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(RAADDITDB, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
        System.out.println("Preparing statements...");
        insertPost = conn.prepareStatement("INSERT INTO posts VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
        insertComment = conn.prepareStatement("INSERT INTO comments VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        try
        {
            System.out.println("Reading file...");
            FileReader fileReader = new FileReader("src/hackerNewsFull.json");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean endOfFile = false;
            while (!endOfFile)
            {
                String entry = bufferedReader.readLine();
                if (entry == null)
                    endOfFile = true;
                else
                {
                    String type = (parseJsonKey(entry, "type"));
                    if (type.equals("story"))
                    {
                        String idString = parseJsonKey(entry, "id");
                        int id = (!idString.isEmpty()) ? Integer.parseInt(idString) : 0;
                        insertPost.setInt(1, id);

                        String title = (parseJsonKey(entry, "title"));
                        insertPost.setString(2, title);

                        String username = (parseJsonKey(entry, "by"));
                        insertPost.setString(3, username);

                        String timeString = parseJsonKey(entry, "time");
                        int time = (!timeString.isEmpty()) ? Integer.parseInt(timeString) : 0;
                        insertPost.setInt(4, time);

                        String url = parseJsonKey(entry, "url");
                        insertPost.setString(5, url);

//                        String descendantsString = parseJsonKey(entry, "descendants");
//                        int descendants = (!descendantsString.isEmpty()) ? Integer.parseInt(descendantsString) : 0;
                        insertPost.setInt(6, 0);

                        String kids = (parseJsonKey(entry, "kids"));
                        insertPost.setString(7, kids);

                        String scoreString = parseJsonKey(entry, "score");
                        int score = (!scoreString.isEmpty()) ? Integer.parseInt(scoreString) : 0;
                        insertPost.setInt(8, score);

                        String text = parseJsonKey(entry, "text");
                        insertPost.setString(9, text);

                        insertPost.execute();

                        System.out.println(id);
                    } else if (type.equals("comment"))
                    {
                        int id = 0;
                        String idString = parseJsonKey(entry, "id");
                        try
                        {
                            id = (!idString.isEmpty()) ? Integer.parseInt(idString) : 0;
                            insertComment.setInt(1, id);
                        }
                        catch(NumberFormatException e)
                        {
                            idString = idString.replaceAll("\\s+", "");
                            id = (!idString.isEmpty()) ? Integer.parseInt(idString) : 0;
                            insertComment.setInt(1, id);
                        }
                        String text = (parseJsonKey(entry, "text"));
                        insertComment.setString(2, text);

                        String username = (parseJsonKey(entry, "by"));
                        insertComment.setString(3, username);

                        //String scoreString = parseJsonKey(entry, "score");
                        //int score = (scoreString.isEmpty()) ? Integer.parseInt(scoreString) : 0;
                        insertComment.setInt(4, 0);

                        String kids = (parseJsonKey(entry, "kids"));
                        insertComment.setString(5, kids);

                        String parentString = parseJsonKey(entry, "parent");
                        int parent = (!parentString.isEmpty()) ? Integer.parseInt(parentString) : 0;
                        insertComment.setInt(6, parent);

                        String timeString = parseJsonKey(entry, "time");
                        int time = (!timeString.isEmpty()) ? Integer.parseInt(timeString) : 0;
                        insertComment.setInt(7, time);

                        //String descendantsString = parseJsonKey(entry, "descendants");
                        //int descendants = (descendantsString.isEmpty()) ? Integer.parseInt(descendantsString) : 0;
                        insertComment.setInt(8, 0);

                        insertComment.execute();

                        System.out.println(id);
                    }
                }
            }
            conn.commit();
        } catch (java.io.FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        insertComment.close();
        insertPost.close();
        conn.close();
    } catch (SQLException se)
    {
        se.printStackTrace();
    } catch (Exception e)
    {
        e.printStackTrace();
    } finally
    {
        try
        {
            if (insertComment != null)
                insertComment.close();
            if (insertComment != null)
                insertPost.close();
        } catch (SQLException se2)
        {
        }
        try
        {
            if (conn != null)
                conn.close();
        } catch (SQLException se)
        {
            se.printStackTrace();
        }
    }

    System.out.printf("%dms", System.currentTimeMillis()-startTime);
    }

    public static String parseJsonKey(String object, String key){
    Matcher keyMatcher = Pattern.compile("(\""+key+"\")(?:: [\"\\[]?)(.+?)(?=[\"\\]]?((?:, +\"\\w*\": )|(?: })))").matcher(object);
    String value = "";
    if (keyMatcher.find())
    {
        value = keyMatcher.group(2);
        return value;
    } else
    {
        return value;
    }
    }
}