package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.order.domain.OmsShoppingCart;
import com.ruoyi.order.mapper.OmsShoppingCartMapper;
import com.ruoyi.order.service.IOmsShoppingCartService;
import com.ruoyi.order.vo.ShoppingCartNum;
import com.ruoyi.store.service.ITStoreShoppingCartService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 购物车Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class OmsShoppingCartServiceImpl implements IOmsShoppingCartService {
    @Autowired
    private OmsShoppingCartMapper omsShoppingCartMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(OmsShoppingCartServiceImpl.class);

    /**
     * 注入购物车数据库接口
     */
    @Autowired
    private OmsShoppingCartMapper shoppingCartMapper;

    /**
     * 注入门店购物车服务接口
     */
    @Autowired
    private ITStoreShoppingCartService storeShoppingCartService;

    /**
     * 注入单品服务
     */
    @Autowired
    private IPmsSkuService skuService;

    @Override
    public int addShoppingCart(OmsShoppingCart shoppingCart) {
        logger.debug("addShoppingCart and shoppingCart:{}", shoppingCart);
        if (Objects.isNull(shoppingCart)) {
            logger.error("addShoppingCart fail due to shoppingCart is empty..");
            return 0;
        }
        shoppingCart.setCreateTime(new Date());
        return shoppingCartMapper.addShoppingCart(shoppingCart);
    }

    @Override
    public int updateShoppingCartNum(OmsShoppingCart shoppingCart) {

        logger.debug("updateShoppingCartNum and shoppingCart:{}", shoppingCart);

        if (Objects.isNull(shoppingCart)) {
            logger.error("updateShoppingCartNum fail due to params is null....");
            return 0;
        }
        if (!checkLimitNum(shoppingCart)) {
            logger.error("updateShoppingCartNum fail:checkLimitNum fail");
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", shoppingCart.getId());
        params.put("num", shoppingCart.getNum());
        params.put("customerId", shoppingCart.getCustomerId());

        return shoppingCartMapper.modifyShoppingCartNum(params);
    }

    /**
     * 校验起订数量
     *
     * @param shoppingCart 购物车实体
     * @return 成功返回true, 否则返回false
     */
    private boolean checkLimitNum(OmsShoppingCart shoppingCart) {
        List<OmsShoppingCart> oldShoppingCartList = this.queryByIds(new Long[]{shoppingCart.getId()}, shoppingCart.getCustomerId());
        if (!CollectionUtils.isEmpty(oldShoppingCartList)) {
            OmsShoppingCart oldShoppingCart = oldShoppingCartList.get(0);
            PmsSku sku = skuService.querySkuById(oldShoppingCart.getSkuId());
            if (Objects.isNull(sku)) {
                logger.error("checkLimitNum fail:sku is not exists");
                return false;
            } else {
                skuService.setSkuDetail(sku, PmsSkuItem.BATCH);
                return shoppingCart.getNum() >= sku.getLimitBatchNum();
            }
        } else {
            logger.error("checkLimitNum fail:shoppingCart is not exists");
            return false;
        }
    }

    @Override
    public int updateAddShoppingCartNum(Map<String, Object> params) {
        logger.debug("updateAddShoppingCartNum and params:{}", params);
        return shoppingCartMapper.updateShoppingCartNum(params);
    }

    @Override
    public int deleteShoppingCart(long customerId, Long[] ids) {
        logger.debug("deleteShoppingCart customerId:{} \r\n ids:{}", customerId, ids);

        if (ArrayUtils.isEmpty(ids)) {
            logger.error("deleteShoppingCart fail due to params is null...");
            return 0;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("ids", Arrays.asList(ids));

        return shoppingCartMapper.deleteShoppingCart(params);
    }

    @Override
    public int clearShoppingCart(long customerId) {
        logger.debug("deleteShoppingCart customerId:{}", customerId);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        return shoppingCartMapper.deleteShoppingCart(params);
    }

    @Override
    public int deleteShoppingCartBySkuId(String skuId) {
        logger.debug("deleteShoppingCartBySkuId and skuId:{}", skuId);
        if (StringUtils.isEmpty(skuId)) {
            logger.error("deleteShoppingCartBySkuId fail : skuId is empty");
            return -1;
        }
        return shoppingCartMapper.deleteShoppingCartBySkuId(skuId);
    }

    @Override
    public ShoppingCartNum queryShoppingCartCount(long customerId) {

        return ShoppingCartNum.build(shoppingCartMapper.queryShoppingCartCount(customerId), storeShoppingCartService.queryStoreShoppingCartCount(customerId));
    }


    @Override
    public List<OmsShoppingCart> queryByCustomerId(long customerId) {
        logger.debug("queryByCustomerId and customerId:{}", customerId);
        return shoppingCartMapper.queryByCustomerId(customerId);
    }

    @Override
    public int updateMarketing(OmsShoppingCart shoppingCart) {

        logger.debug("updateMarketing and shoppingCart:{}", shoppingCart);

        if (Objects.isNull(shoppingCart)) {
            logger.error("updateMarketing fail due to shoppingCart is null...");
            return 0;
        }

        return shoppingCartMapper.updateMarketing(shoppingCart);
    }

    @Override
    public List<OmsShoppingCart> queryByIds(Long[] ids, Long customerId) {

        logger.debug("queryByIds and ids:{}", ids);

        if (ArrayUtils.isEmpty(ids)) {
            logger.error("queryByIds fail due to ids is empty....");
            return Collections.emptyList();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ids", Arrays.asList(ids));
        params.put("customerId", customerId);
        return shoppingCartMapper.queryByIds(params);
    }

    @Override
    public int queryBySkuIdAndCustomerIdCount(String skuId, long customerId) {

        logger.debug("queryBySkuIdAndCustomerIdCount and skuId:{} \r\n customerId:{}", skuId, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        params.put("customerId", customerId);

        //根据用户id和单品id查询购物车信息
        OmsShoppingCart shoppingCart = shoppingCartMapper.queryBySkuIdAndCustomerIdCount(params);

        return Objects.isNull(shoppingCart) ? 0 : shoppingCart.getNum();
    }

    /**
     * 查询购物车
     *
     * @param id 购物车ID
     * @return 购物车
     */
    @Override
    public OmsShoppingCart selectOmsShoppingCartById(Long id) {
        return omsShoppingCartMapper.selectOmsShoppingCartById(id);
    }

    /**
     * 查询购物车列表
     *
     * @param omsShoppingCart 购物车
     * @return 购物车
     */
    @Override
    public List<OmsShoppingCart> selectOmsShoppingCartList(OmsShoppingCart omsShoppingCart) {
        return omsShoppingCartMapper.selectOmsShoppingCartList(omsShoppingCart);
    }

    /**
     * 新增购物车
     *
     * @param omsShoppingCart 购物车
     * @return 结果
     */
    @Override
    public int insertOmsShoppingCart(OmsShoppingCart omsShoppingCart) {
        omsShoppingCart.setCreateTime(DateUtils.getNowDate());
        return omsShoppingCartMapper.insertOmsShoppingCart(omsShoppingCart);
    }

    /**
     * 修改购物车
     *
     * @param omsShoppingCart 购物车
     * @return 结果
     */
    @Override
    public int updateOmsShoppingCart(OmsShoppingCart omsShoppingCart) {
        return omsShoppingCartMapper.updateOmsShoppingCart(omsShoppingCart);
    }

    /**
     * 批量删除购物车
     *
     * @param ids 需要删除的购物车ID
     * @return 结果
     */
    @Override
    public int deleteOmsShoppingCartByIds(Long[] ids) {
        return omsShoppingCartMapper.deleteOmsShoppingCartByIds(ids);
    }

    /**
     * 删除购物车信息
     *
     * @param id 购物车ID
     * @return 结果
     */
    @Override
    public int deleteOmsShoppingCartById(Long id) {
        return omsShoppingCartMapper.deleteOmsShoppingCartById(id);
    }
}
