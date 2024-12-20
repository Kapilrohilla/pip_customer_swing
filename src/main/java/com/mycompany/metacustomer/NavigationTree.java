/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metacustomer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author kapilrohilla
 */
public class NavigationTree extends javax.swing.JPanel {

    /**
     * Creates new form NavigationTree
     */
    public NavigationTree() {
        initComponents();
        tree();
    }
    
    private void tree(){
        DefaultMutableTreeNode root =new DefaultMutableTreeNode("Meta Trader 5");  
        DefaultMutableTreeNode accounts =new DefaultMutableTreeNode("Accounts");  
        DefaultMutableTreeNode quotesDemo =new DefaultMutableTreeNode("Meta Quotes-Demo");  
        DefaultMutableTreeNode desktopDemo =new DefaultMutableTreeNode("501995460: MetaTrader 5 Desktop Demo"); 
        accounts.add(quotesDemo);
        accounts.add(quotesDemo);
        accounts.add(desktopDemo);
        DefaultMutableTreeNode subscription =new DefaultMutableTreeNode("Subscriptions");  
        DefaultMutableTreeNode indicators =new DefaultMutableTreeNode("Indicators");  
        DefaultMutableTreeNode expertDivision =new DefaultMutableTreeNode("Expert Division");  
        DefaultMutableTreeNode script =new DefaultMutableTreeNode("Script");  
        DefaultMutableTreeNode service =new DefaultMutableTreeNode("Service");
        DefaultMutableTreeNode market =new DefaultMutableTreeNode("Market");
        DefaultMutableTreeNode signal =new DefaultMutableTreeNode("Signal");
        DefaultMutableTreeNode vps =new DefaultMutableTreeNode("VPS");
        
        root.add(accounts);
        root.add(subscription);
        root.add(indicators);
        root.add(expertDivision);
        root.add(script);
        root.add(service);
        root.add(market);
        root.add(signal);
        root.add(vps);
        
        JTree jt = new JTree(root);
        jt.setCellRenderer(new MyTreeRenderer());
        setLayout(new BorderLayout());
      
        add(jt,BorderLayout.CENTER);
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

//class MyTreeRenderer extends DefaultTreeCellRenderer {
//    private ImageIcon rootIcon, parentIcon, leafIcon;
//
//    public MyTreeRenderer() {
//        rootIcon = new ImageIcon("assets/shapes.png");
//        parentIcon = new ImageIcon("assets/dollar.png");
//        leafIcon = new ImageIcon("assets/signal.png");
//    }
//
//    @Override
//    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
//        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
//
//        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
//
//        if (node.isRoot()) {
//            setIcon(rootIcon);
//        } else if (node.isLeaf()) {
//            setIcon(leafIcon);
//        } else {
//            setIcon(parentIcon);
//        }
//
//        return this;
//    }
//}

class MyTreeRenderer extends DefaultTreeCellRenderer {
    private ImageIcon rootIcon, accountsIcon, subscriptionsIcon, indicatorsIcon, expertDivisionIcon, scriptIcon, serviceIcon, marketIcon, signalIcon, vpsIcon;

    public MyTreeRenderer() {
        rootIcon = new ImageIcon("assets/dollar.png");
        accountsIcon = new ImageIcon("assets/accouts.png");
        subscriptionsIcon = new ImageIcon("assets/subscriptions.png");
        indicatorsIcon = new ImageIcon("assets/indicators.png");
        expertDivisionIcon = new ImageIcon("assets/expert_advisors.png");
        scriptIcon = new ImageIcon("assets/scripts.png");
        serviceIcon = new ImageIcon("assets/services.png");
        marketIcon = new ImageIcon("assets/bag.png");
        signalIcon = new ImageIcon("assets/signal.png");
        vpsIcon = new ImageIcon("assets/vps.png");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

        if (node.isRoot()) {
            setIcon(rootIcon);
        } else if (node.getUserObject().equals("Accounts")) {
            setIcon(accountsIcon);
        } else if (node.getUserObject().equals("Subscriptions")) {
            setIcon(subscriptionsIcon);
        } else if (node.getUserObject().equals("Indicators")) {
            setIcon(indicatorsIcon);
        } else if (node.getUserObject().equals("Expert Division")) {
            setIcon(expertDivisionIcon);
        } else if (node.getUserObject().equals("Script")) {
            setIcon(scriptIcon);
        } else if (node.getUserObject().equals("Service")) {
            setIcon(serviceIcon);
        } else if (node.getUserObject().equals("Market")) {
            setIcon(marketIcon);
        } else if (node.getUserObject().equals("Signal")) {
            setIcon(signalIcon);
        } else if (node.getUserObject().equals("VPS")) {
            setIcon(vpsIcon);
        }

        return this;
    }
}
