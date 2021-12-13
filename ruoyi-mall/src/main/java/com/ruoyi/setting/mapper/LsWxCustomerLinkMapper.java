package com.ruoyi.setting.mapper;

import com.ruoyi.setting.domain.LsWxCustomerLink;

import java.util.List;

/**
 * 微信登录和商城用户的关联Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
public interface LsWxCustomerLinkMapper {
    /**
     * 查询微信登录和商城用户的关联
     *
     * @param id 微信登录和商城用户的关联ID
     * @return 微信登录和商城用户的关联
     */
    public LsWxCustomerLink selectLsWxCustomerLinkById(Long id);

    /**
     * 查询微信登录和商城用户的关联列表
     *
     * @param lsWxCustomerLink 微信登录和商城用户的关联
     * @return 微信登录和商城用户的关联集合
     */
    public List<LsWxCustomerLink> selectLsWxCustomerLinkList(LsWxCustomerLink lsWxCustomerLink);

    /**
     * 新增微信登录和商城用户的关联
     *
     * @param lsWxCustomerLink 微信登录和商城用户的关联
     * @return 结果
     */
    public int insertLsWxCustomerLink(LsWxCustomerLink lsWxCustomerLink);

    /**
     * 修改微信登录和商城用户的关联
     *
     * @param lsWxCustomerLink 微信登录和商城用户的关联
     * @return 结果
     */
    public int updateLsWxCustomerLink(LsWxCustomerLink lsWxCustomerLink);

    /**
     * 删除微信登录和商城用户的关联
     *
     * @param id 微信登录和商城用户的关联ID
     * @return 结果
     */
    public int deleteLsWxCustomerLinkById(Long id);

    /**
     * 批量删除微信登录和商城用户的关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLsWxCustomerLinkByIds(Long[] ids);
}
