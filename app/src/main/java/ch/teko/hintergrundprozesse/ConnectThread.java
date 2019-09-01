package ch.teko.hintergrundprozesse;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Thread;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ConnectThread extends Thread {
    private static final String LOG_TAG = "ConnectThread";
    private String urlStr;

    public ConnectThread(String urlStr) {
        Log.d(LOG_TAG, "new ConnectThread Object created");
        this.urlStr = urlStr;
    }

    @Override
    public void run() {
        String jsonStr = getJson(urlStr);
        ArrayList<Transport> transportList = parseJson(jsonStr);

//        todo: test
        for(int i = 0; i < transportList.size(); i++) {
            Log.d(LOG_TAG, transportList.get(i).getId());
            Log.d(LOG_TAG, transportList.get(i).getName());
            Log.d(LOG_TAG, transportList.get(i).getScore());
            Log.d(LOG_TAG, transportList.get(i).getCoordinate_type());
            Log.d(LOG_TAG, transportList.get(i).getCoordinate_x());
            Log.d(LOG_TAG, transportList.get(i).getCoordinate_y());
            Log.d(LOG_TAG, transportList.get(i).getDistance());
            Log.d(LOG_TAG, transportList.get(i).getIcon());
            Log.d(LOG_TAG, " ");
        }

//todo test
//        Uri geouri = Uri.parse("geo:" + transportList.get(0).getCoordinate_x() + transportList.get(0).getCoordinate_y());
//        Intent geomap = new Intent(Intent.ACTION_VIEW, geouri);
//        geomap.setPackage("com.google.android.apps.maps");

        //        todo
//        Log.d(LOG_TAG, "jsonStr = " + jsonStr);
    }

    private String getJson(String urlStr) {
        String jsonStr = null;
        try{
            //      todo, if user input is not for transportAPI
            InputStream inputStream = new URL(urlStr).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            jsonStr = buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            //todo
            jsonStr = "MalformedURLException";
        } catch (IOException e) {
            e.printStackTrace();
            //todo
            jsonStr = "IOException";
        } catch (NullPointerException e) {
            e.printStackTrace();
            //todo
            jsonStr = "NullPointerException";
        }
        return jsonStr;
    }

    private ArrayList<Transport> parseJson(String jsonStr) {
        ArrayList<Transport> transportList = new ArrayList<Transport>();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("stations");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject stationInfo = jsonArray.getJSONObject(i);
                //todo test
                Log.d(LOG_TAG, stationInfo.toString());

                String id = stationInfo.getString("id");
                String name = stationInfo.getString("name");
                String score = stationInfo.getString("score");

                String coordinate_type = stationInfo.getJSONObject("coordinate").getString("type");
                String coordinate_x = stationInfo.getJSONObject("coordinate").getString("x");
                String coordinate_y = stationInfo.getJSONObject("coordinate").getString("y");

                String distance = stationInfo.getString("distance");
                String icon = stationInfo.getString("icon");

                Transport transport = new Transport(id,name, score, coordinate_type, coordinate_x, coordinate_y, distance, icon);
                transportList.add(transport);
//                todo
                Log.d(LOG_TAG, "\nid: " + id +
                        "\nname: " + name +
                        "\nscore: " + score +
                        "\ncoordinate_type: " + coordinate_type +
                        "\ncoordinate_x: " + coordinate_x +
                        "\ncoordinate_y: " + coordinate_y +
                        "\ndistance: " + distance +
                        "\nicon: " + icon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transportList;
    }
}
