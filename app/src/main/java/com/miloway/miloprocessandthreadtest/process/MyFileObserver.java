package com.miloway.miloprocessandthreadtest.process;

import android.os.FileObserver;

import com.miloway.miloprocessandthreadtest.UpdateTextView;

/**
 * Created by miloway on 2018/3/23.
 */

public class MyFileObserver extends FileObserver {
    private UpdateTextView listener;

    public MyFileObserver(String path) {
        super(path);
    }

    @Override
    public void onEvent(int event, String path) {
        if (event == FileObserver.MODIFY) {
            if (listener != null) {
                listener.updateText("modify");
            }
        }else if (event == FileObserver.OPEN) {
            if (listener != null) {
                listener.updateText("open");
            }
        }else if (event == FileObserver.ATTRIB){
            if (listener != null) {
                listener.updateText("ATTRIB");
            }
        }else {
            if (listener != null) {
                listener.updateText(path + "open");
            }
        }
    }
    public void setListener(UpdateTextView listener) {
        this.listener = listener;
    }

}
