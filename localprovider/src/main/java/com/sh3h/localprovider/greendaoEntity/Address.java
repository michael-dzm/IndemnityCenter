package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 地址配置信息
 * Created by dengzhimin on 2017/3/29.
 */

@Entity(nameInDb = "BZ_ADDRESS")
public class Address {

    @Id(autoincrement = true)
    @Property(nameInDb = "ADDRESS_ID")
    private Long ADDRESS_ID;//地址ID

    @Property(nameInDb = "SERVER_ID")
    private Long SERVER_ID;//后台ID

    @Property(nameInDb = "USER_ID")
    private Long USER_ID;//用户ID

    @Property(nameInDb = "ADDRESS_CONTENT")
    private String ADDRESS_CONTENT;//地址内容

    private int DEFAULT;//是否默认 0非默认 1默认

    @Property(nameInDb = "OPERATE_TIME")
    private long OPERATE_TIME;//操作时间

    @NotNull
    private int UPLOAD;//上传标志 0:未上传 1:已上传 下载的地址数据默认已上传

    @Generated(hash = 653771182)
    public Address(Long ADDRESS_ID, Long SERVER_ID, Long USER_ID,
            String ADDRESS_CONTENT, int DEFAULT, long OPERATE_TIME, int UPLOAD) {
        this.ADDRESS_ID = ADDRESS_ID;
        this.SERVER_ID = SERVER_ID;
        this.USER_ID = USER_ID;
        this.ADDRESS_CONTENT = ADDRESS_CONTENT;
        this.DEFAULT = DEFAULT;
        this.OPERATE_TIME = OPERATE_TIME;
        this.UPLOAD = UPLOAD;
    }

    @Generated(hash = 388317431)
    public Address() {
    }

    public Long getADDRESS_ID() {
        return this.ADDRESS_ID;
    }

    public void setADDRESS_ID(Long ADDRESS_ID) {
        this.ADDRESS_ID = ADDRESS_ID;
    }

    public Long getSERVER_ID() {
        return this.SERVER_ID;
    }

    public void setSERVER_ID(Long SERVER_ID) {
        this.SERVER_ID = SERVER_ID;
    }

    public Long getUSER_ID() {
        return this.USER_ID;
    }

    public void setUSER_ID(Long USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getADDRESS_CONTENT() {
        return this.ADDRESS_CONTENT;
    }

    public void setADDRESS_CONTENT(String ADDRESS_CONTENT) {
        this.ADDRESS_CONTENT = ADDRESS_CONTENT;
    }

    public int getDEFAULT() {
        return this.DEFAULT;
    }

    public void setDEFAULT(int DEFAULT) {
        this.DEFAULT = DEFAULT;
    }

    public long getOPERATE_TIME() {
        return this.OPERATE_TIME;
    }

    public void setOPERATE_TIME(long OPERATE_TIME) {
        this.OPERATE_TIME = OPERATE_TIME;
    }

    public int getUPLOAD() {
        return this.UPLOAD;
    }

    public void setUPLOAD(int UPLOAD) {
        this.UPLOAD = UPLOAD;
    }

}
