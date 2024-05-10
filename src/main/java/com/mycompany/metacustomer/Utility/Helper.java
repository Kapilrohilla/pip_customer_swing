/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.metacustomer.Utility;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

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
}
