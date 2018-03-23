package com.miloway.miloprocessandthreadtest.process;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.miloway.miloprocessandthreadtest.bean.MyData;
import com.miloway.miloprocessandthreadtest.bean.MyDataManager;

/**
 * Created by miloway on 2018/3/23.
 */

public class MyExportService extends Service {

    private MyData data;
    private MyDataManager.Stub stub = new MyDataManager.Stub() {
        @Override
        public MyData getMyData() throws RemoteException {
            return data;
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        data = new MyData();
        data.name = "MyExportService";
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
