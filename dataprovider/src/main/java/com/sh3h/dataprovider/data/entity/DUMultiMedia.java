package com.sh3h.dataprovider.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 多媒体
 * Created by dengzhimin on 2017/3/31.
 */

public class DUMultiMedia implements Parcelable{
    private Long multimediaId;//多媒体ID

    private Long serverId;//后台ID

    private Long relateId;//关联ID

    private int relateType;//关联类型

    private int fileType;//文件类型 1图片 2语音 3视频

    private String fileName;//文件名称

    private String filePath;//文件地址

    private String fileHash;//文件Hash 文件上传后服务器返回文件的重命名

    private String fileUrl;//文件Url 文件上传后服务器返回文件URL

    private long fileTime;//文件创建时间

    private int upload;//上传标志 0:未上传 1:已上传)

    protected DUMultiMedia(Parcel in) {
        relateType = in.readInt();
        fileType = in.readInt();
        fileName = in.readString();
        filePath = in.readString();
        fileHash = in.readString();
        fileUrl = in.readString();
        fileTime = in.readLong();
        upload = in.readInt();
    }

    public static final Creator<DUMultiMedia> CREATOR = new Creator<DUMultiMedia>() {
        @Override
        public DUMultiMedia createFromParcel(Parcel in) {
            return new DUMultiMedia(in);
        }

        @Override
        public DUMultiMedia[] newArray(int size) {
            return new DUMultiMedia[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(relateType);
        dest.writeInt(fileType);
        dest.writeString(fileName);
        dest.writeString(filePath);
        dest.writeString(fileHash);
        dest.writeString(fileUrl);
        dest.writeLong(fileTime);
        dest.writeInt(upload);
    }

    public enum RelateType{
        STOP_PROJECT(11, "停工"),
        RESTART_PROJECT(12, "复工"),
        START_PROJECT_CARD(13, "施工卡"),
        START_PROJECT_ORDER(14, "开工单"),
        START_PROJECT_NAMEPLATE(15, "现场铭牌"),
        START_PROJECT_FACILITY(16, "安保设施"),
        START_PROJECT_OTHER(17, "其他"), //11~19
        DAILY_PATROL(21, "日常巡视"),
        CENTER_PATROL(22, "保中巡视"),//21~29
        STRENGTH_ACCEPT(31, "强度验收"),
        AIRTIGHT_ACCEPT(32, "气密验收"),
        PROJECT_ACCEPT(33, "工程验收"),//31~39
        CONSTRUCTION_ACCEPT(34, "施工方案验收"),
        MATERIAL_VERIFY(41, "材料确认");//41~49

        private int value;
        private String name;

        RelateType(int value, String name){
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

    public enum FileType{
        IMAGE(1, "图片"),//图片
        AUDIO(2, "音频"),//音频
        VIDEO(3, "视频");//视频

        private int value;
        private String name;

        FileType(int value, String name){
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

    public DUMultiMedia(){}

    public DUMultiMedia(String fileName, String filePath){
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public DUMultiMedia(Long multimediaId, Long serverId, Long relateId, int relateType, int fileType, String fileName, String filePath,
                        String fileHash, String fileUrl, long fileTime, int upload) {
        this.multimediaId = multimediaId;
        this.serverId = serverId;
        this.relateId = relateId;
        this.relateType = relateType;
        this.fileType = fileType;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileHash = fileHash;
        this.fileUrl = fileUrl;
        this.fileTime = fileTime;
        this.upload = upload;
    }

    public Long getMultimediaId() {
        return multimediaId;
    }

    public void setMultimediaId(Long multimediaId) {
        this.multimediaId = multimediaId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public int getRelateType() {
        return relateType;
    }

    public void setRelateType(int relateType) {
        this.relateType = relateType;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public long getFileTime() {
        return fileTime;
    }

    public void setFileTime(long fileTime) {
        this.fileTime = fileTime;
    }

    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
    }
}
