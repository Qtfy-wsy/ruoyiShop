package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.StoreRedEnvelope;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by 魔金商城 on 18/4/9
 * 门店红包数据库接口
 */
@Repository
public interface StoreRedEnvelopeMapper {

    /**
     * 添加红包
     *
     * @param storeRedEnvelope 门店红包实体类
     * @return 返回1成功 0失败
     */

    int addStoreRedEnvelope(StoreRedEnvelope storeRedEnvelope);

    /**
     * 查询红包的总记录数
     *
     * @param params 查询参数
     * @return 返回红包的总记录数
     */

    int queryStoreRedEnvelopeCount(Map<String, Object> params);

    /**
     * 分页查询红包
     *
     * @param params 查询参数
     * @return 返回红包信息
     */

    List<StoreRedEnvelope> queryStoreRedEnvelopes(Map<String, Object> params);

    /**
     * 根据红包id查询红包
     *
     * @param id 红包id
     * @return 返回红包信息
     */

    StoreRedEnvelope queryStoreRedEnvelopeById(long id);

    /**
     * 删除及批量删除红包
     *
     * @param ids 红包id数组
     * @return 成功>0 失败0
     */

    int deleteStoreRedEnvelope(long[] ids);


    /**
     * 根据红包id查询红包信息
     *
     * @param redEnvelopeId 红包id
     * @return 红包信息
     */

    StoreRedEnvelope queryStoreRedEnvelopeByIdForReceive(long redEnvelopeId);

    /**
     * 查询红包
     *
     * @return 红包集合
     */

    List<StoreRedEnvelope> queryStoreRedEnvelopeForPc(Map<String, Object> params);

    /**
     * 查询红包数量
     *
     * @return 红包数量
     */

    int queryStoreRedEnvelopeForPcCount();

    /**
     * 设置红包已经全部领取完
     *
     * @return 成功>0 失败= 0
     */

    int updateStoreRedEnvelopeAllReceived(long id);


    /**
     * 查询可以使用的门店红包
     *
     * @param price   订单总价
     * @param storeId 门店id
     * @return 红包集合
     */

    List<StoreRedEnvelope> queryCanUseStoreRedEnvelopes(@Param("price") BigDecimal price, @Param("storeId") long storeId);

}
