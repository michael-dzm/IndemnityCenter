package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 支管验收信息
 * Created by dengzhimin on 2017/3/29.
 */

@Entity(nameInDb = "BZ_BRANCH_ACCEPT")
public class BranchAccept {

    @Id(autoincrement = true)
    @Property(nameInDb = "ACCEPT_ID")
    private Long ACCEPT_ID;//支管验收ID

    @Property(nameInDb = "SERVER_ID")
    private Long SERVER_ID;//后台ID

    @NotNull
    @Property(nameInDb = "PROJECT_ID")
    private Long PROJECT_ID;//工程ID

    @NotNull
    @Property(nameInDb = "BUDGET_ID")
    private Long BUDGET_ID;//预算ID

    @Property(nameInDb = "CHECK_SITUATION")
    private String CHECK_SITUATION;//检查情况

    @Property(nameInDb = "LEAK_SITUATION")
    private String LEAK_SITUATION;//泄露情况

    @Property(nameInDb = "LEAK_REFORM")
    private String LEAK_REFORM;//泄露整改内容

    private String OPERATOR;//操作人

    @Property(nameInDb = "OPERATE_TIME")
    private long OPERATE_TIME;//操作时间

    private double LONGITUDE;//经度

    private double LATITUDE;//纬度

    private String REMARK;//备注

    @NotNull
    private int UPLOAD;//上传标志 0:未上传 1:已上传)

    private int ACCEPT_TYPE;

    @Generated(hash = 243823113)
    public BranchAccept(Long ACCEPT_ID, Long SERVER_ID, @NotNull Long PROJECT_ID,
            @NotNull Long BUDGET_ID, String CHECK_SITUATION, String LEAK_SITUATION,
            String LEAK_REFORM, String OPERATOR, long OPERATE_TIME,
            double LONGITUDE, double LATITUDE, String REMARK, int UPLOAD,
            int ACCEPT_TYPE) {
        this.ACCEPT_ID = ACCEPT_ID;
        this.SERVER_ID = SERVER_ID;
        this.PROJECT_ID = PROJECT_ID;
        this.BUDGET_ID = BUDGET_ID;
        this.CHECK_SITUATION = CHECK_SITUATION;
        this.LEAK_SITUATION = LEAK_SITUATION;
        this.LEAK_REFORM = LEAK_REFORM;
        this.OPERATOR = OPERATOR;
        this.OPERATE_TIME = OPERATE_TIME;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
        this.REMARK = REMARK;
        this.UPLOAD = UPLOAD;
        this.ACCEPT_TYPE = ACCEPT_TYPE;
    }

    @Generated(hash = 936583486)
    public BranchAccept() {
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

    public String getCHECK_SITUATION() {
        return this.CHECK_SITUATION;
    }

    public void setCHECK_SITUATION(String CHECK_SITUATION) {
        this.CHECK_SITUATION = CHECK_SITUATION;
    }

    public String getLEAK_SITUATION() {
        return this.LEAK_SITUATION;
    }

    public void setLEAK_SITUATION(String LEAK_SITUATION) {
        this.LEAK_SITUATION = LEAK_SITUATION;
    }

    public String getLEAK_REFORM() {
        return this.LEAK_REFORM;
    }

    public void setLEAK_REFORM(String LEAK_REFORM) {
        this.LEAK_REFORM = LEAK_REFORM;
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

    public int getACCEPT_TYPE() {
        return this.ACCEPT_TYPE;
    }

    public void setACCEPT_TYPE(int ACCEPT_TYPE) {
        this.ACCEPT_TYPE = ACCEPT_TYPE;
    }
}
