
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apppackage;
import java.sql.*;
/**
 *
 * @author Polle
 */
public class DatabaseManager {
    private Connection con;
    private Statement st;
    private ResultSet res;
    
    public boolean verifyUser(String userName, String password){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest", "barba", "Ruggenmerg");
            st = con.createStatement();
        }
        catch(Exception ex){
            System.out.println("Error: " + ex);
        }
        try{
            String quary = "select * from Users";
            res = st.executeQuery(quary);
            while(res.next()){
                  if (userName.equals(res.getString("userName"))  && password.equals(res.getString("password")) ){
                      return true;
                  }
                
            }
            
        } catch(Exception ex){
            System.out.println("Error: " + ex);
        }
        return false;
    }
}
