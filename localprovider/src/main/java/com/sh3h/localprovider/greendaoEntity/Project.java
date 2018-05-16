package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 工程信息表
 * Created by dengzhimin on 2017/3/28.
 */

@Entity(nameInDb = "BZ_PROJECT")
public class Project {

    @Id(autoincrement = true)
    @Property(nameInDb = "PROJECT_ID")
    private Long PROJECT_ID;//工程ID

    @Property(nameInDb = "PROJECT_NUMBER")
    private String PROJECT_NUMBER;//工程编号

    @Property(nameInDb = "PROJECT_NAME")
    private String PROJECT_NAME;//工程名字

    @Property(nameInDb = "CONSTRUCTION_UNIT")
    private String CONSTRUCTION_UNIT;//施工单位

    @Property(nameInDb = "CONSTRUCTION_TEAM")
    private String CONSTRUCTION_TEAM;//施工队

    private String OFFICE;//办事处

    private String ADDRESS;//街道

    @Property(nameInDb = "START_TIME")
    private long START_TIME;//开工日期

    @Property(nameInDb = "END_TIME")
    private long END_TIME;//完工时间

    private int STATE;//状态 1新建工程 2相关计划已提交 3编制合同 4开工 5停工 6完工 7送审 8确认

    private String REMARK;//备注

    @Generated(hash = 622998621)
    public Project(Long PROJECT_ID, String PROJECT_NUMBER, String PROJECT_NAME,
            String CONSTRUCTION_UNIT, String CONSTRUCTION_TEAM, String OFFICE,
            String ADDRESS, long START_TIME, long END_TIME, int STATE, String REMARK) {
        this.PROJECT_ID = PROJECT_ID;
        this.PROJECT_NUMBER = PROJECT_NUMBER;
        this.PROJECT_NAME = PROJECT_NAME;
        this.CONSTRUCTION_UNIT = CONSTRUCTION_UNIT;
        this.CONSTRUCTION_TEAM = CONSTRUCTION_TEAM;
        this.OFFICE = OFFICE;
        this.ADDRESS = ADDRESS;
        this.START_TIME = START_TIME;
        this.END_TIME = END_TIME;
        this.STATE = STATE;
        this.REMARK = REMARK;
    }

    @Generated(hash = 1767516619)
    public Project() {
    }

    public Long getPROJECT_ID() {
        return this.PROJECT_ID;
    }

    public void setPROJECT_ID(Long PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public String getPROJECT_NUMBER() {
        return this.PROJECT_NUMBER;
    }

    public void setPROJECT_NUMBER(String PROJECT_NUMBER) {
        this.PROJECT_NUMBER = PROJECT_NUMBER;
    }

    public String getPROJECT_NAME() {
        return this.PROJECT_NAME;
    }

    public void setPROJECT_NAME(String PROJECT_NAME) {
        this.PROJECT_NAME = PROJECT_NAME;
    }

    public String getCONSTRUCTION_UNIT() {
        return this.CONSTRUCTION_UNIT;
    }

    public void setCONSTRUCTION_UNIT(String CONSTRUCTION_UNIT) {
        this.CONSTRUCTION_UNIT = CONSTRUCTION_UNIT;
    }

    public String getCONSTRUCTION_TEAM() {
        return this.CONSTRUCTION_TEAM;
    }

    public void setCONSTRUCTION_TEAM(String CONSTRUCTION_TEAM) {
        this.CONSTRUCTION_TEAM = CONSTRUCTION_TEAM;
    }

    public String getOFFICE() {
        return this.OFFICE;
    }

    public void setOFFICE(String OFFICE) {
        this.OFFICE = OFFICE;
    }

    public String getADDRESS() {
        return this.ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public long getSTART_TIME() {
        return this.START_TIME;
    }

    public void setSTART_TIME(long START_TIME) {
        this.START_TIME = START_TIME;
    }

    public long getEND_TIME() {
        return this.END_TIME;
    }

    public void setEND_TIME(long END_TIME) {
        this.END_TIME = END_TIME;
    }

    public int getSTATE() {
        return this.STATE;
    }

    public void setSTATE(int STATE) {
        this.STATE = STATE;
    }

    public String getREMARK() {
        return this.REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
