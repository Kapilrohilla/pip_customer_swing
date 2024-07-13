/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
//package Level1Socket;
//
//import Level1Socket.Level2Socket.livedata;
package com.mycompany.metacustomer;

import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.AddSymbol2BidAsk;
import static com.mycompany.metacustomer.Metacustomer.HomeChartUrl;
import com.mycompany.metacustomer.Utility.Helper;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import io.socket.client.IO;
import static io.socket.client.IO.socket;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;

/**
 *
 * @author techninza
 */
public class LeftPanel extends javax.swing.JPanel {

    public static DefaultTableModel tableModel = new DefaultTableModel();

    public static ArrayList<JSONObject> watchlistData = new ArrayList<>();

    int row = -1;

    public LeftPanel() {
        try {
            tabledata();
            copiedSocke3();
        } catch (Exception ex) {
            System.out.println("exception occurred in construcotr");
            System.out.println(ex);
        }
    }

    void copiedSocke3() throws Exception {
        Socket socket = Metacustomer.socket;
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected to server.");
            }
        }).on("abc", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jso = (JSONObject) args[0];
                JSONObject response;
                try {
                    response = jso.getJSONObject("newMessage");
                    for (int i = 0; i < watchlistData.size(); i++) {
                        String watchlistsymbol = watchlistData.get(i).getString("symbol");
                        String responseSymbol = response.getString("symbol");
                        if (watchlistsymbol.equals(responseSymbol)) {
                            String bid = response.getString("bid");
                            String ask = response.getString("ask");
                            String volume = response.getString("volume");
                            String vol2display = String.format("%.2f", volume);
                            
                            String[] newRowData = {responseSymbol, bid, ask, vol2display};
                            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                                try {
                                    tableModel.setValueAt(newRowData[j], i, j);
                                } catch (ArrayIndexOutOfBoundsException ex) {
                                }

                            }
                        }
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(LeftPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        socket.connect();
    }

    public void framesetup() {

//        NavigationTree bottomPanel_navigator = new NavigationTree();
        TopPanelMarketData topPanel_marketData = new TopPanelMarketData();
//        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel_marketData, bottomPanel_navigator);
//        jSplitPane.setDividerLocation(0.5);

        add(topPanel_marketData);
    }

    String getData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        String url = APIs.GET_WATCHLIST_DATA;

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

    public void tabledata() throws JSONException {

        String[] columns = {"Symbol", "Bid", "Ask", "Change Percent"};
        String apiData = getData();
        System.out.println("getData: " + apiData);
        JSONObject jso = new JSONObject(apiData);

        for (String column : columns) {
            tableModel.addColumn(column);
        }

        JSONArray jsa = jso.getJSONArray("message");
        for (int i = 0; i < jsa.length(); i++) {

            JSONObject obj = jsa.getJSONObject(i);
            String symbol = "", bid = "", ask = "", volume = "";
            try {
                symbol = obj.getString("symbol");
                bid = obj.getDouble("bid") + "";
                ask = obj.getDouble("ask") + "";
                volume = obj.getDouble("changePercent") + "";
            } catch (JSONException ex) {

            }

            String[] rowData = {symbol, bid, ask, volume};
            watchlistData.add(obj);
            tableModel.addRow(rowData);
        }

        JPopupMenu popup = new JPopupMenu();
        JMenuItem newOrderOption = new JMenuItem("New Order");
        JMenuItem addSymbolOption = new JMenuItem("Add Symbol");
        JMenuItem chartOption = new JMenuItem("Chart");
        JMenuItem specificationOption = new JMenuItem("Specification");
        JMenuItem deleteoption = new JMenuItem("Delete Symbol");
        JMenuItem marketDepthOption = new JMenuItem("Show Market Depth");
        JMenuItem popupform = new JMenuItem("POP UP");

        newOrderOption.addActionListener((e) -> {
            new OrderFrame(Metacustomer.symbol).setVisible(true);
        });

        popupform.addActionListener((e) -> {
            new PopUpForm().setVisible(true);
        });

        addSymbolOption.addActionListener((e) -> {
            try {
                new AddSymbol2BidAsk().setVisible(true);
            } catch (JSONException ex) {
                System.out.println("JSON Exception occurred while Opening the Addsymbol2BidAsk");
            }

        });
        specificationOption.addActionListener((e) -> {
            try {

                String symbol = watchlistData.get(row).getString("symbol");
                SpecificationPanel.symbol = symbol;
                new SpecFrame().setVisible(true);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        deleteoption.addActionListener((e) -> {
            try {
                if (Metacustomer.symbol != null) {
                    tableModel.removeRow(row);
                    watchlistData.remove(row);
                    deleteSymbol(Metacustomer.symbol);
                }
            } catch (JSONException | IOException ex) {
                Logger.getLogger(LeftPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        chartOption.addActionListener((e) -> {
            if (Metacustomer.symbol != null) {
                try {
                    loadChart(Metacustomer.symbol, row);
                } catch (JSONException ex) {
                    System.out.println("exception occurred while loadding the chart  throught loadChart fucntion");
                }

            }
        });
        popup.add(newOrderOption);
        popup.add(addSymbolOption);
        popup.add(chartOption);
        popup.add(specificationOption);
        popup.add(deleteoption);
        popup.add(marketDepthOption);
        popup.add(popupform);

        JTable table = new JTable(tableModel) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                row = table.getSelectedRow();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int clickCount = e.getClickCount();
                    if (clickCount > 1) {
                        try {
                            Metacustomer.symbol = watchlistData.get(row).getString("symbol");

                            String symbolName = Metacustomer.symbol;
                            Helper.updateBrowserChartThroughJava("symbol", symbolName);

//                            Metacustomer.symbol = watchlistData.get(row).getString("symbol");
//
////                            String[] urlArray = Metacustomer.HomeChartUrl.split("symbol=");
////                            String[] resplit = urlArray[1].split("&");
////                            resplit[0] = selectedSymbol;
////
////                            urlArray[1] = String.join("&", resplit);
//                            String newUrl = Metacustomer.HomeChartUrl + "&symbol=" + Metacustomer.symbol;
//
//                            System.out.println("newUrl: " + newUrl);
//                            Metacustomer.rightPanel.browser_.loadURL(newUrl);
                        } catch (JSONException ex) {
                            System.out.println("exception occurred");
                        }
                    }

                } else {
                    popup.show(table, e.getX(), e.getY());
                    System.out.println("row: " + row);

                    try {
                        String symbol = watchlistData.get(row).getString("symbol");
                        System.out.println("symbol : " + symbol);
                        Metacustomer.symbol = symbol;

                    } catch (JSONException ex) {
                        Logger.getLogger(LeftPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        //table.setEnabled(false);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    final void deleteSymbol(String symbolName) throws JSONException, IOException {

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

    final void loadChart(String symbolName, int row) throws JSONException {
        Metacustomer.symbol = watchlistData.get(row).getString("symbol");

//        String[] urlArray = Metacustomer.HomeChartUrl.split("symbol=");
//        String[] resplit = urlArray[1].split("&");
//        resplit[0] = selectedSymbol;
//        urlArray[1] = String.join("&", resplit);
        String newUrl = Metacustomer.HomeChartUrl + "&symbol=" + Metacustomer.symbol;

        System.out.println("newUrl: " + newUrl);
        Metacustomer.rightPanel.browser_.loadURL(newUrl);
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
