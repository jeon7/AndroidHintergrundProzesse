package ch.teko.hintergrundprozesse;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class MyService extends Service {
    private static final String LOG_TAG = "MyService";
    IBinder myBinder = new MyBinder(); // binder given to client
    String locationUserInput;

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
        processServiceRequest(intent);
        return myBinder;
    }

    private void processServiceRequest(Intent intent) {
        Log.d(LOG_TAG,"processServiceRequest() called");
        locationUserInput = intent.getExtras().getString("locationUserInput");
        Log.d(LOG_TAG,"locationUserInput = " + locationUserInput);

//        todo
        final String defaultUrl = "http://transport.opendata.ch/v1/locations?query=";
        String urlStr = defaultUrl + locationUserInput;

        ConnectThread thread = new ConnectThread(urlStr);
        thread.start();

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
