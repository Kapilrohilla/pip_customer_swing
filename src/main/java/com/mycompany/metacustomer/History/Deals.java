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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Deals extends javax.swing.JPanel {

    /**
     * Creates new form Deals
     */
    public Deals() {
        initComponents();
        try {
            tableData();
        } catch (JSONException ex) {
            Logger.getLogger(Deals.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String getData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        String url = APIs.GET_USER_DEALS;
        // get-positions
        // get-user-orders
        // Create a Request object
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
            System.out.println("res: " + res.message());
            return res.body().string();
        } catch (IOException e) {
            System.out.println("Runtime error: deals");
            System.out.println("e" + e);
            return "";
        }
    }

    final void tableData() throws JSONException {
        DefaultTableModel model = new DefaultTableModel();
        String apiData = getData();
        System.out.println("apidata" + apiData);
        JSONArray jsnArray = new JSONArray(apiData);
        String[] columns = {"Ticket", "Symbol", "Volume", "Status", "StopLoss", "TakeProfit", "Profit"};
        for (String column : columns) {
            model.addColumn(column);
        }
        double totalSwap = 0;
        double totalCommission = 0;
        double totalProfit = 0;
        for (int i = 0; i < jsnArray.length(); i++) {
            JSONObject jso = jsnArray.getJSONObject(i);
            System.out.println("deal's jso: " + jso);
            String symbol = jso.getString("symbol");
            String ticket = jso.getString("ticket");
            String volume = jso.getDouble("volume") + "";
            int status = jso.getInt("status");
            String statusText = status == 1 ? "Fulfilled" : "Canceled";
            double profit = jso.getDouble("profit");
            double swap;
            try {
                swap = jso.getDouble("swap");
            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
                swap = 0;
            }
            totalProfit += profit;
            totalSwap += swap;
            double comm;
            try {
                comm = jso.getDouble("comission");
            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
                comm = 0;
            }
            totalCommission += comm;
            String stopLoss;
            try {
                stopLoss = jso.getString("stopLoss");

                if (stopLoss == "null") {
                    stopLoss = "";
                }
            } catch (JSONException ex) {
                stopLoss = "";
            }
            String takeProfit = jso.getString("takeProfit");
            if (takeProfit == "null") {
                takeProfit = "";
            }
            String[] rowData = {ticket, symbol, volume, statusText, stopLoss, takeProfit, String.format("%.2f", profit)};

            model.addRow(rowData);
        }

        JLabel topLabel = new JLabel();

        setLayout(new BorderLayout());
        JTable jt = new JTable(model);
        jt.setAutoCreateRowSorter(true);
        topLabel.setText(String.format("Profit: %.2f,  Comission: %.2f, Profit: %.2f ", totalProfit, totalCommission, totalSwap));

        JScrollPane scrollPane = new JScrollPane(jt);
        add(topLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

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
