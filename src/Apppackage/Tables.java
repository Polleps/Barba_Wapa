/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Apppackage;

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
 * @author Ardley Arrindell
 */
public class Tables extends javax.swing.JFrame {
    static Statement mijnStat;
    public Connection con;
    String user = "barba";
    String password = "Ruggenmerg";

    /**
     * Creates new form Tables
     */
    public Tables() {
        initComponents();
        String connectionUrl = "jdbc:mysql://localhost/barbawapatest";
        String driver = "com.mysql.jdbc.Driver";
        try{
             

        //Class.forName(driver);
        try {
            con = DriverManager.getConnection(connectionUrl, "root", "root");
            mijnStat = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
            //create connection
           
            Statement state = con.createStatement();
            ResultSet rs = state.executeQuery("select * from fbpositive");
            ResultSetMetaData rsmdata = rs.getMetaData();
            //store column numbers
            int columns = rsmdata.getColumnCount();
            //set data into Jtable
            DefaultTableModel dtm = new DefaultTableModel();
            Vector columns_name = new Vector();
            Vector data_rows = new Vector();
            
            for (int i=1; i<columns; i++)
            {
                columns_name.addElement(rsmdata.getColumnName(i));
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
            fbPosT.setModel(dtm);
        } catch (SQLException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
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

        RadioBGrpT2 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        fbPosT = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        toonB = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MainMenu = new javax.swing.JMenuItem();
        OpenMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        ExitMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        TipsMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("datum/gedrag");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("datum/users");

        jButton3.setText("datum/likes");

        fbPosT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "userID", "Likes", "Date", "Gedrag", "Comments"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(fbPosT);
        fbPosT.getColumnModel().getColumn(0).setMaxWidth(200);
        fbPosT.getColumnModel().getColumn(0).setHeaderValue("userID");
        fbPosT.getColumnModel().getColumn(1).setMaxWidth(40);
        fbPosT.getColumnModel().getColumn(1).setHeaderValue("Likes");
        fbPosT.getColumnModel().getColumn(2).setMaxWidth(75);
        fbPosT.getColumnModel().getColumn(2).setHeaderValue("Date");
        fbPosT.getColumnModel().getColumn(3).setMaxWidth(100);
        fbPosT.getColumnModel().getColumn(3).setHeaderValue("Gedrag");
        fbPosT.getColumnModel().getColumn(4).setHeaderValue("Comments");

        jTabbedPane1.addTab("FbPositief", jScrollPane1);
        jTabbedPane1.addTab("tab2", jTabbedPane3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        toonB.setText("datum/comments");
        toonB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toonBActionPerformed(evt);
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

        OpenMenu.setText("Open");

        jMenuItem1.setText("jMenuItem1");
        OpenMenu.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        OpenMenu.add(jMenuItem2);

        jMenuItem3.setText("jMenuItem3");
        OpenMenu.add(jMenuItem3);

        jMenuItem4.setText("jMenuItem4");
        OpenMenu.add(jMenuItem4);

        jMenu1.add(OpenMenu);

        ExitMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        ExitMenu.setText("Exit");
        ExitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitMenuActionPerformed(evt);
            }
        });
        jMenu1.add(ExitMenu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        TipsMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        TipsMenu.setText("Tips");
        TipsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipsMenuActionPerformed(evt);
            }
        });
        jMenu2.add(TipsMenu);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(toonB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 841, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addGap(0, 731, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(toonB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainMenuActionPerformed
        GraphChoose graphChoose = new GraphChoose();
        graphChoose.setVisible(true);
        Tables.this.dispose();
    }//GEN-LAST:event_MainMenuActionPerformed

    private void ExitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitMenuActionPerformed
        Tables.this.dispose();
    }//GEN-LAST:event_ExitMenuActionPerformed

    private void TipsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipsMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipsMenuActionPerformed

    private void toonBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toonBActionPerformed
 try{
            String query="select dates, amount from fbpositive";
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(Connect.ConnectToFb.getConnection(),query);
        //JDBCCategoryDataset dataset = new JDBCCategoryDataset(DriverManager.getConnection(connectionUrl, "root", "root"), query);
        JFreeChart chart = ChartFactory.createLineChart("Positive Reactions", "dates", "amount", dataset, PlotOrientation.VERTICAL, false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = null;
        renderer = new BarRenderer();
        ChartFrame frame = new ChartFrame("Positive Reactions", chart);
        frame.setVisible(true);
        frame.setSize(1000, 650);        
        }
    catch (Exception e) { 
    JOptionPane.showMessageDialog(null, e);{
        
}
    }        
    }//GEN-LAST:event_toonBActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Tables().setVisible(true);
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ExitMenu;
    private javax.swing.JMenuItem MainMenu;
    private javax.swing.JMenu OpenMenu;
    private javax.swing.ButtonGroup RadioBGrpT2;
    private javax.swing.JMenuItem TipsMenu;
    private javax.swing.JTable fbPosT;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JButton toonB;
    // End of variables declaration//GEN-END:variables
}
