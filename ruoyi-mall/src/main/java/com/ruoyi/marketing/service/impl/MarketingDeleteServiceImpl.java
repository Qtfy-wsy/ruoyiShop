package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.mapper.MarketingMapper;
import com.ruoyi.marketing.service.*;
import com.ruoyi.util.MarketingConstant;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 促销删除服务实现类
 */
@Service("marketingDeleteService")
public class MarketingDeleteServiceImpl implements MarketingService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(MarketingDeleteServiceImpl.class);

    /**
     * 注入促销数据库服务
     */
    @Autowired
    private MarketingMapper marketingMapper;

    /**
     * 注入秒杀活动折扣服务接口
     */
    @Autowired
    private SeckillScenePanicbuyService seckillScenePanicbuyService;

    /**
     * 注入拼团活动服务接口
     */
    @Autowired
    private GroupMarketingShowService groupMarketingShowService;

    /**
     * 注入试用活动服务接口
     */
    @Autowired
    private TryMarketingShowService tryMarketingShowService;

    /**
     * 注入预售活动服务接口
     */
    @Autowired
    private PreSaleShowService preSaleShowService;


    @Override
    public int deleteMarketing(long marketingId, long storeId) {
        logger.debug("deleteMarketing and marketingId:{}\r\n storeId:{}", marketingId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", marketingId);
        params.put("storeId", storeId);
        if (marketingMapper.deleteMarketing(params) != 1) {
            logger.error("deleteMarketing fail...");
            return 0;
        }
        return 1;
    }

    @Transactional
    @Override
    public int deleteMarketings(Long[] marketingIds, long storeId, String type) {
        logger.debug("deleteMarketings and marketingIds:{} \r\n storeId:{} \r\n type:{}", marketingIds, storeId, type);
        if (ArrayUtils.isEmpty(marketingIds)) {
            logger.error("deleteMarketings fail due to marketingIds is empty");
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("list", Arrays.asList(marketingIds));
        params.put("storeId", storeId);
        if (marketingMapper.deleteMarketings(params) < 1) {
            logger.error("deleteMarketings fail...");
            return 0;
        }
        // 删除促销关联的相关信息
        deleteOtherDetail(marketingIds, type);
        return 1;
    }


    /**
     * 删除促销关联的相关信息
     *
     * @param marketingIds 促销id数组
     * @param type         促销类型
     */
    private void deleteOtherDetail(Long[] marketingIds, String type) {
        // 删除促销关联的相关信息
        switch (type) {
            case MarketingConstant.FALL_MARKETING:
                // 直降
                break;
            case MarketingConstant.PANIC_BUY_MARKETING:
                // 抢购促销
                seckillScenePanicbuyService.deleteSeckillScenePanicbuysByMarketingIds(Arrays.asList(marketingIds));
                break;
            case MarketingConstant.FULL_DOWN_MARKETING:
                // 满减
                break;
            case MarketingConstant.FULL_DISCOUNT_MARKETING:
                // 满折
                break;
            case MarketingConstant.FULL_GIFT_MARKETING:
                // 满赠
                break;
            case MarketingConstant.DEPOSIT_PRE_SALE_MARKETING:
                // 定金预售
                preSaleShowService.deletePreSalesByMarketingIds(Arrays.asList(marketingIds));
                // 获取需要删除的预售id列表（原有的预售id列表和原有的需要修改预售id列表差集）
                /*List<Long> needDeletePreSaleIdList = originalPreSaleList.stream().map(PreSale::getId).collect(Collectors.toList());
                needDeletePreSaleIdList.removeAll(needUpdatePreSaleIdList);

                // 删除预售促销详情
                if (!CollectionUtils.isEmpty(needDeletePreSaleIdList)) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("marketingId", marketing.getId());
                    params.put("preSaleIdList", needDeletePreSaleIdList);
                    preSaleMapper.deletePreSaleByIds(params);
                }*/
                break;
            case MarketingConstant.FULL_PRE_SALE_MARKETING:
                // 全款预售
                preSaleShowService.deletePreSalesByMarketingIds(Arrays.asList(marketingIds));
                break;
            case MarketingConstant.TRY_MARKETING:
                // 试用促销
                tryMarketingShowService.deleteTrysByMarketingIds(Arrays.asList(marketingIds));
                break;
            case MarketingConstant.GROUP_MARKETING:
                //拼团促销
                groupMarketingShowService.deleteGroupsByMarketingIds(Arrays.asList(marketingIds));
                break;
            case MarketingConstant.CROWD_FUNDING_MARKETING:
                //众筹促销
                break;
            default:
                logger.error("no marketing...");
        }
    }

}
