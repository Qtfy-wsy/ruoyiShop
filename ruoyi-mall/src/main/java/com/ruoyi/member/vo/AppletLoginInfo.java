package com.ruoyi.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 小程序登录信息
 *
 * @author SK
 * @since 2018/6/13
 */
@Data
@ApiModel(description = "小程序登录信息")
public class AppletLoginInfo {

    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String token;

    /**
     * 是否关联用户
     */
    @ApiModelProperty(value = "是否关联用户")
    private boolean isLink;

    /**
     * 是否有unionId
     */
    @ApiModelProperty(value = "是否有unionId")
    private boolean hasUnionId;


    /**
     * 构建已关联用户实体
     *
     * @param token token
     * @return 小程序登录信息实体
     */
    public static AppletLoginInfo buildLinked(String token) {
        AppletLoginInfo appletLoginInfo = new AppletLoginInfo();
        appletLoginInfo.isLink = true;
        appletLoginInfo.token = token;
        appletLoginInfo.hasUnionId = true;
        return appletLoginInfo;
    }

    /**
     * 构建为关联用户实体
     *
     * @param token token
     * @return 小程序登录信息实体
     */
    public static AppletLoginInfo buildNotLinked(String token) {
        AppletLoginInfo appletLoginInfo = new AppletLoginInfo();
        appletLoginInfo.isLink = false;
        appletLoginInfo.token = token;
        return appletLoginInfo;
    }


    /**
     * 构建已关联用户实体
     *
     * @return 小程序登录信息实体
     */
    public static AppletLoginInfo buildLinked() {
        AppletLoginInfo appletLoginInfo = new AppletLoginInfo();
        appletLoginInfo.isLink = true;
        appletLoginInfo.hasUnionId = true;
        return appletLoginInfo;
    }

    /**
     * 构建为关联用户实体
     *
     * @return 小程序登录信息实体
     */
    public static AppletLoginInfo buildNotLinked() {
        AppletLoginInfo appletLoginInfo = new AppletLoginInfo();
        appletLoginInfo.isLink = false;
        return appletLoginInfo;
    }

    /**
     * 添加unionId
     *
     * @param unionId 联合登录id
     * @return 当前实体
     */
    public AppletLoginInfo addUnionId(String unionId) {
        hasUnionId = !StringUtils.isEmpty(unionId);
        return this;
    }

}
