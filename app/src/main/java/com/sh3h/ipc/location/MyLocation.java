package com.sh3h.ipc.location;


import android.os.Parcel;
import android.os.Parcelable;

public class MyLocation implements Parcelable {
    private boolean mIsGpsLocated;
    private double mLongitude;
    private double mLatitude;
    private float mDirection;
    private float mAccuracy;

    public MyLocation() {
        mIsGpsLocated = false;
        mLongitude = 0.0;
        mLatitude = 0.0;
        mDirection = 0.0f;
        mAccuracy = 0.0f;
    }

    public MyLocation(boolean isGpsLocated,
                      double longitude,
                      double latitude,
                      float direction,
                      float accuracy) {
        mIsGpsLocated = isGpsLocated;
        mLongitude = longitude;
        mLatitude = latitude;
        mDirection = direction;
        mAccuracy = accuracy;
    }

    public boolean isGpsLocated() {
        return mIsGpsLocated;
    }

    public void setIsGpsLocated(boolean isGpsLocated) {
        this.mIsGpsLocated = isGpsLocated;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setDirection(float direction) {
        mDirection = direction;
    }

    public float getDirection() {
        return mDirection;
    }

    public void setAccuracy(float accuracy) {
        mAccuracy = accuracy;
    }

    public float getAccuracy() {
        return mAccuracy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(new boolean[]{mIsGpsLocated});
        dest.writeDouble(mLongitude);
        dest.writeDouble(mLatitude);
        dest.writeFloat(mDirection);
        dest.writeFloat(mAccuracy);
    }

    //添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
    public static final Creator<MyLocation> CREATOR = new Creator<MyLocation>() {
        @Override
        public MyLocation createFromParcel(Parcel source) {//从Parcel中读取数据，返回person对象
            boolean[] val = new boolean[1];
            source.readBooleanArray(val);
            return new MyLocation(val[0], source.readDouble(), source.readDouble(),
                    source.readFloat(), source.readFloat());
        }

        @Override
        public MyLocation[] newArray(int size) {
            return new MyLocation[size];
        }
    };
}
