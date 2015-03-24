
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apppackage;

import com.restfb.types.Comment;
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

    public boolean verifyUser(String userName, String password) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest", "barba", "Ruggenmerg");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
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
            System.out.println("Error: " + ex);
        }
        return false;
    }

    public void addCommentsToDB(Post.Comments coms) {
        try {
            List<Comment> comment = coms.getData();
            for (Comment c : comment) {
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
                        //De comment moet in de DB
                    }                    
                }
                catch(Exception ex){
                    System.out.println("Error: " + ex);
                }
            }
        } catch (Exception e) {
            //System.out.println("This post has no comments");
        }
    }
}
