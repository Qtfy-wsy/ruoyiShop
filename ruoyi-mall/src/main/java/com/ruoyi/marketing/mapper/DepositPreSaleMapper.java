package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.DepositPreSale;
import org.springframework.stereotype.Repository;

/**
 * 定金预售数据库接口
 */
@Repository
public interface DepositPreSaleMapper {

    /**
     * 根据促销id查询定金预售
     *
     * @param marketingId 促销id
     * @return 返回定金预售
     */

    DepositPreSale queryDepositPreSaleByMarketingId(long marketingId);

    /**
     * 添加定金预售
     *
     * @param depositPreSale 定金预售实体
     * @return 1:成功 否则失败
     */

    int addDepositPreSale(DepositPreSale depositPreSale);

    /**
     * 更新定金预售
     *
     * @param depositPreSal 定金预售实体
     */

    int updateDepositPreSale(DepositPreSale depositPreSal);
}
