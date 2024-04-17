/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer;

import javax.swing.JTabbedPane;

/**
 *
 * @author kapilrohilla
 */
public class TopPanelMarketData extends javax.swing.JPanel {

    /**
     * Creates new form TopPanelMarketData
     */
    public TopPanelMarketData() {
//        initComponents();
        tabbedPane();
    }

    private void tabbedPane() {
        JTabbedPane jt = new JTabbedPane();
        String[] tabs = {"Symbol", "Details", "Trading", "Tricks"};

        jt.add(tabs[0], new SymbolPanel());
        jt.add(tabs[1], new DetailsPanel());
        jt.add(tabs[2], new TradingPanel());
        jt.add(tabs[3], new TricksPanel());
        add(jt);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

class SymbolPanel extends javax.swing.JPanel {

    SymbolPanel() {

    }
}

class DetailsPanel extends javax.swing.JPanel {

    DetailsPanel() {

    }
}

class TradingPanel extends javax.swing.JPanel {

    TradingPanel() {

    }
}

class TricksPanel extends javax.swing.JPanel {

    TricksPanel() {

    }
}
