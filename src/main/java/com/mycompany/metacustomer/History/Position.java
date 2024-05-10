/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer.History;

import static com.mycompany.metacustomer.History.Trade.mmm;
import static com.mycompany.metacustomer.History.Trade.model;
import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.Metacustomer;
import com.mycompany.metacustomer.Utility.ApiServices;
import com.mycompany.metacustomer.Utility.Helper;
import com.sun.jdi.IntegerType;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kapilrohilla
 */
interface PositionInter {

    void fetchNSetPositions();
//    void intitializeTable();

    void setTableData();
}

public class Position extends javax.swing.JPanel {

    /**
     * Creates new form Position
     */
    final JTable table = new JTable();
    public static JSONArray closedPositions = new JSONArray();
    public static DefaultTableModel model = new DefaultTableModel();

    public final void fetchNSetPositions() {
        ApiServices services = new ApiServices();
        String url = APIs.GET_CLOSED_POSITION;
        String token = Metacustomer.loginToken;
        try {
            Response res = services.getDataWithToken(url, token);
            if (res.isSuccessful()) {
                ResponseBody body = res.body();
                String responseBody = body.string();
                closedPositions = new JSONArray(responseBody);
            }
        } catch (IOException | JSONException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public final void setupTable() {

        String[] columns = {"Time", "Symbol", "Ticket", "Type", "Volume", "Price", "S/L", "T/P", "Closing Time", "Close Price", "Commission", "Fee", "Swap", "Change", "Profit", "Comment"};

        for (int i = 0; i < columns.length; i++) {
            model.addColumn(columns[i]);
        }
    }

    public final void setTableData() {
        int arrLength = this.closedPositions.length();
        for (int i = 0; i < arrLength; i++) {
            try {
                JSONObject json = this.closedPositions.getJSONObject(i);
                String time = Helper.getJSONString(json, "createdAt");
                String ticket = Helper.getJSONString(json, "ticket");
                String symbol = Helper.getJSONString(json, "symbol");
                int typeNum = (int) Helper.getJSONDouble(json, "type");
                String type = Helper.getMappedOrderType((typeNum));
                double volume = Helper.getJSONDouble(json, "volume");
                double price = Helper.getJSONDouble(json, "price");
                double sl = Helper.getJSONDouble(json, "stopLoss");
                double tp = Helper.getJSONDouble(json, "takeProfit");
                double comission = Helper.getJSONDouble(json, "comission");
                double fee = Helper.getJSONDouble(json, "fee");
                double swap = Helper.getJSONDouble(json, "swap");
                double profit = Helper.getJSONDouble(json, "profit");
                String formattedProfit = String.format("%.2f", profit);
                double closePrice = Helper.getJSONDouble(json, "closePrice");
                String closingTime = Helper.getJSONString(json, "updatedAt");
                String comment = Helper.getJSONString(json, "comment");
                String changePercent = String.format("%.2f", closePrice / price * 100 - 100);

                String[] rowData = {time, symbol, ticket, type, volume + "", price + "", sl + "", tp + "", closingTime, closePrice + "", comission + "", fee + "", swap + "", changePercent + "", formattedProfit, comment};
                model.addRow(rowData);
            } catch (JSONException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public final void clearTableRows() {
        int size = model.getRowCount();
        for (int i = 0; i < size; i++) {
            model.removeRow(i);
        }
    }

    public final void setBottomStrip() {
        /// calculate profit
        double profit = 0;
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            try {
                String p = (String) model.getValueAt(i, 14);
                double cp = Double.parseDouble(p);
                profit += cp;
            } catch (Exception ex) {
                System.out.println("Casting failed, probably empty string");

            }

        }
        System.out.println("profit: " + profit);
        try {
            jLabel3.setText(profit + "");
            jLabel4.setText("credit");
            jLabel6.setText("deposit");
            jLabel8.setText("withdrawl");
            jLabel10.setText("bal");
        } catch (NullPointerException ex) {
            ex.getStackTrace();
        }
        // credit

    }

    public Position() {
        initComponents();

        fetchNSetPositions();
        setupTable();
        setTableData();
        setBottomStrip();
        jTable1.setModel(model);
    }

    /*
    public Position() {
//        String[] columnNames = {"ticket", "Symbol", "Type", "Price", "StopLoss", "TakeProfit"};
        String[] columnNames = {"Time","Symbol", "Ticket", "Type", "Volume", "Price", "S/L", "T/P", "Time", "Price", "Commission", "Fee", "Swap", "Change", "Comment"};
        for (String column : columnNames) {
            model.addColumn(column);
        }
        setTableData();
        JTable jt = new JTable(model);
        setLayout(new BorderLayout());
        JScrollPane jp = new JScrollPane(jt);

        add(jp, BorderLayout.CENTER);
    }

    public static void setTableData() {
        try {
            for (int i = 0; i < closedPositions.length(); i++) {
                JSONObject jso = closedPositions.getJSONObject(i);
                String userId = jso.getString("ticket");
                String symbol = jso.getString("symbol");
                String price = jso.getString("price");
                String stopLoss = jso.getString("stopLoss");
                double type = jso.getDouble("type");
                String typeString = (type == 0 || type == 3 || type == 5 || type == 7) ? "Sell" : "Buy";
                
                if ("null".equals(stopLoss)) {
                    stopLoss = "";
                }

                String takeProfit = jso.getString("takeProfit");
                if ("null".equals(takeProfit)) {
                    takeProfit = "";
                }

                String[] rowData = {userId, symbol, typeString, price, stopLoss, takeProfit};
                int xxy = Position.model.getRowCount();
                for (int j = 0; j < xxy; j++) {
                    Position.model.removeRow(j);
                }
                Position.model.addRow(rowData);
            }
            System.out.println("DONE");

        } catch (JSONException ex) {
            System.out.println("error Occurred");
            System.out.println("ex");
        }
    }
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setMinimumSize(new java.awt.Dimension(60, 40));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Profit:");

        jLabel2.setText("Credit:");

        jLabel3.setText("    ");

        jLabel4.setText("    ");

        jLabel5.setText("Deposit");

        jLabel6.setText("    ");

        jLabel7.setText("Withdrawl");

        jLabel8.setText("    ");

        jLabel9.setText("Balance:");

        jLabel10.setText("    ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 62, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
