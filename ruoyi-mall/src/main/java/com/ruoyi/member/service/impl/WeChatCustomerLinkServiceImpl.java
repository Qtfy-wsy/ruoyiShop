package com.ruoyi.member.service.impl;


import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.domain.WeChatCustomerLink;
import com.ruoyi.member.mapper.WeChatCustomerLinkMapper;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.WeChatCustomerLinkService;
import com.ruoyi.util.RedisCahceKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 微信用户关联服务实现类
 */
@Service
public class WeChatCustomerLinkServiceImpl implements WeChatCustomerLinkService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(WeChatCustomerLinkServiceImpl.class);
    /**
     * 注入微信用户数据库服务
     */
    @Autowired
    private WeChatCustomerLinkMapper weChatCustomerLinkMapper;


    /**
     * 注入用户服务
     */
    @Autowired
    private IUmsMemberService customerService;


    @CacheEvict(value = RedisCahceKey.WE_CHAT_CUSTOMER_LINK, key = "'WE_CHAT_CUSTOMER_LINK'+#weChatCustomerLink.unionId")
    @Override
    public int addWeChatCustomerLink(WeChatCustomerLink weChatCustomerLink) {
        logger.debug("addWeChatCustomerLink and weChatCustomerLink:{}", weChatCustomerLink);
        return weChatCustomerLinkMapper.addWeChatCustomerLink(weChatCustomerLink);
    }

    @Override
    public WeChatCustomerLink queryWeChatCustomerLinkByOpenId(String openId) {
        logger.debug("queryWeChatCustomerLinkByOpenId and openId:{}", openId);
        return weChatCustomerLinkMapper.queryWeChatCustomerLinkByOpenId(openId);
    }

    @Override
    public WeChatCustomerLink queryWeChatCustomerLinkByCustomerId(long customerId) {
        logger.debug("queryWeChatCustomerLinkByCustomerId and customerId:{}", customerId);
        return weChatCustomerLinkMapper.queryWeChatCustomerLinkByCustomerId(customerId);
    }

    @Override
    public WeChatCustomerLink queryWeChatCustomerLinkByUserName(String userName) {
        logger.debug("queryWeChatCustomerLinkByUserName and userName:{}", userName);
        UmsMember customer = customerService.queryCustomerByNameInWriteDataSource(userName);
        if (ObjectUtils.isEmpty(customer)) {
            logger.error("queryWeChatCustomerLinkByUserName fail: member is null");
            return null;
        }
        return weChatCustomerLinkMapper.queryWeChatCustomerLinkByCustomerId(customer.getId());
    }

    @Cacheable(value = RedisCahceKey.WE_CHAT_CUSTOMER_LINK, key = "'WE_CHAT_CUSTOMER_LINK'+#unionId")
    @Override
    public WeChatCustomerLink queryWeChatCustomerLinkByUnionId(String unionId) {
        logger.debug("queryWeChatCustomerLinkByUnionId and unionId:{}", unionId);
        return weChatCustomerLinkMapper.queryWeChatCustomerLinkByUnionId(unionId);
    }

    @CacheEvict(value = RedisCahceKey.WE_CHAT_CUSTOMER_LINK, key = "'WE_CHAT_CUSTOMER_LINK'+#unionId")
    @Override
    public int unbindWeChatCustomerLink(long customerId, String unionId) {
        logger.debug("unbindWeChatCustomerLink and customerId:{} \r\n unionId:{}", customerId, unionId);
        return weChatCustomerLinkMapper.deleteWeChatCustomerLink(customerId);
    }

    @CacheEvict(value = RedisCahceKey.WE_CHAT_CUSTOMER_LINK, key = "'WE_CHAT_CUSTOMER_LINK'+#weChatCustomerLink.unionId")
    @Override
    public int updateWeChatCustomerLink(WeChatCustomerLink weChatCustomerLink) {
        logger.debug("updateWeChatCustomerLink andweChatCustomerLink:{}", weChatCustomerLink);
        return weChatCustomerLinkMapper.updateWeChatCustomerLink(weChatCustomerLink);
    }
}
