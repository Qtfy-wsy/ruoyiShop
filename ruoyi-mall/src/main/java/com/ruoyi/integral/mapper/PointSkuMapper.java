package com.ruoyi.integral.mapper;


import com.ruoyi.integral.domain.PointSku;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 积分商品数据库接口
 */
@Repository
public interface PointSkuMapper {

    /**
     * 增加积分商品
     *
     * @param pointSku 积分商品实体
     * @return 1：成功 0：失败
     */

    int addPointSku(PointSku pointSku);

    /**
     * 更新积分商品
     *
     * @param pointSku 积分商品实体
     * @return 1：成功 0：失败
     */

    int updatePointSku(PointSku pointSku);

    /**
     * 查询积分商品
     *
     * @param map 参数
     * @return 积分商品集合
     */

    List<PointSku> queryPointSkus(Map<String, Object> map);

    /**
     * 查询积分商品数量
     *
     * @param map 参数
     * @return 积分商品数量
     */

    int queryPointSkusCount(Map<String, Object> map);

    /**
     * 根据积分商品id查询积分商品
     *
     * @param id 积分商品id
     * @return 积分商品
     */

    PointSku queryPointSkuById(long id);

    /**
     * 根据积分商品编号查询积分商品
     *
     * @param code 积分商品编号
     * @return 积分商品
     */

    PointSku queryPointSkuByCode(String code);

    /**
     * 删除积分商品
     *
     * @param id 积分商品id
     * @return 1：成功 0：失败
     */

    int deletePointSku(long id);

    /**
     * 批量删除积分商品
     *
     * @param ids 积分商品id
     * @return >0：成功 0：失败
     */

    int deletePointSkus(List<Long> ids);

    /**
     * 积分商品减库存
     *
     * @param params 参数
     * @return 成功返回1，失败返回0
     */

    int reducePointSkuStock(Map<String, Object> params);
}
