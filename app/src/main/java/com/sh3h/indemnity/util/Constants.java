package com.sh3h.indemnity.util;

/**
 * Created by dengzhimin on 2017/3/7.
 */

public class Constants {

    public static final String TEXT_EMPTY = "-";

    public static final String INTENT_PARAM_PROJECT = "project";
    public static final String INTENT_PARAM_PROJECT_ID = "projectId";
    public static final String INTENT_PARAM_BUDGET = "budget";
    public static final String INTENT_PARAM_ACCEPT_TYPE = "acceptType";
    public static final String INTENT_PARAM_FLAG = "intentFlag";
    public static final String INTENT_PARAM_ADDRESS = "address";
    public static final String INTENT_PARAM_PATROL_TYPE = "patrolType";
    public static final String INTENT_PARAM_USERID = "userId";
    public static final String INTENT_PARAM_BASEURI = "baseUri";
    public static final String INTENT_PARAM_FROM = "from";
    public static final String INTENT_PARAM_MATERIAL_APPLY = "materialApply";
    public static final String INTENT_PARAM_MATERIAL_VERIFY = "materialVerify";

    public static final String INTENT_PARAM_MULTIMEDIAS = "multiMedias";
    public static final String INTENT_PARAM_MULTIMEDIA_URLS = "multiMediaUrls";
    public static final String INTENT_PARAM_SELECTPOSITION = "selectPosition";

    /** 界面跳转flag 业务流程中共用一个界面而功能逻辑不一样 **/
    public static final String FLAG_STOP_PROJECT = "stopProject";
    public static final String FLAG_RESTART_PROJECT = "restartProject";
    public static final String FLAG_DAILY_PATROL = "dailyPatrol";
    public static final String FLAG_INDEMNITYCENTER_PATROL = "indemnityCenterPatrol";
    public static final String FLAG_SEARCH_PROJECT = "searchProject";
    public static final String FLAG_MATERIAL_APPLY = "materialApply";
    public static final String FLAG_ACCEPT_STRENGTH = "acceptStrength";
    public static final String FLAG_ACCEPT_AIRTIGHT = "acceptAirtight";
    public static final String FLAG_ACCEPT_PROJECT = "acceptProject";
    public static final String FLAG_ACCEPT_CONSTRUCTION = "acceptConstruction";

    public static final String OUTSTATE_FRAGMENT_NAME = "fragmentName";
    public static final String OUTSTATE_PROJECT = "project";

    //工程验收 验收结果词语group
    public static final String WORDS_TYPE_ACCEPT_RESULT = "2759";
    //巡视施工结果词语group
    public static final String WORDS_TYPE_VIOLATION_RESULT = "2751";
    //强度试验 检查情况词语group
    public static final String WORDS_TYPE_CHECK_SITUATION = "2759";
    //强度试验 泄露情况词语group
    public static final String WORDS_TYPE_LEAK_SITUATION = "2753";
    //强度试验 泄露整改词语group
    public static final String WORDS_TYPE_LEAK_CHANGE = "2755";
    //强度试验 施工方案词语group
    public static final String WORDS_TYPE_CONSTRUCTION_PROGRAM = "2754";
    //强度试验 施工整改词语group
    public static final String WORDS_TYPE_CONSTRUCTION_CHANGE = "2756";

    public static final String WORDS_TYPE_NORMAL = "1";
    public static final String WORDS_TYPE_ABNORMAL = "2";

    public static final String WORDS_TYPE_LEAK_YES = "1";
    public static final String WORDS_TYPE_LEAK_NO = "2";

    public static final String WORDS_AGREE_VALUE = "1";
    public static final String WORDS_DISAGREE_VALUE = "2";

}
