package ch.teko.hintergrundprozesse;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    MyService myService;
    boolean isBound = false;

    public ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(LOG_TAG, "onServiceConnected() called from ServiceConnection object");
            myService = ((MyService.MyBinder)binder).getService();
            isBound = true;
            Log.d(LOG_TAG, "isBound = " + isBound);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            unbindService(connection);
            isBound = false;
            Log.d(LOG_TAG, "isBound = " + isBound);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart() called");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop() called");
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }

    public void onButtonOkClicked(View view) {
        Log.d(LOG_TAG, "onButtonOkClicked() called");
        EditText locationUserInput = findViewById(R.id.editText_location);
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("locationUserInput", locationUserInput.getText().toString());

        //todo: once bound, cannot refresh the intent data before unbound?
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        //todo
//        startService(intent);
    }

    //todo
    // test onButton method
    public void onButtonNumClicked(View view) {
        Log.d(LOG_TAG, "onButtonNumClicked() called");
        if(isBound) {
            int num = myService.getRandom();
            Log.d(LOG_TAG, "data from service = " + num);
            Toast.makeText(getApplicationContext(), "Data from MyService: " + num, Toast.LENGTH_LONG).show();
        } else {
            int num = myService.getRandom();                            // for test
            Log.d(LOG_TAG, "data from service = " + num);         // for test
            Toast.makeText(getApplicationContext(), "not connected to MyService", Toast.LENGTH_LONG).show();
        }
    }

    //todo
    // test onButton method
    public void onButtonCityClicked(View view) {
        Log.d(LOG_TAG, "onButtonCityClicked() called");

        if(isBound) {
            String city = myService.getCity();
            Log.d(LOG_TAG, "data from service = " + city);
            Toast.makeText(getApplicationContext(), "Data from MyService: " + city, Toast.LENGTH_LONG).show();
        } else {
            String city = myService.getCity();                          // for test
            Log.d(LOG_TAG, "data from service = " + city);        // for test
            Toast.makeText(getApplicationContext(), "Data from MyService: " + city, Toast.LENGTH_LONG).show();
        }
    }

    //todo
    // test onButton method
    public void onButtonUnbindClicked(View view) {
        Log.d(LOG_TAG, "onButtonUnbindClicked() called");
        if(isBound) {
            unbindService(connection);
            isBound = false;
        } else {
            Toast.makeText(getApplicationContext(), "already unbound", Toast.LENGTH_LONG).show();
        }
    }
}
