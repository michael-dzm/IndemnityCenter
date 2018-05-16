package com.sh3h.dataprovider.data.entity;

import java.io.Serializable;

/**
 * 预算信息
 * Created by dengzhimin on 2017/3/31.
 */

public class DUBudget implements Serializable {

    private Long budgetId;//预算ID

    private Long projectId;//工程ID

    private String budgetNumber;//预算编号

    private String budgetName;//预算名称

    private int budgetState;//预算状态 0未验收  2强度合格 3气密合格 4气密不合格 5验收不合格 6验收合格

    private int stationPileCount;//站桩数量

    private int userCount;//用户数量

    public enum State{

        DEFAULT(0, "未验收"),
        CONSTRUCTION_UNQUALIFIED(1, "施工方案验收不合格"),
        CONSTRUCTION_QUALIFIED(2, "施工方案验收合格"),
        STRENGTH_UNQUALIFIED(3, "强度验收不合格"),
        STRENGTH_QUALIFIED(4, "强度验收合格"),
        AIRTIGHT_UNQUALIFIED(5, "气密验收不合格"),
        AIRTIGHT_QUALIFIED(6, "气密验收合格"),
        PROJECT_UNQUALIFIED(7, "工程验收不合格"),
        PROJECT_QUALIFIED(8, "工程验收合格");

        private int value;
        private String name;

        State(int value, String name){
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public DUBudget() {
    }

    public DUBudget(Long budgetId, Long projectId, String budgetNumber, String budgetName, int budgetState, int stationPileCount, int userCount) {
        this.budgetId = budgetId;
        this.projectId = projectId;
        this.budgetNumber = budgetNumber;
        this.budgetName = budgetName;
        this.budgetState = budgetState;
        this.stationPileCount = stationPileCount;
        this.userCount = userCount;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getBudgetNumber() {
        return budgetNumber;
    }

    public void setBudgetNumber(String budgetNumber) {
        this.budgetNumber = budgetNumber;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public int getBudgetState() {
        return budgetState;
    }

    public void setBudgetState(int budgetState) {
        this.budgetState = budgetState;
    }

    public int getStationPileCount() {
        return stationPileCount;
    }

    public void setStationPileCount(int stationPileCount) {
        this.stationPileCount = stationPileCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
}
