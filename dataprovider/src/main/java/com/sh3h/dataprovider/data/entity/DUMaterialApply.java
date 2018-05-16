package com.sh3h.dataprovider.data.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.sh3h.dataprovider.BR;

/**
 * 材料申领信息
 * Created by dengzhimin on 2017/3/31.
 */

public class DUMaterialApply extends BaseObservable implements Parcelable {

    private Long applyId;//申领ID

    private Long serverId;//后台ID

    private Long materialId;//材料ID

    private String applyNumber;//申领编号

    private float applyCount;//申领数量

    private float verifyCount;//确认入库数量

    private String constructionTeam;//施工队

    private String applyDepartment;//领用部门

    private String applyPersion;//领用人

    private String contactPhone;//联系电话

    private String deliveryAddress;//送货地址

    private String operator;//操作人

    private long operateTime;//操作时间

    private double longitude;//经度

    private double latitude;//纬度

    private String remark;//备注

    private int upload;//上传标志 0:未上传 1:已上传)

    private DUMaterial duMaterial;

    public DUMaterialApply(){}

    public DUMaterialApply(Long applyId, Long serverId, Long materialId, String applyNumber, float applyCount, float verifyCount, String constructionTeam,
                           String applyDepartment, String applyPersion, String contactPhone, String deliveryAddress, String operator, long operateTime,
                           double longitude, double latitude, String remark, int upload) {
        this.applyId = applyId;
        this.serverId = serverId;
        this.materialId = materialId;
        this.applyNumber = applyNumber;
        this.applyCount = applyCount;
        this.verifyCount = verifyCount;
        this.constructionTeam = constructionTeam;
        this.applyDepartment = applyDepartment;
        this.applyPersion = applyPersion;
        this.contactPhone = contactPhone;
        this.deliveryAddress = deliveryAddress;
        this.operator = operator;
        this.operateTime = operateTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remark = remark;
        this.upload = upload;
    }

    protected DUMaterialApply(Parcel in) {
        applyNumber = in.readString();
        applyCount = in.readFloat();
        verifyCount = in.readFloat();
        constructionTeam = in.readString();
        applyDepartment = in.readString();
        applyPersion = in.readString();
        contactPhone = in.readString();
        deliveryAddress = in.readString();
        operator = in.readString();
        operateTime = in.readLong();
        longitude = in.readDouble();
        latitude = in.readDouble();
        remark = in.readString();
        upload = in.readInt();
        duMaterial = in.readParcelable(DUMaterial.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(applyNumber);
        dest.writeFloat(applyCount);
        dest.writeFloat(verifyCount);
        dest.writeString(constructionTeam);
        dest.writeString(applyDepartment);
        dest.writeString(applyPersion);
        dest.writeString(contactPhone);
        dest.writeString(deliveryAddress);
        dest.writeString(operator);
        dest.writeLong(operateTime);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(remark);
        dest.writeInt(upload);
        dest.writeParcelable(duMaterial, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DUMaterialApply> CREATOR = new Creator<DUMaterialApply>() {
        @Override
        public DUMaterialApply createFromParcel(Parcel in) {
            return new DUMaterialApply(in);
        }

        @Override
        public DUMaterialApply[] newArray(int size) {
            return new DUMaterialApply[size];
        }
    };

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
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

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    @Bindable
    public float getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(float applyCount) {
        this.applyCount = applyCount;
        notifyPropertyChanged(BR.applyCount);
    }

    public float getVerifyCount() {
        return verifyCount;
    }

    public void setVerifyCount(float verifyCount) {
        this.verifyCount = verifyCount;
    }

    @Bindable
    public String getConstructionTeam() {
        return constructionTeam;
    }

    public void setConstructionTeam(String constructionTeam) {
        this.constructionTeam = constructionTeam;
        notifyPropertyChanged(BR.constructionTeam);
    }

    @Bindable
    public String getApplyDepartment() {
        return applyDepartment;
    }

    public void setApplyDepartment(String applyDepartment) {
        this.applyDepartment = applyDepartment;
        notifyPropertyChanged(BR.applyDepartment);
    }

    @Bindable
    public String getApplyPersion() {
        return applyPersion;
    }

    public void setApplyPersion(String applyPersion) {
        this.applyPersion = applyPersion;
        notifyPropertyChanged(BR.applyPersion);
    }

    @Bindable
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        notifyPropertyChanged(BR.contactPhone);
    }

    @Bindable
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        notifyPropertyChanged(BR.deliveryAddress);
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
}
