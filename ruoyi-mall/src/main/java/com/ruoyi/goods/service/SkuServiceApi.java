package com.ruoyi.goods.service;


import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.util.PageHelper;

/**
 * 单品聚合服务接口
 */
public interface SkuServiceApi {

    /**
     * 搜索所有单品(包含店铺商品)
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @return 所有单品
     */
    PageHelper<PmsSku> queryAllSkusWithSpecs(PageHelper<PmsSku> pageHelper, String name, String skuNo);

    /**
     * 根据单品id查询单品信息
     *
     * @param skuId 单品id
     * @return 返回单品信息(包括单品的规格信息)
     */
    PmsSku querySkuByIdWithSpecs(String skuId);

}
