package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.store.domain.TStoreShoppingCart;
import com.ruoyi.store.mapper.TStoreShoppingCartMapper;
import com.ruoyi.store.service.ITStoreShoppingCartService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 门店购物车Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class TStoreShoppingCartServiceImpl implements ITStoreShoppingCartService {
    private static final Logger log = LoggerFactory.getLogger(TStoreShoppingCartServiceImpl.class);
    @Autowired
    private TStoreShoppingCartMapper tStoreShoppingCartMapper;
    /**
     * 注入门店购物车数据库接口
     */
    @Autowired
    private TStoreShoppingCartMapper storeShoppingCartMapper;

    @Override
    public int addShoppingCart(TStoreShoppingCart storeShoppingCart) {
        log.debug("addShoppingCart and storeShoppingCart:{}", storeShoppingCart);

        if (Objects.isNull(storeShoppingCart)) {
            log.error("addShoppingCart due to storeShoppingCart is null....");
            return 0;
        }
        return storeShoppingCartMapper.addShoppingCart(storeShoppingCart);
    }

    @Override
    public int queryCustomerSkuCount(long customerId, long storeId, String skuId) {
        log.debug("queryCustomerSkuCount and customerId:{} \r\n storeId:{} \r\n skuId:{}", customerId, storeId, skuId);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("storeId", storeId);
        params.put("skuId", skuId);

        TStoreShoppingCart storeShoppingCart = storeShoppingCartMapper.queryCustomerSkuCount(params);

        return Objects.isNull(storeShoppingCart) ? 0 : storeShoppingCart.getNum();
    }

    @Override
    public int updateShoppingCartNum(TStoreShoppingCart storeShoppingCart) {
        log.debug("updateShoppingCartNum and storeShoppingCart:{}", storeShoppingCart);

        if (Objects.isNull(storeShoppingCart)) {
            log.error("updateShoppingCartNum and storeShoppingCart:{}", storeShoppingCart);
            return 0;
        }

        Map<String, Object> params = new HashMap<>();

        params.put("customerId", storeShoppingCart.getCustomerId());
        params.put("skuId", storeShoppingCart.getSkuId());
        params.put("storeId", storeShoppingCart.getStoreId());
        params.put("num", storeShoppingCart.getNum());

        return storeShoppingCartMapper.updateShoppingCartNum(params);
    }

    @Override
    public int directUpdateShoppingCartNum(TStoreShoppingCart storeShoppingCart) {
        log.debug("directUpdateShoppingCartNum and storeShoppingCart:{}", storeShoppingCart);
        if (Objects.isNull(storeShoppingCart)) {
            log.error("directUpdateShoppingCartNum fail due to storeShoppingCart is null....");
            return 0;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("id", storeShoppingCart.getId());
        params.put("num", storeShoppingCart.getNum());
        params.put("customerId", storeShoppingCart.getCustomerId());
        return storeShoppingCartMapper.directUpdateShoppingCartNum(params);
    }

    @Override
    public List<TStoreShoppingCart> queryByCustomerId(long customerId) {
        log.debug("queryByCustomerId and customerId:{}", customerId);
        return storeShoppingCartMapper.queryByCustomerId(customerId);
    }

    @Override
    public List<TStoreShoppingCart> queryByIds(Long[] ids, Long customerId) {
        log.debug("queryByIds and ids:{} \r\n customerId:{}", ids, customerId);

        if (ArrayUtils.isEmpty(ids)) {
            log.error("queryByIds fail due to ids is empty....");
            return Collections.emptyList();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ids", Arrays.asList(ids));
        params.put("customerId", customerId);

        return storeShoppingCartMapper.queryByIds(params);
    }

    @Override
    public int queryStoreShoppingCartCount(long customerId) {
        log.debug("queryStoreShoppingCartCount and customerId:{}", customerId);
        return storeShoppingCartMapper.queryStoreShoppingCartCount(customerId);
    }

    @Override
    public int deleteStoreShoppingCart(long customerId, Long[] ids) {
        log.debug("deleteStoreShoppingCart and customerId:{}\r\n ids:{}", customerId, ids);


        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("ids", Arrays.asList(ids));

        return storeShoppingCartMapper.deleteStoreShoppingCart(params);
    }

    /**
     * 查询门店购物车
     *
     * @param id 门店购物车ID
     * @return 门店购物车
     */
    @Override
    public TStoreShoppingCart selectTStoreShoppingCartById(Long id) {
        return tStoreShoppingCartMapper.selectTStoreShoppingCartById(id);
    }

    /**
     * 查询门店购物车列表
     *
     * @param tStoreShoppingCart 门店购物车
     * @return 门店购物车
     */
    @Override
    public List<TStoreShoppingCart> selectTStoreShoppingCartList(TStoreShoppingCart tStoreShoppingCart) {
        return tStoreShoppingCartMapper.selectTStoreShoppingCartList(tStoreShoppingCart);
    }

    /**
     * 新增门店购物车
     *
     * @param tStoreShoppingCart 门店购物车
     * @return 结果
     */
    @Override
    public int insertTStoreShoppingCart(TStoreShoppingCart tStoreShoppingCart) {
        tStoreShoppingCart.setCreateTime(DateUtils.getNowDate());
        return tStoreShoppingCartMapper.insertTStoreShoppingCart(tStoreShoppingCart);
    }

    /**
     * 修改门店购物车
     *
     * @param tStoreShoppingCart 门店购物车
     * @return 结果
     */
    @Override
    public int updateTStoreShoppingCart(TStoreShoppingCart tStoreShoppingCart) {
        return tStoreShoppingCartMapper.updateTStoreShoppingCart(tStoreShoppingCart);
    }

    /**
     * 批量删除门店购物车
     *
     * @param ids 需要删除的门店购物车ID
     * @return 结果
     */
    @Override
    public int deleteTStoreShoppingCartByIds(Long[] ids) {
        return tStoreShoppingCartMapper.deleteTStoreShoppingCartByIds(ids);
    }

    /**
     * 删除门店购物车信息
     *
     * @param id 门店购物车ID
     * @return 结果
     */
    @Override
    public int deleteTStoreShoppingCartById(Long id) {
        return tStoreShoppingCartMapper.deleteTStoreShoppingCartById(id);
    }
}
