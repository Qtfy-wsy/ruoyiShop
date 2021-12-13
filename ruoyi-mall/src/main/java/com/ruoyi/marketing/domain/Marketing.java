package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.util.MarketingConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by 伊甸园商城 on 17/6/7.
 * 促销实体
 */
@Data
@ApiModel(description = "促销实体")
public class Marketing {

    /**
     * 促销id
     */
    @ApiModelProperty(value = "促销id")
    private long id;

    /**
     * 促销名称
     */
    @ApiModelProperty(value = "促销名称")
    private String name;

    /**
     * 营销类型 1 直降 2满赠 3抢购 4满减  5 满折 6 包邮 7 定金预售 8 全款预售 9 试用 10拼团 11众筹
     */
    @ApiModelProperty(value = "营销类型 1 直降 2满赠 3抢购 4满减  5 满折 6 包邮 7 定金预售 8 全款预售 9 试用 10拼团 11众筹")
    private String type;

    /**
     * 促销分类id
     */
    @ApiModelProperty(value = "促销分类id")
    private long cateId;

    /**
     * 店铺id  平台的为0
     */
    @ApiModelProperty(value = "店铺id  平台的为0")
    private long storeId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 营销描述
     */
    @ApiModelProperty(value = "营销描述")
    private String desc;

    /**
     * 删除标记  0 未删除 1 删除
     */
    @ApiModelProperty(value = "删除标记  0 未删除 1 删除")
    private int delFlag;

    /**
     * 单品信息
     */
    @ApiModelProperty(value = "单品信息")
    private PmsSku sku;


    /**
     * 开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    /**
     * 抢购详情
     */
    @ApiModelProperty(value = "抢购详情")
    private PanicBuy panicBuy;

    /**
     * 直降促销
     */
    @ApiModelProperty(value = "直降促销")
    private Fall fall;

    /**
     * 试用促销
     */
    @ApiModelProperty(value = "试用促销")
    private TryMarketing tryMarkting;

    /**
     * 拼团促销
     */
    @ApiModelProperty(value = "拼团促销")
    private GroupMarketing groupMarketing;

    /**
     * 众筹促销
     */
    @ApiModelProperty(value = "众筹促销")
    private CrowdFunding crowdFunding;

    /**
     * 满减促销
     */
    @ApiModelProperty(value = "满减促销")
    private List<FullDown> fullDowns;

    /**
     * 满赠促销
     */
    @ApiModelProperty(value = "满赠促销")
    private List<FullGift> fullGifts;

    /**
     * 满折促销
     */
    @ApiModelProperty(value = "满折促销")
    private List<FullDiscount> fullDiscounts;

    /**
     * 促销关联的单品信息
     */
    @ApiModelProperty(value = "促销关联的单品信息")
    private List<MarketingSku> marketingSkus;

    /**
     * 包邮促销
     */
    @ApiModelProperty(value = "包邮促销")
    private FreeShip freeShip;

    /**
     * 预售
     */
    @ApiModelProperty(value = "预售")
    private PreSale preSale;

    /**
     * 抢购详情列表
     */
    @ApiModelProperty(value = "抢购详情列表")
    private List<PanicBuy> panicBuyList;

    /**
     * 拼团促销列表
     */
    @ApiModelProperty(value = "拼团促销列表")
    private List<GroupMarketing> groupMarketingList;

    /**
     * 试用促销列表
     */
    @ApiModelProperty(value = "试用促销列表")
    private List<TryMarketing> tryMarketingList;

    /**
     * 预售促销列表
     */
    @ApiModelProperty(value = "预售促销列表")
    private List<PreSale> preSaleList;

    /**
     * 判断是否是抢购
     *
     * @return 是返回true  不是返回false
     */
    @JsonIgnore
    public boolean isPanicBuyMarketing() {
        return MarketingConstant.PANIC_BUY_MARKETING.equals(this.type);
    }

    /**
     * 判断是否是直降
     *
     * @return 是直降返回true  不是返回false
     */
    @JsonIgnore
    public boolean isFallMarketing() {
        return MarketingConstant.FALL_MARKETING.equals(this.type);
    }

    /**
     * 判断是否是满减
     *
     * @return 是满减返回true  不是返回false
     */
    @JsonIgnore
    public boolean isFullDownMarketing() {
        return MarketingConstant.FULL_DOWN_MARKETING.equals(this.type);
    }

    /**
     * 判断是否是满折
     *
     * @return 是满折返回true  不是返回false
     */
    @JsonIgnore
    public boolean isFullDiscountMarketing() {
        return MarketingConstant.FULL_DISCOUNT_MARKETING.equals(this.type);
    }

    /**
     * 判断是否是满赠
     *
     * @return 是满赠返回true  不是返回false
     */
    @JsonIgnore
    public boolean isFullGiftMarketing() {
        return MarketingConstant.FULL_GIFT_MARKETING.equals(this.type);
    }

    /**
     * 判断是否是定金预售
     *
     * @return 是定金预售返回true  不是返回false
     */
    @JsonIgnore
    public boolean isDepositPreSaleMarketing() {
        return MarketingConstant.DEPOSIT_PRE_SALE_MARKETING.equalsIgnoreCase(this.type);
    }

    /**
     * 判断是否是全款预售
     *
     * @return 是全款预售返回true  不是返回false
     */
    @JsonIgnore
    public boolean isFullPreSaleMarkting() {
        return MarketingConstant.FULL_PRE_SALE_MARKETING.equalsIgnoreCase(this.type);
    }

    /**
     * 判断是否是试用促销
     *
     * @return 是试用促销返回true 不是返回false
     */
    @JsonIgnore
    public boolean isTryMarketing() {
        return MarketingConstant.TRY_MARKETING.equals(this.type);
    }

    /**
     * 判断是否是拼团促销
     *
     * @return 是拼团促销返回true 不是返回false
     */
    @JsonIgnore
    public boolean isGroupMarketingType() {
        return MarketingConstant.GROUP_MARKETING.equals(this.type);
    }

    /**
     * 判断是否是众筹促销
     *
     * @return 是众筹促销返回true 不是返回false
     */
    @JsonIgnore
    public boolean isCrowdFundingMarketingType() {
        return MarketingConstant.CROWD_FUNDING_MARKETING.equals(this.type);
    }


    /**
     * 返回促销描述 不能删除 前端使用
     *
     * @return 返回促销描述
     */
    @ApiModelProperty(value = "促销描述")
    public String getMarketingDesc() {

        switch (this.type) {
            case MarketingConstant.FALL_MARKETING:
                return Objects.nonNull(this.fall) ? "直降" + this.fall.getPrice() : "";
            case MarketingConstant.PANIC_BUY_MARKETING:
                return Objects.nonNull(this.panicBuy) ? changeDiscountDesc(this.panicBuy.getDiscount()) + "折" : "";
            case MarketingConstant.FULL_DISCOUNT_MARKETING:
                StringBuilder sb = new StringBuilder();
                if (!CollectionUtils.isEmpty(this.fullDiscounts)) {
                    this.fullDiscounts.stream().forEach(fullDiscount -> sb.append("满").append(fullDiscount.getFullPrice()).append("打").append(changeDiscountDesc(fullDiscount.getDiscount())).append("折").append(" "));
                }
                return sb.toString();
            case MarketingConstant.FULL_DOWN_MARKETING:
                StringBuilder sbString = new StringBuilder();
                if (!CollectionUtils.isEmpty(this.fullDowns)) {
                    this.fullDowns.stream().forEach(fullDown -> sbString.append("满").append(fullDown.getFullPrice()).append("减").append(fullDown.getPrice()).append(" "));
                }
                return sbString.toString();
            case MarketingConstant.FULL_GIFT_MARKETING:
                StringBuilder sbString2 = new StringBuilder();
                if (!CollectionUtils.isEmpty(this.fullGifts)) {
                    this.fullGifts.stream().forEach(fullGift -> sbString2.append("满").append(fullGift.getFullPrice()).append("赠").append(" "));
                }
                return sbString2.toString();
            case MarketingConstant.FREE_SHIP_MARKETING:
                return Objects.nonNull(freeShip) ? "满" + freeShip.getFullPrice().intValue() + "元包邮" : "";
            default:
                return "";
        }
    }

    /**
     * 改变折扣描述
     *
     * @param discount 折扣
     * @return 整数折扣
     */
    @ApiModelProperty(value = "主键id")
    private String changeDiscountDesc(BigDecimal discount) {
        int newDiscount = discount.multiply(new BigDecimal(100)).intValue();
        if (newDiscount < 10) {
            return "0." + newDiscount;
        } else {
            if (newDiscount % 10 == 0) {
                return (newDiscount / 10) + "";
            } else {
                return newDiscount + "";
            }
        }
    }

    /**
     * 设置添加促销的默认值
     *
     * @param storeId 店铺id
     * @param type    促销类型
     * @return 返回促销信息
     */
    public Marketing setAddMarketingDefaultValues(long storeId, String type) {
        this.storeId = storeId;
        this.delFlag = 0;
        this.type = type;
        return this;
    }

    /**
     * 设置开始时间为当前时间
     */
    public Marketing setStartTimeNow() {
        this.startTime = LocalDateTime.now();
        return this;
    }

    /**
     * 设置更新促销的默认值
     *
     * @param storeId 店铺id
     * @param type    促销类型
     * @return 返回促销信息
     */
    public Marketing setUpdateMarketingDefaultValues(long storeId, String type) {
        this.storeId = storeId;
        this.type = type;
        return this;
    }

    /**
     * 设置和促销相关的促销id
     */
    public void setLinkedMarketingId() {

        // 设置抢购的促销id
        if (Objects.nonNull(this.panicBuy)) {
            panicBuy.setMarketingId(this.id);
        }

        // 设置抢购列表的促销id
        if (!CollectionUtils.isEmpty(this.panicBuyList)) {
            panicBuyList.forEach(panicBuy1 -> panicBuy1.setMarketingId(this.id));
        }

        // 设置直降的促销id
        if (Objects.nonNull(this.fall)) {
            fall.setMarketingId(this.id);
        }

        // 设置满减促销id
        if (!CollectionUtils.isEmpty(this.fullDowns)) {
            fullDowns.stream().forEach(fullDown -> fullDown.setMarketingId(this.id));
        }

        // 设置满赠促销id
        if (!CollectionUtils.isEmpty(this.fullGifts)) {
            fullGifts.stream().forEach(fullGift -> fullGift.setMarketingId(this.id));
        }

        // 设置满折促销id
        if (!CollectionUtils.isEmpty(this.fullDiscounts)) {
            fullDiscounts.stream().forEach(fullDiscount -> fullDiscount.setMarketingId(this.id));
        }

        // 设置包邮促销id
        if (Objects.nonNull(this.freeShip)) {
            freeShip.setMarketingId(this.id);
        }

        // 设置预售促销id
        if (Objects.nonNull(this.preSale)) {
            preSale.setMarketingId(this.id);
        }

        // 设置预售列表的促销id
        if (!CollectionUtils.isEmpty(this.preSaleList)) {
            preSaleList.forEach(preSale1 -> preSale1.setMarketingId(this.id));
        }

        // 设置促销单品的促销id
        if (!CollectionUtils.isEmpty(marketingSkus)) {
            marketingSkus.stream().forEach(marketingSku -> marketingSku.setMarketingId(this.id));
        }

        // 设置试用促销id
        if (Objects.nonNull(this.tryMarkting)) {
            tryMarkting.setMarketingId(this.id);
        }

        // 设置试用列表的促销id
        if (!CollectionUtils.isEmpty(this.tryMarketingList)) {
            tryMarketingList.forEach(tryMarketing1 -> tryMarketing1.setMarketingId(this.id));
        }

        //设置拼团促销id
        if (Objects.nonNull(this.groupMarketing)) {
            groupMarketing.setMarketingId(this.id);
        }

        // 设置拼团列表的促销id
        if (!CollectionUtils.isEmpty(this.groupMarketingList)) {
            groupMarketingList.forEach(groupMarketing1 -> groupMarketing1.setMarketingId(this.id));
        }

        //设置众筹促销id
        if (Objects.nonNull(this.crowdFunding)) {
            crowdFunding.setMarketingId(this.id);
        }
    }

    /**
     * 计算促销价格
     *
     * @param price 原价
     * @return 返回促销后的计算价格
     */
    @ApiModelProperty(value = "促销价格")
    public BigDecimal calcMarketingPrice(BigDecimal price) {
        if (Objects.isNull(price)) {
            return price;
        }
        switch (this.type) {
            case MarketingConstant.FALL_MARKETING:
                // 设置直降的价格
                return price.subtract(this.fall.getPrice());
            // 设置抢购的价格
            case MarketingConstant.PANIC_BUY_MARKETING:
                return price.multiply(this.panicBuy.getDiscount());
            default:
                return price;
        }
    }

    /**
     * 校验抢购时间
     *
     * @return 当前时间在抢购的开始时间和结束时间内 返回true 否则返回false
     */
    @JsonIgnore
    public boolean maketingTimeValidate() {
        return LocalDateTime.now().isAfter(this.startTime) && LocalDateTime.now().isBefore(this.endTime);
    }

    /**
     * 判断包邮是否启用
     *
     * @return 启用返回true  没启用返回false
     */
    @JsonIgnore
    public boolean isFreeShipUse() {
        if (Objects.isNull(this.freeShip)) {
            return false;
        }

        return "1".equals(this.freeShip.getIsUse());
    }

    /**
     * 是否存在互斥促销
     *
     * @return 互斥返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isExclusionMarketing() {
        return !(MarketingConstant.FULL_DISCOUNT_MARKETING.equals(this.type) || MarketingConstant.FULL_GIFT_MARKETING.equals(this.type) || MarketingConstant.FULL_DOWN_MARKETING.equals(this.type));
    }

    /**
     * 获得查询互斥促销的参数
     */
    @JsonIgnore
    public Map<String, Object> getQueryExclusionMap() {
        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isEmpty(this.id)) {
            params.put("id", this.id);
        }
        params.put("queryType", 1);
        params.put("startTime", this.startTime);
        params.put("types", MarketingConstant.EXCLUSION_MARKETING_TYPES);
        params.put("endTime", this.endTime);
        params.put("skuIds", this.getMarketingSkuIds());
        if (isCrowdFundingMarketingType()) {
            params.put("spuId", crowdFunding.getSpuId());
        }
        return params;
    }

    /**
     * 设置促销type
     *
     * @return 促销实体
     */
    @JsonIgnore
    public Marketing setMarketingType() {
        // 设置促销的详细信息
        if (!ObjectUtils.isEmpty(this.getFall())) {
            this.setType(MarketingConstant.FALL_MARKETING);
        }
        if (!CollectionUtils.isEmpty(this.getPanicBuyList())) {
            this.setType(MarketingConstant.PANIC_BUY_MARKETING);
        }
        if (!ObjectUtils.isEmpty(this.getFullDowns())) {
            this.setType(MarketingConstant.FULL_DOWN_MARKETING);
        }
        if (!ObjectUtils.isEmpty(this.getFullDiscounts())) {
            this.setType(MarketingConstant.FULL_DISCOUNT_MARKETING);
        }
        if (!ObjectUtils.isEmpty(this.getPreSale())) {
            this.setStartTime(LocalDateTime.now());
            this.setType(MarketingConstant.DEPOSIT_PRE_SALE_MARKETING);
        }
        if (!CollectionUtils.isEmpty(this.getPreSaleList())) {
            this.setStartTime(LocalDateTime.now());
            this.setType(MarketingConstant.DEPOSIT_PRE_SALE_MARKETING);
        }
        if (!ObjectUtils.isEmpty(this.getTryMarkting())) {
            this.setType(MarketingConstant.TRY_MARKETING);
        }
        if (!CollectionUtils.isEmpty(this.getTryMarketingList())) {
            this.setType(MarketingConstant.TRY_MARKETING);
        }
        if (!ObjectUtils.isEmpty(this.getGroupMarketing())) {
            this.setType(MarketingConstant.GROUP_MARKETING);
        }
        if (!CollectionUtils.isEmpty(this.getGroupMarketingList())) {
            this.setType(MarketingConstant.GROUP_MARKETING);
        }
        if (!ObjectUtils.isEmpty(this.getCrowdFunding())) {
            this.setType(MarketingConstant.CROWD_FUNDING_MARKETING);
        }
        if (StringUtils.isEmpty(this.type)) {
            this.setStartTime(LocalDateTime.now());
            this.setType(MarketingConstant.FULL_PRE_SALE_MARKETING);
        }
        return this;
    }

    /**
     * 获取促销商品id集合
     *
     * @return 商品id集合
     */
    @JsonIgnore
    public List<String> getMarketingSkuIds() {
        if (CollectionUtils.isEmpty(this.marketingSkus)) {
            return Collections.emptyList();
        } else {
            List<String> skuIds = new ArrayList<>();
            this.marketingSkus.forEach(marketingSku -> skuIds.add(marketingSku.getSkuId()));
            return skuIds;
        }
    }


    /**
     * 设置众筹促销中的支持数量
     *
     * @param supportNum 支持数量
     * @return 促销实体
     */
    public Marketing buildSupportNum(int supportNum) {
        if (Objects.nonNull(this.crowdFunding)) {
            this.crowdFunding.setSupportNum(supportNum);
        }
        return this;
    }


    /**
     * 判断众筹的1元支持是否开启
     *
     * @return 开启返回true  没开启返回false
     */
    @JsonIgnore
    public boolean isOneMoneyOpen() {
        if (Objects.isNull(this.crowdFunding)) {
            return false;
        }

        return "0".equals(this.crowdFunding.getLottery());
    }

    /**
     * 判断众筹的无回报支持是否开启
     *
     * @return 开启返回true 没开启返回false
     */
    @JsonIgnore
    public boolean isNoReturnOpen() {
        if (Objects.isNull(this.crowdFunding)) {
            return false;
        }

        return "0".equals(this.crowdFunding.getNoReturnSupport());
    }

    /**
     * 根据skuId 获取众筹价
     *
     * @param skuId 单品id
     * @return 众筹价
     */
    @ApiModelProperty(value = "众筹价格")
    public BigDecimal getCrowdFundingSkuPrice(String skuId) {
        return this.marketingSkus.stream().filter(marketingSku -> skuId.equals(marketingSku.getSkuId())).findFirst().orElse(new MarketingSku()).getPrice();
    }

    /**
     * 获取促销活动状态
     *
     * @return 1 未开始 2 进行中 3 已结束
     */
    @ApiModelProperty(value = "促销活动状态")
    public String getStatus() {
        if (this.startTime.compareTo(LocalDateTime.now()) > 0) {
            return "1";
        } else if (this.endTime.compareTo(LocalDateTime.now()) <= 0) {
            return "3";
        } else {
            return "2";
        }
    }

    /**
     * 判断开始时间是否为整点
     *
     * @return 是 true 否 false
     */
    @JsonIgnore
    public boolean startTimeIsHourly() {
        return this.startTime.getMinute() == 0 && this.startTime.getSecond() == 0;
    }

}
