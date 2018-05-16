package com.sh3h.dataprovider.data.entity;


public class DUMediaResponse {
    private String url;
    private String fileType;
    private String fileSize;
    private String fileHash;
    private String originName;

    public DUMediaResponse() {
    }

    public DUMediaResponse(String url, String fileType,
                           String fileSize, String fileHash,
                           String originFileName) {
        this.url = url;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileHash = fileHash;
        this.originName = originFileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }
}
