package com.ruoyi.member.service;


import com.ruoyi.member.domain.WeChatCustomerLink;
import org.springframework.stereotype.Repository;

/**
 * 微信用户关联服务接口
 */
@Repository
public interface WeChatCustomerLinkService {

    /**
     * 增加关联
     *
     * @param weChatCustomerLink 关联实体
     * @return 1:成功
     */
    int addWeChatCustomerLink(WeChatCustomerLink weChatCustomerLink);

    /**
     * 根据openId查询关联实体
     *
     * @param openId 微信用户唯一标识
     */
    WeChatCustomerLink queryWeChatCustomerLinkByOpenId(String openId);


    /**
     * 根据用户id查询关联实体
     *
     * @param customerId 用户id
     */
    WeChatCustomerLink queryWeChatCustomerLinkByCustomerId(long customerId);

    /**
     * 根据用户名查询关联实体
     *
     * @param userName 用户名
     */
    WeChatCustomerLink queryWeChatCustomerLinkByUserName(String userName);

    /**
     * 根据unionId查询关联实体
     *
     * @param unionId 微信联合登录id
     */
    WeChatCustomerLink queryWeChatCustomerLinkByUnionId(String unionId);


    /**
     * 解绑账号
     *
     * @param customerId 用户id
     * @param unionId    联合登录id,redis缓存使用
     * @return 1:成功
     */
    int unbindWeChatCustomerLink(long customerId, String unionId);


    /**
     * 更新微信用户关联
     *
     * @param weChatCustomerLink 微信用户关联实体
     * @return 1成功 否则失败
     */
    int updateWeChatCustomerLink(WeChatCustomerLink weChatCustomerLink);

}

