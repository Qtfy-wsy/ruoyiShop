package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsShoppingCart;

import java.util.List;
import java.util.Map;

/**
 * 购物车Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface OmsShoppingCartMapper {
    /**
     * 查询购物车
     *
     * @param id 购物车ID
     * @return 购物车
     */
    public OmsShoppingCart selectOmsShoppingCartById(Long id);

    /**
     * 查询购物车中用户的指定单品的数量
     *
     * @param params 查询参数
     * @return 返回用户购物车中指定商品的数量
     */

    OmsShoppingCart queryBySkuIdAndCustomerIdCount(Map<String, Object> params);

    /**
     * 更改购物车的数量 (累加)
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updateShoppingCartNum(Map<String, Object> params);

    /**
     * 新增购物车
     *
     * @param shoppingCart 购物车数据
     * @return 成功返回1 失败返回0
     */

    int addShoppingCart(OmsShoppingCart shoppingCart);

    /**
     * 修改购物车数量 (直接修改购物车数量)
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int modifyShoppingCartNum(Map<String, Object> params);

    /**
     * 删除购物车
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int deleteShoppingCart(Map<String, Object> params);

    /**
     * 根据单品id删除购物车记录
     *
     * @param skuId 单品id
     * @return 删除的记录数
     */

    int deleteShoppingCartBySkuId(String skuId);

    /**
     * 查询会员购物车总数
     *
     * @param customerId 会员id
     * @return 返回会员购物车总数
     */

    int queryShoppingCartCount(long customerId);

    /**
     * 查询会员的购物车信息
     *
     * @param customerId 会员id
     * @return 返回会员的购物车信息
     */

    List<OmsShoppingCart> queryByCustomerId(long customerId);

    /**
     * 更新购物车促销
     *
     * @param shoppingCart 购物车信息
     * @return 成功返回1 失败返回0
     */

    int updateMarketing(OmsShoppingCart shoppingCart);

    /**
     * 根据购物车id查询购物车信息
     *
     * @param params 查询参数
     * @return 返回购物车信息
     */

    List<OmsShoppingCart> queryByIds(Map<String, Object> params);

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
     * 删除购物车
     *
     * @param id 购物车ID
     * @return 结果
     */
    public int deleteOmsShoppingCartById(Long id);

    /**
     * 批量删除购物车
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsShoppingCartByIds(Long[] ids);
}
