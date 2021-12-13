package com.ruoyi.store.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 门店评价对象 t_store_evaluation
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Data
public class TStoreEvaluation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 门店评分
     */
    @Excel(name = "门店评分")
    private Long score;

    /**
     * 评价
     */
    @Excel(name = "评价")
    private String desc;

    /**
     * 订单code
     */
    @Excel(name = "订单code")
    private String orderCode;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private Long storeId;

    /**
     * 会员id
     */
    @Excel(name = "会员id")
    private Long customerId;
    /**
     * 店铺头像
     */
    @ApiModelProperty(value = "店铺头像")
    private String avatarPicture;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 店铺地址
     */
    @ApiModelProperty(value = "店铺地址")
    private String companyAddress;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 营业时间
     */
    @ApiModelProperty(value = "营业时间")
    private String businessTime;

    /**
     * 公交线路
     */
    @ApiModelProperty(value = "公交线路")
    private String busRoute;

    /**
     * 门店平均评价
     */
    @ApiModelProperty(value = "门店平均评价")
    private long avgScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("score", getScore())
                .append("desc", getDesc())
                .append("orderCode", getOrderCode())
                .append("storeId", getStoreId())
                .append("customerId", getCustomerId())
                .append("createTime", getCreateTime())
                .toString();
    }
}
