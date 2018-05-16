package com.sh3h.dataprovider.data.entity.response;

/**
 * 信息返回结果
 * Created by xulongjun on 2017/4/6
 */

public class DUReplyResult {

    private boolean isSuccess;//是否成功

    private String message;//

    private Long localId;//本地编号

    private Long serverId;//返回后台id


    public DUReplyResult(boolean isSuccess, String message, Long localId, Long serverId) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.localId = localId;
        this.serverId = serverId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
