package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.PreSaleShow;
import com.ruoyi.util.PageHelper;

/**
 * 预售活动聚合服务接口
 *
 * @author 伊甸园商城 created on 2020/6/12
 */
public interface PreSaleShowServiceApi {

    /**
     * 分页查询预售活动列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回预售活动列表
     */
    PageHelper<PreSaleShow> queryPreSaleShowList(PageHelper<PreSaleShow> pageHelper, String name, String skuNo, long storeId);

}
