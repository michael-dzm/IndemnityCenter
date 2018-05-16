package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 多媒体
 * Created by dengzhimin on 2017/3/29.
 */

@Entity(nameInDb = "BZ_MULTIMEDIA")
public class MultiMedia {

    @Id(autoincrement = true)
    @Property(nameInDb = "MULTIMEDIA_ID")
    private Long MULTIMEDIA_ID;//多媒体ID

    @Property(nameInDb = "SERVER_ID")
    private Long SERVER_ID;//后台ID

    @NotNull
    @Property(nameInDb = "RELATE_ID")
    private Long RELATE_ID;//关联ID

    @NotNull
    @Property(nameInDb = "RELATE_TYPE")
    private int RELATE_TYPE;//关联类型 1工程操作 2工程巡视 3材料确认 4支管验收 5工程验收

    @NotNull
    @Property(nameInDb = "FILE_TYPE")
    private int FILE_TYPE;//文件类型 1图片 2语音 3视频

    @NotNull
    @Property(nameInDb = "FILE_NAME")
    private String FILE_NAME;//文件名称

    @NotNull
    @Property(nameInDb = "FILE_PATH")
    private String FILE_PATH;//文件地址

    @Property(nameInDb = "FILE_HASH")
    private String FILE_HASH;//文件Hash 文件上传后服务器返回文件的重命名

    @Property(nameInDb = "FILE_URL")
    private String FILE_URL;//文件Url 文件上传后服务器返回文件URL

    @Property(nameInDb = "FILE_TIME")
    private long FILE_TIME;//文件生成时间

    @NotNull
    private int UPLOAD;//上传标志 0:未上传 1:已上传)

    @Generated(hash = 193922781)
    public MultiMedia(Long MULTIMEDIA_ID, Long SERVER_ID, @NotNull Long RELATE_ID,
            int RELATE_TYPE, int FILE_TYPE, @NotNull String FILE_NAME,
            @NotNull String FILE_PATH, String FILE_HASH, String FILE_URL,
            long FILE_TIME, int UPLOAD) {
        this.MULTIMEDIA_ID = MULTIMEDIA_ID;
        this.SERVER_ID = SERVER_ID;
        this.RELATE_ID = RELATE_ID;
        this.RELATE_TYPE = RELATE_TYPE;
        this.FILE_TYPE = FILE_TYPE;
        this.FILE_NAME = FILE_NAME;
        this.FILE_PATH = FILE_PATH;
        this.FILE_HASH = FILE_HASH;
        this.FILE_URL = FILE_URL;
        this.FILE_TIME = FILE_TIME;
        this.UPLOAD = UPLOAD;
    }

    @Generated(hash = 704612124)
    public MultiMedia() {
    }

    public Long getMULTIMEDIA_ID() {
        return this.MULTIMEDIA_ID;
    }

    public void setMULTIMEDIA_ID(Long MULTIMEDIA_ID) {
        this.MULTIMEDIA_ID = MULTIMEDIA_ID;
    }

    public Long getSERVER_ID() {
        return this.SERVER_ID;
    }

    public void setSERVER_ID(Long SERVER_ID) {
        this.SERVER_ID = SERVER_ID;
    }

    public Long getRELATE_ID() {
        return this.RELATE_ID;
    }

    public void setRELATE_ID(Long RELATE_ID) {
        this.RELATE_ID = RELATE_ID;
    }

    public int getRELATE_TYPE() {
        return this.RELATE_TYPE;
    }

    public void setRELATE_TYPE(int RELATE_TYPE) {
        this.RELATE_TYPE = RELATE_TYPE;
    }

    public int getFILE_TYPE() {
        return this.FILE_TYPE;
    }

    public void setFILE_TYPE(int FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    public String getFILE_NAME() {
        return this.FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getFILE_PATH() {
        return this.FILE_PATH;
    }

    public void setFILE_PATH(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    public String getFILE_HASH() {
        return this.FILE_HASH;
    }

    public void setFILE_HASH(String FILE_HASH) {
        this.FILE_HASH = FILE_HASH;
    }

    public String getFILE_URL() {
        return this.FILE_URL;
    }

    public void setFILE_URL(String FILE_URL) {
        this.FILE_URL = FILE_URL;
    }

    public long getFILE_TIME() {
        return this.FILE_TIME;
    }

    public void setFILE_TIME(long FILE_TIME) {
        this.FILE_TIME = FILE_TIME;
    }

    public int getUPLOAD() {
        return this.UPLOAD;
    }

    public void setUPLOAD(int UPLOAD) {
        this.UPLOAD = UPLOAD;
    }

}
