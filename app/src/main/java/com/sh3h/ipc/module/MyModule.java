package com.sh3h.ipc.module;


import android.os.Parcel;
import android.os.Parcelable;

public class MyModule implements Parcelable {
    public static final String PACKAGE_NAME = "packageName";
    public static final String ACTIVITY_NAME = "activityName";
    public static final String DATA = "data";
    public static final String SEPARATOR = "#";
    public static final String COUNT = "count";
    public static final String MQTT = "mqtt";

    /**
     * for example
     * {
     *     "packageName": "com.sh3h.meterreading",
     *     "activityName": "com.sh3h.meterreading.ui.xxx.xxx",
     *     "data" : [
     *       "count#0",
     *       "mqtt#xxxxxxx"
     *     ]
     * }
     *
     */

    private String info; // json

    public MyModule() {
    }

    public MyModule(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(info);
    }

    //添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
    public static final Creator<MyModule> CREATOR = new Creator<MyModule>() {
        @Override
        public MyModule createFromParcel(Parcel source) {//从Parcel中读取数据，返回person对象
            return new MyModule(source.readString());
        }

        @Override
        public MyModule[] newArray(int size) {
            return new MyModule[size];
        }
    };
}
