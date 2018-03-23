package com.miloway.miloprocessandthreadtest.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by miloway on 2018/3/23.
 */

public class MyData implements Parcelable{
    public String name = "milo";

    public static final Creator<MyData> CREATOR = new Creator<MyData>() {
        @Override
        public MyData createFromParcel(Parcel in) {
            MyData data = new MyData();
            data.name = in.readString();
            return data;
        }

        @Override
        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

}
