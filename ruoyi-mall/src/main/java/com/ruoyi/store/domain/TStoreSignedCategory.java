package com.ruoyi.store.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 店铺的签约分类对象 t_store_signed_category
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public class TStoreSignedCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 店铺id  对应ls_store 表中的id
     */
    @Excel(name = "店铺id  对应ls_store 表中的id")
    private Long storeId;

    /**
     * 签约分类id   三级分类id
     */
    @Excel(name = "签约分类id   三级分类id  ")
    private Long cateId;
    /**
     * 0:待审核 1:有效
     */
    @ApiModelProperty(value = "是否有效")
    private boolean state;

    /**
     * 添加签约分类封装实体类
     *
     * @param storeId
     * @param cateId
     * @param state
     * @return
     */
    public TStoreSignedCategory getStoreSignedCategory(long storeId, long cateId, boolean state) {
        this.storeId = storeId;
        this.cateId = cateId;
        this.state = state;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("storeId", getStoreId())
                .append("cateId", getCateId())
                .toString();
    }
}
