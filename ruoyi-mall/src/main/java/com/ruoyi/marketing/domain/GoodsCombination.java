package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品组合实体类
 * <p>
 * Created by 魔金商城 on 2017/6/12.
 */
@Data
@ApiModel(description = "商品组合实体")
public class GoodsCombination {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 商品组合名称
     */
    @ApiModelProperty(value = "商品组合名称")
    private String name;

    /**
     * 店铺id 平台的为0
     */
    @ApiModelProperty(value = "店铺id 平台的为0")
    private long storeId;

    /**
     * 删除标记 0 未删除 1 已删除
     */
    @ApiModelProperty(value = "删除标记 0 未删除 1 已删除")
    private int delFlag;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;

    /**
     * 删除时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime delTime;

    /**
     * 商品组合下的单品
     */
    @ApiModelProperty(value = "商品组合下的单品")
    private List<String> skuIds;

}
