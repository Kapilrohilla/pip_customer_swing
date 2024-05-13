/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer.History;

import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.Metacustomer;
import com.mycompany.metacustomer.Utility.Helper;
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
//        String[] columns = {"Ticket", "Symbol", "Order", "Time", "Type", "Direction", "Commission", "Volume", "Status", "StopLoss", "TakeProfit", "Profit"};
        String[] columns = {"Time", "Symbol", "Deal", "Order", "Type", "Direction", "Volume", "Price", "S/L", "T/P", "Commission", "Fee", "Swap", "Profit", "Change", "Comment"};
        for (String column : columns) {
            model.addColumn(column);
        }
        double totalSwap = 0;
        double totalCommission = 0;
        double totalProfit = 0;
        for (int i = 0; i < jsnArray.length(); i++) {
            JSONObject jso = jsnArray.getJSONObject(i);
            System.out.println("deal's jso: " + jso);
            // new one
            String time = Helper.getJSONString(jso, "createdAt");
            String symbol = Helper.getJSONString(jso, "symbol");
            String deal = Helper.getJSONString(jso, "_id");
            String order = Helper.getJSONString(jso, "order");
            int typeNum = Helper.getJSONInt(jso, "type");
            String type = Helper.getMappedOrderType(typeNum);
            int directionNum = Helper.getJSONInt(jso, "entry");

            String direction = directionNum == 0 ? "Out" : "In";
            double volume = Helper.getJSONDouble(jso, "volume");
            double price = Helper.getJSONDouble(jso, "price");
            String price2Display = String.format("%.2f", price);
            double stopLoss = Helper.getJSONDouble(jso, "stopLoss");
            String stopLoss2Display = String.format("%.2f", stopLoss);
            double takeProfit = Helper.getJSONDouble(jso, "takeProfit");
            String takeProfit2Display = String.format("%.2f", takeProfit);
            double comission = Helper.getJSONDouble(jso, "comission");
            String comission2Display = String.format("%.2f", comission);
            totalCommission += comission;
            double fee = Helper.getJSONDouble(jso, "fee");
            String fee2Display = String.format("%.2f", fee);
            double swap = Helper.getJSONDouble(jso, "swap");
            String swap2Display = String.format("%.2f", swap);
            double profit = Helper.getJSONDouble(jso, "profit");
            String profit2display = String.format("%.2f", profit);
            totalProfit += profit;
            String change = "";
            String initialComment = Helper.getJSONString(jso, "comment");
            String comment = initialComment;
            if ("null".equals(initialComment)) {
                comment = initialComment;
            }
            String[] rowData = {time, symbol, deal, order, type, direction, volume + "", price2Display, stopLoss2Display, takeProfit2Display, comission2Display, fee2Display, swap2Display, profit2display, change, comment};
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
