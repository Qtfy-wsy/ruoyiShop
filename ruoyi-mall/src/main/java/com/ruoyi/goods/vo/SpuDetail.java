package com.ruoyi.goods.vo;


import com.ruoyi.goods.domain.*;
import com.ruoyi.marketing.domain.Coupon;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.order.vo.SkuMarketPriceDetail;
import com.ruoyi.store.vo.StoreResponse;
import com.ruoyi.store.vo.StoreScore;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 17/7/13.
 * 商品详情 此类专门给前端详情页返回的数据
 */
@Data
@ApiModel(description = "商品详情")
public class SpuDetail {

    /**
     * 单品id
     */
    @ApiModelProperty(value = "主键id")
    private String id;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private long spuId;

    /**
     * 单品名称
     */
    @ApiModelProperty(value = "单品名称")
    private String name;

    /**
     * 副标题
     */
    @ApiModelProperty(value = "副标题")
    private String subTitle;

    /**
     * 单品图片
     */
    @ApiModelProperty(value = "单品图片")
    private List<String> images;

    /**
     * 单品价格(优惠后的价格)
     */
    @ApiModelProperty(value = "单品价格(优惠后的价格)")
    private BigDecimal price;

    /**
     * 是否会员价格 默认否
     */
    @ApiModelProperty(value = "是否会员价格 默认否")
    private boolean isMemberPrice;

    /**
     * 是否促销价格 默认否
     */
    @ApiModelProperty(value = "是否促销价格 默认否")
    private boolean isMarketingPrice;

    /**
     * 单品原价格
     */
    @ApiModelProperty(value = "单品原价格")
    private BigDecimal oldPrice;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private int stock;

    /**
     * 单品规格值
     */
    @ApiModelProperty(value = "单品规格值")
    private List<PmsSkuSpecValue> skuSpecValues;

    /**
     * 商城名称
     */
    @ApiModelProperty(value = "商城名称")
    private String storeName;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 单品编号
     */
    @ApiModelProperty(value = "单品编号")
    private String skuNo;

    /**
     * 类型id
     */
    @ApiModelProperty(value = "类型id")
    private long typeId;

    /**
     * 商品的服务支持
     */
    @ApiModelProperty(value = "商品的服务支持")
    private List<PmsGoodsServiceSupport> spuServiceSupports;

    /**
     * 商品属性值
     */
    @ApiModelProperty(value = "商品属性值")
    private List<PmsGoodsAttributeValue> spuAttributeValues;

    /**
     * 店铺平均得分
     */
    @ApiModelProperty(value = "店铺平均得分")
    private BigDecimal storeAveScore;

    /**
     * 店铺被关注的总数
     */
    @ApiModelProperty(value = "店铺被关注的总数")
    private int storeFollowNum;

    /**
     * 促销信息
     */
    @ApiModelProperty(value = "促销信息")
    private List<Marketing> marketings;

    /**
     * 单品所属店铺的优惠券
     */
    @ApiModelProperty(value = "单品所属店铺的优惠券")
    private List<Coupon> coupons;

    /**
     * 商品的规格信息
     */
    @ApiModelProperty(value = "商品的规格信息")
    private List<PmsSpec> specs;

    /**
     * 单品评论的数量
     */
    @ApiModelProperty(value = "单品评论的数量")
    private int skuCommentNum;

    /**
     * 用户是否收藏了该商品
     */
    @ApiModelProperty(value = "用户是否收藏了该商品")
    private boolean hasAtten = false;

    /**
     * 分类信息
     */
    @ApiModelProperty(value = "分类信息")
    private List<PmsCategory> categories;

    /**
     * 店铺评分
     */
    @ApiModelProperty(value = "店铺评分")
    private StoreScore storeScore;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private PmsBrand brand;

    /**
     * 单品
     */
    @ApiModelProperty(value = "单品")
    private PmsSku sku;

    private List<PmsSku> skuList;
    /**
     * pc版的详情描述
     */
    @ApiModelProperty(value = " pc版的详情描述")
    private String pcDesc;

    /**
     * 手机版详情描述
     */
    @ApiModelProperty(value = "手机版详情描述")
    private String mobileDesc;


    /**
     * 预售规则
     */
    @ApiModelProperty(value = "预售规则")
    private String preSaleRule;

    /**
     * 批发规则
     */
    @ApiModelProperty(value = "批发规则")
    private List<PmsSkuBatch> skuBatchList;

    /**
     * 是否批发商品 0否 1是 默认0
     */
    @ApiModelProperty(value = "是否批发商品 0否 1是 默认0")
    private String isBatchSku;

    /**
     * 视频地址
     */
    @ApiModelProperty(value = "视频地址")
    private String video;

    /**
     * 视频封面地址
     */
    @ApiModelProperty(value = "视频封面地址")
    private String videoPic;


    /**
     * 构造商品详情信息
     *
     * @param sku 单品信息
     * @return 返回商品详情
     */
    public static SpuDetail build(final Optional<PmsSku> sku) {

        SpuDetail spuDetail = new SpuDetail();

        sku.ifPresent(s -> {
            spuDetail.id = s.getId();
            spuDetail.spuId = s.getSpuId();
            spuDetail.name = s.getName();
            spuDetail.subTitle = s.getSubtitle();
            spuDetail.price = s.getPrice();
            spuDetail.oldPrice = s.getPrice();
            spuDetail.stock = s.getStock();
            spuDetail.storeId = s.getStoreId();
            spuDetail.skuNo = s.getSkuNo();
            spuDetail.skuBatchList = s.getSkuBatchList();
            spuDetail.isBatchSku = s.getIsBatchSku();
            spuDetail.sku = s;
        });

        return spuDetail;
    }

    /**
     * 添加服务支持
     *
     * @param spuServiceSupports 服务支持
     * @return 返回当前对象
     */
    public SpuDetail addServiceSupports(List<PmsGoodsServiceSupport> spuServiceSupports) {
        this.spuServiceSupports = spuServiceSupports;
        return this;
    }

    /**
     * 设置促销价格
     *
     * @param skuMarketPriceDetail 单品价格详情
     * @return 返回当前对象
     */
    public SpuDetail addMarketPrice(SkuMarketPriceDetail skuMarketPriceDetail) {
        if (Objects.isNull(skuMarketPriceDetail)) {
            return this;
        }

        // 没有促销价格 直接返回
        if (skuMarketPriceDetail.noMarketPrice()) {
            return this;
        }

        // 设置单品的最终价格
        this.price = skuMarketPriceDetail.getPrice();
        // 会员价格
        if (skuMarketPriceDetail.isMemberPrice()) {
            this.setMemberPrice(true);
        } else {
            // 促销价格
            this.setMarketingPrice(true);
            if (CollectionUtils.isEmpty(this.marketings)) {
                marketings = new ArrayList<>();
            }
            marketings.add(skuMarketPriceDetail.getMarketing());
        }
        //批发规则
        if (!CollectionUtils.isEmpty(skuMarketPriceDetail.getSkuBatchList())) {
            skuBatchList = skuMarketPriceDetail.getSkuBatchList();
        }
        return this;
    }

    /**
     * 添加店铺信息
     *
     * @param storeInfo 店铺信息
     * @return 返回当前对象
     */
    public SpuDetail addStoreInfo(StoreResponse storeInfo) {
        if (Objects.isNull(storeInfo)) {
            return this;
        }
        this.setStoreName(storeInfo.getStoreInfo().getStoreName());
        //如果是平台自营，设置默认关注人数
        if (CommonConstant.ADMIN_STOREID == storeInfo.getStoreInfo().getId()) {
            this.setStoreFollowNum(CommonConstant.ADMIN_FOLLOW_NUM);
        } else {
            this.setStoreFollowNum(storeInfo.getFollowNum());
        }
        return this;
    }


    /**
     * 添加单品规格值
     *
     * @param skuSpecValues 规格值
     * @return 返回当前对象
     */
    public SpuDetail addSkuSpecValues(List<PmsSkuSpecValue> skuSpecValues) {
        this.skuSpecValues = skuSpecValues;
        return this;
    }

    /**
     * 添加商品属性
     *
     * @param spuAttributeValues 属性值
     * @return 返回当前对象
     */
    public SpuDetail addSpuAttributeValues(List<PmsGoodsAttributeValue> spuAttributeValues) {
        this.spuAttributeValues = spuAttributeValues;
        return this;
    }

    /**
     * 添加单品图片
     *
     * @param skuImages 单品图片
     * @return 返回当前对象
     */
    public SpuDetail addSkuImages(List<PmsSkuImage> skuImages) {
        if (CollectionUtils.isEmpty(skuImages)) {
            return this;
        }
        this.images = skuImages.stream().map(image -> image.getUrl()).collect(Collectors.toList());
        return this;
    }

    /**
     * 添加优惠券
     *
     * @param coupons 优惠券
     * @return 返回当前对象
     */
    public SpuDetail addCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
        return this;
    }

    /**
     * 增加促销(满减,满折,满赠,定金预售,全款预售)
     *
     * @param marketings 促销信息
     * @return 返回当前对象
     */
    public SpuDetail addMarketings(List<Marketing> marketings) {
        if (CollectionUtils.isEmpty(marketings)) {
            return this;
        }

        if (CollectionUtils.isEmpty(this.marketings)) {
            this.marketings = marketings;
        } else {
            this.marketings.addAll(marketings);
        }
        return this;
    }

    /**
     * 添加单品类型id
     *
     * @param spu 商品
     * @return 返回当前对
     */
    public SpuDetail addTypeId(PmsGoods spu) {
        if (Objects.isNull(spu)) {
            return this;
        }

        this.typeId = spu.getTypeId();

        return this;
    }


    /**
     * 添加视频详情   地址和封面
     *
     * @param spu 商品
     * @return 当前对象
     */
    public SpuDetail addVideoInfo(PmsGoods spu) {

        if (Objects.isNull(spu)) {
            return this;
        }

        this.video = spu.getVideo();
        this.videoPic = spu.getVideoPic();
        return this;
    }

    /**
     * 将价格重置为初始价格
     */
    public void resetPriceToOld() {
        if (Objects.nonNull(oldPrice)) {
            price = oldPrice;
        }
        if (!CollectionUtils.isEmpty(skuBatchList)) {
            skuBatchList.forEach(skuBatch -> {
                if (Objects.nonNull(skuBatch.getOldBatchPrice())) {
                    skuBatch.setBatchPrice(skuBatch.getOldBatchPrice());
                }
            });
        }
    }

    /**
     * 获取批发最低价格
     */
    public BigDecimal getLowestBatchPrice() {
        if (Objects.isNull(sku)) {
            return new BigDecimal(0);
        }
        return sku.getLowestBatchPrice();
    }
}
