/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
//package Level1Socket;
package com.mycompany.metacustomer;

import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.History.Deals;
import com.mycompany.metacustomer.History.Order;
import com.mycompany.metacustomer.History.Position;
import com.mycompany.metacustomer.History.Trade;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author techninza
 */
public class BottomPanel extends javax.swing.JPanel {

    /**
     * Creates new form BottomPanel
     */
    public BottomPanel() {
        initComponents();
        String apiData = getData(APIs.GET_CLOSED_POSITION);
        JSONArray history_data;
        try {
            history_data = new JSONArray(apiData);
        } catch (JSONException ex) {
            history_data = new JSONArray();
            ex.getStackTrace();
        }

        tab(history_data);
    }

    private JTabbedPane tabbedPane = new JTabbedPane();

    final String getData(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

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

    private void tab(JSONArray HistoryData) {
        JournalPanel tab8 = new JournalPanel();

        Position tab9;
        if (HistoryData.isNull(0)) {
            System.out.println("HistoryData is null");
            Position.closedPositions = new JSONArray();
            tab9 = new Position();
        } else {
            System.out.println("HistoryData is not null");
            Position.closedPositions = HistoryData;
            tab9 = new Position();
        }

        Order tab10 = new Order();
        Deals tab11 = new Deals();
        Trade tab12 = new Trade();

        tabbedPane.addTab("Trade", tab12);
        tabbedPane.addTab("Position", tab9);
        tabbedPane.addTab("Order", tab10);
        tabbedPane.addTab("Deals", tab11);

        setLayout(new BorderLayout());

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int positionTabIndex = tabbedPane.getSelectedIndex();
                switch (positionTabIndex) {
                    case 0:
                        System.out.println("Trade");
                        break;
                    case 1:
                        System.out.println("positons");
                        try {
                            String apiData = getData(APIs.GET_CLOSED_POSITION);
                            JSONArray apiJSA = new JSONArray(apiData);
                            Position.closedPositions = apiJSA;
                            tab9.clearTableRows();
                            tab9.setTableData();
                            Position.model.fireTableDataChanged();
//                            Position.setTableData();
                        } catch (JSONException ex) {
                            ex.getStackTrace();
                        }
                        break;
                    case 2:
                        System.out.println("order");
                        try {
                            String apiData = getData(APIs.GET_USER_ORDERS);
                            JSONArray apiJSA = new JSONArray(apiData);
                            System.out.println("apiJSA: " + apiJSA);
//                            Order.orderDataJSNArr = apiJSA;
//                            Order.setTableData();
                        } catch (JSONException ex) {
                            ex.getStackTrace();
                        }

                        break;
                    case 3:
                        System.out.println("deals");
                        break;
                    default:
                        break;
                }

            }
        });

        add(tabbedPane, BorderLayout.CENTER);
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
