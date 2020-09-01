package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 营销设置实体类
 */
@Data
@ApiModel(description = "营销设置实体")
public class MarketingSetting implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    private int id = -1;

    /**
     * pc端抢购广告图
     */
    @ApiModelProperty(value = "pc端抢购广告图")
    private String pcPanicPic;

    /**
     * mobile端抢购广告图
     */
    @ApiModelProperty(value = "mobile端抢购广告图")
    private String mobilePanicPic;

    /**
     * pc端预售广告图
     */
    @ApiModelProperty(value = "pc端预售广告图")
    private String pcPreSalePic;

    /**
     * mobile端预售广告图
     */
    @ApiModelProperty(value = "mobile端预售广告图")
    private String mobilePreSalePic;

    /**
     * pc端试用广告图
     */
    @ApiModelProperty(value = "pc端试用广告图")
    private String pcTryPic;

    /**
     * mobile端试用广告图
     */
    @ApiModelProperty(value = "mobile端试用广告图")
    private String mobileTryPic;

    /**
     * pc端积分商城广告图
     */
    @ApiModelProperty(value = "pc端积分商城广告图")
    private String pcPointPic;

    /**
     * 移动端积分商城广告图
     */
    @ApiModelProperty(value = "移动端积分商城广告图")
    private String mobilePointPic;

    /**
     * pc端红包广告图
     */
    @ApiModelProperty(value = "pc端红包广告图")
    private String pcRedEnvelopePic;

    /**
     * mobile端拼团广告图
     */
    @ApiModelProperty(value = "mobile端拼团广告图")
    private String mobileGroupPic;

    /**
     * pc端众筹广告图
     */
    @ApiModelProperty(value = "pc端众筹广告图")
    private String pcCrowdfundingPic;

    /**
     * mobile端众筹广告图
     */
    @ApiModelProperty(value = "mobile端众筹广告图")
    private String mobileCrowdfundingPic;

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
     * 众筹规则
     */
    @ApiModelProperty(value = "众筹规则")
    private String crowdfundingRule;
}
