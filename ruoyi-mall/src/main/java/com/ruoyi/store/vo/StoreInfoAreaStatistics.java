package com.ruoyi.store.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 店铺地区统计实体类
 *
 * @author 伊甸园商城 created on 2019/4/11
 */
@Data
@ApiModel(description = "店铺地区统计实体类")
public class StoreInfoAreaStatistics {

    /**
     * 店铺的省名称
     */
    @ApiModelProperty(value = "店铺的省名称")
    private String provinceName;

    /**
     * 店铺的数量
     */
    @ApiModelProperty(value = "店铺的数量")
    private int storeInfoNum;

    /**
     * 店铺的省份id
     */
    @ApiModelProperty(value = "店铺的省份id")
    private long provinceId;

}
