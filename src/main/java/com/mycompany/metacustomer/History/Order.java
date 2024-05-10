/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer.History;

import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.Metacustomer;
import com.mycompany.metacustomer.Utility.ApiServices;
import com.mycompany.metacustomer.Utility.Helper;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import okhttp3.Response;
import okhttp3.ResponseBody;
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

    void fetchOrderData() {
        ApiServices services = new ApiServices();
        String url = APIs.GET_USER_ORDERS;
        String token = Metacustomer.loginToken;
        try {
            Response res = services.getDataWithToken(url, token);
            if (res.isSuccessful()) {
                ResponseBody body = res.body();
                String apiData = body.string();
                orderDataJSNArr = new JSONArray(apiData);
            }
        } catch (IOException | JSONException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    void setColumns() {
        String[] columns = {"Time", "Symbol", "Ticket", "Type", "Volume", "Price", "S/L", "T/P", "Time", "State", "Comment"};
        for (String column : columns) {
            model.addColumn(column);
        }
    }

    public void setRowToTable() {
        int orderLength = orderDataJSNArr.length();
        for (int i = 0; i < orderLength; i++) {
            try {
                JSONObject order = orderDataJSNArr.getJSONObject(i);
                String time = Helper.getJSONString(order, "createdAt");
                String symbol = Helper.getJSONString(order, "symbol");
                String ticket = Helper.getJSONString(order, "ticket");
                int typeNum = Helper.getJSONInt(order, "type");
                String type = Helper.getMappedOrderType(typeNum);
                double volume = Helper.getJSONDouble(order, "volume");
                double price = Helper.getJSONDouble(order, "price");
                double sl = Helper.getJSONDouble(order, "stopLoss");
                double tp = Helper.getJSONDouble(order, "takeProfit");
                String ctime = Helper.getJSONString(order, "updatedAt");
                String state = "";
                String comment = Helper.getJSONString(order, "comment");
                String[] rowData = {time, symbol, ticket, type, volume + "", price + "", sl + "", tp + "", ctime, state, comment};
                model.addRow(rowData);
            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public Order() {
        initComponents();

        try {
            tableData();
        } catch (JSONException ex) {

        }

    }

    final void tableData() throws JSONException {
        fetchOrderData();
        setColumns();
        setRowToTable();
        setLayout(new BorderLayout());
        JTable jt = new JTable(model);
        jt.setAutoCreateRowSorter(true);
        topLabel = new JLabel();
        totalOrder = orderDataJSNArr.length();
        totalCancelled = 0;

//        try {
//            for (int i = 0; i < orderDataJSNArr.length(); i++) {
//                JSONObject jso = orderDataJSNArr.getJSONObject(i);
//                String ticket = jso.getString("ticket");
//                String symbol = jso.getString("symbol");
//                String currentPrice = jso.getString("currentPrice");
//                int status = jso.getInt("status");
//                String stopLoss = jso.getString("stopLoss");
//                String takeProfit = jso.getString("takeProfit");
//                String time = jso.getString("createdAt");
//                double initialVolume = jso.getDouble("initialVolume");
//                double currentVolume = jso.getDouble("currentVolume");
//                double volume = currentVolume - initialVolume;
//                if (status == 0) {
//                    totalCancelled++;
//                }
//                if (stopLoss == "null") {
//                    stopLoss = "";
//                }
//                if (takeProfit == "null") {
//                    takeProfit = "";
//                }
//                
//                String[] rowData = {ticket, symbol, time, currentPrice, status == 1 ? "Filled" : "Cancelled", stopLoss, takeProfit, volume + ""};
//                model.addRow(rowData);
//                
//            }
//        } catch (JSONException ex) {
//            ex.getStackTrace();
//        }
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
