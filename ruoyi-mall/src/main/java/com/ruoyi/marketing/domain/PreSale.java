package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 预售实体类
 */
@Data
@ApiModel(description = "预售实体类")
public class PreSale {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 营销id
     */
    @ApiModelProperty(value = "营销id")
    private long marketingId;

    /**
     * 预售类型 1 定金预售 2 全款预售
     */
    @ApiModelProperty(value = "预售类型 1 定金预售 2 全款预售")
    private String type;

    /**
     * 定金百分比
     */
    @ApiModelProperty(value = "定金百分比")
    private BigDecimal depositPre;

    /**
     * 单品id
     */
    @ApiModelProperty(value = "单品id")
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

    /**
     * 转化定金百分比（存数据库时候用，把整数转化为百分比小数）
     */
    @JsonIgnore
    public PreSale convertDepositPre() {
        if (Objects.nonNull(this.depositPre)) {
            this.depositPre = this.depositPre.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
        }
        return this;
    }

    /**
     * 添加预售类型
     *
     * @param type 预售类型
     * @return 返回当前实体
     */
    public PreSale addType(String type) {
        this.type = type;
        return this;
    }

}
