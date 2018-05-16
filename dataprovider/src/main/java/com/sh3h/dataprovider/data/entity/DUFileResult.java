package com.sh3h.dataprovider.data.entity;


public class DUFileResult {
    private DUFile duFile;
    private int percent;

    public DUFileResult() {
    }

    public DUFileResult(DUFile duFile, int percent) {
        this.duFile = duFile;
        this.percent = percent;
    }

    public DUFile getDuFile() {
        return duFile;
    }

    public void setDuFile(DUFile duFile) {
        this.duFile = duFile;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
