/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer.Auth;

import com.mycompany.metacustomer.HandlePreference;
import com.mycompany.metacustomer.Metacustomer;
import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.Utility.ApiServices;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javafx.util.Pair;
import javax.swing.DefaultComboBoxModel;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kapilrohilla
 */
interface SignupFr {
//    final Vector<String> selectableServerUrl;

    JSONArray fetchServers() throws IOException;

    void handleSignup();

    void handleProceed2Login();

    void setServerList(JSONArray servers_api_response);

    void setSignupResponse(JSONObject signup_response);

}

public class Signup extends SignupDesign implements SignupFr {

    JSONArray serverJSON;
    String token;

    /**
     *
     * @return @throws java.io.IOException
     */
    public final JSONArray fetchServers() throws IOException {
        String url = APIs.SERVER_JSON;
        ApiServices services = new ApiServices();
        try {
            Response response = services.getDataWithoutToken(url);
            if (response.isSuccessful()) {
//                return
                ResponseBody apiData = response.body();
                String apiResponse = apiData.string();
                try {
                    JSONArray jsa = new JSONArray(apiResponse);
                    return jsa;
                } catch (JSONException ex) {
                    System.out.println(ex.getStackTrace());
                }

            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (IOException ex) {
            System.out.println("Error: Failed to retrieve " + url + " response.");
            System.out.println(ex.getMessage());
        }
        return new JSONArray();
    }

    public final void handleSignup() {
        String url = APIs.SIGNUP;
        ApiServices services = new ApiServices();
        try {
            Response res = services.postDataWithoutToken(url, new JSONObject());
            if (res.isSuccessful()) {
                ResponseBody resBody = res.body();
                String resString = resBody.string();
                try {
                    JSONObject resJSON = new JSONObject(resString);
                    setSignupResponse(resJSON);
                } catch (JSONException ex) {
                    System.out.println(ex.getStackTrace());

                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void handleProceed2Login() {
        HandlePreference pref = new HandlePreference();
        pref.saveToken(this.token);
        String mainArgs[] = {};
        Metacustomer.main(mainArgs);
        AuthContainer.abc.dispose();
    }

    public final void setServerList(JSONArray servers_api_response) {
        Vector<String> serverUrlTitles = new Vector<>();
        for (int i = 0; i < servers_api_response.length(); i++) {
            try {
                JSONObject jso = servers_api_response.getJSONObject(i);
                String name = jso.getString("name");
                System.out.println("name: " + name);
                serverUrlTitles.add(name);
            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
            }
        }
        DefaultComboBoxModel comboboxModel = new DefaultComboBoxModel(serverUrlTitles);
        jComboBox1.setModel(comboboxModel);
    }

    public final void setSignupResponse(JSONObject signup_response) {
        // signup-response contain message, user, token
        try {
            JSONObject userDetails = signup_response.getJSONObject("user");
            String userId = userDetails.getString("userId");
            String password = userDetails.getString("password");
            String bal = userDetails.getString("balance");

            String token = signup_response.getString("token");
            jTextField1.setText(userId);
            jTextField2.setText(password);
            jTextField3.setText(bal);
            this.token = token;
            jButton2.setVisible(true);

        } catch (JSONException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    Signup() {
        jButton2.setVisible(false);
        try {
            serverJSON = fetchServers();
            setServerList(serverJSON);
            jButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleSignup();
                }
            });
            jButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleProceed2Login();
                }
            });
        } catch (IOException ex) {
            System.out.println("Error: Failed to retrieve Servers Data.");
        }
    }

    public static void main(String[] args) {
        new Signup();
    }

}

class SignupDesign extends javax.swing.JPanel {

    public SignupDesign() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

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

        jButton2.setBackground(new java.awt.Color(196, 43, 28));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Proceed to login.");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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
                .addGap(41, 41, 41)
                .addComponent(jButton1)
                .addGap(31, 31, 31)
                .addComponent(jButton2)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(108, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected final javax.swing.JButton jButton1 = new javax.swing.JButton();
    protected final javax.swing.JButton jButton2 = new javax.swing.JButton();
    public final javax.swing.JComboBox<String> jComboBox1 = new javax.swing.JComboBox<>();
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    protected final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    protected final javax.swing.JTextField jTextField2 = new javax.swing.JTextField();
    protected final javax.swing.JTextField jTextField3 = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables
}
