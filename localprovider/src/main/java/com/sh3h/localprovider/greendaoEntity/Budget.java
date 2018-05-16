package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 预算信息
 * Created by dengzhimin on 2017/3/29.
 */

@Entity(nameInDb = "BZ_BUDGET")
public class Budget {

    @Id(autoincrement = true)
    @Property(nameInDb = "BUDGET_ID")
    private Long BUDGET_ID;//预算ID

    @NotNull
    @Property(nameInDb = "PROJECT_ID")
    private Long PROJECT_ID;//工程ID

    @Property(nameInDb = "BUDGET_NUMBER")
    private String BUDGET_NUMBER;//预算编号

    @Property(nameInDb = "BUDGET_NAME")
    private String BUDGET_NAME;//预算名称

    @Property(nameInDb = "BUDGET_STATE")
    private int BUDGET_STATE;//预算状态 1未验收 2支验不合格 3支验合格 4工验不合格 5工验合格

    @Property(nameInDb = "STATION_PILE_COUNT")
    private int STATION_PILE_COUNT;//站桩数量

    @Property(nameInDb = "USER_COUNT")
    private int USER_COUNT;//用户数量

    @Generated(hash = 899327375)
    public Budget(Long BUDGET_ID, @NotNull Long PROJECT_ID, String BUDGET_NUMBER,
            String BUDGET_NAME, int BUDGET_STATE, int STATION_PILE_COUNT,
            int USER_COUNT) {
        this.BUDGET_ID = BUDGET_ID;
        this.PROJECT_ID = PROJECT_ID;
        this.BUDGET_NUMBER = BUDGET_NUMBER;
        this.BUDGET_NAME = BUDGET_NAME;
        this.BUDGET_STATE = BUDGET_STATE;
        this.STATION_PILE_COUNT = STATION_PILE_COUNT;
        this.USER_COUNT = USER_COUNT;
    }

    @Generated(hash = 1734026453)
    public Budget() {
    }

    public Long getBUDGET_ID() {
        return this.BUDGET_ID;
    }

    public void setBUDGET_ID(Long BUDGET_ID) {
        this.BUDGET_ID = BUDGET_ID;
    }

    public Long getPROJECT_ID() {
        return this.PROJECT_ID;
    }

    public void setPROJECT_ID(Long PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public String getBUDGET_NUMBER() {
        return this.BUDGET_NUMBER;
    }

    public void setBUDGET_NUMBER(String BUDGET_NUMBER) {
        this.BUDGET_NUMBER = BUDGET_NUMBER;
    }

    public String getBUDGET_NAME() {
        return this.BUDGET_NAME;
    }

    public void setBUDGET_NAME(String BUDGET_NAME) {
        this.BUDGET_NAME = BUDGET_NAME;
    }

    public int getBUDGET_STATE() {
        return this.BUDGET_STATE;
    }

    public void setBUDGET_STATE(int BUDGET_STATE) {
        this.BUDGET_STATE = BUDGET_STATE;
    }

    public int getSTATION_PILE_COUNT() {
        return this.STATION_PILE_COUNT;
    }

    public void setSTATION_PILE_COUNT(int STATION_PILE_COUNT) {
        this.STATION_PILE_COUNT = STATION_PILE_COUNT;
    }

    public int getUSER_COUNT() {
        return this.USER_COUNT;
    }

    public void setUSER_COUNT(int USER_COUNT) {
        this.USER_COUNT = USER_COUNT;
    }

}
