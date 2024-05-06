/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer.History;

import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.Metacustomer;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import okhttp3.Call;
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
public class Order extends javax.swing.JPanel {

    public DefaultTableModel model = new DefaultTableModel();
    public double totalCancelled = 0;
    public double totalOrder = 0;
    public JSONArray orderDataJSNArr;
    public JLabel topLabel;

    public Order() {
        initComponents();
        try {
            tableData();
        } catch (JSONException ex) {

        }

    }

    String getData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        String url = APIs.GET_USER_ORDERS;

        String token = Metacustomer.loginToken;
        if (token == null) {
            return "";
        }
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", token)
                .build();

        Call call = client.newCall(request);

        try {
            Response res = call.execute();
            return res.body().string();
        } catch (IOException e) {
            System.out.println("err: \n" + e);
            return "";
        }
    }

    final void tableData() throws JSONException {
        String apiData = getData();

        orderDataJSNArr = new JSONArray(apiData);
        String[] columns = {"Ticket", "Symbol", "CurrentPrice", "Status", "StopLoss", "TakeProfit"};
        for (String column : columns) {
            model.addColumn(column);
        }

        setLayout(new BorderLayout());
        JTable jt = new JTable(model);
        jt.setAutoCreateRowSorter(true);
        topLabel = new JLabel();
        orderDataJSNArr = new JSONArray(apiData);
        totalOrder = orderDataJSNArr.length();
        System.out.println("total: " + totalOrder);
        totalCancelled = 0;
        try {
            for (int i = 0; i < orderDataJSNArr.length(); i++) {
                JSONObject jso = orderDataJSNArr.getJSONObject(i);
                String ticket = jso.getString("ticket");
                String symbol = jso.getString("symbol");
                String currentPrice = jso.getString("currentPrice");
                int status = jso.getInt("status");
                String stopLoss = jso.getString("stopLoss");
                String takeProfit = jso.getString("takeProfit");
                if (status == 0) {
                    totalCancelled++;
                }
                if (stopLoss == "null") {
                    stopLoss = "";
                }
                if (takeProfit == "null") {
                    takeProfit = "";
                }

                String[] rowData = {ticket, symbol, currentPrice, status == 1 ? "Filled" : "Cancelled", stopLoss, takeProfit};
                model.addRow(rowData);

            }
        } catch (JSONException ex) {
            ex.getStackTrace();
        }
//        model.fireTableDataChanged();
        topLabel.setText(String.format("Filled: %f, Cancelled: %f, TotalOrder: %f", (totalOrder - totalCancelled), totalCancelled, totalOrder));

//        setTableData();
        add(topLabel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(jt);

        add(scrollPane, BorderLayout.CENTER);
    }

//    public static void setTableData() {
//       
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
