package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.PreSale;
import com.ruoyi.util.PageHelper;

/**
 * 预售促销聚合服务接口
 *
 * @author 伊甸园商城 created on 2020/6/12
 */
public interface PreSaleServiceApi {

    /**
     * 分页查询预售促销列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回预售促销列表
     */
    PageHelper<PreSale> queryPreSaleList(PageHelper<PreSale> pageHelper, String name, String skuNo, long storeId);

}
