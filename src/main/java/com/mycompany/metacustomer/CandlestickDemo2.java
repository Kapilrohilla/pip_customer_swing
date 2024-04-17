package com.mycompany.metacustomer;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.*;
import java.time.Instant;
import java.util.*;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @see https://stackoverflow.com/a/18421887/230513
 * @see http://www.jfree.org/forum/viewtopic.php?f=10&t=24521
 */
public class CandlestickDemo2 extends JFrame {

    public CandlestickDemo2(String stockSymbol) {
      //  super("CandlestickDemo2");
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final DateAxis domainAxis = new DateAxis("");
        NumberAxis rangeAxis = new NumberAxis("");
        CandlestickRenderer renderer = new CandlestickRenderer();
        XYDataset dataset = getDataSet(stockSymbol);
        XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
        //Do some setting up, see the API Doc
        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setDrawVolume(false);
        rangeAxis.setAutoRangeIncludesZero(false);
        //Now create the chart and chart panel
        JFreeChart chart = new JFreeChart(stockSymbol, null, mainPlot, false);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(600, 300));
//        mainPlot.setDomainPannable(true);
//        mainPlot.setRangePannable(true);
          setLayout(new BorderLayout());
        this.add(chartPanel);
        // Add tiemline toggle
        final Timeline oldTimeline = domainAxis.getTimeline();
        final Timeline newTimeline = domainAxis.getTimeline();
//        this.add(new JCheckBox(new AbstractAction("Segmented Timeline") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JCheckBox jcb = (JCheckBox) e.getSource();
//                if (jcb.isSelected()) {
//                    domainAxis.setTimeline(newTimeline);
//                } else {
//                    domainAxis.setTimeline(oldTimeline);
//                }
//            }
//        }), BorderLayout.SOUTH);
//        this.pack();
//        this.setLocationRelativeTo(null);
    }

    private AbstractXYDataset getDataSet(String stockSymbol) {
        //This is the dataset we are going to create
        DefaultOHLCDataset result;
        //This is the data needed for the dataset
        OHLCDataItem[] data;
        //This is where we go get the data, replace with your own data source
        data = getData(stockSymbol);
        //Create a dataset, an Open, High, Low, Close dataset
        result = new DefaultOHLCDataset(stockSymbol, data);
        return result;
    }
    //This method uses yahoo finance to get the OHLC data

    protected OHLCDataItem[] getData(String stockSymbol) {
        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
        
        String apiUrl =
                        "https://shopninja.in/meta/chartdata.json";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseData = response.body();
//                System.out.println(responseData);
                System.out.println(responseData);
                JSONArray js=new JSONArray(responseData);
                for(int i=0;i<js.length();i++)
                {
                    JSONObject jsonobject=js.getJSONObject(i);
                    String date_str=jsonobject.getString("date");
                   Instant instant = Instant.parse(date_str);
        long timestamp = instant.toEpochMilli();
        Date d=new Date(timestamp);
                    double open=Double.parseDouble(jsonobject.getString("open"));
                    double high=Double.parseDouble(jsonobject.getString("high"));
                    double low=Double.parseDouble(jsonobject.getString("low"));
                    double close=Double.parseDouble(jsonobject.getString("close"));
                    double volume=Double.parseDouble(jsonobject.getString("volume"));
        OHLCDataItem item = new OHLCDataItem(d, open, high, low, close, volume);  
                 dataItems.add(item);
                }
            }
            System.out.println(dataItems.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }    
        
        
        
        
       
             
        //Data from Yahoo is from newest to oldest. Reverse so it is oldest to newest
        Collections.reverse(dataItems);
        //Convert the list into an array
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
        return data;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CandlestickDemo2("AAPL").setVisible(true);
            }
        });
    }
}