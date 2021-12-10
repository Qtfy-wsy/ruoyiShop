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
 * Created by 商城
 * 新增会员统计实体
 */
@Data
@ApiModel(description = "新增会员统计实体")
public class NewCustomerStatistics {

    /**
     * 新增会员的时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "新增会员的时间")
    private LocalDateTime newCustomerTime;

    /**
     * 新增会员的数量
     */
    @ApiModelProperty(value = "新增会员的数量")
    private int newCustomerNum;

}
