package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author iewl
 */
public class Connectie {

    private static String dbserver;
   private static String database;
   private static String username;
   private static String password;

   public static Connection activeConn = null;
   public static Connection con = null;
   
   private static void init()
         
   {
      try {
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
      }
      catch (ClassNotFoundException e) {
          System.out.println(e);
      }


      dbserver="db4free.net:3306";
      database="barbawapatest";
      username = "barba";
      password = "Ruggenmerg";
      
      
   }
   
   public static Connection getConnection() throws SQLException
   {
       if (activeConn==null) {
           init();
           activeConn=createConnection();
       }

       System.out.println(activeConn);
       return activeConn;

   }
    public static Connection getConnectie() {
        try {
            con = createConnection();
            System.out.println("Connected");
            return con;
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return null;
    }

   private static Connection createConnection() throws SQLException
   {

        String connectionString = "jdbc:mysql://" + dbserver + "/" + database + "?" +
                "user=" + username + "&password=" + password;

       return DriverManager.getConnection(connectionString);
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}