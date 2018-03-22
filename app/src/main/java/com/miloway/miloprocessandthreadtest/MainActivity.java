package com.miloway.miloprocessandthreadtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button btnProcess;
    private Button btnThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProcessShareActivity.class);
                startActivity(intent);
            }
        });

        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThreadShareActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btnProcess = (Button) findViewById(R.id.btn_process);
        btnThread = (Button) findViewById(R.id.btn_thread);
    }
}
