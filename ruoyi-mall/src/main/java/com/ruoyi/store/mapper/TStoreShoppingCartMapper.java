package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreShoppingCart;

import java.util.List;
import java.util.Map;

/**
 * 门店购物车Mapper接口
 *
 * @author 商城
 */
public interface TStoreShoppingCartMapper {
    /**
     * 查询门店购物车
     *
     * @param id 门店购物车ID
     * @return 门店购物车
     */
    public TStoreShoppingCart selectTStoreShoppingCartById(Long id);

    /**
     * 增加门店购物车
     *
     * @param storeShoppingCart 门店购物车
     * @return 成功返回1 失败返回0
     */

    int addShoppingCart(TStoreShoppingCart storeShoppingCart);

    /**
     * 查询用户门店购物车中单品的数量
     *
     * @param params 查询参数
     * @return 返回用户门店购物车中单品的数量
     */

    TStoreShoppingCart queryCustomerSkuCount(Map<String, Object> params);

    /**
     * 更新门店购物车中单的数量（累加）
     *
     * @param params 查询参数
     * @return 成功返回1 失败返回0
     */

    int updateShoppingCartNum(Map<String, Object> params);

    /**
     * 更新门店购物车的数量（直接更新）
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int directUpdateShoppingCartNum(Map<String, Object> params);

    /**
     * 查询用户门店购物车
     *
     * @param customerId 用户id
     * @return 返回用户门店购物车
     */

    List<TStoreShoppingCart> queryByCustomerId(long customerId);

    /**
     * 根据门店购物车id和用户id查询门店购物车信息
     *
     * @param params 查询参数
     * @return 返回用户门店购物车信息
     */

    List<TStoreShoppingCart> queryByIds(Map<String, Object> params);

    /**
     * 查询用户门店购物车中单品的总数
     *
     * @param customerId 会员id
     * @return 返回用户门店购物车中单品的总数
     */

    int queryStoreShoppingCartCount(long customerId);


    /**
     * 删除门店购物车
     *
     * @param params 查询
     * @return 成功》1 失败 = 0
     */

    int deleteStoreShoppingCart(Map<String, Object> params);

    /**
     * 查询门店购物车列表
     *
     * @param tStoreShoppingCart 门店购物车
     * @return 门店购物车集合
     */
    public List<TStoreShoppingCart> selectTStoreShoppingCartList(TStoreShoppingCart tStoreShoppingCart);

    /**
     * 新增门店购物车
     *
     * @param tStoreShoppingCart 门店购物车
     * @return 结果
     */
    public int insertTStoreShoppingCart(TStoreShoppingCart tStoreShoppingCart);

    /**
     * 修改门店购物车
     *
     * @param tStoreShoppingCart 门店购物车
     * @return 结果
     */
    public int updateTStoreShoppingCart(TStoreShoppingCart tStoreShoppingCart);

    /**
     * 删除门店购物车
     *
     * @param id 门店购物车ID
     * @return 结果
     */
    public int deleteTStoreShoppingCartById(Long id);

    /**
     * 批量删除门店购物车
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreShoppingCartByIds(Long[] ids);
}
