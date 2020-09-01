package com.ruoyi.store.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by 魔金商城 on 18/2/5
 * 新增店铺统计实体
 */
@Data
@ApiModel(description = "新增店铺统计实体")
public class NewStoreInfoStatistics {

    /**
     * 新增店铺的时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "新增店铺的时间")
    private LocalDateTime newStoreInfoTime;

    /**
     * 新增店铺的数量
     */
    @ApiModelProperty(value = "新增店铺的数量")
    private int newStoreInfoNum;

}
