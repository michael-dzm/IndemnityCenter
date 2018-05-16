package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 施工方案验收
 * Created by dengzhimin on 2017/7/6.
 */

@Entity(nameInDb = "BZ_CONSTRUCTION_ACCEPT")
public class ConstructionAccept {

    @Id(autoincrement = true)
    @Property(nameInDb = "ACCEPT_ID")
    private Long ACCEPT_ID;//施工方案验收ID

    @Property(nameInDb = "SERVER_ID")
    private Long SERVER_ID;//后台ID

    @NotNull
    @Property(nameInDb = "PROJECT_ID")
    private Long PROJECT_ID;//工程ID

    @NotNull
    @Property(nameInDb = "BUDGET_ID")
    private Long BUDGET_ID;//预算ID

    @Property(nameInDb = "CONSTRUCTION_PROGRAM")
    private String CONSTRUCTION_PROGRAM;//施工方案

    @Property(nameInDb = "CONSTRUCTION_REFORM")
    private String CONSTRUCTION_REFORM;//施工整改内容

    private String OPERATOR;//操作人

    @Property(nameInDb = "OPERATE_TIME")
    private long OPERATE_TIME;//操作时间

    private double LONGITUDE;//经度

    private double LATITUDE;//纬度

    private String REMARK;//备注

    @NotNull
    private int UPLOAD;//上传标志 0:未上传 1:已上传

    @Generated(hash = 745250886)
    public ConstructionAccept(Long ACCEPT_ID, Long SERVER_ID,
            @NotNull Long PROJECT_ID, @NotNull Long BUDGET_ID,
            String CONSTRUCTION_PROGRAM, String CONSTRUCTION_REFORM,
            String OPERATOR, long OPERATE_TIME, double LONGITUDE, double LATITUDE,
            String REMARK, int UPLOAD) {
        this.ACCEPT_ID = ACCEPT_ID;
        this.SERVER_ID = SERVER_ID;
        this.PROJECT_ID = PROJECT_ID;
        this.BUDGET_ID = BUDGET_ID;
        this.CONSTRUCTION_PROGRAM = CONSTRUCTION_PROGRAM;
        this.CONSTRUCTION_REFORM = CONSTRUCTION_REFORM;
        this.OPERATOR = OPERATOR;
        this.OPERATE_TIME = OPERATE_TIME;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
        this.REMARK = REMARK;
        this.UPLOAD = UPLOAD;
    }

    @Generated(hash = 186783662)
    public ConstructionAccept() {
    }

    public Long getACCEPT_ID() {
        return this.ACCEPT_ID;
    }

    public void setACCEPT_ID(Long ACCEPT_ID) {
        this.ACCEPT_ID = ACCEPT_ID;
    }

    public Long getSERVER_ID() {
        return this.SERVER_ID;
    }

    public void setSERVER_ID(Long SERVER_ID) {
        this.SERVER_ID = SERVER_ID;
    }

    public Long getPROJECT_ID() {
        return this.PROJECT_ID;
    }

    public void setPROJECT_ID(Long PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public Long getBUDGET_ID() {
        return this.BUDGET_ID;
    }

    public void setBUDGET_ID(Long BUDGET_ID) {
        this.BUDGET_ID = BUDGET_ID;
    }

    public String getCONSTRUCTION_PROGRAM() {
        return this.CONSTRUCTION_PROGRAM;
    }

    public void setCONSTRUCTION_PROGRAM(String CONSTRUCTION_PROGRAM) {
        this.CONSTRUCTION_PROGRAM = CONSTRUCTION_PROGRAM;
    }

    public String getCONSTRUCTION_REFORM() {
        return this.CONSTRUCTION_REFORM;
    }

    public void setCONSTRUCTION_REFORM(String CONSTRUCTION_REFORM) {
        this.CONSTRUCTION_REFORM = CONSTRUCTION_REFORM;
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
