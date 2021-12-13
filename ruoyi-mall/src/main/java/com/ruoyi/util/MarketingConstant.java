package com.ruoyi.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 伊甸园商城 on 17/6/8.
 * 促销常量类
 */
public interface MarketingConstant {

    /**
     * 直降促销
     */
    String FALL_MARKETING = "1";

    /**
     * 满赠
     */
    String FULL_GIFT_MARKETING = "2";

    /**
     * 抢购促销
     */
    String PANIC_BUY_MARKETING = "3";

    /**
     * 满减促销
     */
    String FULL_DOWN_MARKETING = "4";

    /**
     * 满折促销
     */
    String FULL_DISCOUNT_MARKETING = "5";

    /**
     * 包邮促销
     */
    String FREE_SHIP_MARKETING = "6";

    /**
     * 定金预售
     */
    String DEPOSIT_PRE_SALE_MARKETING = "7";

    /**
     * 全款预售
     */
    String FULL_PRE_SALE_MARKETING = "8";

    /**
     * 试用促销
     */
    String TRY_MARKETING = "9";

    /**
     * 拼团促销
     */
    String GROUP_MARKETING = "10";

    /**
     * 众筹促销
     */
    String CROWD_FUNDING_MARKETING = "11";

    /**
     * 存在互斥的促销类型
     */
    List<String> EXCLUSION_MARKETING_TYPES = Arrays.asList(PANIC_BUY_MARKETING, FALL_MARKETING, GROUP_MARKETING, DEPOSIT_PRE_SALE_MARKETING, FULL_PRE_SALE_MARKETING, TRY_MARKETING, CROWD_FUNDING_MARKETING);

    /**
     * 批发商品互斥的促销类型
     */
    List<String> BATCH_EXCLUSION_MARKETING_TYPES = Arrays.asList(PANIC_BUY_MARKETING, GROUP_MARKETING, DEPOSIT_PRE_SALE_MARKETING, FULL_PRE_SALE_MARKETING, TRY_MARKETING, CROWD_FUNDING_MARKETING);
}
