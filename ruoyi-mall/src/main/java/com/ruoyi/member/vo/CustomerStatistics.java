package com.ruoyi.member.vo;


import com.ruoyi.member.domain.UmsMember;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by 魔金商城 on 17/12/5.
 * 会员统计实体
 */
@Data
public class CustomerStatistics {

    /**
     * 会员名称
     */
    private String customerName;

    /**
     * 会员等级名称
     */
    private String customerLevelName;

    /**
     * 会员头像
     */
    private String customerImage;

    /**
     * 是否手机验证 0 否 1 验证 默认0
     */
    private String isPhoneValidate;

    /**
     * 是否邮箱验证 0 否 1 验证 默认0
     */
    private String isEmailValidate;

    /**
     * 是否设置了支付密码  0 否 1 验证 默认0
     */
    private String isPayPasswordValidate;

    /**
     * 消息总数
     */
    private int messageCount;

    /**
     * 待付款订单数量
     */
    private int toPayCount;

    /**
     * 待收货订单数量
     */
    private int toReceiptCount;

    /**
     * 待发货订单数量
     */
    private int toDeliverCount;

    /**
     * 待评价订单数量
     */
    private int toEvaluateCount;

    /**
     * 预存款余额
     */
    private BigDecimal predepositMoney;

    /**
     * 积分数量
     */
    private int point;

    /**
     * 优惠券数量
     */
    private int couponNum;

    /**
     * 关注商品数量
     */
    private int followSkuNum;

    /**
     * 关注店铺数量
     */
    private int followStoreNum;

    /**
     * 浏览纪录数量
     */
    private int historyNum;

    /**
     * 构造会员统计
     *
     * @param customer 会员实体
     * @return 返回会员统计
     */
    public static CustomerStatistics build(UmsMember customer) {
        CustomerStatistics customerStatistics = new CustomerStatistics();

        if (Objects.isNull(customer)) {
            return customerStatistics;
        }

        customerStatistics.customerName = customer.getUsername();
        customerStatistics.customerImage = customer.getImage();
        customerStatistics.isPhoneValidate = customer.getIsMobileVerification();
        customerStatistics.isEmailValidate = customer.getIsEmailVerification();
        customerStatistics.isPayPasswordValidate = StringUtils.isEmpty(customer.getPaypassword()) ? "0" : "1";
        customerStatistics.customerLevelName = customer.getCustomerLevelName();

        return customerStatistics;
    }
}
