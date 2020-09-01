package com.ruoyi.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 店铺销售实体
 */
@Data
@ApiModel(description = "店铺销售实体")
public class StoreSaleAmount {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 销售额
     */
    @ApiModelProperty(value = "销售额")
    private BigDecimal salesAmount;

    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private int salesVolume;

    /**
     * 新增订单的时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "新增订单的时间")
    private LocalDateTime newOrderTime;
}
