/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.*;

public class JSONHelper {

    Integer tem;
    Float dist;
    Double lat;
    Double lon;

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public float calcDistance(StringBuffer beg, StringBuffer end) {
        JSONObject rootObject = null;
        try {
            rootObject = readJsonFromUrl("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + beg + "&destinations=" + end + "&mode=driving&sensor=false");
            JSONArray rows = null;
            rows = rootObject.getJSONArray("rows");
            tem = (Integer) rows.getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getInt("value");
            dist = (float) tem / 1000;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dist;
    }
    
    public double getGPSlat(String address){
        JSONObject rootObject = null;
        try {
            rootObject = readJsonFromUrl("https://maps.googleapis.com/maps/api/geocode/json?address=" + address);
            JSONArray results = null;
            results = rootObject.getJSONArray("results");
            lat = (Double) results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lat;
    }
    
    public double getGPSlon(String address){
        JSONObject rootObject = null;
        try{
            rootObject = readJsonFromUrl("https://maps.googleapis.com/maps/api/geocode/json?address=" + address);
            JSONArray results = null;
            results = rootObject.getJSONArray("results");
            lon = (Double) results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lon;
    }
}