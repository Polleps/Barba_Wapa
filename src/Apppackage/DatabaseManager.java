
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apppackage;

import com.restfb.types.Comment;
import com.restfb.types.Comment.Comments;
import com.restfb.types.Post;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Polle
 */
public class DatabaseManager {

    private Connection con;
    private Statement st;
    private ResultSet res;
    private ResultSet commentsInDB;

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

    public void addFBCommentsToDB(Post.Comments coms) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Error 61: " + ex);
        }
        try {
            String quary = "SELECT commentID FROM FbComments";
            commentsInDB = st.executeQuery(quary);
        } catch (Exception ex) {
            System.out.println("Error idChecker: " + ex);
        }
        try {
            List<Comment> comment = coms.getData();
            for (Comment c : comment) {

                boolean isInDB = true;
                boolean first = commentsInDB.first();
                while (commentsInDB.next()) {
                    if (res.getString("commentID").equals(c.getId())) {
                        isInDB = false;
                        System.out.println(" - Comment was found in the database.");
                        break;
                    }
                }
                if (isInDB) {
                    System.out.println(" - Comment is not yet in the database.");
                    String mood = checkCommentMood(c);
                    System.out.println(mood);
                    Comments replies = c.getComments();
                    try {
                        /*Class.forName("com.mysql.jdbc.Driver");

                        con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest", "barba", "Ruggenmerg");
                        st = con.createStatement();*/
                        
                        String query = "INSERT INTO FbComments (commentID, commentBody, likes, replies, mood) VALUES('" + c.getId() + "', '" + c.getMessage() + "', " + c.getLikeCount() + ", " + 0 + ", '" + mood + "')";
                        st.execute(query);
                        System.out.println(" - " + c.getId() + " was added to the Database.");
                    } catch (Exception ex) {
                        System.out.println("Error 74: " + ex);
                    }
                }

            }
        } catch (Exception e) {
            //System.out.println("This post has no comments");
        }
    }

    
    public void addTwitterCommentstoDB(Post.tweets tweets) {
        try {
            List<Tweet> tweet = tweets.getData();
            for (Tweet c : tweet) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest", "barba", "Ruggenmerg");
                    st = con.createStatement();
                } catch (Exception ex) {
                    System.out.println("Error: " + ex);
                }
                try{
                    String quary = "select commentID from Comments where commentID='" + c.getId() + "'";
                    res = st.executeQuery(quary);
                    if (!res.first()){
                        String mood = checkTweetMood(c);
                        // Checked voor de Mood (zie hierbeneden)
                    }                    
                }
                catch(Exception ex){
                    System.out.println("Error: " + ex);
                }
            }
        } catch (Exception e) {
            System.out.println("This post has no tweets");
        }
    }
    
    
    private String checkCommentMood(Comment c){

        String mood;
        String mess = c.getMessage();
        String[] goodWords = {"goed", "oke", "vet"};
        String[] badWords = {"slecht", "vreselijk", "stom"};
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
}
