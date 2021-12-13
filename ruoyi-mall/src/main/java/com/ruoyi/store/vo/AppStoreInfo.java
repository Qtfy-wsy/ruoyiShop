package com.ruoyi.store.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by 伊甸园商城 on 2019/3/5.
 * 店铺信息实体
 */
@Data
@Builder
@ApiModel(description = "店铺信息实体")
public class AppStoreInfo {

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String username;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
