package com.miloway.miloprocessandthreadtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miloway.miloprocessandthreadtest.thread.SemaphoreWay;
import com.miloway.miloprocessandthreadtest.thread.SynchronizedWay;


/**
 * Created by miloway on 2018/3/22.
 */

public class ThreadShareActivity extends Activity implements UpdateTextView{
    private Button btnSynWay;
    private Button btnVolatile;
    private Button btnLockConditionWay;
    private Button btnBlockingQueue;
    private Button btnMutex;
    private Button btnSemaphore;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_share_layout);
        initView();
        btnSynWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SynchronizedWay way = SynchronizedWay.getInstance();
                way.setListener(ThreadShareActivity.this);
                way.run();
            }
        });

        btnSemaphore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SemaphoreWay way = SemaphoreWay.getInstance();
                way.setListener(ThreadShareActivity.this);
                way.run();
            }
        });

    }

    private void initView() {
        btnSynWay = (Button) findViewById(R.id.btn_syn_way);
        btnVolatile = (Button) findViewById(R.id.btn_volatile);
        btnLockConditionWay = (Button) findViewById(R.id.btn_lock_condition_way);
        btnBlockingQueue = (Button) findViewById(R.id.btn_BlockingQueue);
        btnMutex = (Button) findViewById(R.id.btn_Mutex);
        btnSemaphore = (Button) findViewById(R.id.btn_Semaphore);
        tvShow = (TextView) findViewById(R.id.tv_show);
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
}
