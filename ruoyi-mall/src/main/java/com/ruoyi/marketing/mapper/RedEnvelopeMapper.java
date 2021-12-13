package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.RedEnvelope;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 伊甸园商城 on 18/1/15
 * 红包数据库接口
 */
@Repository
public interface RedEnvelopeMapper {

    /**
     * 添加红包
     *
     * @param redEnvelope 红包实体类
     * @return 返回1成功 0失败
     */

    int addRedEnvelope(RedEnvelope redEnvelope);

    /**
     * 查询红包的总记录数
     *
     * @param params 查询参数
     * @return 返回红包的总记录数
     */

    int queryRedEnvelopeCount(Map<String, Object> params);

    /**
     * 分页查询红包
     *
     * @param params 查询参数
     * @return 返回红包信息
     */

    List<RedEnvelope> queryRedEnvelopes(Map<String, Object> params);

    /**
     * 根据红包id查询红包
     *
     * @param id 红包id
     * @return 返回红包信息
     */

    RedEnvelope queryRedEnvelopeById(long id);

    /**
     * 删除及批量删除红包
     *
     * @param ids 红包id数组
     * @return 成功1 失败0
     */

    int deleteRedEnvelope(long[] ids);


    /**
     * 根据红包id查询红包信息
     *
     * @param redEnvelopeId 红包id
     * @return 红包信息
     */

    RedEnvelope queryRedEnvelopeByIdForReceive(long redEnvelopeId);

    /**
     * 查询红包
     *
     * @return 红包集合
     */

    List<RedEnvelope> queryRedEnvelopeForPc(Map<String, Object> params);

    /**
     * 查询红包数量
     *
     * @return 红包数量
     */

    int queryRedEnvelopeForPcCount();

    /**
     * 设置红包已经全部领取完
     *
     * @return 成功>0 失败= 0
     */

    int updateRedEnvelopeAllReceived(long id);
}
