package com.sh3h.indemnity.service.action;

/**
 * Created by dengzhimin on 2017/4/26.
 */

public enum SyncAction {

    CHECK_NO_UPLOAD_DATA("检测本地未上传的数据"),
    UPLOAD_ALL_OPERATE("上传所有工程操作数据"),
    UPLOAD_ALL_OPERATE_MULTIMEDIA("上传所有工程操作已上传多媒体未上传的多媒体数据"),
    UPLOAD_ALL_PATROL("上传所有工程巡视数据"),
    UPLOAD_ALL_PATROL_MULTIMEDIA("上传所有工程巡视已上传多媒体未上传的多媒体数据"),
    UPLOAD_ALL_ACCEPT("上传所有工程验收数据"),
    UPLOAD_ALL_ACCEPT_MULTIMEDIA("上传所有工程验收已上传多媒体未上传的多媒体数据");
    private String action;

    SyncAction(String action){
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
