package Apppackage;

import com.restfb.types.Comment;
import com.restfb.types.Post.Comments;
import com.restfb.types.Post;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
/*import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;*/

public class DatabaseManager {

    private Connection con;
    private Statement st;
    private ResultSet res;
    private ResultSet commentsInDB;
    private ResultSet tweetInDB;

    public boolean verifyUser(String userName, String password) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Error 34: " + ex);
        }
        try {
            String quary = "select * from Users";
            res = st.executeQuery(quary);
            while (res.next()) {
                if (userName.equals(res.getString("userName")) && password.equals(res.getString("password"))) {
                    return true;
                }

            }

        } catch (Exception ex) {
            System.out.println("Error 46: " + ex);
        }
        return false;
    }

    public String addFBCommentsToDB(Post.Comments coms) {
       String out = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
            st = con.createStatement();
        } catch (Exception ex) {
            out = out + "\nError 61: " + ex;
            return out;
        }
        try {
            String quary = "SELECT * FROM FbComments";
            commentsInDB = st.executeQuery(quary);
        } catch (Exception ex) {
            out = out + "\nError idChecker: " + ex;
            return out;
        }
        try {
            List<Comment> comment = coms.getData();
            for (Comment c : comment) {
                out = out + c.getMessage();
                boolean isInDB = true;
                boolean first = commentsInDB.first();
                while (commentsInDB.next()) {
                    if (commentsInDB.getString("commentID").equals(c.getId())) {
                        isInDB = false;
                        out = out + "\n - Comment was found in the database.";
                        break;
                    }
                }
                if (isInDB) {
                    out = out + "\n - Comment is not yet in the database.";
                    String mood = checkCommentMood(c);
                    System.out.println(mood);
                   
                    
                    //java.sql.Date sqlDate = new java.sql.Date(c.getCreatedTime().getTime());
                    //Post.Comments replies = c.getComments();
                    try {
                        /*Class.forName("com.mysql.jdbc.Driver");

                        con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest", "barba", "Ruggenmerg");
                        st = con.createStatement();*/
                        c.setMessage(c.getMessage().replaceAll("'", "."));
                        String query = "INSERT INTO FbComments (commentID, commentBody, likes, replies, mood, dates, times ) VALUES('" + c.getId() + "', '" + c.getMessage() + "', " + c.getLikeCount() + ", " + 0 + ", '" + mood + "','"+ c.getCreatedTime().getYear() + ":" + c.getCreatedTime().getMonth() + ":" + c.getCreatedTime().getDate()  + "','" + c.getCreatedTime().getHours() + ":" + c.getCreatedTime().getMinutes() + ":" + c.getCreatedTime().getSeconds() +"')";
                        st.execute(query);
                        
                       
                        out = out + "\n - " + c.getId() + " was added to the Database.";
                    } catch (Exception ex) {
                        out = out + "\nError 74: " + ex;
                        return out;
                    }
                }

            }
        } catch (Exception e) {
            return out;
        }
        return out;
    }
/*
    public void addTweetsToDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Error 61: " + ex);
        }
        try {
            String quary = "SELECT * FROM tweets";
            tweetInDB = st.executeQuery(quary);
        } catch (Exception ex) {
            System.out.println("Error idChecker: " + ex);
        }
        try {
            List<Tweets> tweet = Tweet.getData();
            for (Tweet t : comment) {
                System.out.println(t.getMessage());
                boolean isInDB = true;
                boolean first = tweetInDB.first();
                while (tweetInDB.next()) {
                    if (tweetInDB.getString("tweetID").equals(t.getId())) {
                        isInDB = false;
                        System.out.println(" - tweet was found in the database.");
                        break;
                    }
                }
                if (isInDB) {
                    System.out.println(" - tweet is not yet in the database.");
                    //String mood = checktweetMood(c);
                    //System.out.println(mood);
                   
                    
                    //java.sql.Date sqlDate = new java.sql.Date(c.getCreatedTime().getTime());
                    //Post.Comments replies = c.getComments();
                    try {
                        /*Class.forName("com.mysql.jdbc.Driver");

                        con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest", "barba", "Ruggenmerg");
                        st = con.createStatement();*/
                        /*
                        String query = "INSERT INTO tweets (tweetID, tweet) VALUES('" + c.getId() + "', '" + c.gettweet() + "')";
                        st.execute(query);
                        
                        System.out.println(" - " + c.getId() + " was added to the Database.");
                    } catch (Exception ex) {
                        System.out.println("Error 74: " + ex);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("There are no tweets");
        }
    }
  
    */
    
    private String checkCommentMood(Comment c){

        String mood;
        String mess = c.getMessage().toLowerCase();
        String[] goodWords = {" goed ", " oke ", " vet "," leuk "," mooi "," lachen "," vet "," prachtig"," benieuwd "," adembenemend ", " trots ", " zin ", " geweldige ", " slim ", " gelukkig ", " geweldig ", " duimen ", " gezellig ", " top ", " gefeliciteerd ", " wauw ", " super ", " topper ", " yes ", " Gezellig ", " gaaf ", " plezier ", " favorite ", " Wauw ", " stoere ", " cool ", "Prachtig", " Hardstikke ", " Superleuke "    };
        String[] badWords = {" slecht ", " vreselijk ", " stom "," klote "," vervelend "," rot ", " teleurstelling ", " nee ", " blûh ", " jammer ", " waanzinnig ",  };
        int goodCount = 0;
        int badCount = 0;
        for (int i = 0; i < goodWords.length; i++) {
            if (mess.contains(goodWords[i])) {
                goodCount++;
            }
        }
        for (int i = 0; i < badWords.length; i++) {
            if (mess.contains(badWords[i])) {
                badCount++;
            }
        }
        if (!(goodCount == 0 && badCount == 0)) {
            if (goodCount > badCount) {
                mood = "Positief";
            } else if (goodCount < badCount) {
                mood = "Negatief";
            } else {
                mood = "Undefined";
            }
        } else {
            mood = "No Keywords";
        }
        return mood;
    }
    
}
    /*
    
        private String checkTweetMood(Comment c){
        String mood;
        String mess = c.getMessage();
        String[] goodWords = {"goed", "oke", "vet","leuk","mooi","lachen","vet","prachtig","benieuwd","adenbenemd"};
        String[] badWords = {"slecht", "vreselijk", "stom","klote","vervelend","rot","laat"};
        int goodCount = 0;
        int badCount = 0;
        for(int i = 0; i < goodWords.length;i++){
            if(mess.contains(goodWords[i])){
                goodCount++;
            }
        }
        for(int i = 0; i < badWords.length;i++){
            if(mess.contains(badWords[i])){
                badCount++;
            }
        }
        if(!(goodCount == 0 && badCount == 0)){
            if(goodCount > badCount){
                mood = "Positief";
            }
            else if(goodCount < badCount){
                mood = "Negatief";
            }
            else{
                mood = "Undefined";
            }
        }
        else{
            mood =  "No Keywords";
        }
        return mood;
    }
}*/
