package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.CrowdfundingProgress;
import com.ruoyi.marketing.mapper.CrowdfundingProgressMapper;
import com.ruoyi.marketing.service.CrowdfundingProgressService;
import com.ruoyi.marketing.service.MarketingQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 2018/4/23
 * <p>
 * 众筹进度服务实现接口
 */
@Service
@Slf4j
public class CrowdfundingProgressServiceImpl implements CrowdfundingProgressService {

    /**
     * 注入众筹进度数据库接口
     */
    @Autowired
    private CrowdfundingProgressMapper crowdfundingProgressMapper;

    /**
     * 注入促销查询服务接口
     */
    @Autowired
    private MarketingQueryService marketingQueryService;

    /**
     * 查询众筹进度
     *
     * @param marketingId 促销id
     * @param storeId     店铺id
     * @return 返回众筹进度信息
     */
    @Override
    public List<CrowdfundingProgress> queryCrowdfundingProgressByMarketingId(long marketingId, long storeId) {
        log.debug("queryCrowdfundingProgressByMarketingId and marketingId :{} \r\n storeId :{}", marketingId, storeId);
        if (Objects.isNull(marketingQueryService.querySimpleMarketingById(marketingId, storeId))) {
            log.error("queryCrowdfundingProgressByMarketingId fail due to marketing is not exist");
            return Collections.emptyList();
        }
        return crowdfundingProgressMapper.queryCrowdfundingProgressByMarketingId(marketingId);
    }

    /**
     * 根据众筹进度id查询众筹进度
     *
     * @param id          众筹进度id
     * @param marketingId 促销id
     * @param storeId     店铺id
     * @return 返回众筹进度信息
     */
    @Override
    public CrowdfundingProgress queryCrowdfundingProgressById(long id, long marketingId, long storeId) {
        log.debug("queryCrowdfundingProgressById and id :{} \r\n marketingId :{} \r\n storeId :{}", id, marketingId, storeId);
        if (Objects.isNull(marketingQueryService.querySimpleMarketingById(marketingId, storeId))) {
            log.error("queryCrowdfundingProgressById fail due to marketing is not exist");
            return null;
        }
        return crowdfundingProgressMapper.queryCrowdfundingProgressById(id);
    }

    /**
     * 新增众筹进度
     *
     * @param crowdfundingProgress 众筹进度实体
     * @param storeId              店铺id
     * @return 成功返回1 失败返回0 促销不存在-1
     */
    @Override
    public int addCrowdfundingProgress(CrowdfundingProgress crowdfundingProgress, long storeId) {
        log.debug("addCrowdfundingProgress and crowdfundingProgress :{} \r\n storeId :{}", crowdfundingProgress, storeId);
        if (Objects.isNull(crowdfundingProgress)) {
            log.error("addCrowdfundingProgress fail due to crowdfundingProgress is empty");
            return 0;
        }
        if (Objects.isNull(marketingQueryService.querySimpleMarketingById(crowdfundingProgress.getMarketingId(), storeId))) {
            log.error("addCrowdfundingProgress fail due to marketing is not exist");
            return -1;
        }
        return crowdfundingProgressMapper.addCrowdfundingProgress(crowdfundingProgress);
    }

    /**
     * 修改众筹进度
     *
     * @param crowdfundingProgress 众筹进度实体
     * @param storeId              店铺id
     * @return 成功返回1 失败返回0 促销不存在-1
     */
    @Override
    public int updateCrowdfundingProgress(CrowdfundingProgress crowdfundingProgress, long storeId) {
        log.debug("updateCrowdfundingProgress and crowdfundingProgress :{} \r\n storeId", crowdfundingProgress, storeId);
        if (Objects.isNull(crowdfundingProgress)) {
            log.error("updateCrowdfundingProgress fail due to crowdfundingProgress is empty");
            return 0;
        }
        if (Objects.isNull(marketingQueryService.querySimpleMarketingById(crowdfundingProgress.getMarketingId(), storeId))) {
            log.error("updateCrowdfundingProgress fail due to marketing is not exist");
            return -1;
        }
        return crowdfundingProgressMapper.updateCrowdfundingProgress(crowdfundingProgress);
    }

    /**
     * 删除众筹进度
     *
     * @param id          众筹进度id
     * @param marketingId 促销id
     * @param storeId     店铺id
     * @return 成功返回1 失败返回0
     */
    @Override
    public int deleteCrowdfundingProgress(long id, long marketingId, long storeId) {
        log.debug("deleteCrowdfundingProgress and id :{} \r\n marketingId :{} \r\n storeId :{}", id, marketingId, storeId);
        if (Objects.isNull(marketingQueryService.querySimpleMarketingById(marketingId, storeId))) {
            log.error("deleteCrowdfundingProgress fail due to marketing is not exist");
            return 0;
        }
        return crowdfundingProgressMapper.deleteCrowdfundingProgress(id);
    }

}
