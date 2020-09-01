package com.ruoyi.marketing.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import com.ruoyi.common.utils.CustomPriceDeserializer;
import com.ruoyi.goods.domain.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * Created by 魔金商城 on 18/1/16.
 * 试用促销
 */
@Data
@ApiModel(description = "试用促销实体")
public class TryMarketing {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 促销id
     */
    @ApiModelProperty(value = "促销id")
    private long marketingId;

    /**
     * 0元试用类型 1 付邮试用 2 返券试用
     */
    @ApiModelProperty(value = "0元试用类型 1 付邮试用 2 返券试用")
    private String type;

    /**
     * 提供商品试用的份数
     */
    @ApiModelProperty(value = "提供商品试用的份数")
    private int tryNum;

    /**
     * 可以申请的人数 为0 表示不限制人数
     */
    @ApiModelProperty(value = "可以申请的人数 为0 表示不限制人数")
    private int applyNum;

    /**
     * 优惠券的id（类型为返券试用的时候使用）
     */
    @ApiModelProperty(value = "优惠券的id（类型为返券试用的时候使用）")
    private long couponId;

    /**
     * 已经申请的试用人数
     */
    @ApiModelProperty(value = "已经申请的试用人数")
    private int alerdyApplyNum;

    /**
     * 是否有过审核，0 没有过 1 审核过 默认0
     */
    @ApiModelProperty(value = "是否有过审核，0 没有过 1 审核过 默认0")
    private String hasAudit;

    /**
     * 单品信息
     */
    @JsonIgnore
    @ApiModelProperty(value = "单品信息")
    private String skuInfo;

    /**
     * 单品详情实体
     */
    @ApiModelProperty(value = "单品详情实体")
    private SkuDetail sku;

    /**
     * 试用的单品id
     */
    @ApiModelProperty(value = "试用的单品id")
    private String skuId;

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
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;
    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 获取促销活动状态
     *
     * @return 1 未开始 2 进行中 3 已结束
     */
    @ApiModelProperty(value = "促销活动状态")
    public String getStatus() {
        if (Objects.isNull(this.startTime) || Objects.isNull(this.endTime)) {
            return "-1";
        } else {
            if (this.startTime.compareTo(LocalDateTime.now()) > 0) {
                return "1";
            } else if (this.endTime.compareTo(LocalDateTime.now()) <= 0) {
                return "3";
            } else {
                return "2";
            }
        }
    }

    /**
     * 判断是否不含有主键id
     */
    @JsonIgnore
    public boolean notHasId() {
        return this.id == 0;
    }

    /**
     * 判断是否含有主键id
     */
    @JsonIgnore
    public boolean hasId() {
        return this.id != 0;
    }

    /**
     * 将试用商品的json信息转化成对象信息
     */
    public TryMarketing convertJsonToObject(String tryRule) {
        if (StringUtils.isEmpty(this.skuInfo)) {
            return this;
        }
        this.sku = JSON.parseObject(this.skuInfo, SkuDetail.class);
        this.sku.tryRule = tryRule;
        return this;
    }

    /**
     * 获得普通单品详情，返回Sku对象
     */
    @JsonIgnore
    @ApiModelProperty(value = "普通单品详情")
    public PmsSku getNormalSku() {
        PmsSku sku = new PmsSku();
        BeanUtils.copyProperties(this.sku, sku);
        return sku;
    }

    /**
     * 构建商品信息
     *
     * @param sku 商品实体
     * @return 试用促销实体
     */
    public TryMarketing buildSkuInfo(PmsSku sku, PmsGoods spu) {
        if (ObjectUtils.isEmpty(spu)) {
            return this;
        }
        this.skuInfo = JSON.toJSONString(SkuDetail.buildSkuDetail(sku, spu));
        return this;
    }


    /**
     * 判断是否是付邮试用
     *
     * @return 付邮试用 返回true   不是返回false
     */
    @JsonIgnore
    public boolean isPayPostage() {
        return "1".equals(this.type);
    }

    /**
     * 判断是否是返券试用
     *
     * @return 返券试用 返回true   不是返回false
     */
    @JsonIgnore
    public boolean isCouponType() {
        return "2".equals(this.type);
    }

    /**
     * 判断申请人数是否已满，true标识已满
     */
    @JsonIgnore
    public boolean isApplyFull() {
        if (this.applyNum == 0) {
            return false;
        }
        return this.alerdyApplyNum >= applyNum;
    }

    /**
     * 单品详情
     */
    @Data
    @ApiModel(description = "单品详情实体")
    public static class SkuDetail {

        /**
         * 单品名
         */
        @ApiModelProperty(value = "单品名")
        private String name;

        /**
         * 副标题
         */
        @ApiModelProperty(value = "副标题")
        private String subTitle;

        /**
         * 店铺id 平台的店铺id为0
         */
        @ApiModelProperty(value = "店铺id 平台的店铺id为0")
        private long storeId;

        /**
         * 单品默认图片
         */
        @ApiModelProperty(value = "单品默认图片")
        private String url;

        /**
         * 销售价格
         */
        @JsonDeserialize(using = CustomPriceDeserializer.class)
        @ApiModelProperty(value = "销售价格")
        private BigDecimal price;

        /**
         * 单品图片
         */
        @ApiModelProperty(value = "单品图片")
        private List<PmsSkuImage> skuImages;

        /**
         * 单品规格值
         */
        @ApiModelProperty(value = "单品规格值")
        private List<PmsSkuSpecValue> skuSpecValues;


        /**
         * pc版的详情描述
         */
        @ApiModelProperty(value = "pc版的详情描述")
        private String pcDesc;

        /**
         * 手机版详情描述
         */
        @ApiModelProperty(value = "手机版详情描述")
        private String mobileDesc;

        /**
         * 商品属性值
         */
        @ApiModelProperty(value = "商品属性值")
        private List<PmsGoodsAttributeValue> spuAttributeValues;

        /**
         * 商品id
         */
        @ApiModelProperty(value = "商品id")
        private long spuId;

        /**
         * 试用规则
         */
        @ApiModelProperty(value = "试用规则")
        private String tryRule;

        /**
         * 上架时间
         */
        @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        @ApiModelProperty(value = "上架时间")
        private LocalDateTime upTime = LocalDateTime.now();

        /**
         * 构建单品详请
         *
         * @param sku 单品实体
         * @param spu 商品实体
         * @return 单品详情实体
         */
        public static SkuDetail buildSkuDetail(PmsSku sku, PmsGoods spu) {
            SkuDetail skuDetail = new SkuDetail();
            skuDetail.setStoreId(sku.getStoreId());
            skuDetail.setName(sku.getName());
            skuDetail.setPrice(sku.getPrice());
            skuDetail.setSkuImages(sku.getSkuImages());
            skuDetail.setSkuSpecValues(sku.getSkuSpecValues());
            skuDetail.setUrl(sku.getUrl());
            skuDetail.setSubTitle(sku.getSubtitle());
            skuDetail.setPcDesc(spu.getPcDesc());
            skuDetail.setMobileDesc(spu.getMobileDesc());
            skuDetail.setSpuAttributeValues(spu.getSpuAttributeValues());
            skuDetail.setSpuId(sku.getSpuId());
            skuDetail.setUpTime(sku.getUpTime());
            return skuDetail;
        }


    }
}
