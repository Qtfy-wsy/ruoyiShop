package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * Created by 魔金商城 on 18/4/9
 * 门店红包实体类
 */
@Data
@ApiModel(description = "门店红包实体类")
public class StoreRedEnvelope {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 红包名称
     */
    @ApiModelProperty(value = "红包名称")
    private String name;

    /**
     * 红包生成的个数
     */
    @ApiModelProperty(value = "红包生成的个数")
    private int num;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String desc;

    /**
     * 满多少钱
     */
    @ApiModelProperty(value = "满多少钱")
    private BigDecimal fullPrice;

    /**
     * 减多少钱
     */
    @ApiModelProperty(value = "减多少钱")
    private BigDecimal price;

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
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 是否领完
     */
    @ApiModelProperty(value = "是否领完")
    private boolean runOut;

    /**
     * 剩余数量
     */
    @ApiModelProperty(value = "剩余数量")
    private int canReceiveCount;

    /**
     * 红包是否已领完 0 否  1 是  默认0
     */
    @ApiModelProperty(value = "红包是否已领完 0 否  1 是  默认0")
    private String status;

    /**
     * 构建是否领完
     *
     * @return 红包实体
     */
    public StoreRedEnvelope buildIsRunOut() {
        this.runOut = this.checkRunOut();
        return this;
    }

    /**
     * 是否领完 true 已领完
     */
    @JsonIgnore
    public boolean checkRunOut() {
        return this.canReceiveCount <= 0;
    }

    /**
     * 判断开始时间是否大于结束时间
     *
     * @return 开始时间大于结束时间返回true, 小于false
     */
    public boolean toCompareStartTime() {
        return this.getStartTime().isAfter(this.getEndTime());
    }

}
