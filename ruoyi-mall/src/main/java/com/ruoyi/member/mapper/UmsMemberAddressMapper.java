package com.ruoyi.member.mapper;

import com.ruoyi.member.domain.UmsMemberAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户收货地址Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
public interface UmsMemberAddressMapper {
    /**
     * 查询用户收货地址
     *
     * @param id 用户收货地址ID
     * @return 用户收货地址
     */
    public UmsMemberAddress selectUmsMemberAddressById(Long id);

    /**
     * 查询用户的默认收货地址
     *
     * @param customerId 用户id
     * @return 返回用户的默认收货地址
     */

    UmsMemberAddress queryCustomerDefaultAddressByCustomerId(Long customerId);

    /**
     * 查询用户的所有收货地址
     *
     * @param customerId 用户id
     * @return 返回用户的所有收货地址
     */

    List<UmsMemberAddress> queryCustomerAllAddressByCustomerId(Long customerId);

    /**
     * 根据id查询收货地址
     *
     * @return 返回用户的所有收货地址
     */

    UmsMemberAddress queryCustomerAddressById(Map map);

    /**
     * 根据id删除收货地址
     */

    int deleteCustomerAddressById(Map map);

    /**
     * 根据用户id重置所有收货地址为非默认
     *
     * @param customerId 用户id
     */

    int clearDefaultAddressByCustomerId(Long customerId);

    /**
     * 设置默认收货地址
     */

    int setDefaultAddressById(Map map);

    /**
     * 增加收货地址
     *
     * @param customerAddress 地址对象
     */

    int addCustomerAddress(UmsMemberAddress customerAddress);

    /**
     * 更新收货地址
     *
     * @param customerAddress 地址对象
     */

    int updateCustomerAddress(UmsMemberAddress customerAddress);

    /**
     * 查找用户默认选中地址
     *
     * @param customerId 用户id
     * @return 默认选中地址
     */

    UmsMemberAddress queryCustomerDefaultChosenAddress(@Param("customerId") long customerId);

    /**
     * 查询用户收货地址列表
     *
     * @param umsMemberAddress 用户收货地址
     * @return 用户收货地址集合
     */
    public List<UmsMemberAddress> selectUmsMemberAddressList(UmsMemberAddress umsMemberAddress);

    /**
     * 新增用户收货地址
     *
     * @param umsMemberAddress 用户收货地址
     * @return 结果
     */
    public int insertUmsMemberAddress(UmsMemberAddress umsMemberAddress);

    /**
     * 修改用户收货地址
     *
     * @param umsMemberAddress 用户收货地址
     * @return 结果
     */
    public int updateUmsMemberAddress(UmsMemberAddress umsMemberAddress);

    /**
     * 删除用户收货地址
     *
     * @param id 用户收货地址ID
     * @return 结果
     */
    public int deleteUmsMemberAddressById(Long id);

    /**
     * 批量删除用户收货地址
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUmsMemberAddressByIds(Long[] ids);
}
