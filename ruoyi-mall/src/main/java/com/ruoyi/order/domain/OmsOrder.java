package com.ruoyi.order.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 订单对象 oms_order
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Setter
@Getter
public class OmsOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String express100Url;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单code
     */
    @Excel(name = "订单code")
    private String orderCode;

    /**
     * 主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单
     */
    @Excel(name = "主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单")
    private String masterOrderCode;

    /**
     * 订单所属的会员id
     */
    @Excel(name = "订单所属的会员id")
    private Long customerId;

    /**
     * 订单的最终价格(订单的最终成交价格)
     */
    @Excel(name = "订单的最终价格(订单的最终成交价格)")
    private BigDecimal price = new BigDecimal(0);

    /**
     * 预售需要支付的金额 （预售订单使用）
     */
    @Excel(name = "预售需要支付的金额 ", readConverterExp = "预=售订单使用")
    private BigDecimal presalePrice= new BigDecimal(0);

    /**
     * 订单原始金额（每个单品详情页面的总价）
     */
    @Excel(name = "订单原始金额", readConverterExp = "每=个单品详情页面的总价")
    private BigDecimal originalPrice = new BigDecimal(0);

    /**
     * 运费
     */
    @Excel(name = "运费")
    private BigDecimal freightPrice = new BigDecimal(0);

    /**
     * 订单修改的金额（后端管理员修改）
     */
    @Excel(name = "订单修改的金额", readConverterExp = "后=端管理员修改")
    private BigDecimal modifyPrice;

    /**
     * 积分抵消的金额
     */
    @Excel(name = "积分抵消的金额")
    private BigDecimal pointPrice = new BigDecimal(0);

    /**
     * 优惠卷抵消的价格
     */
    @Excel(name = "优惠卷抵消的价格")
    private BigDecimal couponPrice = new BigDecimal(0);

    /**
     * 使用红包减去的价格
     */
    @Excel(name = "使用红包减去的价格")
    private BigDecimal redEnvelopePrice = new BigDecimal(0);

    /**
     * 总的优惠价（包含订单使用的优惠卷 积分 红包 ,满减满折的价格）
     */
    @Excel(name = "总的优惠价", readConverterExp = "包=含订单使用的优惠卷,积=分,红=包,,=满减满折的价格")
    private BigDecimal concessionalRate = new BigDecimal(0);

    /**
     * 1:待付款  （用户刚下单）
     * 2:代发货  （用户付完款 等待商城发货）
     * 3:代收货  （商城已经发货 等待用户确认收货）
     * 4:已完成  （用户已经确认收货 订单结束）
     * 5:取消订单 （用户未付款前取消订单）
     * 6:退款通过  （用户已经付款但是商城还未发货，用户发出退款申请，商城同意退款）
     * 7:退货通过   （用户已经确认收货后用户发出退货申请，商城同意所有退货申请 ，一个订单可能有多个单品）
     */
    @Excel(name = "1:待付款  ", readConverterExp = "用=户刚下单")
    private String status;

    /**
     * 预售订单状态   普通订单 该状态没作用
     * 0 第一阶段未支付
     * 1 第一阶段已支付第二阶段未支付
     * 2 第二阶段已支付   默认0
     */
    @Excel(name = "预售订单状态   普通订单 ")
    private String presaleStatus;

    /**
     * 评价状态  0 未评价 1 已评价  默认为0 未评价
     */
    @Excel(name = "评价状态  0 未评价 1 已评价  默认为0 未评价")
    private String evaluationStatus;

    /**
     * 使用红包的code
     */
    @Excel(name = "使用红包的code")
    private String redEnvelopeCode;

    /**
     * 使用的优惠卷的卷码
     */
    @Excel(name = "使用的优惠卷的卷码")
    private String couponNo;

    /**
     * 使用的积分数量
     */
    @Excel(name = "使用的积分数量")
    private int usePoint = 0;

    /**
     * 支付类型  0在线支付  1货到付款
     */
    @Excel(name = "支付类型  0在线支付  1货到付款 ")
    private String payType;

    /**
     * 订单店铺id  平台的订单id为0
     */
    @Excel(name = "订单店铺id  平台的订单id为0")
    private long storeId=0;

    /**
     * 订单取消原因
     * 1:现在不想买
     * 2:商品价格较贵
     * 3:价格波动
     * 4:商品缺货
     * 5:重复下单
     * 6:收货人信息有误
     * 7:发票信息有误/发票未开
     * 8:送货时间过长
     * 9:其他原因
     * 0:系统取消
     */
    @Excel(name = "订单取消原因")
    private String cancelReson;

    /**
     * 是否预存款支付  0 否 1 是  默认0
     */
    @Excel(name = "是否预存款支付  0 否 1 是  默认0")
    private String predepositPay;

    /**
     * 订单来源 1pc  2 h5   3 app
     */
    @Excel(name = "订单来源 1pc  2 h5   3 app")
    private String source;

    /**
     * 运单号
     */
    @Excel(name = "运单号")
    private String waybillCode;

    /**
     * 订单类型 0 普通订单 1 定金预售订单 2全款预售订单 3 拼团订单 4 众筹全款 5 众筹1元 6 众筹无回报 7 虚拟商品订单 8 社区团购订单 默认0 普通订单
     */
    @Excel(name = "订单类型 0 普通订单 1 定金预售订单 2全款预售订单 3 拼团订单 4 众筹全款 5 众筹1元 6 众筹无回报 7 虚拟商品订单 8 社区团购订单 默认0 普通订单")
    private String orderType;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryTime;

    /**
     * 确认收货时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "确认收货时间")
    private LocalDateTime receivingTime;

    /**
     * 订单取消时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订单取消时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cancelTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /**
     * 订单评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订单评价时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date evaluationTime;

    /**
     * 预售订单的时间
     * 对于定金预售 则是第二阶段付款时间 和发货时间 对于全款预售 则是发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预售订单的时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime presaleTime;

    /**
     * 是否团长  0 是 1 否  当订单为拼团订单的时候有用
     */
    @Excel(name = "是否团长  0 是 1 否  当订单为拼团订单的时候有用")
    private String groupHead;

    /**
     * 拼团订单的id  拼团订单的时候有用
     */
    @Excel(name = "拼团订单的id  拼团订单的时候有用")
    private String groupId;

    /**
     * 拼团订单的促销id 拼团订单的时候有用
     */
    @Excel(name = "拼团订单的促销id 拼团订单的时候有用")
    private Long groupMarketingId;

    /**
     * 拼团的单品id （拼团订单时候有效）
     */
    @Excel(name = "拼团的单品id ", readConverterExp = "拼=团订单时候有效")
    private String groupSkuId;

    /**
     * 拼团状态 -1无状态 0未成团 1已成团 默认-1
     */
    @Excel(name = "拼团状态 -1无状态 0未成团 1已成团 默认-1 ")
    private String groupStatus;

    /**
     * 成团的数量
     */
    @Excel(name = "成团的数量")
    private int groupNum;

    /**
     * 开团时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开团时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime openGroupTime;

    /**
     * 拼团订单定时任务处理状态 '0'未处理 '1'已处理 默认'0'
     */
    @Excel(name = "拼团订单定时任务处理状态 '0'未处理 '1'已处理 默认'0'")
    private String autoHandleStatus;

    /**
     * 订单推广人的会员id(订单会员的推广人 ，也就是订单会员的上级的id) 如果为-1 则说明该订单会员没有推荐人或者不是推广订单
     */
    @Excel(name = "订单推广人的会员id(订单会员的推广人 ，也就是订单会员的上级的id) 如果为-1 则说明该订单会员没有推荐人或者不是推广订单")
    private Long recommended = -1L;

    /**
     * 订单二级推广人的会员id(订单会员的二级推广人 ，也就是订单会员的上级的上级会员id) 如果为-1 则说明该订单会员没有推荐人或者不是推广订单
     */
    @Excel(name = "订单二级推广人的会员id(订单会员的二级推广人 ，也就是订单会员的上级的上级会员id) 如果为-1 则说明该订单会员没有推荐人或者不是推广订单")
    private Long sRecommended = -1L;

    /**
     * 众筹id  对应ls_markeing 表中的id
     */
    @Excel(name = "众筹id  对应ls_markeing 表中的id")
    private Long crowdfundingId;

    /**
     * 抽奖状态  0 待抽奖 1 抽中 默认0
     */
    @Excel(name = "抽奖状态  0 待抽奖 1 抽中 默认0")
    private String lotteryStatus;

    /**
     * 虚拟订单的核销码
     */
    @Excel(name = "虚拟订单的核销码")
    private String writeOffCode;

    /**
     * 社区团购的团长会员id  社区团购的时候使用 对应ums_member表中的id
     */
    @Excel(name = "社区团购的团长会员id  社区团购的时候使用 对应ums_member表中的id")
    private Long communityBuyCustomerId;

    /**
     * 社区团购的团购id  对应s_community_buy 表中的id
     */
    @Excel(name = "社区团购的团购id  对应s_community_buy 表中的id ")
    private Long communityBuyId;

    /**
     * 社区团购的利润，社区团购的时候使用
     */
    @Excel(name = "社区团购的利润，社区团购的时候使用")
    private BigDecimal profit= new BigDecimal(0);

    /**
     * 小区名称 社区团购使用
     */
    @Excel(name = "小区名称 社区团购使用")
    private String communityName;

    /**
     * 社区团购名称
     */
    @Excel(name = "社区团购名称")
    private String communityBuyName;

    /**
     * 物流公司名称
     */
    @Excel(name = "物流公司名称")
    private String logisticsCompany;

    /**
     * 物流公司code
     */
    @Excel(name = "物流公司code")
    private String logisticsCode;

    /**
     * 订单操作日志
     */
    @ApiModelProperty(value = "订单操作日志")
    private List<OmsOrderOperationLog> orderOperatonLogs;

    /**
     * 订单附属信息
     */
    @ApiModelProperty(value = "订单附属信息")
    private OmsOrderAttr orderAttr;

    /**
     * 订单单品信息
     */
    @ApiModelProperty(value = "订单单品信息")
    private List<OmsOrderSku> orderSkus = new ArrayList<>();

    /**
     * 购物车id
     */
    @ApiModelProperty(value = "购物车id")
    private Long[] cartIds;


    /**
     * 众筹id  对应ls_markeing 表中的id'
     */
    @ApiModelProperty(value = "众筹id  对应ls_markeing 表中的id'")
    private Long crowdFundingId;

    /**
     * 是否在退单审核中
     */
    @ApiModelProperty(value = "是否在退单审核中")
    private boolean isInBackProgress;

    /**
     * 订单的赠送单品
     */
    @ApiModelProperty(value = "订单的赠送单品")
    private List<OrderGift> orderGifts;
    /**
     * 团长名称
     */
    @ApiModelProperty(value = "团长名称")
    private String communityBuyHeadName;

    /**
     * 团长手机号码
     */
    @ApiModelProperty(value = "团长手机号码")
    private String communityBuyHeadMobile;

    /**
     * 佣金等级 1 一级佣金 2 二级佣金
     */
    @ApiModelProperty(value = "团长手机号码")
    private String commissionLevel = "1";

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String customerName;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private String customerMobile;
    /**
     * 判断是否可以申请退款
     */
    @ApiModelProperty(value = "判断是否可以申请退款")
    private boolean isCanRefund;
    /**
     * 判断是否可以申请退货
     */
    @ApiModelProperty(value = "判断是否可以申请退货")
    private boolean isCanReturn;

    /**
     * 判断众筹抽奖是否抽中
     *
     * @return 抽中返回true
     */
    @JsonIgnore
    public boolean isCrowdFundingWinning() {
        return "1".equals(this.lotteryStatus);
    }

    /**
     * 判断是否使用了积分
     *
     * @return 积分>0 返回true  否则返回false
     */
    @JsonIgnore
    public boolean isUsedPoint() {
        return this.usePoint > 0;
    }

    /**
     * 不需要付款的订单
     *
     * @return 货到付款的订单或者价格为0 的订单不需要付款
     */
    @JsonIgnore
    public boolean isNoNeedPay() {
        return "1".equals(this.payType) || checkZeroPrice();
    }

    /**
     * 设置订单的状态
     */
    public void setCreateOrderStatus() {
        // 如果货到付款
        if ("1".equals(this.payType)) {
            // 货到付款  则订单状态直接设置成待发货
            this.status = "2";
        } else {
            // 在线支付  则订单状态设置成待付款
            this.status = "1";
        }
    }

    /**
     * 校验金额是否为0
     *
     * @return 为0返回true, 否则返回false
     */
    public boolean checkZeroPrice() {
        return this.price.setScale(2, RoundingMode.HALF_EVEN).compareTo(new BigDecimal(0)) == 0;
    }

    /**
     * 判断是否使用了优惠券
     *
     * @return 看是否有优惠券码  有返回true  没有返回false
     */
    public boolean isCouponUsed() {
        return !StringUtils.isEmpty(this.couponNo);
    }

    /**
     * 判断是否使用了红包
     *
     * @return 看是否有红包券吗  有返回true  没有返回false
     */
    public boolean isRedEnvelopeUsed() {
        return !StringUtils.isEmpty(this.redEnvelopeCode);
    }


    /**
     * 判断是否使用了积分
     *
     * @return 积分的使用金额不为0 则说明使用了积分
     */
    private boolean isPointsUsed() {
        return Objects.nonNull(this.pointPrice) && !this.pointPrice.equals(new BigDecimal(0));
    }


    /**
     * 获得没有运费的订单金额
     *
     * @return 返回没有运费的订单金额
     */
    @JsonIgnore
    public BigDecimal getPriceWhitNoFreightPrice() {
        return this.price.subtract(this.freightPrice);
    }

    /**
     * 超过了可以退货的天数（7天）
     */
    public boolean overCanReturnDays() {
        return Objects.nonNull(receivingTime) && LocalDateTime.now().isAfter(receivingTime.plusDays(7));
    }

    /**
     * 设置使用红包的情况下每个单品应该减去的平均优惠金额
     */
    public void calcRedEnvelopeEverySkuPrice() {
        // 如果没有使用红包 则直接返回
        if (!isRedEnvelopeUsed()) {
            return;
        }

        // 单品的总价格
        final BigDecimal allPrice = getAllSkuPrice();

        // 如果单品的价格为0  则直接返回
        if (allPrice.intValue() == 0) {
            return;
        }


        // 使用了红包则计算每个单品应该平均优惠的价格
        this.orderSkus.stream().forEach(orderSku -> {
            // 单品平均红包优惠的价格
            BigDecimal price = (orderSku.getPrice().divide(allPrice, 10, RoundingMode.HALF_EVEN)).multiply(this.redEnvelopePrice).setScale(2, RoundingMode.HALF_EVEN);
            orderSku.addSkuPriceDetail(new SkuPriceDetail(6, price));
            orderSku.setPrice(orderSku.getPrice().subtract(price));
        });

    }

    /**
     * 设置使用优惠券的情况下每个单品应该减去的平均优惠金额
     */
    public void calcCouponEverySkuPrice() {
        // 如果没有使用优惠券 则直接返回
        if (!isCouponUsed()) {
            return;
        }

        // 单品的总价格
        final BigDecimal allPrice = getAllSkuPrice();

        // 如果单品的价格为0  则直接返回
        if (allPrice.intValue() == 0) {
            return;
        }

        // 使用了优惠券 则计算每个单品应该平均优惠的价格
        this.orderSkus.stream().forEach(orderSku -> {
            // 单品平均优惠券优惠的价格
            BigDecimal price = (orderSku.getPrice().divide(allPrice, 10, RoundingMode.HALF_EVEN)).multiply(this.couponPrice).setScale(2, RoundingMode.HALF_EVEN);
            orderSku.addSkuPriceDetail(new SkuPriceDetail(3, price));
            orderSku.setPrice(orderSku.getPrice().subtract(price));
        });
    }

    /**
     * 设置管理员修改订单价格的情况下每个单品应该减去的平均优惠信息
     *
     * @param modifyPrice 管理员修改的金额
     */
    public void calcModifyPriceEverySkuPrice(BigDecimal modifyPrice) {

        // 修改金额不合法 直接返回
        if (Objects.isNull(modifyPrice)) {
            return;
        }

        // 单品的总价格
        final BigDecimal allPrice = getAllSkuPrice();

        // 管理员修改订单价格 则计算每个单品应该平均优惠的价格
        this.orderSkus.stream().forEach(orderSku -> {
            // 将单品的优惠详情json转化成list
            orderSku.convertJsonPriceDetailToList();
            // 单品平均修改金额的价格
            BigDecimal price = (orderSku.getPrice().divide(allPrice, 10, RoundingMode.HALF_EVEN)).multiply(modifyPrice).setScale(2, RoundingMode.HALF_EVEN);
            orderSku.addSkuPriceDetail(new SkuPriceDetail(5, price));
            orderSku.setPrice(orderSku.getPrice().subtract(price));
        });
    }

    /**
     * 设置使用积分的情况下每个单品应该减去的平均优惠金额
     */
    public void calcPointsEverySkuPrice() {
        // 如果没有使用积分 则直接返回
        if (!isPointsUsed()) {
            return;
        }

        // 单品的总价格
        final BigDecimal allPrice = getAllSkuPrice();

        // 如果单品的价格为0  则直接返回
        if (allPrice.intValue() == 0) {
            return;
        }

        this.orderSkus.stream().forEach(orderSku -> {
            // 单品平均积分优惠的价格
            BigDecimal price = (orderSku.getPrice().divide(allPrice, 10, RoundingMode.HALF_EVEN).multiply(this.pointPrice)).setScale(2, RoundingMode.HALF_EVEN);
            orderSku.addSkuPriceDetail(new SkuPriceDetail(4, price));
            orderSku.setPrice(orderSku.getPrice().subtract(price));
        });
    }

    /**
     * 获得每个单品的累计金额
     *
     * @return 返回每个单品的累计金额
     */
    @JsonIgnore
    private BigDecimal getAllSkuPrice() {
        return this.orderSkus.stream().map(OmsOrderSku::getPrice).reduce(new BigDecimal(0), BigDecimal::add);
    }

    /**
     * 设置订单id  主要是订单附属信息和订单单品下的订单id
     */
    public void setEveryOrderId() {

        // 设置订单附属信息的id
        if (Objects.nonNull(this.orderAttr)) {
            this.orderAttr.setOrderId(this.id);
        }

        // 设置订单单品的订单id
        if (!CollectionUtils.isEmpty(this.orderSkus)) {
            this.orderSkus.stream().forEach(orderSku -> orderSku.setOrderId(this.id));
        }
    }

    /**
     * 返回订单的详细收货地址
     *
     * @return 返回订单的详情收货地址
     */
    @JsonIgnore
    public String getOrderDetailAddress() {
        if (Objects.isNull(this.orderAttr)) {
            return "";
        }
        return orderAttr.getReceiptAddress() + orderAttr.getReceiptDetailAddress();
    }

    /**
     * 返回订单收货人名称
     *
     * @return 返回订单收货人名称
     */
    @JsonIgnore
    public String getShippingName() {
        return Objects.nonNull(this.orderAttr) ? this.orderAttr.getReceiptName() : "";
    }

    /**
     * 返回订单收货人的手机号码
     *
     * @return 返回订单收货人的手机号码
     */
    @JsonIgnore
    public String getShippingMobile() {
        return Objects.nonNull(this.orderAttr) ? this.orderAttr.getReceiptMobile() : "";
    }

    /**
     * 返回订单下面的单品的名称
     *
     * @return 返回订单下面的单品的名称
     */
    @JsonIgnore
    public String getOrderSkuNames() {
        if (CollectionUtils.isEmpty(this.orderSkus)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        IntStream.range(0, this.orderSkus.size()).forEach(index -> {
            sb.append(this.orderSkus.get(index).getSkuName());
            if (index != this.orderSkus.size() - 1) {
                sb.append(",");
            }
        });
        return sb.toString();
    }

    /**
     * 将赠送单品转化成list集合
     */
    public void convertGiftToList() {
        if (Objects.nonNull(this.orderAttr) && !StringUtils.isEmpty(this.orderAttr.getGiftInfos())) {
            this.orderGifts = JSON.parseArray(this.orderAttr.getGiftInfos(), OrderGift.class);
        }
    }

    /**
     * 判断订单是否为待付款状态
     *
     * @return 待付款状态返回true
     */
    @JsonIgnore
    public boolean isWaitToPay() {
        return "1".equals(this.status);
    }

    /**
     * 判断订单是否为众筹全款支持订单
     *
     * @return 众筹全款支持订单返回true
     */
    @JsonIgnore
    public boolean isFullSupportType() {
        return "4".equals(this.orderType);
    }

    /**
     * 判断订单是否为众筹一元支持订单
     *
     * @return 众筹一元支持订单返回true
     */
    @JsonIgnore
    public boolean isOneCoinSupportType() {
        return "5".equals(this.orderType);
    }


    /**
     * 判断是否是待发货状态
     *
     * @return 发货返回true
     */
    @JsonIgnore
    public boolean isToDeliver() {
        return "2".equals(this.status);
    }

    /**
     * 判断订单是否完成
     *
     * @return 完成返回true
     */
    @JsonIgnore
    public boolean isFinished() {
        return "4".equals(this.status);
    }

    /**
     * 是否确认收货过
     *
     * @return 确认收货过返回true
     */
    public boolean isConfirmReceipted() {
        return "4".equals(this.status) || "6".equals(this.status) || "7".equals(this.status);
    }

    /**
     * 判断当前订单是否可以评分
     * 订单状态是已完成并且订单没有评论过 就可以评分
     */
    @JsonIgnore
    public boolean isCanEvaluation() {
        return "4".equals(this.status) && "0".equals(this.evaluationStatus);
    }

    /**
     * 判断是否已经评价
     *
     * @return 已经评价返回true  否则返回false
     */
    @JsonIgnore
    public boolean hasEvaluationed() {
        return "1".equals(this.evaluationStatus);
    }

    /**
     * 校验修改的金额 修改后的金额必须要>=0.1元   必须要支付1毛钱 (不能把运费修改掉)
     *
     * @return 修改后的金额>=0.1 返回true  否则返回false
     */
    public boolean validateModifyPrice(BigDecimal modifyPrice) {
        if (Objects.isNull(modifyPrice)) {
            return false;
        }

        return this.price.subtract(this.freightPrice).setScale(2, BigDecimal.ROUND_HALF_EVEN).subtract(modifyPrice).setScale(2, BigDecimal.ROUND_HALF_EVEN).compareTo(new BigDecimal(0.10).setScale(2, BigDecimal.ROUND_HALF_EVEN)) >= 0;
    }

    /**
     * 是否可以修改价格
     *
     * @return 定金预售和全款预售和拼团订单不能修改价格
     */
    public boolean isCanModifyPrice() {
        return !isDepositPresaleOrder() && !"2".equals(this.orderType) && !isGroupOrder();
    }

    /**
     * 判断是否是定金预售订单
     *
     * @return 是返回true  不是返回false
     */
    public boolean isDepositPresaleOrder() {
        return "1".equals(this.orderType);
    }

    /**
     * 判断定金预售 第一阶段是否已经支付
     *
     * @return 支付返回true  未支付返回false
     */
    public boolean isPresaleOnePayed() {
        return !"0".equals(this.presaleStatus);
    }


    /**
     * 判断是否是拼团订单
     *
     * @return 是返回true  不是返回false
     */
    @JsonIgnore
    public boolean isGroupOrder() {
        return "3".equals(this.orderType);
    }

    /**
     * 判断是否是团长订单
     *
     * @return 是返回true  不是返回false
     */
    @JsonIgnore
    public boolean isGroupHeadOrder() {
        return isGroupOrder() && "0".equals(this.groupHead);
    }

    /**
     * 判断是否需要修改成团时间
     *
     * @return 0 需要 1 不需要 团长订单需要修改成团时间
     */
    @JsonIgnore
    public int isNeedUpdateTime() {
        return this.isGroupHeadOrder() ? 0 : 1;
    }


    /**
     * 判断订单是否可以支付
     *
     * @return 只有是定金预售的订单  并且是第二次支付的时候 如果支付时间没到 则返回false  否则返回true
     */
    public boolean isPresaleOrderCanPay() {
        // 如果不是定金预售订单则直接返回 true  可以支付
        if (!isDepositPresaleOrder()) {
            return true;
        }

        // 定金预售订单 判断第一阶段是否一件支付 如果没有支付 则直接返回true 可以支付
        if (!isPresaleOnePayed()) {
            return true;
        }

        //  定金预售订单 第二阶段付款的时候判断是否满足了付款时间
        return LocalDateTime.now().isAfter(this.getPresaleTime());
    }

    /**
     * 判断是否可以发货
     *
     * @return 如果不是拼团订单 则直接返回true 可以发货 如果是拼团订单 则只有拼团成功了才能发货
     */
    @JsonIgnore
    public boolean isCanDeliverOrder() {

        // 不是拼团订单 直接返回true
        if (!isGroupOrder()) {
            return true;
        }

        // 拼团订单 如果拼团成功则返回true  否则返回fasle
        return "1".equals(this.groupStatus);
    }

    /**
     * 判断订单下的单品是否有佣金比例的单品 只要有一个  就返回true
     *
     * @return 判断订单下的单品是否有佣金比例的单品 只要有一个  就返回true
     */
    @JsonIgnore
    public boolean orderSkuHasCommissionRate() {
        if (CollectionUtils.isEmpty(this.orderSkus)) {
            return false;
        }
        return !CollectionUtils.isEmpty(this.orderSkus.stream().filter(OmsOrderSku::hasSetCommissionRate).collect(Collectors.toList()));
    }

    /**
     * 判断订单下的单品是否有二级佣金比例的单品 只要有一个  就返回true
     *
     * @return 判断订单下的单品是否有二级佣金比例的单品 只要有一个  就返回true
     */
    @JsonIgnore
    public boolean orderSkuHasSCommissionRate() {
        if (CollectionUtils.isEmpty(this.orderSkus)) {
            return false;
        }
        return !CollectionUtils.isEmpty(this.orderSkus.stream().filter(OmsOrderSku::hasSetSCommissionRate).collect(Collectors.toList()));
    }

    /**
     * 获得订单的佣金
     *
     * @return 返回订单下每个单品的佣金
     */
    @JsonIgnore
    public BigDecimal getOrderCommission() {
        if (CollectionUtils.isEmpty(this.orderSkus) || !orderSkuHasCommissionRate()) {
            return new BigDecimal(0);
        }
        return this.orderSkus.stream().map(OmsOrderSku::getCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 获得订单的二级佣金
     *
     * @return 返回订单下每个单品的二级佣金
     */
    @JsonIgnore
    public BigDecimal getOrderSCommission() {
        if (CollectionUtils.isEmpty(this.orderSkus) || !orderSkuHasSCommissionRate()) {
            return new BigDecimal(0);
        }
        return this.orderSkus.stream().map(OmsOrderSku::getSCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 判断是否是平台的 是返回true  否则返回fase
     *
     * @return 是平台返回true
     */
    @JsonIgnore
    public boolean isPlatform() {
        return this.storeId == 0;
    }


    /**
     * 判断是否是无回报支持订单
     *
     * @return 是返回true  不是返回false
     */
    @JsonIgnore
    public boolean isNoReturnOrder() {
        return "6".equals(this.orderType);
    }


    /**
     * 判断结算的时候是否要佣金
     *
     * @return 如果不是众筹订单直接返回true  如果是众筹订单 则只有全款众筹需要佣金 抽奖和无回报支持不需要
     */
    @JsonIgnore
    public boolean isNeedCommission() {
        // 如果不是众筹订单直接返回true
        if (!this.isCrowdfundingOrder()) {
            return true;
        }

        //如果是众筹订单 则只有全款众筹需要佣金 抽奖和无回报支持不需要
        return "4".equals(this.orderType);
    }

    /**
     * 判断是否是虚拟订单 是返回true  不是返回false
     *
     * @return 虚拟订单返回true  不是返回false
     */
    @JsonIgnore
    public boolean isVirtualOrder() {
        return "7".equals(this.orderType);
    }

    /**
     * 判断是否是众筹订单
     *
     * @return 订单类型是4 5 6 的就是众筹订单 返回true  否则返回false
     */
    @JsonIgnore
    public boolean isCrowdfundingOrder() {
        return "4".equals(this.orderType) || "5".equals(this.orderType) || "6".equals(this.orderType);
    }

    /**
     * 设置虚拟订单的核销码
     */
    public void setVirtualOrderWriteOffCode() {
        // 如果是虚拟订单 则设置核销码
        if (this.isVirtualOrder()) {
            this.writeOffCode = (int) ((Math.random() * 9 + 1) * 100000) + "";
        }
    }

    /**
     * 是否是社区团购
     *
     * @return 是返回true 不是返回false
     */
    @JsonIgnore
    public boolean isCommunityBuyOrder() {
        return "8".equals(this.orderType);
    }

    /**
     * 判断是否有一级推荐人
     *
     * @return 有返回true  没有返回false
     */
    @JsonIgnore
    public boolean hasRecommendedCommission() {
        return this.recommended != -1;
    }

    /**
     * 判断是否有二级推荐人
     *
     * @return 有返回true  没有返回false
     */
    @JsonIgnore
    public boolean hasSRecommondedCommission() {
        return this.sRecommended != -1;
    }
}
