package Apppackage;

import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Polle
 */
public class Aanmelder {
    private Connection con;
    private Statement st;
    private ResultSet res;
    private final String firstName;
    private final String lastName;
    private final String initalen;
    private final String adress;
    private final String huisNummer;
    private final String telefoonNummer;
    private final String mobielNummer;
    private final String email;
    private final String sekse;
    private String regId; 
    public Aanmelder(String[] medewerker){
        this.firstName = medewerker[0];
        this.lastName = medewerker[1];
        this.initalen = medewerker[2];
        this.adress = medewerker[3];
        this.huisNummer = medewerker[4];
        this.telefoonNummer = medewerker[5];
        this.mobielNummer = medewerker[6];
        this.email = medewerker[7];
        this.sekse = medewerker[8];
        this.regId = "0";
        for(int i = 0; i < medewerker.length; i++){
            System.out.println(medewerker[i]);
        }
        addToDB();
    }
    
    private boolean addToDB(){
        try{
         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
         st = con.createStatement();
         String getMedewerkerQuery = "SELECT * FROM medewerker";
         res = st.executeQuery(getMedewerkerQuery);
         boolean hasRegisterd = false;
         while(res.next()){
             if(this.email.equals(res.getString("email"))){
                 hasRegisterd = true;
                 System.out.println("User already registered");
                 break;
             }
         }
         if(!hasRegisterd){
             String addQuery = "INSERT INTO medewerker (name, l_name, adress, h_nr, initials, t_nr, m_nr, gender, email, reg_key) VALUES ('" + this.firstName + "', '" + this.lastName +"','" + this.adress + "', '" + this.huisNummer + "', '" + this.initalen + "', '" + this.telefoonNummer + "','" + this.mobielNummer + "', '" + this.sekse + "', '" + this.email + "', '" + this.regId + "');";
             st.execute(addQuery);
             System.out.println("User was registered!");
             return true;
         } 
        }
        catch(Exception e){
            System.out.println("ERROR 64: " + e.getMessage());
        }
        return false;
    }
}
