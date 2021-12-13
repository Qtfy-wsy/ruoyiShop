package com.ruoyi.order.service.impl;


import com.ruoyi.goods.service.IPmsGoodsService;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.order.domain.OmsLogisticsTemplate;
import com.ruoyi.order.service.IOmsLogisticsTemplateService;
import com.ruoyi.order.service.LogisticsTemplateServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * 物流模版服务api接口实现
 *
 * @author 伊甸园商城 created on 2020/4/24
 */
@Service
@Slf4j
public class LogisticsTemplateServiceApiImpl implements LogisticsTemplateServiceApi {

    /**
     * 注入物流模版服务接口
     */
    @Autowired
    private IOmsLogisticsTemplateService logisticsTemplateService;

    /**
     * 注入商品服务接口
     */
    @Autowired
    private IPmsGoodsService spuService;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;


    @Override
    public int deleteLogisticsTemplate(long id, long storeId) {
        log.debug("deleteLogisticsTemplate id:{} \r\n storeId:{}", id, storeId);

        int res = logisticsTemplateService.deleteLogisticsTemplate(id, storeId);

        if (res == 1) {
            // 查询店铺的默认运费模版
            OmsLogisticsTemplate defaultLogisticsTemplate = logisticsTemplateService.queryDefaultLogisticsTemplate(storeId);
            if (Objects.nonNull(defaultLogisticsTemplate)) {
                // 修改商品物流模版id，把被删除的物流模版id改成默认物流模版id
                spuService.updateSpuLogisticsTemplateId(id, defaultLogisticsTemplate.getId(), storeId);
                // 修改单品物流模版id，把被删除的物流模版id改成默认物流模版id
                skuService.updateSkuLogisticsTemplateId(id, defaultLogisticsTemplate.getId(), storeId);
            }
        }

        return res;
    }

}
