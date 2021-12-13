package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.CrowdfundingProgress;

import java.util.List;

/**
 * Created by 伊甸园商城 on 2018/4/23
 * <p>
 * 众筹进度数据库接口
 */
public interface CrowdfundingProgressMapper {

    /**
     * 查询众筹进度
     *
     * @param marketingId 促销id
     * @return 返回众筹进度信息
     */

    List<CrowdfundingProgress> queryCrowdfundingProgressByMarketingId(long marketingId);

    /**
     * 根据众筹进度id查询众筹进度
     *
     * @param id 众筹进度id
     * @return 返回众筹进度信息
     */

    CrowdfundingProgress queryCrowdfundingProgressById(long id);

    /**
     * 新增众筹进度
     *
     * @param crowdfundingProgress 众筹进度实体
     * @return 成功返回1 失败返回0
     */

    int addCrowdfundingProgress(CrowdfundingProgress crowdfundingProgress);

    /**
     * 修改众筹进度
     *
     * @param crowdfundingProgress 众筹进度实体
     * @return 成功返回1 失败返回0
     */

    int updateCrowdfundingProgress(CrowdfundingProgress crowdfundingProgress);

    /**
     * 删除众筹进度
     *
     * @param id 众筹进度id
     * @return 成功返回1 失败返回0
     */

    int deleteCrowdfundingProgress(long id);

}
