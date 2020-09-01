package com.ruoyi.marketing.service.impl;


import com.ruoyi.marketing.domain.SeckillScenePanicbuy;
import com.ruoyi.marketing.service.SeckillScenePanicbuyService;
import com.ruoyi.marketing.service.SeckillScenePanicbuyServiceApi;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 秒杀场次折扣聚合服务接口实现
 *
 * @author 魔金商城 created on 2020/5/15
 */
@Slf4j
@Service
public class SeckillScenePanicbuyServiceApiImpl implements SeckillScenePanicbuyServiceApi {

    /**
     * 注入秒杀场次折扣
     */
    @Autowired
    private SeckillScenePanicbuyService seckillScenePanicbuyService;

    /**
     * 注入店铺信息服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;


    @Override
    public PageHelper<SeckillScenePanicbuy> querySeckillScenePanicbuyList(PageHelper<SeckillScenePanicbuy> pageHelper, long seckillSceneId, long storeId) {
        log.debug("querySeckillScenePanicbuyList and pageHelper :{} \r\n seckillSceneId :{} \r\n storeId :{}", pageHelper, seckillSceneId, storeId);
        return pageHelper.setListDates(seckillScenePanicbuyService.querySeckillScenePanicbuyList(pageHelper, seckillSceneId, storeId).getList()
                .stream().peek(seckillScenePanicbuy -> setOtherInfo(seckillScenePanicbuy, storeId)).collect(Collectors.toList()));
    }


    /**
     * 设置列表其他信息
     *
     * @param seckillScenePanicbuy 秒杀场次折扣
     * @param storeId              店铺id
     */
    private void setOtherInfo(SeckillScenePanicbuy seckillScenePanicbuy, long storeId) {
        log.debug("setOtherInfo and seckillScenePanicbuy :{} \r\n storeId :{}", seckillScenePanicbuy, storeId);

        if (Objects.isNull(seckillScenePanicbuy)) {
            return;
        }

        // 平台查询，则设置店铺名称
        if (CommonConstant.ADMIN_STOREID == storeId) {

            if (CommonConstant.ADMIN_STOREID == seckillScenePanicbuy.getStoreId()) {

                seckillScenePanicbuy.setStoreName("平台自营");

            } else {

                // 设置店铺名称
                TStoreInfo storeInfo = storeInfoService.queryStoreInfo(seckillScenePanicbuy.getStoreId());

                if (Objects.nonNull(storeInfo)) {
                    seckillScenePanicbuy.setStoreName(storeInfo.getStoreName());
                }
            }

        }

    }

}
