package com.sh3h.dataprovider.data.entity;

/**
 * 词语信息
 * Created by dengzhimin on 2017/3/31.
 */

public class DUWord {

    private Long wordId;//词语ID

    private Long parentId;//父级编号

    private String group;//词语组

    private String name;//词语文本

    private String value;//词语值

    private String remark;//备注

    public DUWord(){}

    public DUWord(Long wordId, Long parentId, String group, String name, String value, String remark) {
        this.wordId = wordId;
        this.parentId = parentId;
        this.group = group;
        this.name = name;
        this.value = value;
        this.remark = remark;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
