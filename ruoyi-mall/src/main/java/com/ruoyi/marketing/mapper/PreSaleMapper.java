package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.PreSale;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 预售数据库接口
 */
@Repository
public interface PreSaleMapper {

    /**
     * 查询预售促销信息
     *
     * @param params 查询参数
     * @return 返回预售促销信息
     */

    List<PreSale> queryPreSale(Map<String, Object> params);

    /**
     * 添加预售
     *
     * @param preSale 预售实体
     * @return 1:成功 否则失败
     */

    int addPreSale(PreSale preSale);

    /**
     * 更新预售
     *
     * @param preSale 预售实体
     */

    int updatePreSale(PreSale preSale);

    /**
     * 根据预售id集合删除预售促销
     *
     * @param params 参数
     * @return 成功>0 否则失败
     */

    int deletePreSaleByIds(Map<String, Object> params);

    /**
     * 分页查询预售促销集合
     *
     * @param parsms 查询参数
     * @return 预售促销集合
     */

    List<PreSale> queryPreSaleList(Map<String, Object> parsms);

    /**
     * 查询预售促销总记录数
     *
     * @param parsms 查询参数
     * @return 预售促销总记录数
     */

    int queryPreSaleListCount(Map<String, Object> parsms);

    /**
     * 根据id查询预售促销信息
     *
     * @param parsms 查询参数
     * @return 预售促销信息
     */

    PreSale queryPreSaleById(Map<String, Object> parsms);
}
