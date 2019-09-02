package ch.teko.hintergrundprozesse.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Functions {
    private static final String LOG_TAG = "json.Functions";

    public static ArrayList<Transport> parseJsonFromUrl(String urlStr) {
        return parseJson(getJson(urlStr));
    }

    private static String getJson(String urlStr) {
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

    private static ArrayList<Transport> parseJson(String jsonStr) {
        ArrayList<Transport> transportList = new ArrayList();
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
