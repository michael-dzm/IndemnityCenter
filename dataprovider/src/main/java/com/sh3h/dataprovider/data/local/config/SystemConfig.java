package com.sh3h.dataprovider.data.local.config;


public class SystemConfig extends BaseConfig {

    public enum JineAutoCacluate {
        /**
         * 自动
         */
        auto,
        /**
         * 手动
         */
        manual,
        /**
         * 禁用，不计算
         */
        disable
    }

    /**
     * gps定位方式
     */
    public static final String PARAM_SYST_GPS_TYPE = "sys.gps.type";
    public static final String GPS_TYPE_NONE = "none";
    public static final String GPS_TYPE_SYSTEM = "system";
    public static final String GPS_TYPE_BAIDU = "baidu";

    /**
     * 数据后台地址
     */
    public static final String PARAM_SERVER_BASE_URI = "server.baseuri";
    /**
     * 调试模式
     */
    public static final String PARAM_SYS_IS_DEBUG_MODE = "sys.is_debug_mode";
    /**
     * 分页大小
     */
    public static final String PARAM_LIST_PAGE_COUNT = "sys.list_page_size";
    /**
     * 呼吸包发送频率
     */
    public static final String PARAM_KEEP_LIVE_INTERVAL = "sys.keepalive_interval";
    public static final int KEEP_LIVE_INTERVAL_DEFAULT_VALUE = 300000;
    /**
     * 估表状态
     */
    public static final String PARAM_CB_GUBIAO = "cb.gubiaozt";
    /**
     * 金额是否自动计算
     */
    public static final String PARAM_CB_AUTO_CALC_JINE = "cb.auto_calc_jine";
    /**
     * 是否输入量高量低原因
     */
    public static final String PARAM_CB_INPUT_LGLDYY = "cb.need_lgldyy";
    /**
     * 是否允许2G/3G方式下载抄表数据
     */
    public static final String PARAM_NET_ALLOW_EDGED_OWNLOAD = "net.allow_edged_ownload";
    /**
     * 是否保存后自动上传抄表数据
     */
    public static final String PARAM_CB_AUTO_UPLOAD = "cb.auto_upload";
    /**
     * 是否允许WIFI自动下载抄表数据
     */
    public static final String PARAM_NET_ALLOW_AUTO_DOWNLOAD_WIFI_ENABLED = "net.allow_auto_download_wifi_enabled";
    /**
     * 是否显示垃圾费
     */
    public static final String KG_SHOW_LAJIFEI = "kg.show_lajifei";
    /**
     * 是否显示垃圾费系数
     */
    public static final String KG_SHOW_LAJIFEIXISHU = "kg.show_lajifeixishu";
    /**
     * 是否显示定额加价
     */
    public static final String KG_SHOW_DINGEJIAJIA = "kg.show_dingejiajia";
    /**
     * 是否显示违约金
     */
    public static final String KG_SHOW_WEIYUEJIN = "kg.show_weiyuejin";
    /**
     * 是否显示水表倍率
     */
    public static final String KG_SHOW_SHUIBIAOBEILV = "kg.show_shuibiaobeilv";
    /**
     * 推送后台地址
     */
    public static final String PARAM_BROKER_URL = "broker.url";

    /**
     * 抄表状态名称是否增加PC快捷键前缀
     */
    public static final String CB_ISADD_PC_KUAIJIEJIAN_PREFIX = "cb.isadd_pc_kuaijiejian_prefix";
    /**
     * 是否显示连续估表次数
     */
    public static final String CX_ISSHOW_LIANXUGUBIAOCS = "cx.show_lianxugubiaocs";
    /**
     * 是否显示连续量高量低次数
     */
    public static final String CX_ISSHOW_LIANXULIANGGAOLDCS = "cx.show_lianxulianggaoldcs";

    /**
     * 人口水量（阶梯1）
     */
    public static final String JIETIJB1_RENKOUSL = "jietijb1.renkousl";
    /**
     * 人口水量（阶梯2）
     */
    public static final String JIETIJB2_RENKOUSL = "jietijb2.renkousl";
    /**
     * 人口水量（阶梯3）
     */
    public static final String JIETIJB3_RENKOUSL = "jietijb3.renkousl";

    /**
     * 抄表程序 or 工单程序
     */
    public static final String PARAM_SYS_IS_GONGDAN = "sys.is_gongdan";

    /**
     * 查询记录期限
     */
    public static final String CB_DATA_DOWNLOAD_TIME = "sys.data_download_time";
    public static final int DEFALUT_DOWNLOAD_TIME = 3;

    /**
     * version: jiading
     */
    public static final String PARAM_SYS_REGION = "sys.region";
    public static final String REGION_SHANGHAI = "shanghai";
    public static final String REGION_JIADING = "jiading";
    public static final String REGION_WENZHOU = "wenzhou";
    public static final String REGION_SUZHOU = "suzhou";
    public static final String REGION_CHANGSHU = "changshu";
    public static final String REGION_YIWU = "yiwu";

    /**
     * downloading parameter
     */
    public static final String PARAM_SYS_DOWNLOADING_TOTAL = "sys.downloading.total";

    /**
     * uploading parameter
     */
    public static final String PARAM_SYS_UPLOADING_TOTAL = "sys.uploading.total";

    /**
     * synchronize data automatically
     */
    public static final String PARAM_SYS_SYNC_DATA_AUTO = "sys.sync.data.auto";

    /**
     * update version automatically
     */
    public static final String PARAM_SYS_UPDATING_VERSION_AUTO = "sys.updating.version.auto";

    /**
     * true for restful api, or for json rpc
     */
    public static final String PARAM_SYS_RESTFUL_API = "sys.restful.api";

    /**
     * 单条上传
     */
    public static final String PARAM_SYS_SINGLE_UPLOAD = "sys.single.upload";

    /**
     * 册本抄完上传
     */
    public static final String PARAM_SYS_UPLOAD_AFTER_CEBEN = "sys.upload.after.ceben";

    /**
     * 册本抄完上传
     */
    public static final String PARAM_SYS_OPERATION_LEFTORRIGHT = "sys.operation.leftorrigt";

    /**
     * 监控类型：none & countly & umeng
     */
    public static final String PARAM_SYS_MONITOR_TYPE = "sys.monitor.type";

    public enum MonitorType {
        NONE(0, "none"),
        COUTLY(1, "countly"),
        UMENG(2, "umeng");

        private MonitorType(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public static MonitorType toMonitorType(String name) {
            switch (name) {
                case "none":
                    return NONE;
                case "countly":
                    return COUTLY;
                case "umeng":
                    return UMENG;
                default:
                    return NONE;
            }
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private int index;
        private String name;
    }

    /**
     * Countly服务器地址
     **/
    public static final String PARAM_COUNTLY_SERVER_URI = "countly.server.uri";

    /**
     * Countly默认服务器地址
     **/
    public static final String DEFAULT_COUNTLY_SERVER_URI = "http://128.1.2.164";

    /**
     * 后台版本（1.0 或者 2.0）
     */
    public static final String PLATFORM_VERSION = "platform.version";

    /**
     * 强度验收第二步时间限制
     */
    public static final String PARAM_SYS_STRENGTH_STEP_TWO_TIME_LIMIT = "sys.strength.step.two.time.limit";
    public static final long DEFAULT_VALUE_STRENGTH_STEP_TWO_TIME_LIMIT = 30*60*1000;

    /**
     * 强度验收第二步至第三步时间间隔
     */
    public static final String PARAM_SYS_STRENGTH_STEP_TWO_TO_THREE_TIME_INTERVAL = "sys.strength.step.two.to.three.time.interval";
    public static final long DEFAULT_VALUE_STRENGTH_STEP_TWO_TO_THREE_TIME_INTERVAL = 60*60*1000;

    /**
     * 强度验收第三步时间限制
     */
    public static final String PARAM_SYS_STRENGTH_STEP_THREE_TIME_LIMIT = "sys.strength.step.three.time.limit";
    public static final long DEFAULT_VALUE_STRENGTH_STEP_THREE_TIME_LIMIT = 60*60*1000;

    /**
     * 气密验收第二步时间限制
     */
    public static final String PARAM_SYS_AIRTIGHT_STEP_TWO_TIME_LIMIT = "sys.airtight.step.two.time.limit";
    public static final long DEFAULT_VALUE_AIRTIGHT_STEP_TWO_TIME_LIMIT = 15*60*1000;

    /**
     * 气密验收第二步至第三步时间间隔
     */
    public static final String PARAM_SYS_AIRTIGHT_STEP_TWO_TO_THREE_TIME_INTERVAL = "sys.airtight.step.two.to.three.time.interval";
    public static final long DEFAULT_VALUE_AIRTIGHT_STEP_TWO_TO_THREE_TIME_INTERVAL = 45*60*1000;

    /**
     * 气密验收第三步时间限制
     */
    public static final String PARAM_SYS_AIRTIGHT_STEP_THREE_TIME_LIMIT = "sys.airtight.step.three.time.limit";
    public static final long DEFAULT_VALUE_AIRTIGHT_STEP_THREE_TIME_LIMIT = 15*60*1000;

    public SystemConfig() {

    }

}
