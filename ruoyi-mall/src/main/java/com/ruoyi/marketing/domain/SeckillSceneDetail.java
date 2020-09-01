package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 魔金商城 on 2020/5/15.
 * 秒杀场次详情
 */
@Data
@ApiModel(description = "秒杀场次详情")
public class SeckillSceneDetail {

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
     * 秒杀场次
     */
    @ApiModelProperty(value = "秒杀场次")
    private List<SeckillSceneVo> secenes;

}
