/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.metacustomer;

import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.Auth.AuthContainer;
import com.mycompany.metacustomer.CandlestickDemo2;
import com.mycompany.metacustomer.History.Trade;

import io.socket.client.IO;
import io.socket.client.Socket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
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
 * @author techninza
 */
public class Metacustomer extends JFrame {

    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    public static Label balance, equity, margin, freemargin, level;
    private javax.swing.JMenuBar jMenuBar1;
    public static MainFrame rightPanel;
    private JSplitPane jSplitPane;
    public static String userId;
    static public String loginToken;
    JSplitPane jSplitPane1;

//    static public String loginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NTliYTdhMTRiNGQyZmEzMDU4ZDM5ZWMiLCJpYXQiOjE3MDU2NTgyMTcsImV4cCI6MTcwNjI2MzAxN30.qL2Z2gEWcaKEjmpxJl-yR__bs9hEC5_3EdOzw33C8po";
    static public String HomeChartUrl = APIs.CHART + "?token=";
    static public String symbol = "XAUUSD";
    static public String time = "1";
    static public String type = "candle";
    public static String bal;

    private void updateChartBaseTime(String timeInMinute) {
//        String[] splitedUrl = Metacustomer.HomeChartUrl.split("time=");
//        splitedUrl[1] = timeInMinute;
        String newUrl = HomeChartUrl + "&symbol=" + symbol + "&time=" + timeInMinute;
//        HomeChartUrl = newUrl;
//        System.out.println("Url: " + HomeChartUrl);
//        HomeChartPanel.browser.navigation().loadUrl(newUrl);
        MainFrame.browser_.loadURL(newUrl);
    }
    public static Socket socket;

    public static void main(String[] args) {

        HandlePreference savedLoginCredentials = new HandlePreference();
//        savedLoginCredentials.logout();
        String token = savedLoginCredentials.retrieveToken();
        System.out.println("token: " + token);
        if ("not found".equals(token)) {
            new AuthContainer().setVisible(true);
        } else {
            Metacustomer.loginToken = token;
            HomeChartUrl += token;
            Socket socket = null;
            try {
                socket = IO.socket(APIs.BASE_URL);
            } catch (URISyntaxException ex) {
                System.out.println("ex");
            }
            new Metacustomer(socket).setVisible(true);
        }

    }

    private void toolbar() {
//        System.out.println("gettoolbar"+getData());

        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.HORIZONTAL);

        balance = new Label("Balance");
        margin = new Label("Margin");
        freemargin = new Label("Free Margin");
        equity = new Label("Equity");
        level = new Label("Margin Level: ");

        ImageIcon cursor = new ImageIcon("assets/cursor.png");
        JButton cursorBtn = new JButton(cursor);
        ImageIcon plus = new ImageIcon("assets/plus.png");
        JButton plusBtn = new JButton(plus);

        ImageIcon slantLine = new ImageIcon("assets/slantLine.png");
        JButton slantLineBtn = new JButton(slantLine);

        ImageIcon twoLine = new ImageIcon("assets/two-line.png");
        JButton twoLineBtn = new JButton(twoLine);

        ImageIcon hamburger = new ImageIcon("assets/hamburger-menu.png");
        JButton hamburgerBtn = new JButton(hamburger);

        ImageIcon talterNate = new ImageIcon("assets/t-alternate.png");
        JButton talternateBtn = new JButton(talterNate);

        ImageIcon shapes = new ImageIcon("assets/shapes.png");
        JButton shapesBtn = new JButton(shapes);

        JButton m1 = new JButton("M1");
        m1.setForeground(Color.BLUE);
        JButton m5 = new JButton("M5");
        m5.setForeground(Color.BLUE);
        JButton m15 = new JButton("M15");
        m15.setForeground(Color.BLUE);
        JButton h1 = new JButton("H1");
        h1.setForeground(Color.BLUE);
        JButton h4 = new JButton("H4");
        h4.setForeground(Color.BLUE);
        JButton d1 = new JButton("D1");
        d1.setForeground(Color.BLUE);
        JButton w1 = new JButton("W1");
        w1.setForeground(Color.BLUE);
        JButton mn = new JButton("MN");
        mn.setForeground(Color.BLUE);

        m1.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("1");
        });
        m5.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("5");
        });
        m15.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("15");
        });
        h1.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("60");
        });
        h4.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("240");
        });
        d1.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("1440");
        });
        w1.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("10080");
        });
        mn.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("43200");
        });
        ImageIcon lineChartFilled = new ImageIcon("assets/line-chart-filled.png");
        JButton lineChartFilledBtn = new JButton(lineChartFilled);

        ImageIcon dollor = new ImageIcon("assets/dollar.png");
        JButton dollorBtn = new JButton(dollor);

        ImageIcon lineChart = new ImageIcon("assets/line-chart.png");
        JButton lineChartBtn = new JButton(lineChart);

        JButton algoTradingBtn = new JButton("Area");
        algoTradingBtn.addActionListener((e) -> {
            System.out.println("It works");
            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlTypeBreak = initialUrl.split("type=");
            String[] initialUrlTypeBreakRightBreak = initialUrlTypeBreak[1].split("&");
            initialUrlTypeBreakRightBreak[0] = "area";
            initialUrlTypeBreak[1] = String.join("&", initialUrlTypeBreakRightBreak);
            initialUrl = String.join("type=", initialUrlTypeBreak);
            System.out.println("URL: " + initialUrl);
            Metacustomer.HomeChartUrl = initialUrl;

            rightPanel.browser_.loadURL(HomeChartUrl);
        });
        JButton candle = new JButton("Candle");
        candle.addActionListener((e) -> {
            System.out.println("It works");
            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlTypeBreak = initialUrl.split("type=");
            String[] initialUrlTypeBreakRightBreak = initialUrlTypeBreak[1].split("&");
            initialUrlTypeBreakRightBreak[0] = "candleSticks";
            initialUrlTypeBreak[1] = String.join("&", initialUrlTypeBreakRightBreak);
            initialUrl = String.join("type=", initialUrlTypeBreak);
            System.out.println("URL: " + initialUrl);
            Metacustomer.HomeChartUrl = initialUrl;

            rightPanel.browser_.loadURL(HomeChartUrl);
        });
        JButton newOrderBtn = new JButton("New Order");

        ImageIcon zoom = new ImageIcon("assets/zoom.png");
        JButton zoomBtn = new JButton(zoom);

        ImageIcon zoomOut = new ImageIcon("assets/zoom-out.png");
        JButton zoomOutBtn = new JButton(zoomOut);

        ImageIcon box = new ImageIcon("assets/box.png");
        JButton boxBtn = new JButton(box);

        ImageIcon search = new ImageIcon("assets/search.png");
        JButton searchBtn = new JButton(search);

        JButton addsymbol = new JButton("Add Symbol");
        JButton deletesymbol = new JButton("Delete Symbol");

        JButton ideBtn = new JButton("BAR");

        ideBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
//                System.out.println("It works");
//            String initialUrl = Metacustomer.HomeChartUrl;
//            String[] initialUrlTypeBreak = initialUrl.split("type=");
//            String[] initialUrlTypeBreakRightBreak = initialUrlTypeBreak[1].split("&");
//            initialUrlTypeBreakRightBreak[0] = "bar";
//            initialUrlTypeBreak[1] = String.join("&", initialUrlTypeBreakRightBreak);
//            initialUrl = String.join("type=", initialUrlTypeBreak);
//            System.out.println("URL: " + initialUrl);
//            Metacustomer.HomeChartUrl = initialUrl;

                type = "bar";
                String newURL = HomeChartUrl + "&symbol=" + Metacustomer.symbol + "&type=" + type;
                rightPanel.browser_.loadURL(newURL);
            }
        });
//        ideBtn.setForeground(Color.BLUE);

//        toolBar.add();
//        toolBar.add(hamburgerBtn);
//        toolBar.add(talternateBtn);
//        toolBar.add(shapesBtn);
//        toolBar.addSeparator();
//        JButton refresh = new JButton("Refresh");
//        toolBar.add(refresh);
//        toolBar.add(m1);
//        toolBar.add(m5);
//        toolBar.add(m15);
//        toolBar.add(h1);
//        toolBar.add(h4);
//        toolBar.add(d1);
//        toolBar.add(w1);
//        toolBar.add(mn);
//        toolBar.addSeparator();
//        
//        toolBar.add(addsymbol);
//        toolBar.add(deletesymbol);
//        toolBar.add(dollorBtn);
//        toolBar.add(ideBtn);
//        toolBar.add(algoTradingBtn);
//        toolBar.add(candle);
        toolBar.add(newOrderBtn);
//        toolBar.add(zoomBtn);
//        toolBar.add(zoomOutBtn);
//        toolBar.add(boxBtn);
//        toolBar.add(searchBtn);

        toolBar.add(balance);
        toolBar.add(equity);
        toolBar.addSeparator();
        toolBar.add(margin);
        toolBar.add(freemargin);
        toolBar.add(level);

//        refresh.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                jSplitPane1.setBottomComponent(new BottomPanel());
//            }
//        });
//        
        addsymbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddSymbol2BidAsk frame;
                try {
                    frame = new AddSymbol2BidAsk();
                    frame.setVisible(true);
                } catch (JSONException ex) {
//                    Logger.getLogger(Metacustomer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        newOrderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderFrame frame = new OrderFrame();
                frame.setVisible(true);
            }
        });

        String userdata = getData();
        try {
            JSONObject js = new JSONObject(userdata);
            JSONObject user = js.getJSONObject("user");
            bal = user.getString("balance");
            userId = user.getString("_id");
            JSONObject jso = new JSONObject();
            jso.put("userId", Metacustomer.userId);
            System.out.println("userpositions payload: " + jso);
            Metacustomer.socket.emit("userpositions", jso);
            System.out.println("User is live now..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final String getData() {
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

    private void menubarsetup() {
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu3.setText("View");
        jMenuBar1.add(jMenu3);

        jMenu6.setText("Insert");
        // jMenuBar1.add(jMenu6);

        jMenu7.setText("Charts");
        jMenuBar1.add(jMenu7);

        jMenu2.setText("Tools");
        jMenuBar1.add(jMenu2);

        jMenu4.setText("Window");
        //jMenuBar1.add(jMenu4);

        jMenu5.setText("Help");
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

    }

    private void framesetup() {
        LeftPanel leftPanel = new LeftPanel();
        try {
            String URLs = Metacustomer.HomeChartUrl + "&symbol=" + Metacustomer.symbol;
            System.out.println(URLs);
            rightPanel = new MainFrame(URLs, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        jSplitPane.setDividerLocation(300);
        BottomPanel bottomPanel = new BottomPanel();

        jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jSplitPane, bottomPanel);
        jSplitPane1.setDividerLocation(600);

        add(jSplitPane1);

        pack();
    }

    private void menusetup() {
        JMenu a1 = new JMenu("New Chart");

        JMenuItem a2 = new JMenuItem("Profiles");
        JMenuItem a3 = new JMenuItem("Close");
        JMenuItem a4 = new JMenuItem("Save");
        JMenuItem a5 = new JMenuItem("Save as Picture");
        JMenuItem a15 = new JMenuItem("Add symbol");
        JMenuItem a6 = new JMenuItem("Open Data Folder");
        JMenuItem a7 = new JMenuItem("Print");
        JMenuItem a8 = new JMenuItem("Print Preview");
        JMenuItem a9 = new JMenuItem("Print Setup");
        JMenuItem a10 = new JMenuItem("Open an Account");
        JMenuItem a11 = new JMenuItem("Login to Trade Account");
        JMenuItem a12 = new JMenuItem("Login to Web Trader");
        JMenuItem a14 = new JMenuItem("Login to MQL5. Community");
        JMenuItem a13 = new JMenuItem("Exit");

        JMenuItem a1a = new JMenuItem("EURUSD");
        JMenuItem a1b = new JMenuItem("GBPUSD");
        JMenuItem a1c = new JMenuItem("USDCHF");
        JMenuItem a1d = new JMenuItem("USDJPY");
        JMenuItem a1e = new JMenuItem("USDCNH");
        JMenuItem a1f = new JMenuItem("USDRUB");
        JMenuItem a1g = new JMenuItem("Forex");
        JMenuItem a1h = new JMenuItem("XAUUSD");

        a13.addActionListener((ActionEvent e) -> {
            HandlePreference pref = new HandlePreference();
            try {
                boolean isLogoutSuccess = pref.logout();
                if (isLogoutSuccess) {
                    dispose();
                    LeftPanel.watchlistData.clear();
                    LeftPanel.tableModel = new DefaultTableModel();
                    Trade.model = new DefaultTableModel();

                    new AuthContainer().setVisible(true);
                } else {
                    System.out.println("Failed to logout");
                }
            } catch (Exception ex) {
                System.out.println("Something went wrong while disconnecting / logout");
            }
        });

        a15.addActionListener((ActionEvent e) -> {
            try {
                new AddSymbol2BidAsk().setVisible(true);
            } catch (JSONException ex) {
                Logger.getLogger(Metacustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        a1a.addActionListener((ActionEvent e) -> {
            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlSymBreak = initialUrl.split("symbol=");
            String[] resplit = initialUrlSymBreak[1].split("&");
            resplit[0] = "EURUSD";
            initialUrlSymBreak[1] = String.join("&", resplit);
            initialUrl = String.join("symbol=", initialUrlSymBreak);
            HomeChartUrl = initialUrl;
            System.out.println("url: " + HomeChartUrl);
            rightPanel.browser_.loadURL(HomeChartUrl);
        });

        a1b.addActionListener((ActionEvent e) -> {

            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlSymBreak = initialUrl.split("symbol=");
            String[] resplit = initialUrlSymBreak[1].split("&");
            resplit[0] = "GBPUSD";
            initialUrlSymBreak[1] = String.join("&", resplit);
            initialUrl = String.join("symbol=", initialUrlSymBreak);
            HomeChartUrl = initialUrl;
            System.out.println("url: " + HomeChartUrl);
            rightPanel.browser_.loadURL(HomeChartUrl);
        });
        a1c.addActionListener((ActionEvent e) -> {

            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlSymBreak = initialUrl.split("symbol=");
            String[] resplit = initialUrlSymBreak[1].split("&");
            resplit[0] = "USDCHF";
            initialUrlSymBreak[1] = String.join("&", resplit);
            initialUrl = String.join("symbol=", initialUrlSymBreak);
            HomeChartUrl = initialUrl;
            System.out.println("url: " + HomeChartUrl);
            rightPanel.browser_.loadURL(HomeChartUrl);
        });
        a1d.addActionListener((ActionEvent e) -> {

            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlSymBreak = initialUrl.split("symbol=");
            String[] resplit = initialUrlSymBreak[1].split("&");
            resplit[0] = "USDJPY";
            initialUrlSymBreak[1] = String.join("&", resplit);
            initialUrl = String.join("symbol=", initialUrlSymBreak);
            HomeChartUrl = initialUrl;
            System.out.println("url: " + HomeChartUrl);
            rightPanel.browser_.loadURL(HomeChartUrl);
        });
        a1e.addActionListener((ActionEvent e) -> {

            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlSymBreak = initialUrl.split("symbol=");
            String[] resplit = initialUrlSymBreak[1].split("&");
            resplit[0] = "USDCNH";
            initialUrlSymBreak[1] = String.join("&", resplit);
            initialUrl = String.join("symbol=", initialUrlSymBreak);
            HomeChartUrl = initialUrl;
            System.out.println("url: " + HomeChartUrl);
            rightPanel.browser_.loadURL(HomeChartUrl);
        });
        a1f.addActionListener((ActionEvent e) -> {

            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlSymBreak = initialUrl.split("symbol=");
            String[] resplit = initialUrlSymBreak[1].split("&");
            resplit[0] = "USDRUB";
            initialUrlSymBreak[1] = String.join("&", resplit);
            initialUrl = String.join("symbol=", initialUrlSymBreak);
            HomeChartUrl = initialUrl;
            System.out.println("url: " + HomeChartUrl);
            rightPanel.browser_.loadURL(HomeChartUrl);
        });
        a1h.addActionListener((ActionEvent e) -> {
            System.out.println("It works");
            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlSymBreak = initialUrl.split("symbol=");
            String[] resplit = initialUrlSymBreak[1].split("&");
            resplit[0] = "XAUUSD";
            initialUrlSymBreak[1] = String.join("&", resplit);
            initialUrl = String.join("symbol=", initialUrlSymBreak);
            HomeChartUrl = initialUrl;
            System.out.println("url: " + HomeChartUrl);
            rightPanel.browser_.loadURL(HomeChartUrl);
        });
        a1.add(a1a);
        a1.add(a1h);
        a1.add(a1b);
        a1.add(a1c);
        a1.add(a1d);
        a1.add(a1e);
        a1.add(a1f);
        a1.addSeparator();
        a1.add(a1g);

        jMenu1.add(a1);
        jMenu1.add(a2);
        jMenu1.add(a3);
        jMenu1.add(a4);

        jMenu1.add(a5);
        jMenu1.add(a15);
        jMenu1.addSeparator();
        jMenu1.add(a6);
        jMenu1.addSeparator();
        jMenu1.add(a7);
        jMenu1.add(a8);
        jMenu1.add(a9);
        jMenu1.addSeparator();
        jMenu1.add(a10);
        jMenu1.add(a11);
        jMenu1.add(a12);
        jMenu1.add(a14);
        jMenu1.addSeparator();
        jMenu1.add(a13);

        a2.addActionListener((ActionEvent e) -> {
            System.out.println("It works");
            new AuthContainer().setVisible(true);
        });

        JMenuItem b1 = new JMenuItem("New Order");
        JMenuItem b2 = new JMenuItem("Strategy Tester Agents Manager");
        JMenuItem b3 = new JMenuItem("MetaQuotes Language Editor");
        JMenuItem b4 = new JMenuItem("Task Manager");
        JMenuItem b5 = new JMenuItem("Global Variables");
        JMenuItem b6 = new JMenuItem("MQL5 Market");
        JMenuItem b7 = new JMenuItem("MQL5 Signals");
        JMenuItem b8 = new JMenuItem("MQL5 Virtual Hosting");
        JMenuItem b9 = new JMenuItem("Options");

        b1.addActionListener((ActionEvent e) -> {
            new OrderFrame().setVisible(true);
        });

        jMenu2.add(b1);
        jMenu2.addSeparator();
        jMenu2.add(b2);
        jMenu2.add(b3);
        jMenu2.add(b4);
        jMenu2.add(b5);
        jMenu2.addSeparator();
        jMenu2.add(b6);
        jMenu2.add(b7);
        jMenu2.add(b8);
        jMenu2.add(b9);

        JMenu c1 = new JMenu("Languages");
        JMenuItem c2 = new JMenuItem("Toolbar");
        JMenuItem c3 = new JMenuItem("Status bar");
        JMenuItem c4 = new JMenuItem("Symbols");
        JMenuItem c5 = new JMenuItem("Depth of Market");
        JMenuItem c6 = new JMenuItem("Market Watch");
        JMenuItem c7 = new JMenuItem("Data Window");
        JMenuItem c8 = new JMenuItem("Navigator");
        JMenuItem c9 = new JMenuItem("Toolbox");
        JMenuItem c10 = new JMenuItem("Strategy Tester");
        JMenuItem c11 = new JMenuItem("Reports");
        JMenuItem c12 = new JMenuItem("Full Screen");

        JMenuItem l1 = new JMenuItem("Arabi");
        JMenuItem l2 = new JMenuItem("English");
        JMenuItem l3 = new JMenuItem("Hindi");
        JMenuItem l4 = new JMenuItem("Japanese");
        JMenuItem l5 = new JMenuItem("Bengali");
        JMenuItem l6 = new JMenuItem("Urdu");

        c1.add(l1);
        c1.add(l2);
        c1.add(l3);
        c1.add(l4);
        c1.add(l5);
        c1.add(l6);

        jMenu3.add(c1);
        jMenu3.addSeparator();
        jMenu3.add(c2);
        jMenu3.add(c3);
        jMenu3.add(c4);
        jMenu3.addSeparator();
        jMenu3.add(c5);
        jMenu3.add(c6);
        jMenu3.addSeparator();
        jMenu3.add(c7);
        jMenu3.add(c8);
        jMenu3.add(c9);
        jMenu3.add(c10);
        jMenu3.addSeparator();
        jMenu3.add(c11);
        jMenu3.add(c12);

        JMenuItem d1 = new JMenuItem("Tile Windows");
        JMenuItem d2 = new JMenuItem("Cascade");
        JMenuItem d3 = new JMenuItem("Tile Horizontally");
        JMenuItem d4 = new JMenuItem("Tile Vertically");
        JMenuItem d5 = new JMenuItem("Arrange Icons");
        JMenuItem d6 = new JMenuItem("Resolutions");

        jMenu4.add(d1);
        jMenu4.addSeparator();
        jMenu4.add(d2);
        jMenu4.add(d3);
        jMenu4.add(d4);
        jMenu4.add(d5);
        jMenu4.addSeparator();
        jMenu4.add(d6);

        JMenu g1 = new JMenu("Indicators");
        JMenu g2 = new JMenu("Objects");
        JMenu g3 = new JMenu("Experts");
        JMenu g4 = new JMenu("Scripts");

        JMenuItem g1a = new JMenuItem("Williams Percent Range");
        JMenuItem g1b = new JMenuItem("Volumes");
        JMenuItem g1c = new JMenuItem("Variable Index Dynamic Average");
        JMenuItem g1d = new JMenuItem("TripleExponential Moving Average");
        JMenuItem g1e = new JMenuItem("Triple Exponential Average");
        JMenuItem g1f = new JMenuItem("Trend");
        JMenuItem g1g = new JMenuItem("Oscillators");
        JMenuItem g1h = new JMenuItem("Volumes");
        JMenuItem g1i = new JMenuItem("Bill Williams");
        JMenuItem g1j = new JMenuItem("Custom");

        g1.add(g1a);
        g1.add(g1b);
        g1.add(g1c);
        g1.add(g1d);
        g1.add(g1e);
        g1.addSeparator();
        g1.add(g1f);
        g1.add(g1g);
        g1.add(g1h);
        g1.add(g1i);
        g1.add(g1j);
        // insert -> objects
        JMenuItem g2a = new JMenuItem("Vertical Line");
        JMenuItem g2b = new JMenuItem("Horizontal line");
        JMenuItem g2c = new JMenuItem("Trendline");
        JMenuItem g2d = new JMenuItem("Trend By Angle");
        JMenuItem g2e = new JMenuItem("Cyclic Lines");
        JMenuItem g2f = new JMenuItem("Lines");
        JMenuItem g2g = new JMenuItem("Channels");
        JMenuItem g2h = new JMenuItem("Gann");
        JMenuItem g2i = new JMenuItem("Fibonacci");
        JMenuItem g2j = new JMenuItem("Elliott");
        JMenuItem g2k = new JMenuItem("Shapes");
        JMenuItem g2l = new JMenuItem("Arrows");
        JMenuItem g2m = new JMenuItem("Graphical");

        g2.add(g2a);
        g2.add(g2b);
        g2.add(g2c);
        g2.add(g2d);
        g2.add(g2e);
        g2.addSeparator();
        g2.add(g2f);
        g2.add(g2g);
        g2.add(g2h);
        g2.add(g2i);
        g2.add(g2j);
        g2.add(g2k);
        g2.add(g2l);
        g2.add(g2m);

        // insert -> experts
        JMenuItem g3a = new JMenuItem("ExpertMACD");
        JMenuItem g3b = new JMenuItem("ExpertMAMA");
        JMenuItem g3c = new JMenuItem("ExpertMAPSAR");
        JMenuItem g3d = new JMenuItem("ExpertMAPSARSizeOptimized");
        JMenuItem g3e = new JMenuItem("ChartInChart");

        g3.add(g3a);
        g3.add(g3b);
        g3.add(g3c);
        g3.add(g3d);
        g3.add(g3e);

        // insert -> scripts
        JMenuItem g4a = new JMenuItem("AccountInfoSample");
        JMenuItem g4b = new JMenuItem("AccountDoubleSample");
        JMenuItem g4c = new JMenuItem("CanvasSample");
        JMenuItem g4d = new JMenuItem("HistogramChartSample");
        JMenuItem g4e = new JMenuItem("LineChartSample");

        g4.add(g4a);
        g4.add(g4b);
        g4.add(g4c);
        g4.add(g4d);
        g4.add(g4e);

        jMenu6.add(g1);
        jMenu6.add(g2);
        jMenu6.add(g3);
        jMenu6.add(g4);

        JMenuItem f1 = new JMenuItem("Depth of Market");
        JMenuItem f2 = new JMenuItem("Indicator List");
        JMenuItem f3 = new JMenuItem("Objects");
        JMenuItem f4 = new JMenuItem("Expert List");
        JMenuItem f5 = new JMenuItem("Bar Chart");
        JMenuItem f6 = new JMenuItem("Candlesticks");
        JMenuItem f7 = new JMenuItem("Line Chart");
        JMenu f8 = new JMenu("Timeframes");
        JMenuItem f9 = new JMenuItem("Templates");
        JMenuItem f10 = new JMenuItem("Grid");
        JMenuItem f11 = new JMenuItem("Auto Scroll");
        JMenuItem f12 = new JMenuItem("Chart Shift");
        JMenuItem f13 = new JMenuItem("Volumes");
        JMenuItem f14 = new JMenuItem("Tick Volumes");
        JMenuItem f15 = new JMenuItem("Trade Levels");
        JMenuItem f16 = new JMenuItem("Trade History");
        JMenuItem f17 = new JMenuItem("Zoom in");
        JMenuItem f18 = new JMenuItem("Step by Step");
        JMenuItem f19 = new JMenuItem("Properties");

        // chart -> timeframes
        JMenuItem f8a = new JMenuItem("1 Minute");
        JMenuItem f8b = new JMenuItem("5 Minute");
        JMenuItem f8c = new JMenuItem("15 Minute");
        JMenuItem f8d = new JMenuItem("30 Minute");
        JMenuItem f8e = new JMenuItem("Minutes");
        JMenuItem f8f = new JMenuItem("1 Hour");
        JMenuItem f8g = new JMenuItem("4 Hour");
        JMenuItem f8h = new JMenuItem("Hours");
        JMenuItem f8i = new JMenuItem("Daily");
        JMenuItem f8j = new JMenuItem("Weekly");
        JMenuItem f8k = new JMenuItem("Monthly");

        f8a.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("1");
        });
        f8b.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("5");
        });
        f8c.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("15");
        });
        f8d.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("30");
        });
//        f8e.addActionListener( (ActionEvent e) -> {
//            updateChartBaseTime("1");
//        });
        f8f.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("60");
        });
        f8g.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("240");
        });
//        f8h.addActionListener( (ActionEvent e) -> {
//            updateChartBaseTime("1");
//        });
        f8i.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("1440");
        });
        f8j.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("10080");
        });
        f8k.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("43200");
        });

        f8.add(f8a);
        f8.add(f8b);
        f8.add(f8c);
        f8.add(f8d);
        f8.add(f8e);
        f8.addSeparator();
        f8.add(f8f);
        f8.add(f8g);
        f8.add(f8h);
        f8.addSeparator();
        f8.add(f8i);
        f8.add(f8j);
        f8.add(f8k);
        f5.addActionListener((ActionEvent e) -> {
            System.out.println("It works");
            String initialUrl = Metacustomer.HomeChartUrl;
            String[] initialUrlTypeBreak = initialUrl.split("type=");
            String[] initialUrlTypeBreakRightBreak = initialUrlTypeBreak[1].split("&");
            initialUrlTypeBreakRightBreak[0] = "bar";
            initialUrlTypeBreak[1] = String.join("&", initialUrlTypeBreakRightBreak);
            initialUrl = String.join("type=", initialUrlTypeBreak);
            System.out.println("URL: " + initialUrl);
            Metacustomer.HomeChartUrl = initialUrl;

            rightPanel.browser_.loadURL(HomeChartUrl);
        });
        f6.addActionListener((ActionEvent e) -> {
//            System.out.println("It works");
//            String initialUrl = Metacustomer.HomeChartUrl;
//            String[] initialUrlTypeBreak = initialUrl.split("type=");
//            String[] initialUrlTypeBreakRightBreak = initialUrlTypeBreak[1].split("&");
//            initialUrlTypeBreakRightBreak[0] = "candleSticks";
//            initialUrlTypeBreak[1] = String.join("&", initialUrlTypeBreakRightBreak);
//            initialUrl = String.join("type=", initialUrlTypeBreak);
//            System.out.println("URL: " + initialUrl);
//            Metacustomer.HomeChartUrl = initialUrl;
//            
//            rightPanel.browser_.loadURL(HomeChartUrl);

            type = "candle";
            String newURL = HomeChartUrl + "&symbol=" + Metacustomer.symbol + "&type=" + type;
            rightPanel.browser_.loadURL(newURL);
        });
        f7.addActionListener((ActionEvent e) -> {
//            System.out.println("It works");
//            String initialUrl = Metacustomer.HomeChartUrl;
//            String[] initialUrlTypeBreak = initialUrl.split("type=");
//            String[] initialUrlTypeBreakRightBreak = initialUrlTypeBreak[1].split("&");
//            initialUrlTypeBreakRightBreak[0] = "area";
//            initialUrlTypeBreak[1] = String.join("&", initialUrlTypeBreakRightBreak);
//            initialUrl = String.join("type=", initialUrlTypeBreak);
//            System.out.println("URL: " + initialUrl);
//            Metacustomer.HomeChartUrl = initialUrl;

            type = "area";
            String newURL = HomeChartUrl + "&symbol=" + Metacustomer.symbol + "&type=" + type;
            rightPanel.browser_.loadURL(newURL);

//            rightPanel.browser_.loadURL(HomeChartUrl);            
        });
        jMenu7.add(f1);
        jMenu7.add(f2);
        jMenu7.add(f3);
        jMenu7.add(f4);
        jMenu7.addSeparator();
        jMenu7.add(f5);
        jMenu7.add(f6);
        jMenu7.add(f7);
        jMenu7.addSeparator();
        jMenu7.add(f8);
        jMenu7.add(f9);
        jMenu7.add(f10);
        jMenu7.addSeparator();
        jMenu7.add(f11);
        jMenu7.add(f12);
        jMenu7.addSeparator();
        jMenu7.add(f13);
        jMenu7.add(f14);
        jMenu7.add(f15);
        jMenu7.addSeparator();
        jMenu7.add(f16);
        jMenu7.add(f17);
        jMenu7.add(f18);
        jMenu7.addSeparator();
        jMenu7.add(f19);

        JMenuItem e1 = new JMenuItem("Help Topics");
        JMenuItem e2 = new JMenuItem("What's New");
        jMenu5.add(e1);
        jMenu5.add(e2);
    }

    public Metacustomer(Socket socket) {
        Metacustomer.socket = socket;
//        JSONObject userpositions = new JSONObject();
//        try {
//            userpositions.put("userId", Metacustomer.userId);
//            socket.emit("userpositions", userpositions);
//        } catch (JSONException ex) {
//            System.out.println(ex.getMessage());
//        }

        setTitle("Customer App");
        menubarsetup();
        menusetup();
        toolbar();
        framesetup();

        setVisible(true);
    }

}
