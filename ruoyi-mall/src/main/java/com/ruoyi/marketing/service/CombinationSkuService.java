package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.CombinationSku;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 商品组合单品service
 * <p>
 * Created by 魔金商城 on 2017/6/13.
 */
public interface CombinationSkuService {

    /**
     * 分页查询商品组合下的商品
     *
     * @param id      商品组合id
     * @param storeId 店铺id
     * @return 商品组合下的单品
     */
    PageHelper<CombinationSku> querySkuOfGoodCom(PageHelper<CombinationSku> pageHelper, long id, long storeId);

    /**
     * 为商品组合添加单品
     *
     * @param combinationSkus 商品组合单品集合
     * @return 成功返回1，失败返回0
     */
    int addCombinationSkus(List<CombinationSku> combinationSkus);

    /**
     * 批量删除单品
     *
     * @param ids           单品id数组
     * @param combinationId 商品组合id
     * @return 成功返回>=1，失败返回0
     */
    int batchDeleteCombiantionSKu(String[] ids, long combinationId);

    /**
     * 根据商品组合id查询该组合下的单品信息
     *
     * @param combinationId 组合id
     * @return 返回组合信息
     */
    List<String> queryByCombiantionId(long combinationId);
}
