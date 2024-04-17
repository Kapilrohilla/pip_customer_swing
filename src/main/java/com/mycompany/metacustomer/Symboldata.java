/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.metacustomer;

/**
 *
 * @author techninza
 */
public class Symboldata {
    private String symbol;
    private String bid;
    private String ask;
    private String volume;
    
    
    public Symboldata()
    {
        
    }

    public Symboldata(String symbol, String bid, String ask, String volume) {
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
    
    
}
