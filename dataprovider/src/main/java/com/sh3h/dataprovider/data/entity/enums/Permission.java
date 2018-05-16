package com.sh3h.dataprovider.data.entity.enums;

/**
 * Created by dengzhimin on 2017/5/11.
 */

public enum Permission {

    //施工队
    CONSTRUCTION_WORKER("2170", "施工队人员"),//暂无权限
    CONSTRUCTION_MATERIAL_MANAGER("2171", "材料管理员"),
    CONSTRUCTION_SCENE_MANAGER("2172", "现场负责人"),
    CONSTRUCTION_PROJECT_MANAGER("2173", "项目负责人"),//暂无权限
    //保障中心
    INDEMNITY_OPERATOR("2174", "保障中心操作员"),
    INDEMNITY_MANAGER("2175", "保障中心管理员"),
    INDEMNITY_APP_OPERATOR("2176", "保障中心APP操作员");

    private String value;
    private String name;

    Permission(String value, String name){
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
