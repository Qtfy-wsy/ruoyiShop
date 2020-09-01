package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 拼团促销实体
 */
@Data
@ApiModel(description = "拼团促销实体")
public class GroupMarketing {

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
     * 拼团人数
     */
    @ApiModelProperty(value = "拼团人数")
    private int groupNum;

    /**
     * 限购数量 0表示不限制
     */
    @ApiModelProperty(value = "限购数量 0表示不限制")
    private int limitNum;

    /**
     * 拼团规则
     */
    @ApiModelProperty(value = "拼团规则")
    private String rule;

    /**
     * 拼团价格
     */
    @ApiModelProperty(value = "拼团价格")
    private BigDecimal price;

    /**
     * 拼团的单品id
     */
    @ApiModelProperty(value = "拼团的单品id")
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
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 校验拼团人数，大于等于2返回true 否则返回false
     */
    public boolean checkGroupNum() {
        return this.groupNum >= 2;
    }

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
