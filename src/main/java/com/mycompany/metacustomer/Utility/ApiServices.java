/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.metacustomer.Utility;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

/**
 *
 * @author Kapil
 */
public class ApiServices {

    public Response getDataWithoutToken(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();

        Call newCall = client.newCall(req);
        Response res = newCall.execute();
        return res;
    }

    public Response postDataWithoutToken(String url, JSONObject payload) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, payload.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response res = call.execute();
        return res;
    }

    public Response getDataWithToken(String url, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder().url(url).header("Authorization", token).build();

        Call call = client.newCall(req);
        Response res = call.execute();
        return res;
    }

    public Response postDataWithToken(String url, JSONObject payload, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, payload.toString());
        Request req = new Request.Builder().url(url).post(body).header("Authorization", token).build();

        Call call = client.newCall(req);
        Response res = call.execute();
        return res;
    }

}
