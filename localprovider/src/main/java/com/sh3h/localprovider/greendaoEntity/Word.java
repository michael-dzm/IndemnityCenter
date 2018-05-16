package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 词语信息
 * Created by dengzhimin on 2017/3/29.
 */

@Entity(nameInDb = "BZ_WORD")
public class Word {

    @Id(autoincrement = true)
    @Property(nameInDb = "WORD_ID")
    private Long WORD_ID;//词语ID

    @Property(nameInDb = "PARENT_ID")
    private Long PARENT_ID;//父级编号

    private String GROUP;//词语组

    private String NAME;//词语文本

    private String VALUE;//词语值

    private String REMARK;//备注

    @Generated(hash = 819202600)
    public Word(Long WORD_ID, Long PARENT_ID, String GROUP, String NAME,
            String VALUE, String REMARK) {
        this.WORD_ID = WORD_ID;
        this.PARENT_ID = PARENT_ID;
        this.GROUP = GROUP;
        this.NAME = NAME;
        this.VALUE = VALUE;
        this.REMARK = REMARK;
    }

    @Generated(hash = 3342184)
    public Word() {
    }

    public Long getWORD_ID() {
        return this.WORD_ID;
    }

    public void setWORD_ID(Long WORD_ID) {
        this.WORD_ID = WORD_ID;
    }

    public Long getPARENT_ID() {
        return this.PARENT_ID;
    }

    public void setPARENT_ID(Long PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public String getGROUP() {
        return this.GROUP;
    }

    public void setGROUP(String GROUP) {
        this.GROUP = GROUP;
    }

    public String getNAME() {
        return this.NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getVALUE() {
        return this.VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getREMARK() {
        return this.REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
