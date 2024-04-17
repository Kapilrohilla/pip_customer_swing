/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer;

import com.mycompany.metacustomer.Utility.APIs;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
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
public class JournalPanel extends javax.swing.JPanel {

    public JournalPanel() {
        initComponents();
        try {
            tabledata();
        } catch (JSONException ex) {
            System.out.println("failed to fetch journal data");
        }
    }

    String getData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        String url = APIs.GET_JOURNAL;

        Request request = new Request.Builder()
                .url(url).build();

        Call call = client.newCall(request);

        try {
            Response res = call.execute();
            return res.body().string();
        } catch (IOException e) {
            System.out.println("err: \n" + e);
            return "";
        }
    }

    private void tabledata() throws JSONException {
        DefaultTableModel tablemodel = new DefaultTableModel();
        String[] columns = {"Time", "Server", "Message"};
        for (String column : columns) {
            tablemodel.addColumn(column);
        }

        String apiData = getData();
        System.out.println("journal apiData: " + apiData);
        JSONObject jso = new JSONObject(apiData);

        JSONArray jsa = jso.getJSONArray("message");
        System.out.println("jsa length = " + jsa.length());
        for (int i = 0; i < jsa.length(); i++) {
            JSONObject js1 = jsa.getJSONObject(i);
            String time = js1.getString("createdAt");
            String message = js1.getString("text");
            String server = js1.getString("server");

            String[] rowData = {time, server, message};
            tablemodel.addRow(rowData);
        }

        JTable jTable1 = new JTable(tablemodel);
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(jTable1);
        add(scrollPane, BorderLayout.CENTER);

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

}
