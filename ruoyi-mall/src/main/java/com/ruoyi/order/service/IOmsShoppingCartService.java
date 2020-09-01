package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsShoppingCart;
import com.ruoyi.order.vo.ShoppingCartNum;

import java.util.List;
import java.util.Map;

/**
 * 购物车Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IOmsShoppingCartService {
    /**
     * 增加购物车
     *
     * @param shoppingCart 新增购物车
     * @return 成功返回1 失败返回 0
     */
    int addShoppingCart(OmsShoppingCart shoppingCart);

    /**
     * 修改购物车的数量(直接修改)
     *
     * @param shoppingCart 购物车
     * @return 成功返回1 失败返回0
     */
    int updateShoppingCartNum(OmsShoppingCart shoppingCart);

    /**
     * 修改购物车数量(累加)
     *
     * @param params 购物车参数
     * @return 成功返回1 失败返回0
     */
    int updateAddShoppingCartNum(Map<String, Object> params);

    /**
     * 删除购物车
     *
     * @param customerId 会员id
     * @param ids        购物车ids
     * @return 成功返回1 失败返回0
     */
    int deleteShoppingCart(long customerId, Long[] ids);

    /**
     * 根据skuId删除购物车
     *
     * @param skuId 单品id
     * @return 删除条数 -1:没有skuId
     */
    int deleteShoppingCartBySkuId(String skuId);

    /**
     * 查询会员购物车总数
     *
     * @param customerId 会员id
     * @return 返回会员的购物车总数
     */
    ShoppingCartNum queryShoppingCartCount(long customerId);

    /**
     * 查询用户购物车
     *
     * @param customerId 用户id
     * @return 返回用户购物车
     */
    List<OmsShoppingCart> queryByCustomerId(long customerId);

    /**
     * 更新购物车的促销信息
     *
     * @param shoppingCart 购物车信息
     * @return 成功返回1 失败返回0
     */
    int updateMarketing(OmsShoppingCart shoppingCart);

    /**
     * 根据购物车id查询购物车信息
     *
     * @param ids        购物车id
     * @param customerId 用户id
     * @return 返回购物车信息
     */
    List<OmsShoppingCart> queryByIds(Long[] ids, Long customerId);

    /**
     * 查询用户某个单品在购物车中的数量
     *
     * @param skuId      单品id
     * @param customerId 会员id
     * @return 返回单品在购物车中的数量
     */
    int queryBySkuIdAndCustomerIdCount(String skuId, long customerId);

    /**
     * 查询购物车
     *
     * @param id 购物车ID
     * @return 购物车
     */
    public OmsShoppingCart selectOmsShoppingCartById(Long id);

    /**
     * 查询购物车列表
     *
     * @param omsShoppingCart 购物车
     * @return 购物车集合
     */
    public List<OmsShoppingCart> selectOmsShoppingCartList(OmsShoppingCart omsShoppingCart);

    /**
     * 新增购物车
     *
     * @param omsShoppingCart 购物车
     * @return 结果
     */
    public int insertOmsShoppingCart(OmsShoppingCart omsShoppingCart);

    /**
     * 修改购物车
     *
     * @param omsShoppingCart 购物车
     * @return 结果
     */
    public int updateOmsShoppingCart(OmsShoppingCart omsShoppingCart);

    /**
     * 批量删除购物车
     *
     * @param ids 需要删除的购物车ID
     * @return 结果
     */
    public int deleteOmsShoppingCartByIds(Long[] ids);

    /**
     * 删除购物车信息
     *
     * @param id 购物车ID
     * @return 结果
     */
    public int deleteOmsShoppingCartById(Long id);

    int clearShoppingCart(long customerId);
}
