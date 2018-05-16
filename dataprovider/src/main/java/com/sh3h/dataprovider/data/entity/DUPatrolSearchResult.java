package com.sh3h.dataprovider.data.entity;

/**
 * Created by dengzhimin on 2017/5/16.
 */

public class DUPatrolSearchResult{

    private Long serverId;//后台ID

    private Long projectId;//工程ID

    private String violationType;//违规类型

    private String violationContent;//违规内容

    private String patrolSituation;//巡视情况 1合格 2不合格

    private int patrolType;//巡视类型 1施工队巡视 2保中巡视

    private String operator;//操作人

    private long operateTime;//操作时间

    private double longitude;//经度

    private double latitude;//纬度

    private String remark;//备注

    private String imageUrl;

    public DUPatrolSearchResult(){
    }

    public DUPatrolSearchResult(Long serverId, Long projectId, String violationType, String violationContent, String patrolSituation, int patrolType,
                                String operator, long operateTime, double longitude, double latitude, String remark, String imageUrl) {
        this.serverId = serverId;
        this.projectId = projectId;
        this.violationType = violationType;
        this.violationContent = violationContent;
        this.patrolSituation = patrolSituation;
        this.patrolType = patrolType;
        this.operator = operator;
        this.operateTime = operateTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remark = remark;
        this.imageUrl = imageUrl;
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

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public String getViolationContent() {
        return violationContent;
    }

    public void setViolationContent(String violationContent) {
        this.violationContent = violationContent;
    }

    public String getPatrolSituation() {
        return patrolSituation;
    }

    public void setPatrolSituation(String patrolSituation) {
        this.patrolSituation = patrolSituation;
    }

    public int getPatrolType() {
        return patrolType;
    }

    public void setPatrolType(int patrolType) {
        this.patrolType = patrolType;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
