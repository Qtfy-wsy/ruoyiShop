package com.ruoyi.marketing.vo;


import com.ruoyi.goods.domain.*;
import com.ruoyi.marketing.domain.CrowdfundingProgress;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.MarketingSetting;
import com.ruoyi.store.domain.TStoreInfo;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 众筹商品详情实体(前端返回使用)
 *
 * @author SK
 * @since 2018/4/24
 */
@Data
public class CrowdFundingSpuDetail {

    /**
     * 单品id
     */
    private String id;

    /**
     * 商品id
     */
    private long spuId;

    /**
     * 单品名称
     */
    private String name;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 单品编号
     */
    private String skuNo;

    /**
     * 促销实体
     */
    private Marketing marketing;

    /**
     * 已筹金额
     */
    private BigDecimal alreadyMoney = new BigDecimal(0);

    /**
     * 众筹金额(需求金额)
     */
    private BigDecimal needMoney;

    /**
     * 商城名称
     */
    private String storeName;

    /**
     * 参与人数
     */
    private int supportCustomerNum;

    /**
     * 达成百分比
     */
    private int reachPercent;

    /**
     * 剩余天数
     */
    private long residualDay;

    /**
     * 项目进展
     */
    private List<CrowdfundingProgress> crowdfundingProgressList;

    /**
     * 众筹说明
     */
    private String crowdFundingRule;

    /**
     * pc版的详情描述
     */
    private String pcDesc;

    /**
     * 手机版详情描述
     */
    private String mobileDesc;

    /**
     * 单品实体
     */
    private PmsSku sku;

    /**
     * 分类信息
     */
    private List<PmsCategory> categories;

    /**
     * 商品的规格信息
     */
    private List<PmsSpec> specs;

    /**
     * 商品的服务支持
     */
    private List<PmsGoodsServiceSupport> spuServiceSupports;

    /**
     * 商品属性值
     */
    private List<PmsGoodsAttributeValue> spuAttributeValues;


    /**
     * 单品规格值
     */
    private List<PmsSkuSpecValue> skuSpecValues;


    /**
     * 单品图片
     */
    private List<String> images;

    /**
     * 单品价格(众筹价格)
     */
    private BigDecimal price;

    /**
     * 店铺id
     */
    private long storeId;


    /**
     * 构造商品详情信息
     *
     * @param sku 单品信息
     * @return 返回商品详情
     */
    public static CrowdFundingSpuDetail build(final PmsSku sku) {
        CrowdFundingSpuDetail crowdFundingSpuDetail = new CrowdFundingSpuDetail();
        crowdFundingSpuDetail.id = sku.getId();
        crowdFundingSpuDetail.spuId = sku.getSpuId();
        crowdFundingSpuDetail.name = sku.getName();
        crowdFundingSpuDetail.subTitle = sku.getSubtitle();
        crowdFundingSpuDetail.price = sku.getPrice();
        crowdFundingSpuDetail.storeId = sku.getStoreId();
        crowdFundingSpuDetail.skuNo = sku.getSkuNo();
        if (!CollectionUtils.isEmpty(sku.getSkuImages())) {
            crowdFundingSpuDetail.images = sku.getSkuImages().stream().map(PmsSkuImage::getUrl).collect(Collectors.toList());
        }
        crowdFundingSpuDetail.skuSpecValues = sku.getSkuSpecValues();
        crowdFundingSpuDetail.sku = sku;
        return crowdFundingSpuDetail;
    }

    /**
     * 添加服务支持
     *
     * @param spuServiceSupports 服务支持
     * @return 返回当前对象
     */
    public CrowdFundingSpuDetail addServiceSupports(List<PmsGoodsServiceSupport> spuServiceSupports) {
        this.spuServiceSupports = spuServiceSupports;
        return this;
    }


    /**
     * 添加商品属性
     *
     * @param spuAttributeValues 属性值
     * @return 返回当前对象
     */
    public CrowdFundingSpuDetail addSpuAttributeValues(List<PmsGoodsAttributeValue> spuAttributeValues) {
        this.spuAttributeValues = spuAttributeValues;
        return this;
    }

    /**
     * 构建分类信息
     *
     * @param categories 分类信息
     * @return 返回当前对象
     */
    public CrowdFundingSpuDetail addCategories(List<PmsCategory> categories) {
        this.categories = categories;
        return this;
    }

    /**
     * 获得促销单品id集合
     */
    public List<String> getSkuIdList() {
        if (Objects.nonNull(this.marketing)) {
            return marketing.getMarketingSkuIds();
        }
        return Collections.emptyList();
    }

    /**
     * 设置店铺名称
     *
     * @param storeInfo 店铺信息
     * @return 当前对象
     */
    public CrowdFundingSpuDetail addStoreName(TStoreInfo storeInfo) {
        if (Objects.nonNull(storeInfo)) {
            this.storeName = storeInfo.getStoreName();
        }
        return this;
    }


    /**
     * 构建返回参数
     *
     * @param marketing                促销
     * @param crowdfundingProgressList 众筹项目进度
     * @param marketingSetting         促销设置实体
     * @param supportCustomerNum       支持人数
     * @return 当前对象
     */
    public CrowdFundingSpuDetail buildForSite(final Marketing marketing, final List<CrowdfundingProgress> crowdfundingProgressList, final MarketingSetting marketingSetting, final int supportCustomerNum) {
        this.alreadyMoney = marketing.getCrowdFunding().getAlreadyMoney();
        this.needMoney = marketing.getCrowdFunding().getMoney();
        this.marketing = marketing;
        this.crowdfundingProgressList = crowdfundingProgressList;
        if (Objects.nonNull(marketingSetting)) {
            this.crowdFundingRule = marketingSetting.getCrowdfundingRule();
        }
        this.supportCustomerNum = supportCustomerNum;
        calReachPercentAndResidualDay();
        return this;
    }

    /**
     * 计算达成百分比和剩余天数
     */
    private void calReachPercentAndResidualDay() {
        this.reachPercent = this.alreadyMoney.divide(this.needMoney, 2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).intValue();
        Duration duration = Duration.between(LocalDateTime.now(), marketing.getEndTime());
        this.residualDay = duration.toDays() + 1;
    }

}
