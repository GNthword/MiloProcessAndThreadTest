package com.miloway.miloprocessandthreadtest.process;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by miloway on 2018/3/23.
 */

public class MyService extends Service {

    private MyBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        binder.string = "from onCreate";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        binder = new MyBinder();
        binder.string = "from onStartCommand";
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }



}
