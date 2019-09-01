package ch.teko.hintergrundprozesse;

import android.os.Binder;

public class MyBinder extends Binder {

    private MyService service;

    public MyBinder(MyService service) {
        this.service = service;
    }

    MyService getService() {
        return service;
    }
}
