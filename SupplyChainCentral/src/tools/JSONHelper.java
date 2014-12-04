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

    int tem;
    float dist;
    double lat;
    double lon;
    int time;

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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

    public float calcDistance(String beg, String end) {
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
    
    public int calcTravelTime(String beg, String end) {
        JSONObject rootObject;
        try {
            rootObject = readJsonFromUrl("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + beg + "&destinations=" + end + "&mode=driving&sensor=false");
            JSONArray rows = rootObject.getJSONArray("rows");
            
            tem = rows.getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getInt("value");
            time = tem;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time;
    }
    
    
    public double getGPSlat(String address){
        JSONObject rootObject = null;
        try{
            rootObject = readJsonFromUrl("https://maps.googleapis.com/maps/api/geocode/json?address=" + address);
            JSONArray results = null;
            results = rootObject.getJSONArray("results");
            lat = (double) results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
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
            lon = (double) results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lon;
    }
}
