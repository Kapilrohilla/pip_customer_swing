/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.metacustomer.Utility;

//import io.socket.client.Url;
import com.mycompany.metacustomer.Metacustomer;
import static com.mycompany.metacustomer.Metacustomer.HomeChartUrl;
import static com.mycompany.metacustomer.Metacustomer.rightPanel;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Kapil
 */
public class Helper {

    public static String getMappedOrderType(int type) {
        HashMap<Integer, String> map = new HashMap();
        map.put(0, "Sell");
        map.put(1, "Buy");
        map.put(2, "Buy Limit");
        map.put(3, "Sell Limit");
        map.put(4, "Buy Stop");
        map.put(5, "Sell Stop");
        map.put(6, "Buy Stop Limit");
        map.put(7, "Sell Stop Limit");

        return map.get(type);
    }

    public static double getJSONDouble(JSONObject json, String key) {
        double toRet = 0.0;
        try {
            toRet = json.getDouble(key);
        } catch (JSONException ex) {
            System.out.println("Error: " + key + " not found, " + ex.getMessage());
        }
        return toRet;
    }

    public static String getJSONString(JSONObject json, String key) {
        String toRet = "";
        try {
            toRet = json.getString(key);
            if ("null".equals(toRet)) {
                toRet = "";
            }
        } catch (JSONException ex) {
            System.out.println("Error: " + key + " not found, " + ex.getMessage());
        }
        return toRet;
    }

    public static int getJSONInt(JSONObject json, String key) {
        int toRet = 0;
        try {
            toRet = json.getInt(key);
        } catch (JSONException ex) {
            System.out.println("Error: " + key + " not found, " + ex.getMessage());
        }
        return toRet;
    }

    public static String updateQueryParamterInUrl(String url, String param, String value) throws MalformedURLException {
        URL uri = new URL(url);
        String host = uri.getHost();
        int port = uri.getPort();
        String protocol = uri.getProtocol();
        String path = uri.getPath();
        String queryString = uri.getQuery();

        String[] queries = queryString.split("&");
        String[] newQueries = new String[queries.length];
        boolean isUpdated = false;
        for (int i = 0; i < queries.length; i++) {
            String query = queries[i];
            String[] aQuery = query.split("=");
            if (param.equals(aQuery[0])) {
                isUpdated = true;
                aQuery[1] = value;
            }
            newQueries[i] = String.join("=", aQuery);
        }

        String newQueryString = String.join("&", newQueries);
        String newUrlString;
        if (isUpdated) {
            newUrlString = protocol + "://" + host + ":" + port + path + "?" + newQueryString;
        } else {
            newUrlString = protocol + "://" + host + ":" + port + path + "?" + newQueryString + "&" + param + "=" + value;
        }

        System.out.println("newUrlString: " + newUrlString);
        return newUrlString;
    }

    public static void updateBrowserChartThroughJava(String param, String value) {
        String initialUrl = Metacustomer.HomeChartUrl;
        try {
            String newUrl = Helper.updateQueryParamterInUrl(initialUrl, param, value);
            Metacustomer.HomeChartUrl = newUrl;

        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());

        }
        rightPanel.browser_.loadURL(HomeChartUrl);
    }
}
