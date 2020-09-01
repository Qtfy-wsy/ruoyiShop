package com.ruoyi.goods.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.enums.StatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 单品对象 pms_sku
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Slf4j
@Data
public class PmsSku extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 商品id ，对应pms_goods中的id
     */
    @Excel(name = "商品id ，对应pms_goods中的id")
    private Long spuId;

    /**
     * 单品的编号
     */
    @Excel(name = "单品的编号")
    private String skuNo;

    /**
     * 单品的名称
     */
    @Excel(name = "单品的名称")
    private String name;

    /**
     * 单品的副标题
     */
    @Excel(name = "单品的副标题")
    private String subtitle;

    /**
     * 单品库存
     */
    @Excel(name = "单品库存")
    private Integer stock;

    /**
     * 预警库存
     */
    @Excel(name = "预警库存")
    private Long warningStock;

    /**
     * 单品价格
     */
    @Excel(name = "单品价格")
    private BigDecimal price;

    /**
     * 单品的重量
     */
    @Excel(name = "单品的重量")
    private BigDecimal weight;

    /**
     * 店铺id  如果是平台  则是0
     */
    @Excel(name = "店铺id  如果是平台  则是0")
    private Long storeId;

    /**
     * 单品上架状态 0 下架  1上架 2 违规下架 默认0
     */
    @Excel(name = "单品上架状态 0 下架  1上架 2 违规下架 默认0")
    private String shelvesStatus;

    /**
     * 默认图片的url
     */
    @Excel(name = "默认图片的url")
    private String url;

    /**
     * 审核状态  AuditE
     */
    @Excel(name = "审核状态  3 审核通过 2 审核未通过 1 审核中")
    private String status;

    /**
     * 拒绝原因
     */
    @Excel(name = "拒绝原因")
    private String reason;

    /**
     * 删除标记  0 未删除 1删除 默认0
     */
    private int delFlag;

    /**
     * 创建者名称
     */
    @Excel(name = "创建者名称")
    private String createName;

    /**
     * 修改者名称
     */
    @Excel(name = "修改者名称")
    private String modifyName;

    /**
     * 删除者名称
     */
    @Excel(name = "删除者名称")
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
     * 单品上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "单品上架时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime upTime;

    /**
     * 佣金比例  该字段是冗余字段，和商品表中的佣金比例是一个字断
     */
    @Excel(name = "佣金比例  该字段是冗余字段，和商品表中的佣金比例是一个字断")
    private BigDecimal commissionRate;

    /**
     * 二级佣金比例  该字段是冗余字段，和商品表中的佣金比例是一个字断
     */
    @Excel(name = "二级佣金比例  该字段是冗余字段，和商品表中的佣金比例是一个字断")
    private BigDecimal sCommissionRate;

    /**
     * 是否是虚拟商品 0 否 1 是  默认0  该字段是商品冗余字段 和商品表保持一致
     */
    @Excel(name = "是否是虚拟商品 0 否 1 是  默认0  该字段是商品冗余字段 和商品表保持一致")
    private String isVirtual;

    /**
     * 是否批发商品 0否 1是 默认0
     */
    @Excel(name = "是否批发商品 0否 1是 默认0")
    private String isBatchSku;

    /**
     * 物流模版id  该字段是冗余字段和pms_goods表中的保持一致
     */
    @Excel(name = "物流模版id  该字段是冗余字段和pms_goods表中的保持一致")
    private Long logisticsTemplateId;

    /**
     * 批发规则
     */
    private List<PmsSkuBatch> skuBatchList;
    /**
     * 门店单品管理实体集合
     */
    private List<StoreSku> storeSkuList;
    /**
     * 单品图片
     */

    private List<PmsSkuImage> skuImages;
    /**
     * 单品的规格和规格值组合
     */
    @ApiModelProperty(value = "单品的规格和规格值组合")
    private String specValues;
    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    /**
     * 单品会员价
     */

    private List<PmsSkuMemberPrice> skuMemberPrices;

    /**
     * 单品规格值
     */

    private List<PmsSkuSpecValue> skuSpecValues;

    /**
     * 规格标记
     */

    private String remark;
    /**
     * 移动版详情
     */

    private String mobileDesc;

    /**
     * 设置移动版详情
     *
     * @param desc 详情
     * @return sku
     */
    public PmsSku addMobileDesc(String desc) {
        mobileDesc = desc;
        return this;
    }

    /**
     * 设置查询 goods 参数  目前仅用于查询移动版详情
     *
     * @return 参数集
     */
    public Map<String, Object> querySpuParam() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", spuId);
        return map;
    }


    /**
     * 设置单品的门店单品信息
     *
     * @param storeSkuList 门店单品信息
     */
    public void setStoreSkuInfoAndPriceStock(List<StoreSku> storeSkuList) {
        this.storeSkuList = storeSkuList;
        this.setStoreSkuPriceAndStock();
    }

    /**
     * 构建门店单品管理实体集合
     *
     * @param storeSkuList 门店单品管理实体集合
     * @return 单品实体
     */
    public PmsSku buildStoreSkuList(List<StoreSku> storeSkuList) {
        this.storeSkuList = storeSkuList;
        return this;
    }

    /**
     * 获得单品的规格信息
     *
     * @return 返回单品的规格信息
     */
    @JsonIgnore
    public String getSpecValuesString() {
        if (CollectionUtils.isEmpty(this.skuSpecValues)) {
            return "";
        }
        System.out.println(skuSpecValues);
        log.debug("ceshi="+skuSpecValues);
        return skuSpecValues.stream().map(skuSpecValue -> new StringBuilder(skuSpecValue.getSpec().getName() + ":" + skuSpecValue.getValueRemark() + "|")).
                reduce(new StringBuilder(), StringBuilder::append).toString();
    }


    /**
     * 设置和单品关联的单品id(主要是 单品图片 ,单品会员价格,单品规格值)
     */
    public void setSkuLinkedSkuIdAndSpuId() {
        // 设置单品图片的单品id
        if (!CollectionUtils.isEmpty(skuImages)) {
            skuImages.stream().forEach(skuImage -> skuImage.setSkuIdAndSpuId(this.id, this.spuId));
        }

        // 设置单品的会员价
        if (!CollectionUtils.isEmpty(skuMemberPrices)) {
            skuMemberPrices.stream().forEach(skuMemberPrice -> skuMemberPrice.setSkuIdAndSpuId(this.id, this.spuId));
        }

        // 设置单品的规格值
        if (!CollectionUtils.isEmpty(skuSpecValues)) {
            skuSpecValues.stream().forEach(skuSpecValue -> skuSpecValue.setSkuIdAndSpuId(this.id, this.spuId));
        }
    }

    /**
     * 设置默认图片
     */
    public void setDefaultPic() {
        if (!CollectionUtils.isEmpty(this.skuImages)) {
            this.url = this.skuImages.get(0).getUrl();
        }
    }


    /**
     * 设置自定义的主键id
     *
     * @param index 索引
     */
    public void setCustomId(int index) {
        if (!hasId()) {
            this.id = String.valueOf(System.currentTimeMillis()) + this.spuId + index;
        }
    }

    /**
     * 判断是否已经存在主键
     *
     * @return 存在返回true  不存在返回false
     */
    @JsonIgnore
    public boolean hasId() {
        return !StringUtils.isEmpty(this.id);
    }


    /**
     * 校验库存
     *
     * @param buyNum 需要购买的数量
     * @return 如果有库存返回true  没有库存返回false
     */
    public boolean validateStock(int buyNum) {
        return this.stock > 0 && this.stock >= buyNum;
    }


    // 设置单品在门店的价格和库存
    public void setStoreSkuPriceAndStock() {
        if (CollectionUtils.isEmpty(this.storeSkuList)) {
            return;
        }

        this.price = this.storeSkuList.get(0).getPrice();
        this.stock = this.storeSkuList.get(0).getStock();
    }

    /**
     * 校验单品是否是上架状态
     *
     * @return 如果是上架则返回true  不是返回false
     */
    public boolean validateStatus() {
        return "1".equals(this.shelvesStatus);
    }


    /**
     * 审核通过返回true 否则返回false
     */
    public boolean inAuditedStatus() {
        return "0".equals(status);
    }


    /**
     * 校验单品
     *
     * @return 单品存在并且上架并且审核通过则返回true  否则返回false
     */
    @JsonIgnore
    public boolean validate(boolean isCrowdfunding) {
        // 如果是众筹 则不要校验上下架状态
        if (isCrowdfunding) {
            return "0".equals(this.status);
        }
        return validateStatus() && (StatusEnum.AuditType.SUCESS.code()+"").equals(this.status);
    }

    /**
     * 判断是否是虚拟商品 是返回true  否返回false
     *
     * @return 虚拟商品返回是
     */
    @JsonIgnore
    public boolean isVirtualSku() {
        return "1".equals(this.isVirtual);
    }

    /**
     * 处理批发规则列表
     */
    public void dealSkuBatchList() {
        if (!CollectionUtils.isEmpty(skuBatchList)) {
            distinctAndSortSkuBatch();
            fillSkuBatchListSkuIdAndSpuId();
        }
    }


    /**
     * 去重并排序单品批发规则
     */
    private void distinctAndSortSkuBatch() {
        skuBatchList = skuBatchList.stream().distinct().sorted().collect(Collectors.toList());
        skuBatchList = skuBatchList.subList(0, skuBatchList.size() >= 3 ? 3 : skuBatchList.size());
    }

    /**
     * 填充批发规则skuId
     */
    private void fillSkuBatchListSkuIdAndSpuId() {
        skuBatchList.forEach(skuBatch -> skuBatch.setSkuIdAndSpuId(id, spuId));
    }

    /**
     * 获取批发最低价格
     */
    public BigDecimal getLowestBatchPrice() {
        BigDecimal lowestBatchPrice = null;
        if (Objects.nonNull(price)) {
            lowestBatchPrice = new BigDecimal(price.doubleValue());
            if (CollectionUtils.isEmpty(skuBatchList)) {
                return lowestBatchPrice;
            } else {
                for (int i = 0; i < skuBatchList.size(); i++) {
                    if (i == 0) {
                        lowestBatchPrice = skuBatchList.get(i).getBatchPrice();
                        continue;
                    }
                    if (lowestBatchPrice.compareTo(skuBatchList.get(i).getBatchPrice()) > 0) {
                        lowestBatchPrice = skuBatchList.get(i).getBatchPrice();
                    }
                }
            }
        }
        return lowestBatchPrice;
    }

    /**
     * 获取起订量
     */
    public int getLimitBatchNum() {
        if (CollectionUtils.isEmpty(skuBatchList)) {
            return 1;
        } else {
            return skuBatchList.get(0).getBatchNum();
        }
    }

    public boolean isBatchSku() {
        return "1".equals(isBatchSku);
    }

    /**
     * 计算批发价格
     *
     * @param num 购买数量
     * @return 批发价格
     */
    public BigDecimal calculateBatchPrice(int num) {
        BigDecimal finalPrice = new BigDecimal(skuBatchList.get(0).getBatchPrice().doubleValue());
        for (PmsSkuBatch skuBatch : skuBatchList) {
            if (num < skuBatch.getBatchNum()) {
                break;
            }
            finalPrice = skuBatch.getBatchPrice();
        }
        return finalPrice;
    }

    public List<PmsSkuBatch> getSkuBatchList() {
        if (CollectionUtils.isEmpty(skuBatchList)) {
            return skuBatchList;
        }
        return setBatchInterval(skuBatchList);
    }

    public void setSkuBatchList(List<PmsSkuBatch> skuBatchList) {
        this.skuBatchList = skuBatchList;
    }

    /**
     * 设置批发区间
     *
     * @param skuBatchList 批发规则列表
     * @return 批发规则列表
     */
    private List<PmsSkuBatch> setBatchInterval(List<PmsSkuBatch> skuBatchList) {
        for (int i = 0; i < skuBatchList.size(); i++) {
            if (i == skuBatchList.size() - 1) {
                skuBatchList.get(i).setBatchInterval("≥" + skuBatchList.get(i).getBatchNum());
            } else {
                if (skuBatchList.get(i).getBatchNum() == skuBatchList.get(i + 1).getBatchNum() - 1) {
                    skuBatchList.get(i).setBatchInterval(skuBatchList.get(i).getBatchNum() + "");
                } else {
                    skuBatchList.get(i).setBatchInterval(skuBatchList.get(i).getBatchNum() + "-" + (skuBatchList.get(i + 1).getBatchNum() - 1));
                }
            }
        }
        return skuBatchList;
    }

    /**
     * 判断单品同时包含批发价和会员价
     *
     * @return 同时包含返回true
     */
    public boolean hasBatchPriceAndMemberPrice() {
        return !CollectionUtils.isEmpty(skuBatchList) && !CollectionUtils.isEmpty(skuMemberPrices);
    }

    /**
     * 添加修改商品时修正单品名称
     *
     * @param spuName 商品名称
     */
    public void fixSkuNameForSave(String spuName) {
        if (name.contains("#")) {
            subtitle = subtitle.replaceAll("#", "");
            name = name.replaceAll("#", "");
        } else {
            if (name.equals(spuName)) {
                subtitle = remark;
                name = name +"("+ remark+")";
            }
        }
    }

    public List<StoreSku> getStoreSkuList() {
        return storeSkuList;
    }

    public void setStoreSkuList(List<StoreSku> storeSkuList) {
        this.storeSkuList = storeSkuList;
    }

    public List<PmsSkuImage> getSkuImages() {
        return skuImages;
    }

    public void setSkuImages(List<PmsSkuImage> skuImages) {
        this.skuImages = skuImages;
    }

    public List<PmsSkuMemberPrice> getSkuMemberPrices() {
        return skuMemberPrices;
    }

    public void setSkuMemberPrices(List<PmsSkuMemberPrice> skuMemberPrices) {
        this.skuMemberPrices = skuMemberPrices;
    }

    public List<PmsSkuSpecValue> getSkuSpecValues() {
        return skuSpecValues;
    }

    public void setSkuSpecValues(List<PmsSkuSpecValue> skuSpecValues) {
        this.skuSpecValues = skuSpecValues;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobileDesc() {
        return mobileDesc;
    }

    public void setMobileDesc(String mobileDesc) {
        this.mobileDesc = mobileDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getWarningStock() {
        return warningStock;
    }

    public void setWarningStock(Long warningStock) {
        this.warningStock = warningStock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getShelvesStatus() {
        return shelvesStatus;
    }

    public void setShelvesStatus(String shelvesStatus) {
        this.shelvesStatus = shelvesStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public LocalDateTime getUpTime() {
        return upTime;
    }

    public void setUpTime(LocalDateTime upTime) {
        this.upTime = upTime;
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

    public String getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(String isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getIsBatchSku() {
        return isBatchSku;
    }

    public void setIsBatchSku(String isBatchSku) {
        this.isBatchSku = isBatchSku;
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
                .append("spuId", getSpuId())
                .append("skuNo", getSkuNo())
                .append("name", getName())
                .append("subtitle", getSubtitle())
                .append("stock", getStock())
                .append("warningStock", getWarningStock())
                .append("price", getPrice())
                .append("weight", getWeight())
                .append("storeId", getStoreId())
                .append("shelvesStatus", getShelvesStatus())
                .append("url", getUrl())
                .append("status", getStatus())
                .append("reason", getReason())
                .append("delFlag", getDelFlag())
                .append("createName", getCreateName())
                .append("modifyName", getModifyName())
                .append("delName", getDelName())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("delTime", getDelTime())
                .append("upTime", getUpTime())
                .append("commissionRate", getCommissionRate())
                .append("sCommissionRate", getsCommissionRate())
                .append("isVirtual", getIsVirtual())
                .append("isBatchSku", getIsBatchSku())
                .append("logisticsTemplateId", getLogisticsTemplateId())
                .toString();
    }
}
