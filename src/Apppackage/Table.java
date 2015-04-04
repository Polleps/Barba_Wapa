/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apppackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;



/**
 *
 * @author Willl_000
 */
public class Table extends javax.swing.JFrame {

    static Statement mijnStat;
    public Connection con;
    
    /**
     * Creates new form Table
     */
    public Table() throws ClassNotFoundException  {
        initComponents();
        /*try {
           
            //load the driver
            Class.forName("com.microsoft.sqlserver.jbdc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://db4free.net:3306; databaseName=barbawapatest; user=barba; password=Ruggenmerg";
            Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from FbComments");
            ResultSetMetaData rsmdata = rs.getMetaData();
            //store culumn number
            int columns = rsmdata.getColumnCount();
            //set data into jTable
            DefaultTableModel dtm = new DefaultTableModel();
            Vector columns_name = new Vector();
            Vector data_rows = new Vector();
            for (int i=1; i<columns; i++)
            {
                columns_name.addElement(rsmdata.getColumnName(1));
            }
            dtm.setColumnIdentifiers(columns_name);
                
                while(rs.next())
                {
                    data_rows = new Vector();
                    for(int j=1; j<columns; j++)
                    {
                        data_rows.addElement(rs.getString(j));
                    }
                    dtm.addRow(data_rows);                
                }
                //pass default table object over into jtable
                fbComTable.setModel(dtm);
        } catch (SQLException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        } */
            
            Searchbutton.addActionListener(new ActionListener() 
                    {
            @Override
            public void actionPerformed(ActionEvent e)
                    {
                       jLabel1.setText("Er zijn geen resultaten voor: "+Searchbar.getText()); 
                    }
             });
        
                
                }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        Searchbar = new javax.swing.JTextField();
        Searchbutton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        BackbuttonTable = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        fbComTable = new javax.swing.JTable();
        toonDataB = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MainMenu = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Searchbutton.setText("Zoeken");

        BackbuttonTable.setText("Terug");
        BackbuttonTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackbuttonTableMouseClicked(evt);
            }
        });

        fbComTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "UserID", "Likes", "Datum", "Gedrag", "Commentaar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(fbComTable);
        fbComTable.getColumnModel().getColumn(0).setMaxWidth(200);
        fbComTable.getColumnModel().getColumn(1).setMaxWidth(50);
        fbComTable.getColumnModel().getColumn(2).setMaxWidth(75);
        fbComTable.getColumnModel().getColumn(3).setMaxWidth(200);

        toonDataB.setText("Toon");
        toonDataB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toonDataBActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        MainMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        MainMenu.setText("Main");
        MainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainMenuActionPerformed(evt);
            }
        });
        jMenu1.add(MainMenu);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem2.setText("Exit");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Searchbar, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Searchbutton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(toonDataB, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132)
                        .addComponent(BackbuttonTable, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Searchbar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Searchbutton))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackbuttonTable, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toonDataB))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackbuttonTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackbuttonTableMouseClicked
    GraphChoose graphChoose = new GraphChoose();
    graphChoose.setVisible(true);
    Table.this.dispose();
    }//GEN-LAST:event_BackbuttonTableMouseClicked

    private void MainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainMenuActionPerformed
    LoginScreen loginScreen = new LoginScreen();
    loginScreen.setVisible(true);
    Table.this.dispose();
    }//GEN-LAST:event_MainMenuActionPerformed

    private void toonDataBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toonDataBActionPerformed
        try {            
        //EXTERNE DATABASE_______________________            
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   
            String connectionUrl = "jdbc:sqlserver://db4free.net:3306; databaseName=barbawapatest; user=barba; password=Ruggenmerg";
            Connection con = Database.Connectie.getConnection();
            //Connection con = DriverManager.getConnection(connectionUrl);
        
     /*   //LOKAAL DATABASE!!______________________
            String connectionUrl = "jdbc:mysql://localhost/barbawapatest";
            String driver = "com.mysql.jdbc.Driver";
            try{
                //Class.forName(driver);
            try{
                con = DriverManager.getConnection(connectionUrl, "root", "root");
                mijnStat = con.createStatement();
            }catch (SQLException ex){
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }  */               
            //Create Connection..
            Statement state = con.createStatement();
            ResultSet rs = state.executeQuery("select * from FbComments");
            ResultSetMetaData rsmdata = rs.getMetaData();
            //store culumn number
            int columns = rsmdata.getColumnCount();
            //set data into jTable
            DefaultTableModel dtm = new DefaultTableModel();
            Vector columns_name = new Vector();
            Vector data_rows = new Vector();
            for (int i=1; i<columns; i++)
            {
                columns_name.addElement(rsmdata.getColumnName(1));
            }
            dtm.setColumnIdentifiers(columns_name);
                
                while(rs.next())
                {
                    data_rows = new Vector();
                    for(int j=1; j<columns; j++)
                    {
                        data_rows.addElement(rs.getString(j));
                    }
                    dtm.addRow(data_rows);                
                }
                //pass default table object over into jtable
                fbComTable.setModel(dtm);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//GEN-LAST:event_toonDataBActionPerformed

     
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
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Table().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackbuttonTable;
    private javax.swing.JMenuItem MainMenu;
    private javax.swing.JTextField Searchbar;
    private javax.swing.JButton Searchbutton;
    private javax.swing.JTable fbComTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton toonDataB;
    // End of variables declaration//GEN-END:variables
}
