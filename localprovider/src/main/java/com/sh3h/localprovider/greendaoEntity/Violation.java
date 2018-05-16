package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 违规配置信息表
 * Created by dengzhimin on 2017/3/28.
 */
@Entity(nameInDb = "BZ_VIOLATION")
public class Violation {

    @Id(autoincrement = true)
    @Property(nameInDb = "VIOLATION_ID")
    private Long VIOLATION_ID;//违规ID

    @Property(nameInDb = "VIOLATION_NUMBER")
    private Long VIOLATION_NUMBER;//违规编号

    @Property(nameInDb = "VIOLATION_TYPE")
    private String VIOLATION_TYPE;//违规类型

    @Property(nameInDb = "VIOLATION_CONTENT")
    private String VIOLATION_CONTENT;//违规内容

    private String REMARK;//备注

    @Generated(hash = 1729234783)
    public Violation(Long VIOLATION_ID, Long VIOLATION_NUMBER,
            String VIOLATION_TYPE, String VIOLATION_CONTENT, String REMARK) {
        this.VIOLATION_ID = VIOLATION_ID;
        this.VIOLATION_NUMBER = VIOLATION_NUMBER;
        this.VIOLATION_TYPE = VIOLATION_TYPE;
        this.VIOLATION_CONTENT = VIOLATION_CONTENT;
        this.REMARK = REMARK;
    }

    @Generated(hash = 1789693990)
    public Violation() {
    }

    public Long getVIOLATION_ID() {
        return this.VIOLATION_ID;
    }

    public void setVIOLATION_ID(Long VIOLATION_ID) {
        this.VIOLATION_ID = VIOLATION_ID;
    }

    public Long getVIOLATION_NUMBER() {
        return this.VIOLATION_NUMBER;
    }

    public void setVIOLATION_NUMBER(Long VIOLATION_NUMBER) {
        this.VIOLATION_NUMBER = VIOLATION_NUMBER;
    }

    public String getVIOLATION_TYPE() {
        return this.VIOLATION_TYPE;
    }

    public void setVIOLATION_TYPE(String VIOLATION_TYPE) {
        this.VIOLATION_TYPE = VIOLATION_TYPE;
    }

    public String getVIOLATION_CONTENT() {
        return this.VIOLATION_CONTENT;
    }

    public void setVIOLATION_CONTENT(String VIOLATION_CONTENT) {
        this.VIOLATION_CONTENT = VIOLATION_CONTENT;
    }

    public String getREMARK() {
        return this.REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

}
