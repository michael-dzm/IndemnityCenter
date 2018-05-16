package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 材料申领信息
 * Created by dengzhimin on 2017/3/29.
 */

@Entity(nameInDb = "BZ_MATERIAL_APPLY")
public class MaterialApply {

    @Id(autoincrement = true)
    @Property(nameInDb = "APPLY_ID")
    private Long APPLY_ID;//申领ID

    @Property(nameInDb = "SERVER_ID")
    private Long SERVER_ID;//后台ID

    @NotNull
    @Property(nameInDb = "MATERIAL_ID")
    private Long MATERIAL_ID;//材料ID

    @Property(nameInDb = "APPLY_NUMBER")
    private String APPLY_NUMBER;//申领编号

    @Property(nameInDb = "APPLY_COUNT")
    private float APPLY_COUNT;//申领数量

    @Property(nameInDb = "VERIFY_COUNT")
    private float VERIFY_COUNT;//确认入库数量

    @Property(nameInDb = "CONSTRUCTION_TEAM")
    private String CONSTRUCTION_TEAM;//施工队

    @Property(nameInDb = "APPLY_DEPARTMENT")
    private String APPLY_DEPARTMENT;//领用部门

    @Property(nameInDb = "APPLY_PERSION")
    private String APPLY_PERSION;//领用人

    @Property(nameInDb = "CONTACT_PHONE")
    private String CONTACT_PHONE;//联系电话

    @Property(nameInDb = "DELIVERY_ADDRESS")
    private String DELIVERY_ADDRESS;//送货地址

    private String OPERATOR;//操作人

    @Property(nameInDb = "OPERATE_TIME")
    private long OPERATE_TIME;//操作时间

    private double LONGITUDE;//经度

    private double LATITUDE;//纬度

    private String REMARK;//备注

    @NotNull
    private int UPLOAD;//上传标志 0:未上传 1:已上传)

    @Generated(hash = 1012103831)
    public MaterialApply(Long APPLY_ID, Long SERVER_ID, @NotNull Long MATERIAL_ID,
            String APPLY_NUMBER, float APPLY_COUNT, float VERIFY_COUNT,
            String CONSTRUCTION_TEAM, String APPLY_DEPARTMENT,
            String APPLY_PERSION, String CONTACT_PHONE, String DELIVERY_ADDRESS,
            String OPERATOR, long OPERATE_TIME, double LONGITUDE, double LATITUDE,
            String REMARK, int UPLOAD) {
        this.APPLY_ID = APPLY_ID;
        this.SERVER_ID = SERVER_ID;
        this.MATERIAL_ID = MATERIAL_ID;
        this.APPLY_NUMBER = APPLY_NUMBER;
        this.APPLY_COUNT = APPLY_COUNT;
        this.VERIFY_COUNT = VERIFY_COUNT;
        this.CONSTRUCTION_TEAM = CONSTRUCTION_TEAM;
        this.APPLY_DEPARTMENT = APPLY_DEPARTMENT;
        this.APPLY_PERSION = APPLY_PERSION;
        this.CONTACT_PHONE = CONTACT_PHONE;
        this.DELIVERY_ADDRESS = DELIVERY_ADDRESS;
        this.OPERATOR = OPERATOR;
        this.OPERATE_TIME = OPERATE_TIME;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
        this.REMARK = REMARK;
        this.UPLOAD = UPLOAD;
    }

    @Generated(hash = 298145240)
    public MaterialApply() {
    }

    public Long getAPPLY_ID() {
        return this.APPLY_ID;
    }

    public void setAPPLY_ID(Long APPLY_ID) {
        this.APPLY_ID = APPLY_ID;
    }

    public Long getSERVER_ID() {
        return this.SERVER_ID;
    }

    public void setSERVER_ID(Long SERVER_ID) {
        this.SERVER_ID = SERVER_ID;
    }

    public Long getMATERIAL_ID() {
        return this.MATERIAL_ID;
    }

    public void setMATERIAL_ID(Long MATERIAL_ID) {
        this.MATERIAL_ID = MATERIAL_ID;
    }

    public String getAPPLY_NUMBER() {
        return this.APPLY_NUMBER;
    }

    public void setAPPLY_NUMBER(String APPLY_NUMBER) {
        this.APPLY_NUMBER = APPLY_NUMBER;
    }

    public float getAPPLY_COUNT() {
        return this.APPLY_COUNT;
    }

    public void setAPPLY_COUNT(float APPLY_COUNT) {
        this.APPLY_COUNT = APPLY_COUNT;
    }

    public float getVERIFY_COUNT() {
        return this.VERIFY_COUNT;
    }

    public void setVERIFY_COUNT(float VERIFY_COUNT) {
        this.VERIFY_COUNT = VERIFY_COUNT;
    }

    public String getCONSTRUCTION_TEAM() {
        return this.CONSTRUCTION_TEAM;
    }

    public void setCONSTRUCTION_TEAM(String CONSTRUCTION_TEAM) {
        this.CONSTRUCTION_TEAM = CONSTRUCTION_TEAM;
    }

    public String getAPPLY_DEPARTMENT() {
        return this.APPLY_DEPARTMENT;
    }

    public void setAPPLY_DEPARTMENT(String APPLY_DEPARTMENT) {
        this.APPLY_DEPARTMENT = APPLY_DEPARTMENT;
    }

    public String getAPPLY_PERSION() {
        return this.APPLY_PERSION;
    }

    public void setAPPLY_PERSION(String APPLY_PERSION) {
        this.APPLY_PERSION = APPLY_PERSION;
    }

    public String getCONTACT_PHONE() {
        return this.CONTACT_PHONE;
    }

    public void setCONTACT_PHONE(String CONTACT_PHONE) {
        this.CONTACT_PHONE = CONTACT_PHONE;
    }

    public String getDELIVERY_ADDRESS() {
        return this.DELIVERY_ADDRESS;
    }

    public void setDELIVERY_ADDRESS(String DELIVERY_ADDRESS) {
        this.DELIVERY_ADDRESS = DELIVERY_ADDRESS;
    }

    public String getOPERATOR() {
        return this.OPERATOR;
    }

    public void setOPERATOR(String OPERATOR) {
        this.OPERATOR = OPERATOR;
    }

    public long getOPERATE_TIME() {
        return this.OPERATE_TIME;
    }

    public void setOPERATE_TIME(long OPERATE_TIME) {
        this.OPERATE_TIME = OPERATE_TIME;
    }

    public double getLONGITUDE() {
        return this.LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public double getLATITUDE() {
        return this.LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getREMARK() {
        return this.REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public int getUPLOAD() {
        return this.UPLOAD;
    }

    public void setUPLOAD(int UPLOAD) {
        this.UPLOAD = UPLOAD;
    }

}
