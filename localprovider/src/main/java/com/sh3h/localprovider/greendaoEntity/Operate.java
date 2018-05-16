package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 工程操作表
 * Created by dengzhimin on 2017/3/28.
 */

@Entity(nameInDb = "BZ_OPERATE")
public class Operate {

    @Id(autoincrement = true)
    @Property(nameInDb = "OPERATE_ID")
    private Long OPERATE_ID;//操作ID

    @Property(nameInDb = "SERVER_ID")
    private Long SERVER_ID;//后台ID

    @NotNull
    @Property(nameInDb = "PROJECT_ID")
    private Long PROJECT_ID;//工程ID

    @Property(nameInDb = "OPERATE_TYPE")
    private int OPERATE_TYPE;//操作类型 1开工 2停工 3复工 4完工

    @Property(nameInDb = "START_TIME")
    private long START_TIME;//开工时间

    @Property(nameInDb = "STOP_TIME")
    private long STOP_TIME;//停工时间

    @Property(nameInDb = "RESTART_TIME")
    private long RESTART_TIME;//复工时间

    @Property(nameInDb = "END_TIME")
    private long END_TIME;//完工时间

    private String OPERATOR;//操作人

    @Property(nameInDb = "OPERATE_TIME")
    private long OPERATE_TIME;//操作时间

    private double LONGITUDE;//经度

    private double LATITUDE;//纬度

    private String REMARK;//备注

    @NotNull
    private int UPLOAD;//上传标志 0:未上传 1:已上传

    @Generated(hash = 218411627)
    public Operate(Long OPERATE_ID, Long SERVER_ID, @NotNull Long PROJECT_ID,
            int OPERATE_TYPE, long START_TIME, long STOP_TIME, long RESTART_TIME,
            long END_TIME, String OPERATOR, long OPERATE_TIME, double LONGITUDE,
            double LATITUDE, String REMARK, int UPLOAD) {
        this.OPERATE_ID = OPERATE_ID;
        this.SERVER_ID = SERVER_ID;
        this.PROJECT_ID = PROJECT_ID;
        this.OPERATE_TYPE = OPERATE_TYPE;
        this.START_TIME = START_TIME;
        this.STOP_TIME = STOP_TIME;
        this.RESTART_TIME = RESTART_TIME;
        this.END_TIME = END_TIME;
        this.OPERATOR = OPERATOR;
        this.OPERATE_TIME = OPERATE_TIME;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
        this.REMARK = REMARK;
        this.UPLOAD = UPLOAD;
    }

    @Generated(hash = 774142702)
    public Operate() {
    }

    public Long getOPERATE_ID() {
        return this.OPERATE_ID;
    }

    public void setOPERATE_ID(Long OPERATE_ID) {
        this.OPERATE_ID = OPERATE_ID;
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

    public int getOPERATE_TYPE() {
        return this.OPERATE_TYPE;
    }

    public void setOPERATE_TYPE(int OPERATE_TYPE) {
        this.OPERATE_TYPE = OPERATE_TYPE;
    }

    public long getSTART_TIME() {
        return this.START_TIME;
    }

    public void setSTART_TIME(long START_TIME) {
        this.START_TIME = START_TIME;
    }

    public long getSTOP_TIME() {
        return this.STOP_TIME;
    }

    public void setSTOP_TIME(long STOP_TIME) {
        this.STOP_TIME = STOP_TIME;
    }

    public long getRESTART_TIME() {
        return this.RESTART_TIME;
    }

    public void setRESTART_TIME(long RESTART_TIME) {
        this.RESTART_TIME = RESTART_TIME;
    }

    public long getEND_TIME() {
        return this.END_TIME;
    }

    public void setEND_TIME(long END_TIME) {
        this.END_TIME = END_TIME;
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
