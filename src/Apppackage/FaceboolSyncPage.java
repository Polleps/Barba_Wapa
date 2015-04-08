package Apppackage;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;
import com.restfb.types.Comment;
import com.restfb.types.Page;
import com.restfb.types.Post;
import java.awt.Color;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Polle
 */
public class FaceboolSyncPage extends javax.swing.JFrame {

    private boolean Canceled = false;
    private java.sql.Connection con;
    private Statement st;
    private ResultSet res;
    private ResultSet commentsInDB;

    /**
     * Creates new form FaceboolSyncPage
     */
    public FaceboolSyncPage() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backButton = new javax.swing.JButton();
        tokenField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        syncButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(655, 475));

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Access Token:");

        syncButton.setText("Sync");
        syncButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncButtonActionPerformed(evt);
            }
        });

        outputPane.setEditable(false);
        outputPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        outputPane.setDragEnabled(true);
        jScrollPane2.setViewportView(outputPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tokenField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(syncButton, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tokenField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(syncButton)
                        .addGap(0, 297, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    Thread t1 = new Thread() {
        public void run() {
            System.out.println("Thread1 started!");
            getFbData(tokenField.getText());
        }
    };

    public void addFBCommentsToDB(Post.Comments coms) {
        java.sql.Connection conFB = null;
        Statement stFB = null;
        ResultSet resFB = null;
        ResultSet commentsInDBFB = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            conFB = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
            stFB = conFB.createStatement();

        } catch (Exception ex) {
            output("Error 61: " + ex, Color.RED);
        }
        try {
            String quary = "SELECT * FROM FbComments";
            commentsInDBFB = stFB.executeQuery(quary);
        } catch (Exception ex) {
            output("Error idChecker: " + ex, Color.RED);

        }
        try {
            List<Comment> comment = coms.getData();
            for (Comment c : comment) {
                output(c.getMessage(), Color.magenta);
                boolean isInDB = true;
                boolean first = commentsInDBFB.first();
                while (commentsInDBFB.next()) {
                    if (commentsInDBFB.getString("commentID").equals(c.getId())) {
                        isInDB = false;
                        output("- Comment was found in the database.", Color.BLACK);
                        break;
                    }
                }
                if (isInDB) {
                    Statement stIntoFB = conFB.createStatement();
                    output("- Comment is not yet in the database.", Color.GREEN);
                    String mood = checkCommentMood(c);
                    System.out.println(mood);

                    //java.sql.Date sqlDate = new java.sql.Date(c.getCreatedTime().getTime());
                    //Post.Comments replies = c.getComments();
                    try {
                        /*Class.forName("com.mysql.jdbc.Driver");

                         con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest", "barba", "Ruggenmerg");
                         st = con.createStatement();*/
                        c.setMessage(c.getMessage().replaceAll("'", "\'"));

                        String query = "INSERT INTO FbComments (commentID, commentBody, likes, replies, mood, dates ) VALUES('" + c.getId() + "', '" + c.getMessage() + "', " + c.getLikeCount() + ", " + 0 + ", '" + mood + "','" + (c.getCreatedTime().getYear() + 1900) + ":" + (c.getCreatedTime().getMonth() + 1) + ":" + c.getCreatedTime().getDate() + ":" + c.getCreatedTime().getHours() + ":" + c.getCreatedTime().getMinutes() + ":" + c.getCreatedTime().getSeconds() + "')";
                        stIntoFB.execute(query);

                        output("- " + c.getId() + " was added to the Database.", Color.BLUE);
                    } catch (Exception ex) {
                        System.out.println("Error 74: " + ex);

                    }
                }

            }
        } catch (Exception e) {
            output(e.getMessage(), Color.RED);
        }
        try {
            conFB.close();
        } catch (SQLException ex) {
           output(ex.toString(), Color.RED);
        }
    }

    public boolean getFbData(String token) {
        output("Sync prosses wordt gestart.", Color.BLACK);
        Canceled = false;

        FacebookClient fbClient = new DefaultFacebookClient(token);// HIER MOET EEN ACCES TOKEN
        Page page = fbClient.fetchObject("165327986836920", Page.class);  //ID van de pagina
        BatchRequest postsRequest = new BatchRequest.BatchRequestBuilder("ahoyrotterdam/posts").parameters(Parameter.with("limit", 100)).build();
        List<BatchResponse> batchResponses = fbClient.executeBatch(postsRequest);
        BatchResponse ahoyResponse = batchResponses.get(0);
        Connection<Post> ahoyPosts = new Connection<>(fbClient, ahoyResponse.getBody(), Post.class);

        for (List<Post> ahoyPostsConnectionPage : ahoyPosts) {
            for (Post post : ahoyPostsConnectionPage) {
                //System.out.println("=================================\n" + post.getMessage());
                Post.Comments coms = post.getComments();
                System.out.println(post.getCommentsCount());
                //if(post.getCommentsCount() <= 0){
                //    System.out.println("- " + post.getId() + " has no comments");
                //}  
                //else{
                output("Adding comments of " + post.getId() + " to the Database.", Color.BLACK);
                addFBCommentsToDB(coms);

                //}
            }
            if (Canceled) {
                break;
            }
        }

        return true;
    }

    private String checkCommentMood(Comment c) {

        String mood;
        String mess = c.getMessage().toLowerCase();
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

    private void syncButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncButtonActionPerformed
        if (tokenField.getText().isEmpty()) {
            output("Vul een Access token in.", Color.RED);
            output("Deze kun je hier halen:", Color.RED);
            output("https://developers.facebook.com/tools/explorer", Color.BLUE);
        } else {
            syncButton.setEnabled(false);

            t1.start();
        }
    }//GEN-LAST:event_syncButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        GraphChoose gpChoose = new GraphChoose();
        gpChoose.setVisible(true);
        FaceboolSyncPage.this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

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
            java.util.logging.Logger.getLogger(FaceboolSyncPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FaceboolSyncPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FaceboolSyncPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FaceboolSyncPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FaceboolSyncPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane outputPane;
    private javax.swing.JButton syncButton;
    private javax.swing.JTextField tokenField;
    // End of variables declaration//GEN-END:variables
}
