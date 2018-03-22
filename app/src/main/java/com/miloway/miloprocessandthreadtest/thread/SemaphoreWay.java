package com.miloway.miloprocessandthreadtest.thread;

import android.util.Log;


import com.miloway.miloprocessandthreadtest.UpdateTextView;

import java.util.concurrent.Semaphore;

/**
 * Created by miloway on 2018/3/23.
 */

public class SemaphoreWay {

    private static  SemaphoreWay s = new SemaphoreWay();
    private Semaphore semaphore = new Semaphore(1);
    private UpdateTextView listener;
    private String message = "SemaphoreWay\n";
    private int count = 0;
    private SemaphoreWay() {}

    public static SemaphoreWay getInstance() {
        return s;
    }

    private Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 10 ; ) {
                try {
                    semaphore.acquire();
                    i++;
                    count++;
                    message += "创建了一个对象"+i + "\n";
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            Log.d("cc","theard 1 end");
            synchronized (listener) {
                count++;
                Log.d("cc","theard 1 end" + count);
                listener.notify();
            }
        }
    });

    private Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 10 ; ) {
                try {
                    semaphore.acquire();
                    if (count < 1){
                        semaphore.release();
                    }else {
                        i++;
                        count--;
                        message += "销毁了一个对象"+i + "\n";
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            Log.d("cc","theard 2 end");
            synchronized (listener) {
                count++;
                Log.d("cc","theard 2 end count" + count);
                listener.notify();
            }
        }
    });

    private Thread thread3 = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                synchronized (listener) {
                    Log.d("cc","thread3 " + count);
                    if (count == 2) {
                        listener.updateText(message);
                        return;
                    }else {
                        try {
                            listener.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    });

    public void setListener(UpdateTextView listener) {
        this.listener = listener;
    }

    public void run(){
        if (listener != null) {
            thread2.start();
            thread1.start();
            thread3.start();
        }
    }
}
