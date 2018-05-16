package com.sh3h.dataprovider.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 材料确认信息
 * Created by dengzhimin on 2017/3/31.
 */

public class DUMaterialVerify implements Parcelable {

    private Long verifyId;//材料确认ID

    private Long serverId;//后台ID

    private Long materialId;//材料ID

    private String deliveryNumber;//送货单编号

    private String constructionTeam;//施工队

    private float verifyCount;//确认数量

    private String operator;//操作人

    private long operateTime;//操作时间

    private double longitude;//经度

    private double latitude;//纬度

    private String remark;//备注

    private String imageUrl;

    private int upload;//上传标志 0:未上传 1:已上传

    private DUMaterial duMaterial;

    public DUMaterialVerify(){}

    public DUMaterialVerify(Long verifyId, Long serverId, Long materialId, String deliveryNumber, String constructionTeam, float verifyCount, String operator,
                            long operateTime, double longitude, double latitute, String remark,String imageUrl, int upload) {
        this.verifyId = verifyId;
        this.serverId = serverId;
        this.materialId = materialId;
        this.deliveryNumber = deliveryNumber;
        this.constructionTeam = constructionTeam;
        this.verifyCount = verifyCount;
        this.operator = operator;
        this.operateTime = operateTime;
        this.longitude = longitude;
        this.latitude = latitute;
        this.remark = remark;
        this.imageUrl = imageUrl;
        this.upload = upload;
    }

    protected DUMaterialVerify(Parcel in) {
        deliveryNumber = in.readString();
        constructionTeam = in.readString();
        verifyCount = in.readFloat();
        operator = in.readString();
        operateTime = in.readLong();
        longitude = in.readDouble();
        latitude = in.readDouble();
        remark = in.readString();
        imageUrl = in.readString();
        upload = in.readInt();
        duMaterial = in.readParcelable(DUMaterial.class.getClassLoader());
    }

    public static final Creator<DUMaterialVerify> CREATOR = new Creator<DUMaterialVerify>() {
        @Override
        public DUMaterialVerify createFromParcel(Parcel in) {
            return new DUMaterialVerify(in);
        }

        @Override
        public DUMaterialVerify[] newArray(int size) {
            return new DUMaterialVerify[size];
        }
    };

    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getConstructionTeam() {
        return constructionTeam;
    }

    public void setConstructionTeam(String constructionTeam) {
        this.constructionTeam = constructionTeam;
    }

    public float getVerifyCount() {
        return verifyCount;
    }

    public void setVerifyCount(float verifyCount) {
        this.verifyCount = verifyCount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(long operateTime) {
        this.operateTime = operateTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
    }

    public DUMaterial getDuMaterial() {
        return duMaterial;
    }

    public void setDuMaterial(DUMaterial duMaterial) {
        this.duMaterial = duMaterial;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deliveryNumber);
        dest.writeString(constructionTeam);
        dest.writeFloat(verifyCount);
        dest.writeString(operator);
        dest.writeLong(operateTime);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(remark);
        dest.writeString(imageUrl);
        dest.writeInt(upload);
        dest.writeParcelable(duMaterial, flags);
    }
}
