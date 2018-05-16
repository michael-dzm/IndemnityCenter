package com.sh3h.dataprovider.data.entity;

/**
 * 违规配置信息表
 * Created by dengzhimin on 2017/3/31.
 */
public class DUViolation {

    private Long violationId;//违规ID

    private Long violationNumber;//违规编号

    private String violationType;//违规类型

    private String violationContent;//违规内容

    private String remark;//备注

    public DUViolation(){}

    public DUViolation(Long violationId, Long violationNumber, String violationType, String violationContent, String remark) {
        this.violationId = violationId;
        this.violationNumber = violationNumber;
        this.violationType = violationType;
        this.violationContent = violationContent;
        this.remark = remark;
    }

    public Long getViolationId() {
        return violationId;
    }

    public void setViolationId(Long violationId) {
        this.violationId = violationId;
    }

    public Long getViolationNumber() {
        return violationNumber;
    }

    public void setViolationNumber(Long violationNumber) {
        this.violationNumber = violationNumber;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
