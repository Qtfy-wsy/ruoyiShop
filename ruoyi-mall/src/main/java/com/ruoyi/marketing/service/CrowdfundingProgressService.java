package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.CrowdfundingProgress;

import java.util.List;

/**
 * Created by 魔金商城 on 2018/4/23
 * <p>
 * 众筹进度服务接口
 */
public interface CrowdfundingProgressService {

    /**
     * 查询众筹进度
     *
     * @param marketingId 促销id
     * @param storeId     店铺id
     * @return 返回众筹进度信息
     */
    List<CrowdfundingProgress> queryCrowdfundingProgressByMarketingId(long marketingId, long storeId);

    /**
     * 根据众筹进度id查询众筹进度
     *
     * @param id          众筹进度id
     * @param marketingId 促销id
     * @param storeId     店铺id
     * @return 返回众筹进度信息
     */
    CrowdfundingProgress queryCrowdfundingProgressById(long id, long marketingId, long storeId);

    /**
     * 新增众筹进度
     *
     * @param crowdfundingProgress 众筹进度实体
     * @param storeId              店铺id
     * @return 成功返回1 失败返回0 促销不存在-1
     */
    int addCrowdfundingProgress(CrowdfundingProgress crowdfundingProgress, long storeId);

    /**
     * 修改众筹进度
     *
     * @param crowdfundingProgress 众筹进度实体
     * @param storeId              店铺id
     * @return 成功返回1 失败返回0 促销不存在-1
     */
    int updateCrowdfundingProgress(CrowdfundingProgress crowdfundingProgress, long storeId);

    /**
     * 删除众筹进度
     *
     * @param id          众筹进度id
     * @param marketingId 促销id
     * @param storeId     店铺id
     * @return 成功返回1 失败返回0
     */
    int deleteCrowdfundingProgress(long id, long marketingId, long storeId);

}
