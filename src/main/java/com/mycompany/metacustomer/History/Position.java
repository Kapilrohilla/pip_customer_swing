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
//    static int cc = 0;
    public static DefaultTableModel model = new DefaultTableModel();

    public Position() {
        String[] columnNames = {"user_id", "Symbol", "Price", "StopLoss", "TakeProfit"};
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
//        int rowCount = model.getRowCount();
//        for (int i = 0; i < rowCount; i++) {
//            model.removeRow(i);
////            model.fireTableDataChanged();
//        }

        try {
            for (int i = 0; i < closedPositions.length(); i++) {
                JSONObject jso = closedPositions.getJSONObject(i);
                String userId = jso.getString("user_id");
                String symbol = jso.getString("symbol");
                String price = jso.getString("price");
                String stopLoss = jso.getString("stopLoss");
                String takeProfit = jso.getString("takeProfit");

                String[] rowData = {userId, symbol, price, stopLoss, takeProfit};
                Position.model.addRow(rowData);
            }

//            model.fireTableDataChanged();
        } catch (JSONException ex) {
            System.out.println("error Occurred");
            System.out.println("ex");
        }
    }

    /*
    String getData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        String url = APIs.GET_POSITIONS;
        String token = Metacustomer.loginToken;
        if (token == null) {
            System.out.println("token not found token: " + token);
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
            System.out.println("err:-" + "\n" + e);
            System.out.println("Runtime error positions");
            return "";
        }
    }
     */
 /*
    void tableData() throws JSONException {
        DefaultTableModel model = new DefaultTableModel();
        String apiData = getData();
        JLabel topText = new JLabel();
        double totalProfit = 0;
        double totalSwap = 0;
        double totalCommission = 0;
        System.out.println("apiData " + apiData);
        String[] columnNames = {"user_id", "Symbol", "Type", "Volume", "Ticket", "Swap", "Commission", "Price", "StopLoss", "TakeProfit", "profit"};
        for (String column : columnNames) {
            model.addColumn(column);
        }
        JSONObject json = new JSONObject(apiData);
        JSONArray jsa = json.getJSONArray(("positions"));
        for (int i = 0; i < jsa.length(); i++) {
            JSONObject jso = jsa.getJSONObject(i);
            System.out.println("jso: " + jso);
            String userId = jso.getString("user_id");
            String symbol = jso.getString("symbol");
            int orderType = jso.getInt("type");
            String volume = jso.getDouble("volume") + "";
            String ticket = jso.getString("ticket");
            String orderTypeText;
            double comission = jso.getDouble("comission");
            totalCommission += comission;
            String swap;
            try {
                swap = jso.getString("swap");
            } catch (JSONException ex) {
                swap = "";
                System.out.println(ex.getMessage());
            }
            try {
                totalSwap += Double.parseDouble(swap);
            } catch (NumberFormatException ex) {
                totalSwap += 0;
                System.out.println(ex.getMessage());
            }
            double profit = jso.getDouble("profit");
            totalProfit += profit;

            switch (orderType) {
                case 0:
                    orderTypeText = "Sell";
                    break;
                case 1:
                    orderTypeText = "Buy";
                    break;
                case 2:
                    orderTypeText = "Buy Limit";
                    break;
                case 3:
                    orderTypeText = "Sell Limit";
                    break;
                case 4:
                    orderTypeText = "Buy Stop";
                    break;
                case 5:
                    orderTypeText = "Sell Stop";
                    break;
                case 6:
                    orderTypeText = "Buy Stop Limit";
                    break;
                case 7:
                    orderTypeText = "Sell Stop Limit";
                    break;
                default:
                    orderTypeText = "Invalid Order Type";
            }

            String price = jso.getString("price");
            String stopLoss = jso.getString("stopLoss");
            String takeProfit = jso.getString("takeProfit");

            if (stopLoss == "null") {
                stopLoss = "";
            }
            if (takeProfit == "null") {
                takeProfit = "";
            }

            String[] rowData = {userId, symbol, orderTypeText, volume, ticket, swap, comission + "", price, stopLoss, takeProfit, String.format("%.2f", profit)};
            System.out.println("creating rows");
            this.watchlistData.add(jso);
            model.addRow(rowData);
        }

        topText.setText(String.format("Profit: %2f, Swap: %2f, Commission: %2f", totalProfit, totalSwap, totalCommission));

        JTable jt = new JTable(model);
        jt.setAutoCreateRowSorter(true);
        setLayout(new BorderLayout());
        JScrollPane jp = new JScrollPane(jt);
        add(topText, BorderLayout.NORTH);
        add(jp, BorderLayout.CENTER);
    }
     */
 /*
    void listenGetDataSocket() {
        Socket socket = Metacustomer.socket;
        socket.on("getOrder", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
//                System.out.println(args[0]);
                JSONObject jso = (JSONObject) args[0];
                System.out.println("getOrder: " + jso);
                try {
//                    JSONObject jso = new JSONObject(args[0].toString());

                    JSONObject getOrder = jso.getJSONObject("getOrder");
                    boolean isValid = getOrder.getBoolean("valid");
                    if (!isValid) {
                        String message = getOrder.getString("message");
                        JOptionPane.showMessageDialog(Position.this, message);
                    }
                    try {
                        String totalMargin = getOrder.getString("totalMargin");
                        Metacustomer.margin.setText("Margin : " + totalMargin);
                        mmm = Double.parseDouble(totalMargin);
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                    }
                    JSONObject newPosition = getOrder.getJSONObject("position");
                    String symbol = newPosition.getString("symbol");
                    String ticket = newPosition.getString("ticket");
                    String time;
                    try {
                        time = newPosition.getString("createdAt");
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                        time = "";
                    }

                    String type;
                    try {
                        type = newPosition.getInt("type") == 0 ? "Sell" : "Buy";
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                        type = "NULL";
                    }

                    int status;
                    try {
                        status = newPosition.getInt("status");
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                        status = 0;
                    }

                    String volume;
                    try {
                        volume = newPosition.getDouble("volume") + "";
                    } catch (JSONException ex) {
                        volume = "";
                    }
                    String price;
                    try {
                        price = newPosition.getDouble("price") + "";
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                        price = "0";
                    }

                    String stopLoss;
                    String takeProfit;
                    String swap = "";
                    String comment;
                    String profit = price;
                    String action = "Close position";

                    try {
                        stopLoss = newPosition.getDouble("stopLoss") + "";
                    } catch (JSONException ex) {
                        stopLoss = "0";
                    }
                    try {
                        takeProfit = newPosition.getDouble("takeProfit") + "";
                    } catch (JSONException ex) {
                        takeProfit = "0";
                    }

                    try {
                        comment = newPosition.getString("comment");
                        if ("null".equals(comment)) {
                            comment = "";
                        }
                    } catch (JSONException ex) {
                        comment = "";
                    }

                    Vector<String> newRow = new Vector();
                    newRow.add(symbol);
                    newRow.add(ticket);
                    newRow.add(time);
                    newRow.add(type);
                    newRow.add(volume);
                    newRow.add(price);
                    newRow.add(stopLoss);
                    newRow.add(takeProfit);
                    newRow.add(swap);
                    newRow.add(comment);
                    newRow.add(profit);
                    newRow.add(action);
                    model.addRow(newRow);
                    watchlistData.add(newPosition);
                } catch (JSONException ex) {
                    System.out.println("exception occurred in newOrder event in Trade panel");
                    System.out.println(ex.getMessage());
                }

            }
        });
    }
     */
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
