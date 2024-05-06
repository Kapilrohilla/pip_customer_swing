/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer.History;

import static com.mycompany.metacustomer.History.Trade.mmm;
import static com.mycompany.metacustomer.History.Trade.model;
import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.Metacustomer;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kapilrohilla
 */
public class Position extends javax.swing.JPanel {

    /**
     * Creates new form Position
     */
    public static JSONArray closedPositions = new JSONArray();
    public static DefaultTableModel model = new DefaultTableModel();

    public Position() {
        String[] columnNames = {"ticket", "Symbol", "Type", "Price", "StopLoss", "TakeProfit"};
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
