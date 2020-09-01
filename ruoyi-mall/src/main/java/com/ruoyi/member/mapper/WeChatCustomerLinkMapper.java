package com.ruoyi.member.mapper;


import com.ruoyi.member.domain.WeChatCustomerLink;
import org.springframework.stereotype.Repository;

/**
 * 微信用户关联数据库接口
 */
@Repository
public interface WeChatCustomerLinkMapper {

    /**
     * 根据openId查询关联实体
     *
     * @param openId 微信用户唯一标识
     */

    WeChatCustomerLink queryWeChatCustomerLinkByOpenId(String openId);


    /**
     * 根据customerId查询关联实体
     *
     * @param customerId 用户id
     */

    WeChatCustomerLink queryWeChatCustomerLinkByCustomerId(long customerId);

    /**
     * 根据unionId查询关联实体
     *
     * @param unionId 微信联合登录id
     */

    WeChatCustomerLink queryWeChatCustomerLinkByUnionId(String unionId);

    /**
     * 增加关联
     *
     * @param weChatCustomerLink 关联实体
     * @return 1:成功
     */

    int addWeChatCustomerLink(WeChatCustomerLink weChatCustomerLink);

    /**
     * 删除关联
     *
     * @param customerId 用户id
     * @return 1:成功
     */

    int deleteWeChatCustomerLink(long customerId);

    /**
     * 更新微信用户关联
     *
     * @param weChatCustomerLink 微信用户关联实体
     * @return 1成功 否则失败
     */

    int updateWeChatCustomerLink(WeChatCustomerLink weChatCustomerLink);
}

