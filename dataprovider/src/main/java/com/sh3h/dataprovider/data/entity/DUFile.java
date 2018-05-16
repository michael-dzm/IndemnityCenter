package com.sh3h.dataprovider.data.entity;


public class DUFile {
    public enum FileType {
        APK,
        DATA
    }

    public static final int DEFAULT_VERSION_CODE = 0;

    private FileType fileType;
    private String appName;
    private String appId;
    private String packageName;
    private int versionCode;
    private String versionName;
    private String url;
    private String path;
    private String role;
    private boolean isExistingLocal;
    private boolean needDownloadOtherFiles;

    public DUFile() {
    }

    public DUFile(FileType fileType, String appName, String appId, String packageName,
                  int versionCode, String versionName, String url, String path,
                  boolean needDownloadOtherFiles) {
        this.fileType = fileType;
        this.appName = appName;
        this.appId = appId;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.url = url;
        this.path = path;
        this.isExistingLocal = false;
        this.needDownloadOtherFiles = needDownloadOtherFiles;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isExistingLocal() {
        return isExistingLocal;
    }

    public void setExistingLocal(boolean existingLocal) {
        isExistingLocal = existingLocal;
    }

    public boolean isNeedDownloadOtherFiles() {
        return needDownloadOtherFiles;
    }

    public void setNeedDownloadOtherFiles(boolean needDownloadOtherFiles) {
        this.needDownloadOtherFiles = needDownloadOtherFiles;
    }
}
