package com.sh3h.dataprovider.data.entity.enums;

/**
 * Created by dengzhimin on 2017/5/10.
 */

public enum Upload {

    CANNOT(-1),//不能上传
    DEFAULT(0),//未上传
    UPLOADED(1),//已上传
    FAILED(2);//上传失败


    private int value;

    Upload(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
