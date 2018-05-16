package com.sh3h.dataprovider.data.entity;

/**
 * 施工方案验收
 * Created by dengzhimin on 2017/7/11.
 */

public class DUConstructionAccept {

    private Long acceptId;//施工方案验收ID

    private Long serverId;//后台ID

    private Long projectId;//工程ID

    private Long budgetId;//预算ID

    private String constructionProgram;//施工方案

    private String constructionReform;//施工整改内容

    private String operator;//操作人

    private long operateTime;//操作时间

    private double longitude;//经度

    private double latitude;//纬度

    private String remark;//备注

    private int upload;//上传标志 0:未上传 1:已上传)

    public DUConstructionAccept(){}

    public DUConstructionAccept(Long acceptId, Long serverId, Long projectId, Long budgetId, String constructionProgram, String constructionReform,
                                String operator, long operateTime, double longitude, double latitude, String remark, int upload) {
        this.acceptId = acceptId;
        this.serverId = serverId;
        this.projectId = projectId;
        this.budgetId = budgetId;
        this.constructionProgram = constructionProgram;
        this.constructionReform = constructionReform;
        this.operator = operator;
        this.operateTime = operateTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remark = remark;
        this.upload = upload;
    }

    public Long getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Long acceptId) {
        this.acceptId = acceptId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public String getConstructionProgram() {
        return constructionProgram;
    }

    public void setConstructionProgram(String constructionProgram) {
        this.constructionProgram = constructionProgram;
    }

    public String getConstructionReform() {
        return constructionReform;
    }

    public void setConstructionReform(String constructionReform) {
        this.constructionReform = constructionReform;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(long operateTime) {
        this.operateTime = operateTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
    }
}
