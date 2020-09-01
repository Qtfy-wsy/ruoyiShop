package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.GroupMarketing;
import com.ruoyi.util.PageHelper;

/**
 * 拼团促销聚合服务接口
 *
 * @author 魔金商城 created on 2020/6/1
 */
public interface GroupMarketingServiceApi {

    /**
     * 分页查询拼团促销列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回拼团促销列表
     */
    PageHelper<GroupMarketing> queryGroupMarketingList(PageHelper<GroupMarketing> pageHelper, String name, String skuNo, long storeId);

}
