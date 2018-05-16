package com.sh3h.indemnity.event;

/**
 * Created by dengzhimin on 2017/4/26.
 */

public class BusEvent {

    //检测本地未上传数据
    public static class CheckNoUploadData{
        public CheckNoUploadData(){}
    }

    //上传工程操作
    public static class UploadOperate{
        private boolean isSuccess;
        public UploadOperate(boolean isSuccess){
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }

    //上传工程巡视
    public static class UploadPatrol{
        private boolean isSuccess;
        private int type;
        public UploadPatrol(int type, boolean isSuccess){
            this.isSuccess = isSuccess;
            this.type = type;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public int getType() {
            return type;
        }
    }

    //上传验收
    public static class UploadAccept{
        private boolean isSuccess;

        public UploadAccept(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

    }

    //上传多媒体
    public static class UploadMultiMedia{
        private boolean isSuccess;
        private int successCount;
        private int failedCount;

        public UploadMultiMedia(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public UploadMultiMedia(boolean isSuccess, int successCount, int failedCount) {
            this.isSuccess = isSuccess;
            this.successCount = successCount;
            this.failedCount = failedCount;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public int getSuccessCount() {
            return successCount;
        }

        public void setSuccessCount(int successCount) {
            this.successCount = successCount;
        }

        public int getFailedCount() {
            return failedCount;
        }

        public void setFailedCount(int failedCount) {
            this.failedCount = failedCount;
        }
    }
}
