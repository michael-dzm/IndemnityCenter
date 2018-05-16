package com.sh3h.dataprovider.data.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.sh3h.dataprovider.BR;


/**
 * Created by xulongjun on 2017/3/9.
 */

public class DUAddress extends BaseObservable implements Parcelable {

    private Long addressId;//地址ID

    private Long serverId;//后台ID

    private Long userId;//用户ID

    private String addressContent;//地址内容

    private int isDefault;//是否默认 0非默认 1默认

    private long operateTime;//操作时间

    private int upload;//上传标志 0:未上传 1:已上传 下载的地址数据默认已上传

    public enum Default{
        YES(1,"默认"), NO(0, "非默认");

        private int value;
        private String name;

        Default(int value, String name){
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    protected DUAddress(Parcel in) {
        addressContent = in.readString();
        isDefault = in.readInt();
        operateTime = in.readLong();
        upload = in.readInt();
    }

    public static final Creator<DUAddress> CREATOR = new Creator<DUAddress>() {
        @Override
        public DUAddress createFromParcel(Parcel in) {
            return new DUAddress(in);
        }

        @Override
        public DUAddress[] newArray(int size) {
            return new DUAddress[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressContent);
        dest.writeInt(isDefault);
        dest.writeLong(operateTime);
        dest.writeInt(upload);
    }

    public DUAddress(){}

    public DUAddress(String addressContent, int isDefault) {
        this.addressContent = addressContent;
        this.isDefault = isDefault;
    }

    public DUAddress(Long addressId, Long serverId, Long userId, String addressContent, int isDefault, long operateTime, int upload) {
        this.addressId = addressId;
        this.serverId = serverId;
        this.userId = userId;
        this.addressContent = addressContent;
        this.isDefault = isDefault;
        this.operateTime = operateTime;
        this.upload = upload;
    }

    @Bindable
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
        notifyPropertyChanged(BR.addressId);
    }

    @Bindable
    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
        notifyPropertyChanged(BR.serverId);
    }

    @Bindable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }

    @Bindable
    public String getAddressContent() {
        return addressContent;
    }

    public void setAddressContent(String addressContent) {
        this.addressContent = addressContent;
        notifyPropertyChanged(BR.addressContent);
    }

    @Bindable
    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
        notifyPropertyChanged(BR.isDefault);
    }

    @Bindable
    public long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(long operateTime) {
        this.operateTime = operateTime;
        notifyPropertyChanged(BR.operateTime);
    }

    @Bindable
    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
        notifyPropertyChanged(BR.upload);
    }
}
