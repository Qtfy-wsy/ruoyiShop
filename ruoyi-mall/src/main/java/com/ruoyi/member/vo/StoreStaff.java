package com.ruoyi.member.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 魔金商城 on 2017/6/12.
 */
@Data
@ApiModel(description = "员工实体")
public class StoreStaff {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 用户的真实姓名
     */
    @ApiModelProperty(value = "用户的真实姓名")
    private String releName;

    /**
     * 店铺id  平台的为0  默认为平台
     */
    @ApiModelProperty(value = "店铺id  平台的为0  默认为平台")
    private long storeId;

    /**
     * 1 普通用户 2 商家店铺用户 3 店铺员工
     */
    @ApiModelProperty(value = "1 普通用户 2 商家店铺用户 3 店铺员工")
    private String type;

    /**
     * 用户状态 1 正常 2 冻结 3 未启用 默认1
     */
    @ApiModelProperty(value = "用户状态 1 正常 2 冻结 3 未启用 默认1")
    private String status;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private long roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 最后登录时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;

    public StoreStaff getAddStoreStaffData(StoreStaff storeStaff, long storeId, String type) {
        storeStaff.setStoreId(storeId);
        storeStaff.setType(type);
        return storeStaff;
    }
}
