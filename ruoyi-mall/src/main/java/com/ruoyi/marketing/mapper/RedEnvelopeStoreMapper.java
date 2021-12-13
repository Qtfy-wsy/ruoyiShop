package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.RedEnvelopeStore;

import java.util.List;
import java.util.Map;

/**
 * Created by 伊甸园商城 on 18/1/17.
 * 红包店铺关联数据库接口
 */
public interface RedEnvelopeStoreMapper {

    /**
     * 根据红包id查询红包下的店铺
     *
     * @param redEnvelopeId 红包id
     * @return 返回红包下的店铺信息
     */

    List<RedEnvelopeStore> queryByRedEnvelopeId(long redEnvelopeId);

    /**
     * 根据红包id查询红包下的店铺信息及名称
     *
     * @param redEnvelopeId 红包id
     * @return 返回红包下的店铺信息及名称
     */

    List<RedEnvelopeStore> queryStoresByRedEnvelopeId(long redEnvelopeId);

    /**
     * 添加红包店铺
     *
     * @param redEnvelopeId 红包id
     * @return 成功1 失败0
     */

    int addRedEnvelopeStore(long redEnvelopeId);

    /**
     * 参加红包
     *
     * @param params 红包id和店铺id
     * @return 成功1 失败0
     */

    int joinRedEnvelope(Map<String, Object> params);

    /**
     * 判断是否已经参加红包
     *
     * @param params 红包id和店铺id
     * @return 已经参加>0 没有参加0
     */

    int hasJoinRedEnvelope(Map<String, Object> params);

}
