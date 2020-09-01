package com.ruoyi.store.service.impl;

import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.store.domain.AttentionStore;
import com.ruoyi.store.mapper.AttentionStoreMapper;
import com.ruoyi.store.mapper.TStoreInfoMapper;
import com.ruoyi.store.service.AttentionStoreService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 关注店铺service实现类
 *
 * @author 魔金商城 on 2017/7/4.
 */
@Service
public class AttentionStoreServiceImpl implements AttentionStoreService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(AttentionStoreServiceImpl.class);

    /**
     * 注入关注的店铺mapper
     */
    @Autowired
    private AttentionStoreMapper attentionStoreMapper;

    /**
     * 注入店铺信息service
     */
    @Autowired
    private TStoreInfoMapper storeInfoMapper;
    /**
     * 注入单品service
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 根据店铺id查询关注的店铺
     *
     * @param customerId 会员id
     * @return 关注的店铺信息集合
     */
    @Override
    public PageHelper<AttentionStore> queryAttentionByCustomerId(PageHelper<AttentionStore> pageHelper, long customerId) {
        logger.debug("queryAttentionByCustomerId and customerId:{}", customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        PageHelper<AttentionStore> pageHelperData = pageHelper.setListDates(attentionStoreMapper.queryAttentionByCustomerId(pageHelper.getQueryParams(params, this.queryCustomerAttentionStoreCount(customerId))));
        pageHelperData.getList().forEach(attentionStore -> attentionStore.getAttentionStore(storeInfoMapper.queryAuditPassStoreInfo(attentionStore.getStoreId()),
                skuService.queryFiveDataForAttentionStore(attentionStore.getStoreId()), attentionStoreMapper.queryStoreAttentionCountByStoreId(attentionStore.getStoreId())));
        return pageHelperData;
    }

    /**
     * 根据店铺id和会员id取消关注
     *
     * @param storeId    店铺id
     * @param customerId 会员id
     * @return 删除返回码
     */
    @Override
    public int cancelStoreAttention(long storeId, long customerId) {
        logger.debug("queryAttentionByCustomerId and customerId:{}\r\n storeId:{}", customerId, storeId);
        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("customerId", customerId);
        return attentionStoreMapper.cancelStoreAttention(map);
    }

    @Override
    public int queryNumByStore(long storeId) {
        logger.debug("queryNumByStore and storeId:{}", storeId);
        return attentionStoreMapper.queryStoreAttentionCountByStoreId(storeId);
    }

    @Override
    public int queryCustomerAttentionStoreCount(long customerId) {
        logger.debug("queryCustomerAttentionStoreCount and customerId:{}", customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        return attentionStoreMapper.queryAttentionByCustomerIdCount(params);
    }

    @Override
    public int attentionStore(long customerId, long storeId) {
        logger.debug("attentionStore and customerId:{} \r\n storeId:{}", customerId, storeId);

        // 判断用户是否已经关注该店铺
        if (hasAttention(customerId, storeId)) {
            logger.error("attentionStore fail due to has alerdy attention  store...");
            return -1;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("storeId", storeId);

        return attentionStoreMapper.attentionStore(params);
    }

    /**
     * 判断用户是否关注了店铺
     *
     * @param customerId 会员id
     * @param storeId    店铺id
     * @return 关注返回true  没关注返回false
     */
    private boolean hasAttention(long customerId, long storeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("storeId", storeId);
        return attentionStoreMapper.queryStoreAttentionByCustomerIdCount(params) != 0;
    }
}
