package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 促销图片实体类
 *
 * @author 魔金商城 created on 2020/5/12
 */
@Data
@ApiModel(description = "促销图片实体")
public class MarketingPic {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * pc端的图片
     */
    @ApiModelProperty(value = "pc端的图片")
    private String pcPic;

    /**
     * 移动端图片
     */
    @ApiModelProperty(value = "移动端图片")
    private String mobilePic;

    /**
     * 促销类型 1 折扣 2 预售 3 拼团 4 试用
     */
    @ApiModelProperty(value = "促销类型 1 折扣 2 预售 3 拼团 4 试用")
    private String type;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 试用规则
     */
    @ApiModelProperty(value = "试用规则")
    private String tryRule;

    /**
     * 预售规则
     */
    @ApiModelProperty(value = "预售规则")
    private String preSaleRule;


    /**
     * 添加促销类型
     *
     * @param type 促销类型
     * @return 当前实体
     */
    public MarketingPic addType(String type) {
        this.type = type;
        return this;
    }

    /**
     * 添加店铺id
     *
     * @param storeId 店铺id
     * @return 当前实体
     */
    public MarketingPic addStoreId(long storeId) {
        this.storeId = storeId;
        return this;
    }

}
