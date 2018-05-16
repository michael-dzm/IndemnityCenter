package com.sh3h.dataprovider.data.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sh3h.dataprovider.BR;

/**
 * 工程操作表
 * Created by dengzhimin on 2017/3/28.
 */

public class DUOperate extends BaseObservable{

    private Long operateId;//操作ID

    private Long serverId;//后台ID

    private Long projectId;//工程ID

    private int operateType;//操作类型 1开工 2停工 3复工 4完工

    private long startTime;//开工时间

    private long stopTime;//停工时间

    private long restartTime;//复工时间

    private long endTime;//完工时间

    private String operator;//操作人

    private long operateTime;//操作时间

    private double longitude;//经度

    private double latitude;//纬度

    private String remark;//备注

    private int upload;//上传标志 0:未上传 1:已上传

    public enum Type{
        START(4),//开工
        STOP(5),//停工
        RESTART(6),//复工
        FINISH(7);//完工

        private int value;

        Type(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public DUOperate(){}

    public DUOperate(Long operateId, Long serverId, Long projectId, int operateType, long startTime, long stopTime, long restartTime, long endTime,
                     String operator, long operateTime, double longitude, double latitude, String remark, int upload) {
        this.operateId = operateId;
        this.serverId = serverId;
        this.projectId = projectId;
        this.operateType = operateType;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.restartTime = restartTime;
        this.endTime = endTime;
        this.operator = operator;
        this.operateTime = operateTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remark = remark;
        this.upload = upload;
    }

    @Bindable
    public Long getOperateId() {
        return operateId;
    }

    public void setOperateId(Long operateId) {
        this.operateId = operateId;
        notifyPropertyChanged(BR.operateId);
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
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
        notifyPropertyChanged(BR.projectId);
    }

    @Bindable
    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
        notifyPropertyChanged(BR.operateType);
    }

    @Bindable
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
    }

    @Bindable
    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
        notifyPropertyChanged(BR.stopTime);
    }

    @Bindable
    public long getRestartTime() {
        return restartTime;
    }

    public void setRestartTime(long restartTime) {
        this.restartTime = restartTime;
        notifyPropertyChanged(BR.restartTime);
    }

    @Bindable
    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
        notifyPropertyChanged(BR.endTime);
    }

    @Bindable
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
        notifyPropertyChanged(BR.operator);
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
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        notifyPropertyChanged(BR.longitude);
    }

    @Bindable
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        notifyPropertyChanged(BR.latitude);
    }

    @Bindable
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
        notifyPropertyChanged(BR.remark);
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
