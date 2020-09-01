package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.MarketingPic;
import com.ruoyi.marketing.mapper.MarketingPicMapper;
import com.ruoyi.marketing.service.MarketingPicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 促销图片服务接口实现类
 *
 * @author 魔金商城 created on 2020/5/12
 */
@Service
@Slf4j
public class MarketingPicServiceImpl implements MarketingPicService {

    /**
     * 注入促销图片数据库接口
     */
    @Autowired
    private MarketingPicMapper marketingPicMapper;


    /**
     * 保存促销图片
     *
     * @param marketingPic 促销图片实体
     * @return 成功1 否则失败
     */
    @Override
    public int saveMarketingPic(MarketingPic marketingPic) {
        log.debug("saveMarketingPic and marketingPic :{}", marketingPic);

        if (Objects.isNull(marketingPic)) {
            log.error("saveMarketingPic fail due to marketingPic is null");
            return -1;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("type", marketingPic.getType());
        params.put("storeId", marketingPic.getStoreId());
        int marketingPicCount = marketingPicMapper.queryMarketingPicCount(params);
        // 判断查询促销图片记录是否超过一条
        if (marketingPicCount > 1) {
            log.error("saveMarketingPic fail due to marketingPic total count beyond 1");
            return -2;
        }
        if (marketingPicCount == 0) {
            // 不存在促销图片记录，新增促销图片
            log.info("saveMarketingPic by addMarketingPic");
            return marketingPicMapper.addMarketingPic(marketingPic);
        } else {
            // 存在促销图片记录，修改促销图片
            log.info("saveMarketingPic by updateMarketingPic");
            return marketingPicMapper.updateMarketingPic(marketingPic);
        }
    }

    /**
     * 查询促销图片
     *
     * @param type    促销图片类型
     * @param storeId 店铺id
     * @return 返回促销图片信息
     */
    @Override
    public MarketingPic queryMarketingPic(String type, long storeId) {
        log.debug("queryMarketingPic and type :{} \r\n storeId :{}", type, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("storeId", storeId);
        return marketingPicMapper.queryMarketingPic(params);
    }

}
