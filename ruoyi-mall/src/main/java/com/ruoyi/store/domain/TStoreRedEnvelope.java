package com.ruoyi.store.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 门店红包对象 t_store_red_envelope
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public class TStoreRedEnvelope extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 红包名称
     */
    @Excel(name = "红包名称")
    private String name;

    /**
     * 红包生成的个数
     */
    @Excel(name = "红包生成的个数")
    private Long num;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String desc;

    /**
     * 满多少钱
     */
    @Excel(name = "满多少钱")
    private BigDecimal fullPrice;

    /**
     * 减多少钱
     */
    @Excel(name = "减多少钱")
    private BigDecimal price;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date starttime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endtime;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private Long storeId;

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

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("num", getNum())
                .append("desc", getDesc())
                .append("fullPrice", getFullPrice())
                .append("price", getPrice())
                .append("starttime", getStarttime())
                .append("endtime", getEndtime())
                .append("storeId", getStoreId())
                .toString();
    }
}
