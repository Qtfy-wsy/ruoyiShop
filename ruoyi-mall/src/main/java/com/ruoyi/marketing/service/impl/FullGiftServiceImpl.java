package com.ruoyi.marketing.service.impl;

import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.FullGift;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.mapper.FullGiftMapper;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by 魔金商城 on 18/1/2.
 * 满赠服务接口
 */
@Service("fullGiftService")
public class FullGiftServiceImpl extends MarketingTemplate {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(FullGiftServiceImpl.class);

    /**
     * 注入满赠数据库接口
     */
    @Autowired
    private FullGiftMapper fullGiftMapper;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;


    @Override
    public int addMarketingDetail(Marketing marketing) {
        logger.debug("addMarketingDetail and marketing:{}", marketing);
        if (!validateParams(marketing)) {
            logger.error("addMarketingDetail fail:validateParams error");
            return 0;
        }
        fullGiftMapper.addFullGifts(marketing.getFullGifts());
        return 1;
    }

    @Override
    public int updateMarketingDetail(Marketing marketing, Marketing oldMarketing) {
        logger.debug("updateMarketingDetail and marketing:{}", marketing);
        if (!validateParams(marketing)) {
            logger.error("updateMarketingDetail fail:validateParams error");
            return 0;
        }
        //删除满赠促销
        fullGiftMapper.deleteByMarketingId(marketing.getId());
        //新增满减促销
        addMarketingDetail(marketing);
        return 1;
    }


    @Override
    public void setMarketingDetail(Marketing marketing) {
        if (Objects.isNull(marketing) || !marketing.isFullGiftMarketing()) {
            logger.error("setMarketingDetail fail due to params is error....");
            return;
        }

        // 具体的满赠信息
        List<FullGift> fullGifts = fullGiftMapper.queryByMarketingId(marketing.getId());
        if (CollectionUtils.isEmpty(fullGifts)) {
            logger.error("setMarketingDetail fail due to fullGifts is not exist...");
            return;
        }

        // 将赠品的json信息转化成对象
        fullGifts.stream().forEach(FullGift::convertJsonToObject);

        // 设置单品赠品的图片
        fullGifts.stream().flatMap(fullGift -> fullGift.getGiftSkuInfos().stream()).forEach(giftSkuInfo -> giftSkuInfo.setSku(skuService.querySkuByIdWithSpecs(giftSkuInfo.getSkuId(), CommonConstant.QUERY_WITH_NO_STORE)));

        // 设置满赠信息
        marketing.setFullGifts(fullGifts);
    }

    /**
     * 校验当前促销是否正确
     *
     * @param marketing 促销信息
     * @return 正确返回 true 失败返回false
     */
    private boolean validateParams(Marketing marketing) {
        return Objects.nonNull(marketing) && !CollectionUtils.isEmpty(marketing.getFullGifts()) && marketing.isFullGiftMarketing();
    }
}
