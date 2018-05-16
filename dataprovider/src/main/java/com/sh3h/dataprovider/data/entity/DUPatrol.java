package com.sh3h.dataprovider.data.entity;

/**
 * 巡视信息表
 * Created by dengzhimin on 2017/3/31.
 */

public class DUPatrol {

    private Long patrolId;//巡视ID

    private Long serverId;//后台ID

    private Long projectId;//工程ID

    private Long violationNumber;//违规编号

    private String patrolSituation;//巡视情况 1合格 2不合格

    private int patrolType;//巡视类型 1施工队巡视 2保中巡视

    private String operator;//操作人

    private long operateTime;//操作时间

    private double longitude;//经度

    private double latitude;//纬度

    private String remark;//备注

    private int upload;//上传标志 0:未上传 1:已上传)

    public enum Type{
        DAILY_PATROL(1, "日常巡视"),//日常巡视
        INDEMNITY_CENTER_PATROL(2, "保中巡视");//保中巡视

        private int value;
        private String name;

        Type(int value, String name){
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

    public DUPatrol(){}

    public DUPatrol(Long patrolId, Long serverId, Long projectId, Long violationNumber, String patrolSituation, int patrolType, String operator,
                    long operateTime, double longitude, double latitude, String remark, int upload) {
        this.patrolId = patrolId;
        this.serverId = serverId;
        this.projectId = projectId;
        this.violationNumber = violationNumber;
        this.patrolSituation = patrolSituation;
        this.patrolType = patrolType;
        this.operator = operator;
        this.operateTime = operateTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remark = remark;
        this.upload = upload;
    }

    public Long getPatrolId() {
        return patrolId;
    }

    public void setPatrolId(Long patrolId) {
        this.patrolId = patrolId;
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

    public Long getViolationNumber() {
        return violationNumber;
    }

    public void setViolationNumber(Long violationNumber) {
        this.violationNumber = violationNumber;
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

    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
    }
}
