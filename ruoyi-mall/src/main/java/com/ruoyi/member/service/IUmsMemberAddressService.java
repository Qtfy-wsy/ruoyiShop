package com.ruoyi.member.service;

import com.ruoyi.member.domain.UmsMemberAddress;

import java.util.List;

/**
 * 用户收货地址Service接口
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
public interface IUmsMemberAddressService {
    /**
     * 查询用户收货地址
     *
     * @param id 用户收货地址ID
     * @return 用户收货地址
     */
    public UmsMemberAddress selectUmsMemberAddressById(Long id);

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
     * 批量删除用户收货地址
     *
     * @param ids 需要删除的用户收货地址ID
     * @return 结果
     */
    public int deleteUmsMemberAddressByIds(Long[] ids);

    /**
     * 查询用户的默认收货地址
     *
     * @param customerId 用户id
     * @return 返回用户的默认收货地址
     */
    UmsMemberAddress queryCustomerDefaultAddress(Long customerId);

    /**
     * 根据addressId 查询收货地址
     *
     * @param customerId 用户id
     * @param addressId  收货地址id
     * @return 收货地址
     */
    UmsMemberAddress queryCustomerAddressById(Long customerId, Long addressId);

    /**
     * 查询用户所有收货地址(不包含删除的)
     *
     * @param customerId 用户id
     * @return 收货地址集合
     */
    List<UmsMemberAddress> queryCustomerAllAddress(Long customerId);

    /**
     * 删除收货地址
     *
     * @param customerId 用户id
     * @param addressId  地址id
     * @return 1：成功
     */
    int deleteCustomerAddressById(Long customerId, Long addressId);

    /**
     * 根据用户id重置所有收货地址为非默认
     *
     * @param customerId 用户id
     * @return 1：成功
     */
    int clearDefaultAddressByCustomerId(Long customerId);

    /**
     * 设置默认收货地址
     *
     * @param customerId 用户id
     * @param addressId  地址id
     * @return 1：成功
     */
    int setDefaultAddressById(Long customerId, Long addressId);

    /**
     * 新增收货地址
     *
     * @param customerAddress 地址实体
     * @return 1：成功 -1：已达上限
     */
    int addCustomerAddress(UmsMemberAddress customerAddress);

    /**
     * 更新收货地址
     *
     * @param customerAddress 地址实体
     * @return 1:成功 -1:手机格式不对  -2:电话格式不对
     */
    int updateCustomerAddress(UmsMemberAddress customerAddress);

    /**
     * 查找用户默认选中地址
     *
     * @param customerId 用户id
     * @return 默认选中地址
     */
    UmsMemberAddress queryCustomerDefaultChosenAddress(long customerId);

    /**
     * 删除用户收货地址信息
     *
     * @param id 用户收货地址ID
     * @return 结果
     */
    public int deleteUmsMemberAddressById(Long id);
}
