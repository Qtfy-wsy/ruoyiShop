package com.ruoyi.common.utils.bean;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 生成微信小程序码请求实体类
 *
 * @author 魔金商城 created on 2020/4/14
 */
@Data
@ApiModel(description = "生成微信小程序码请求实体")
public class WeChatAppletCodeRequest {

    /**
     * 参数，最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
     */
    @ApiModelProperty(value = "参数")
    @JSONField(name = "scene")
    private String scene;

    /**
     * 路径，必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加 /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面
     */
    @ApiModelProperty(value = "路径")
    @JSONField(name = "page")
    private String page;

    /**
     * 二维码的宽度，单位 px，最小 280px，最大 1280px，默认 430
     */
    @ApiModelProperty(value = "二维码的宽度")
    @JSONField(name = "width")
    private int width;

    /**
     * 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false
     */
    @ApiModelProperty(value = "是否自动配置线条颜色")
    @JSONField(name = "auto_color")
    private boolean auto_color;

    /**
     * 颜色实体，auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
     */
    @ApiModelProperty(value = "颜色实体")
    @JSONField(name = "line_color")
    private LineColor line_color;

    /**
     * 是否需要透明底色，为 true 时，生成透明底色的小程序，默认 false
     */
    @ApiModelProperty(value = "是否需要透明底色")
    @JSONField(name = "is_hyaline")
    private boolean is_hyaline;

}
