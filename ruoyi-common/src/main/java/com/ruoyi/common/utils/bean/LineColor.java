package com.ruoyi.common.utils.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 颜色实体类
 *
 * @author 伊甸园商城 created on 2020/4/14
 */
@Data
@ApiModel(description = "颜色实体")
public class LineColor {

    /**
     * 红色
     */
    @ApiModelProperty(value = "红色")
    private int r;

    /**
     * 绿色
     */
    @ApiModelProperty(value = "绿色")
    private int g;

    /**
     * 蓝色
     */
    @ApiModelProperty(value = "蓝色")
    private int b;

}
