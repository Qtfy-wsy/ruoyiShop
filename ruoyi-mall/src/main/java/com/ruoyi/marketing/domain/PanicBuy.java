package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by 伊甸园商城 on 17/6/8.
 * 抢购实体
 */
@Data
@ApiModel(description = "抢购实体")
public class PanicBuy {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 促销id
     */
    @ApiModelProperty(value = "促销id")
    private long marketingId;

    /**
     * 折扣
     */
    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;

    /**
     * 限购个数
     */
    @ApiModelProperty(value = "限购个数")
    private int limitNum;

    /**
     * 抢购的单品id
     */
    @ApiModelProperty(value = "抢购的单品id")
    private String skuId;

    /**
     * 单品信息
     */
    @ApiModelProperty(value = "单品信息")
    private PmsSku sku;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 是否显示 0 不显示 1 显示
     */
    @ApiModelProperty(value = "是否显示 0 不显示 1 显示")
    private String isShow;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private int sort;

    /**
     * 开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;


    /**
     * 判断是否不含有主键id
     */
    @JsonIgnore
    public boolean notHasId() {
        return this.id == 0;
    }

    /**
     * 判断是否含有主键id
     */
    @JsonIgnore
    public boolean hasId() {
        return this.id != 0;
    }

}
