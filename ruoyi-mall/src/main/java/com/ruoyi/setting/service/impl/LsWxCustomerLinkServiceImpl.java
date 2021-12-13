package com.ruoyi.setting.service.impl;

import com.ruoyi.setting.domain.LsWxCustomerLink;
import com.ruoyi.setting.mapper.LsWxCustomerLinkMapper;
import com.ruoyi.setting.service.ILsWxCustomerLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信登录和商城用户的关联Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
@Service
public class LsWxCustomerLinkServiceImpl implements ILsWxCustomerLinkService {
    private static final Logger logger = LoggerFactory.getLogger(LsWxCustomerLinkServiceImpl.class);
    @Autowired
    private LsWxCustomerLinkMapper lsWxCustomerLinkMapper;

    /**
     * 查询微信登录和商城用户的关联
     *
     * @param id 微信登录和商城用户的关联ID
     * @return 微信登录和商城用户的关联
     */
    @Override
    public LsWxCustomerLink selectLsWxCustomerLinkById(Long id) {
        return lsWxCustomerLinkMapper.selectLsWxCustomerLinkById(id);
    }

    /**
     * 查询微信登录和商城用户的关联列表
     *
     * @param lsWxCustomerLink 微信登录和商城用户的关联
     * @return 微信登录和商城用户的关联
     */
    @Override
    public List<LsWxCustomerLink> selectLsWxCustomerLinkList(LsWxCustomerLink lsWxCustomerLink) {
        return lsWxCustomerLinkMapper.selectLsWxCustomerLinkList(lsWxCustomerLink);
    }

    /**
     * 新增微信登录和商城用户的关联
     *
     * @param lsWxCustomerLink 微信登录和商城用户的关联
     * @return 结果
     */
    @Override
    public int insertLsWxCustomerLink(LsWxCustomerLink lsWxCustomerLink) {
        return lsWxCustomerLinkMapper.insertLsWxCustomerLink(lsWxCustomerLink);
    }

    /**
     * 修改微信登录和商城用户的关联
     *
     * @param lsWxCustomerLink 微信登录和商城用户的关联
     * @return 结果
     */
    @Override
    public int updateLsWxCustomerLink(LsWxCustomerLink lsWxCustomerLink) {
        return lsWxCustomerLinkMapper.updateLsWxCustomerLink(lsWxCustomerLink);
    }

    /**
     * 批量删除微信登录和商城用户的关联
     *
     * @param ids 需要删除的微信登录和商城用户的关联ID
     * @return 结果
     */
    @Override
    public int deleteLsWxCustomerLinkByIds(Long[] ids) {
        return lsWxCustomerLinkMapper.deleteLsWxCustomerLinkByIds(ids);
    }

    /**
     * 删除微信登录和商城用户的关联信息
     *
     * @param id 微信登录和商城用户的关联ID
     * @return 结果
     */
    @Override
    public int deleteLsWxCustomerLinkById(Long id) {
        return lsWxCustomerLinkMapper.deleteLsWxCustomerLinkById(id);
    }
}
