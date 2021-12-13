package com.ruoyi.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.goods.domain.PmsShippingMethod;
import com.ruoyi.goods.domain.PmsShippingMethodFreeship;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 物流模版对象 oms_logistics_template
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class OmsLogisticsTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 物流模版名称
     */
    @Excel(name = "物流模版名称")
    private String name;

    /**
     * 是否默认模版 0 是 1 否 默认1
     */
    @Excel(name = "是否默认模版 0 是 1 否 默认1 ")
    private String isDefault;

    /**
     * 谁承担运费 0 买家 1 商家 默认 0 买家
     */
    @Excel(name = "谁承担运费 0 买家 1 商家 默认 0 买家")
    private String freightBear;

    /**
     * 计价方式 0 按件  1 按重量  默认0
     */
    @Excel(name = "计价方式 0 按件  1 按重量  默认0 ")
    private String pricintMethod;

    /**
     * 店铺id  平台的为 0
     */
    @Excel(name = "店铺id  平台的为 0 ")
    private Long storeId;

    /**
     * 删除标记 0 未删除 1 删除 默认0
     */
    private int delFlag;

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
     * 物流公司
     */
    private OmsLogisticsCompany logisticsCompany;

    /**
     * 物流模版的运费方式
     */
    private List<PmsShippingMethod> shippingMethods;

    /**
     * 物流模版的包邮
     */
    private List<PmsShippingMethodFreeship> shippingMethodFreeShips;

    /**
     * 用户的运费方式
     */

    private PmsShippingMethod customerShippingMethod;

    /**
     * 包邮运费方式
     */

    private PmsShippingMethodFreeship shippingMethodFreeShip;

    public OmsLogisticsCompany getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(OmsLogisticsCompany logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public List<PmsShippingMethod> getShippingMethods() {
        return shippingMethods;
    }

    public void setShippingMethods(List<PmsShippingMethod> shippingMethods) {
        this.shippingMethods = shippingMethods;
    }

    public List<PmsShippingMethodFreeship> getShippingMethodFreeShips() {
        return shippingMethodFreeShips;
    }

    public void setShippingMethodFreeShips(List<PmsShippingMethodFreeship> shippingMethodFreeShips) {
        this.shippingMethodFreeShips = shippingMethodFreeShips;
    }

    public PmsShippingMethod getCustomerShippingMethod() {
        return customerShippingMethod;
    }

    public void setCustomerShippingMethod(PmsShippingMethod customerShippingMethod) {
        this.customerShippingMethod = customerShippingMethod;
    }

    public PmsShippingMethodFreeship getShippingMethodFreeShip() {
        return shippingMethodFreeShip;
    }

    public void setShippingMethodFreeShip(PmsShippingMethodFreeship shippingMethodFreeShip) {
        this.shippingMethodFreeShip = shippingMethodFreeShip;
    }

    /**
     * 判断是否是商家承担运费
     *
     * @return 是返回true  否返回false
     */
    @JsonIgnore
    public boolean isStoreBearFreight() {
        return "1".equals(this.freightBear);
    }

    /**
     * 设置运费方式的模版id
     */
    public void setShippingMethodTemplateId() {

        shippingMethods.stream().forEach(shippingMethod -> shippingMethod.setTemplateId(this.id));

        shippingMethodFreeShips.stream().forEach(shippingMethodFreeShip -> shippingMethodFreeShip.setTemplateId(this.id));
    }


    /**
     * 判断是否是默认模版 是返回true 不是返回false
     *
     * @return 默认模版返回true  不是返回false
     */
    public boolean isDefaultTemplate() {
        return "0".equals(this.isDefault);
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

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getFreightBear() {
        return freightBear;
    }

    public void setFreightBear(String freightBear) {
        this.freightBear = freightBear;
    }

    public String getPricintMethod() {
        return pricintMethod;
    }

    public void setPricintMethod(String pricintMethod) {
        this.pricintMethod = pricintMethod;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("isDefault", getIsDefault())
                .append("freightBear", getFreightBear())
                .append("pricintMethod", getPricintMethod())
                .append("storeId", getStoreId())
                .append("delFlag", getDelFlag())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("delTime", getDelTime())
                .toString();
    }
}
