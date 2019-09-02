package ch.teko.hintergrundprozesse;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import ch.teko.hintergrundprozesse.json.Functions;
import ch.teko.hintergrundprozesse.json.Transport;

public class MyService extends Service {
    private static final String LOG_TAG = "MyService";
    private final String defaultUrl = "http://transport.opendata.ch/v1/locations?query=";
    IBinder myBinder = new MyBinder(); // binder given to client

    String locationUserInput;
    String urlStr;
    ArrayList<Transport> transportList;

    //     inner Class used for the client binder
    public class MyBinder extends Binder {
        private static final String LOG_TAG = "MyBinder in MyService";

        public MyBinder(){
            Log.d(LOG_TAG, "MyBinder() created");
        }
        MyService getService() {
            Log.d(LOG_TAG, "getService() called");
            return MyService.this;
        }
    }

    public MyService() {
        Log.d(LOG_TAG, "MyService() created");
    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG,"onCreate() called");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind() called");
        locationUserInput = intent.getExtras().getString("locationUserInput");
        getTransportTable(locationUserInput);

        //todo test
//        Uri geouri = Uri.parse("geo:" + transportList.get(0).getCoordinate_x() + transportList.get(0).getCoordinate_y());
//        Intent geomap = new Intent(Intent.ACTION_VIEW, geouri);
//        geomap.setPackage("com.google.android.apps.maps");


        return myBinder;
    }

    private void getTransportTable(String locationUserInput) {
        Log.d(LOG_TAG,"getTransportTable() called");
        Log.d(LOG_TAG,"locationUserInput = " + locationUserInput);

//        todo
        urlStr = defaultUrl + locationUserInput;

        new Thread(new Runnable() {
            @Override
            public void run() {
                transportList = Functions.parseJsonFromUrl(urlStr);

//        todo: test
                for (int i = 0; i < transportList.size(); i++) {
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
            }
        }).start();

    }


    @Override
    // never called, due to bindService() without startService()
    //todo
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG,"onStartCommand() called");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // test Method for client
    //todo
    int getRandom() {
        return new Random().nextInt(100);
    }

    // test Method for client
    //todo
    String getCity() {
        return locationUserInput;
    }
}
