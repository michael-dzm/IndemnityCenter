package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 材料配置信息
 * Created by dengzhimin on 2017/3/28.
 */
@Entity(nameInDb = "BZ_MATERIAL")
public class Material {

    @Id(autoincrement = true)
    @Property(nameInDb = "MATERIAL_ID")
    private Long MATERIAL_ID;//材料ID

    @Property(nameInDb = "MATERIAL_CATEGORY")
    private String MATERIAL_CATEGORY;//材料类别

    @Property(nameInDb = "MATERIAL_NAME")
    private String MATERIAL_NAME;//材料名

    @Property(nameInDb = "MATERIAL_FORMAT")
    private String MATERIAL_FORMAT;//材料规格

    @Property(nameInDb = "MATERIAL_UNIT")
    private String MATERIAL_UNIT;//材料单位

    @Property(nameInDb = "MATERIAL_PRICE")
    private float MATERIAL_PRICE;//材料价格

    @Property(nameInDb = "UPPER_LIMIT")
    private float UPPER_LIMIT;//领用上限

    @Property(nameInDb = "UPPER_LIMIT_TYPE")
    private int UPPER_LIMIT_TYPE;//上限类型 按比例 按个数

    private float MULTIPLE;//领用倍数

    private String REMARK;//备注

    @Generated(hash = 259377795)
    public Material(Long MATERIAL_ID, String MATERIAL_CATEGORY,
            String MATERIAL_NAME, String MATERIAL_FORMAT, String MATERIAL_UNIT,
            float MATERIAL_PRICE, float UPPER_LIMIT, int UPPER_LIMIT_TYPE,
            float MULTIPLE, String REMARK) {
        this.MATERIAL_ID = MATERIAL_ID;
        this.MATERIAL_CATEGORY = MATERIAL_CATEGORY;
        this.MATERIAL_NAME = MATERIAL_NAME;
        this.MATERIAL_FORMAT = MATERIAL_FORMAT;
        this.MATERIAL_UNIT = MATERIAL_UNIT;
        this.MATERIAL_PRICE = MATERIAL_PRICE;
        this.UPPER_LIMIT = UPPER_LIMIT;
        this.UPPER_LIMIT_TYPE = UPPER_LIMIT_TYPE;
        this.MULTIPLE = MULTIPLE;
        this.REMARK = REMARK;
    }

    @Generated(hash = 1176792654)
    public Material() {
    }

    public Long getMATERIAL_ID() {
        return this.MATERIAL_ID;
    }

    public void setMATERIAL_ID(Long MATERIAL_ID) {
        this.MATERIAL_ID = MATERIAL_ID;
    }

    public String getMATERIAL_CATEGORY() {
        return this.MATERIAL_CATEGORY;
    }

    public void setMATERIAL_CATEGORY(String MATERIAL_CATEGORY) {
        this.MATERIAL_CATEGORY = MATERIAL_CATEGORY;
    }

    public String getMATERIAL_NAME() {
        return this.MATERIAL_NAME;
    }

    public void setMATERIAL_NAME(String MATERIAL_NAME) {
        this.MATERIAL_NAME = MATERIAL_NAME;
    }

    public String getMATERIAL_FORMAT() {
        return this.MATERIAL_FORMAT;
    }

    public void setMATERIAL_FORMAT(String MATERIAL_FORMAT) {
        this.MATERIAL_FORMAT = MATERIAL_FORMAT;
    }

    public String getMATERIAL_UNIT() {
        return this.MATERIAL_UNIT;
    }

    public void setMATERIAL_UNIT(String MATERIAL_UNIT) {
        this.MATERIAL_UNIT = MATERIAL_UNIT;
    }

    public float getMATERIAL_PRICE() {
        return this.MATERIAL_PRICE;
    }

    public void setMATERIAL_PRICE(float MATERIAL_PRICE) {
        this.MATERIAL_PRICE = MATERIAL_PRICE;
    }

    public float getUPPER_LIMIT() {
        return this.UPPER_LIMIT;
    }

    public void setUPPER_LIMIT(float UPPER_LIMIT) {
        this.UPPER_LIMIT = UPPER_LIMIT;
    }

    public int getUPPER_LIMIT_TYPE() {
        return this.UPPER_LIMIT_TYPE;
    }

    public void setUPPER_LIMIT_TYPE(int UPPER_LIMIT_TYPE) {
        this.UPPER_LIMIT_TYPE = UPPER_LIMIT_TYPE;
    }

    public float getMULTIPLE() {
        return this.MULTIPLE;
    }

    public void setMULTIPLE(float MULTIPLE) {
        this.MULTIPLE = MULTIPLE;
    }

    public String getREMARK() {
        return this.REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

}
