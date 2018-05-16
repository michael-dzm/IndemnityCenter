package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 材料确认信息
 * Created by dengzhimin on 2017/3/29.
 */

@Entity(nameInDb = "BZ_MATERIAL_VERIFY")
public class MaterialVerify {

    @Id(autoincrement = true)
    @Property(nameInDb = "VERIFY_ID")
    private Long VERIFY_ID;//材料确认ID

    @Property(nameInDb = "SERVER_ID")
    private Long SERVER_ID;//后台ID

    @NotNull
    @Property(nameInDb = "MATERIAL_ID")
    private Long MATERIAL_ID;//材料ID

    @Property(nameInDb = "DELIVERY_NUMBER")
    private String DELIVERY_NUMBER;//送货单编号

    @Property(nameInDb = "CONSTRUCTION_TEAM")
    private String CONSTRUCTION_TEAM;//施工队

    @Property(nameInDb = "VERIFY_COUNT")
    private float VERIFY_COUNT;//确认数量

    private String OPERATOR;//操作人

    @Property(nameInDb = "OPERATE_TIME")
    private long OPERATE_TIME;//操作时间

    private double LONGITUDE;//经度

    private double LATITUDE;//纬度

    private String REMARK;//备注

    @NotNull
    private int UPLOAD;//上传标志 0:未上传 1:已上传

    @Generated(hash = 1872535744)
    public MaterialVerify(Long VERIFY_ID, Long SERVER_ID,
            @NotNull Long MATERIAL_ID, String DELIVERY_NUMBER,
            String CONSTRUCTION_TEAM, float VERIFY_COUNT, String OPERATOR,
            long OPERATE_TIME, double LONGITUDE, double LATITUDE, String REMARK,
            int UPLOAD) {
        this.VERIFY_ID = VERIFY_ID;
        this.SERVER_ID = SERVER_ID;
        this.MATERIAL_ID = MATERIAL_ID;
        this.DELIVERY_NUMBER = DELIVERY_NUMBER;
        this.CONSTRUCTION_TEAM = CONSTRUCTION_TEAM;
        this.VERIFY_COUNT = VERIFY_COUNT;
        this.OPERATOR = OPERATOR;
        this.OPERATE_TIME = OPERATE_TIME;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
        this.REMARK = REMARK;
        this.UPLOAD = UPLOAD;
    }

    @Generated(hash = 786319451)
    public MaterialVerify() {
    }

    public Long getVERIFY_ID() {
        return this.VERIFY_ID;
    }

    public void setVERIFY_ID(Long VERIFY_ID) {
        this.VERIFY_ID = VERIFY_ID;
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

    public String getDELIVERY_NUMBER() {
        return this.DELIVERY_NUMBER;
    }

    public void setDELIVERY_NUMBER(String DELIVERY_NUMBER) {
        this.DELIVERY_NUMBER = DELIVERY_NUMBER;
    }

    public String getCONSTRUCTION_TEAM() {
        return this.CONSTRUCTION_TEAM;
    }

    public void setCONSTRUCTION_TEAM(String CONSTRUCTION_TEAM) {
        this.CONSTRUCTION_TEAM = CONSTRUCTION_TEAM;
    }

    public float getVERIFY_COUNT() {
        return this.VERIFY_COUNT;
    }

    public void setVERIFY_COUNT(float VERIFY_COUNT) {
        this.VERIFY_COUNT = VERIFY_COUNT;
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
