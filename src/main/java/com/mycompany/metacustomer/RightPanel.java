/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer;

import java.awt.BorderLayout;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author techninza
 */
public class RightPanel extends javax.swing.JPanel {

    /**
     * Creates new form RightPanel
     */
    public RightPanel() {
        initComponents();
     //  chart();
    }
    
//    private void chart()
//    {
//         JFXPanel jfxPanel = new JFXPanel();
//            frame.add(jfxPanel);
//
//            Platform.runLater(() -> {
//                WebView webView = new WebView();
//                WebEngine engine = webView.getEngine();
//                engine.load("https://www.example.com");
//
//                Scene scene = new Scene(webView);
//                jfxPanel.setScene(scene);
//            });
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
