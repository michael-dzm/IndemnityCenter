package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 巡视信息表
 * Created by dengzhimin on 2017/3/28.
 */

@Entity(nameInDb = "BZ_PATROL")
public class Patrol {

    @Id(autoincrement = true)
    @Property(nameInDb = "PATROL_ID")
    private Long PATROL_ID;//巡视ID

    @Property(nameInDb = "SERVER_ID")
    private Long SERVER_ID;//后台ID

    @NotNull
    @Property(nameInDb = "PROJECT_ID")
    private Long PROJECT_ID;//工程ID

    @NotNull
    @Property(nameInDb = "VIOLATION_NUMBER")
    private Long VIOLATION_NUMBER;//违规编号

    @Property(nameInDb = "PATROL_SITUATION")
    private String PATROL_SITUATION;//巡视情况 1合格 2不合格

    @Property(nameInDb = "PATROL_TYPE")
    private int PATROL_TYPE;//巡视类型 1施工队巡视 2保中巡视

    private String OPERATOR;//操作人

    @Property(nameInDb = "OPERATE_TIME")
    private long OPERATE_TIME;//操作时间

    private double LONGITUDE;//经度

    private double LATITUDE;//纬度

    private String REMARK;//备注

    @NotNull
    private int UPLOAD;//上传标志 0:未上传 1:已上传)

    @Generated(hash = 775015643)
    public Patrol(Long PATROL_ID, Long SERVER_ID, @NotNull Long PROJECT_ID,
            @NotNull Long VIOLATION_NUMBER, String PATROL_SITUATION,
            int PATROL_TYPE, String OPERATOR, long OPERATE_TIME, double LONGITUDE,
            double LATITUDE, String REMARK, int UPLOAD) {
        this.PATROL_ID = PATROL_ID;
        this.SERVER_ID = SERVER_ID;
        this.PROJECT_ID = PROJECT_ID;
        this.VIOLATION_NUMBER = VIOLATION_NUMBER;
        this.PATROL_SITUATION = PATROL_SITUATION;
        this.PATROL_TYPE = PATROL_TYPE;
        this.OPERATOR = OPERATOR;
        this.OPERATE_TIME = OPERATE_TIME;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
        this.REMARK = REMARK;
        this.UPLOAD = UPLOAD;
    }

    @Generated(hash = 743822884)
    public Patrol() {
    }

    public Long getPATROL_ID() {
        return this.PATROL_ID;
    }

    public void setPATROL_ID(Long PATROL_ID) {
        this.PATROL_ID = PATROL_ID;
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

    public Long getVIOLATION_NUMBER() {
        return this.VIOLATION_NUMBER;
    }

    public void setVIOLATION_NUMBER(Long VIOLATION_NUMBER) {
        this.VIOLATION_NUMBER = VIOLATION_NUMBER;
    }

    public String getPATROL_SITUATION() {
        return this.PATROL_SITUATION;
    }

    public void setPATROL_SITUATION(String PATROL_SITUATION) {
        this.PATROL_SITUATION = PATROL_SITUATION;
    }

    public int getPATROL_TYPE() {
        return this.PATROL_TYPE;
    }

    public void setPATROL_TYPE(int PATROL_TYPE) {
        this.PATROL_TYPE = PATROL_TYPE;
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
