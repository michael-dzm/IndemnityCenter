package com.sh3h.dataprovider.data.entity;

/**
 * 工程验收信息
 * Created by dengzhimin on 2017/3/31.
 */

public class DUProjectAccept {

    private Long acceptId;//工程验收ID

    private Long serverId;//后台ID

    private Long projectId;//工程ID

    private Long budgetId;//预算ID

    private int acceptResult;//验收结果

    private String operator;//操作人

    private long operateTime;//操作时间

    private double longitude;//经度

    private double latitude;//纬度

    private String remark;//备注

    private int upload;//上传标志 0:未上传 1:已上传)

    public DUProjectAccept(){}

    public DUProjectAccept(Long acceptId, Long serverId, Long projectId, Long budgetId, int acceptResult, String operator, long operateTime,
                           double longitude, double latitude, String remark, int upload) {
        this.acceptId = acceptId;
        this.serverId = serverId;
        this.projectId = projectId;
        this.budgetId = budgetId;
        this.acceptResult = acceptResult;
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

    public int getAcceptResult() {
        return acceptResult;
    }

    public void setAcceptResult(int acceptResult) {
        this.acceptResult = acceptResult;
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
