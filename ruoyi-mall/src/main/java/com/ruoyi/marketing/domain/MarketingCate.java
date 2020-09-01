package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by 魔金商城 on 18/1/9.
 * 促销分类
 */
@Data
@ApiModel(description = "促销分类实体")
public class MarketingCate {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 促销分类名称
     */
    @ApiModelProperty(value = "促销分类名称")
    private String name;

    /**
     * 营销分类类型 1 预售 2 拼团 3 试用 4 抢购 5 众筹
     */
    @ApiModelProperty(value = "营销分类类型 1 预售 2 拼团 3 试用 4 抢购 5 众筹")
    private String type;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private int sort;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

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
     * 构造新增促销分类
     *
     * @param type    类型
     * @param storeId 店铺id
     * @return 返回促销分类
     */
    public MarketingCate buildForAdd(String type, long storeId) {
        this.type = type;
        this.storeId = storeId;
        return this;
    }
}
