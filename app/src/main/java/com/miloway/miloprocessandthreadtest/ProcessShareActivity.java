package com.miloway.miloprocessandthreadtest;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miloway.miloprocessandthreadtest.process.MyBinder;
import com.miloway.miloprocessandthreadtest.process.MyContentProvider;
import com.miloway.miloprocessandthreadtest.process.MyFileObserver;
import com.miloway.miloprocessandthreadtest.process.MyService;

import java.io.File;
import java.io.IOException;

/**
 * Created by miloway on 2018/3/22.
 */

public class ProcessShareActivity extends Activity implements UpdateTextView {
    private Button btnService;
    private Button btnFile;
    private TextView tvShow;
    private ServiceConnection connection;
    private MyFileObserver observer;
    private Button btnContentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_share_layout);
        initView();
        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startServiceDirect();
            }
        });
        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileObserver();
            }
        });
        btnContentProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentProvider();
            }
        });
    }

    private void initView() {
        btnService = (Button) findViewById(R.id.btn_service);
        btnFile = (Button) findViewById(R.id.btn_file);
        tvShow = (TextView) findViewById(R.id.tv_show);
        btnContentProvider = (Button) findViewById(R.id.btn_content_provider);
    }


    private void startServiceDirect() {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                tvShow.setText(binder.string);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }


    private void fileObserver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return;
        }

        String path = Environment.getExternalStorageDirectory() + "/" + "a.txt";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tvShow.setText(path);
        observer = new MyFileObserver(path);
        observer.setListener(this);
        observer.startWatching();
    }

    private void contentProvider() {
        ContentValues values = new ContentValues();
        values.put("txt", "insert");
        getContentResolver().insert(MyContentProvider.uri,values);

    }













    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        observer.stopWatching();
    }

    @Override
    public void updateText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvShow.setText(text);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0])) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tvShow.setText("获取写权限成功");
                    fileObserver();
                } else {
                    tvShow.setText("获取写权限失败");
                }
            }
        }
    }
}
