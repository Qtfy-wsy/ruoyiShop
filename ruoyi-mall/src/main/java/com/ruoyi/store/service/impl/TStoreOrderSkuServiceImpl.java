package com.ruoyi.store.service.impl;

import com.ruoyi.store.domain.TStoreOrderSku;
import com.ruoyi.store.mapper.TStoreOrderSkuMapper;
import com.ruoyi.store.service.ITStoreOrderSkuService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 门店订单单品信息Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Slf4j
@Service
public class TStoreOrderSkuServiceImpl implements ITStoreOrderSkuService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreOrderSkuServiceImpl.class);
    @Autowired
    private TStoreOrderSkuMapper tStoreOrderSkuMapper;
    /**
     * 注入门店订单单品数据库接口
     */
    @Autowired
    private TStoreOrderSkuMapper storeOrderSkuMapper;

    @Override
    public List<TStoreOrderSku> queryByStoreOrderId(long orderId) {
        log.debug("queryByStoreOrderId and orderId:{}", orderId);
        return storeOrderSkuMapper.queryByStoreOrderId(orderId);
    }

    @Override
    public int saveStoreOrderSkus(List<TStoreOrderSku> storeOrderSkus) {
        log.debug("saveStoreOrderSkus and orderSkus:{}", storeOrderSkus);
        if (CollectionUtils.isEmpty(storeOrderSkus)) {
            log.error("saveStoreOrderSkus fail:storeOrderSkus is empty");
            return 0;
        }
        return storeOrderSkuMapper.saveStoreOrderSkus(storeOrderSkus);
    }

    @Override
    public List<TStoreOrderSku> queryStoreRecommentSkusThirtyDays(long storeId) {
        log.debug("queryStoreSaleAmountThisWeekGroupByDay and storeId :{}", storeId);
        return storeOrderSkuMapper.queryStoreRecommentSkusThirtyDays(storeId);
    }

    /**
     * 查询门店订单单品信息
     *
     * @param id 门店订单单品信息ID
     * @return 门店订单单品信息
     */
    @Override
    public TStoreOrderSku selectTStoreOrderSkuById(Long id) {
        return tStoreOrderSkuMapper.selectTStoreOrderSkuById(id);
    }

    /**
     * 查询门店订单单品信息列表
     *
     * @param tStoreOrderSku 门店订单单品信息
     * @return 门店订单单品信息
     */
    @Override
    public List<TStoreOrderSku> selectTStoreOrderSkuList(TStoreOrderSku tStoreOrderSku) {
        return tStoreOrderSkuMapper.selectTStoreOrderSkuList(tStoreOrderSku);
    }

    /**
     * 新增门店订单单品信息
     *
     * @param tStoreOrderSku 门店订单单品信息
     * @return 结果
     */
    @Override
    public int insertTStoreOrderSku(TStoreOrderSku tStoreOrderSku) {
        return tStoreOrderSkuMapper.insertTStoreOrderSku(tStoreOrderSku);
    }

    /**
     * 修改门店订单单品信息
     *
     * @param tStoreOrderSku 门店订单单品信息
     * @return 结果
     */
    @Override
    public int updateTStoreOrderSku(TStoreOrderSku tStoreOrderSku) {
        return tStoreOrderSkuMapper.updateTStoreOrderSku(tStoreOrderSku);
    }

    /**
     * 批量删除门店订单单品信息
     *
     * @param ids 需要删除的门店订单单品信息ID
     * @return 结果
     */
    @Override
    public int deleteTStoreOrderSkuByIds(Long[] ids) {
        return tStoreOrderSkuMapper.deleteTStoreOrderSkuByIds(ids);
    }

    /**
     * 删除门店订单单品信息信息
     *
     * @param id 门店订单单品信息ID
     * @return 结果
     */
    @Override
    public int deleteTStoreOrderSkuById(Long id) {
        return tStoreOrderSkuMapper.deleteTStoreOrderSkuById(id);
    }
}
