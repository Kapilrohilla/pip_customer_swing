/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer.History;

import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.LeftPanel;
import com.mycompany.metacustomer.Metacustomer;
import static com.mycompany.metacustomer.Metacustomer.bal;
import com.mycompany.metacustomer.OrderFrame;
import com.mycompany.metacustomer.Utility.Helper;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kapilrohilla
 */
public class Trade extends javax.swing.JPanel {

    public static double updatedbal, eq, mmm;
    public static JLabel j1;
    private boolean isBalanceSet = false;
    private ArrayList<JSONObject> watchlistData = new ArrayList<>();
    private int selectedRow = -1;
    public static DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    ;
    };
    Socket socket = Metacustomer.socket;

    /**
     * Creates new form Trade
     */
    public Trade() {
        initComponents();
        try {
            table();
            try {
                setData();
                listenGetOrderEvent();
                listenPosData();
                listenPosProfit();
                listenClosePosition();
            } catch (Exception ex) {
                System.out.println("JSON exception occurred in socket connection at trade panel");
                System.out.println(ex);
            }

        } catch (JSONException ex) {
            System.out.println("exception occurred in table funciton of trade panel");
            System.out.println(ex);
        }
    }

    final void listenGetOrderEvent() {
        socket.on("getOrder", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jso = (JSONObject) args[0];
                System.out.println("recieved getorder: " + jso);
                try {
                    JSONObject getOrder = jso.getJSONObject("getOrder");
                    boolean isValid = getOrder.getBoolean("valid");
                    if (!isValid) {
                        String message = getOrder.getString("message");
                        JOptionPane.showMessageDialog(Trade.this, message);
                    }
                    try {
                        String totalMargin = getOrder.getString("totalMargin");
                        Metacustomer.margin.setText("Margin : " + totalMargin);
                        mmm = Double.parseDouble(totalMargin);
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                    }
                    JSONObject newPosition = getOrder.getJSONObject("position");
                    System.out.println("newPositions: " + newPosition);
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
                        int typeNum = newPosition.getInt("type");
                        switch (typeNum) {
                            case 0: {
                                type = "Sell";
                                break;
                            }
                            case 1: {
                                type = "Buy";
                                break;
                            }
                            case 2: {
                                type = "Buy Limit";
                                break;
                            }
                            case 3: {
                                type = "Sell Limit";
                                break;
                            }
                            case 4: {
                                type = "Buy Stop";
                                break;
                            }
                            case 5: {
                                type = "Buy Stop Limit";
                                break;
                            }
                            case 6: {
                                type = "Sell Stop Limit";
                                break;
                            }
                            default: {
                                type = "Invalid Type";
                            }
                        }
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
                    newRow.add("");
                    if (status == 1) {
                        newRow.add(profit);
                    } else {
                        newRow.add("Placed");
                    }
//                    newRow.add(profit);
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

    final void listenPosData() {
        socket.on("posData", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jso = (JSONObject) args[0];
                System.out.println("posData: " + jso);
                try {
                    double freeMargin = jso.getDouble("freeMargin");
                    double margin = jso.getDouble("margin");
                    double balance = jso.getDouble("balance");
                    double level = jso.getDouble("level");
                    double runningProfit = jso.getDouble("runningProfit");
                    double equity = jso.getDouble("equity");
                    double credit = jso.getDouble("credit");
                    if (!Trade.this.isBalanceSet) {
                        String balance2display = String.format("%.2f", balance);
                        jLabel2.setText(balance2display);
                        Trade.this.isBalanceSet = true;
                    }
                    String equity2display = String.format("%.2f", equity);
                    jLabel4.setText(equity2display);
                    String credit2display = String.format("%.2f", credit);
                    jLabel6.setText(credit2display);
                    String margin2display = String.format("%.2f", margin);
                    jLabel8.setText(margin2display);
                    String freeMarginDisplay = String.format("%.2f", freeMargin);
                    jLabel10.setText(freeMarginDisplay);
                    String level2display = String.format("%.2f", level);
                    jLabel12.setText(level2display);
                    String runningProfit2display = String.format("%.2f", runningProfit);
                    jLabel14.setText(runningProfit2display);
                } catch (JSONException ex) {
                    System.out.println("ex: " + ex.getMessage());
                }
            }
        });
    }

    final void listenPosProfit() {
        socket.on("posProfit", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                JSONObject posProfit = (JSONObject) os[0];
//                System.out.println("posProfit: " + posProfit);
                try {
//                    System.out.println("posProfit: " + posProfit);
                    double profit = posProfit.getDouble("profit");
//                    System.out.println("profit: " + profit);
                    String positionId = posProfit.getString("positionId");
                    double currentPrice = posProfit.getDouble("currentPrice");

                    for (int i = 0; i < watchlistData.size(); i++) {
                        JSONObject positionJSON = watchlistData.get(i);
                        String watchPositionId = positionJSON.getString("_id");
                        if (positionId.equals(watchPositionId)) {
                            String profit2display = String.format("%.2f", profit);
                            String currentPrice2display = String.format("%.2f", currentPrice);
                            model.setValueAt(profit2display, i, 11);
                            model.setValueAt(currentPrice2display, i, 10);
                            model.fireTableDataChanged();
                        }
                    }
                } catch (JSONException ex) {
                    ex.getStackTrace();
                }
            }
        });
    }

    final String getUserData() {
        OkHttpClient client = new OkHttpClient();
        String url = APIs.USER;
        System.out.println(url);
        String token = Metacustomer.loginToken;
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", token)
                .build();
        Call call = client.newCall(request);

        try {
            Response res = call.execute();
            return res.body().string();
        } catch (IOException e) {
            System.out.println("Error occurred while fetching trading account specific overview");
            return "";
        }
    }

    final void setData() {
        String apiData = getUserData();
        System.out.println("apiData: " + apiData);
        try {
            JSONObject jso = new JSONObject(apiData);
            JSONObject userData = jso.getJSONObject("user");
            double balance = userData.getDouble("balance");
            double credit = userData.getDouble("credit");
            double margin = userData.getDouble("margin");
            String user_id = userData.getString("email");
            System.out.println(":::::::::::::::::::::");
            System.out.println("user_id:  " + user_id);
            System.out.println(":::::::::::::::::::::");
            jLabel6.setText(credit + "");
            jLabel8.setText(margin + "");
            jLabel2.setText(balance + "");
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void updateData() {
        updatedbal = Double.parseDouble(bal);
        int rows = Trade.model.getRowCount();
        int lastColumn = 10;
        double runningProfit = 0;
        for (int i = 0; i < rows; i++) {
            String value = Trade.model.getValueAt(i, lastColumn).toString();
            if (!"placed".equalsIgnoreCase(value)) {
                runningProfit += Double.parseDouble(value);
            }
        }

        eq = updatedbal + runningProfit;
        String equity = "Equity: " + String.format("%.2f", eq);
        if (mmm != 0) {
            double fm = eq - mmm;
            double levelper = ((eq / mmm) * 100);
            String freemargin = String.format("%.2f", fm);
            j1.setText("Balance : " + bal + " Equity : " + eq + " Margin : " + mmm + " FreeMargin : " + freemargin + " Level : " + levelper);
        }

    }

    private void table() throws JSONException {

        String[] columns = {"Symbol", "Ticket", "Time", "type", "Volume", "Price", "StopLoss", "TakeProfit", "Swap", "Comment", "Current Price", "Profit", "Action"};
        for (String column : columns) {
            model.addColumn(column);
        }
        String apiData = getData();
        JSONObject json = new JSONObject(apiData);
        JSONArray jsa = json.getJSONArray("positions");
//        setTableData(jsa);

        try {
            for (int i = 0; i < jsa.length(); i++) {
                JSONObject jso = jsa.getJSONObject(i);

//                String symbol = jso.getString("symbol");
                String symbol = Helper.getJSONString(jso, "symbol");

                int typeNum = Helper.getJSONInt(jso, "type");
                String type = Helper.getMappedOrderType(typeNum);

                String createdAt = Helper.getJSONString(jso, "createdAt");
                double stopLoss = Helper.getJSONDouble(jso, "stopLoss");
                String stopLossS = stopLoss + "";
                double takeProfit = Helper.getJSONDouble(jso, "takeProfit");
                String takeProfitS = takeProfit + "";
                double volume = Helper.getJSONDouble(jso, "volume");
                String volumeS = volume + "";
                double price = Helper.getJSONDouble(jso, "price");
                String priceS = price + "";
                String ticket = Helper.getJSONString(jso, "ticket");
//                String ticket = jso.getString("ticket");

                String time = createdAt;
                try {
                    String year = createdAt.substring(0, 3);
                    String month = createdAt.substring(5, 6);
                    String day = createdAt.substring(8, 9);

                    time = day + "/" + month + "/" + year;
                } catch (Exception ex) {
                    System.out.println("error occurred");
                    time = "";
                }

                String swap = "";
                String comment;
                try {
                    comment = jso.getString("comment");
                    if ("null".equals(comment)) {
                        comment = "";
                    }
                } catch (JSONException ex) {
                    comment = "";
                }
                int profitNum = jso.getInt("status");
                String profit = "";
                if (profitNum == 0) {
                    profit = "Placed";
                }
                String[] rowData = {symbol, ticket, createdAt, type, volumeS, priceS, stopLossS, takeProfitS, swap, comment, "", profit, "Close Position"};
                watchlistData.add(jso);
                model.addRow(rowData);
                System.out.println("trade updated.");
            }
        } catch (JSONException ex) {
            ex.getStackTrace();
        }

        jTable1.setModel(model);
        JPopupMenu jp = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("New Order");
        JMenuItem menuItem2 = new JMenuItem("Close Position");
        JMenuItem menuItem3 = new JMenuItem("Delete");
        JMenuItem menuItem4 = new JMenuItem("Close All Positions");
        JMenuItem menuItem5 = new JMenuItem("Close Profitable Positions");
        JMenuItem menuItem6 = new JMenuItem("Close Losing Positions");
        JMenuItem menuItem7 = new JMenuItem("Modify");
        jp.add(menuItem1);

        jp.add(menuItem2);

        jp.add(menuItem3);

        jp.addSeparator();

        jp.add(menuItem4);

        jp.add(menuItem5);

        jp.add(menuItem6);

        jp.add(menuItem7);
        menuItem1.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                new OrderFrame().setVisible(true);
            }

        });

        menuItem7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
//                    try {
                    JSONObject jso = watchlistData.get(selectedRow);
                    String positionId = Helper.getJSONString(jso, "_id");
                    String ticket = Helper.getJSONString(jso, "ticket");
                    String stopLoss = Helper.getJSONString(jso, "stopLoss");
                    String takeProfit = Helper.getJSONString(jso, "takeProfit");
                    new ModifyPosition(positionId, ticket, stopLoss, takeProfit).setVisible(true);
//                    } catch (JSONException ex) {
//                        System.out.println(ex.getMessage());
//                    }

                }
            }
        });
        menuItem4.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                JSONObject requestBody = new JSONObject();
                Request request = new Request.Builder()
                        .url(APIs.CLOSE_ALL_POSITION)
                        .addHeader("Authorization", Metacustomer.loginToken)
                        .post(RequestBody.create(
                                MediaType.parse("application/json"), requestBody.toString()
                        ))
                        .build();
                OkHttpClient client = new OkHttpClient();
                Call call = client.newCall(request);

                try {
                    Response res = call.execute();
                    System.out.println("res: " + res.body().toString());
                    if (res.isSuccessful()) {
                        JOptionPane.showMessageDialog(Trade.this, "All Position Closed");
                    }
                } catch (IOException ex) {
                    System.out.println("exception occurred while executing close all position");
                    System.out.println(ex);
                }
            }
        }
        );

        menuItem5.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                JSONObject requestBody = new JSONObject();
                Request request = new Request.Builder()
                        .url(APIs.CLOSE_PROFITABLE_POSITION)
                        .addHeader("Authorization", Metacustomer.loginToken)
                        .post(RequestBody.create(
                                MediaType.parse("application/json"), requestBody.toString()
                        ))
                        .build();
                OkHttpClient client = new OkHttpClient();

                Call call = client.newCall(request);

                try {
                    Response res = call.execute();
                    System.out.println("res: " + res.body().toString());
                    if (res.isSuccessful()) {
                        JOptionPane.showMessageDialog(Trade.this, "All Proiftable position closed");
                    }
                } catch (IOException ex) {
                    System.out.println("exception occurred while executing close all position");
                    System.out.println(ex);
                }
            }
        }
        );

        menuItem6.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                JSONObject requestBody = new JSONObject();
                Request request = new Request.Builder()
                        .url(APIs.CLOSE_LOSING_POSITION)
                        .addHeader("Authorization", Metacustomer.loginToken)
                        .post(RequestBody.create(
                                MediaType.parse("application/json"), requestBody.toString()
                        ))
                        .build();
                OkHttpClient client = new OkHttpClient();

                Call call = client.newCall(request);

                try {
                    Response res = call.execute();
                    System.out.println("res: " + res.body().toString());
                    if (res.isSuccessful()) {
                        JOptionPane.showMessageDialog(Trade.this, "All Losing Position closed");
                    }
                } catch (IOException ex) {
                    System.out.println("exception occurred while executing close all position");
                    System.out.println(ex);
                }
            }
        }
        );

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    jp.show(e.getComponent(), e.getX(), e.getY());
                }

                selectedRow = jTable1.rowAtPoint(e.getPoint());
                int selectedColumn = jTable1.columnAtPoint(e.getPoint());
                int totalColumn = jTable1.getColumnCount();
                selectedRow = jTable1.rowAtPoint(e.getPoint());
                if (totalColumn - 1 == selectedColumn) {
                    try {
                        String positionId = watchlistData.get(selectedRow).getString("_id");

                        JSONObject rawBody = new JSONObject();
                        rawBody.put("positionId", positionId);

                        try {
                            OkHttpClient client = new OkHttpClient.Builder().build();
                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            RequestBody body = RequestBody.create(JSON, rawBody.toString());
                            Request request = new Request.Builder().url(APIs.CLOSE_POSITION)
                                    .method("POST", body)
                                    .header("Authorization", Metacustomer.loginToken).build();

                            Response response = client.newCall(request).execute();
                            String responsebody = response.body().string();
                            System.out.println("responsedbody: " + responsebody);

                            // JSONObject responseJSON = new JSONObject(responsebody);
                            if (response.isSuccessful()) {
                                JSONObject res = new JSONObject(responsebody);
                                String message = res.getString("message");
                                double margin = res.getDouble("margin");
                                double balance = res.getDouble("balance");
                                jLabel8.setText(String.format("%.2f", margin));
                                jLabel2.setText(String.format("%.2f", balance));
                                if ("sucess".equals(message)) {
                                    model.removeRow(selectedRow);
                                }
                            }

                        } catch (IOException ex) {
                            System.out.println("Exeption occurred while closing the Positoin using position id=" + positionId);
                        }

                    } catch (JSONException ex) {
                        System.out.println("exception occureed while fetching the _id for close position");
                    }
                }

            }
        }
        );

        j1 = new JLabel();

//        JScrollPane jscroll = new JScrollPane(jt);
//        setLayout(new BorderLayout());
//        add(j1, BorderLayout.NORTH);
//        add(j1jTable1, BorderLayout.CENTER);
    }

    private void listenClosePosition() {
        socket.on("closePosition", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
//                    try {
                JSONObject closePositionResponse = (JSONObject) os[0];
//                System.out.println("jso: " + closePositionResponse);
                try {
                    String positionId = closePositionResponse.getString("positionId");
                    for (int i = 0; i < watchlistData.size(); i++) {
                        JSONObject position = watchlistData.get(i);
                        String watchPositionId = position.getString("_id");
                        if (watchPositionId.equals(positionId)) {
                            watchlistData.remove(i);
                            model.removeRow(i);
                            model.fireTableRowsDeleted(i, i);
                        }
                    }
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
//                    } catch (JSONException ex) {
//                        System.out.println(ex.getMessage());
//                    }

            }
        });
    }

    final String getData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        String url = APIs.GET_POSITIONS;
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        jLabel1.setText("Balance:");

        jLabel2.setText("0.00");
        jLabel2.setMinimumSize(new java.awt.Dimension(60, 16));

        jLabel3.setText("Equity:");

        jLabel4.setText("0.00");
        jLabel4.setMinimumSize(new java.awt.Dimension(60, 16));

        jLabel5.setText("Credit:");

        jLabel6.setText("0.00");
        jLabel6.setMinimumSize(new java.awt.Dimension(60, 16));

        jLabel7.setText("Margin:");

        jLabel8.setText("0.00");
        jLabel8.setMinimumSize(new java.awt.Dimension(60, 16));

        jLabel9.setText("Free margin:");

        jLabel10.setText("0.00");
        jLabel10.setMinimumSize(new java.awt.Dimension(60, 16));

        jLabel11.setText("Margin level:");

        jLabel12.setText("0.00");
        jLabel12.setMinimumSize(new java.awt.Dimension(60, 16));

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
        jScrollPane1.setViewportView(jTable1);

        jLabel13.setText("Running Profit:");

        jLabel14.setText("0.00");
        jLabel14.setMinimumSize(new java.awt.Dimension(60, 16));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 51, Short.MAX_VALUE)
                .addGap(409, 409, 409))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    public static javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
