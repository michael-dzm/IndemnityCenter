package com.sh3h.dataprovider.data.local.config;


public class UserConfig extends BaseConfig {
    /**
     * 图片采集质量
     */
    public static final String PARAM_SYS_QUALITY_PHOTO = "sys.quality.photo";
    /**
     * 声音采集质量
     */
    public static final String PARAM_SYS_QUALITY_SOUND = "sys.quality.sound";

    /**
     * 主菜单显示方式
     */
    public static final String PARAM_SYS_HOME_STYLE = "sys.display_style";

    /**
     * 新数据类提醒方式
     */
    public static final String PARAM_RING_NEWSYNC = "ring.new_sync";
    /**
     * 警告类提醒方式
     */
    public static final String PARAM_RING_WARNRING = "ring.warning";

    /**
     * 常用抄表状态配置
     */
    public static final String PARAM_CB_DEFAULT_CYCBZT = "cb.default_cycbzt";
    /**
     * 保存自动翻页
     */
    public static final String PARAM_CB_AUTO_GOON = "cb.auto_goon";

    public static final int QUALITY_LOW = 1;
    public static final int QUALITY_MIDDLE = 2;
    public static final int QUALITY_HIGH = 3;

    public static final int HOME_STYLE_LIST = 1;
    public static final int HOME_STYLE_GRID = 2;

    public static final String RING_01 = "ring_01";
    public static final String RING_02 = "ring_02";
    public static final String RING_03 = "ring_03";
    public static final String RING_04 = "ring_04";
    public static final String RING_05 = "ring_05";

    /**
     * 自动
     */
    public static final int CB_GOON_AUTO = 1;
    /**
     * 手动
     */
    public static final int CB_GOON_MANUAL = 2;

    /**
     * 用户编号
     */
    public static final String LOGIN_USERID = "login_userid";

    /**
     * 用户名
     */
    public static final String LOGIN_USERNAME = "login_username";

    /**
     * 简单账号
     */
    public static final String LOGIN_ACCOUNT = "login_account";

    /**
     * 密码
     */
    public static final String LOGIN_PWS = "login_pws";

    /**
     * 手机
     */
    public static final String LOGIN_CELLPHONE = "login_cellphone";

    /**
     * 固定电话
     */
    public static final String LOGIN_PHONE = "login_phone";

    /**
     * 地址
     */
    public static final String LOGIN_ADDRESS = "login_adress";

    public UserConfig() {

    }

    public void loadDefaultConfig() {
        set(PARAM_SYS_QUALITY_PHOTO, QUALITY_MIDDLE);
        set(PARAM_SYS_QUALITY_SOUND, QUALITY_MIDDLE);
        set(PARAM_SYS_HOME_STYLE, HOME_STYLE_GRID);
        set(PARAM_RING_NEWSYNC, RING_01);
        set(PARAM_RING_WARNRING, RING_04);
        set(PARAM_CB_DEFAULT_CYCBZT, "");
        set(PARAM_CB_AUTO_GOON, CB_GOON_MANUAL);
        setRead(true);
    }

    public String getChaoYongPeiZhi(String name){
        return (String) get(name);
    }
}
