package com.ruoyi.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 物流公司对象 oms_logistics_company
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Data
public class OmsLogisticsCompany extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 物流公司名称
     */
    @Excel(name = "物流公司名称")
    private String name;

    /**
     * 物流公司代码
     */
    @Excel(name = "物流公司代码")
    private String code;

    /**
     * 删除标记  0未删除 1 删除  默认0
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
     * 物流公司是否使用 0 未使用 1 使用 默认0
     */
    @ApiModelProperty(value = "物流公司是否使用 0 未使用 1 使用 默认0")
    private int isUse = 0;

    /**
     * 设置店铺使用物流公司
     */
    public void setStoreUseCompany() {
        this.isUse = 1;
    }
}
