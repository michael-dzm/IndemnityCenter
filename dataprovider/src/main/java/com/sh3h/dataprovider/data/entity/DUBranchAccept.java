package com.sh3h.dataprovider.data.entity;

/**
 * 支管验收信息
 * Created by dengzhimin on 2017/3/31.
 */

public class DUBranchAccept {
    public static final int ACCEPT_TYPE_STRENGTH = 1;
    public static final int ACCEPT_TYPE_AIRTIGHT = 2;

    private Long acceptId;//支管验收ID

    private Long serverId;//后台ID

    private Long projectId;//工程ID

    private Long budgetId;//预算ID

    private String checkSituation;//检查情况

    private String leakSituation;//泄露情况

    private String leakReform;//泄露整改内容

    private String operator;//操作人

    private long operateTime;//操作时间

    private double longitude;//经度

    private double latitude;//纬度

    private String remark;//备注

    private int upload;//上传标志 0:未上传 1:已上传)

    private int acceptType; //验收类型 1:强度 2:气密

    public DUBranchAccept(){}

    public DUBranchAccept(Long acceptId, Long serverId, Long projectId, Long budgetId, String checkSituation, String leakSituation, String constructionProgram,
                          String leakReform, String constructionReform, String operator, long operateTime, double longitude, double latitude, String remark,
                          int upload,int acceptType) {
        this.acceptId = acceptId;
        this.serverId = serverId;
        this.projectId = projectId;
        this.budgetId = budgetId;
        this.checkSituation = checkSituation;
        this.leakSituation = leakSituation;
        this.leakReform = leakReform;
        this.operator = operator;
        this.operateTime = operateTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remark = remark;
        this.upload = upload;
        this.acceptType = acceptType;
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

    public String getCheckSituation() {
        return checkSituation;
    }

    public void setCheckSituation(String checkSituation) {
        this.checkSituation = checkSituation;
    }

    public String getLeakSituation() {
        return leakSituation;
    }

    public void setLeakSituation(String leakSituation) {
        this.leakSituation = leakSituation;
    }


    public String getLeakReform() {
        return leakReform;
    }

    public void setLeakReform(String leakReform) {
        this.leakReform = leakReform;
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

    public int getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(int acceptType) {
        this.acceptType = acceptType;
    }
}
