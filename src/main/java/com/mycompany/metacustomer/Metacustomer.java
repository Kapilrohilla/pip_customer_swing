/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.metacustomer;

import com.mycompany.metacustomer.Utility.APIs;
import com.mycompany.metacustomer.Auth.AuthContainer;
import com.mycompany.metacustomer.History.Trade;
import com.mycompany.metacustomer.Utility.ApiServices;
import com.mycompany.metacustomer.Utility.Helper;

import io.socket.client.IO;
import io.socket.client.Socket;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kapil Rohilla
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
    public static String accountId;
    public static String name;
    static public String HomeChartUrl = APIs.CHART + "?token=";
    static public String symbol = "XAUUSD";
    static public String time = "1";
    static public String type = "candle";
    public static String bal;
    public static String groupCategory;
    public static double credit;
//    public static MetaThis = Metacustomer.this;

    private void updateChartBaseTime(String timeInMinute) {
        Helper.updateBrowserChartThroughJava("time", timeInMinute);
    }
    public static Socket socket;

    public static void main(String[] args) {

        HandlePreference savedLoginCredentials = new HandlePreference();
        // savedLoginCredentials.logout();
        String token = savedLoginCredentials.retrieveToken();
        System.out.println("token: " + token);
        if ("not found".equals(token)) {
            new AuthContainer().setVisible(true);
        } else {
            Metacustomer.loginToken = token;
            HomeChartUrl += token;
//            System.out.println("HOMEChart: " + HomeChartUrl);
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
                type = "bar";
                String newURL = HomeChartUrl + "&symbol=" + Metacustomer.symbol + "&type=" + type;
                rightPanel.browser_.loadURL(newURL);
            }
        });
        toolBar.add(newOrderBtn);

        toolBar.add(balance);
        toolBar.add(equity);
        toolBar.addSeparator();
        toolBar.add(margin);
        toolBar.add(freemargin);
        toolBar.add(level);

        addsymbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddSymbol2BidAsk frame;
                try {
                    frame = new AddSymbol2BidAsk();
                    frame.setVisible(true);
                } catch (JSONException ex) {
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

        ApiServices services = new ApiServices();
        try {
            Response res = services.getDataWithToken(APIs.USER, Metacustomer.loginToken);
            String userdata = res.body().string();
            try {

                JSONObject js = new JSONObject(userdata);
                try {
                    boolean status = js.getBoolean("status");
                    if (status == false) {
                        JOptionPane.showMessageDialog(this, "Token is expred.");
                        AuthContainer auth = new AuthContainer();
                        auth.setVisible(true);
                    }
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println("js: " + js);
                JSONObject user = js.getJSONObject("user");
                bal = Helper.getJSONString(user, "balance");
                userId = Helper.getJSONString(user, "_id");
                name = Helper.getJSONString(user, "name");
                accountId = user.getString("email");
                credit = Helper.getJSONDouble(user, "credit");
                JSONObject group = js.getJSONObject("group");
                try {
                    groupCategory = group.getString("HCategory");
                } catch (JSONException ex) {
                }

                JSONObject jso = new JSONObject();
                jso.put("userId", Metacustomer.userId);
                Metacustomer.socket.emit("userpositions", jso);
                System.out.println("User is live now..");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
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

        jMenu6.setText("Insert");

        jMenu7.setText("Charts");
        jMenuBar1.add(jMenu7);

        jMenu2.setText("Tools");
        jMenuBar1.add(jMenu2);

        jMenu4.setText("Window");

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
        JMenuItem a11 = new JMenuItem("My Profile");
        JMenuItem a13 = new JMenuItem("Logout");

        String watchlist_url = APIs.GET_WATCHLIST_DATA;
        ApiServices services = new ApiServices();
        try {
            Response res = services.getDataWithToken(watchlist_url, Metacustomer.loginToken);
            if (res.isSuccessful()) {
                ResponseBody apiData = res.body();
                String apiJSONString = apiData.string();
                JSONObject watchlistJSON = new JSONObject(apiJSONString);
                JSONArray jsa = watchlistJSON.getJSONArray("message");

                for (int i = 0; i < jsa.length(); i++) {
                    JSONObject singleWatchlist = jsa.getJSONObject(i);
                    String symbol = Helper.getJSONString(singleWatchlist, "symbol");
                    JMenuItem symMenu = new JMenuItem(symbol);
                    a1.add(symMenu);
                    symMenu.addActionListener((ActionEvent e) -> {
//                        System.out.println(symMenu.getText());
                        String symbolName = symMenu.getText();
                        Metacustomer.symbol = symbolName;
                        Helper.updateBrowserChartThroughJava("symbol", symbolName);

                    });
                }

            }
        } catch (JSONException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        a11.addActionListener((ActionEvent e) -> {
            new Profile().setVisible(true);
        });

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

        jMenu1.add(a1);
        jMenu1.add(a11);
//        jMenu1.add(a15);
        jMenu1.add(a13);

        JMenuItem b1 = new JMenuItem("New Order");
        b1.addActionListener((ActionEvent e) -> {
            new OrderFrame().setVisible(true);
        });

        jMenu2.add(b1);
        JMenuItem f5 = new JMenuItem("Bar Chart");
        JMenuItem f6 = new JMenuItem("Candlesticks");
        JMenuItem f7 = new JMenuItem("Area Chart");
        JMenu f8 = new JMenu("Tizmeframes");
        JMenu f9 = new JMenu("Templates");
        JMenuItem redwhite = new JMenuItem("Red-white");
        JMenuItem redblue = new JMenuItem("red-blue");
        JMenuItem redgreen = new JMenuItem("red-green");

        redwhite.addActionListener(((e) -> {
            Helper.updateBrowserChartThroughJava("color", "red-white");
        }));
        redblue.addActionListener(((e) -> {
            Helper.updateBrowserChartThroughJava("color", "red-blue");
        }));
        redgreen.addActionListener(((e) -> {
            Helper.updateBrowserChartThroughJava("color", "red-green");
        }));

        f9.add(redwhite);
        f9.add(redblue);
//        f9.add(redgreen);
//        JMenu volumes = new JMenu("Volumes");
//        JMenuItem showVolumes = new JMenuItem("Show");
//        JMenuItem hideVolumes = new JMenuItem("Hide");
//
//        showVolumes.addActionListener((e) -> {
//            Helper.updateBrowserChartThroughJava("show-volume", "true");
//        });
//        hideVolumes.addActionListener((e) -> {
//            Helper.updateBrowserChartThroughJava("show-volume", "false");
//        });
//
//        volumes.add(showVolumes);
//        volumes.add(hideVolumes);
//        JMenu tickVolume = new JMenu("Tick volume");
//        JMenuItem showTickVolume = new JMenuItem("Show");
//        JMenuItem hideTickVolume = new JMenuItem("Hide");
//        showTickVolume.addActionListener((e) -> {
//            Helper.updateBrowserChartThroughJava("show-tick-volume", "true");
//        });
//        hideTickVolume.addActionListener((e) -> {
//            Helper.updateBrowserChartThroughJava("show-tick-volume", "false");
//        });
//        tickVolume.add(showTickVolume);
//        tickVolume.add(hideTickVolume);

        // chart -> timeframes
        JMenuItem f8a = new JMenuItem("1 Minute");
        JMenuItem f8b = new JMenuItem("5 Minute");
        JMenuItem f8c = new JMenuItem("15 Minute");
        JMenuItem f8d = new JMenuItem("30 Minute");
        JMenuItem f8f = new JMenuItem("1 Hour");
        JMenuItem f8g = new JMenuItem("4 Hour");
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

        f8f.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("60");
        });
        f8g.addActionListener((ActionEvent e) -> {
            updateChartBaseTime("240");
        });
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
//        f8.add(f8e);
        f8.addSeparator();
        f8.add(f8f);
        f8.add(f8g);
//        f8.add(f8h);
        f8.addSeparator();
        f8.add(f8i);
        f8.add(f8j);
        f8.add(f8k);
        f5.addActionListener((ActionEvent e) -> {
            Helper.updateBrowserChartThroughJava("type", "bar");
        });
        f6.addActionListener((ActionEvent e) -> {
            Helper.updateBrowserChartThroughJava("type", "candle");
        });
        f7.addActionListener((ActionEvent e) -> {
            Helper.updateBrowserChartThroughJava("type", "area");
        });

        jMenu7.addSeparator();
        jMenu7.add(f5);
        jMenu7.add(f6);
        jMenu7.add(f7);
        jMenu7.addSeparator();
        jMenu7.add(f8);
        jMenu7.add(f9);
//        jMenu7.add(volumes);
//        jMenu7.add(tickVolume);
    }

    public Metacustomer(Socket socket) {
        Metacustomer.socket = socket;

        setTitle("Rapid Trader");
        menubarsetup();
        menusetup();
        toolbar();
        framesetup();

        setVisible(true);
    }

}
