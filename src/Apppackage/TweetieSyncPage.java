/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apppackage;

import static Database.Connectie.con;
import java.awt.Color;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Polle
 */
public class TweetieSyncPage extends javax.swing.JFrame {

    private  Twitter twitter;
    private int limitRemaning = 0;
    private  List<String> foreigners = new ArrayList<String>();
    private  Paging pg = new Paging();
    private  int numTweets = 200;
    private  long lastID = Long.MAX_VALUE;
    private  List<Status> tweets;
    private  List<Status> statuses;
    private  ResponseList<Status> status;
    /**
     * Creates new form TweetieSyncPage
     */
    public TweetieSyncPage() {
        initComponents();
    }

    private ConfigurationBuilder configBuild() {
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("yyWmEPUxeyRMgloAJO8OG8sOO");
        cb.setOAuthConsumerSecret("63eI6lEf1x5XiN6JaRzHB52nvWvuTNUuzZDLhtVPqrpuoYdPCF");
        cb.setOAuthAccessToken("388485636-s9zmeX3KFApKxO1xRCEYbmQYlEe6DrdsIB7UlGk0");
        cb.setOAuthAccessTokenSecret("rKY21nXqAUjeGTa6G5QoozQPxh0jhDt129o1bJMsABC0P");
        return cb;
    }
    
    private void getTweets() {
        boolean cancel;
        /*while (tweets.size() < numTweets) {
            
            try {
                tweets.addAll(twitter.getUserTimeline("ahoyrotterdam", pg));
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Status t : tweets) {
                    
                    if (t.getId() < lastID) {
                        lastID = t.getId();
                        
                    }
                }
            } catch (TwitterException e) {
                System.out.println("Couldn't connect" + e);
            }
        }*/
        Twitter twitter = new TwitterFactory(configBuild().build()).getInstance();
        List<Long> curRS = getCurrentDB();
        try {
            Query query = new Query("#Ahoy");
            QueryResult result;
            do {
                result = twitter.search(query);
                tweets = result.getTweets();
                for (Status tweet : tweets) {
                    output("@" + tweet.getUser().getScreenName() + " - " + tweet.getText(), Color.MAGENTA);
                    cancel = addToDB(tweet, curRS);
                    if(cancel)
                    {
                        output("No more new Tweeties.", Color.BLACK);
                        break;
                    }
                }
            } while ((query = result.nextQuery()) != null);
                
        } catch (TwitterException te) {
            te.printStackTrace();
            output("Failed to search tweets: " + te.getMessage(), Color.RED);
            
        }
       
        
       
        System.out.println("Done");
    }
    
    private boolean addToDB(Status s, List<Long> r) {
        System.out.println("Connecting to Database");
        java.sql.Connection adderCon = null;
        try {
            for(int i = 0; i < r.size(); i++){
                if(r.get(i) == s.getId()){
                   return true;
                }
            }
            adderCon = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
            Statement adderSt = adderCon.createStatement();
            long tweetID =  s.getId();
            String body = s.getText().replaceAll("'", ".");
            String mood = checkMood(body);
            output(mood, Color.ORANGE);
            String tweetTime = (s.getCreatedAt().getYear() + 1900) + ":" + (s.getCreatedAt().getMonth() + 1) + ":" + s.getCreatedAt().getDate() + ":" + s.getCreatedAt().getHours() + ":" + s.getCreatedAt().getMinutes() + ":" + s.getCreatedAt().getSeconds() ;
            String qwu = "INSERT INTO tweets (tweet_id, tweet_body, dates, mood) VALUES ("+ tweetID +", '" + body + "', '" + tweetTime + "', '" + mood + "')";
            adderSt.execute(qwu);
            output(tweetID + " was added.", Color.BLUE);
        } catch (SQLException ex) {
            Logger.getLogger(Tweet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private List<Long> getCurrentDB() {
       ResultSet resDBC = null;
       java.sql.Connection conDBC = null;
       List<Long> result = new ArrayList<Long>();
       int count = 0;
        try {
             conDBC = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
            Statement stDBC = conDBC.createStatement();
            resDBC = stDBC.executeQuery("SELECT tweet_id FROM tweets");
            while(resDBC.next()){
                result.add(resDBC.getLong("tweet_id"));
                
            }
            //conDBC.close();
        } catch (SQLException e) {
            output(e.getMessage(), Color.RED);
            
        }
        return result;
    }
    private String checkMood(String c) {

        String mood;
        String mess = c.toLowerCase();
        String[] goodWords = {"goed ", "oke ", "vet ", "leuk ", "mooi ", "lachen ", "vet ", "prachtig ", "benieuwd ", "adembenemend ", "trots ",
            "zin in ", "geweldige ", "slim ", "gelukkig ", "geweldig ", "duimen ", "gezellig ", "top ", "gefeliciteerd ", "wauw ", "super ",
            "topper ", "yes ", " :) ", " :D ", " :-) ", "beste ", "leukste " , "wauw ", "wow ", "leuke ", " gaan ook", "gaaf ", "uitstekend ",
            "uitstekende ", "genieten ", "genoten ",
             "favoriete ", "ik ga", "(Y)", "plezier "};
        String[] badWords = {"slecht ", "vreselijk ", "stom ", "klote ", "vervelend ", "rot ", "teleurstelling ", "blûh ", "jammer ", "waanzinnig ",
            ":( ", ">:( ", "niet mijn ding ", "nee bedankt ", "kloten ",
            "klote ", "overbodig ", "gvd ", "belachelijk ", "ik kom niet", "schaam ", "shame ", "scande ", "achterlijke ", "zielig "};
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
    private void output(String text, Color col) {
        StyledDocument doc = (StyledDocument) outputPane.getDocument();
        Style style = doc.addStyle("StyleName", null);
        StyleConstants.setForeground(style, col);
        try {
            doc.insertString(doc.getLength(), "\n" + text, style);
            if(doc.getLength() > 3000){
                outputPane.setText("");
            }
            outputPane.setCaretPosition(doc.getLength());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deGroteSyncKnop = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputPane = new javax.swing.JTextPane();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        deGroteSyncKnop.setText("Sync");
        deGroteSyncKnop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deGroteSyncKnopActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(outputPane);

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deGroteSyncKnop, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(deGroteSyncKnop, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        Table tb = null;
        try {
            tb = new Table();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TweetieSyncPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        tb.setVisible(true);
        TweetieSyncPage.this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void deGroteSyncKnopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deGroteSyncKnopActionPerformed
       getTweets();
    }//GEN-LAST:event_deGroteSyncKnopActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TweetieSyncPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TweetieSyncPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TweetieSyncPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TweetieSyncPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TweetieSyncPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton deGroteSyncKnop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane outputPane;
    // End of variables declaration//GEN-END:variables
}
