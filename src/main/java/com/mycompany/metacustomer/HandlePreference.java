/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.metacustomer;

// this class is to handle Preferences in the project
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class HandlePreference {

    private final Preferences storage = Preferences.userRoot();

    public void saveToken(String token) {
        storage.put("Token", token);
        System.out.println("save successful");
    }

    public String retrieveToken() {
        String token = storage.get("Token", "not found");
        return token;
    }

    public boolean logout() {
        try {
            storage.remove("Token");
            return true;
        } catch (Exception ex) {
//            System.out.println("Failed to logout");
            return false;
        }
    }

}
