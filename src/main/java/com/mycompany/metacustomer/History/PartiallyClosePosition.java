/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.metacustomer.History;

import com.mycompany.metacustomer.Metacustomer;
import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.Utility.ApiServices;
import com.mycompany.metacustomer.Utility.Helper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.SpinnerNumberModel;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kapil
 */
public class PartiallyClosePosition extends javax.swing.JFrame {

    /**
     * Creates new form PartiallyClosePosition
     */
    String symbol, ticket;
    double initialVolume;
    String positionId;

    public PartiallyClosePosition(String symbol, String ticket, double initialVolume, String positionId) {
        initComponents();
        this.symbol = symbol;
        this.ticket = ticket;
        this.positionId = positionId;
        this.initialVolume = initialVolume;
        jLabel5.setText(symbol);
        jLabel6.setText(ticket);
        jLabel7.setText(this.initialVolume + "");
        SpinnerNumberModel model = new SpinnerNumberModel(this.initialVolume / 2, 0, this.initialVolume, 0.1);
        jSpinner1.setModel(model);
    }

    private void updateTradePanelTable(double vol2close) {
        Vector<Vector> tableData = Trade.model.getDataVector();
        for (int i = 0; i < tableData.size(); i++) {
            Vector<String> rowData = tableData.get(i);
            double volume = Double.parseDouble(rowData.get(4));
            double finalVolume = volume - vol2close;
            System.out.println("finalVolume: " + finalVolume);
            Trade.model.setValueAt(finalVolume + "", i, 4);
            Trade.model.fireTableRowsUpdated(i, i);
        }
//        ArrayList<JSONObject> tableData = new ArrayList<>();
//        System.out.println(tableData.size());
//        for (int i = 0; i < tableData.size(); i++) {
//            System.out.println("working: ");
//            JSONObject rowData = tableData.get(i);
//            String id = Helper.getJSONString(rowData, "_id");
//            System.out.println("_ID = " + id + " positionId: " + this.positionId);
//            if (!id.equals(this.positionId)) {
//                continue;
//            }
//            double initialVolume = (double) Trade.model.getValueAt(i, 4);
//            System.out.println("initial volume: " + initialVolume);
//
//            Trade.model.setValueAt(initialVolume, i, 4);
//            Trade.model.fireTableCellUpdated(i, 4);
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Symbol:");

        jLabel2.setText("Ticket:");

        jLabel3.setText("Current Volume:");

        jLabel4.setText("Volume to close:");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, null, 0, 1));

        jLabel5.setText(" ");

        jLabel6.setText(" ");

        jLabel7.setText(" ");

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            JSONObject payload = new JSONObject();
            payload.put("positionId", positionId);
            String pos2closeStr = jSpinner1.getValue().toString();
            double pos2closeQty = Double.parseDouble(pos2closeStr);
            System.out.println(positionId);
            System.out.println(pos2closeQty);
            payload.put("volume2Close", pos2closeQty);

            String api = APIs.PARTIAL_CLOSE;
            System.out.println(api);
            ApiServices service = new ApiServices();

            String token = Metacustomer.loginToken;

            try {
                Response res = service.postDataWithToken(api, payload, token);
                if (res.isSuccessful()) {
                    JSONObject responseObject = new JSONObject(res.body().string());
                    String message = Helper.getJSONString(responseObject, "message");
                    if (message.equals("sucess")) {
                        double balance = Helper.getJSONDouble(responseObject, "balance");
                        String balanceStr = String.format("%.2f", balance);
                        Trade.jLabel2.setText(balanceStr);
                        this.updateTradePanelTable(pos2closeQty);
                        System.out.println("response success");
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
//        Response res = service.postDataWithToken();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(PartiallyClosePosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PartiallyClosePosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PartiallyClosePosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PartiallyClosePosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new PartiallyClosePosition().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration//GEN-END:variables
}
