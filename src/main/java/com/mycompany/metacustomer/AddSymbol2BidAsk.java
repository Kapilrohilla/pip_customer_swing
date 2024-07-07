/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.metacustomer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mycompany.metacustomer.Utility.APIs;
import java.util.Vector;

/**
 *
 * @author Kapil
 */
public class AddSymbol2BidAsk extends javax.swing.JFrame {

    private ArrayList<String> arraylist = new ArrayList<>();
    String selectedSymbol = null;

    public AddSymbol2BidAsk() throws JSONException {
        initComponents();
        String groupSymbol = getData();

        JSONObject jso = new JSONObject(groupSymbol);
        JSONArray jsa = jso.getJSONArray("symbolData");

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < jsa.length(); i++) {
            JSONObject symbolObj = jsa.getJSONObject(i);
            System.out.println("symbolObj: " + symbolObj);
            String symbol = symbolObj.getString("Symbol");
            arraylist.add(symbol);
            System.out.println("symbol: " + symbol);
            listModel.addElement(symbol);
        }
        jList1.setModel(listModel);
        jList1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                int index = list.locationToIndex(evt.getPoint());
                if (evt.getClickCount() == 1) {
                    selectedSymbol = arraylist.get(index);
                    System.out.println("selectedSymbol = " + selectedSymbol);
                }
                if (evt.getClickCount() == 2) {
                    String symbolClicked = arraylist.get(index);
                    try {
                        PostData(symbolClicked);
                    } catch (JSONException | IOException ex) {
                        System.out.println("eception occurred in add Symbol");
                        System.out.println(ex);
                    }

                }
            }
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
    }

    final void DeleteSymbol(String symbolName) throws JSONException, IOException {

        OkHttpClient client = new OkHttpClient();
        String token = Metacustomer.loginToken;

        // groups/symbol
        String symbolApiUrl = APIs.DELETE_SYMBOL;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jso = new JSONObject();
        jso.put("symbol", symbolName);
        RequestBody body = RequestBody.create(JSON, jso.toString());

        Request request = new Request.Builder()
                .url(symbolApiUrl)
                .post(body)
                .header("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        System.out.println("resonse body" + responseBody);
    }

    final String getData() {
        OkHttpClient client = new OkHttpClient();
        String token = Metacustomer.loginToken;

        // groups/symbol
        String symbolApiUrl = APIs.GET_GROUP_SYMBOLS;
        Request symbolRequest = new Request.Builder()
                .url(symbolApiUrl)
                .header("Authorization", token)
                .build();

        Call call2 = client.newCall(symbolRequest);
        try {
            Response symbolResponse = call2.execute();
            String groupSymbol = symbolResponse.body().string();
            String symbolsInGroup = groupSymbol;
            return symbolsInGroup;
        } catch (IOException e) {
            System.out.println("Runtime error");
            return "";
        }
    }

    final void PostData(String symbolName) throws JSONException, IOException {
        OkHttpClient client = new OkHttpClient();
        String token = Metacustomer.loginToken;

        // groups/symbol
        String symbolApiUrl = APIs.ADD_SYMBOL;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jso = new JSONObject();
        jso.put("symbol", symbolName);
        RequestBody body = RequestBody.create(JSON, jso.toString());

        Request request = new Request.Builder()
                .url(symbolApiUrl)
                .post(body)
                .header("Authorization", Metacustomer.loginToken)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JSONObject responseJSON = new JSONObject(responseBody);
        try {
            boolean isValid = responseJSON.getBoolean("valid");
            if (isValid) {
                Vector<String> newRow = new Vector<>();
                newRow.add(selectedSymbol);
                newRow.add("");
                newRow.add("");
                newRow.add("");
                LeftPanel.tableModel.addRow(newRow);
                System.out.println(responseJSON);
                // JSONObject newSymbolData = responseJSON.getJSONObject("symbolData");
                System.out.println("FUCK: " + LeftPanel.watchlistData.size());
                JSONObject newSymbolData = new JSONObject();
                newSymbolData.put("symbol", selectedSymbol);
                newSymbolData.put("bid", 0);
                newSymbolData.put("ask", 0);
                newSymbolData.put("volumw", 1);
                LeftPanel.watchlistData.add(newSymbolData);
                System.out.println("FUCK: " + LeftPanel.watchlistData.size());

            }
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("resonse body" + responseBody);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddSymbol2BidAsk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddSymbol2BidAsk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddSymbol2BidAsk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddSymbol2BidAsk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AddSymbol2BidAsk().setVisible(true);
                } catch (JSONException ex) {
                    System.out.println("error occurred in addsymbol2bidask frame");
                    System.out.println(ex);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
