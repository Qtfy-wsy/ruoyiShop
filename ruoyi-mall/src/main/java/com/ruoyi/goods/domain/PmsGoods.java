package com.ruoyi.goods.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.enums.StatusEnum;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 商品对象 pms_goods
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
public class PmsGoods extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String name;

    /**
     * 商品副标题
     */
    @Excel(name = "商品副标题")
    private String subtitle;

    /**
     * 销售价
     */
    @Excel(name = "销售价")
    private BigDecimal price;

    /**
     * PC版详情
     */
    @Excel(name = "PC版详情")
    private String pcDesc;

    /**
     * 手机版详情
     */
    @Excel(name = "手机版详情")
    private String mobileDesc;

    /**
     * seo标题
     */
    @Excel(name = "seo标题")
    private String seoTitle;

    /**
     * seo关键字
     */
    @Excel(name = "seo关键字")
    private String seoKeywords;

    /**
     * see描述
     */
    @Excel(name = "see描述")
    private String seoDesc;

    /**
     * 商品的店铺id 如果是平台的则为0
     */
    @Excel(name = "商品的店铺id 如果是平台的则为0 ")
    private Long storeId;

    /**
     * 一级分类id
     */
    @Excel(name = "一级分类id")
    private Long firstCateId;

    /**
     * 二级分类id
     */
    @Excel(name = "二级分类id")
    private Long secondCateId;

    /**
     * 三级分类id
     */
    @Excel(name = "三级分类id")
    private Long thirdCateId;

    /**
     * 类型id
     */
    @Excel(name = "类型id")
    private Long typeId;

    /**
     * 品牌id
     */
    @Excel(name = "品牌id")
    private Long brandId;

    /**
     * 店铺一级分类
     */
    @Excel(name = "店铺一级分类")
    private Long storeFcateId;

    /**
     * 店铺二级分类
     */
    @Excel(name = "店铺二级分类")
    private Long storeScateId;

    /**
     * 店铺三级分类
     */
    @Excel(name = "店铺三级分类")
    private Long storeTcateId;

    /**
     * 商品图片
     */
    @Excel(name = "商品图片")
    private String url;

    /**
     * 删除标记   0未删除 1删除 默认0
     */
    private int delFlag;

    /**
     * 创建人的名称
     */
    @Excel(name = "创建人的名称")
    private String createName;

    /**
     * 修改人的名称
     */
    @Excel(name = "修改人的名称")
    private String modifyName;

    /**
     * 删除人的名字
     */
    @Excel(name = "删除人的名字")
    private String delName;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /**
     * 佣金比例
     */
    @Excel(name = "佣金比例")
    private BigDecimal commissionRate;

    /**
     * 二级佣金比例
     */
    @Excel(name = "二级佣金比例")
    private BigDecimal sCommissionRate;

    /**
     * 审核状态  状态  0 申请中  1通过 2 拒绝
     */
    @Excel(name = "审核状态  0 审核通过 1 审核未通过 2 审核中")
    private String status;

    /**
     * 商品上架状态 0 下架  1上架 2违规下架 默认0
     */
    @Excel(name = "商品上架状态 0 下架  1上架 2违规下架 默认0")
    private String shelvesStatus;

    /**
     * 是否是虚拟商品 0 否 1 是默认0
     */
    @Excel(name = "是否是虚拟商品 0 否 1 是默认0")
    private String isVirtual;

    /**
     * 商品视频地址
     */
    @Excel(name = "商品视频地址")
    private String video;

    /**
     * 商品视频封面地址
     */
    @Excel(name = "商品视频封面地址")
    private String videoPic;

    /**
     * 物流模版id 对应oms_logistics_template表的id
     */
    @Excel(name = "物流模版id 对应oms_logistics_template表的id")
    private Long logisticsTemplateId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 商品下面的单品
     */
    private List<PmsSku> skus;

    /**
     * 商品的服务支持
     */
    private List<PmsGoodsServiceSupport> spuServiceSupports;

    /**
     * 商品规格值
     */
    private List<PmsGoodsSpecValue> spuSpecValues;

    /**
     * 商品图片
     */
    private List<PmsGoodsImage> spuImages;

    /**
     * 商品属性值
     */
    private List<PmsGoodsAttributeValue> spuAttributeValues;

    /**
     * 商品导入id
     */
    private long spuImportId;

    /**
     * 一级分类
     */
    private PmsCategory firstCategory;

    /**
     * 二级分类
     */
    private PmsCategory secondCategory;

    /**
     * 三级分类
     */
    private PmsCategory thirdCategory;

    /**
     * 品牌
     */
    private PmsBrand brand;

    /**
     * 构造删除商品的实体
     *
     * @param name    操作人
     * @param id      商品id
     * @param storeId 店铺id
     * @return 返回商品信息
     */
    public static PmsGoods buildForDelete(String name, long id, long storeId) {
        PmsGoods spu = new PmsGoods();
        spu.id = id;
        spu.delFlag = 1;
        spu.delName = name;
        spu.storeId = storeId;
        return spu;
    }

    /**
     * 判断是否是店铺的商品
     *
     * @return 是返回true  不是返回false  根据store是否是0 判断  0 是平台 不是0 是店铺
     */
    @JsonIgnore
    public boolean isStoreSpu() {
        if (!ObjectUtils.isEmpty(this.storeId)) {
            return CommonConstant.ADMIN_STOREID != this.storeId;
        }
        return false;
    }

    /**
     * 设置新增商品的默认值
     *
     * @param name    操作人名称
     * @param storeId 店铺id
     * @return 返回商品
     */
    public PmsGoods setDefaultValuesForAdd(String name, long storeId) {
        this.createName = name;
        this.delFlag = 0;
        this.storeId = storeId;
        setDefaultPic();
        return this;
    }

    /**
     * 设置默认图片
     */
    public void setDefaultPic() {
        if (!CollectionUtils.isEmpty(this.spuImages)) {
            this.url = this.spuImages.get(0).getUrl();
        }
    }

    /**
     * 设置单品的店铺id和审核状态和上下加状态
     */
    public void setSkuStoreIdAndAuditAndShelvesStatus(boolean isNeedAudit) {

        // 设置商品的审核状态
        setAuditStatus(isNeedAudit);

        if (!CollectionUtils.isEmpty(this.skus)) {
            skus.stream().forEach(sku -> {
                sku.setStoreId(this.storeId);
                // 单品的审核状态和商品的一致
                sku.setStatus(this.status);
                // 单品的上下架状态和商品的一致
                sku.setShelvesStatus(this.shelvesStatus);
            });
        }
    }

    /**
     * 设置单品是否虚拟商品
     */
    public void setSkuIsVirtual() {
        skus.forEach(sku -> sku.setIsVirtual(this.getIsVirtual()));
    }

    /**
     * 设置单品物流模版id
     */
    public void setSkuLogisticsTemplateId() {

        // 虚拟商品把物流模版id设为0
        if ("1".equals(this.getIsVirtual())) {
            this.setLogisticsTemplateId(0L);
        }

        skus.forEach(sku -> sku.setLogisticsTemplateId(this.getLogisticsTemplateId()));
    }

    /**
     * 设置单品的审核状态
     */
    public void setSkuStatus() {
        if (!CollectionUtils.isEmpty(skus)) {
            skus.stream().forEach(sku -> sku.setStatus(this.status));
        }
    }

    /**
     * 设置审核的状态
     */
    public void setAuditStatus(boolean isNeedAudit) {
        // 平台的 或者审核开关关闭的 不需要审核 直接审核通过
        if (CommonConstant.ADMIN_STOREID == this.storeId || !isNeedAudit) {
            this.status = StatusEnum.AuditType.SUCESS.code()+"";
        } else {
            // 店铺的需要审核
            this.status = StatusEnum.AuditType.INIT.code()+"";
        }
    }

    /**
     * 设置单品的店铺id和上下架状态
     */
    public void setSkuStoreIdAndShelvesStatus() {
        // 设置单品的店铺id
        if (!CollectionUtils.isEmpty(skus)) {
            skus.stream().forEach(sku -> {
                sku.setStoreId(this.storeId);
                // 单品的上下架状态和商品的一致
                sku.setShelvesStatus(this.shelvesStatus);
            });
        }
    }

    /**
     * 设置和商品关联的商品id主要是(单品,服务支持,规格值)
     */
    public void setSpuLinkedSpuId() {

        // 设置单品的商品id
        if (!CollectionUtils.isEmpty(skus)) {
            skus.stream().forEach(sku -> sku.setSpuId(this.id));
        }
        // 设置商品服务支持的商品id
        if (!CollectionUtils.isEmpty(spuServiceSupports)) {
            spuServiceSupports.stream().forEach(spuServiceSupport -> spuServiceSupport.setSpuId(this.id));
        }

        // 设置商品规格值的商品id
        if (!CollectionUtils.isEmpty(spuSpecValues)) {
            spuSpecValues.stream().forEach(spuSpecValue -> spuSpecValue.setSpuId(this.id));
        }

        // 设置图片的商品id
        if (!CollectionUtils.isEmpty(spuImages)) {
            spuImages.stream().forEach(spuImage -> spuImage.setSpuId(this.id));
        }

        // 设置属性值的商品id
        if (!CollectionUtils.isEmpty(spuAttributeValues)) {
            spuAttributeValues.stream().forEach(spuAttributeValue -> spuAttributeValue.setSpuId(this.id));
        }
    }

    /**
     * 获取有会员价单品id集合
     *
     * @return 有会员价单品id集合
     */

    public List<String> getHasMemberPriceSkuIds() {
        if (CollectionUtils.isEmpty(this.skus)) {
            return Collections.emptyList();
        } else {
            List<String> skuIds = new ArrayList<>();
            this.skus.forEach(sku -> {
                if (!CollectionUtils.isEmpty(sku.getSkuMemberPrices()) && !ObjectUtils.isEmpty(sku.getId())) {
                    skuIds.add(sku.getId());
                }
            });
            return skuIds;
        }
    }


    /**
     * 获取有批发规则单品id集合
     *
     * @return 有批发规则单品id集合
     */

    public List<String> getHasBatchSkuIds() {
        if (CollectionUtils.isEmpty(this.skus)) {
            return Collections.emptyList();
        } else {
            List<String> skuIds = new ArrayList<>();
            this.skus.forEach(sku -> {
                if (!CollectionUtils.isEmpty(sku.getSkuBatchList()) && !ObjectUtils.isEmpty(sku.getId())) {
                    skuIds.add(sku.getId());
                }
            });
            return skuIds;
        }
    }

    public List<PmsSku> getSkus() {
        return skus;
    }

    public void setSkus(List<PmsSku> skus) {
        this.skus = skus;
    }

    public List<PmsGoodsServiceSupport> getSpuServiceSupports() {
        return spuServiceSupports;
    }

    public void setSpuServiceSupports(List<PmsGoodsServiceSupport> spuServiceSupports) {
        this.spuServiceSupports = spuServiceSupports;
    }

    public List<PmsGoodsSpecValue> getSpuSpecValues() {
        return spuSpecValues;
    }

    public void setSpuSpecValues(List<PmsGoodsSpecValue> spuSpecValues) {
        this.spuSpecValues = spuSpecValues;
    }

    public List<PmsGoodsImage> getSpuImages() {
        return spuImages;
    }

    public void setSpuImages(List<PmsGoodsImage> spuImages) {
        this.spuImages = spuImages;
    }

    public List<PmsGoodsAttributeValue> getSpuAttributeValues() {
        return spuAttributeValues;
    }

    public void setSpuAttributeValues(List<PmsGoodsAttributeValue> spuAttributeValues) {
        this.spuAttributeValues = spuAttributeValues;
    }

    public long getSpuImportId() {
        return spuImportId;
    }

    public void setSpuImportId(long spuImportId) {
        this.spuImportId = spuImportId;
    }

    public PmsCategory getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(PmsCategory firstCategory) {
        this.firstCategory = firstCategory;
    }

    public PmsCategory getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(PmsCategory secondCategory) {
        this.secondCategory = secondCategory;
    }

    public PmsCategory getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(PmsCategory thirdCategory) {
        this.thirdCategory = thirdCategory;
    }

    public PmsBrand getBrand() {
        return brand;
    }

    public void setBrand(PmsBrand brand) {
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPcDesc() {
        return pcDesc;
    }

    public void setPcDesc(String pcDesc) {
        this.pcDesc = pcDesc;
    }

    public String getMobileDesc() {
        return mobileDesc;
    }

    public void setMobileDesc(String mobileDesc) {
        this.mobileDesc = mobileDesc;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoDesc() {
        return seoDesc;
    }

    public void setSeoDesc(String seoDesc) {
        this.seoDesc = seoDesc;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getFirstCateId() {
        return firstCateId;
    }

    public void setFirstCateId(Long firstCateId) {
        this.firstCateId = firstCateId;
    }

    public Long getSecondCateId() {
        return secondCateId;
    }

    public void setSecondCateId(Long secondCateId) {
        this.secondCateId = secondCateId;
    }

    public Long getThirdCateId() {
        return thirdCateId;
    }

    public void setThirdCateId(Long thirdCateId) {
        this.thirdCateId = thirdCateId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getStoreFcateId() {
        return storeFcateId;
    }

    public void setStoreFcateId(Long storeFcateId) {
        this.storeFcateId = storeFcateId;
    }

    public Long getStoreScateId() {
        return storeScateId;
    }

    public void setStoreScateId(Long storeScateId) {
        this.storeScateId = storeScateId;
    }

    public Long getStoreTcateId() {
        return storeTcateId;
    }

    public void setStoreTcateId(Long storeTcateId) {
        this.storeTcateId = storeTcateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public String getDelName() {
        return delName;
    }

    public void setDelName(String delName) {
        this.delName = delName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public BigDecimal getsCommissionRate() {
        return sCommissionRate;
    }

    public void setsCommissionRate(BigDecimal sCommissionRate) {
        this.sCommissionRate = sCommissionRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShelvesStatus() {
        return shelvesStatus;
    }

    public void setShelvesStatus(String shelvesStatus) {
        this.shelvesStatus = shelvesStatus;
    }

    public String getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(String isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideoPic() {
        return videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public Long getLogisticsTemplateId() {
        return logisticsTemplateId;
    }

    public void setLogisticsTemplateId(Long logisticsTemplateId) {
        this.logisticsTemplateId = logisticsTemplateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("subtitle", getSubtitle())
                .append("price", getPrice())
                .append("pcDesc", getPcDesc())
                .append("mobileDesc", getMobileDesc())
                .append("seoTitle", getSeoTitle())
                .append("seoKeywords", getSeoKeywords())
                .append("seoDesc", getSeoDesc())
                .append("storeId", getStoreId())
                .append("firstCateId", getFirstCateId())
                .append("secondCateId", getSecondCateId())
                .append("thirdCateId", getThirdCateId())
                .append("typeId", getTypeId())
                .append("brandId", getBrandId())
                .append("storeFcateId", getStoreFcateId())
                .append("storeScateId", getStoreScateId())
                .append("storeTcateId", getStoreTcateId())
                .append("url", getUrl())
                .append("delFlag", getDelFlag())
                .append("createName", getCreateName())
                .append("modifyName", getModifyName())
                .append("delName", getDelName())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("delTime", getDelTime())
                .append("commissionRate", getCommissionRate())
                .append("sCommissionRate", getsCommissionRate())
                .append("status", getStatus())
                .append("shelvesStatus", getShelvesStatus())
                .append("isVirtual", getIsVirtual())
                .append("video", getVideo())
                .append("videoPic", getVideoPic())
                .append("logisticsTemplateId", getLogisticsTemplateId())
                .toString();
    }
}
