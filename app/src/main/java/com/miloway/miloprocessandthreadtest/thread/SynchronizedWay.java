package com.miloway.miloprocessandthreadtest.thread;

import android.util.Log;

import com.miloway.miloprocessandthreadtest.UpdateTextView;

/**
 * Created by miloway on 2018/3/22.
 */

public class SynchronizedWay {

    private static SynchronizedWay s = new SynchronizedWay();
    private int count = 0;
    private String message = "SynchronizedWay\n";
    private UpdateTextView listener;

    private SynchronizedWay() {}
    public static SynchronizedWay getInstance() {
        return s;
    }


    private Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {

            for (int i = 0; i < 10 ; ) {
                synchronized (s){
                    i++;
                    count++;
                    message += "创建了一个对象"+i + "\n";
                    s.notify();
                }
                try {
                    Thread.sleep(100);
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
                synchronized (s){
                    if (count < 1){
                        try {
                            s.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        i++;
                        count--;
                        message += "销毁了一个对象"+i + "\n";
                        s.notify();
                    }
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
