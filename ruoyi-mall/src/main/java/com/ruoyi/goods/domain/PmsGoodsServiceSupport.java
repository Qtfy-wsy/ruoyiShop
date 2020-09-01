package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商品和服务支持的关联对象 pms_goods_service_support
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public class PmsGoodsServiceSupport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品id 对应pms_goods表中的id
     */
    @Excel(name = "商品id 对应pms_goods表中的id")
    private Long spuId;

    /**
     * 服务支持id  对应ls_service_support中的id
     */
    @Excel(name = "服务支持id  对应ls_service_support中的id")
    private Long serviceSupportId;

    /**
     * 删除标记 0 未删除 1 删除 默认0
     */
    private int delFlag;

    /**
     * 服务支持
     */
    private PmsServiceSupport serviceSupport;

    public PmsServiceSupport getServiceSupport() {
        return serviceSupport;
    }

    public void setServiceSupport(PmsServiceSupport serviceSupport) {
        this.serviceSupport = serviceSupport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Long getServiceSupportId() {
        return serviceSupportId;
    }

    public void setServiceSupportId(Long serviceSupportId) {
        this.serviceSupportId = serviceSupportId;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("spuId", getSpuId())
                .append("serviceSupportId", getServiceSupportId())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
