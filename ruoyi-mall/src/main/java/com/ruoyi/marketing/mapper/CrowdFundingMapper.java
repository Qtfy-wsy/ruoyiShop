package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.CrowdFunding;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 众筹促销数据库接口
 *
 * @author SK
 * @since 2018/4/23
 */
@Repository
public interface CrowdFundingMapper {

    /**
     * 添加众筹促销
     *
     * @param crowdFunding 众筹促销
     * @return 添加众筹促销
     */

    int addCrowdFunding(CrowdFunding crowdFunding);


    /**
     * 根据促销id查询众筹促销信息
     *
     * @param marketingId 促销id
     * @return 返回众筹促销信息
     */

    CrowdFunding queryCrowdFunding(long marketingId);

    /**
     * 更新众筹促销
     *
     * @param crowdFunding 众筹促销
     * @return 成功返回1 失败返回0
     */

    int updateCrowdFunding(CrowdFunding crowdFunding);

    /**
     * 新增众筹的已筹金额
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int addCrowdFundingAlerdyMoney(Map<String, Object> params);

    /**
     * 更新自动处理状态
     *
     * @return 1成功，否则失败
     */

    int updateAutoHandleStatus(long marketingId);


}
