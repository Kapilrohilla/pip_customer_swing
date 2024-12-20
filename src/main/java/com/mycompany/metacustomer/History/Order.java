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
    public int totalCancelled = 0;
    public int totalOrder = 0;
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
                String sl2display = sl > 0 ? sl + "" :"";
                double tp = Helper.getJSONDouble(order, "takeProfit");
                String tp2display = tp > 0 ? tp + "" : "";
                String ctime = Helper.getJSONString(order, "updatedAt");
                String state = "";
                String comment = Helper.getJSONString(order, "comment");
                String[] rowData = {time, symbol, ticket, type, volume + "", price + "", sl2display ,tp2display, ctime, state, comment};
                model.addRow(rowData);
            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void setStripData() {
        topLabel = new JLabel();
        totalOrder = orderDataJSNArr.length();
        totalCancelled = 0;
        topLabel.setText(String.format("Filled: %d, Cancelled: %d, TotalOrder: %d", (totalOrder - totalCancelled), totalCancelled, totalOrder));
    }

    public Order() {
        initComponents();
        tableData();
    }

    final void tableData() {
        fetchOrderData();
        setColumns();
        setRowToTable();
        setLayout(new BorderLayout());
        JTable jt = new JTable(model);
        jt.setAutoCreateRowSorter(true);
        setStripData();
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
