package com.ruoyi.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 会员浏览记录对象 ums_browse_record
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
@Data
public class UmsBrowseRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 会员id
     */
    @Excel(name = "会员id")
    private Long customerId;

    /**
     * 单品id
     */
    @Excel(name = "单品id")
    private String skuId;

    /**
     * 删除标记  0 未删除 1 删除
     */
    private int delFlag;

    /**
     * 单品
     */
    @ApiModelProperty(value = "单品")
    private PmsSku sku;

    /**
     * 单品类型
     */
    @JsonIgnore
    @ApiModelProperty(value = "单品类型")
    private long skuTypeId;

    /**
     * 构造添加浏览记录的实体
     *
     * @param customerId 会员id
     * @param skuId      单品id
     * @return 返回浏览记录
     */
    public static UmsBrowseRecord buildForAdd(long customerId, String skuId) {
        UmsBrowseRecord browseRecord = new UmsBrowseRecord();
        browseRecord.customerId = customerId;
        browseRecord.skuId = skuId;
        return browseRecord;
    }

    public String getGroupByTime() {
        return DateUtils.parseDateToStr("yyyy-MM-dd", this.getCreateTime());
    }

    /**
     * 获得商品id
     *
     * @return 返回商品id
     */
    @JsonIgnore
    public long getSpuId() {
        return Objects.nonNull(sku) ? sku.getSpuId() : 0;
    }

    /**
     * 获得单品名称
     *
     * @return 返回单品名称
     */
    @JsonIgnore
    public String getSkuName() {
        return Objects.nonNull(sku) ? sku.getName() : "";
    }

    /**
     * 获得单品价格
     *
     * @return 返回单品价格
     */
    @JsonIgnore
    public BigDecimal getPrice() {
        return Objects.nonNull(sku) ? sku.getPrice() : new BigDecimal(0);
    }

    /**
     * 获得单品的默认图片
     *
     * @return 返回单品默认图片
     */
    @JsonIgnore
    public String getImage() {
        return Objects.nonNull(sku) ? sku.getUrl() : "";
    }
}
