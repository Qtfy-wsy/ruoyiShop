package com.ruoyi.util;

/**
 * Created by 伊甸园商城 on 18/2/6.
 * redis缓存中的key
 */
public interface RedisCahceKey {

    /**
     * mobile端首页缓存key
     */
    String MOBILE_TEMPLATE = "MOBILE_TEMPLATE";

    /**
     * 地区缓存key
     */
    String AREA = "AREA";

    /**
     * 基本信息设置缓存key
     */
    String BASE_INFO_SET = "BASE_INFO_SET";

    /**
     * pc端首页缓存key
     */
    String PC_TEMPLATE = "PC_TEMPLATE";


    // pc端店铺首页模版缓存key
    String STORE_PC_TEMPLATE = "STORE_PC_TEMPLATE";

    /**
     * 手机端专题页缓存key
     */
    String MOBILE_THEMATIC = "MOBILE_THEMATIC";

    /**
     * PC端专题页缓存key
     */
    String PC_THEMATIC = "PC_THEMATIC";

    /**
     * APP端首页模版缓存key
     */
    String APP_INDEX_TEMPLATE = "APP_INDEX_TEMPLATE";

    /**
     * APP 专题页缓存
     */
    String APP_THEMATIC = "APP_THEMATIC";

    /**
     * APPLET 端首页模版缓存key
     */
    String APPLET_INDEX_TEMPLATE = "APPLET_INDEX_TEMPLATE";

    /**
     * APPLET 专题页缓存
     */
    String APPLET_THEMATIC = "APPLET_THEMATIC";


    /**
     * 促销设置缓存key
     */
    String MARKETING_SETTING = "MARKETING_SETTING";

    /**
     * 微信用户关联缓存key
     */
    String WE_CHAT_CUSTOMER_LINK = "WE_CHAT_CUSTOMER_LINK";

    /**
     * app版本
     */
    String APP_VERSION = "APP_VERSION";
    /**
     * 社区团购设置
     */
    String COMMUNITY_BUY_SETTING = "COMMUNITY_BUY_SETTING";
    /**
     * 社区推广
     */
    String COMMUNITY_BUY_INDEX = "COMMUNITY_BUY_INDEX";
}
