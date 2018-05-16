package com.sh3h.dataprovider.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 材料配置信息
 * Created by dengzhimin on 2017/3/31.
 */
public class DUMaterial implements Parcelable{

    private Long materialId;//材料ID

    private String materialCategory;//材料类别

    private String materialName;//材料名

    private String materialFormat;//材料规格

    private String materialUnit;//材料单位

    private float materialPrice;//材料价格

    private float upperLimit;//领用上限

    private int upperLimitType;//上限类型 按比例 按个数

    private float multiple;//领用倍数

    private String remark;//备注

    public DUMaterial(){}

    public DUMaterial(Long materialId, String materialCategory, String materialName, String materialFormat, String materialUnit, float materialPrice,
                      float upperLimit, int upperLimitType, float multiple, String remark) {
        this.materialId = materialId;
        this.materialCategory = materialCategory;
        this.materialName = materialName;
        this.materialFormat = materialFormat;
        this.materialUnit = materialUnit;
        this.materialPrice = materialPrice;
        this.upperLimit = upperLimit;
        this.upperLimitType = upperLimitType;
        this.multiple = multiple;
        this.remark = remark;
    }

    protected DUMaterial(Parcel in) {
        materialCategory = in.readString();
        materialName = in.readString();
        materialFormat = in.readString();
        materialUnit = in.readString();
        materialPrice = in.readFloat();
        upperLimit = in.readFloat();
        upperLimitType = in.readInt();
        multiple = in.readFloat();
        remark = in.readString();
    }

    public static final Creator<DUMaterial> CREATOR = new Creator<DUMaterial>() {
        @Override
        public DUMaterial createFromParcel(Parcel in) {
            return new DUMaterial(in);
        }

        @Override
        public DUMaterial[] newArray(int size) {
            return new DUMaterial[size];
        }
    };

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialFormat() {
        return materialFormat;
    }

    public void setMaterialFormat(String materialFormat) {
        this.materialFormat = materialFormat;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public float getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(float materialPrice) {
        this.materialPrice = materialPrice;
    }

    public float getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(float upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getUpperLimitType() {
        return upperLimitType;
    }

    public void setUpperLimitType(int upperLimitType) {
        this.upperLimitType = upperLimitType;
    }

    public float getMultiple() {
        return multiple;
    }

    public void setMultiple(float multiple) {
        this.multiple = multiple;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(materialCategory);
        dest.writeString(materialName);
        dest.writeString(materialFormat);
        dest.writeString(materialUnit);
        dest.writeFloat(materialPrice);
        dest.writeFloat(upperLimit);
        dest.writeInt(upperLimitType);
        dest.writeFloat(multiple);
        dest.writeString(remark);
    }
}
