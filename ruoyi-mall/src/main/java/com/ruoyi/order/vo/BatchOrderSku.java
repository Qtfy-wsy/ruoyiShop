package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 2019/1/15.
 * 批量订单导入的订单
 */
@Data
@ApiModel(description = "批量订单导入的订单实体")
public class BatchOrderSku {


    /**
     * 主键唯一id
     */
    @ApiModelProperty(value = "主键唯一id")
    private String id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo = "";

    /**
     * 单品编号
     */
    @ApiModelProperty(value = "单品编号")
    private String skuNo = "";

    /**
     * 货品名称
     */
    @ApiModelProperty(value = "货品名称")
    private String skuName = "";

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private int num = 0;

    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名")
    private String receiptName = "";

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String receiptProvinceName = "";

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String receiptCityName = "";

    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String receiptDistrictName = "";

    /**
     * 市id
     */
    @ApiModelProperty(value = "市id")
    private long receiptCityId;


    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal reducedPrice = BigDecimal.ZERO;

    /**
     * 收货人的详细地址
     */
    @ApiModelProperty(value = "收货人的详细地址")
    private String receiptDetailAddress = "";

    /**
     * 收货人的手机号码
     */
    @ApiModelProperty(value = "收货人的手机号码")
    private String receiptMobile = "";

    /**
     * 单品规格
     */
    @ApiModelProperty(value = "单品规格")
    private String skuSpecs = "";

    /**
     * 单品单价
     */
    @ApiModelProperty(value = "单品单价")
    private BigDecimal unitPrice = new BigDecimal(0);

    /**
     * 单品总价
     */
    @ApiModelProperty(value = "单品总价")
    private BigDecimal price = new BigDecimal(0);

    /**
     * 导入失败原因
     */
    @ApiModelProperty(value = "导入失败原因")
    private String failDesc = "";

    /**
     * 是否有错误
     */
    @ApiModelProperty(value = "是否有错误")
    private boolean hasError = false;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;


    /**
     * 设置错误
     *
     * @param desc 错误描述
     */
    public void setError(String desc) {
        this.hasError = true;
        this.failDesc = desc;
    }


    /**
     * 校验参数
     *
     * @return 成功返回true 失败返回false
     */
    public boolean validateParams() {
        return !StringUtils.isEmpty(this.orderNo) && !StringUtils.isEmpty(this.skuNo) && this.num > 0 && !StringUtils.isEmpty(this.receiptName) && !StringUtils.isEmpty(this.receiptMobile) && !StringUtils.isEmpty(this.receiptProvinceName) && !StringUtils.isEmpty(receiptCityName) && !StringUtils.isEmpty(receiptDistrictName) && !StringUtils.isEmpty(this.receiptDetailAddress);
    }

    /**
     * 获得收货信息 主要是收货人姓名+手机+地址+详细地址
     *
     * @return 返货收货信息
     */
    @JsonIgnore
    public String getReceiptInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.receiptName).append(this.receiptMobile).append(this.receiptProvinceName).append(receiptCityName).append(receiptDistrictName).append(this.receiptDetailAddress);
        return sb.toString();
    }

    /**
     * 获取订单code唯一key
     */
    @JsonIgnore
    public String getOrderCodeDistinctKey() {
        return getReceiptInfo() + "storeId" + storeId;
    }

    /**
     * 返回是否有效
     *
     * @return 没有错误返回true  有错误返回false
     */
    @JsonIgnore
    public boolean isEffective() {
        return !hasError;
    }

    /**
     * 设置订单单品信息
     *
     * @param sku 单品信息
     */
    public void convertSkuToBatchOrderSku(PmsSku sku) {
        if (Objects.isNull(sku)) {
            this.setError("单品不存在");
            return;
        }

        this.skuName = sku.getName();

        this.skuSpecs = sku.getSpecValuesString();

        if (sku.isBatchSku()) {
            this.unitPrice = sku.calculateBatchPrice(this.num);
        } else {
            this.unitPrice = sku.getPrice();
        }

        this.price = this.unitPrice.multiply(new BigDecimal(this.num));
        this.storeId = sku.getStoreId();
    }

    /**
     * 获取省市区信息
     */
    @ApiModelProperty(value = "省市区信息")
    public String getReceiptAddress() {
        return receiptProvinceName + receiptCityName + receiptDistrictName;
    }
}
