/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer.Auth;

import com.mycompany.metacustomer.HandlePreference;
import com.mycompany.metacustomer.Utility.APIs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
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
public class Signup extends javax.swing.JPanel {

    /**
     * Creates new form Signup
     */
    Vector<String> selectableServerUrl;

    String getData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        String url = APIs.SERVER_JSON;

        Request request = new Request.Builder()
                .url(url).build();

        Call call = client.newCall(request);

        try {
            Response res = call.execute();
            System.out.println("res: " + res.message());
            String body = res.body().string();
            System.out.println("body:  " + body);
            return body;
        } catch (IOException e) {
            System.out.println("Runtime error: deals");
            System.out.println("e" + e);
            return "[]";
        }
    }

    public Signup() {
        initComponents();
        String apiData = getData();
        System.out.println(apiData);
        try {

            JSONArray serverJSON = new JSONArray(apiData);
            selectableServerUrl = new Vector<>();
            Vector<String> selectableServerUrl = new Vector<>();
            for (int i = 0; i < serverJSON.length(); i++) {
                try {
                    JSONObject jso = serverJSON.getJSONObject(i);
                    String name = jso.getString("name");
                    String ip = jso.getString("IPADDRESS");
                    this.selectableServerUrl.add(name);
                    selectableServerUrl.add(ip);
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(this.selectableServerUrl);
            jComboBox1.setModel(model);
            signup();
        } catch (JSONException ex) {
        }

    }

    void signup() {

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = jTextField1.getText();
                    String email = jTextField2.getText();
                    String password = jTextField3.getText();

//                    JSONObject payload = new JSONObject();
//                    payload.put("name", name);
//                    payload.put("email", email);
//                    payload.put("password", password);
                    OkHttpClient client = new OkHttpClient();
                    String apiUrl = APIs.SIGNUP;
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, "");

                    Request request = new Request.Builder()
                            .url(apiUrl)
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                    if (response.isSuccessful()) {
                        String APIDATA = response.body().string();
                        JSONObject jso = new JSONObject(APIDATA);
                        System.out.println(APIDATA);
                        jso = jso.getJSONObject("user");
                        String userId = jso.getString("userId");
                        String password1 = jso.getString("password");
                        String balance = jso.getString("balance");

                        jTextField1.setText(userId);
                        jTextField2.setText(password1);
                        jTextField3.setText(balance);

//                        System.out.println("Signup successful");
//                        String responseString =  response.body()
//.string();
//                        System.out.println("Response: " + responseString);
////                        JSONObject jso = new JSONObject(respon);
//                        System.out.println("jso: " + jso);
//                        String token = jso.getString("token");
//                        HandlePreference pref = new HandlePreference();
//                        pref.saveToken(token);
//                        System.out.println("token: " + token);
//                        String mainArgs[] = {};
//                        Metacustomer.main(mainArgs);
                    } else {
                        System.out.println("Signup Failed" + response.message());
                    }
                } catch (Exception ex) {
                    System.out.println("ex: ");
                    System.out.println(ex);
                    System.out.println("Error occurred while login");
                }
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        jLabel1.setText("USERID : ");

        jLabel2.setText("Password : ");

        jLabel3.setText("Balance :");

        jButton1.setBackground(new java.awt.Color(65, 190, 86));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Open Demo Account");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Server:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel3))
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(jTextField2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(108, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
